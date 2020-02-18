/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.controller.leavebill;

import com.github.jackieonway.activiti.entity.LeaveBillDo;
import com.github.jackieonway.activiti.service.LeaveBillService;
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
 * @version $id: LeaveBillController.java v 0.1 2020-02-12 9:41 Jackie Exp $$
 */
@CrossOrigin
@RestController
@RequestMapping("/leaveBill")
@Api(value = "请假单相关接口", tags = "请假单相关接口")
public class LeaveBillController {

    @Autowired
    private LeaveBillService leaveBillService;

    @GetMapping("/createLeaveBill")
    @ApiOperation(value = "创建请假单")
    public ResultMsg createLeaveBill(LeaveBillDo leaveBillDo) {
        return leaveBillService.createLeaveBill(leaveBillDo);
    }

    @GetMapping("/modifyLeaveBill")
    @ApiOperation(value = "根据请假单id删除请假单")
    public ResultMsg modifyLeaveBill(LeaveBillDo leaveBillDo) {
        return leaveBillService.modifyLeaveBill(leaveBillDo);
    }

    @GetMapping("/queryLeaveBill")
    @ApiOperation(value = "根据请假单id查询请假单")
    public ResultMsg queryLeaveBill(LeaveBillDo leaveBillDo) {
        return leaveBillService.queryLeaveBill(leaveBillDo);
    }

    @GetMapping("/queryLeaveBills")
    @ApiOperation(value = "查询请假单列表")
    public ResultMsg queryLeaveBills(LeaveBillDo leaveBillDo) {
        return leaveBillService.queryLeaveBills(leaveBillDo);
    }

    @GetMapping("/deleteLeaveBill")
    @ApiOperation(value = "根据请假单id删除请假单")
    public ResultMsg deleteLeaveBill(LeaveBillDo leaveBillDo) {
        return leaveBillService.deleteLeaveBill(leaveBillDo);
    }
}
