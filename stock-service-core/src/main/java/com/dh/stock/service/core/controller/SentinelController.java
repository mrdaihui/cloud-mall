package com.dh.stock.service.core.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dh.common.models.R;
import com.dh.stock.service.core.handler.SentinelExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author daihui
 * @date 2020/7/20 15:48
 */
@RestController
public class SentinelController {

    //测试sentinel限流
    @GetMapping("sentinel")
    @SentinelResource(value = "sentinel", blockHandlerClass = SentinelExceptionHandler.class, blockHandler = "sentinelException")
    public R sentinel() {
//        SphU.
        try {
            TimeUnit.MILLISECONDS.sleep(20);
            System.out.println("get----");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.ok();
    }


    @GetMapping("flow")
    @SentinelResource(value = "flow-nacos")
    public R sentinel2() {
//        SphU.
        try {
            TimeUnit.MILLISECONDS.sleep(20);
            System.out.println("pass----");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    //测试sentinel降级
    @GetMapping("reduce")
    @SentinelResource(value = "reduce", fallback = "reduceException")
    public R reduce() {
        int a = (int)(Math.random()*10+1);
        if(a<=5){
            System.out.println(a);
            throw new RuntimeException("小于等于5了，抛出异常");
        }
        System.out.println("正常"+a);
        return R.ok();
    }

    //限流处理方法 blockHandler
//    public R sentinelException(BlockException ex){
////        ex.printStackTrace();
//        System.out.println("被限流了");
//        return R.error("被限流了");
//    }


    public R reduceException() {
//        ex.printStackTrace();
        System.out.println("被降级了");
        return R.error("被降级了");
    }

}
