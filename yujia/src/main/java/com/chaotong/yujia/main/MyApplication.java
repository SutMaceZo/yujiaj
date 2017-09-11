package com.chaotong.yujia.main;

import com.chaotong.yujia.main.property.yujiajia.Data;
import com.chaotong.yujia.main.property.yujiajia.YujiajiaBean;
import com.chaotong.yujia.main.utils.CrashHandler2;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import cn.jpush.android.api.JPushInterface;

/**
 * 用到的百度API可参考http://blog.csdn.net/gf771115/article/details/8208841
 */
public class MyApplication extends Application {

    public static String TAG = "LocTestDemo";
    public static String CT_BlOCkBOOK="CT_BlOCkBOOK";
    public static String CT_MONEY="CT_MONEY";
    public static String APP_TYPE="android";
    public static String PHONENAME="phonename";
    public static String APP_NAME="https://www.pgyer.com/YuJiaJiaAndroid";
    public static Boolean IsFirst=true;


    public static Data yujia;


    private static Handler handler = null;
    private static Context context = null;
    private static long mainThreadId;

//   public static String URL="http://223.72.192.176:8080/";
    public static String URL="http://192.168.0.159:8080/";
  // public  static String URL="http://192.168.0.5:8080/";

    public static String SpName="Yuga";
    public static String getHttpUrl(){
        return URL;
    }
    @Override
    public void onCreate() {

        CrashHandler2 crashHandler2=CrashHandler2.getInstance();
        crashHandler2.init(this);


        Fresco.initialize(this);
        JPushInterface.init(this); // 初始化 JPush


        handler = new Handler();
        context = this;
        //因为MyApplication中的onCreate方法运行在主线程中的代码,获取当前线程id就是主线程id
        mainThreadId = Thread.currentThread().getId();

        super.onCreate();

    }
    public static Data getData(){
        return yujia;
    }
    public static void setData(Data bean)
    {
        MyApplication.yujia=bean;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return context;
    }

    public static long getMainThreadId() {
        return mainThreadId;
    }


}