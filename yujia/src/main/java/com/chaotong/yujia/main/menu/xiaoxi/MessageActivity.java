package com.chaotong.yujia.main.menu.xiaoxi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.property.xiaoxi.PushMsg;
import com.chaotong.yujia.main.property.xiaoxi.XiaoXi;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private PullableListView listView;

    private PullToRefreshLayout layout;

    private View loadmore;


    private String push_type;
    private String reqid;
    private XiaoxiAdapter xiaoxiAdapter;
    private List<PushMsg> pushMsgList;
    private XiaoXi xiaoXi;
    private ImageView xiaoxizx_back;
    private Intent intent;
    private RelativeLayout rl_xiaoxizx_back;
    int more=0;

    Handler  hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (!json.equals("") && json!=null){
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                Gson gson = new Gson();
                xiaoXi = gson.fromJson(json, XiaoXi.class);
                if (more==0) {
                    if (pushMsgList != null && pushMsgList.size() > 0) {
                        pushMsgList.clear();
                    }
                    if (xiaoXi.getPush_msg() != null && xiaoXi.getPush_msg().size() > 0) {
                        pushMsgList.addAll(xiaoXi.getPush_msg());
                    }
                    if (pushMsgList != null && pushMsgList.size() > 0) {
                        xiaoxiAdapter = new XiaoxiAdapter(MessageActivity.this, pushMsgList);
                        listView.setAdapter(xiaoxiAdapter);
                    }
                }else {
                    pushMsgList.addAll(xiaoXi.getPush_msg());
                    xiaoxiAdapter.notifyDataSetChanged();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rl_xiaoxizx_back = (RelativeLayout) findViewById(R.id.rl_xiaoxizx_back);
        listView = (PullableListView) findViewById(R.id.record_listView);
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        xiaoxizx_back = (ImageView) findViewById(R.id.xiaoxizx_back);
        SharedPreferences preferences = getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        reqid = preferences.getString("reqid", "defaultname");
        intent = getIntent();
        if (intent != null && intent.getStringExtra("push_type") != null) {
            push_type = intent.getStringExtra("push_type");
        }
        pushMsgList = new ArrayList<>();

        rl_xiaoxizx_back.setOnClickListener(this);
            more=0;
        initListener(more);
        layout.setOnRefreshListener(new MyListener());
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
    UrlPath urlPath = UrlPath.getUrlPath();
    private void initListener(final int more) {

        String url = urlPath.getUrl()+ urlPath.XXTS.replace("reqid=x", "reqid=" + reqid).replace("push_type=x", "push_type=" + push_type).replace("more=x", "more=" + more);
        Log.i("yueke------------",url);
        new ThreadUtils_no(MessageActivity.this,url,hand).start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_xiaoxizx_back:
                setResult(200);
                this.finish();
                break;

        }

    }
}
