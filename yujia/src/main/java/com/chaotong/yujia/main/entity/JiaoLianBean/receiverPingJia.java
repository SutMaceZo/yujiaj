package com.chaotong.yujia.main.entity.JiaoLianBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class receiverPingJia implements Serializable {
    String name;
    String classname;
    String pic;
    String date;
    String time;
    String content;

    public receiverPingJia(String name, String classname,
                           String pic, String date, String time, String content) {
        this.name = name;
        this.classname = classname;
        this.pic = pic;
        this.date = date;
        this.time = time;
        this.content = content;
    }

    public receiverPingJia() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
