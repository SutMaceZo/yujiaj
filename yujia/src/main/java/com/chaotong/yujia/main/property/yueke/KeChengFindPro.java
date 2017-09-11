package com.chaotong.yujia.main.property.yueke;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class KeChengFindPro {

    private String code;
    private String msg;
    private List<String> dates;//日期
    private List<Districts> districts;//商圈
    private List<String> classtypes;//课程类型

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

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Districts> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Districts> districts) {
        this.districts = districts;
    }

    public List<String> getClasstypes() {
        return classtypes;
    }

    public void setClasstypes(List<String> classtypes) {
        this.classtypes = classtypes;
    }
}
