package com.blueeyescloud.baselib.mvvm;

public class ErrorType {
    public static final int NO_NETWORK = 0; //手机无网络
    public static final int NETWORK_TIMEOUT = 1; //网络无返回，超时
    public static final int HTTP_ERROR = 2; //HTTP请求错误码（非2xx和3xx）
    public static final int SERVER_ERROR = 3; //收到服务端returnCode不为1，即业务错误码
}
