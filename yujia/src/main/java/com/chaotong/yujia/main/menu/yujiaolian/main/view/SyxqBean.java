package com.chaotong.yujia.main.menu.yujiaolian.main.view;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 */
public class SyxqBean implements Serializable{

    /**
     * code : 20
     * msg : 请先登录
     * pics : ["http://192.168.0.254:8080/wit_yjj/yujia/upload/3000000011ZB9KR2.jpg"]
     * ncname : \u68B5\u6797\u745C\u4F3D\u7684\u80E1\u8001\u5E08
     * lv : 1.0
     * jf : 0.0
     * type : 肚皮舞
     * Pj : 1
     * maxclasssum : 1
     * ifsc_code : 0
     * ifsc : 收藏
     * classdetails : [{"date":"12月26日星期一","detail":[{"time":"14:30-15:30","maxpeople":"2","minpeople":"1","havepeople":"0","classid":"00000148","classname":"热瑜伽","classstatu_code":"0","classstatu":"预约","sy_code":"1","sy_msg":"私约课","stadiumname":"梵林瑜伽","classmoney":"300"}]},{"date":"12月27日星期二","detail":[]},{"date":"12月28日星期三","detail":[]},{"date":"12月29日星期四","detail":[]},{"date":"12月30日星期五","detail":[]},{"date":"12月31日星期六","detail":[]},{"date":"1月01日星期日","detail":[]}]
     */

    private String code;
    private String msg;
    private String ncname;
    private String lv;
    private String jf;
    private String type;
    private String Pj;
    private String maxclasssum;
    private String ifsc_code;
    private String ifsc;
    private List<String> pics;
    /**
     * date : 12月26日星期一
     * detail : [{"time":"14:30-15:30","maxpeople":"2","minpeople":"1","havepeople":"0","classid":"00000148","classname":"热瑜伽","classstatu_code":"0","classstatu":"预约","sy_code":"1","sy_msg":"私约课","stadiumname":"梵林瑜伽","classmoney":"300"}]
     */

    private List<ClassdetailsBean> classdetails;

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

    public String getNcname() {
        return ncname;
    }

    public void setNcname(String ncname) {
        this.ncname = ncname;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPj() {
        return Pj;
    }

    public void setPj(String Pj) {
        this.Pj = Pj;
    }

    public String getMaxclasssum() {
        return maxclasssum;
    }

    public void setMaxclasssum(String maxclasssum) {
        this.maxclasssum = maxclasssum;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<ClassdetailsBean> getClassdetails() {
        return classdetails;
    }

    public void setClassdetails(List<ClassdetailsBean> classdetails) {
        this.classdetails = classdetails;
    }

    public static class ClassdetailsBean implements Serializable{
        private String date;
        /**
         * time : 14:30-15:30
         * maxpeople : 2
         * minpeople : 1
         * havepeople : 0
         * classid : 00000148
         * classname : 热瑜伽
         * classstatu_code : 0
         * classstatu : 预约
         * sy_code : 1
         * sy_msg : 私约课
         * stadiumname : 梵林瑜伽
         * classmoney : 300
         */

        private List<DetailBean> detail;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean  implements Serializable{
            private String time;
            private String maxpeople;
            private String minpeople;
            private String havepeople;
            private String classid;
            private String classname;
            private String classstatu_code;
            private String classstatu;
            private String sy_code;
            private String sy_msg;
            private String stadiumname;
            private String classmoney;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMaxpeople() {
                return maxpeople;
            }

            public void setMaxpeople(String maxpeople) {
                this.maxpeople = maxpeople;
            }

            public String getMinpeople() {
                return minpeople;
            }

            public void setMinpeople(String minpeople) {
                this.minpeople = minpeople;
            }

            public String getHavepeople() {
                return havepeople;
            }

            public void setHavepeople(String havepeople) {
                this.havepeople = havepeople;
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

            public String getClassstatu_code() {
                return classstatu_code;
            }

            public void setClassstatu_code(String classstatu_code) {
                this.classstatu_code = classstatu_code;
            }

            public String getClassstatu() {
                return classstatu;
            }

            public void setClassstatu(String classstatu) {
                this.classstatu = classstatu;
            }

            public String getSy_code() {
                return sy_code;
            }

            public void setSy_code(String sy_code) {
                this.sy_code = sy_code;
            }

            public String getSy_msg() {
                return sy_msg;
            }

            public void setSy_msg(String sy_msg) {
                this.sy_msg = sy_msg;
            }

            public String getStadiumname() {
                return stadiumname;
            }

            public void setStadiumname(String stadiumname) {
                this.stadiumname = stadiumname;
            }

            public String getClassmoney() {
                return classmoney;
            }

            public void setClassmoney(String classmoney) {
                this.classmoney = classmoney;
            }
        }
    }
}
