package com.chaotong.yujia.main.menu.yujiajia.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.UrlPath;

import com.chaotong.yujia.main.menu.xiulian.main.adapter.CgAdapter;
import com.chaotong.yujia.main.menu.yujiajia.Location.CityPickerActivity;

import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;

import com.chaotong.yujia.main.menu.yujiajia.cgxq.CgxqActivity;
import com.chaotong.yujia.main.menu.yujiajia.ckgd.FindMoreActivity;
import com.chaotong.yujia.main.menu.yujiajia.ckgd.FindMoreCgActivity;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.main.view.VerticalLampLayout;
import com.chaotong.yujia.main.menu.yujiajia.points.MyPointsActivity;

import com.chaotong.yujia.main.menu.yujiajia.yhj.view.YhjActivity;

import com.chaotong.yujia.main.property.yujiajia.AdvertisingImages;
import com.chaotong.yujia.main.property.yujiajia.Data;
import com.chaotong.yujia.main.property.yujiajia.Msg;
import com.chaotong.yujia.main.property.yujiajia.Stadiums;
import com.chaotong.yujia.main.property.yujiajia.Trainers;
import com.chaotong.yujia.main.property.yujiajia.YujiajiaBean;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.viewUtils;
import com.chaotong.yujia.utils.GsonTools;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;


public class YuJiaJiaFragment extends Fragment implements View.OnClickListener {
    int[] images = null;//图片资源ID
    private Data yujia;//主页属性
    ArrayList<ImageView> imageSource = null;//图片资源

    String[] titles = null;//标题
    ArrayList<View> dots = null;//点
    TextView title = null;
    ViewPager viewPager;//用于显示图片
    MyPagerAdapter adapter;//viewPager的适配器
    private int currPage = 0;//当前显示的页
    private int oldPage = 0;//上一次显示的页
    private ScheduledExecutorService scheduled;

    @Bind(R.id.Vertical)
    VerticalLampLayout verticalLampLayout;

    @Bind(R.id.bt_fyphb)
    TextView bt_fyphb;
    @Bind(R.id.bt_yktz)
    LinearLayout bt_yktz;
    @Bind(R.id.bt_location)
    ImageView bt_location;

    @Bind(R.id.bt_locations)
    RelativeLayout bt_locations;
    @Bind(R.id.findcity)
    TextView bt_find;
    @Bind(R.id.bt_jifen)
    LinearLayout bt_jifen;
    @Bind(R.id.rl_imageButton2)
    RelativeLayout rl_imageButton2;
    @Bind(R.id.imageButton2)
    ImageView imageButton2;//查看更多
    @Bind(R.id.rl_imageButton3)
    RelativeLayout rl_imageButton3;
    @Bind(R.id.imageButton3)
    ImageView imageButton3;//场馆查看更多
    @Bind(R.id.dot1)
    View dot1;
    @Bind(R.id.dot2)
    View dot2;
    @Bind(R.id.dot3)
    View dot3;
    @Bind(R.id.dot4)
    View dot4;
    @Bind(R.id.dot5)
    View dot5;
    String reqid = "";

   /* @Bind(R.id.ls_rel)
    RelativeLayout jl_rel;*/

  /*  @Bind(R.id.cg_rel)
    LinearLayout cg_rel;*/

    private List<Msg> msgs;

    @Bind(R.id.yjj_srfl)
    SwipeRefreshLayout yjj_srfl;
    @Bind(R.id.probar)
    ProgressBar proBar;
    List<AdvertisingImages> advertisingImages;
    SharedPreferences preferences;
    String region = "";
    String findCity = "";
    View view;
    Context context;

