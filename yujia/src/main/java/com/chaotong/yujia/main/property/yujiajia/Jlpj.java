package com.chaotong.yujia.main.property.yujiajia;

import java.util.List;

/**
 * 教练评价
 */
public class Jlpj {

    private String code;
    private String msg;
    private List<Trainerspj> trainerspj;//评价属性

    public List<Trainerspj> getTrainerspj() {
        return trainerspj;
    }

    public void setTrainerspj(List<Trainerspj> trainerspj) {
        this.trainerspj = trainerspj;
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
