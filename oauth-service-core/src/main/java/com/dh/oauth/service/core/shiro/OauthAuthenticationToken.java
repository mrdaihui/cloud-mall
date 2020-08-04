package com.dh.oauth.service.core.shiro;

import com.dh.common.util.UUIDUtils;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author daihui
 * @date 2020/7/29 14:04
 */
public class OauthAuthenticationToken implements AuthenticationToken {

    private String username;

    private String password;

    private String token;

    public OauthAuthenticationToken(String username,String password){
        this.username = username;
        this.password = password;
        //生成token
        this.token = UUIDUtils.getUUID();
    }

    public OauthAuthenticationToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return password;
    }


    //判断是否是在登录时创建
    public boolean isLogin(){
        return username !=null && password !=null;
    }
}
