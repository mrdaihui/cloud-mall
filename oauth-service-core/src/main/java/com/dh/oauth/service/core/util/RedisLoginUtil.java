package com.dh.oauth.service.core.util;

import com.dh.oauth.service.core.entity.UserEntity;
import com.dh.oauth.service.core.shiro.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author daihui
 * @date 2020/8/4 11:24
 */
@Component
public class RedisLoginUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取LoginInfo
     * @param username
     * @return
     */
    public LoginInfo getLoginInfo(String username){
        return (LoginInfo) redisTemplate.opsForValue().get(username);
    }

    /**
     * 第一次登录且发生异常了，初始化LoginInfo
     * @param username
     */
    public void initLoginInfo(String username){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setIsLock(0);
        loginInfo.setIsLogin(0);
        loginInfo.setLoginCount(1);
        redisTemplate.opsForValue().set(username,loginInfo,120,TimeUnit.MINUTES);
    };

    /**
     *登录异常，登录次数+1
     * @param username
     */
    public void addCount(String username){
        LoginInfo loginInfo = getLoginInfo(username);
        loginInfo.setLoginCount(loginInfo.getLoginCount()+1);
        redisTemplate.opsForValue().set(username,loginInfo,120,TimeUnit.MINUTES);
    }

    /**
     * 锁定用户
     * @param username
     */
    public void lockUser(String username){
        LoginInfo loginInfo = getLoginInfo(username);
        loginInfo.setIsLock(1);
        redisTemplate.opsForValue().set(username,loginInfo,30,TimeUnit.MINUTES);
    }

    /**
     * 登录成功，刷新LoginInfo
     * @param username
     * @param token
     */
    public void refreshLoginInfo(String username,String token){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setIsLock(0);
        loginInfo.setIsLogin(1);
        loginInfo.setLoginCount(1);
        loginInfo.setToken(token);
        redisTemplate.opsForValue().set(username,loginInfo,120,TimeUnit.MINUTES);
    };

    /**
     * 删除redis缓存信息
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    /**
     * 登录成功，存储用户信息
     * @param token
     * @param userEntity
     */
    public void saveUserInfo(String token, UserEntity userEntity){
        redisTemplate.opsForValue().set(token,userEntity,120,TimeUnit.MINUTES);
    }

    /**
     * 根据token获取用户信息
     *
     */
    public UserEntity getUserInfo(String token){
        return (UserEntity)redisTemplate.opsForValue().get(token);
    }
}
