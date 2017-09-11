package com.chaotong.yujia.main.entity.HuiYuanBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class SsAddBean {

    /**
     * code : 1
     * msg : 返回成功
     * list : [{"sp_id":"6000000016","sp_name":"果冻瑜伽馆","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/60000000160m7l2O.jpg"},{"sp_id":"6000000018","sp_name":"菜户营瑜伽馆","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/6000000018I5ncF1.jpg"},{"sp_id":"6000000019","sp_name":"南国丽人美容瑜伽会所(侯家塘店)","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/6000000019e6i8JL.PNG"},{"sp_id":"6000000020","sp_name":"财富西环瑜伽中心","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/6000000020l63krU.PNG"},{"sp_id":"6000000021","sp_name":"梵林瑜伽加","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/"},{"sp_id":"6000000022","sp_name":"哈哈哈","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/6000000022341IUU.PNG"},{"sp_id":"6000000023","sp_name":"橙子瑜伽馆","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/6000000023634b7u.jpg"},{"sp_id":"6000000024","sp_name":"测试场馆","sp_pic":"http://192.168.0.5:8080/wit_yjj/yujia/upload/60000000248If25W.jpg"}]
     */

    private String code;
    private String msg;
    /**
     * sp_id : 6000000016
     * sp_name : 果冻瑜伽馆
     * sp_pic : http://192.168.0.5:8080/wit_yjj/yujia/upload/60000000160m7l2O.jpg
     */

    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String sp_id;
        private String sp_name;
        private String sp_pic;

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
    }
}
