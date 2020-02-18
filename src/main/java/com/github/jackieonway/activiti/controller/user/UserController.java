/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.controller.user;

import com.github.jackieonway.activiti.entity.SysUserDo;
import com.github.jackieonway.activiti.service.SysUserService;
import com.github.jackieonway.activiti.utils.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jackie
 * @version $id: UserController.java v 0.1 2020-02-12 9:41 Jackie Exp $$
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(value = "用户相关接口", tags = "用户相关接口")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/createUser")
    @ApiOperation(value = "创建用户")
    public ResultMsg createSysUser(SysUserDo sysUserDo) {
        return sysUserService.createSysUser(sysUserDo);
    }

    @GetMapping("/modifyUser")
    @ApiOperation(value = "根据用户id删除用户")
    public ResultMsg modifySysUser(SysUserDo sysUserDo) {
        return sysUserService.modifySysUser(sysUserDo);
    }

    @GetMapping("/queryUser")
    @ApiOperation(value = "根据用户id查询用户")
    public ResultMsg querySysUser(SysUserDo sysUserDo) {
        return sysUserService.querySysUser(sysUserDo);
    }

    @GetMapping("/queryUsers")
    @ApiOperation(value = "查询用户列表")
    public ResultMsg querySysUsers(SysUserDo sysUserDo) {
        return sysUserService.querySysUsers(sysUserDo);
    }

    @GetMapping("/deleteUser")
    @ApiOperation(value = "根据用户id删除用户")
    public ResultMsg deleteUser(SysUserDo sysUserDo) {
        return sysUserService.deleteSysUser(sysUserDo);
    }
}
