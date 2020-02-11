/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.service;

import com.github.jackieonway.activiti.entity.SysUserDo;
import com.github.jackieonway.activiti.utils.ResultMsg;

/**
 * @author Jackie
 * @version $id: SysUserService.java v 0.1 2020-02-11 17:44 Jackie Exp $$
 */
public interface SysUserService {

    ResultMsg createSysUser(SysUserDo sysUserDo);

    ResultMsg modifySysUser(SysUserDo sysUserDo);

    ResultMsg querySysUser(SysUserDo sysUserDo);

    ResultMsg querySysUsers(SysUserDo sysUserDo);

    ResultMsg deleteSysUser(SysUserDo sysUserDo);
}
