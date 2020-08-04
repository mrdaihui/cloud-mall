package com.dh.oauth.service.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author daihui
 * @date 2020/7/29 9:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable{

    private String id;

    private String username;

    private String password;

    //用户状态，0-不可用，1-可用
    private Integer status;

    private Set<RoleEntity> roles;

}
