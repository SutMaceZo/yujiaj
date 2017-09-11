package com.chaotong.yujia.main.property.yujiaolian;

/**
 * Created by Administrator on 2016/8/17.
 */
public class OrderInfo {

    private String classid; //课程id
    private String classname;//课程名
    private String date;//日期
    private String time;//	时间
    private String stadiumid;//场馆id
    private String stadiumname;//	场馆名
    private String trainerid;//教练id
    private String trainername;//教练名
    private String orderid;//订单id
    private String maxpeople;//课程最大人数
    private String minpeople;//课程最小人数
    private String havepeople;//已报人数
    private String ordermoney;//预定钱数
    private String ifblockbooking;//是否可包场（true,false）
    private String blockbookingmoney;//包场钱数

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
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

    public String getStadiumid() {
        return stadiumid;
    }

    public void setStadiumid(String stadiumid) {
        this.stadiumid = stadiumid;
    }

    public String getStadiumname() {
        return stadiumname;
    }

    public void setStadiumname(String stadiumname) {
        this.stadiumname = stadiumname;
    }

    public String getTrainerid() {
        return trainerid;
    }

    public void setTrainerid(String trainerid) {
        this.trainerid = trainerid;
    }

    public String getTrainername() {
        return trainername;
    }

    public void setTrainername(String trainername) {
        this.trainername = trainername;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMaxpeople() {
        return maxpeople;
    }

    public void setMaxpeople(String maxpeople) {
        this.maxpeople = maxpeople;
    }

    public String getMinpeople() {
        return minpeople;
    }

    public void setMinpeople(String minpeople) {
        this.minpeople = minpeople;
    }

    public String getHavepeople() {
        return havepeople;
    }

    public void setHavepeople(String havepeople) {
        this.havepeople = havepeople;
    }

    public String getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(String ordermoney) {
        this.ordermoney = ordermoney;
    }

    public String getIfblockbooking() {
        return ifblockbooking;
    }

    public void setIfblockbooking(String ifblockbooking) {
        this.ifblockbooking = ifblockbooking;
    }

    public String getBlockbookingmoney() {
        return blockbookingmoney;
    }

    public void setBlockbookingmoney(String blockbookingmoney) {
        this.blockbookingmoney = blockbookingmoney;
    }
}
