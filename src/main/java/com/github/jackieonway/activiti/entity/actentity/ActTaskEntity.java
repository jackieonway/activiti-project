/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.entity.actentity;

import lombok.Data;

import java.util.Date;

/**
 * @author Jackie
 * @version $id: ActTaskEntity.java v 0.1 2020-02-12 12:49 Jackie Exp $$
 */
@Data
public class ActTaskEntity {

    private String id;

    private int revision;

    private String owner;

    private String assignee;

    private String initialAssignee;

    private String parentTaskId;

    private String name;

    private String localizedName;

    private String description;

    private String localizedDescription;

    private int priority;

    private Date createTime; // The time when the task has been created

    private Date dueDate;

    private int suspensionState;

    private String category;

    private String executionId;

    private String processInstanceId;

    private String processDefinitionId;

    private String taskDefinitionKey;

    private String formKey;

    private boolean isDeleted;

    private String eventName;

    private String tenantId;

    private boolean forcedUpdate;
}
