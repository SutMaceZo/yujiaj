package com.chaotong.yujia.main.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class loadImageUtils {


    public static int Width(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int Height(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
