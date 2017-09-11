package com.chaotong.yujia.main.menu.xiulian.main.view;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class cropString {

    public static String getString(String value) {
        String s = "";
        s = value.substring(5);
        return s;
    }

    public static String corpString2(String value) {
        value=value.replace(" ","+");
        return value;
    }

}
