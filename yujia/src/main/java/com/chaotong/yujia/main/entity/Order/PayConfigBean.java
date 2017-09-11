package com.chaotong.yujia.main.entity.Order;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class PayConfigBean {

    /**
     * code : 1
     * msg : 返回成功
     * appid : wx570f6ae848cbd3a1
     * partnerid : 1426999002
     * prepayid : wx20161223165255bea09d41040988758966
     * packages : Sign=WXPay
     * noncestr : 7524d788e91642bdb14d015b9e98f815
     * timestamp : 1482483158
     * sign : 9B4D5D3225C5D32BE84135A768D3233C
     */

    private String code;
    private String msg;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String packages;
    private String noncestr;
    private String timestamp;
    private String sign;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PayConfigBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packages='" + packages + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
