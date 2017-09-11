package com.chaotong.yujia.main.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.chaotong.yujia.main.MyApplication;

/**
 * Created by HASEE on 2016/12/3.
 */
public class UIUtils {
    public static Context getContext(){
        return MyApplication.getContext();
    }
    public static long getMainThreadId(){
        return MyApplication.getMainThreadId();
    }
    public static Handler getHandler(){
        return MyApplication.getHandler();
    }
    //1.xml--->view
    public static View inflate(int layoutId){
        return View.inflate(getContext(),layoutId,null);
    }
    //获取资源文件对象方法
    public static Resources getResource(){
        return getContext().getResources();
    }
    //获取字符串
    public static String getString(int stringId){
        return getResource().getString(stringId);
    }
    //获取图片
    public static Drawable getDrawable(int drawableId){
        return getResource().getDrawable(drawableId);
    }
    //dp和px转换
    //1dp  * density = 0.75px      320*240 density = 0.75
    //1dp  * density = 1px         480*320 density = 1.0
    //1dp  * density = 1.5px       800*480 density = 1.5
    //1dp  * density = 2px         1280*720 density = 2
    //1dp  * density  = 3px         1920*1080 density = 3
    //.....
    public static int dip2px(int dip){
        //矩阵
        float density = getResource().getDisplayMetrics().density;
        //数学角度四舍五入
//        100.7+0.5 = 101.2
//        100.3+0.5 = 100.8
        return (int)(density*dip+0.5);
    }
    //px和dp转换
    public static int  px2dip(int px){
        //矩阵
        float density = getResource().getDisplayMetrics().density;
        //数学角度四舍五入
//        100.7+0.5 = 101.2
//        100.3+0.5 = 100.8
        return (int)(px/density+0.5);
    }

    //保证代码可以运行在主线程中的方法
    public static void runOnUIThread(Runnable runnable){
        //如果此方法运行在主线程中,则直接运行runnable中的run方法
        if(getMainThreadId() == Thread.currentThread().getId()){
            runnable.run();
        }else{
            getHandler().post(runnable);
            //如果此方法运行在子线程中,通过hanlder,将runnable传递到主线程中去运行
        }
    }

    public static ColorStateList getColorStateList(int colorStateListId) {
        return getResource().getColorStateList(colorStateListId);
    }

    //获取stringArray方法
    public static String[] getStringArray(int stringArrayId){
        return getResource().getStringArray(stringArrayId);
    }



}

