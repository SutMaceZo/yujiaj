package com.chaotong.yujia.main.entity.JiaoLianBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class Wdkc {
    String code;
    String msg;
    List<WdkcBean> lwdkc;

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

    public List<WdkcBean> getLwdkc() {
        return lwdkc;
    }

    public void setLwdkc(List<WdkcBean> lwdkc) {
        this.lwdkc = lwdkc;
    }
}
