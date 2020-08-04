package com.dh.oauth.service.core.shiro;

import com.dh.oauth.service.core.entity.PermissionEntity;
import com.dh.oauth.service.core.entity.RoleEntity;
import com.dh.oauth.service.core.entity.UserEntity;
import com.dh.oauth.service.core.service.UserService;
import com.dh.oauth.service.core.util.RedisLoginUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author daihui
 * @date 2020/7/28 16:04
 */
public class CloudRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisLoginUtil redisLoginUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OauthAuthenticationToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserEntity userEntity = (UserEntity) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (RoleEntity role : userEntity.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (PermissionEntity permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserEntity userEntity =null;
        String token = authenticationToken.getPrincipal().toString();
        if(((OauthAuthenticationToken)authenticationToken).isLogin()){
            String username = ((OauthAuthenticationToken)authenticationToken).getUsername();
            userEntity = userService.findUserByName(username);
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo =null;
        UserEntity user = redisLoginUtil.getUserInfo(token);
        if(userEntity==null){
            //接口访问走这里，不对密码进行验证。安全验证已经在OauthAuthenticatingFilter中对token合法性经行验证过了
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, "", getName());
        }else {
            //走正常的登录流程
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, userEntity.getPassword().toString(), getName());
        }

        return simpleAuthenticationInfo;
    }
}
