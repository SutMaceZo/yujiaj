package com.chaotong.yujia.main.property.yujiajia;

import java.io.Serializable;
import java.util.List;

/**
 * 主页信息
 */
public class Data implements Serializable{

    private List<ActiveComponents> ActiveComponents;//组件栏
    private List<AdvertisingImages> AdvertisingImages;//滚动广告图片
    private List<Msg> Msg;//轮播消息栏
    private List<Stadiums> Stadiums;//场馆栏
    private List<Trainers> Trainers;//风云排行榜栏.



    public List<ActiveComponents> getActiveComponents() {
        return ActiveComponents;
    }

    public void setActiveComponents(List<ActiveComponents> ActiveComponents) {
        this.ActiveComponents = ActiveComponents;
    }

    public List<AdvertisingImages> getAdvertisingImages() {
        return AdvertisingImages;
    }

    public void setAdvertisingImages(List<AdvertisingImages> AdvertisingImages) {
        this.AdvertisingImages = AdvertisingImages;
    }

    public List<Msg> getMsg() {
        return Msg;
    }

    public void setMsg(List<Msg> msg) {
        this.Msg = msg;
    }

    public List<Stadiums> getStadiums() {
        return Stadiums;
    }

    public void setStadiums(List<Stadiums> stadiums) {
        this.Stadiums = stadiums;
    }

    public List<Trainers> getTrainers() {
        return Trainers;
    }

    public void setTrainers(List<Trainers> trainers) {
        this.Trainers = trainers;
    }
}
