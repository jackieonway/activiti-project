package com.github.jackieonway.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jackieonway.activiti.utils.page.QueryConditionBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @className LeaveBillDo
 * @description  
 * @author Jackie
 * @date 2020年02月11日 17:34:40
 * @version 1.0
 */

@Data
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel("请假单")
public class LeaveBillDo implements Serializable {
    /** 
    * id
    **/ 
    @ApiModelProperty("id")
    private Integer id;

    /** 
    * 请假人id
    **/ 
    @ApiModelProperty("请假人id")
    private Integer userId;

    /** 
    * 请假原因
    **/ 
    @ApiModelProperty("请假原因")
    private String reason;

    /** 
    * 请假时间
    **/ 
    @ApiModelProperty("请假时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date leaveTime;

    /** 
    * 审批状态 0:未提交 1:审批中 2:审批通过 3:放弃
    **/ 
    @ApiModelProperty("审批状态 0:未提交 1:审批中 2:审批通过 3:放弃")
    private Integer status;

    /**
     * Serializable ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分页
     **/
    @ApiModelProperty("分页")
    private QueryConditionBean queryConditionBean;
    /**
     * 开始时间
     **/
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     **/
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date endTime;
}