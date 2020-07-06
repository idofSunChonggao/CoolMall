package com.nmsl.coolmall.core.utils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by NoOne on 2017/10/14 0014.
 */

public class NetworkErrorUtils {

    public static boolean isNetworkError(Throwable e) {
        return e instanceof UnknownHostException || e instanceof SocketException || e instanceof SocketTimeoutException;
    }
}
