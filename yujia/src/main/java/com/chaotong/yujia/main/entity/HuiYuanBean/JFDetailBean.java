package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class JFDetailBean {
    String code;
    String msg;
    /*收支*/
    List<SZ> sz_list;

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

    public List<SZ> getSz_list() {
        return sz_list;
    }

    public void setSz_list(List<SZ> sz_list) {
        this.sz_list = sz_list;
    }
}
