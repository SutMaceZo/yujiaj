package com.chaotong.yujia.main.property.xiaoxi;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class XiaoXi {

    private String code;
    private String msg;
    private List<PushMsg> push_msg;//消息体

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<PushMsg> getPush_msg() {
        return push_msg;
    }

    public void setPush_msg(List<PushMsg> push_msg) {
        this.push_msg = push_msg;
    }
}
