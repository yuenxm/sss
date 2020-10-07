package com.blueeyescloud.baselib.net;

import java.io.IOException;

public class ServerException extends IOException {
    private int errorCode;

    public ServerException(String message){
        super(message);
    }

    public ServerException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
