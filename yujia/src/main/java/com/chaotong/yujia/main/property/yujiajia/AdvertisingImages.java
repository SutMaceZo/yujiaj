package com.chaotong.yujia.main.property.yujiajia;

/**
 * 滚动广告图片
 */
public class AdvertisingImages {

     private long ad_id;    //图片id
     private String picurl;//图片路径
     private String content;//图片标题
    private String detail_type;//当前图片的属性（老师、场馆或url）点击判断需要跳转到那个页面
    private String detail_id;//根据请求相应的数据

    public String getDetail_type() {
        return detail_type;
    }

    public void setDetail_type(String detail_type) {
        this.detail_type = detail_type;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public long getAd_id() {
        return ad_id;
    }

    public void setAd_id(long ad_id) {
        this.ad_id = ad_id;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
