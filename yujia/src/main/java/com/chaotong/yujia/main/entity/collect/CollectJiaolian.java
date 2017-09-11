package com.chaotong.yujia.main.entity.collect;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class CollectJiaolian {
    String trainerid;
    String trainername;
    String type;
    String tel;
    String lv;
    String pic;

    public CollectJiaolian(String trainerid,String trainername, String type, String tel, String lv, String pic) {
        this.trainerid=trainerid;
        this.trainername = trainername;
        this.type = type;
        this.tel = tel;
        this.lv = lv;
        this.pic = pic;

    }

    public String getTrainerid() {
        return trainerid;
    }

    public void setTrainerid(String trainerid) {
        this.trainerid = trainerid;
    }

    public CollectJiaolian() {
    }

    public String getTrainername() {
        return trainername;
    }

    public void setTrainername(String trainername) {
        this.trainername = trainername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
