package com.dh.stock.service.core.mq.comsumer;

import com.dh.stock.service.core.entity.UserEntity;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 顺序消息消费
 * @author daihui
 * @date 2020/7/24 9:01
 */
@RocketMQMessageListener(consumerGroup = "consumer_group2",topic = "order_topic",selectorExpression = "order_tag",consumeMode = ConsumeMode.ORDERLY)
@Component
public class OrderConsumerListener implements RocketMQListener<String>{

    @Override
    public void onMessage(String message) {
        System.out.println("----"+message);
    }
}
