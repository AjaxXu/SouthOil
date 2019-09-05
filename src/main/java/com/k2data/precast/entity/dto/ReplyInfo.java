package com.k2data.precast.entity.dto;

public class ReplyInfo {
    private boolean success;
    private Object data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReplyInfo(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ReplyInfo(boolean success, Object data) {
        this.success = success;
        this.data = data;
        this.message = "";
    }

    public ReplyInfo(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    public ReplyInfo(boolean success) {
        this.success = success;
    }
}
