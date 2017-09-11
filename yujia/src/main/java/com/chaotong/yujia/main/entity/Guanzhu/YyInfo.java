package com.chaotong.yujia.main.entity.Guanzhu;

import com.chaotong.yujia.main.property.yujiajia.Classdetails;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class YyInfo implements Serializable{
    String code;
    String msg;
    String stadiumid;
    String maxclasssum;
    List<Classdetails> classdetails;

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

    public String getStadiumid() {
        return stadiumid;
    }

    public void setStadiumid(String stadiumid) {
        this.stadiumid = stadiumid;
    }

    public String getMaxclasssum() {
        return maxclasssum;
    }

    public void setMaxclasssum(String maxclasssum) {
        this.maxclasssum = maxclasssum;
    }

    public List<Classdetails> getClassdetails() {
        return classdetails;
    }

    public void setClassdetails(List<Classdetails> classdetails) {
        this.classdetails = classdetails;
    }
}