    /*@Bind(R.id.launch)
    LinearLayout mLaunchLayout;*/
    UrlPath urlPath = UrlPath.getUrlPath();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Log.i("-------shouye",json);
            if (!json.equals("") && json != null) {
               /* MyApplication.IsFirst=false;
                mLaunchLayout.setVisibility(View.GONE);*/
                proBar.setVisibility(View.INVISIBLE);
                yjj_srfl.setRefreshing(false);
                yujiajiaBean = GsonTools.getObject(json, YujiajiaBean.class);
                if (!yujiajiaBean.getCode().equals("-1")) {
                    if (yujiajiaBean != null && yujiajiaBean.getData() != null) {
                        yujia = yujiajiaBean.getData();
                        init();
                        init2();
                        init3();
                        initMsg();
                    }
                } else {
                    Toast.makeText(getActivity(),yujiajiaBean.getMessage(),Toast.LENGTH_SHORT).show();
                /*MyApplication.IsFirst=true;
                mLaunchLayout.setVisibility(View.VISIBLE);*/
                }
            }
        }
    };
    YujiajiaBean yujiajiaBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_yujiajia, null, false);

        ButterKnife.bind(this, view);
        title = (TextView) view.findViewById(R.id.title);
        viewPager = (ViewPager) view.findViewById(R.id.vp);

        initView();
       Log.i("-------------ssssssss",urlPath.getUrl());
        return view;
    }

    List<String> mMsg;

    private void initMsg() {
        msgs = yujia.getMsg();
        if (msgs != null && msgs.size() > 0) {
            for (int i = 0; i < msgs.size(); i++) {
                mMsg.add(i, msgs.get(i).getContent());
            }
            verticalLampLayout.setData(mMsg);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(new ViewPagerTask(), 1, 4, TimeUnit.SECONDS);
        verticalLampLayout.startRun();
    }

    @Override
    public void onStop() {
        super.onStop();
        scheduled.shutdown();
        verticalLampLayout.stopRun();
    }
    String yh_type;
    private void initView() {
        preferences = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region = preferences.getString("region", "");           //地区
        findCity = preferences.getString("findCity", "");
        reqid = preferences.getString("reqid", "");
        yh_type = preferences.getString("yh_type","");
        urlPath.getData(yh_type);


        //Log.i("aaaaaaxx---------------",urlPath.getData(yh_type));
        scroll.smoothScrollTo(0,0);
        scroll.setFocusable(true);

        if (!("").equals(region) && region != null) {
            if (!("").equals(findCity) && findCity != null) {
                bt_find.setText(findCity);
               /* bt_location.setVisibility(View.GONE);*/
            } else {
                bt_find.setText(region+" ");
               /* bt_location.setVisibility(View.GONE);*/
            }
        } else {
           /* bt_location.setVisibility(View.VISIBLE);*/
            initLocation();
            Toast.makeText(context, "正在定位...", Toast.LENGTH_SHORT).show();
        }

        advertisingImages = new ArrayList<AdvertisingImages>();
        msgs = new ArrayList<Msg>();
        mMsg = new ArrayList<String>();
        trainerses = new ArrayList<>();
        stadiums = new ArrayList<>();

        yujia = new Data();
        listener = new MyPageChangeListener();
        proBar.setVisibility(View.VISIBLE);
        yjj_srfl.setColorSchemeResources(R.color.firebrick,
                R.color.gold, R.color.lavender, R.color.aqua);
        yjj_srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

        init4();
        initLinster();
    }

    private AMapLocationClient mLocationClient;

    private void initLocation() {
        mLocationClient = new AMapLocationClient(context);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    Log.i("info", aMapLocation.getErrorCode() + "");
                    if (aMapLocation.getErrorCode() == 0) {
                        String region = aMapLocation.getCity();
                        String lat = String.valueOf(aMapLocation.getLatitude());
                        String lon = String.valueOf(aMapLocation.getLongitude());
                        SharedPreferences sp = context.getSharedPreferences(MyApplication.SpName,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("region", region);
                        editor.putString("findCity", region);
                        editor.putString("latitude", lat);
                        editor.putString("longitude", lon);
                        editor.commit();
                        //  bt_location.setImageResource(R.mipmap.dw);
                       /* bt_location.setVisibility(View.GONE);*/
                        bt_find.setText(region);
                        initLinster();
                    } else {
                        //   bt_location.setImageResource(R.mipmap.cancel);
                        ToastUtils.showToast(context, "定位失败");
                        Log.i("info", "error:" + aMapLocation.getErrorInfo());

                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void initLinster() {
        urlPath.getData(yh_type);
        //这样有必要吗?
        String city = viewUtils.subString(bt_find.getText().toString());
        String citys = viewUtils.corpString(city);
        String url = urlPath.getUrl()+urlPath.ZY_URL.replace("region=x", "region=" + citys);
        Log.i("--------yujiajia---",url);
        String a = url.replaceAll("reqid=x", "reqid=" + reqid);

      /*  if (MyApplication.IsFirst){
            mLaunchLayout.setVisibility(View.VISIBLE);
        }else {
            mLaunchLayout.setVisibility(View.INVISIBLE);
        }*/

        new ThreadUtils_no(context, a, hand).start();

    }

    /**
     * 为按钮添加点击事件
     */
    private void init4() {
        bt_locations.setOnClickListener(this);      //瑜伽伽页面左上角图标和地点名
        bt_jifen.setOnClickListener(this);          //换积分的布局
        bt_yktz.setOnClickListener(this);           //优惠券的布局
        rl_imageButton2.setOnClickListener(new View.OnClickListener() {//查看更多
            @Override
            public void onClick(View v) {   //风云排行榜右侧箭头
                context.startActivity(new Intent(context, FindMoreActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("region", bt_find.getText().toString()));
            }
        });
        rl_imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //场馆推荐右侧箭头
                context.startActivity(new Intent(context, FindMoreCgActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra("region", bt_find.getText().toString()));
            }
        });

    }

    /**
     * 场馆推荐
     */

    private List<Stadiums> stadiums;
    @Bind(R.id.cg_lv)
    ListView listView;
    CgAdapter Adapter;

    @Bind(R.id.scroll)
    ScrollView scroll;

    private void init3() {
        stadiums = yujia.getStadiums();
        Adapter = new CgAdapter(context, stadiums,0);
        listView.setAdapter(Adapter);
        viewUtils.setListViewHeightBasedOnChildren(listView);
        scroll.smoothScrollTo(0,0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, CgxqActivity.class);
                intent.putExtra("id", stadiums.get(i).getSp_id());
                intent.putExtra("activity_tag", "");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Activity activity = (Activity) context;
                activity.startActivity(intent);

            }
        });
    }

    /**
     * 风云排行榜
     */
    @Bind(R.id.fragment_yujiajia_grid)
    GridView gridView;
    List<Trainers> trainerses;
    int itemWidth;

    private void init2() {
        setGridView();

    }
    GridViewAdapter Gridadapter;
    private void setGridView() {

        if (trainerses!=null&&trainerses.size()>0&&Gridadapter!=null){
            Gridadapter.notifyDataSetChanged();
            trainerses.clear();
        }
        if (yujia.getTrainers()!=null&&yujia.getTrainers().size()>0){
            trainerses.addAll(yujia.getTrainers());
        }

        if (trainerses!=null&&trainerses.size()>0){
            int size = trainerses.size();

            int length = 145;

            DisplayMetrics dm = new DisplayMetrics();

            // getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            float density = dm.density;
            int gridviewWidth = (int) (size * (length + 4) * density);
            itemWidth= (int) (length * density);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
            gridView.setLayoutParams(params); // 重点
            gridView.setColumnWidth(itemWidth); // 重点
            gridView.setHorizontalSpacing(5); // 间距
            gridView.setStretchMode(GridView.NO_STRETCH);
            gridView.setNumColumns(size); // 重点

            Gridadapter = new GridViewAdapter(context,
                    trainerses);
            gridView.setAdapter(Gridadapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context,JlxqActivity.class);
                    String id = trainerses.get(i).getTs_id();
                    intent.putExtra("id",id);
                    intent.putExtra("activity_tag","");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                }
            });
        }else {
            //  jl_rel.setVisibility(View.GONE);
        }

    }


    /**
     * 加载图片
     */
    private void init() {

        advertisingImages = yujia.getAdvertisingImages();
        if (advertisingImages != null && advertisingImages.size() > 0) {
            images = new int[advertisingImages.size()];
            titles = new String[advertisingImages.size()];
            imageSource = new ArrayList<>();
            for (int i = 0; i < advertisingImages.size(); i++) {
                titles[i] = advertisingImages.get(i).getContent();
            }
            WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            //将要显示的图片放到list集合中
            for (int i = 0; i < advertisingImages.size(); i++) {
                SimpleDraweeView image = new SimpleDraweeView(context);
                GenericDraweeHierarchy hierarchy = image.getHierarchy();
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                hierarchy.setPlaceholderImage(R.drawable.not_find);

                if (advertisingImages.get(i).getPicurl() != null && !advertisingImages.get(i).getPicurl().equals("")) {
                    image.setImageURI(Uri.parse(advertisingImages.get(i).getPicurl()));
                }
                imageSource.add(image);
            }

        }
        //获取显示的点（即文字下方的点，表示当前是第几张）
        dots = new ArrayList<View>();
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        dots.add(dot5);

        if (advertisingImages != null && advertisingImages.size() > 0) {
            for (int i = 0; i < advertisingImages.size(); i++) {
                dots.get(i).setVisibility(View.VISIBLE);
            }
        }
        //图片的标题

        if (titles != null && titles.length > 0) {
            title.setText(titles[0]);
        }
        //显示图片的VIew

        //为viewPager设置适配器
        adapter = new MyPagerAdapter();
        if (adapter != null && images != null && images.length > 0) {
            viewPager.setAdapter(adapter);
        }
        //为viewPager添加监听器，该监听器用于当图片变换时，标题和点也跟着变化
        viewPager.setOnPageChangeListener(listener);


    }

    MyPageChangeListener listener;

    //	ViewPager每次仅最多加载三张图片（有利于防止发送内存溢出）
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //判断将要显示的图片是否和现在显示的图片是同一个
            //arg0为当前显示的图片，arg1是将要显示的图片

            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem==" + position);
            //销毁该图片
            container.removeView(imageSource.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            //初始化将要显示的图片，将该图片加入到container中，即viewPager中
            container.addView(imageSource.get(position));
            //给该图片设置点击事件
            View view = imageSource.get(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent intent = new Intent(getActivity().getApplicationContext(), EventDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("where", position + 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(getActivity(),position+"-"+v.getId(),Toast.LENGTH_LONG).show();
                    scheduled.shutdown();//跳转后要关闭，以免返回时又创建一个定时*/
                    if (advertisingImages!=null&&advertisingImages.size()>0){
                        if (advertisingImages.get(position).getDetail_type().equals("2")){
                            Intent intent = new Intent(context, CgxqActivity.class);
                            intent.putExtra("id", advertisingImages.get(position).getDetail_id());
                            intent.putExtra("activity_tag","");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Activity activity = (Activity) context;
                            activity.startActivity(intent);
                        }else if(advertisingImages.get(position).getDetail_type().equals("1")){
                            Intent intent = new Intent(context,JlxqActivity.class);
                            String id = advertisingImages.get(position).getDetail_id();
                            intent.putExtra("id",id);
                            intent.putExtra("activity_tag","");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getActivity().startActivity(intent);
                        }else if (advertisingImages.get(position).getDetail_type().equals("3")){
                            Intent intent = new Intent(context,Banner_url_Activity.class);
                            String id = advertisingImages.get(position).getDetail_id();
                            intent.putExtra("id",id);
                            intent.putExtra("activity_tag","");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getActivity().startActivity(intent);
                        }

                    }

                }
            });
            return imageSource.get(position);
        }
    }

    //监听ViewPager的变化
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            //当显示的图片发生变化之后
            //设置标题
            title.setText(titles[position]);
            //改变点的状态
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
            //记录的页面
            oldPage = position;
            currPage = position;
        }
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            //改变当前页面的值
            currPage = (currPage + 1) % images.length;
            //发送消息给UI线程
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //接收到消息后，更新页面
            viewPager.setCurrentItem(currPage);
        }
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_locations:
                Intent intent_loc = new Intent(context, CityPickerActivity.class);
                startActivityForResult(intent_loc, 1);
                break;
            case R.id.bt_jifen:
                if (!reqid.equals("")) {
                    Intent intent = new Intent(context, MyPointsActivity.class);
                    intent.putExtra("reqid", reqid);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    scheduled.shutdown();//跳转后要关闭，以免返回时又创建一个定时
                } else {
                    Toast.makeText(context, "请检查是否登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_yktz:
                startActivity(new Intent(context, YhjActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                scheduled.shutdown();//跳转后要关闭，以免返回时又创建一个定时
                break;
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                final String namString = bundle.getString("location");
                SharedPreferences sharedPreferences = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("findCity", namString);
                editor.commit();
                bt_find.setText(namString);
                initLinster();
                // bt_location.setVisibility(View.GONE);
            }
        }

    }


    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<Trainers> list;

        public GridViewAdapter(Context _context, List<Trainers> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return trainerses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView ( int position, View convertView, ViewGroup parent){

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.f_yjj_coach_list_item, parent, false);
                holder.tvList = (TextView) convertView.findViewById(R.id.tvList);
                holder.tvCode = (TextView) convertView.findViewById(R.id.tvCode);
                holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.ItemImage);
                holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Trainers item = trainerses.get(position);
            holder.tvList.setText(item.getTs_type());

            String ss = StringEscapeUtils.unescapeJava(item.getTs_name());
            holder.tvCode.setText(ss);
            holder.ratingBar.setIsIndicator(true);
            holder.ratingBar.setRating(Float.valueOf(item.getSp_lv()));
            if (item.getTs_pic() != null && !item.getTs_pic().equals("")) {
                holder.imageView.setImageURI(Uri.parse(item.getTs_pic()));
            }

            return convertView;
        }
    }

    public class ViewHolder {

        TextView tvList, tvCode;
        SimpleDraweeView imageView;
        RatingBar ratingBar;

    }
}