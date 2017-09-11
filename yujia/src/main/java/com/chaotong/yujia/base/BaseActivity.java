package com.chaotong.yujia.base;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;


//import com.chaotong.yujia.main.utils.AppManager;
//import com.facebook.drawee.backends.pipeline.Fresco;
import com.chaotong.yujia.main.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wyouflf on 15/11/4.
 */
public class BaseActivity extends AppCompatActivity {


    private static final String TAG = "BaseActivity";

    public String TAG1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        AppManager.getAppManager().addActivity(this);
//        Fresco.initialize(this);
//        initUmeng();
    }
    protected void initUmeng(){
        TAG1 = getComponentName().getShortClassName();
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG1);
        MobclickAgent.onResume(this);

    }
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG1);
        MobclickAgent.onPause(this);
    }



}
