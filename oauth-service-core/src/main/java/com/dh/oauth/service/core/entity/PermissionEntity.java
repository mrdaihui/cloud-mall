package com.dh.oauth.service.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author daihui
 * @date 2020/7/29 9:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity implements Serializable{

    private String id;

    private String permissionName;

}
