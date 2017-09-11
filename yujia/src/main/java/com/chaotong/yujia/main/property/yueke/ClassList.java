package com.chaotong.yujia.main.property.yueke;

/**
 * 课程属性
 */
public class ClassList {

    private String ct_id;    //课程id
    private String ct_name;    //课程名称
    private String ct_pic;    //课程图片
    private String classpic;
    private String classstatu;//	预约状态（可预约，已预约，约满）
    private String sp_id;    //场馆id
    private String sp_name;    //场馆名称
    private String distance;    //距离
    private String ct_time;  //时间

    public String getClasspic() {
        return classpic;
    }

    public void setClasspic(String classpic) {
        this.classpic = classpic;
    }

    public String getCt_time() {
        return ct_time;
    }

    public void setCt_time(String ct_time) {
        this.ct_time = ct_time;
    }


    public String getCt_id() {
        return ct_id;
    }

    public void setCt_id(String ct_id) {
        this.ct_id = ct_id;
    }

    public String getCt_name() {
        return ct_name;
    }

    public void setCt_name(String ct_name) {
        this.ct_name = ct_name;
    }

    public String getCt_pic() {
        return ct_pic;
    }

    public void setCt_pic(String ct_pic) {
        this.ct_pic = ct_pic;
    }

    public String getClassstatu() {
        return classstatu;
    }

    public void setClassstatu(String classstatu) {
        this.classstatu = classstatu;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
