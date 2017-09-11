package com.chaotong.yujia.main.property.yueke;

import com.chaotong.yujia.main.property.yujiajia.Trainers;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class Jllb {

    private String code;
    private String msg;
    private List<String> ts_types;//教练类型
    private List<Trainers> trainers;//教练属性
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

    public List<String> getTs_types() {
        return ts_types;
    }

    public void setTs_types(List<String> ts_types) {
        this.ts_types = ts_types;
    }

    public List<Trainers> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainers> trainers) {
        this.trainers = trainers;
    }
}
