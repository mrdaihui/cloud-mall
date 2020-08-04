package com.dh.oauth.service.core.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daihui
 * @date 2020/8/4 11:19
 */
@Data
public class LoginInfo implements Serializable{

    private String token;

    //用户是否在登录状态，0-未登录，1-已登录
    private Integer isLogin;

    //登录次数，登录成功时初始化为1。每登录异常一次+1,登录异常次数5次锁定用户3一段时间
    private Integer loginCount;

    //锁定标识，0-未锁定，1-已锁定
    private Integer isLock;

}
