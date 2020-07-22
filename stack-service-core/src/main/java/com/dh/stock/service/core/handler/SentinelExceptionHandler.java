package com.dh.stock.service.core.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.dh.common.models.R;

/**
 * @author daihui
 * @date 2020/7/21 15:58
 */
public class SentinelExceptionHandler {

    public static R sentinelException(BlockException ex){
//        ex.printStackTrace();
        System.out.println("被限流了2");
        return R.error("被限流了");
    }

}
