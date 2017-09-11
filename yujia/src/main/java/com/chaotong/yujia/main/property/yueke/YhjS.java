package com.chaotong.yujia.main.property.yueke;

import com.chaotong.yujia.main.property.yujiajia.YhjPro;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class YhjS {
    //优惠卷列表
    private String code;
    private String msg;
    private List<YhjPro> yhj;
    private String yytype_code;
    private String yytype_msg;

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

    public List<YhjPro> getYhj() {
        return yhj;
    }

    public void setYhj(List<YhjPro> yhj) {
        this.yhj = yhj;
    }

    public String getYytype_code() {
        return yytype_code;
    }

    public void setYytype_code(String yytype_code) {
        this.yytype_code = yytype_code;
    }

    public String getYytype_msg() {
        return yytype_msg;
    }

    public void setYytype_msg(String yytype_msg) {
        this.yytype_msg = yytype_msg;
    }
}
