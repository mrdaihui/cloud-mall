package com.dh.oauth.service.core.service;

import com.dh.oauth.service.core.entity.UserEntity;

/**
 * @author daihui
 * @date 2020/7/29 10:00
 */
public interface UserService {

    UserEntity findUserByName(String name);

}
