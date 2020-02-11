package com.github.jackieonway.activiti.entity;

import com.github.jackieonway.activiti.utils.page.QueryConditionBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @className SysUserDo
 * @description  
 * @author Jackie
 * @date 2020年02月11日 17:34:40
 * @version 1.0
 */

@Data
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel("系统用户")
public class SysUserDo implements Serializable {
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
    private Date gmtCreate;

    /**
     * Serializable ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分页
     **/
    @ApiModelProperty("分页")
    private QueryConditionBean queryConditionBean;
}