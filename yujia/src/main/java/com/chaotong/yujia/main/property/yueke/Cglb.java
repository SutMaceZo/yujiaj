package com.chaotong.yujia.main.property.yueke;

import com.chaotong.yujia.main.property.yujiajia.Stadiums;

import java.io.Serializable;
import java.util.List;

/**
 * 场馆列表属性
 */
public class Cglb implements Serializable {
    private String code;
    private String msg;
    private List<Stadiums> Stadiums;
    private List<Stadiums> stadiums;

    public List<com.chaotong.yujia.main.property.yujiajia.Stadiums> getStadiums() {
        return stadiums;
    }

    public void setStadiums(List<com.chaotong.yujia.main.property.yujiajia.Stadiums> stadiums) {
        this.stadiums = stadiums;
    }


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

    public List<Stadiums> getStadiumses() {
        return Stadiums;
    }

    public void setStadiumses(List<Stadiums> Stadiums) {
        this.Stadiums = Stadiums;
    }
}
