package com.dh.stock.service.core.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

/***
 * @Author limin
 * @Description //TODO 需要解决消息重复收听问题 消息顺序问题
 * @Date 16:31 2018/11/2
 **/

public class ConsumerDemo {
    private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class);

    public static void main(String[] args) throws MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumerGroupName");

        // Specify name server addresses.
        //todo 这个参数要改
        consumer.setNamesrvAddr("106.13.205.140:9876");
        //广播模式 可能产生重复广播
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // Subscribe one more more topics to consume.
        consumer.subscribe("topic_user", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        //noinspection Duplicates
        consumer.registerMessageListener((List<MessageExt> msgs,
                                          ConsumeConcurrentlyContext context) -> {
            msgs.forEach((MessageExt msg) -> {
                try {
                    String tags = msg.getTags();
                    System.out.println(tags);
                    String msgStr = new String(msg.getBody(), "UTF-8");
//                    log.info(String.format("%s Receive New Messages:",Thread.currentThread().getName())+"tag:" + tags + "----------------- msg:" + msgStr);
                    log.info(msgStr);
                    ;
                } catch (UnsupportedEncodingException e) {
                    log.error("msgBody toString error:"+e.getMessage());
                }
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
