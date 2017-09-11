package com.chaotong.yujia.main.menu.benggong.gy_gz_kc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.chaotong.yujia.main.adapter.GyGzKcAdapter;
import com.chaotong.yujia.main.adapter.WdkcAdapter;
import com.chaotong.yujia.main.adapter.YuYueAdapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.HanGouBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.Hg_list;
import com.chaotong.yujia.main.entity.HuiYuanBean.YuYueBean;
import com.chaotong.yujia.main.entity.JiaoLianBean.Wdkc;
import com.chaotong.yujia.main.entity.JiaoLianBean.WdkcBean;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/15 0015.
 */

public class GyGzKcActivity extends BaseActivity implements AdapterView.OnItemClickListener, GyGzKcAdapter.Callback {

    @Bind(R.id.record_listView)
     ListView mlistview;
    private String reqid = "";
    String content = "";
    private GyGzKcAdapter adapter;

    private Wdkc wdkc;
    private List<WdkcBean> mlist;
    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    String url = urlPath.getUrl() + "WdkcServlet";

    @Bind(R.id.refresh_view)
     PullToRefreshLayout refresh_view;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            wdkc = gson.fromJson(json, Wdkc.class);
            String code = wdkc.getCode();
            if (code.equals("1")) {
                if (mlist != null && mlist.size() > 0 && adapter != null) {
                    mlist.clear();
                    adapter.notifyDataSetChanged();
                }
                if (wdkc.getLwdkc() != null && wdkc.getLwdkc().size() > 0) {
                    mlist.addAll(wdkc.getLwdkc());
                }
                init();
            } else {
                Toast.makeText(GyGzKcActivity.this, wdkc.getMsg(),
                        Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    private void init() {
        if (mlist != null && mlist.size() > 0) {
            adapter = new GyGzKcAdapter(mlist, this, reqid, this);
            mlistview.setAdapter(adapter);
            loadmore.setVisibility(View.VISIBLE);
        } else {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    View loadmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdkc);
        ButterKnife.bind(this);
//        mlistview = (ListView) findViewById(R.id.record_listView);
//        bt_back = (Button)findViewById(R.id.bt_back);

        initView();
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private static final String TAG = "GyGzKcActivity";

    private void initView() {
        mlist = new ArrayList<WdkcBean>();
        reqid = getIntent().getStringExtra("reqid");
        JSONObject object;
        if (reqid != null && !("").equals(reqid)) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("more", 0);
                content = String.valueOf(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ThreadUtils(GyGzKcActivity.this, url, content, handler).start();
        } else {
            Toast.makeText(GyGzKcActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
        }
        Log.i("info", TAG + ":" + content);

        refresh_view.setOnRefreshListener(new MyListener());
        loadmore = refresh_view.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
        mlistview.setOnItemClickListener(this);
    }

    private int QRCODE = 01;

    @Override
    public void click(View view) {
        if (view.getTag() != null) {
            int position = (int) view.getTag();
            String classid = mlist.get(position).getClassid();
            Intent intent = new Intent(GyGzKcActivity.this, QrcodeActivity.class);
            intent.putExtra("classid", classid);
            startActivityForResult(intent, QRCODE);
        }

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
                            e.printStackTrace();
                        }
                        new ThreadUtils(GyGzKcActivity.this, url, content, handler).start();
                    } else {
                        Toast.makeText(GyGzKcActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
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
                            e.printStackTrace();
                        }
                        new ThreadUtils(GyGzKcActivity.this, url, content, xhandler).start();
                    } else {
                        Toast.makeText(GyGzKcActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }


    private int more = 0;
    private Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            wdkc = gson.fromJson(json, Wdkc.class);
            String code = wdkc.getCode();
            if (code.equals("1")) {
                if (!wdkc.getLwdkc().equals("")) {
                    for (int i = 0; i < wdkc.getLwdkc().size(); i++) {
                        WdkcBean bean = wdkc.getLwdkc().get(i);
                        mlist.add(bean);
                    }
                    adapter.notifyDataSetChanged();
                    loadmore.setVisibility(View.VISIBLE);
                }

            } else {
                Toast.makeText(GyGzKcActivity.this, wdkc.getMsg(), Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(GyGzKcActivity.this, KcDetailActivity.class);
        intent.putExtra("reqid", reqid);
        intent.putExtra("classid", mlist.get(i).getClassid());
        startActivity(intent);
    }
}
