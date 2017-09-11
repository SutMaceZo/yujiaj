package com.chaotong.yujia.main.menu.xiulian.main.view;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class SearchCgBean {

    /**
     * code : 1
     * msg : 返回成功
     * stadiums : [{"sp_id":"6000000015","sp_name":"梵林瑜伽","sp_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg","sp_pf":"0","distance":">100km","address":"长沙市长沙县新闻大厦15a"}]
     */

    private String code;
    private String msg;
    /**
     * sp_id : 6000000015
     * sp_name : 梵林瑜伽
     * sp_pic : http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg
     * sp_pf : 0
     * distance : >100km
     * address : 长沙市长沙县新闻大厦15a
     */

    private List<StadiumsBean> stadiums;

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

    public List<StadiumsBean> getStadiums() {
        return stadiums;
    }

    public void setStadiums(List<StadiumsBean> stadiums) {
        this.stadiums = stadiums;
    }

    public static class StadiumsBean {
        private String sp_id;
        private String sp_name;
        private String sp_pic;
        private String sp_pf;
        private String distance;
        private String address;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
