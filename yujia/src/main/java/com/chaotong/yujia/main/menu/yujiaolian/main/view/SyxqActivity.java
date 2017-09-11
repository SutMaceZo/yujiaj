package com.chaotong.yujia.main.menu.yujiaolian.main.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.PagerTab;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.xiulian.main.JlPjActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.view.ImageViewAdapter;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.ViewPagerIndicator;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.VpSimpleFragment;
import com.chaotong.yujia.main.property.yujiajia.Classdetails;
import com.chaotong.yujia.main.property.yujiajia.Jlxq;
import com.chaotong.yujia.main.property.yujiajia.Trainers;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.CustomViewPager;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 教练详情页
 */
public class SyxqActivity extends BaseActivity implements View.OnClickListener {

    private List<VpSimpleFragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private CustomViewPager mViewPager;
    private Jlxq jlxq = null;
    private String ts_id;//教练id
    private TextView textView32, textView34, textView28, teacher_name;
    private List<Trainers> trainers;
    private List<String> mDatas;
    private ViewPager mImagePager;
//    private ViewPagerIndicator mIndicator;

    private RatingBar jlxq_ratingBar;
    private List<Classdetails> classdetails;

    @Bind(R.id.srfl)
    SwipeRefreshLayout srfl;
    @Bind(R.id.image1)
    SimpleDraweeView image1;
    @Bind(R.id.probar)
    ProgressBar probar;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Bind(R.id.teacher_pj_layout)
    RelativeLayout pj_detail;

    @Bind(R.id.jiaolian_collect)
    TextView jiaolian_collect;

    @Bind(R.id.jlxq_id_indicator)
    PagerTab mIndicator;

    SharedPreferences preferences;
    String reqid = "";
    List<View> viewList;
    String activity_tag = "";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Log.i("jiaoxinxi===========", json);
            if (!json.equals("") && json != null) {
                probar.setVisibility(View.INVISIBLE);
                srfl.setRefreshing(false);
                Gson gson = new Gson();
                jlxq = gson.fromJson(json, Jlxq.class);
                initView();
                initDatas();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jlxq);
        ButterKnife.bind(this);

        rl_bt_back.setOnClickListener(this);
        init();
    }

    public void init() {

        mViewPager = (CustomViewPager) findViewById(R.id.id_vp);

        teacher_name = (TextView) findViewById(R.id.teacher_name);//名字
        // textView30 = (TextView) findViewById(R.id.teacher_pf);//评分
        textView32 = (TextView) findViewById(R.id.teacher_sanchang);//擅长
        textView34 = (TextView) findViewById(R.id.teacher_rongyu);//荣誉
        jlxq_ratingBar = (RatingBar) findViewById(R.id.teacher_rating);
        textView28 = (TextView) findViewById(R.id.teacher_pj);//评价数
        jlxq_ratingBar.setIsIndicator(true);
        viewList = new ArrayList<>();
        mDatas = new ArrayList<>();
        classdetails = new ArrayList<>();
        mTabContents = new ArrayList<>();
//        mIndicator = (ViewPagerIndicator) findViewById(R.id.jlxq_id_indicator);
        mImagePager = (ViewPager) findViewById(R.id.jiaolian_vp);//教练图片

        probar.setVisibility(View.VISIBLE);
        srfl.setColorSchemeResources(R.color.lightblue, R.color.red, R.color.lightyellow, R.color.colorPrimaryDark);
        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        initLinster();

                    }
                }, 2000);
            }
        });
        Intent intent = getIntent();
        ts_id = intent.getStringExtra("id");
        activity_tag = intent.getStringExtra("activity_tag");
        preferences = getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        reqid = preferences.getString("reqid", "defaultname");
        pj_detail.setOnClickListener(this);
        initLinster();


        jiaolian_collect.setOnClickListener(this);
    }
    private int currentItem;
    public void initDatas() {
        currentItem = mViewPager.getCurrentItem();
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
        }
        if (jlxq.getClassdetails() != null && jlxq.getClassdetails().size() > 0) {
            for (int i = 0; i < jlxq.getClassdetails().size(); i++) {
                mDatas.add(i, jlxq.getClassdetails().get(i).getDate());
            }
        }

        if (mTabContents != null && mTabContents.size() > 0 && mAdapter != null) {
            mTabContents.clear();
            mAdapter.notifyDataSetChanged();
        }
        if (classdetails != null && classdetails.size() > 0) {
            classdetails.clear();
        }
        if (jlxq.getClassdetails() != null && jlxq.getClassdetails().size() > 0) {
            classdetails.addAll(jlxq.getClassdetails());
        }
        for (int i = 0; i < mDatas.size(); i++) {//日期
            String data = mDatas.get(i);
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(this, data, classdetails, i, ts_id, reqid, activity_tag);
            mTabContents.add(fragment);
        }
        ifsc = jlxq.getIfsc();

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }

            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mDatas.get(position);
            }
        };

