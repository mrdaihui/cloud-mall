package com.dh.stock.service.core.mq;

import com.alibaba.fastjson.JSON;
import com.dh.stock.service.core.entity.UserEntity;
import com.dh.stock.service.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * @author daihui
 * @date 2020/7/1 16:32
 */
@Slf4j
@RocketMQTransactionListener
public class LocalTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private UserService userService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //接受业务发送的消息
        //执行本地事务操作
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        String topic = (String) message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TOPIC);
        String tags = (String) message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS);
        log.info("executeLocalTransaction-----");
        boolean result =false;
        if(TopicTagEnums.Notice.ADD_USER.getTopic().equals(topic)&&TopicTagEnums.Notice.ADD_USER.getTag().equals(tags)){
            String str = new String((byte[]) message.getPayload());
            UserEntity userEntity = JSON.parseObject(str,UserEntity.class);
            System.out.println(userEntity);
            result = userService.createUserTx(userEntity,transId);
        }
        if (result) {
            log.info("commit");
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            log.info("rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("回查");
        //根据事务号查询本地事务是否成功
        if (false) {
            log.info("rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        log.info("commit");
        return RocketMQLocalTransactionState.COMMIT;
    }
}
