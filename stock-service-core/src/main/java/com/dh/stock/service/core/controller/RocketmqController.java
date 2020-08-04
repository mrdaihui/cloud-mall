package com.dh.stock.service.core.controller;

import com.dh.common.models.R;
import com.dh.common.util.UUIDUtils;
import com.dh.stock.service.core.entity.UserEntity;
import com.dh.stock.service.core.service.UserService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
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

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("tx")
    public R mq() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(18);
        userEntity.setName("daihui");
        userService.sendTxMessage(userEntity);
        return R.ok();
    }

    /**
     * 发送顺序消息
     */
    @GetMapping("order/msg")
    public R orderMq() {
        String msg = "msg:";
        //自定义队列选择。默认使用hash来选择队列：SelectMessageQueueByHash
//        rocketMQTemplate.setMessageQueueSelector((list, message, o) -> {
//            return null;
//        });
        for (int i = 0; i < 20; i++) {
            rocketMQTemplate.asyncSendOrderly("order_topic:order_tag", msg + i, "orderKey", new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.getMessageQueue().getQueueId());
                    System.out.println(sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
        }
        return R.ok();
    }

//    延时消息messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
}
