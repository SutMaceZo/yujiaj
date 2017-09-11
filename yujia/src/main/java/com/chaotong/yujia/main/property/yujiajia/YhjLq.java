package com.chaotong.yujia.main.property.yujiajia;

/**
 * Created by Administrator on 2016/8/24.
 */
public class YhjLq {


    private String code;
    private String msg;
    private String getyhj_code;//1,2,3,4,5,6,7
    private String getyhj_msg;//该优惠券不存在,该优惠券被禁止领用,该优惠券领取时间已过，该优惠券已领完，请勿重复领取，领券成功,积分不够

    public String getGetyhj_msg() {
        return getyhj_msg;
    }

    public void setGetyhj_msg(String getyhj_msg) {
        this.getyhj_msg = getyhj_msg;
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

    public String getGetyhj_code() {
        return getyhj_code;
    }

    public void setGetyhj_code(String getyhj_code) {
        this.getyhj_code = getyhj_code;
    }


}
