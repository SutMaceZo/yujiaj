package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/12 0012.
 */
public class SscgBean {

    /**
     * code : 1
     * msg : 返回成功
     * cards : [{"card_id":"99213","sp_id":"6000000015","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg","valid_date":"2017-12-31 00:00:00","b_time":"00:00:00","e_time":"23:59:00","flag":"0","flag_msg":"年卡","total_num":"0","surplus_num":"0"}]
     */

    private String code;
    private String msg;
    /**
     * card_id : 99213
     * sp_id : 6000000015
     * sp_pic : http://192.168.0.5:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg
     * valid_date : 2017-12-31 00:00:00
     * b_time : 00:00:00
     * e_time : 23:59:00
     * flag : 0
     * flag_msg : 年卡
     * total_num : 0
     * surplus_num : 0
     */

    private List<CardsBean> cards;

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

    public List<CardsBean> getCards() {
        return cards;
    }

    public void setCards(List<CardsBean> cards) {
        this.cards = cards;
    }

    public static class CardsBean {
        private String card_id;
        private String sp_id;
        private String sp_pic;
        private String valid_date;
        private String b_time;
        private String e_time;
        private String flag;
        private String flag_msg;
        private String total_num;
        private String surplus_num;
        private String sp_name;

        private String use_flag;
        private String use_msg;
        private String b_date;
        private String e_date;

        public String getUse_flag() {
            return use_flag;
        }

        public void setUse_flag(String use_flag) {
            this.use_flag = use_flag;
        }

        public String getUse_msg() {
            return use_msg;
        }

        public void setUse_msg(String use_msg) {
            this.use_msg = use_msg;
        }

        public String getB_date() {
            return b_date;
        }

        public void setB_date(String b_date) {
            this.b_date = b_date;
        }

        public String getE_date() {
            return e_date;
        }

        public void setE_date(String e_date) {
            this.e_date = e_date;
        }

        public String getSp_name() {
            return sp_name;
        }

        public void setSp_name(String sp_name) {
            this.sp_name = sp_name;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getSp_id() {
            return sp_id;
        }

        public void setSp_id(String sp_id) {
            this.sp_id = sp_id;
        }

        public String getSp_pic() {
            return sp_pic;
        }

        public void setSp_pic(String sp_pic) {
            this.sp_pic = sp_pic;
        }

        public String getValid_date() {
            return valid_date;
        }

        public void setValid_date(String valid_date) {
            this.valid_date = valid_date;
        }

        public String getB_time() {
            return b_time;
        }

        public void setB_time(String b_time) {
            this.b_time = b_time;
        }

        public String getE_time() {
            return e_time;
        }

        public void setE_time(String e_time) {
            this.e_time = e_time;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getFlag_msg() {
            return flag_msg;
        }

        public void setFlag_msg(String flag_msg) {
            this.flag_msg = flag_msg;
        }

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public String getSurplus_num() {
            return surplus_num;
        }

        public void setSurplus_num(String surplus_num) {
            this.surplus_num = surplus_num;
        }
    }
}
