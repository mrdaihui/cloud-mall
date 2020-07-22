package com.dh.stock.service.core.controller;

import com.dh.common.models.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daihui
 * @date 2020/7/20 10:15
 */
@RestController
@RefreshScope
public class StockConfigController {

    @Value("${stock.title}")
    private String stockTitle;

//    @Value("${cloud.common.title}")
//    private String commonTtile;

    //测试nacos做配置中心
    @GetMapping("stock/title")
    public R title(){
        return R.ok(stockTitle);
    }

//    @GetMapping("stock/common/title")
//    public R common(){
//        return R.ok(commonTtile);
//    }


}
