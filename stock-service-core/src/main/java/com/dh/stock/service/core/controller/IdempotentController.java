package com.dh.stock.service.core.controller;

import com.dh.common.models.R;
import com.dh.stock.service.core.config.Idempotent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daihui
 * @date 2020/7/24 14:18
 */
@RestController
@Idempotent
public class IdempotentController {


    @PostMapping("idempotent")
//    @Idempotent
    public R idempotent(){
        System.out.println("--");
        return R.ok();
    }

}
