package com.chaotong.yujia.utils;

/**
 * Created by Administrator on 2016/8/1.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class GsonTools {

    public GsonTools() {
    }

    //使用Gson进行解析对象
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
        }
        return t;
    }


    // 使用Gson进行解析 List
    public static <T> List<T> getList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

}