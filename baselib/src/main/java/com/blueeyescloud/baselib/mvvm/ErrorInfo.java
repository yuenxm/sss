package com.blueeyescloud.baselib.mvvm;

/**
 * 错误信息（用于界面错误状态到信息记录）
 */
public class ErrorInfo {
    private boolean hasError; //是否显示
    private int errorType; //错误类别
    private int errorCode; //具体错误码
    private String message; //错误内容

    public ErrorInfo(){
        this.hasError = true;
    }
    public ErrorInfo(String message){
        this.errorCode = ErrorType.SERVER_ERROR;
        this.message = message;
        this.hasError = true;
    }

    public ErrorInfo(boolean hasError){
        this.hasError = hasError;
    }

    public ErrorInfo(int errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
        this.hasError = true;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
