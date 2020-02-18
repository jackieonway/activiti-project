/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.controller.workflow;

import com.github.jackieonway.activiti.entity.actentity.ActTaskEntity;
import com.github.jackieonway.activiti.service.WorkFlowService;
import com.github.jackieonway.activiti.utils.ResponseUtils;
import com.github.jackieonway.activiti.utils.ResultMsg;
import com.github.jackieonway.activiti.utils.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String s = "tchnicalReview";
    }
}
