package com.dh.oauth.service.core.shiro;

import com.dh.common.models.R;
import com.dh.common.util.SpringContextUtil;
import com.dh.common.util.StringUtils;
import com.dh.oauth.service.core.entity.UserEntity;
import com.dh.oauth.service.core.util.RedisLoginUtil;
import com.google.gson.Gson;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author daihui
 * @date 2020/7/29 13:50
 */
public class OauthAuthenticatingFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) servletRequest);

        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new OauthAuthenticationToken(token);
    }

    /**
     * 在这里验证请求是否携带token以及验证token的合法性。这里验证失败就不会走shiro的认证操作
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        /**
         * 获取当前用户登陆的上下文信息
         * 如果token不存在，直接返回401
         */
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)) {
            return generateErrorTokenResponse((HttpServletRequest)servletRequest, (HttpServletResponse) servletResponse, HttpStatus.UNAUTHORIZED.toString(), "invalid token");
        }
        UserEntity userEntity = SpringContextUtil.getBean(RedisLoginUtil.class).getUserInfo(token);

        if (userEntity == null) {
            return generateErrorTokenResponse((HttpServletRequest)servletRequest, (HttpServletResponse) servletResponse, HttpStatus.UNAUTHORIZED.toString(), "invalid token");
        }

        return executeLogin(servletRequest,servletResponse);
    }



    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }

        return token;
    }

    private boolean generateErrorTokenResponse(HttpServletRequest request,HttpServletResponse response, String statusCode, String message) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        String json = new Gson().toJson(R.error(statusCode, message));

        httpResponse.getWriter().print(json);

        return false;
    }
}
