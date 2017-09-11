package com.chaotong.yujia.main.entity.Order;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class OrderKc {

    /**
     * code : 1
     * msg : 返回成功
     * classlist : [{"ct_id":"00000127","ct_name":"晨练","ct_time":"07:30-09:00","ct_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg,6000000015Gb8w8J.jpg,6000000015jj3i6d.jpg","classstatu_code":"0","classstatu":"预约","sy_code":"0","sy_msg":"不是私约课","sp_id":"6000000015","sp_name":"梵林瑜伽","distance":">100km","ct_bdate":"2016-12-22 07:30:00"},{"ct_id":"00000128","ct_name":"肚皮舞","ct_time":"12:20-13:20","ct_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg,6000000015Gb8w8J.jpg,6000000015jj3i6d.jpg","classstatu_code":"0","classstatu":"预约","sy_code":"0","sy_msg":"不是私约课","sp_id":"6000000015","sp_name":"梵林瑜伽","distance":">100km","ct_bdate":"2016-12-22 12:20:00"},{"ct_id":"00000129","ct_name":"瑜伽理疗","ct_time":"15:20-16:30","ct_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg,6000000015Gb8w8J.jpg,6000000015jj3i6d.jpg","classstatu_code":"0","classstatu":"预约","sy_code":"0","sy_msg":"不是私约课","sp_id":"6000000015","sp_name":"梵林瑜伽","distance":">100km","ct_bdate":"2016-12-22 15:20:00"},{"ct_id":"00000130","ct_name":"阴瑜伽","ct_time":"18:20-19:20","ct_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg,6000000015Gb8w8J.jpg,6000000015jj3i6d.jpg","classstatu_code":"0","classstatu":"预约","sy_code":"0","sy_msg":"不是私约课","sp_id":"6000000015","sp_name":"梵林瑜伽","distance":">100km","ct_bdate":"2016-12-22 18:20:00"},{"ct_id":"00000131","ct_name":"舞韵瑜伽","ct_time":"19:30-20:30","ct_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg,6000000015Gb8w8J.jpg,6000000015jj3i6d.jpg","classstatu_code":"0","classstatu":"预约","sy_code":"0","sy_msg":"不是私约课","sp_id":"6000000015","sp_name":"梵林瑜伽","distance":">100km","ct_bdate":"2016-12-22 19:30:00"}]
     */

    private String code;
    private String msg;
    /**
     * ct_id : 00000127
     * ct_name : 晨练
     * ct_time : 07:30-09:00
     * ct_pic : http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg,6000000015Gb8w8J.jpg,6000000015jj3i6d.jpg
     * classstatu_code : 0
     * classstatu : 预约
     * sy_code : 0
     * sy_msg : 不是私约课
     * sp_id : 6000000015
     * sp_name : 梵林瑜伽
     * distance : >100km
     * ct_bdate : 2016-12-22 07:30:00
     */

    private List<ClasslistBean> classlist;

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

    public List<ClasslistBean> getClasslist() {
        return classlist;
    }

    public void setClasslist(List<ClasslistBean> classlist) {
        this.classlist = classlist;
    }

    public static class ClasslistBean {
        private String ct_id;
        private String classpic;
        private String ct_name;
        private String ct_time;
        private String ct_pic;
        private String classstatu_code;
        private String classstatu;
        private String sy_code;
        private String sy_msg;
        private String sp_id;
        private String sp_name;
        private String distance;
        private String ct_bdate;

        public String getClasspic() {
            return classpic;
        }

        public void setClasspic(String classpic) {
            this.classpic = classpic;
        }

        @Override
        public String toString() {
            return "ClasslistBean{" +
                    "ct_id='" + ct_id + '\'' +
                    ", ct_name='" + ct_name + '\'' +
                    ", ct_time='" + ct_time + '\'' +
                    ", ct_pic='" + ct_pic + '\'' +
                    ", classstatu_code='" + classstatu_code + '\'' +
                    ", classstatu='" + classstatu + '\'' +
                    ", sy_code='" + sy_code + '\'' +
                    ", sy_msg='" + sy_msg + '\'' +
                    ", sp_id='" + sp_id + '\'' +
                    ", sp_name='" + sp_name + '\'' +
                    ", distance='" + distance + '\'' +
                    ", ct_bdate='" + ct_bdate + '\'' +
                    '}';
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

        public String getCt_time() {
            return ct_time;
        }

        public void setCt_time(String ct_time) {
            this.ct_time = ct_time;
        }

        public String getCt_pic() {
            return ct_pic;
        }

        public void setCt_pic(String ct_pic) {
            this.ct_pic = ct_pic;
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

        public String getCt_bdate() {
            return ct_bdate;
        }

        public void setCt_bdate(String ct_bdate) {
            this.ct_bdate = ct_bdate;
        }
    }
}
