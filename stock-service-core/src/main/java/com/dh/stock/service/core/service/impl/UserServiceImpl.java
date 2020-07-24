package com.dh.stock.service.core.service.impl;

import com.dh.common.util.UUIDUtils;
import com.dh.stock.service.core.entity.UserEntity;
import com.dh.stock.service.core.mq.TopicTagEnums;
import com.dh.stock.service.core.service.UserService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author daihui
 * @date 2020/7/24 9:08
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送创建用户的事务消息
     * @param userEntity
     */
    @Override
    public void sendTxMessage(UserEntity userEntity) {
        //设置keys,flag等。。。
        Message message = MessageBuilder.withPayload(userEntity)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, UUIDUtils.getUUID())
                .setHeader(RocketMQHeaders.KEYS,"key_user")
                .setHeader(RocketMQHeaders.FLAG,"flag_user")
                .build();
        rocketMQTemplate.sendMessageInTransaction(TopicTagEnums.Notice.ADD_USER.getTopic()+":"+TopicTagEnums.Notice.ADD_USER.getTag(),message,null);
    }

    /**
     * 本地真正执行入库操作的逻辑，由LocalTransactionListener:executeLocalTransaction来调用
     * @param userEntity
     * @return
     */
    @Override
    @Transactional
    public boolean createUserTx(UserEntity userEntity,String transactionId) {
        //根据transactionId控制本地事务是否成功，为后续回查做准备
        System.out.println("txNo:"+transactionId);
        //模拟本地入库操作
        System.out.println("producer入库----"+userEntity);
        return true;
    }
}
