package com.dh.stock.service.core.controller;

import com.dh.common.exception.RException;
import com.dh.common.models.R;
import com.dh.common.util.UUIDUtils;
import com.dh.stock.service.core.lock.LettuceLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author daihui
 * @date 2020/7/22 11:10
 */
@RestController
public class LockController {

    public int count = 10;

    @Autowired
    private LettuceLock lettuceLock;

    //测试redis分布式锁
    @GetMapping("lock")
    public R lockTest(){
        String reqId = UUIDUtils.getUUID();
        Boolean lock = lettuceLock.tryLock("lockKey",reqId,10, TimeUnit.SECONDS);
        if(lock&&count>0){
            try {
                Thread.sleep(10);
                System.out.println(--count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lettuceLock.releaseLock("lockKey",reqId);
        return R.ok();
    }

}
