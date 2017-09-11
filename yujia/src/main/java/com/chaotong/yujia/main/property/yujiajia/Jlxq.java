package com.chaotong.yujia.main.property.yujiajia;

import java.util.List;

/**
 * 教练详情
 */
public class Jlxq {

    private String code;//返回码
    private String msg;//返回结果信息
    private List<String> pics;//教练图片组
    private String ncname;//教练昵称
    private String lv;//教练星级
    private String jf;//教练积分
    private String type;//教练类型
    private String honor;//教练荣誉
    private String Pj;//教练被评价数
    private String  ifsc_code;
    private String ifsc;

    private String maxclasssum;//一天最大课程数
    private List<Classdetails> classdetails;//每日的课程

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

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

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getNcname() {
        return ncname;
    }

    public void setNcname(String ncname) {
        this.ncname = ncname;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getPj() {
        return Pj;
    }

    public void setPj(String pj) {
        Pj = pj;
    }

    public String getMaxclasssum() {
        return maxclasssum;
    }

    public void setMaxclasssum(String maxclasssum) {
        this.maxclasssum = maxclasssum;
    }

   /* public List<Classdetails> getClassdetailses() {
        return classdetails;
    }

    public void setClassdetailses(List<Classdetails> classdetailses) {
        this.classdetails = classdetailses;
    }*/

    public List<Classdetails> getClassdetails() {
        return classdetails;
    }

    public void setClassdetails(List<Classdetails> classdetails) {
        this.classdetails = classdetails;
    }
}
