package com.dh.stock.service.core.controller;

import com.dh.common.models.R;
import com.dh.common.util.UUIDUtils;
import com.dh.stock.service.core.entity.UserEntity;
import com.dh.stock.service.core.service.UserService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daihui
 * @date 2020/7/23 14:04
 */
@RestController
public class RocketmqController {

    @Autowired
    private UserService userService;

    @GetMapping("tx")
    public R mq(){
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(18);
        userEntity.setName("daihui");
        userService.sendTxMessage(userEntity);
        return R.ok();
    }

}
