package com.lee.common;

import java.io.Serializable;

public class Message implements Serializable {
    private boolean success;
    private String message;
    private String url;
    private Object data;
    private int code;

    public Message(boolean success, String message, String url, Object data, int code) {
        this.success = success;
        this.message = message;
        this.url = url;
        this.data = data;
        this.code = code;
    }
    public static Message success(String msg){
        return new Message(true,msg,"",null,200);
    }
    public static Message success(String msg,Object data){
        return new Message(true,msg,"",data,200);
    }
    public static Message success(String msg,Object data,String url){
        return new Message(true,msg,url,data,200);
    }
    public static Message fail(String msg){
        return new Message(false,msg,"",null,400);
    }
    public static Message fail(String msg,Object data){
        return new Message(false,msg,"",data,200);
    }
    public static Message fail(String msg,Object data,String url){
        return new Message(false,msg,url,data,200);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
