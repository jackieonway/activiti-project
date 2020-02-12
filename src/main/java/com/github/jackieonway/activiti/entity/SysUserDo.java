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
 * @author Jackie
 * @version 1.0
 * @className SysUserDo
 * @description
 * @date 2020年02月11日 17:34:40
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("系统用户")
public class SysUserDo implements Serializable {
    /**
     * Serializable ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     **/
    @ApiModelProperty("id")
    private Integer id;
    /**
     * 用户名
     **/
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 姓名
     **/
    @ApiModelProperty("姓名")
    private String name;
    /**
     * 用户等级
     **/
    @ApiModelProperty("用户等级")
    private Integer level;
    /**
     * 管理者id
     **/
    @ApiModelProperty("管理者id")
    private Integer leaderId;
    /**
     * 创建时间
     **/
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
    /**
     * 分页
     **/
    @ApiModelProperty("分页")
    private QueryConditionBean queryConditionBean;


    /**
     * 关键字
     **/
    @ApiModelProperty("关键字")
    private String keywords;

    /**
     * 开始时间
     **/
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     **/
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}