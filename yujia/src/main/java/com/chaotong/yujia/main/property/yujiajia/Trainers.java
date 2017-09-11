package com.chaotong.yujia.main.property.yujiajia;

import java.io.Serializable;

/**
 * (教练信息)
 */
public class Trainers implements Serializable {

    private String ts_id;//	教练id
    private String ts_name;//	教练名
    private String ts_type;//	教练类型
    private String ts_lv;//	教练星级
    private String ts_pic;//	教练图片路径
    private String ts_sex;//教练性别
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTs_sex() {
        return ts_sex;
    }

    public void setTs_sex(String ts_sex) {
        this.ts_sex = ts_sex;
    }

    public String getTs_lv() {
        return ts_lv;
    }

    public void setTs_lv(String ts_lv) {
        this.ts_lv = ts_lv;
    }

    public String getTs_pic() {
        return ts_pic;
    }

    public void setTs_pic(String ts_pic) {
        this.ts_pic = ts_pic;
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

    public String getSp_lv() {
        return ts_lv;
    }

    public void setSp_lv(String sp_lv) {
        this.ts_lv = sp_lv;
    }

    public String getTs_type() {
        return ts_type;
    }

    public void setTs_type(String ts_type) {
        this.ts_type = ts_type;
    }

    public String getSp_pic() {
        return ts_pic;
    }

    public void setSp_pic(String sp_pic) {
        this.ts_pic = sp_pic;
    }
}
