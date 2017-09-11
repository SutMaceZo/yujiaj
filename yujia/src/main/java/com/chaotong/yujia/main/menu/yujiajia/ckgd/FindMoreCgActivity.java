package com.chaotong.yujia.main.menu.yujiajia.ckgd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;

import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Order.OrderCg;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.CgAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.CgAdapter2;
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
 * 查看更多教练
 */

public class FindMoreCgActivity extends BaseActivity {

    @Bind(R.id.refresh_view)
     PullToRefreshLayout layout;
    @Bind(R.id.record_listView)
    PullableListView listview;//发现更多

    View loadmore;

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView back;

    Intent intent;
    int more = 0;

    OrderCg CgBean;
    List<Stadiums> list;

    CgAdapter adapter;

    SharedPreferences sp;
    String region;
    UrlPath urlPath = UrlPath.getUrlPath();
    String fMuRL = urlPath.getUrl() + "StadiumsServlet?";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if (json != null) {

                Gson gson = new Gson();
                CgBean = gson.fromJson(json, OrderCg.class);
                String code = CgBean.getCode();
                String mesage = CgBean.getMsg();
                if (code != null && code.equals("1")) {
                    init1();
                } else {
                    ToastUtils.showToast(FindMoreCgActivity.this, mesage);
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fmcg);
        ButterKnife.bind(this);
        initView();

    }

    String longitude = "";
    String latitude = "";

    private void initView() {
        sp = getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        latitude = sp.getString("latitude", "");
        longitude = sp.getString("longitude", "");
        list = new ArrayList<>();
        intent = getIntent();
        region = intent.getStringExtra("region");

        layout.setOnRefreshListener(new MyListener());

        loadmore=layout.getChildAt(2);
        if (loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        more = 0;
        initListener(more);

    }



    private void init1() {

        if (more == 0) {
            if (list!=null&&list.size()>0&&adapter!=null){
                list.clear();
                adapter.notifyDataSetChanged();
            }
            if (CgBean.getStadiums() != null && CgBean.getStadiums().size() > 0) {
                    list.addAll(CgBean.getStadiums());
                }
            if (list!=null&&list.size()>0){
                adapter=new CgAdapter(FindMoreCgActivity.this,list,1);
                listview.setAdapter(adapter);
                loadmore.setVisibility(View.VISIBLE);
            }else {
                loadmore.setVisibility(View.INVISIBLE);
            }

            } else if (more > 0) {
                if (CgBean.getStadiums() != null && CgBean.getStadiums().size() > 0) {
                    for (int i = 0; i < CgBean.getStadiums().size(); i++) {
                        Stadiums bean = CgBean.getStadiums().get(i);
                        list.add(bean);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FindMoreCgActivity.this,CgxqActivity.class);
                intent.putExtra("id",list.get(i).getSp_id());
                intent.putExtra("activity_tag","");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void initListener(final int more) {
        String city = viewUtils.Region(region);
        String url = "";
        try {
            url = fMuRL + "region=" + URLEncoder.encode(city, "UTF-8") +
                    "&more=" + more +
                    "&longitude=" + longitude +
                    "&latitude=" + latitude;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new ThreadUtils_no(FindMoreCgActivity.this, url, handler).start();

    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    more=0;
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

}
