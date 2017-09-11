package com.chaotong.yujia.main.entity.HuiYuanBean;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class YuYueBean {
    String classid;
    String classname;
    String trainerid;
    String trainername;
    String stadiumid;
    String stadiumname;
    String date;
    String time;
    String pic;
    String add;
    String tel;
    String mobile;
    String orderid;
    String kc_code;
    String kc_msg;
    String week;//星期几，后来修改

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getKc_code() {
        return kc_code;
    }

    public void setKc_code(String kc_code) {
        this.kc_code = kc_code;
    }

    public String getKc_msg() {
        return kc_msg;
    }

    public void setKc_msg(String kc_msg) {
        this.kc_msg = kc_msg;
    }

    public YuYueBean() {
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public YuYueBean(String classid, String classname, String trainerid, String trainername,
                     String stadiumid, String stadiumname, String date, String time, String pic,
                     String add, String tel, String mobile, String orderid, String kc_code, String kc_msg) {
        this.classid = classid;
        this.classname = classname;
        this.trainerid = trainerid;
        this.trainername = trainername;
        this.stadiumid = stadiumid;
        this.stadiumname = stadiumname;
        this.date = date;
        this.time = time;
        this.pic = pic;
        this.add = add;
        this.tel = tel;
        this.mobile = mobile;
        this.orderid = orderid;
        this.kc_code = kc_code;
        this.kc_msg = kc_msg;
    }

    public String getPic() {

        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getTrainerid() {
        return trainerid;
    }

    public void setTrainerid(String trainerid) {
        this.trainerid = trainerid;
    }

    public String getTrainername() {
        return trainername;
    }

    public void setTrainername(String trainername) {
        this.trainername = trainername;
    }

    public String getStadiumid() {
        return stadiumid;
    }

    public void setStadiumid(String stadiumid) {
        this.stadiumid = stadiumid;
    }

    public String getStadiumname() {
        return stadiumname;
    }

    public void setStadiumname(String stadiumname) {
        this.stadiumname = stadiumname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
