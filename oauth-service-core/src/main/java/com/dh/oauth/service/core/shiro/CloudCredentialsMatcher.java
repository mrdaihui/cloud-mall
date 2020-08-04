package com.dh.oauth.service.core.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author daihui
 * @date 2020/7/28 16:04
 */
public class CloudCredentialsMatcher extends SimpleCredentialsMatcher {


    /**
     * 这里做自定义密码比对
     * @param token 登录用户传过来的信息
     * @param info，数据库中的信息
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //不是登录，直接验证通过。接口访问的安全性已经在OauthAuthenticatingFilter中验证过token的合法性了
        if(!((OauthAuthenticationToken)token).isLogin()){
            return true;
        }
        return super.doCredentialsMatch(token, info);
    }
}
