package com.chaotong.yujia.main.entity.HuiYuanBean;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class WcksBean {
    String date;
    String time;
    String classname;
    String stadium;
    String trainer;
    String classstatu;
    String trainerpic;
    String ct_max;
    String ct_min;
    String ct_finish;
    String ct_num;

    String pj_fs;
    String pj_num;
    String week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getPj_fs() {
        return pj_fs;
    }

    public void setPj_fs(String pj_fs) {
        this.pj_fs = pj_fs;
    }

    public String getPj_num() {
        return pj_num;
    }

    public void setPj_num(String pj_num) {
        this.pj_num = pj_num;
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

    public String getCt_finish() {
        return ct_finish;
    }

    public void setCt_finish(String ct_finish) {
        this.ct_finish = ct_finish;
    }

    public String getCt_num() {
        return ct_num;
    }

    public void setCt_num(String ct_num) {
        this.ct_num = ct_num;
    }

    public String getClassstatu() {
        return classstatu;
    }

    public void setClassstatu(String classstatu) {
        this.classstatu = classstatu;
    }

    public String getTrainerpic() {
        return trainerpic;
    }

    public void setTrainerpic(String trainerpic) {
        this.trainerpic = trainerpic;
    }

    public WcksBean(String date, String time, String classname, String stadium, String trainer) {
        this.date = date;
        this.time = time;
        this.classname = classname;
        this.stadium = stadium;
        this.trainer = trainer;
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

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
}
