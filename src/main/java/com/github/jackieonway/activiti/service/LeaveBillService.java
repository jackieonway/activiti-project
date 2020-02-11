/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.service;

import com.github.jackieonway.activiti.entity.LeaveBillDo;
import com.github.jackieonway.activiti.utils.ResultMsg;
import com.github.jackieonway.activiti.utils.page.QueryConditionBean;

/**
 * @author Jackie
 * @version $id: LeaveBillService.java v 0.1 2020-02-11 17:44 Jackie Exp $$
 */
public interface LeaveBillService {

    ResultMsg createLeaveBill(LeaveBillDo leaveBillDo);

    ResultMsg modifyLeaveBill(LeaveBillDo leaveBillDo);

    ResultMsg queryLeaveBill(LeaveBillDo leaveBillDo);

    ResultMsg queryLeaveBills(LeaveBillDo leaveBillDo);

    ResultMsg deleteLeaveBill(LeaveBillDo leaveBillDo);
}
