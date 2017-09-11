package com.chaotong.yujia.main.property.yueke;

import java.util.List;

/**
 * 约教练课程属性
 */
public class Kclb {

    private String code;
    private String msg;
    private List<ClassList> classlist;//课程属性

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

    public List<ClassList> getClasslist() {
        return classlist;
    }

    public void setClasslist(List<ClassList> classlist) {
        this.classlist = classlist;
    }
}
