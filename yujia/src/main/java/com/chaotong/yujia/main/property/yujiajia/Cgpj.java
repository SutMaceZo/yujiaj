package com.chaotong.yujia.main.property.yujiajia;

import java.util.List;

/**
 * 场馆评价
 */
public class Cgpj {

    private String code;
    private String msg;
    private List<Stadiumspj> stadiumspj;//评价属性

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

    public List<Stadiumspj> getStadiumspj() {
        return stadiumspj;
    }

    public void setStadiumspj(List<Stadiumspj> stadiumspj) {
        this.stadiumspj = stadiumspj;
    }
}
