package com.chaotong.yujia.main.property.yujiajia;

/**
 * 组件栏
 */
public class ActiveComponents {

    private int ac_id;//组件id
    private String picUrl;//图片路径
    private String content;//组件内容

    public int getAc_id() {
        return ac_id;
    }

    public void setAc_id(int ac_id) {
        this.ac_id = ac_id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return  "ac_id == "+ac_id;
    }
}
