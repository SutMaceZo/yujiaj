package com.chaotong.yujia.main.menu.benggong.wo_de_you_hui_juan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import com.chaotong.yujia.main.adapter.YouHuiJuanAdapter;

import com.chaotong.yujia.main.entity.HuiYuanBean.YHJ;
import com.chaotong.yujia.main.entity.HuiYuanBean.Yhj_;

import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;

import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WdyhjActivity extends BaseActivity {
    @Bind(R.id.record_listView)
     ListView listView;
    @Bind(R.id.rl_yhj_back)
    RelativeLayout rl_yhj_back;
    @Bind(R.id.yhj_back)
    ImageView bt_back;
    String reqid="";
    Yhj_ yhj_;
    YouHuiJuanAdapter adapter;
    UrlPath urlPath = UrlPath.getUrlPath();
    String url = urlPath.getUrl()+"MyYhjServlet";
     List<YHJ> mlist;

    View loadmore;

     Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json=msg.obj.toString();
            Gson gson=new Gson();
            yhj_=gson.fromJson(json,Yhj_.class);
            String code=yhj_.getCode();
            String message=yhj_.getMsg();
            if (code!=null&&message!=null) {
                    if (code.equals("1")) {
                        init();
                    }else if(code.equals("0")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(WdyhjActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle(getResources().getString(R.string.app_name) + "")
                                .setMessage(getResources().getString(R.string.message)+"")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferences sp=getSharedPreferences(MyApplication.SpName,MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sp.edit();
                                        editor.putBoolean("isLogin",false);
                                        editor.putString("reqid","");
                                        editor.putString("type","");
                                        editor.commit();
                                        Intent intent = new Intent(WdyhjActivity.this, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }).show();
                    } else {
                        Toast.makeText(WdyhjActivity.this, yhj_.getMsg(), Toast.LENGTH_SHORT).show();
                    }

            }else {
                ToastUtils.showToast(WdyhjActivity.this,getResources().getString(R.string.error));
            }


            super.handleMessage(msg);
        }
    };
     Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json=msg.obj.toString();
            Gson gson=new Gson();
            yhj_=gson.fromJson(json,Yhj_.class);
            String code=yhj_.getCode();
            if(code.equals("1")||code.equals("0")){
                if(yhj_.getYhjs()!=null){
                    for(int i=0;i<yhj_.getYhjs().size();i++){
                        YHJ bean=yhj_.getYhjs().get(i);
                        mlist.add(bean);
                    }
                    loadmore.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }

            }else{
                Toast.makeText(WdyhjActivity.this,yhj_.getMsg(),Toast.LENGTH_SHORT).show();
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdyhj);
        ButterKnife.bind(this);
        initView();

    }
     void init() {
        if (mlist!=null&&mlist.size()>0&&adapter!=null){
            mlist.clear();
            adapter.notifyDataSetChanged();
        }
        mlist=yhj_.getYhjs();
        if (mlist!=null&&mlist.size()>0){
            loadmore.setVisibility(View.VISIBLE);
            adapter=new YouHuiJuanAdapter(WdyhjActivity.this,mlist);
            listView.setAdapter(adapter);
        }else {
            loadmore.setVisibility(View.INVISIBLE);
        }

    }
     PullToRefreshLayout ptrl;
     boolean isFirstIn = true;
    int more=0;


     void initView() {
        mlist=new ArrayList<YHJ>();
        reqid=getIntent().getStringExtra("reqid");
        if(!reqid.equals("")){
            url=url+"?reqid="+reqid+"&more="+0;
            new ThreadUtils_no(WdyhjActivity.this, url, handler).start();
        }else {
            Toast.makeText(WdyhjActivity.this,"请重新登录",Toast.LENGTH_LONG).show();
        }


         rl_yhj_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        ptrl = ((PullToRefreshLayout)findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new MyListener());

        loadmore=ptrl.getChildAt(2);
        if (loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {
            new ThreadUtils_no(WdyhjActivity.this, url, handler).start();
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
                    new ThreadUtils_no(WdyhjActivity.this, url, xhandler).start();
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }}

}
