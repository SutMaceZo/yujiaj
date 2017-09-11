package com.chaotong.yujia.main.menu.yujiaolian.main.view;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */
public class SelectType {

    /**
     * code : 1
     * msg : 返回成功
     * classtype : ["全部","热瑜伽","普拉提","瑜伽","艾扬格","肚皮舞","普拉提","颈椎调理","生理期修复","哈他流","其他","空中瑜伽","阴瑜伽"]
     */

    private String code;
    private String msg;
    private List<String> classtype;

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

    public List<String> getClasstype() {
        return classtype;
    }

    public void setClasstype(List<String> classtype) {
        this.classtype = classtype;
    }
}
