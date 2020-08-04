package com.dh.oauth.service.core.controller;

import com.dh.common.models.CodeDefined;
import com.dh.common.models.R;
import com.dh.oauth.service.core.service.UserService;
import com.dh.oauth.service.core.shiro.LoginInfo;
import com.dh.oauth.service.core.shiro.OauthAuthenticationToken;
import com.dh.oauth.service.core.entity.UserEntity;
import com.dh.oauth.service.core.util.RedisLoginUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daihui
 * @date 2020/7/29 9:51
 */
@RestController
public class UserContoller {


    @Autowired
    private RedisLoginUtil redisLoginUtil;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public R login(@RequestBody UserEntity userEntity){
        String username = userEntity.getUsername();

        //1.验证验证码
        //2.getUserByName()得到user,user=null返回错误，user不可用返回错误
        //这里查处了用户角色，权限，只是为了方便。模拟数据库查询
        UserEntity user = userService.findUserByName(username);
        if(user==null){
            return R.error(CodeDefined.ERROR_USER_OR_PASS);
        }
        // 判断账户是否可用
        if (user.getStatus() == 0) {
            return R.error(CodeDefined.LOCK_LOGIN);
        }
        //3.通过username查询redis得到用户登录信息(token，是否是登录状态，登录异常次数，是否锁定)
        LoginInfo loginInfo = redisLoginUtil.getLoginInfo(username);
        if(loginInfo!=null && loginInfo.getIsLock().equals(1)){
            return R.error(CodeDefined.LOCK_LOGIN_PASS_MISTAKE);
        }
        OauthAuthenticationToken oauthAuthenticationToken = new OauthAuthenticationToken(username,userEntity.getPassword());

        String token = oauthAuthenticationToken.getPrincipal().toString();
        //在这里存储用户信息，为了方便在realm里面向shiro中存储principal。这里的user对象可以放入任何后续shiro认证成功后系统后续需要用到或者返回的数据
        redisLoginUtil.saveUserInfo(token,user);
        try {

            SecurityUtils.getSubject().login(oauthAuthenticationToken);
            //登录成功，判断这个账户是否是已登录的状态
            if(loginInfo!=null && loginInfo.getIsLogin().equals(1)){
                //当前账户是已登录的状态，清除前一个登录状态下的token信息
                redisLoginUtil.deleteKey(loginInfo.getToken());
            }

            // 刷新LoginInfo
            redisLoginUtil.refreshLoginInfo(username,token);
            //存储用户信息
            redisLoginUtil.saveUserInfo(token,(UserEntity) SecurityUtils.getSubject().getPrincipal());
        } catch (AuthenticationException e) {
            //登录失败
            if(loginInfo==null){
                //第一次登录且发生异常了
                redisLoginUtil.initLoginInfo(username);
            }else if(loginInfo!=null && loginInfo.getErrorLoginCount()>=4){
                //登录异常次数过多（登录异常5次），锁定用户
                redisLoginUtil.lockUser(username);
            }else {
                //登录异常，登录次数+1
                redisLoginUtil.addCount(username);
            }
            e.printStackTrace();
            return R.error(CodeDefined.USER_PASS_ERROR);
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return R.error(CodeDefined.ACCESS_DENY);
        }
        return R.ok().put("token",token);
    }


    @GetMapping("user/info")
    public R info(){
        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        return R.ok().data(userEntity);
    }

    @RequiresPermissions(value = {"add"}, logical = Logical.OR)
    @GetMapping("user/add")
    public R add(){
        return R.ok();
    }
}
