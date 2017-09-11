package com.chaotong.yujia.main.entity.CoachBean;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class Trainers {
    String ts_id;
    String ts_name;
    String ts_pic;
    String ts_type;
    String ts_lv;
    String sex;

    public Trainers(String ts_id, String ts_name, String ts_pic, String ts_type, String ts_lv, String sex) {
        this.ts_id = ts_id;
        this.ts_name = ts_name;
        this.ts_pic = ts_pic;
        this.ts_type = ts_type;
        this.ts_lv = ts_lv;
        this.sex = sex;
    }

    public String getTs_id() {
        return ts_id;
    }

    public void setTs_id(String ts_id) {
        this.ts_id = ts_id;
    }

    public String getTs_name() {
        return ts_name;
    }

    public void setTs_name(String ts_name) {
        this.ts_name = ts_name;
    }

    public String getTs_pic() {
        return ts_pic;
    }

    public void setTs_pic(String ts_pic) {
        this.ts_pic = ts_pic;
    }

    public String getTs_type() {
        return ts_type;
    }

    public void setTs_type(String ts_type) {
        this.ts_type = ts_type;
    }

    public String getTs_lv() {
        return ts_lv;
    }

    public void setTs_lv(String ts_lv) {
        this.ts_lv = ts_lv;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
