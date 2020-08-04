package com.dh.stock.service.core.mq.comsumer;

import com.dh.stock.service.core.entity.UserEntity;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author daihui
 * @date 2020/7/24 9:01
 */
@RocketMQMessageListener(consumerGroup = "consumer_group",topic = "topic_user",selectorExpression = "tag_user")
@Component
public class UserConsumerListener implements RocketMQListener<UserEntity>{

    @Override
    public void onMessage(UserEntity message) {
        //模拟本地入库操作
        System.out.println("consumer入库----"+message);
    }

//    如果要使用PUSH的方式消费实现RocketMQPushConsumerLifecycleListener
//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer) {
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
//        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
//
//    }
}
