package com.chaotong.yujia.main.property.yujiajia;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CgxqClassdetails implements Serializable{

    private String date;//日期
    private List<Detail> detail;//属性

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }
}
