package com.dh.common.models;


public enum CodeDefined {

    SUCCESS("200", "true"),
    ERROR("9999", "系统异常,请稍后再试"),
    // URL、参数 错误
    URL_NOT_FOUND("2100", "URL地址错误"),
    METHOD_ERROR("2101", "请求方法类型错误，可以使用' %s '等类型"),
    ERROR_PARAMETER("2102","参数异常"),
    ERROR_SYNTAX("2201", "请求语法错误"),
    ERROR_CAPTCHA_LACK_PARAM("2202", "缺少获取验证码的必要参数"),
    ERROR_OBJECT_TO_JSONSTRING("2203", "对象转json格式字符串异常"),

    //登录错误
    USER_PASS_ERROR("3100", "用户名或密码错误"),
    ACCESS_DENY("3200", "没权限访问"),

    // MQ 错误
    MQ_SEND_ERROR("7001", "发送MQ消息异常"),
    MQ_RECEIVE_ERROR("7002", "接收MQ消息异常");


	/***************************************************************************/

    private String code;

    private String msg;

    CodeDefined(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getValue() {
        return this.code;
    }

    public String getDesc() {
        return this.msg;
    }

}


