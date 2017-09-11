package com.chaotong.yujia.main.property.yujiajia;

/**
 * 轮播消息栏
 */
public class Msg {
    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    private int m_id	;	//消息id
   private String content;	//消息内容

}
