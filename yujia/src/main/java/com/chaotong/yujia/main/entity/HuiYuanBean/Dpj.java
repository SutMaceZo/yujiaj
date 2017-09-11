package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class Dpj {
    String code;
    String msg;
    List<DaiPingJiaBean> dpj;

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

    public List<DaiPingJiaBean> getDpj() {
        return dpj;
    }

    public void setDpj(List<DaiPingJiaBean> dpj) {
        this.dpj = dpj;
    }
}
