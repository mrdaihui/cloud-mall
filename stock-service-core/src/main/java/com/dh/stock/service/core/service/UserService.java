package com.dh.stock.service.core.service;

import com.dh.stock.service.core.entity.UserEntity;

public interface UserService {

    void sendTxMessage(UserEntity userEntity);

    boolean createUserTx(UserEntity userEntity,String transactionId);

}
