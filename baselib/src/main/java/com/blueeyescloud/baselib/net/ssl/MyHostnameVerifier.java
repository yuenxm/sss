package com.blueeyescloud.baselib.net.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 接受所有hostName
 * Created by caill on 2016/9/6.
 */
public class MyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}