package com.github.jackieonway.activiti.controller.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jackieonway.activiti.entity.LeaveBillDo;
import com.github.jackieonway.activiti.service.LeaveBillService;
import com.github.jackieonway.activiti.utils.ResponseUtils;
import com.github.jackieonway.activiti.utils.ResultMsg;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ActivitiModelController {
    private static final Logger log = LoggerFactory.getLogger(ActivitiModelController.class);
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private LeaveBillService leaveBillService;

    /**
     * 新建一个空模型
     */
    @RequestMapping("/create")
    public void newModel(HttpServletResponse response) throws IOException {
        //RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        String id = model.getId();
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.set("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes(StandardCharsets.UTF_8));
        response.sendRedirect("/modeler.html?modelId=" + id);
    }

    /**
     * 获取所有模型
     */
    @GetMapping("/modelList")
    @ResponseBody
    public Object modelList() {
        return repositoryService.createModelQuery().list();
    }

    /**
     * 发布模型为流程定义
     * http://localhost:8080/deploy?modelId=1
     */
    @GetMapping("/deploy")
    @ResponseBody
    public Object deploy(String modelId) throws IOException {
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        byte[] pngBytes = repositoryService.getModelEditorSourceExtra(modelData.getId());
        if (bytes == null && pngBytes == null) {
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (CollectionUtils.isEmpty(model.getProcesses())) {
            return "数据模型不符要求，请至少设计一条主线流程。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        String processPngName = modelData.getName() + ".png";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addInputStream(processName, new ByteArrayInputStream(bpmnBytes))
                .addInputStream(processPngName, new ByteArrayInputStream(pngBytes))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return "SUCCESS";
    }

    /**
     * 启动流程
     */
    @GetMapping("/start")
    @ResponseBody
    public Object startProcess(String keyName) {
        ProcessInstance process = runtimeService.startProcessInstanceByKey(keyName);
        return process.getId() + " : " + process.getProcessDefinitionId();
    }

    /**
     * 启动流程
     * http://localhost:8080/startBusinessKey?keyName=leaveBill&name=userId&value=4&businessKey=1
     */
    @GetMapping("/startBusinessKey")
    @ResponseBody
    public ResultMsg startProcessByBusinessKey(String keyName, String name, String value, String businessKey) {
        LeaveBillDo leaveBillDo = new LeaveBillDo();
        leaveBillDo.setId(Integer.valueOf(businessKey));
        ResultMsg<LeaveBillDo> resultMsg = leaveBillService.queryLeaveBill(leaveBillDo);
        LeaveBillDo resultData = resultMsg.getResultData();
        if (resultData == null) {
            return ResponseUtils.fail("请假单不存在");
        }
        if (resultData.getStatus() > 0) {
            return ResponseUtils.fail("请假单已在审批或审批结束");
        }
        Map<String, Object> variabals = new HashMap<>();
        variabals.put(name, value);
        ProcessInstance process = runtimeService.startProcessInstanceByKey(keyName, businessKey, variabals);
        Map<String, Object> result = new HashMap<>();
        result.put("processInstanceId", process.getId());
        result.put("processDefinitionId", process.getProcessDefinitionId());
        leaveBillDo.setStatus(1);
        leaveBillService.modifyLeaveBill(leaveBillDo);
        return ResponseUtils.success(result);
    }


    /**
     * 提交任务
     */
    @GetMapping("/run")
    @ResponseBody
    public Object run(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        log.info("task {} find ", task.getId());
        taskService.complete(task.getId());
        return "SUCCESS";
    }

    /**
     * 提交任务
     * http://localhost:8080/runVariabals?processInstanceId=20001&name=giveup,userId&value=false,3&leaveBillId=1
     */
    @GetMapping("/runVariabals")
    @ResponseBody
    public Object runVariabals(String processInstanceId, String name, String value, String userId, Integer leaveBillId) {
        LeaveBillDo leaveBillDo = new LeaveBillDo();
        leaveBillDo.setId(leaveBillId);
        ResultMsg<LeaveBillDo> resultMsg = leaveBillService.queryLeaveBill(leaveBillDo);
        if (resultMsg.getResultData() == null) {
            return ResponseUtils.fail("请假单不存在");
        }
        if (resultMsg.getResultData().getStatus() < 1) {
            return ResponseUtils.fail("非法操作");
        }
        if (resultMsg.getResultData().getStatus() > 1) {
            return ResponseUtils.fail("请假单已审批结束");
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task == null) {
            return "当前审批已经审批结束";
        }
        log.info("task {} find ", task.getId());
        if (!task.getAssignee().equalsIgnoreCase(userId)) {
            return "FAIL，非法操作";
        }
        Map<String, Object> variabals = new HashMap<>();
        String[] names = name.split(",");
        String[] values = value.split(",");
        String condition = "";
        boolean result = false;
        for (int i = 0; i < names.length; i++) {
            if ("giveup".equalsIgnoreCase(names[i])) {
                condition = "giveup";
            }
            if ("false".equalsIgnoreCase(values[i])) {
                variabals.put(names[i], false);
                result = false;
            } else if ("true".equalsIgnoreCase(values[i])) {
                variabals.put(names[i], true);
                result = true;
            } else {
                variabals.put(names[i], values[i]);
            }
        }
        taskService.complete(task.getId(), variabals);
        task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task == null) {
            if ("giveup".equalsIgnoreCase(condition) && result) {
                leaveBillDo.setStatus(3);
            } else {
                leaveBillDo.setStatus(2);
            }
            leaveBillService.modifyLeaveBill(leaveBillDo);
        }
        return "SUCCESS";
    }
}
