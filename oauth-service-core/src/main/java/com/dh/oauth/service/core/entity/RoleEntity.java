package com.dh.oauth.service.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author daihui
 * @date 2020/7/29 9:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable{

    private String id;

    private String roleName;

    private Set<PermissionEntity> permissions;

}
