package com.chaotong.yujia.main.menu.xiulian.main.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.OrderJlAdapterAdapter;
import com.chaotong.yujia.main.entity.Order.OrderJl;
import com.chaotong.yujia.main.menu.xiulian.main.view.SpinerPopWindow;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
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
public class SearJlFragment extends Fragment{
   View view;
    @Bind(R.id.record_listView)
    PullableListView lv;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    View loadmore;
    @Bind(R.id.probar)
    ProgressBar progressBar;
    @Bind(R.id.tv_value)
    TextView text;
    int more=0;
    String region="";
    String longitude = "";
    String latitude = "";

    String type="教练";
    String search="";
    UrlPath urlPath = UrlPath.getUrlPath();
    String jlUrl= urlPath.getUrl()+"SearchServlet?";
    SharedPreferences sp;
    Context context;

    @Bind(R.id.jl_1)
    LinearLayout jl_1;

    OrderJl JlBean;
    List<OrderJl.TrainersBean> Trainers;
    OrderJlAdapterAdapter adapter;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            Gson gson=new Gson();
            JlBean=gson.fromJson(json,OrderJl.class);
            if (JlBean!=null) {
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                String code=JlBean.getCode();
                String message=JlBean.getMsg();
                if (code!=null&&message!=null){
                    if (("1").equals(code)||("0").equals(code)){
                        init(JlBean);
                    }else {
                        ToastUtils.showToast(context,message);
                    }
                }else {
                    ToastUtils.showToast(context,message);
                }
            }
        }
    };
    private void init(OrderJl jlBean) {
            initT(jlBean);

    }

    private void initT(OrderJl Jl) {
        if (more == 0) {
            if (Trainers!=null&&Trainers.size()>0&&adapter!=null){
                Trainers.clear();
                adapter.notifyDataSetChanged();
            }
            if (Jl.getTrainers()!=null&&Jl.getTrainers().size()>=0){
                Trainers.addAll(Jl.getTrainers());
            }

            if (Trainers != null && Trainers.size() > 0) {
                adapter = new OrderJlAdapterAdapter(context,Trainers);
                lv.setAdapter(adapter);
                loadmore.setVisibility(View.VISIBLE);

            }
        } else {

            if (adapter != null && Trainers!= null) {
                Trainers.addAll(Jl.getTrainers());
                adapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,JlxqActivity.class);
                Bundle bundle = new Bundle();
                String id = Trainers.get(i).getTs_id();
                intent.putExtra("id",id);
                intent.putExtra("activity_tag","");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.jlft,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    Boolean reflash=false;
    @Override
    public void onPause() {
        super.onPause();
        reflash=true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (reflash){
            SharedPreferences   sp1=context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
            region=sp1.getString("findCity","");
            latitude = sp1.getString("latitude", "");
            longitude = sp1.getString("longitude", "");
            initListener(0);
            reflash=false;
        }
    }

    private void initView() {
        jl_1.setVisibility(View.GONE);
        Trainers=new ArrayList<>();
        sp=getContext().getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region=sp.getString("findCity","");
        latitude = sp.getString("latitude", "");
        longitude = sp.getString("longitude", "");
        search=getArguments().getString("search");

        if (("").equals(latitude)||("").equals(longitude)) {
            initLocation();
        }else {
            initListener(more);
        }

      /*  more=0;
        initListener(more);*/

        layout.setOnRefreshListener(new MyListener());
        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
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


    private void initListener(int more) {
        String city1=viewUtils.subString(region);
        String city= viewUtils.corpString(city1);

        if (search!=null&&!search.equals("")){
            String url= null;
          /*  url = jlUrl+"search="+search+"&more="+more+"&type="+type+"&region="+city+"&longitude=" + longitude +
                    "&latitude=" + latitude;*/
       try {
            url = jlUrl+"search="+URLEncoder.encode(search,"UTF-8")+"&more="+more+"&type="+URLEncoder.encode(type,"UTF-8")+"&region="+URLEncoder.encode(city,"UTF-8")+"&longitude=" + longitude +
                   "&latitude=" + latitude;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
            new ThreadUtils_no(getContext(),url,handler).start();

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
                    more = 0;
                    initListener(more);
                    // 千万别忘了告诉控件刷新完毕了哦！

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



