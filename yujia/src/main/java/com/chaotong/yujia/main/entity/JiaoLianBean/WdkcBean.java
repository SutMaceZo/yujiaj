package com.chaotong.yujia.main.entity.JiaoLianBean;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class WdkcBean {
    String classid;
    String classname;
    String trainerid;
    String trainername;
    String stadiumid;
    String stadiumname;
    String date;
    String time;
    String pic;

    String ct_max;
    String ct_min;
    String ct_num;

    public WdkcBean(String classid, String classname, String trainerid, String trainername, String stadiumid, String stadiumname, String date, String time, String pic,String ct_max,String ct_min,String ct_num) {
        this.classid = classid;
        this.classname = classname;
        this.trainerid = trainerid;
        this.trainername = trainername;
        this.stadiumid = stadiumid;
        this.stadiumname = stadiumname;
        this.date = date;
        this.time = time;
        this.pic = pic;
        this.ct_max=ct_max;
        this.ct_min=ct_min;
        this.ct_num=ct_num;
    }


    public String getCt_max() {
        return ct_max;
    }

    public void setCt_max(String ct_max) {
        this.ct_max = ct_max;
    }

    public String getCt_min() {
        return ct_min;
    }

    public void setCt_min(String ct_min) {
        this.ct_min = ct_min;
    }

    public String getCt_num() {
        return ct_num;
    }

    public void setCt_num(String ct_num) {
        this.ct_num = ct_num;
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
