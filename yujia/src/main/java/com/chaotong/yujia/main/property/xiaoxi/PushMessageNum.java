package com.chaotong.yujia.main.property.xiaoxi;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class PushMessageNum {

    private String code;
    private String msg;
    private List<PushMsgNum> pushmsg_nums;

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

    public List<PushMsgNum> getPushmsg_nums() {
        return pushmsg_nums;
    }

    public void setPushmsg_nums(List<PushMsgNum> pushmsg_nums) {
        this.pushmsg_nums = pushmsg_nums;
    }
}
