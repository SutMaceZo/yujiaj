package com.chaotong.yujia.main.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class SdPjBean implements Serializable{

    /**
     * code : 1
     * msg : 返回成功
     * wdpj : [{"name":"雯子","classname":"哈达瑜伽","pic":"http://192.168.0.159:8080/yujiajia/main/pic/Member/13312341234.jpg","date":"2016-07-18","time":"下午 5:53:19","content":"我"}]
     */

    private String code;
    private String msg;
    /**
     * name : 雯子
     * classname : 哈达瑜伽
     * pic : http://192.168.0.159:8080/yujiajia/main/pic/Member/13312341234.jpg
     * date : 2016-07-18
     * time : 下午 5:53:19
     * content : 我
     */

    private List<WdpjBean> wdpj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WdpjBean> getWdpj() {
        return wdpj;
    }

    public void setWdpj(List<WdpjBean> wdpj) {
        this.wdpj = wdpj;
    }

    public static class WdpjBean implements Serializable {
        private String name;
        private String classname;
        private String pic;
        private String date;
        private String time;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
