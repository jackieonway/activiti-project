/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.service.impl;

import com.github.jackieonway.activiti.dao.SysUserDao;
import com.github.jackieonway.activiti.entity.LeaveBillDo;
import com.github.jackieonway.activiti.entity.SysUserDo;
import com.github.jackieonway.activiti.service.SysUserService;
import com.github.jackieonway.activiti.utils.ResponseUtils;
import com.github.jackieonway.activiti.utils.ResultMsg;
import com.github.jackieonway.activiti.utils.page.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jackie
 * @version $id: SysUserServiceImpl.java v 0.1 2020-02-11 17:45 Jackie Exp $$
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Override
    public ResultMsg createSysUser(SysUserDo sysUserDo) {
        int i = sysUserDao.insertSelective(sysUserDo);
        if (i > 0) {
            return ResponseUtils.success();
        }else {
            return ResponseUtils.fail();
        }
    }

    @Override
    public ResultMsg modifySysUser(SysUserDo sysUserDo) {
        int i = sysUserDao.updateByPrimaryKeySelective(sysUserDo);
        if (i > 0) {
            return ResponseUtils.success();
        }else {
            return ResponseUtils.fail();
        }
    }

    @Override
    public ResultMsg querySysUser(SysUserDo sysUserDo) {
        return ResponseUtils.success(sysUserDao.selectByPrimaryKey(sysUserDo.getId()));
    }

    @Override
    public ResultMsg querySysUsers(SysUserDo sysUserDo) {
        List<SysUserDo> sysUserDos = sysUserDao.selectSysUsers(sysUserDo);
        Long count = sysUserDao.countSysUser(sysUserDo);
        PageResult<SysUserDo> pageResult = new PageResult<>();
        pageResult.setPageNum(sysUserDo.getQueryConditionBean().getPageNum());
        pageResult.setPageSize(sysUserDo.getQueryConditionBean().getPageSize());
        pageResult.setList(sysUserDos);
        pageResult.setTotalCount(count);
        return ResponseUtils.success(pageResult);
    }

    @Override
    public ResultMsg deleteSysUser(SysUserDo sysUserDo) {
        return ResponseUtils.success(sysUserDao.deleteByPrimaryKey(sysUserDo.getId()));
    }
}
