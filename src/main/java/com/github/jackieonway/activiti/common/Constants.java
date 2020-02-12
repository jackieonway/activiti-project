package com.github.jackieonway.activiti.common;

/**
 * 常量接口
 *
 * @author mujun 2019年10月15日 09:52:57
 */
public class Constants {
    private Constants() {
    }

    /**
     * 操作失败的统一返回消息内容
     */
    public static final String ERROR_MSG = "操作失败！";

    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /**
     * 逻辑已删除(字符串）
     */
    public static final String LOGIC_DELETE_STR = "1";
    /**
     * 逻辑已删除
     */
    public static final Integer LOGIC_DELETE = 1;
    /**
     * 逻辑未删除
     */
    public static final Integer LOGIC_NOT_DELETE = 0;
    /**
     * 日期时间格式
     */
    public static final String DATE_TIME_FORMAT = "HH:mm";

    /**
     * rest Template 连接超时
     */
    public static final int REST_CONNECT_TIMEOUT = 3000;

    /**
     * rest Template 读写超时
     */
    public static final int REST_READ_TIMEOUT = 30000;

    /**
     * 以后需要添加模板的下载地址
     */
    public static final String TEMPLATE = "templates/export_template.xlsx";

    /**
     * 字符长度常量
     */
    public static final Integer CHAR_LENGTH = 200;

    /**
     * 批量上传最大文件个数
     */
    public static final Integer MAX_FILE_NUM = 4;

    /**
     * 批量上传的数据正文内容从第几行开始读取
     */
    public static final Integer ROW_INDEX = 4;

    /**
     * 表单填报校验保存操作
     */
    public static final String FORMFILL_SAVE_CHECK = "1";

    /**
     * 表单填报校验修改操作
     */
    public static final String FORMFILL_UPDATE_CHECK = "2";


    public static final Integer VERSION_MUN_MAX = 10;

    /**
     * 每月第一天
     */
    public static final Integer FIRSTDAY_OF_MOUTH = 01;

    /**
     * 横线
     */
    public static final String LINE = "-";

    /**
     * 数字常量2
     */
    public static final Integer NUM_TWO = 2;

    /**
     * excel文件上传类型，批量/单个
     */
    public static final String BATCH = "batch";
    public static final String SINGLE = "single";
}
