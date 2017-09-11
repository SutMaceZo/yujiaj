package com.chaotong.yujia.main.menu.benggong.wo_de_shou_chang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.adapter.CollectChangguanAdapter;
import com.chaotong.yujia.main.adapter.CollectJiaolianAdapter;
import com.chaotong.yujia.main.adapter.ViewPagerAdapter;
import com.chaotong.yujia.main.entity.collect.CollectChangguan;
import com.chaotong.yujia.main.entity.collect.CollectJiaolian;
import com.chaotong.yujia.main.menu.benggong.wo_de_you_hui_juan.WdyhjActivity;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.SearchCgAdapter;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.CgxqActivity;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WdscActivity extends BaseActivity{

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView mbBack;

    @Bind(R.id.tablayout)
    TabLayout mTablayout;

    @Bind(R.id.vp)
    ViewPager mVp;

    List<Fragment> mFragmetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdsc);
        ButterKnife.bind(this);
        initView();

    }

    public  void initView() {
        mFragmetList=new ArrayList<>();
        initTablayout();

        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initTablayout() {
        mFragmetList.add(new ScCgFragment());
        mFragmetList.add(new ScjlFragment());
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(mFragmetList.get(0),"场馆");
        adapter.addFrag(mFragmetList.get(1),"教练");
        mVp.setAdapter(adapter);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        mTablayout.setTabTextColors(getResources().getColor(R.color.textcolor),
               getResources().getColor(R.color.titlebar_color));
        mTablayout.setupWithViewPager(mVp);
    }

}
