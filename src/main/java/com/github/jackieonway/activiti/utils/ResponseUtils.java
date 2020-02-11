package com.github.jackieonway.activiti.utils;


/**
 * @Author deng1
 * @Description 返回工具类
 * @Date 2019/11/26 上午 10:57
 **/
@SuppressWarnings({"all"})
public class ResponseUtils<T> extends ResultMsg<T> {

    /**
     * 操作成功，无返回数据
     *
     * @return message
     */
    public static ResultMsg success() {
        return ResultMsg.defaultSuccessResponse();
    }

    /**
     * 操作成功，有返回数据
     *
     * @return message
     */
    public static <T> ResultMsg<T> success(T data) {
        return ResultMsg.defaultSuccessResponse().setResultData(data);
    }

    /**
     * 操作失败，无返回数据
     *
     * @return message
     */
    public static <T> ResultMsg<T> fail() {
        return ResultMsg.defaultErrorResponse();
    }

    /**
     * 操作失败，无返回数据，设置错误信息
     *
     * @return message
     */
    public static <T> ResultMsg<T> fail(String msg) {
        return ResultMsg.defaultErrorResponse().setResultMsg(msg);
    }

    /**
     * 验证失败，无返回数据
     *
     * @return message
     */
    public static <T> ResultMsg<T> validFail() {
        return ResultMsg.validFailResponse();
    }

    /**
     * 验证失败，无返回数据，设置错误信息
     *
     * @return message
     */
    public static <T> ResultMsg<T> validFail(String msg) {
        ResultMsg resultMsg = ResultMsg.validFailResponse();
        resultMsg.setResultMsg(msg);
        return resultMsg;
    }

    /**
     * 服务失败，无返回数据
     *
     * @return message
     */
    public static <T> ResultMsg<T> fallback() {
        return ResultMsg.defaultFallBack();
    }

    /**
     * 服务失败，无返回数据，设置错误信息
     *
     * @return message
     */
    public static <T> ResultMsg<T> fallback(String msg) {
        ResultMsg resultMsg = ResultMsg.defaultFallBack();
        resultMsg.setResultMsg(msg);
        return resultMsg;
    }

}
