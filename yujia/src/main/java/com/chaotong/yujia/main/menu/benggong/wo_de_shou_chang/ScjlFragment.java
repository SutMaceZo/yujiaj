package com.chaotong.yujia.main.menu.benggong.wo_de_shou_chang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;

import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.CollectChangguanAdapter;
import com.chaotong.yujia.main.adapter.CollectJiaolianAdapter;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class ScjlFragment extends Fragment {
    View mView;
    Context mContext;

    @Bind(R.id.record_listView)
    PullableListView mListView;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout mLayout;
    View mLoadmore;
    SharedPreferences mPreference;
    String reqid="";
    String latitude="";
    String longitude="";
    int more=0;
    List<ScBean.WdscjlBean> mlist;
    CollectJiaolianAdapter mAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.xlistview, null, false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }
    private void initView() {
        mPreference=mContext.getSharedPreferences(MyApplication.SpName,Context.MODE_PRIVATE);
        reqid=mPreference.getString("reqid","");
        mlist=new ArrayList<>();
        more=0;
        if (("").equals(latitude)||("").equals(longitude)){
            initLocate(more);
        }else {
            initListener(reqid,latitude,longitude,more);
        }

        mLayout.setOnRefreshListener(new MyListener());

        mLoadmore=mLayout.getChildAt(2);
        if (mLoadmore!=null){
            mLoadmore.setVisibility(View.INVISIBLE);
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
                    initListener(reqid,latitude,longitude,more);
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
                    initListener(reqid,latitude,longitude,more);
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

    private AMapLocationClient mLocationClient;
    private void initLocate(final int more) {
        mLocationClient = new AMapLocationClient(mContext);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {

                    if (aMapLocation.getErrorCode() == 0) {
                        latitude = String.valueOf(aMapLocation.getLatitude());
                        longitude = String.valueOf(aMapLocation.getLongitude());
                        initListener(reqid,latitude,longitude,more);
                    } else {
                        Log.i("info", "errorcode:" + aMapLocation.getErrorCode() + "");
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }
    UrlPath urlPath = UrlPath.getUrlPath();
    String mUrl=urlPath.getUrl()+"WdscServlet";
    ScBean mScbean;
    private void initListener(String reqid, String latitude, String longitude, int more) {
        JSONObject object;
        String content = "";
        if (reqid != null && !reqid.equals("")) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("req_longitude", longitude);
                object.put("req_latitude", latitude);
                object.put("more", more);
                content = String.valueOf(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ThreadUtils(mContext, mUrl, content, handler).start();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson=new Gson();
            mScbean=gson.fromJson(json,ScBean.class);
            String code = mScbean.getCode();
            String message =mScbean.getMsg();
            if (("1").equals(code)) {

                init(mScbean);

            }else if(("0").equals(code)){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setTitle(getResources().getString(R.string.app_name) + "")
                        .setMessage(getResources().getString(R.string.message)+"")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sp=mContext.getSharedPreferences(MyApplication.SpName,Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putBoolean("isLogin",false);
                                editor.putString("reqid","");
                                editor.putString("type","");
                                editor.commit();
                                Intent intent = new Intent(mContext, loginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).show();
            }
            else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    private void init(ScBean mScbean) {
        if (more==0){
            if (mlist!=null&&mlist.size()>0&&mAdapter!=null){
                mlist.clear();
            }
            if (mScbean.getWdscjl()!=null&&mScbean.getWdscjl().size()>0){
                mlist.addAll(mScbean.getWdscjl());
            }
            if (mlist!=null&&mlist.size()>0){
                mAdapter=new CollectJiaolianAdapter(mContext,mlist);
                mListView.setAdapter(mAdapter);
                mLoadmore.setVisibility(View.VISIBLE);
            }else {
                mLoadmore.setVisibility(View.INVISIBLE);
            }
        }else {
            if (mScbean.getWdscjl()!=null&&mScbean.getWdscjl().size()>0){
                mlist.addAll(mScbean.getWdscjl());
                mAdapter.notifyDataSetChanged();
                mLoadmore.setVisibility(View.VISIBLE);
            }
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,JlxqActivity.class);
                String id = mlist.get(i).getTrainerid();
                intent.putExtra("id",id);
                intent.putExtra("activity_tag","");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }
        });


    }
}
