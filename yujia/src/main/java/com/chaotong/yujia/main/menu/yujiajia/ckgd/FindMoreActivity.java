package com.chaotong.yujia.main.menu.yujiajia.ckgd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.ckgd.view.CkgdTranAdapter;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.property.yujiajia.Ckgd;
import com.chaotong.yujia.main.property.yujiajia.Trainers;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.viewUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 查看更多教练
 */

public class FindMoreActivity extends BaseActivity implements View.OnClickListener {

    private CkgdTranAdapter adapter;
    private List<Trainers> trainers;

    @Bind(R.id.rl_ckgd_bt_back)
    RelativeLayout rl_ckgd_bt_back;
    @Bind(R.id.ckgd_bt_back)
    ImageView ckgd_bt_back;//返回

    private String region;

    @Bind(R.id.refresh_view)
     PullToRefreshLayout prfl;
    @Bind(R.id.record_listView)
     ListView find_more_tran_listview;//发现更多

    View loadmore;

    @Bind(R.id.progress)
     ProgressBar progressBar;

    Intent intent;
    private int more = 0;
    private int position = 0;
    Ckgd ckgd;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (!json.equals("") && json!=null){
                Log.i("jiaolian------------",json);
                progressBar.setVisibility(View.INVISIBLE);
                prfl.refreshFinish(PullToRefreshLayout.SUCCEED);
                Gson gson = new Gson();
                ckgd = gson.fromJson(json, Ckgd.class);
                init1();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_more_transtor);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rl_ckgd_bt_back.setOnClickListener(this);
        trainers = new ArrayList<>();
        intent = getIntent();
        region = intent.getStringExtra("region");
        progressBar.setVisibility(View.VISIBLE);
        more = 0;
        initListener(more);

        prfl.setOnRefreshListener(new MyListener());

        loadmore = prfl.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    private void init1() {

        if (more == 0) {
            if (trainers != null && trainers.size() > 0 && adapter != null) {
                trainers.clear();
                adapter.notifyDataSetChanged();
            }
            if (ckgd.getTrainers() != null && ckgd.getTrainers().size() > 0) {
                trainers.addAll(ckgd.getTrainers());
            }
            if (trainers != null && trainers.size() > 0) {
                loadmore.setVisibility(View.VISIBLE);
                adapter = new CkgdTranAdapter(FindMoreActivity.this, trainers);
                find_more_tran_listview.setAdapter(adapter);
            } else {
                loadmore.setVisibility(View.INVISIBLE);
            }


        } else if (more > 0) {
            if (ckgd.getTrainers() != null && ckgd.getTrainers().size() > 0) {
                for (int i = 0; i < ckgd.getTrainers().size(); i++) {
                    Trainers bean = ckgd.getTrainers().get(i);
                    trainers.add(bean);
                }
                adapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }
        find_more_tran_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FindMoreActivity.this,JlxqActivity.class);
                String id = trainers.get(i).getTs_id();
                intent.putExtra("id",id);
                intent.putExtra("activity_tag","");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });


    }
    UrlPath urlPath = UrlPath.getUrlPath();
    private void initListener(final int more) {
        String city = viewUtils.Region(region);
        String url = null;
        try {
            url = urlPath.getUrl()+ urlPath.ZY_FIND_MORE_TRANTERS.replace("more=x", "more=" + more).replace("region=x", "region=" + URLEncoder.encode(city, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
        new ThreadUtils_no(FindMoreActivity.this,url,handler).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_ckgd_bt_back:
                finish();
                break;
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
                    initListener(more);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }

    @Bind(R.id.loadstate_tv)
     TextView loadstate_tv;

}
