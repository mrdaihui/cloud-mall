package com.dh.common.context;

import java.util.UUID;

/**
 * 请求上下文，给接口统一返回requestId
 */
public class RequestContext {

    /**
     * 请求ID
     */
    private String requestId;


    /**
     * 本地现成变量，存放线程信息
     */
    private static ThreadLocal<RequestContext> local = ThreadLocal.withInitial(() -> new RequestContext(UUID.randomUUID().toString()));




    /**
     * get RequestContext.
     *
     * @return context
     */
    public static RequestContext getContext() {

        return local.get();
    }



    public String getRequestId() {
        return requestId;
    }


    public String rebuildRequestId() {
        requestId = UUID.randomUUID().toString();
        return requestId;
    }


    private RequestContext(String requestId) {
        this.requestId = requestId;
    }
}



