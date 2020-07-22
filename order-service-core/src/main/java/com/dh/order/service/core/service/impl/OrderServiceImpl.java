package com.dh.order.service.core.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dh.order.service.api.dto.OrderResponse;
import com.dh.order.service.api.service.OrderService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author daihui
 * @date 2020/7/17 15:37
 */
@Service(group = "order")
public class OrderServiceImpl implements OrderService{


    @Override
    @SentinelResource("order-provide")
    public OrderResponse create(String testInfo) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId("12345");
        System.out.println(testInfo);
        return orderResponse;
    }
}
