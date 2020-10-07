package com.blueeyescloud.ext.devicemaster.entity;

public class ResultInfo {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_DATA = 2;

    private int status;
    private String message;

    public ResultInfo(){

    }

    public ResultInfo(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
