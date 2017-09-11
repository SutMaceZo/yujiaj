package com.chaotong.yujia.main.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class VersonUtils {

    public static String getVersonCode(Context context){
        String vercode="";
        try {
            vercode=context.getPackageManager().
                    getPackageInfo("com.chaotong.yujia.main",0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("info", e.getMessage());
            e.printStackTrace();
        }

        return vercode;
    }
}
