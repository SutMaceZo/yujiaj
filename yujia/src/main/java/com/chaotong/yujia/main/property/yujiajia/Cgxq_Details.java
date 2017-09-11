package com.chaotong.yujia.main.property.yujiajia;

import java.io.Serializable;

/**
 * 场馆详情_课程属性
 */
public class Cgxq_Details implements Serializable{

    private String time;//课程时间
    private String classid;//课程id
    private String classname;//课程名字
    private String classstatu;//课程状态
    private String trainername;//教练名
    private String sy_code;
    private String sy_msg;
    private String stadiumname;
    private String maxpeople;
    private String minpeople;
    private String havepeople;
    private String classmoney;

    public String getSy_code() {
        return sy_code;
    }

    public void setSy_code(String sy_code) {
        this.sy_code = sy_code;
    }

    public String getSy_msg() {
        return sy_msg;
    }

    public void setSy_msg(String sy_msg) {
        this.sy_msg = sy_msg;
    }

    public String getStadiumname() {
        return stadiumname;
    }

    public void setStadiumname(String stadiumname) {
        this.stadiumname = stadiumname;
    }

    public String getMaxpeople() {
        return maxpeople;
    }

    public void setMaxpeople(String maxpeople) {
        this.maxpeople = maxpeople;
    }

    public String getMinpeople() {
        return minpeople;
    }

    public void setMinpeople(String minpeople) {
        this.minpeople = minpeople;
    }

    public String getHavepeople() {
        return havepeople;
    }

    public void setHavepeople(String havepeople) {
        this.havepeople = havepeople;
    }

    public String getClassmoney() {
        return classmoney;
    }

    public void setClassmoney(String classmoney) {
        this.classmoney = classmoney;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassstatu() {
        return classstatu;
    }

    public void setClassstatu(String classstatu) {
        this.classstatu = classstatu;
    }

    public String getTrainername() {
        return trainername;
    }

    public void setTrainername(String trainername) {
        this.trainername = trainername;
    }
}
