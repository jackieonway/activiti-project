/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.service.impl;

import com.github.jackieonway.activiti.dao.LeaveBillDao;
import com.github.jackieonway.activiti.entity.LeaveBillDo;
import com.github.jackieonway.activiti.service.LeaveBillService;
import com.github.jackieonway.activiti.utils.ResponseUtils;
import com.github.jackieonway.activiti.utils.ResultMsg;
import com.github.jackieonway.activiti.utils.page.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jackie
 * @version $id: LeaveBillServiceImpl.java v 0.1 2020-02-11 17:45 Jackie Exp $$
 */
@Service
public class LeaveBillServiceImpl implements LeaveBillService {

    @Resource
    private LeaveBillDao leaveBillDao;

    @Override
    public ResultMsg createLeaveBill(LeaveBillDo leaveBillDo) {
        int i = leaveBillDao.insertSelective(leaveBillDo);
        if (i > 0) {
            return ResponseUtils.success();
        } else {
            return ResponseUtils.fail();
        }
    }

    @Override
    public ResultMsg modifyLeaveBill(LeaveBillDo leaveBillDo) {
        int i = leaveBillDao.updateByPrimaryKeySelective(leaveBillDo);
        if (i > 0) {
            return ResponseUtils.success();
        } else {
            return ResponseUtils.fail();
        }
    }

    @Override
    public ResultMsg<LeaveBillDo> queryLeaveBill(LeaveBillDo leaveBillDo) {
        return ResponseUtils.success(leaveBillDao.selectByPrimaryKey(leaveBillDo.getId()));
    }

    @Override
    public ResultMsg queryLeaveBills(LeaveBillDo leaveBillDo) {
        List<LeaveBillDo> leaveBillDos = leaveBillDao.selectLeaveBills(leaveBillDo);
        Long count = leaveBillDao.countLeaveBill(leaveBillDo);
        PageResult<LeaveBillDo> pageResult = new PageResult<>();
        pageResult.setPageNum(leaveBillDo.getQueryConditionBean().getPageNum());
        pageResult.setPageSize(leaveBillDo.getQueryConditionBean().getPageSize());
        pageResult.setList(leaveBillDos);
        pageResult.setTotalCount(count);
        return ResponseUtils.success(pageResult);
    }

    @Override
    public ResultMsg deleteLeaveBill(LeaveBillDo leaveBillDo) {
        int i = leaveBillDao.deleteByPrimaryKey(leaveBillDo.getId());
        if (i > 0) {
            return ResponseUtils.success();
        } else {
            return ResponseUtils.fail();
        }
    }
}
