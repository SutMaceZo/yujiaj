package com.chaotong.yujia.main.property.yujiajia;

/**
 * 场馆栏
 */
public class Stadiums {

    private String sp_id;	//	场馆id
    private String sp_name;	//	场馆名
    private String sp_pic;	//	图片路径
    private String sp_pf;   //	场馆评分
    private String distance;//	距离
    private String address;

    @Override
    public String toString() {
        return "Stadiums{" +
                "sp_id='" + sp_id + '\'' +
                ", sp_name='" + sp_name + '\'' +
                ", sp_pic='" + sp_pic + '\'' +
                ", sp_pf='" + sp_pf + '\'' +
                ", distance='" + distance + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSp_pf() {
        return sp_pf;
    }

    public void setSp_pf(String sp_pf) {
        this.sp_pf = sp_pf;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public String getSp_pic() {
        return sp_pic;
    }

    public void setSp_pic(String sp_pic) {
        this.sp_pic = sp_pic;
    }
}
