package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class HanGouBean {
    String code;
    String msg;
    List<Hg_list> hg_list;

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

    public List<Hg_list> getHg_list() {
        return hg_list;
    }

    public void setHg_list(List<Hg_list> hg_list) {
        this.hg_list = hg_list;
    }
}
