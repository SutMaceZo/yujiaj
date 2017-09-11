package com.chaotong.yujia.main.property.yujiaolian;

import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class Order {

    private String code;
    private String msg;
    private OrderInfo orderInfo;//订单详情

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

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
