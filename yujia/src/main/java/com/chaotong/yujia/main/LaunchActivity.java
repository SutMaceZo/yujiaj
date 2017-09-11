package com.chaotong.yujia.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.property.yujiajia.Data;
import com.chaotong.yujia.main.property.yujiajia.YujiajiaBean;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.viewUtils;
import com.chaotong.yujia.utils.GsonTools;


/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class LaunchActivity extends BaseActivity {

    SharedPreferences preferences;
    String region = "";
    String findCity = "";
    String reqid = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);
        //进入到主界面
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        }, 1000);

        initView();

    }

    private void initView() {
        preferences = getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region = preferences.getString("region", "");//地区
        findCity = preferences.getString("findCity", "");
        reqid = preferences.getString("reqid", "");


        if (!("").equals(region) && region != null) {
            if (!("").equals(findCity) && findCity != null) {
                initLinster(findCity);
            } else {
                initLinster(region);
            }
        } else {
           /* bt_location.setVisibility(View.VISIBLE);*/
            initLocation();
            Toast.makeText(LaunchActivity.this, "正在定位...", Toast.LENGTH_SHORT).show();
        }
    }

    private AMapLocationClient mLocationClient;

    /**
     * 获取定位坐标
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        option.setOnceLocation(true);

//        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
//        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
//        option.setOnceLocationLatest(true);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        option.setInterval(2000);

        //设置定位参数
        mLocationClient.setLocationOption(option);
        //设置定位监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    Log.i("info=================", aMapLocation.getErrorCode() + "");
                    if (aMapLocation.getErrorCode() == 0) {
                        String region = aMapLocation.getCity();
                        String lat = String.valueOf(aMapLocation.getLatitude());
                        String lon = String.valueOf(aMapLocation.getLongitude());
                        SharedPreferences sp = getSharedPreferences(MyApplication.SpName,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("region", region);
                        editor.putString("findCity", region);
                        editor.putString("latitude", lat);
                        editor.putString("longitude", lon);
                        editor.commit();
                        initLinster(findCity);
                    } else {
                        ToastUtils.showToast(LaunchActivity.this, "定位失败");
                        Log.i("info", "error:" + aMapLocation.getErrorInfo());

                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    UrlPath urlPath = UrlPath.getUrlPath();
    YujiajiaBean yujiajiaBean;
    private Data yujia;

    private void initLinster(String findCity) {
        String city = viewUtils.subString(findCity);
        String citys = viewUtils.corpString(city);
        String url = urlPath.getUrl() + urlPath.ZY_URL.replace("region=x", "region=" + citys);
        String a = url.replaceAll("reqid=x", "reqid=" + reqid);
//        Intent intent = new Intent(
//                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        new ThreadUtils_no(this, a, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                String json = msg.obj.toString();
                if (json != null) {
                    yujiajiaBean = GsonTools.getObject(json, YujiajiaBean.class);
                    if (yujiajiaBean != null && yujiajiaBean.getData() != null) {
                        yujia = yujiajiaBean.getData();
                        MyApplication.setData(yujia);
                    }
//                    Intent intent=new Intent(LaunchActivity.this,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
                } else {
                    ToastUtils.showToast(LaunchActivity.this, getResources().getString(R.string.error));
                }


            }
        }).start();

    }

}
