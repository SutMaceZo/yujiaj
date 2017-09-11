package com.chaotong.yujia.main.menu.yujiajia.yhj.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.property.yujiajia.Yhj;
import com.chaotong.yujia.main.property.yujiajia.YhjLq;
import com.chaotong.yujia.main.property.yujiajia.YhjPro;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/*优惠卷
*
*
* */
public class YhjActivity extends BaseActivity implements View.OnClickListener, YhjAdapter.calledBack {
    private ListView yhj_lv;
    RelativeLayout rl_bt_back_yhj;
    private ImageView bt_back_yhj;//返回
    private YhjAdapter yhjAdapter;

    private Yhj yhj;
    private String yhj_id;
    private String dh_code;
    private String jf;
    private YhjLq yhjLq;
    private int position;

    ProgressBar progressBar;


    private List<YhjPro> mlist;

    private SharedPreferences preferences;
    private  String reqid;


    private PullToRefreshLayout ptrf;

    private Handler  handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (!json.equals("") && json!=null){
                Log.i("youhui----------",json);
                Gson gson = new Gson();
                yhj = new Yhj();
                yhj = gson.fromJson(json, Yhj.class);
                if(more==0){
                    mlist = yhj.getYhjs();
                }else {
                    if(yhj.getYhjs().size()>0&&yhj.getYhjs()!=null){
                        for (int i=0;i<yhj.getYhjs().size();i++){
                            YhjPro bean=yhj.getYhjs().get(i);
                            mlist.add(bean);
                        }
                    }
                }
                setListView();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yhj);
        rl_bt_back_yhj = (RelativeLayout) findViewById(R.id.rl_bt_back_yhj);
        yhj_lv = (ListView) findViewById(R.id.record_listView);
        bt_back_yhj = (ImageView) findViewById(R.id.bt_back_yhj);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        preferences = getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        reqid= preferences.getString("reqid", "defaultname");
        rl_bt_back_yhj.setOnClickListener(this);
        ptrf= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        ptrf.setOnRefreshListener(new MyListener());
        mlist=new ArrayList<YhjPro>();
        initListener(0);

    }
    private void setListView() {
        if (mlist != null) {
            Context context = YhjActivity.this;
            yhjAdapter = new YhjAdapter(context, mlist, this);
            yhj_lv.setAdapter(yhjAdapter);
        }
    }
    UrlPath urlPath1 = UrlPath.getUrlPath();
    private void initListener(final int more) {

        String urlPath = urlPath1.getUrl()+ urlPath1.YY_YHJLQ;
        SharedPreferences preferences = getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        String reqid = preferences.getString("reqid", "");
        String urlP = urlPath.replace("reqid=x", "reqid=" + reqid).replace("more=x", "more=" + more);
        Log.i("urlP----------",urlP);
        new ThreadUtils_no(YhjActivity.this,urlP,handler).start();

    }
    int more=0;

    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 下拉刷新操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    more=0;
                    initListener(more);
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
                    initListener(more);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }

    @Override
    public void clickOk(View v) {
        if (v.getTag() != null) {
            int position = (int) v.getTag();
            yhj_id = yhj.getYhjs().get(position).getYhj_id();
            dh_code = yhj.getYhjs().get(position).getDh_code();
            jf = yhj.getYhjs().get(position).getJf();
            if(reqid.equals("defaultname") || reqid.equals("")){
                Toast.makeText(YhjActivity.this,"请先登录，再领取优惠卷",Toast.LENGTH_SHORT).show();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                String urlPath = urlPath1.getUrl()+ urlPath1.YY_YHJDJ;
                String url = urlPath.replace("reqid=x", "reqid=" + reqid)
                        .replace("yhj_id=x", "yhj_id=" + yhj_id)
                        .replace("dh_code=x", "dh_code=" + dh_code)
                        .replace("jf=x", "jf=" + jf);

                new ThreadUtils_no(YhjActivity.this,url,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String json=msg.obj.toString();
                        if (json!=null){
                            progressBar.setVisibility(View.INVISIBLE);
                            Gson gson = new Gson();
                            yhjLq = new YhjLq();
                            yhjLq = gson.fromJson(json, YhjLq.class);
                            Toast.makeText(YhjActivity.this, yhjLq.getGetyhj_msg(), Toast.LENGTH_SHORT).show();
                            if (mlist!=null&&mlist.size()>0){
                                mlist.clear();
                            }
                            initListener(more);
                        }
                    }
                }).start();

            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bt_back_yhj:
                finish();
                break;
        }
    }
}
