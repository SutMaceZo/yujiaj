package com.chaotong.yujia.main.menu.yujiajia.record;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.RecordAdapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.HanGouBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.Hg_list;

import com.chaotong.yujia.main.thread.ThreadUtils_no;

import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;

import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RecordActivity extends BaseActivity {
    @Bind(R.id.record_listView)
    PullableListView listView;
    String reqid = "";
    UrlPath urlPath = UrlPath.getUrlPath();
    String url = urlPath.getUrl() + "HgDetailServlet?";
    HanGouBean hanGouBean;
    List<Hg_list> hg_list;
    private PullToRefreshLayout ptrl;
    private boolean isFirstIn = true;

    private RelativeLayout xx;
    private RelativeLayout yy;
    private RecordAdapter adapter;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;

    View loadmore;
    int more=0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            hanGouBean = gson.fromJson(json, HanGouBean.class);
            String code=hanGouBean.getCode();
            if(code.equals("1")){
                if (hg_list!=null&&hg_list.size()>0&&adapter!=null){
                    hg_list.clear();
                    adapter.notifyDataSetChanged();
                }
                if (hanGouBean.getHg_list()!=null&&hanGouBean.getHg_list().size()>0){
                    hg_list.addAll(hanGouBean.getHg_list());
                }

                init();
            }else {
                Toast.makeText(RecordActivity.this,hanGouBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            hanGouBean = gson.fromJson(json, HanGouBean.class);
            String code=hanGouBean.getCode();
            if(code.equals("1")){
                if(hanGouBean.getHg_list()!=null&&hanGouBean.getHg_list().size()>0){
                    for(int i=0;i<hanGouBean.getHg_list().size();i++){
                        Hg_list bean=hanGouBean.getHg_list().get(i);
                        hg_list.add(bean);
                    }
                    adapter.notifyDataSetChanged();
                    loadmore.setVisibility(View.VISIBLE);
                }
            }else {
                Toast.makeText(RecordActivity.this,hanGouBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        ButterKnife.bind(this);
        initView();

        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initView() {
        reqid = getIntent().getStringExtra("reqid");
        hg_list=new ArrayList<>();
        if (!("").equals(reqid)) {
            url = url + "reqid=" + reqid+"&more="+0;
            Log.i("result", "url:" + url);
            new ThreadUtils_no(RecordActivity.this, url, handler).start();
        }   else {
            Toast.makeText(RecordActivity.this,"请登录",Toast.LENGTH_SHORT).show();
        }
        ptrl = ((PullToRefreshLayout)findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new MyListener());

        loadmore=ptrl.getChildAt(2);
        if(loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    private void init() {
        if (hg_list!=null&&hg_list.size()>0){
            adapter=new RecordAdapter(RecordActivity.this,hg_list);
            listView.setAdapter(adapter);
            loadmore.setVisibility(View.VISIBLE);
        }else {
            loadmore.setVisibility(View.INVISIBLE);
        }

    }
    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {

                new ThreadUtils_no(RecordActivity.this, url, handler).start();

            // 下拉刷新操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

                }
            }.sendEmptyMessageDelayed(0, 2000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 加载操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    more++;
                    url=url.replace("more=0","more="+more);
                    new ThreadUtils_no(RecordActivity.this, url, xhandler).start();
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }


}
