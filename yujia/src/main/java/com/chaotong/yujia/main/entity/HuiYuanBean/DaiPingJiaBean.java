package com.chaotong.yujia.main.entity.HuiYuanBean;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class DaiPingJiaBean {
    String date;
    String time;
    String classid;
    String classname;
    String stadium;
    String trainerid;
    String trainer;
    String trainerpic;

    public DaiPingJiaBean(String date, String time, String classid, String classname,
                          String stadium, String trainerid, String trainer, String trainerpic) {
        this.date = date;
        this.time = time;
        this.classid = classid;
        this.classname = classname;
        this.stadium = stadium;
        this.trainerid = trainerid;
        this.trainer = trainer;
        this.trainerpic = trainerpic;
    }

    public DaiPingJiaBean() {
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

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTrainerid() {
        return trainerid;
    }

    public void setTrainerid(String trainerid) {
        this.trainerid = trainerid;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainerpic() {
        return trainerpic;
    }

    public void setTrainerpic(String trainerpic) {
        this.trainerpic = trainerpic;
    }
}
