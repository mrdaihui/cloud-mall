package com.dh.stock.service.core.mq;

/**
 * MQ 消息 键
 */
public class TopicTagEnums {


    /**
     * 消息通知
     */
    public enum Notice implements IMQEnum {

        ADD_USER("topic_user", "tag_user", "测试事务");

        final String topic;
        final String tag;
        final String tagDesc;

        Notice(String topic, String tag, String tagDesc) {
            this.topic = topic;
            this.tag = tag;
            this.tagDesc = tagDesc;
        }


        @Override
        public final String getTopic() {
            return topic;
        }

        @Override
        public final String getTag() {
            return tag;
        }

        @Override
        public final String getTagDesc() {
            return tagDesc;
        }
    }

}