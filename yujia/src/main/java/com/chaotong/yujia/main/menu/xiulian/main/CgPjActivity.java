package com.chaotong.yujia.main.menu.xiulian.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.PjAdapter;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.property.yujiajia.Cgpj;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/22.
 */
public class CgPjActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.pj_listview)
    PullableListView listView;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    PjAdapter pjAdapter;
    @Bind(R.id.rl_back)
    RelativeLayout rl_back;
    @Bind(R.id.back)
    ImageView pj_bt_back;
    @Bind(R.id.probar)
    ProgressBar progress;

    View loadmore;

    UrlPath urlPath = UrlPath.getUrlPath();
    Cgpj mPjBean;
    int more = 0;
    String cg_id = "";
    String mPjUrl= urlPath.getUrl()+"StadiumsPjServlet?";
    Intent intent;
    List list;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (json!=null){
                progress.setVisibility(View.INVISIBLE);
                Gson gson = new Gson();
                mPjBean = gson.fromJson(json, Cgpj.class);
                String code=mPjBean.getCode();
                String message=mPjBean.getMsg();
                if (code!=null&&code.equals("1")){
                    init();
                }else {
                    ToastUtils.showToast(CgPjActivity.this,message);
                }

            }

        }
    };

    private void init() {
        if (more==0){
            if (list!=null&&list.size()>0&&pjAdapter!=null){
                list.clear();
                pjAdapter.notifyDataSetChanged();
            }
            if (mPjBean.getStadiumspj()!=null&&mPjBean.getStadiumspj().size()>=0){

                list.addAll(mPjBean.getStadiumspj());
            }
            if (list!=null&&list.size()>0&&list!=null){
                pjAdapter=new PjAdapter(CgPjActivity.this,list,"cg");
                listView.setAdapter(pjAdapter);
                loadmore.setVisibility(View.VISIBLE);
            }else {
                loadmore.setVisibility(View.INVISIBLE);
            }
        }else {
            if (list!=null&&pjAdapter!=null){
                list.addAll(mPjBean.getStadiumspj());
                pjAdapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pj);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        rl_back.setOnClickListener(this);
        initListener(more);
        layout.setOnRefreshListener(new MyListener());

        list=new ArrayList();

        intent=getIntent();
        cg_id=intent.getStringExtra("cg_id");
        more=0;
        initListener(more);
        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    private void initListener(final int a) {
        progress.setVisibility(View.VISIBLE);
        getJllb(a);

    }

    private void getJllb(int more) {
        String url = mPjUrl+"stadiumid="+cg_id+"&more="+more;
        new ThreadUtils_no(CgPjActivity.this,url,handler).start();
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
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;

        }

    }
}
