package com.dh.common.util;

import java.util.UUID;

/**
 * @author daihui
 * @date 2020/7/17 15:27
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
