package com.chaotong.yujia.main.menu.xiulian.main.base;

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
import com.chaotong.yujia.main.menu.xiulian.main.adapter.SearchCgAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.view.SearchCgBean;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
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

public class SearCgFragment extends Fragment {
    View view;

    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    @Bind(R.id.record_listView)
    PullableListView listview;

    @Bind(R.id.probar)
    ProgressBar pb;
    Context context;
    UrlPath urlPath = UrlPath.getUrlPath();
    String cgUrl = urlPath.getUrl() + "SearchServlet?";

    int more = 0;
    String region = "";
    String longitude = "";
    String latitude = "";
    String type="场馆";
    String search="";

    SharedPreferences sp;
    SearchCgBean CgBean;
    List<SearchCgBean.StadiumsBean> list;
    SearchCgAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Gson gson = new Gson();
            CgBean = gson.fromJson(json, SearchCgBean.class);
            if (CgBean != null) {
               /* pb.setVisibility(View.INVISIBLE);*/
                String code = CgBean.getCode();
                String message = CgBean.getMsg();
                if (code != null&&message!=null) {
                    if (("0").equals(code)||("1").equals(code)){
                        if (CgBean.getStadiums()!=null&&CgBean.getStadiums().size()>0){
                            init(CgBean);
                        }else{
                           // Toast.makeText(getActivity(),"查无记录",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        viewUtils.ToastUtils(getActivity(), message);
                    }
                } else {
                    viewUtils.ToastUtils(getActivity(), message);
                }
            }
        }
    };

    private void init(SearchCgBean CgBean) {
        if (more == 0) {
            list = CgBean.getStadiums();
            if (list != null && list.size() > 0) {
                adapter = new SearchCgAdapter(getContext(), list);
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
                Intent intent = new Intent(context,CgxqActivity.class);
                intent.putExtra("id",list.get(i).getSp_id());
                intent.putExtra("activity_tag","");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cgft, container, false);
        ButterKnife.bind(this, view);
        initView();
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
        reflash = true;
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
        search=getArguments().getString("search");

        if (("").equals(latitude)||("").equals(longitude)) {
            initLocation();
        }else {
            initListener(more);
        }
        layout.setOnRefreshListener(new MyListener());
        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }


    private void initListener(int more) {
       /* pb.setVisibility(View.VISIBLE);*/
        String c=viewUtils.subString(region);
        String city = viewUtils.corpString(c);
        Log.i("info", "initListener:" + latitude
                + " " + longitude
                + " " + region);
        if (search!=null&&!search.equals("")){
            String url = null;
            try {
                url = cgUrl +"search="+URLEncoder.encode(search, "UTF-8")+
                        "&more="+more+"&type="+URLEncoder.encode(type, "UTF-8")+"&region=" + URLEncoder.encode(city, "UTF-8") +
                        "&longitude=" + longitude +
                        "&latitude=" + latitude;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

           /* url = cgUrl +"search="+search+
                    "&more="+more+"&type="+type+"&region=" + city +
                    "&longitude=" + longitude +
                    "&latitude=" + latitude;*/
            new ThreadUtils_no(context, url, handler).start();
        }else {
            ToastUtils.showToast(context,"请输入查询条件");
        }

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
