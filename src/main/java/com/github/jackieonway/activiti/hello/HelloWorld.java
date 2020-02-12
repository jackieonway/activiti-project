/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.hello;

import com.github.jackieonway.activiti.ActivitiProjectApplication;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileNotFoundException;

/**
 * @author Jackie
 * @version $id: HelloWorld.java v 0.1 2020-02-06 17:05 Jackie Exp $$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiProjectApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloWorld {


    @Test
    public void deploy() throws FileNotFoundException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment helloWorld = repositoryService.createDeployment().name("HelloWorld")
//                .addInputStream("HelloWorld.bpmn20.xml",new FileInputStream(new File("D:\\activiti\\xml\\HelloWorld.bpmn20.xml")))
//                .addInputStream("HelloWorld.jpg",new FileInputStream(new File("D:\\activiti\\pic\\HelloWorld.jpg")))
                .addClasspathResource("file:///D:/activiti/xml/HelloWorld.bpmn20.xml")
                .addClasspathResource("file:///D:/activiti/pic/HelloWorld.jpg")
                .deploy();
        System.out.println("发布流程ID： " + helloWorld.getId());
    }

    @Test
    public void start() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance process = runtimeService.startProcessInstanceByKey("process");
        System.out.println("流程实例Id： " + process.getId());
    }

    @Test
    public void run() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId("25001").singleResult();
        System.out.println("流程任务Id： " + task.getId());
        taskService.complete(task.getId());
        System.out.println("流程执行成功 ");
    }

}
