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
    MQ_RECEIVE_ERROR("7002", "接收MQ消息异常"),

    ERROR_TOKEN("9900", "TOKEN认证错误"),
    ERROR_USER_OR_PASS("9901", "用户名或密码错误，请注意区分用户名或密码的大小写"),
    LOCK_LOGIN("9002", "该账户已被锁定"),
    LOCK_LOGIN_PASS_MISTAKE("9003", "该账户因为密码错误次数过多，已被暂时锁定，请30分钟后重试登录"),
    ERROR_CAPTCHA("9004", "验证码错误或失效"),
    ERROR_SHIRO_AUTH("9005","鉴权失败，暂无权限执行当前操作");


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


