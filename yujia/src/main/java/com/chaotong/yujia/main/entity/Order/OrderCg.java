package com.chaotong.yujia.main.entity.Order;

import com.chaotong.yujia.main.property.yujiajia.Stadiums;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class OrderCg {

    /**
     * code : 1
     * msg : 返回成功
     * Stadiums : [{"sp_id":"6000000015","sp_name":"梵林瑜伽","sp_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000153aY7Pk.jpg","sp_pf":"0","distance":">100km","address":"长沙市长沙县新闻大厦15a"},{"sp_id":"6000000016","sp_name":"果冻瑜伽馆","sp_pic":"http://223.72.192.176:8080/wit_yjj/yujia/upload/60000000160m7l2O.jpg","sp_pf":"0","distance":">100km","address":"长沙市开福区专业瑜伽会所"}]
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

     List<Stadiums> Stadiums;

    public List<com.chaotong.yujia.main.property.yujiajia.Stadiums> getStadiums() {
        return Stadiums;
    }

    public void setStadiums(List<com.chaotong.yujia.main.property.yujiajia.Stadiums> stadiums) {
        Stadiums = stadiums;
    }

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


}
