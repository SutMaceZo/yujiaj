package com.chaotong.yujia.main.menu.benggong.wo_de_ke_cheng;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.WdkcAdapter;
import com.chaotong.yujia.main.adapter.YuYueAdapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.HanGouBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.Hg_list;
import com.chaotong.yujia.main.entity.HuiYuanBean.YuYueBean;
import com.chaotong.yujia.main.entity.JiaoLianBean.Wdkc;
import com.chaotong.yujia.main.entity.JiaoLianBean.WdkcBean;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/15 0015.
 * 我的课程界面
 * */

public class WdkcActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.record_listView)
     PullableListView mlistview;
     String reqid = "";
    String content = "";
     WdkcAdapter adapter;

     Wdkc wdkc;
     List<WdkcBean> mlist;

    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    String url = urlPath.getUrl() + "WdkcServlet";
    String url1 = urlPath.getUrl() + "WdkcServlet";
    int more=0;
    @Bind(R.id.refresh_view)
     PullToRefreshLayout refresh_view;
    View loadmore;

     Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if(!json.equals("")&&json!=null) {
                Log.i("wodekecheng----------", json);
                Gson gson = new Gson();
                wdkc = gson.fromJson(json, Wdkc.class);
                String code = wdkc.getCode();
                String message = wdkc.getMsg();
                if (code != null && message != null) {
                    if (code.equals("1")) {
                        if (mlist != null && mlist.size() > 0 && adapter != null) {
                            mlist.clear();
                            adapter.notifyDataSetChanged();
                        }
                        mlist = wdkc.getLwdkc();
                        init();
                    } else if (code.equals("0")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WdkcActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle(getResources().getString(R.string.app_name) + "")
                                .setMessage(getResources().getString(R.string.message) + "")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferences sp = getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putBoolean("isLogin", false);
                                        editor.putString("reqid", "");
                                        editor.putString("type", "");
                                        editor.commit();
                                        Intent intent = new Intent(WdkcActivity.this, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();

                    } else {
                        Toast.makeText(WdkcActivity.this, wdkc.getMsg(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WdkcActivity.this, getResources().getString(R.string.error),
                            Toast.LENGTH_SHORT).show();
                }
            }
            super.handleMessage(msg);
        }
    };

    public  void init() {
        if (mlist!=null&&mlist.size()>0){
            adapter = new WdkcAdapter(mlist, this, reqid);
            mlistview.setAdapter(adapter);
            loadmore.setVisibility(View.VISIBLE);
        }else
        {
            loadmore.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdkc);
        ButterKnife.bind(this);
        initView();
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public   void initView() {
        mlist = new ArrayList<WdkcBean>();
        reqid = getIntent().getStringExtra("reqid");
        url=url+"?reqid="+reqid+"&more="+more;
        Log.i("jiaolianurl------",url);
        JSONObject object;
        if (!("").equals(reqid)) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("more", 0);
                content = String.valueOf(object);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            new ThreadUtils(WdkcActivity.this, url, content, handler).start();
        } else {
            Toast.makeText(WdkcActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
        }

        loadmore=refresh_view.getChildAt(2);
        if (loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }
        refresh_view.setOnRefreshListener(new MyListener());
        mlistview.setOnItemClickListener(this);
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    JSONObject object;
                    if (!("").equals(reqid)) {
                        try {
                            object = new JSONObject();
                            object.put("reqid", reqid);
                            object.put("more", 0);
                            content = String.valueOf(object);
                        } catch (JSONException e) {
                           // e.printStackTrace();
                        }
                        url=url+"?reqid="+reqid+"&more=0";
                        new ThreadUtils(WdkcActivity.this, url, content, handler).start();
                    } else {
                        Toast.makeText(WdkcActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    // 千万别忘了告诉控件刷新完毕了哦！
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
                    more++;
                    JSONObject object;
                    if (!("").equals(reqid)) {
                        try {
                            object = new JSONObject();
                            object.put("reqid", reqid);
                            object.put("more", more);
                            content = String.valueOf(object);
                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                        url=url+"?reqid="+reqid+"&more="+more;
                        new ThreadUtils(WdkcActivity.this, url1, content, xhandler).start();
                    } else {
                        Toast.makeText(WdkcActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }


     Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            wdkc = gson.fromJson(json, Wdkc.class);
            String code = wdkc.getCode();
            if (code.equals("1")&&code.equals("0")) {
                if (!wdkc.getLwdkc().equals("")) {
                    for (int i = 0; i < wdkc.getLwdkc().size(); i++) {
                        WdkcBean bean = wdkc.getLwdkc().get(i);
                        mlist.add(bean);
                    }
                    adapter.notifyDataSetChanged();
                    loadmore.setVisibility(View.VISIBLE);
                }

            } else {
                Toast.makeText(WdkcActivity.this, wdkc.getMsg(), Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(WdkcActivity.this, KcDetailActivity.class);
        intent.putExtra("reqid", reqid);
        intent.putExtra("classid", mlist.get(i).getClassid());
        startActivity(intent);
    }
}
