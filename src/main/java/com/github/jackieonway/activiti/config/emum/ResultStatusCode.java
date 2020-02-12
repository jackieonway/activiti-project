package com.github.jackieonway.activiti.config.emum;

/**
 * resultCode枚举类
 *
 * @author :luzhifu
 */
public enum ResultStatusCode {

    /**
     * OK
     */
    OK(0, "OK"),
    /**
     * ERROR
     */
    ERROR(-1, "ERROR");

    private int resultCode;
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    ResultStatusCode(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
