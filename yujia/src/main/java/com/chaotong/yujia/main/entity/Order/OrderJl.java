package com.chaotong.yujia.main.entity.Order;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class OrderJl {


    /**
     * code : 1
     * msg : 返回成功
     * ts_types : ["热瑜伽","普拉提","瑜伽","艾扬格","肚皮舞","普拉提","颈椎调理","生理期修复","哈他流","其他","空中瑜伽","阴瑜伽"]
     * trainers : [{"ts_id":"3000000011","ts_name":"\\u68B5\\u6797\\u745C\\u4F3D\\u7684\\u80E1\\u8001\\u5E08","ts_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/3000000011ZB9KR2.jpg","ts_type":"肚皮舞","ts_lv":"1.0","sex":"女"},{"ts_id":"3000000012","ts_name":"\\u6559\\u7EC3\\u5377","ts_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/3000000012E93GYB.jpg","ts_type":"高温瑜伽","ts_lv":"1.0","sex":"女"}]
     */

    private String code;
    private String msg;
    private List<String> ts_types;
    /**
     * ts_id : 3000000011
     * ts_name : \u68B5\u6797\u745C\u4F3D\u7684\u80E1\u8001\u5E08
     * ts_pic : http://223.72.192.176:8080/wit_yjj/yujia/upload/3000000011ZB9KR2.jpg
     * ts_type : 肚皮舞
     * ts_lv : 1.0
     * sex : 女
     */

    private List<TrainersBean> trainers;

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

    public List<String> getTs_types() {
        return ts_types;
    }

    public void setTs_types(List<String> ts_types) {
        this.ts_types = ts_types;
    }

    public List<TrainersBean> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TrainersBean> trainers) {
        this.trainers = trainers;
    }

    public static class TrainersBean {
        private String ts_id;
        private String ts_name;
        private String ts_pic;
        private String ts_type;
        private String ts_lv;
        private String sex;

        public String getTs_id() {
            return ts_id;
        }

        public void setTs_id(String ts_id) {
            this.ts_id = ts_id;
        }

        public String getTs_name() {
            return ts_name;
        }

        public void setTs_name(String ts_name) {
            this.ts_name = ts_name;
        }

        public String getTs_pic() {
            return ts_pic;
        }

        public void setTs_pic(String ts_pic) {
            this.ts_pic = ts_pic;
        }

        public String getTs_type() {
            return ts_type;
        }

        public void setTs_type(String ts_type) {
            this.ts_type = ts_type;
        }

        public String getTs_lv() {
            return ts_lv;
        }

        public void setTs_lv(String ts_lv) {
            this.ts_lv = ts_lv;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
