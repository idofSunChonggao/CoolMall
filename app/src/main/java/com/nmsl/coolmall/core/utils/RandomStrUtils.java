package com.nmsl.coolmall.core.utils;

import java.util.Random;

/**
 * @description:
 * @author: NoOne
 * @date: 2019-04-26 13:48
 */
public class RandomStrUtils {

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
