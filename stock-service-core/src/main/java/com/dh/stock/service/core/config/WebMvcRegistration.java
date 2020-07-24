package com.dh.stock.service.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 幂等，接口调用方第一次调用接口时生成全局唯一的requestId,接口返回之前多次调用requestId为同一个
 * @author daihui
 * @date 2020/7/24 14:18
 */
@Slf4j
@Component
@ConditionalOnWebApplication
public class WebMvcRegistration implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new IdempotentRequestMappingHandlerAdapter();
    }

    public static class IdempotentRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

        /**
         *  method -> (requestId,{@link IdempotentRequestMappingHandlerAdapter#OBJECT})
         */
        private final Map<Method, LinkedHashMap<String, Object>> lruContainer = new HashMap<>();

        private Object OBJECT = new Object();

        @Override
        protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
            Idempotent idempotent = handlerMethod.getMethodAnnotation(Idempotent.class);
            idempotent = idempotent == null ? handlerMethod.getBeanType().getDeclaredAnnotation(Idempotent.class) : idempotent;
            Method method = handlerMethod.getMethod();
            if (idempotent != null) {
                String requestId = getRequestId(request);
                if (requestId == null){
                    response.getOutputStream().write("requestId not fund".getBytes());
                    response.getOutputStream().flush();
                    return null;
                }
                boolean duplicate = false;
                synchronized (lruContainer){
                    LinkedHashMap<String, Object> lru = lruContainer.get(method);
                    if (lru!=null && lru.containsKey(requestId)){
                        duplicate = true;
                    }else{
                        Idempotent finalIdempotent = idempotent;
                        lruContainer.compute(method, (key, oldVal) -> {
                            LinkedHashMap<String, Object> newV = oldVal;
                            //如果oldVal==null 说明lruContainer中没有key为此method的值
                            if (newV == null) {
                                newV = new LinkedHashMap<String, Object>() {
                                    @Override
                                    protected boolean removeEldestEntry(Map.Entry eldest) {
                                        //当LinkedHashMap的大小超过Idempotent中size规定的阈值，移除LinkedHashMap尾部元素
                                        return size() == finalIdempotent.size() + 1;
                                    }
                                };
                            }
                            //将{requestId:OBJECT}放入到LinkedHashMap，requestId重复则替换
                            newV.put(requestId,OBJECT);
                            //给lruContainer设置值{method:newV}
                            return newV;
                        });
                    }
                }
                //duplicate=true,请求不通过
                if (duplicate){
                    response.getOutputStream().write("requestId duplicate".getBytes());
                    response.getOutputStream().flush();
                    return null;
                }
            }
            return super.handleInternal(request, response, handlerMethod);
        }

        private String getRequestId(HttpServletRequest request) {
            return request.getHeader("requestId");
        }
    }
}
