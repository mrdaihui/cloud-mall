package com.dh.order.service.core.controller;

import com.dh.order.service.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daihui
 * @date 2020/7/20 9:03
 */
@RestController
public class OrderServiceController {

    @Autowired(required = false)
    private OrderService orderService;

    @GetMapping("order")
    public String order(){
        orderService.create("oooo");
        return "ok";
    }

}
