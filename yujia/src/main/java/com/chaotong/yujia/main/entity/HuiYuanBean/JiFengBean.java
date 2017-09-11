package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class JiFengBean {
    String code;
    String msg;
    Wdjf wdjf;
    List<YuHuiJuanBean> yhjs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<YuHuiJuanBean> getYhjs() {
        return yhjs;
    }

    public void setYhjs(List<YuHuiJuanBean> yhjs) {
        this.yhjs = yhjs;
    }

    public Wdjf getWdjf() {
        return wdjf;
    }

    public void setWdjf(Wdjf wdjf) {
        this.wdjf = wdjf;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
