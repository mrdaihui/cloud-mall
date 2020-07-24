package com.dh.stock.service.core.config;


import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.*;

/**
 * 控制方法的幂等 {@link WebMvcRegistration.IdempotentRequestMappingHandlerAdapter#handleInternal(HttpServletRequest, HttpServletResponse, HandlerMethod)}
 * @author daihui
 * @date 2020/7/24 14:18
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     *  方法的lru淘汰阈值
     */
    int size() default 1000;

}
