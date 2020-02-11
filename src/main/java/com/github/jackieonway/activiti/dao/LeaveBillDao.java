package com.github.jackieonway.activiti.dao;

import com.github.jackieonway.activiti.entity.LeaveBillDo;

import java.util.List;

public interface LeaveBillDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LeaveBillDo record);

    int insertSelective(LeaveBillDo record);

    LeaveBillDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LeaveBillDo record);

    int updateByPrimaryKey(LeaveBillDo record);

    List<LeaveBillDo> selectLeaveBills(LeaveBillDo leaveBillDo);

    Long countLeaveBill(LeaveBillDo leaveBillDo);
}