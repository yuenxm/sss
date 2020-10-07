package com.blueeyescloud.baselib.net;

import java.io.Serializable;

public class BaseHttpResponse<T> implements Serializable {
    private int returnCode;

    private String returnMsg;

    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String toString() {
        return "BaseHttpResponse{" +
                "returnCode=" + returnCode +
                ", returnMsg='" + returnMsg + '\'' +
                ", content=" + content +
                '}';
    }
}
