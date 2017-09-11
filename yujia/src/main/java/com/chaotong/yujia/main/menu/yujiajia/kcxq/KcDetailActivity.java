package com.chaotong.yujia.main.menu.yujiajia.kcxq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;


import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.SyddActivity;
import com.chaotong.yujia.main.property.yujiajia.kcxqBean;
import com.chaotong.yujia.main.thread.ThreadUtils_no;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/23 0023.
 */

public class KcDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.class_changguan)
     TextView class_changguan;
    @Bind(R.id.class_time)
     TextView class_time;
    @Bind(R.id.class_adress)
     TextView class_adress;
    @Bind(R.id.class_phone)
     TextView class_phone;
    @Bind(R.id.class_info)
     TextView class_info;
    @Bind(R.id.or_yuyue)
     Button or_yuyue;

    @Bind(R.id.vp)
     ViewPager classImage;
    List<String> images = null;
    ArrayList<ImageView> imageSource = null;//图片资源
    MyPagerAdapter adapter;//viewPager的适配器

    @Bind(R.id.class_type)
     TextView class_type;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
     ImageView bt_back;

    @Bind(R.id.kc_srfl)
     SwipeRefreshLayout kc_srfl;
    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.progress)
     ProgressBar pregress;
    String reqid = "";
    SharedPreferences sp;
    String classid = "";
    String url = urlPath.getUrl() + "ClassDetailServlet?";

    kcxqBean kcxq = null;
    int imageWidth;
    int imageHeight;
    String longitude;
    String latitude;


    @Bind(R.id.kc_layout_adress)
    RelativeLayout kc_adress_layout;
    @Bind(R.id.kc_layout_tel)
    RelativeLayout kc_tel_layout;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Log.i("yi------------",json);
            Gson gson = new Gson();
            kcxq = gson.fromJson(json, kcxqBean.class);
            if (kcxq != null) {
                kc_srfl.setRefreshing(false);
                pregress.setVisibility(View.INVISIBLE);
                class_changguan.setText(kcxq.getStadiumname());
                class_adress.setText(kcxq.getPosition());
                class_time.setText(kcxq.getDate());
                class_phone.setText(kcxq.getMobile());
                class_info.setText(kcxq.getPrompt());
                class_type.setText(kcxq.getClassname());
                String classstate_code = kcxq.getClassstatu_code();
                String classstate = kcxq.getSy_code();
                if (classstate.equals("0")) {
                    if (classstate_code.equals("0")){
                        or_yuyue.setText(kcxq.getClassstatu());
                        or_yuyue.setBackgroundResource(R.drawable.text_bg2);
                    }else{
                        or_yuyue.setBackgroundResource(R.drawable.text_bg4);
                        or_yuyue.setText(kcxq.getClassstatu());
                        or_yuyue.setEnabled(false);
                    }

                } else {
                    if(!activity_tag.equals("")&&activity_tag.equals("buxianshi")){

                        if (classstate_code.equals("0")){
                            or_yuyue.setText(kcxq.getClassstatu());
                            or_yuyue.setBackgroundResource(R.drawable.text_bg2);
                        }else{
                            or_yuyue.setBackgroundResource(R.drawable.text_bg4);
                            or_yuyue.setText(kcxq.getClassstatu());
                            or_yuyue.setEnabled(false);
                        }
                    }else{
                        if (classstate_code.equals("0")){
                            or_yuyue.setText(kcxq.getClassstatu());
                            or_yuyue.setBackgroundResource(R.drawable.text_bg2);
                        }else{
                            or_yuyue.setBackgroundResource(R.drawable.text_bg4);
                            or_yuyue.setText(kcxq.getClassstatu());
                            or_yuyue.setEnabled(false);
                        }
                    }

                }
                latitude = kcxq.getLatitude();
                longitude = kcxq.getLongitude();
                initImage(kcxq);
                initEvent();
            }
            super.handleMessage(msg);
        }
    };

    private void initImage(kcxqBean kcxq01) {
        images = kcxq01.getPics();
        for (int i = 0; i < images.size(); i++) {
            SimpleDraweeView image = new SimpleDraweeView(this);
            GenericDraweeHierarchy hierarchy=image.getHierarchy();
            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
            hierarchy.setPlaceholderImage(R.drawable.not_find);
           if (images.get(i)!=null&&!images.get(i).equals("")){
               image.setImageURI(Uri.parse(images.get(i)));
               Log.i("image----------",images.get(i));
           }
            imageSource.add(image);
        }
        adapter = new MyPagerAdapter();
        classImage.setAdapter(adapter);

    }

    String ts_id;
    String codeId = "";
    String cg_id;
    String activity_tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kcxq);
        ButterKnife.bind(this);
        init();
        initView();
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initEvent() {
        kc_adress_layout.setOnClickListener(this);
        kc_tel_layout.setOnClickListener(this);
        if (!reqid.equals("")) {
            or_yuyue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity_tag.equals("")&&kcxq.getSy_code().equals("1")){
                        Intent intent = new Intent(KcDetailActivity.this, SyddActivity.class);
                        intent.putExtra("classid", kcxq.getClassid());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(KcDetailActivity.this, QdddActivity.class);
                        intent.putExtra("classid", kcxq.getClassid());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
            });
        }else {
            Toast.makeText(KcDetailActivity.this, "请先登录", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK && data != null) {
            classid = data.getStringExtra("classid");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        pregress.setVisibility(View.VISIBLE);
        kc_srfl.setColorSchemeResources(R.color.lightblue, R.color.red, R.color.lightyellow, R.color.colorPrimaryDark);
        kc_srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        initListener();
                    }
                }, 2000);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        imageWidth = dm.widthPixels;
        imageHeight = dm.heightPixels;
        imageSource = new ArrayList<ImageView>();
    }

    private void initView() {
        sp = getSharedPreferences("Yuga", MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        classid = getIntent().getStringExtra("classid");
        activity_tag = getIntent().getStringExtra("activity_tag");
        initListener();
    }

    Boolean reflash=false;
    @Override
    protected void onPause() {
        super.onPause();
        reflash=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    if (reflash){
        reflash=false;
        initListener();
    }
    }

    private void initListener() {

        String url2 = url + "classid=" + classid + "&reqid=" + reqid;
        new ThreadUtils_no(this, url2, handler).start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kc_layout_adress:
                try {
                    Uri uri = Uri.parse("geo:" + longitude + ","
                            + latitude + "?" + "q=" + kcxq.getPosition());
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent1);
                } catch (Exception e) {
                    Toast.makeText(KcDetailActivity.this,
                            "您未安装地图，无法查看", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.kc_layout_tel:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:" + kcxq.getMobile()));
                startActivity(intent1);
                break;
        }

    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //判断将要显示的图片是否和现在显示的图片是同一个
            //arg0为当前显示的图片，arg1是将要显示的图片

            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁该图片
            container.removeView(imageSource.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            //初始化将要显示的图片，将该图片加入到container中，即viewPager中
            container.addView(imageSource.get(position));
            //给该图片设置点击事件
            View view = imageSource.get(position);

            return view;
        }
    }
}