//        mIndicator.setTabItemTitles(mDatas);
        if (mAdapter != null) {
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(currentItem);
        }
        if(!bangDing){
            mIndicator.setViewPager(mViewPager);
            bangDing = true;
        }

    }
    Boolean bangDing = false;

    Boolean reflash = false;

    @Override
    protected void onPause() {
        super.onPause();
        reflash = true;
        scheduled.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (reflash) {
            initLinster();
            reflash = false;
        }*/
        scheduled = Executors.newSingleThreadScheduledExecutor();
        if (viewList.size() > 0 && viewList.size() != 1) {
            scheduled.scheduleAtFixedRate(new ViewPagerTask(), 1, 4, TimeUnit.SECONDS);
        }

    }

    //获取教练信息
    public void initLinster() {
        String mSyUrl = urlPath.getUrl() + "YjlTrainersDetailServlet?";
        String url = mSyUrl + "reqid=" + reqid + "&trainerid=" + ts_id;
        Log.i("jiaolxiangq==========", url);
        new ThreadUtils_no(SyxqActivity.this, url, handler).start();

    }

    private ScheduledExecutorService scheduled;
    private int currPage = 0;//当前显示的页


    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            //改变当前页面的值
            currPage = (currPage + 1) % viewList.size();
            //发送消息给UI线程
            handlers.sendEmptyMessage(0);
        }
    }

    private Handler handlers = new Handler() {
        public void handleMessage(Message msg) {
            //接收到消息后，更新页面
            mImagePager.setCurrentItem(currPage);
        }

        ;
    };

    public void initView() {


        String face = StringEscapeUtils.unescapeJava(jlxq.getNcname());
        teacher_name.setText(face);


        // textView30.setText(jlxq.getLv());
        textView32.setText(jlxq.getType());
        textView34.setText(jlxq.getHonor());
        //image1.setImageURI(jlxq.get);
        textView28.setText("(" + jlxq.getPj() + "人评价)");

        if (jlxq.getLv() != null && !("").equals(jlxq.getLv())) {
            jlxq_ratingBar.setRating(Float.valueOf(jlxq.getLv()));
            jlxq_ratingBar.setEnabled(false);
        }

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(getApplicationContext().WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        if (!jlxq.getPics().get(0).equals("") && jlxq.getPics().get(0) != null) {
            image1.setImageURI(Uri.parse(jlxq.getPics().get(0)));
        }
        if (jlxq.getPics() != null && jlxq.getPics().size() > 0) {
            for (int i = 0; i < jlxq.getPics().size(); i++) {
                SimpleDraweeView image = new SimpleDraweeView(SyxqActivity.this);

                GenericDraweeHierarchy hierarchy = image.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                hierarchy.setPlaceholderImage(R.drawable.not_find);
                if (!jlxq.getPics().get(i).equals("") && jlxq.getPics().get(i) != null) {
                    image.setImageURI(Uri.parse(jlxq.getPics().get(i)));
                }
                viewList.add(image);
            }
            ImageViewAdapter imageViewAdapter = new ImageViewAdapter(viewList);
            mImagePager.setAdapter(imageViewAdapter);


            mImagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            collect_state = jlxq.getIfsc_code();
            if (!("").equals(collect_state)) {
                if (collect_state.equals("0")) {
                    jiaolian_collect.setText(jlxq.getIfsc());
                }
                if (collect_state.equals("1")) {
                    jiaolian_collect.setText(jlxq.getIfsc());
                }
            }
        }

    }


    UrlPath urlPath = UrlPath.getUrlPath();
    private String collect_state = "";
    String ifsc = "收藏";
    private String Collect_url = urlPath.getUrl() + "ScServlet?";
    private Handler lhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String message = object.getString("msg");
                if (("1").equals(code)) {
                    jiaolian_collect.setText("已收藏");
                    collect_state = "0";
                    ToastUtils.showToast(SyxqActivity.this, "收藏成功");
                } else if (("2").equals(code)) {
                    jiaolian_collect.setText("收藏");
                    collect_state = "1";
                    ToastUtils.showToast(SyxqActivity.this, "取消成功");
                } else if (("0").equals(code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SyxqActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle(getResources().getString(R.string.app_name) + "")
                            .setMessage(getResources().getString(R.string.message) + "")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putBoolean("isLogin", false);
                                    editor.putString("reqid", "");
                                    editor.putString("type", "");
                                    editor.commit();
                                    Intent intent = new Intent(SyxqActivity.this, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).show();
                } else {
                    Toast.makeText(SyxqActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bt_back:
                finish();
                break;
            case R.id.teacher_pj_layout:
                Intent intent = new Intent(SyxqActivity.this, JlPjActivity.class);
                intent.putExtra("ts_id", ts_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.jiaolian_collect:
                String b;
                if (collect_state.equals("1")) {
                    ifsc = "收藏";
                    b = Collect_url + "reqid=" + reqid + "&sctype=" + "教练" + "&scid=" + ts_id + "&ifsc=" + ifsc;
                } else {
                    ifsc = "取消收藏";
                    b = Collect_url + "reqid=" + reqid + "&sctype=" + "教练" + "&scid=" + ts_id + "&ifsc=" + ifsc;
                }
                new ThreadUtils_no(SyxqActivity.this, b, lhandler).start();
                break;
        }
    }
}
