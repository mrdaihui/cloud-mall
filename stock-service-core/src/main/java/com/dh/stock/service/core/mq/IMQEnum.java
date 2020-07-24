package com.dh.stock.service.core.mq;

import java.io.Serializable;

/**
 * 通用常量定义接口
 */
public interface IMQEnum extends Serializable {

    /**
     * 获取TOPIC
     *
     * @return
     */
    String getTopic();

    /**
     * 获取TAG
     *
     * @return
     */
    String getTag();

    /**
     * 获取TAG描述信息
     *
     * @return
     */
    String getTagDesc();

}
