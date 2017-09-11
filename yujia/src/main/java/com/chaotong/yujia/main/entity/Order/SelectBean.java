package com.chaotong.yujia.main.entity.Order;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class SelectBean {

    /**
     * code : 1
     * msg : 返回成功
     * dates : ["12-19 星期一","12-20 星期二","12-21 星期三","12-22 星期四","12-23 星期五","12-24 星期六","12-25 星期日","12-26 星期一"]
     * classtypes : ["全部","热瑜伽","普拉提","瑜伽","艾扬格","肚皮舞","普拉提","颈椎调理","生理期修复","哈他流","其他","空中瑜伽","阴瑜伽"]
     * districts : [{"district":"附近","business":[]}]
     */

    private String code;
    private String msg;
    private List<String> dates;
    private List<String> classtypes;
    /**
     * district : 附近
     * business : []
     */

    private List<DistrictsBean> districts;

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

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getClasstypes() {
        return classtypes;
    }

    public void setClasstypes(List<String> classtypes) {
        this.classtypes = classtypes;
    }

    public List<DistrictsBean> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictsBean> districts) {
        this.districts = districts;
    }

    public static class DistrictsBean {
        private String district;
        private List<String> business;

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public List<String> getBusiness() {
            return business;
        }

        public void setBusiness(List<String> business) {
            this.business = business;
        }
    }
}
