package com.dh.stock.service.core.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dh.common.models.R;
import com.dh.order.service.api.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daihui
 * @date 2020/7/17 17:18
 */
@RestController
public class StockController {

    @Reference(group = "order")
    private OrderService orderService;

    //测试dubbo服务调用
    @GetMapping("stock")
    public R reduce(){
        orderService.create("create");
        return R.ok();
    }

}
