package com.chaotong.yujia.main.menu.xiulian.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.PjAdapter;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.property.yujiajia.Jlpj;
import com.chaotong.yujia.main.property.yujiajia.Trainerspj;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/22.
 */
public class JlPjActivity extends BaseActivity implements View.OnClickListener {

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
    Jlpj mPjBean;
    int more = 0;
    String ts_id = "";
    String mPjUrl= urlPath.getUrl()+"TrainersPjServlet?";
    Intent intent;
    List<Trainerspj> mTsPj;
    List list;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (json!=null){
              //  progress.setVisibility(View.INVISIBLE);
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                Gson gson = new Gson();
                mPjBean = gson.fromJson(json, Jlpj.class);
                String code=mPjBean.getCode();
                String message=mPjBean.getMsg();
                if (code!=null&&code.equals("1")){
                    init();
                }else {
                    ToastUtils.showToast(JlPjActivity.this,message);
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
            if (mPjBean.getTrainerspj()!=null&&mPjBean.getTrainerspj().size()>=0){
               // mTsPj.addAll(mPjBean.getTrainerspj());
                list.addAll(mPjBean.getTrainerspj());
            }
            if (list!=null&&list.size()>0&&list!=null){
                pjAdapter=new PjAdapter(JlPjActivity.this,list,"jl");
                listView.setAdapter(pjAdapter);
                loadmore.setVisibility(View.VISIBLE);
            }else {
                loadmore.setVisibility(View.INVISIBLE);
            }
        }else {

            if (list!=null&&pjAdapter!=null){
                list.addAll(mPjBean.getTrainerspj());
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

       // mTsPj=new ArrayList<Trainerspj>();
        list=new ArrayList();

        intent=getIntent();
        ts_id=intent.getStringExtra("ts_id");
        more=0;
        initListener(more);
        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    private void initListener(final int a) {
      //  progress.setVisibility(View.VISIBLE);

        String url = mPjUrl+"trainerid="+ts_id+"&more="+a;
        new ThreadUtils_no(JlPjActivity.this,url,handler).start();

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
