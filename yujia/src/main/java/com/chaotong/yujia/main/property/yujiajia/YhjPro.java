package com.chaotong.yujia.main.property.yujiajia;

/**
 * Created by Administrator on 2016/8/18.
 */
public class YhjPro {
//优惠卷属性
    private String yhj_id;//优惠卷ID
    private String yhj_pic;//图片路径
    private String yhj_money;//金额
    private String yhj_num;//数量
    private String yhj_name;//已被领取的数量
    private String yhj_startdate;//开抢日期
    private String yhj_starttime;//开抢时间
    private String yhj_enddate;//结束日期
    private String yhj_endtime;//结束时间
    private String valid_time;//有效期
    private String yhj_sm;
    private String sp_id;//场馆ID
    private String sp_name;//优惠卷所属场馆名称

    private String yhjlist_code;
    private String yhjlist_msg;//是否领取
    private String dh_code;
    private String jf;

    private String ct_blockbook;//包场金额
    private String ct_money;//现金支付金额

    public String getCt_blockbook() {
        return ct_blockbook;
    }

    public void setCt_blockbook(String ct_blockbook) {
        this.ct_blockbook = ct_blockbook;
    }

    public String getCt_money() {
        return ct_money;
    }

    public void setCt_money(String ct_money) {
        this.ct_money = ct_money;
    }

    public String getYhj_sm() {
        return yhj_sm;
    }

    public void setYhj_sm(String yhj_sm) {
        this.yhj_sm = yhj_sm;
    }

    public String getYhjlist_code() {
        return yhjlist_code;
    }

    public void setYhjlist_code(String yhjlist_code) {
        this.yhjlist_code = yhjlist_code;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }




    public String getDh_code() {
        return dh_code;
    }

    public void setDh_code(String dh_code) {
        this.dh_code = dh_code;
    }



    public String getYhjlist_msg() {
        return yhjlist_msg;
    }

    public void setYhjlist_msg(String yhjlist_msg) {
        this.yhjlist_msg = yhjlist_msg;
    }


    public String getYhj_id() {
        return yhj_id;
    }

    public void setYhj_id(String yhj_id) {
        this.yhj_id = yhj_id;
    }

    public String getYhj_pic() {
        return yhj_pic;
    }

    public void setYhj_pic(String yhj_pic) {
        this.yhj_pic = yhj_pic;
    }

    public String getYhj_money() {
        return yhj_money;
    }

    public void setYhj_money(String yhj_money) {
        this.yhj_money = yhj_money;
    }

    public String getYhj_num() {
        return yhj_num;
    }

    public void setYhj_num(String yhj_num) {
        this.yhj_num = yhj_num;
    }

    public String getYhj_name() {
        return yhj_name;
    }

    public void setYhj_name(String yhj_name) {
        this.yhj_name = yhj_name;
    }

    public String getYhj_startdate() {
        return yhj_startdate;
    }

    public void setYhj_startdate(String yhj_startdate) {
        this.yhj_startdate = yhj_startdate;
    }

    public String getYhj_starttime() {
        return yhj_starttime;
    }

    public void setYhj_starttime(String yhj_starttime) {
        this.yhj_starttime = yhj_starttime;
    }

    public String getYhj_enddate() {
        return yhj_enddate;
    }

    public void setYhj_enddate(String yhj_enddate) {
        this.yhj_enddate = yhj_enddate;
    }

    public String getYhj_endtime() {
        return yhj_endtime;
    }

    public void setYhj_endtime(String yhj_endtime) {
        this.yhj_endtime = yhj_endtime;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getSp_name() {
        return sp_name;
    }

    public void setSp_name(String sp_name) {
        this.sp_name = sp_name;
    }
}
