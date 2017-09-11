package com.chaotong.yujia.main.menu.yujiajia.cgxq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.PagerTab;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.xiulian.main.CgPjActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.view.VpSimpleFragment;
import com.chaotong.yujia.main.property.yujiajia.Cgxq;
import com.chaotong.yujia.main.property.yujiajia.CgxqClassdetails;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.CustomViewPager;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 场馆详情
 */

public class CgxqActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.id_indicator)
    PagerTab mIndicator;
    private List<VpSimpleFragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private CustomViewPager mViewPager;
    List<ImageView> ImageResurce;
    private ViewPager CgxqPager;
    private Cgxq cgxq;
    private TextView textView43, textView44, textView45, textView46, textView47;
    private String id;
    private List<String> mDatas;
    List<CgxqClassdetails> mClassdetail;

//    private ViewPagerIndicator mIndicator;
//    @Bind(R.id.id_indicator)
//    TabPageIndicator mIndicator;

    @Bind(R.id.adress_cgxq)
    RelativeLayout adress_cgxq;
    @Bind(R.id.tel_cgxq)
    RelativeLayout tel_cgxq;
    @Bind(R.id.phone_cgxq)
    RelativeLayout mobile_cgxq;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Bind(R.id.cg_collect)
    TextView cg_collect;        //收藏
    UrlPath urlPath = UrlPath.getUrlPath();
    String Collect_url = urlPath.getUrl() + "ScServlet?";
    String reqid = "";
    String activity_tag = "";
    @Bind(R.id.pj_cgxq)
    RelativeLayout pj_cgxq;

    @Bind(R.id.srfl)
    SwipeRefreshLayout srfl;

    @Bind(R.id.progress)
    ProgressBar progress;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String json = msg.obj.toString();
            Log.e("---xiangqing--------", json);
            if (!json.equals("") && json != null) {
                srfl.setRefreshing(false);
                progress.setVisibility(View.INVISIBLE);
                Gson gson = new Gson();
                cgxq = gson.fromJson(json, Cgxq.class);
                initView();
                initDatas();
            }
        }
    };
    private int currentItem;
    private Boolean bangDing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cgxq);

        ButterKnife.bind(this);
        rl_bt_back.setOnClickListener(this);
        initV();
        init();
    }

    String ifsc = "";

    private void init() {
        SharedPreferences preferences = getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        reqid = preferences.getString("reqid", "");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        activity_tag = intent.getStringExtra("activity_tag");
        mClassdetail = new ArrayList<>();

        initLinster();

        progress.setVisibility(View.VISIBLE);
        srfl.setColorSchemeResources(R.color.red, R.color.lightgreen,
                R.color.lavenderblush, R.color.blue);

        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initLinster();

                    }
                }, 2000);
            }
        });

