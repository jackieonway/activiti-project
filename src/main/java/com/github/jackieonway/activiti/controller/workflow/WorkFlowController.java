/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.controller.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jackieonway.activiti.entity.actentity.ActTaskEntity;
import com.github.jackieonway.activiti.service.ChangeBpmnService;
import com.github.jackieonway.activiti.service.WorkFlowService;
import com.github.jackieonway.activiti.utils.ResponseUtils;
import com.github.jackieonway.activiti.utils.ResultMsg;
import com.github.jackieonway.activiti.utils.page.PageResult;
import com.sun.xml.internal.fastinfoset.stax.factory.StAXInputFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackie
 * @version $id: WorkFlowController.java v 0.1 2020-02-12 11:27 Jackie Exp $$
 */
@CrossOrigin
@RestController
@RequestMapping("/workFlow")
@Api(value = "用户流程相关接口", tags = "用户相关接口")
public class WorkFlowController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private ChangeBpmnService changeBpmnService;

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * 查询当前用户待办任务
     * http://localhost:8080/workFlow/queryCurrentUserTask?userId=3&pageNum=1&pageSize=10
     *
     * @param userId   用户id
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 待办任务列表
     * @author Jackie
     * @date 2020/2/12 12:58
     * @method-name com.github.jackieonway.activiti.controller.workflow.WorkFlowController#queryCurrentUserTask
     * @see WorkFlowController
     * @since 1.0
     */
    @GetMapping("/queryCurrentUserTask")
    @ApiOperation(value = "查询用户当前任务")
    public ResultMsg queryCurrentUserTask(String userId, Integer pageNum, Integer pageSize) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(userId);
        List<Task> list = taskQuery.listPage((pageNum - 1) * pageSize, pageSize);
        List<ActTaskEntity> actTaskEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(task -> {
                ActTaskEntity actTaskEntity = new ActTaskEntity();
                BeanUtils.copyProperties(task, actTaskEntity);
                actTaskEntities.add(actTaskEntity);
            });
        }
        long count = taskQuery.count();
        PageResult<ActTaskEntity> pageResult = PageResult.newEmptyResult(pageNum, pageSize);
        pageResult.setTotalCount(count);
        pageResult.setList(actTaskEntities);
        return ResponseUtils.success(pageResult);
    }

    /**
     * http://localhost:8080/workFlow/showImage?processInstanceId=40001
     *
     * @param processInstanceId
     * @param response
     * @throws IOException
     */
    @GetMapping("/showImage")
    @ApiOperation(value = "查询流程图")
    public void readProcessImg(String processInstanceId, HttpServletResponse response) throws IOException {
        workFlowService.readProcessImg(processInstanceId, response);
    }

    @GetMapping("/handle")
    @ApiOperation(value = "处理数据")
    public void handle() throws IOException {
        changeBpmnService.changeBpmnService();
    }

    @PostMapping(value = "/uploadFile")
    public void deployUploadedFile(@RequestParam("file") MultipartFile uploadfile) {
        InputStreamReader in = null;
        try {
            String fileName = uploadfile.getOriginalFilename();
            if (!(fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn"))) {
                return;
            }
            XMLInputFactory xif = new StAXInputFactory();
            in = new InputStreamReader(new ByteArrayInputStream(uploadfile.getBytes()), "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

            if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
                System.out.println("err1");
                return;
            }
            if (bpmnModel.getLocationMap().isEmpty()) {
                System.out.println("err2");
                return;
            }
            String processName = null;
            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
                processName = bpmnModel.getMainProcess().getName();
            } else {
                processName = bpmnModel.getMainProcess().getId();
            }
            Model modelData;
            modelData = repositoryService.newModel();
            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(processName);
            repositoryService.saveModel(modelData);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (Exception e) {
            System.out.println("err4");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("err5");
                }
            }
        }
    }

}
