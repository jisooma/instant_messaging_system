
package com.mzx.wechat.util;

import java.util.UUID;

/**
 * @program XHotel
 * @description 用于提供一个uuid，并去除其中的横线
 */
public class UUIDUtils {
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","");
    }
}