//        ImageResurce = new ArrayList<ImageView>();
//        mDatas = new ArrayList<>();
//        mTabContents = new ArrayList<>();


        cg_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b;
                Log.i("info", reqid);
                if (("0").equals(collect_State)) {
                    ifsc = "收藏";

                    b = Collect_url + "reqid=" + reqid + "&sctype=" + "场馆" + "&scid=" + id + "&ifsc=" + ifsc;
                } else {
                    ifsc = "取消收藏";
                    b = Collect_url + "reqid=" + reqid + "&sctype=" + "场馆" + "&scid=" + id + "&ifsc=" + ifsc;
                }


                new ThreadUtils_no(CgxqActivity.this, b, lhandler).start();
            }
        });

    }

    private Handler lhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (!json.equals("") && json != null) {
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    if (("1").equals(code)) {
                        cg_collect.setText("已收藏");
                        collect_State = "1";
                        ToastUtils.showToast(CgxqActivity.this, "收藏成功");
                    } else if (("2").equals(code)) {
                        cg_collect.setText("收藏");
                        collect_State = "0";
                        ToastUtils.showToast(CgxqActivity.this, "取消成功");
                    } else if (("0").equals(code)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CgxqActivity.this);
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

                                        Intent intent = new Intent(CgxqActivity.this, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }).show();
                    } else {
                        Toast.makeText(CgxqActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mTabContents.clear();
        ImageResurce.clear();
        mDatas.clear();
        mClassdetail.clear();
        finish();

    }

    String a;
    String b;

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
        scheduled.scheduleAtFixedRate(new ViewPagerTask(), 1, 4, TimeUnit.SECONDS);

    }


    String url = urlPath.getUrl() + urlPath.ZY_CGXQ;

    //获取教练信息
    private void initLinster() {
        currentItem = mViewPager.getCurrentItem();


        a = url.replaceAll("stadiumid=x", "stadiumid=" + id);
        b = a.replaceAll("reqid=x", "reqid=" + reqid);

        new ThreadUtils_no(CgxqActivity.this, b, handler).start();
    }

    private void initDatas() {

        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
        }
        for (int i = 0; i < cgxq.getClassdetails().size(); i++) {
            mDatas.add(i, cgxq.getClassdetails().get(i).getDate());
        }
        if (mTabContents != null && mTabContents.size() > 0) {
            mTabContents.clear();
            mAdapter.notifyDataSetChanged();
        }
        if (mClassdetail != null && mClassdetail.size() > 0) {
            mClassdetail.clear();
        }
        if (cgxq.getClassdetails() != null && cgxq.getClassdetails().size() > 0) {
            mClassdetail.addAll(cgxq.getClassdetails());
        }
        for (int i = 0; i < mDatas.size(); i++) {
            String data = mDatas.get(i);

            VpSimpleFragment fragment = VpSimpleFragment.newInstance(this, data, mClassdetail, i, id, reqid, activity_tag);
            mTabContents.add(fragment);
        }
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

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


    private void initV() {
        textView43 = (TextView) findViewById(R.id.te4);//评分
        textView44 = (TextView) findViewById(R.id.textView44);//地址
        textView45 = (TextView) findViewById(R.id.textView45);//电话
        textView46 = (TextView) findViewById(R.id.textView46);//手机
        textView47 = (TextView) findViewById(R.id.textView47);//评论个数
        mViewPager = (CustomViewPager) findViewById(R.id.id_vp);
//        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        ImageResurce = new ArrayList<ImageView>();
        mDatas = new ArrayList<>();
        mTabContents = new ArrayList<>();
    }

    private void initView() {
        //textView43 = (TextView) findViewById(R.id.te4);//评分
        textView43.setText(cgxq.getNcname());
        //textView44 = (TextView) findViewById(R.id.textView44);//地址
        textView44.setText(cgxq.getLocation());
        //textView45 = (TextView) findViewById(R.id.textView45);//电话
        if (cgxq.getTel() != null) {
            textView45.setText(cgxq.getTel());
        }
//        textView46 = (TextView) findViewById(R.id.textView46);//手机
        textView46.setText(cgxq.getMobile());
//        textView47 = (TextView) findViewById(R.id.textView47);//评论个数
        pj_cgxq.setOnClickListener(this);
        textView47.setText("（" + cgxq.getPj() + "人评价）");
        CgxqPager = (ViewPager) findViewById(R.id.cgxq_vp);     //轮播图

        adress_cgxq.setOnClickListener(this);
        tel_cgxq.setOnClickListener(this);
        mobile_cgxq.setOnClickListener(this);
        images = new int[cgxq.getPics().size()];
        List<View> viewList = new ArrayList<>();
        ImageResurce.clear();
        if (cgxq.getPics() != null && cgxq.getPics().size() > 0) {
            for (int i = 0; i < cgxq.getPics().size(); i++) {
                SimpleDraweeView image = new SimpleDraweeView(CgxqActivity.this);
                //ImageView image = new ImageView(CgxqActivity.this);
                GenericDraweeHierarchy hierarchy = image.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                hierarchy.setPlaceholderImage(R.drawable.not_find);


                if (!cgxq.getPics().get(i).equals("") && cgxq.getPics().get(i) != null) {


                    image.setImageURI(Uri.parse(cgxq.getPics().get(i)));
//                   Bitmap bitmap = decodeUriAsBitmapFromNet(cgxq.getPics().get(i));
//                   int height = bitmap.getHeight();
//                   int width = bitmap.getWidth();
//                  Bitmap bitmap1 = reduce(bitmap,height,width,false);
//                   image.setImageBitmap(bitmap1);
                    ImageResurce.add(image);

                    Log.i("uri------------", Uri.parse(cgxq.getPics().get(i)) + "");
                }

            }
        }
        CgxqPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(ImageResurce.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(ImageResurce.get(position));

                return ImageResurce.get(position);
            }
        });
        //对轮播图进行滑动监听
        CgxqPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                oldPage = position;
                currPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        collect_State = cgxq.getIfsc_code();
        if (("0").equals(collect_State)) {
            cg_collect.setText("收藏");
        } else {
            cg_collect.setText("已收藏");
        }

    }

    String collect_State = "";
    int[] images;
    private ScheduledExecutorService scheduled;
    private int currPage = 0;//当前显示的页
    private int oldPage = 0;//上一次显示的页

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            //改变当前页面的值
            currPage = (currPage + 1) % images.length;
            //发送消息给UI线程
            Xhandler.sendEmptyMessage(0);
        }
    }

    private Handler Xhandler = new Handler() {
        public void handleMessage(Message msg) {
            //接收到消息后，更新页面
            if (images.length > 0) {
                CgxqPager.setCurrentItem(currPage);
            }
        }

        ;
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bt_back:
                mTabContents.clear();
                ImageResurce.clear();
                mDatas.clear();
                mClassdetail.clear();
                finish();
                break;
            case R.id.tel_cgxq:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:" + cgxq.getMobile()));
                startActivity(intent1);
                break;
            case R.id.adress_cgxq:
                try {
                    Uri mUri = Uri.parse("geo:" + cgxq.getLongitude() + "," + cgxq.getLatitude() + "?" + "q=" + cgxq.getLocation());
                    Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
                    startActivity(mIntent);
                    break;
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(CgxqActivity.this, "您未安装地图，不能查看！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.phone_cgxq:
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_CALL);
                intent2.setData(Uri.parse("tel:" + cgxq.getTel()));
                startActivity(intent2);
                break;
            case R.id.pj_cgxq:
                Intent intent = new Intent(CgxqActivity.this, CgPjActivity.class);
                intent.putExtra("cg_id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }
    }

    /**
     * 压缩图片
     *
     * @param bitmap   源图片
     * @param width    想要的宽度
     * @param height   想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    public Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        }
        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode);
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
        float sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN).floatValue();
        float sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN).floatValue();
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    private Bitmap decodeUriAsBitmapFromNet(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return bitmap;

    }


}
