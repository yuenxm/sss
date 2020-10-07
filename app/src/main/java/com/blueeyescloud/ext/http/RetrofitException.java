package com.blueeyescloud.ext.http;

import com.blueeyescloud.baselib.mvvm.ErrorInfo;
import com.blueeyescloud.baselib.mvvm.ErrorType;
import com.blueeyescloud.baselib.net.ServerException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class RetrofitException {

    public static ErrorInfo wrapperToErrorInfo(Throwable throwable){
        ErrorInfo errorInfo = new ErrorInfo();
        if(throwable instanceof ServerException){
            //服务端问题
            ServerException ex = (ServerException)throwable;
            errorInfo.setErrorType(ErrorType.SERVER_ERROR);
            errorInfo.setErrorCode(ex.getErrorCode());
            errorInfo.setMessage(ex.getMessage());
        }else if (throwable instanceof ConnectException
                || throwable instanceof SocketTimeoutException
                || throwable instanceof UnknownHostException){
            //无网络
            errorInfo.setErrorType(ErrorType.NO_NETWORK);
            errorInfo.setErrorCode(ErrorType.NO_NETWORK);
            errorInfo.setMessage(throwable.getMessage());
        }else {
            // 其他也都归类到服务端问题 (譬如http协议状态码出错）
            errorInfo.setErrorType(ErrorType.SERVER_ERROR);
            errorInfo.setErrorCode(ErrorType.SERVER_ERROR);
            errorInfo.setMessage(throwable.getMessage());
        }
        return errorInfo;
    }
}
