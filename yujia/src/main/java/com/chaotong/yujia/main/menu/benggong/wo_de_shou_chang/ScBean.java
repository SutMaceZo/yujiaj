package com.chaotong.yujia.main.menu.benggong.wo_de_shou_chang;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class ScBean {

    /**
     * code : 1
     * msg : 返回成功
     * wdscjl : [{"trainerid":"3000000027","trainername":"Lion","type":"瑜伽","tel":"13501165492","lv":"4.0","pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/3000000027v99D7v.jpg"}]
     * wdsccg : [{"stadiumsid":"6000000015","stadiumsname":"梵林瑜伽","add":"湖南省长沙市长沙县","tel":"15308408987","mobile":"82301055","area":"","pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/20170506100553702.jpeg","distance":">100km"}]
     */

    private String code;
    private String msg;
    /**
     * trainerid : 3000000027
     * trainername : Lion
     * type : 瑜伽
     * tel : 13501165492
     * lv : 4.0
     * pic : http://223.72.192.176:8080/wit_yjj/yujia/upload/3000000027v99D7v.jpg
     */

    private List<WdscjlBean> wdscjl;
    /**
     * stadiumsid : 6000000015
     * stadiumsname : 梵林瑜伽
     * add : 湖南省长沙市长沙县
     * tel : 15308408987
     * mobile : 82301055
     * area :
     * pic : http://223.72.192.176:8080/wit_yjj/yujia/upload/20170506100553702.jpeg
     * distance : >100km
     */

    private List<WdsccgBean> wdsccg;

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

    public List<WdscjlBean> getWdscjl() {
        return wdscjl;
    }

    public void setWdscjl(List<WdscjlBean> wdscjl) {
        this.wdscjl = wdscjl;
    }

    public List<WdsccgBean> getWdsccg() {
        return wdsccg;
    }

    public void setWdsccg(List<WdsccgBean> wdsccg) {
        this.wdsccg = wdsccg;
    }

    public static class WdscjlBean {
        private String trainerid;
        private String trainername;
        private String type;
        private String tel;
        private String lv;
        private String pic;

        public String getTrainerid() {
            return trainerid;
        }

        public void setTrainerid(String trainerid) {
            this.trainerid = trainerid;
        }

        public String getTrainername() {
            return trainername;
        }

        public void setTrainername(String trainername) {
            this.trainername = trainername;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getLv() {
            return lv;
        }

        public void setLv(String lv) {
            this.lv = lv;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public static class WdsccgBean {
        private String stadiumsid;
        private String stadiumsname;
        private String add;
        private String tel;
        private String mobile;
        private String area;
        private String pic;
        private String distance;

        public String getStadiumsid() {
            return stadiumsid;
        }

        public void setStadiumsid(String stadiumsid) {
            this.stadiumsid = stadiumsid;
        }

        public String getStadiumsname() {
            return stadiumsname;
        }

        public void setStadiumsname(String stadiumsname) {
            this.stadiumsname = stadiumsname;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
