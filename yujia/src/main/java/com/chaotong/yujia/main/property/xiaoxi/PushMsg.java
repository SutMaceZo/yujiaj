package com.chaotong.yujia.main.property.xiaoxi;

/**
 * Created by Administrator on 2016/9/18.
 */
public class PushMsg {

    private String msg;//内容
    private String flag;//0：未读，1：已读
    private String date;//接收到该消息的时间

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
