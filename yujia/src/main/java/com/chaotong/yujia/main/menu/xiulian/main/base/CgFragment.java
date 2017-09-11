package com.chaotong.yujia.main.menu.xiulian.main.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Order.OrderCg;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.CgAdapter;

import com.chaotong.yujia.main.menu.xiulian.main.adapter.CgAdapter2;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.CgxqActivity;
import com.chaotong.yujia.main.property.yujiajia.Stadiums;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.chaotong.yujia.main.utils.viewUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/13 0013.
 */

public class CgFragment extends Fragment {
    View view;

    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    @Bind(R.id.record_listView)
    PullableListView listview;

    @Bind(R.id.probar)
    ProgressBar pb;
    Context context;
    UrlPath urlPath = UrlPath.getUrlPath();
    String cgUrl = urlPath.getUrl() + "StadiumsServlet?";

    int more = 0;
    String region = "";
    String longitude = "";
    String latitude = "";

    SharedPreferences sp;
    OrderCg CgBean;
    List<Stadiums> list;
    CgAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if(!json.equals("") && json!=null) {
                Gson gson = new Gson();
                CgBean = gson.fromJson(json, OrderCg.class);
                if (CgBean != null) {
                    pb.setVisibility(View.INVISIBLE);
                    String code = CgBean.getCode();
                    String message = CgBean.getMsg();
                    if (code != null && message != null) {
                        if (("0").equals(code) || ("1").equals(code)) {
                            init(CgBean);
                        } else {
                            viewUtils.ToastUtils(getActivity(), message);
                        }
                    } else {
                        viewUtils.ToastUtils(getActivity(), message);
                    }
                }
            }
        }
    };
    Intent intent;
    private void init(OrderCg CgBean) {
        if (more == 0) {
            list = CgBean.getStadiums();
            if (list != null && list.size() > 0) {
                adapter = new CgAdapter(getContext(), list,1);
                listview.setAdapter(adapter);
                loadmore.setVisibility(View.VISIBLE);
            }
        } else {
            if (adapter != null && CgBean.getStadiums() != null) {
                list.addAll(CgBean.getStadiums());
                adapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent intent = new Intent(context,CgxqActivity.class);
                //Toast.makeText(context,list.get(i).getSp_id()+"--"+i,Toast.LENGTH_SHORT).show();
                intent.putExtra("id",list.get(i).getSp_id());
                intent.putExtra("activity_tag","buxianshi");
                Log.e("list------------",list.toString());
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null){
            this.context = context;
        }else{
            this.context=getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cgft, container, false);
        ButterKnife.bind(this, view);
     //   initLocation();
        initView();
        intent = new Intent(context,CgxqActivity.class);
        return view;
    }

    View loadmore;

   Boolean reflash = false;

    @Override
    public void onResume() {
        super.onResume();

        if (reflash) {
            SharedPreferences sp1 = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
            region = sp1.getString("findCity", "");
            latitude = sp1.getString("latitude", "");
            longitude = sp1.getString("longitude", "");
            initLocation();
            initListener(0);
            reflash = false;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        //reflash = true;
        reflash = false;
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
                    Log.i("info", "errorcode:" + aMapLocation.getErrorCode() + "");
                    if (aMapLocation.getErrorCode() == 0) {
                        latitude = String.valueOf(aMapLocation.getLatitude());
                        longitude = String.valueOf(aMapLocation.getLongitude());
                        Log.i("info", "location:" + latitude
                                + " " + longitude);
                        pb.setVisibility(View.VISIBLE);
                        initListener(more);
                    } else {
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void initView() {
        sp = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region = sp.getString("findCity", "");
        latitude = sp.getString("latitude", "");
        longitude = sp.getString("longitude", "");
        list = new ArrayList<>();
        more = 0;

        if (("").equals(latitude)||("").equals(longitude)) {
            initLocation();
        }else {
            pb.setVisibility(View.VISIBLE);
            initListener(more);
        }
        layout.setOnRefreshListener(new MyListener());
        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }


    private void initListener(int more) {

        String city = viewUtils.Region(region);
        Log.i("info", "initListener:" + latitude
                + " " + longitude
                + " " + region);
        String url = null;
        try {
            url = cgUrl + "region=" + URLEncoder.encode(city, "UTF-8") +
                    "&more=" + more +
                    "&longitude=" + longitude +
                    "&latitude=" + latitude;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
        new ThreadUtils_no(context, url, handler).start();
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    more = 0;
                    initListener(more);
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {

            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    more++;
                    initListener(more);
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }
}
