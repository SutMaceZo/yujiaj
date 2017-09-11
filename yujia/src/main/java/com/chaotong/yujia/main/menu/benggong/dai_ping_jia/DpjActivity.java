package com.chaotong.yujia.main.menu.benggong.dai_ping_jia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.Daipingjia_Adapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.DaiPingJiaBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.Dpj;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 待评价
 */

public class DpjActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Bind(R.id.record_listView)
    ListView mlistview;
    String reqid;
    Daipingjia_Adapter adapter;
    String content = "";
    UrlPath urlPath = UrlPath.getUrlPath();
    String daipingjia_url = urlPath.getUrl() + "DpjServlet";

    Dpj dpj;
    List<DaiPingJiaBean> list;

    View loadmore;

    Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            dpj = gson.fromJson(json, Dpj.class);
            String code = dpj.getCode();
            String message = dpj.getMsg();
            if (code != null && message != null) {
                if (code.equals("1")) {
                    if (dpj.getDpj() != null) {
                        for (int i = 0; i < dpj.getDpj().size(); i++) {
                            DaiPingJiaBean bean = dpj.getDpj().get(i);
                            list.add(bean);
                        }
                        adapter.notifyDataSetChanged();
                        loadmore.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(DpjActivity.this, dpj.getMsg(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(DpjActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }


            super.handleMessage(msg);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if(!json.equals("") && json!=null) {
                Log.i("pingjia-----------",json);
                Gson gson = new Gson();
                dpj = gson.fromJson(json, Dpj.class);
                String code = dpj.getCode();
                String message = dpj.getMsg();
                if (code != null && message != null) {
                    if (code.equals("1")) {
                        init();
                    } else if (code.equals("0")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DpjActivity.this);
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
                                        Intent intent = new Intent(DpjActivity.this, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }).show();
                    } else {
                        Toast.makeText(DpjActivity.this, dpj.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DpjActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            super.handleMessage(msg);
        }
    };


    void init() {
        if (list != null && list.size() > 0 && adapter != null) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
        list = dpj.getDpj();
        if (list != null && list.size() > 0) {
            adapter = new Daipingjia_Adapter(this, list, reqid);
            mlistview.setAdapter(adapter);
            loadmore.setVisibility(View.VISIBLE);
        } else {
            loadmore.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dpj);
        ButterKnife.bind(this);
        initView();
        InitEvent();
    }

    public void InitEvent() {
        rl_bt_back.setOnClickListener(this);
    }

    @Bind(R.id.refresh_view)
    PullToRefreshLayout pullLayout;

    int more = 0;

    public void initView() {
        list = new ArrayList<DaiPingJiaBean>();
        reqid = getIntent().getStringExtra("reqid");
        JSONObject object;
        if (!("").equals(reqid)) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("more", 0);
                content = String.valueOf(object);
                Log.i("thread", content.toString());
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            new ThreadUtils(DpjActivity.this, daipingjia_url, content, handler).start();
        } else {
            Toast.makeText(DpjActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
        }
        pullLayout.setOnRefreshListener(new MyListener());
        loadmore = pullLayout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bt_back:
                finish();
                break;
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            JSONObject object;
            if (!("").equals(reqid)) {
                try {
                    object = new JSONObject();
                    object.put("reqid", reqid);
                    object.put("more", 0);
                    content = String.valueOf(object);
                    Log.i("thread", content.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new ThreadUtils(DpjActivity.this, daipingjia_url, content, handler).start();
            } else {
                Toast.makeText(DpjActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
            }
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
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

                    JSONObject object;
                    if (!("").equals(reqid)) {
                        more++;
                        try {
                            object = new JSONObject();
                            object.put("reqid", reqid);
                            object.put("more", more);
                            content = String.valueOf(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new ThreadUtils(DpjActivity.this, daipingjia_url, content, xhandler).start();
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

}
