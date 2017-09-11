package com.chaotong.yujia.main.property.yujiajia;

import java.io.Serializable;

/**
 * 教练详情_课程属性
 */
public class Detail implements Serializable {

    private String time;//时间
    private String classid;//课程id
    private String classname;//课程名称
    private String classstatu;//课程状态
    private String stadiumname;//场馆名
    private String maxpeople;//最大人数
    private String minpeople;//最小人数
    private String havepeople;//拥有人数
    private String classstatu_code;//
    private String sy_code;//
    private String sy_msg;//是否是私约课
    private String classmoney;//包场全额
    private String classpic;
    private String trainername;

    public String getTrainername() {
        return trainername;
    }

    public void setTrainername(String trainername) {
        this.trainername = trainername;
    }

    public String getClasspic() {
        return classpic;
    }

    public void setClasspic(String classpic) {
        this.classpic = classpic;
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

    public String getClassstatu_code() {
        return classstatu_code;
    }

    public void setClassstatu_code(String classstatu_code) {
        this.classstatu_code = classstatu_code;
    }

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

    public String getClassmoney() {
        return classmoney;
    }

    public void setClassmoney(String classmoney) {
        this.classmoney = classmoney;
    }
}
