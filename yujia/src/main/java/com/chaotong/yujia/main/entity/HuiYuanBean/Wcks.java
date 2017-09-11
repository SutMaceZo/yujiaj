package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class Wcks {
    String code;
    String msg;
    List<WcksBean> Wcks;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WcksBean> getWcks() {
        return Wcks;
    }

    public void setWcks(List<WcksBean> wcks) {
        Wcks = wcks;
    }
}
