package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Hg_list implements Serializable{
    String hg_id;
    String l_id;
    String hg_type;
    String hg_type_sm;

    YHJ yhj;
    String hg_jf;
    String hg_date;

    public String getHg_type_sm() {
        return hg_type_sm;
    }

    public void setHg_type_sm(String hg_type_sm) {
        this.hg_type_sm = hg_type_sm;
    }

    public YHJ getYhj() {
        return yhj;
    }

    public void setYhj(YHJ yhj) {
        this.yhj = yhj;
    }

    public String getHg_date() {
        return hg_date;
    }

    public void setHg_date(String hg_date) {
        this.hg_date = hg_date;
    }

    public String getHg_jf() {
        return hg_jf;
    }

    public void setHg_jf(String hg_jf) {
        this.hg_jf = hg_jf;
    }


    public String getHg_type() {
        return hg_type;
    }

    public void setHg_type(String hg_type) {
        this.hg_type = hg_type;
    }

    public String getL_id() {
        return l_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    public String getHg_id() {
        return hg_id;
    }

    public void setHg_id(String hg_id) {
        this.hg_id = hg_id;
    }
}
