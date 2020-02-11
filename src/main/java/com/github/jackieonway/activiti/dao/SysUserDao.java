package com.github.jackieonway.activiti.dao;

import com.github.jackieonway.activiti.entity.SysUserDo;

public interface SysUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserDo record);

    int insertSelective(SysUserDo record);

    SysUserDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserDo record);

    int updateByPrimaryKey(SysUserDo record);
}