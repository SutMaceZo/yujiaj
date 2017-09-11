package com.chaotong.yujia.main.property.yujiajia;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class YujiajiaBean {

    private String code;//状态码
    private String message;//提示信息
    private Data data;//消息体

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public  Data getData() {
        return data;
    }

    public void setData( Data data) {
        this.data = data;
    }
}
