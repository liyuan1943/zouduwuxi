package com.aorise.utils;

import java.util.UUID;

/**
 * Created by cat on 2019/6/23.
 */
public class UUIDBits {

    public static void main(String[] args) {
        System.out.println(getUUID(20));
    }

    /**
     *32位默认长度的uuid
     * (获取32位uuid)
     *
     * @return
     */
    public static  String getUUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     *
     * (获取指定长度uuid)
     *
     * @return
     */
    public static  String getUUID(int len) {
        if (0 >= len) {
            return null;
        }
        String uuid = getUUID();
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }

        return str.toString();
    }
}
