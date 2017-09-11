package com.chaotong.yujia.main.property.yujiajia;

import java.util.List;

/**
 * 场馆详情
 */
public class Cgxq {

    private String code;//返回值
    private String msg;//状态信息
    private List<String> pics;//	场馆图片组
    private String ncname;//场馆昵称
    private String pf;//场馆评分
    private String lv;//教练星级
    private String location;//场馆位置
    private String longitude;//	经度
    private String latitude;//纬度
    private String tel;//手机
    private String mobile;//固话
    private String pj;//	场馆被评价数
    private String ifsc;
    private String ifsc_code;
    private String maxclasssum;//该场馆一天最大课程数
    private List<CgxqClassdetails> classdetails;//课程属性

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
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

    public  List<String> getPics() {
        return pics;
    }

    public void setPics( List<String> pics) {
        this.pics = pics;
    }

    public String getNcname() {
        return ncname;
    }

    public void setNcname(String ncname) {
        this.ncname = ncname;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }

    public String getMaxclasssum() {
        return maxclasssum;
    }

    public void setMaxclasssum(String maxclasssum) {
        this.maxclasssum = maxclasssum;
    }

    public List<CgxqClassdetails> getClassdetails() {
        return classdetails;
    }

    public void setClassdetails(List<CgxqClassdetails> classdetails) {
        this.classdetails = classdetails;
    }
}
