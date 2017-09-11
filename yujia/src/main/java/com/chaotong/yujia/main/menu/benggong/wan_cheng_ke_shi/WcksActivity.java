package com.chaotong.yujia.main.menu.benggong.wan_cheng_ke_shi;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.keshi_Adapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.Wcks;
import com.chaotong.yujia.main.entity.HuiYuanBean.WcksBean;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 完成课时
 */

public class WcksActivity extends BaseActivity {

    @Bind(R.id.record_listView)
     PullableListView mlistview;

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;

    UrlPath urlPath = UrlPath.getUrlPath();
     String reqid = "";
     String url = urlPath.getUrl() + "WcksServlet";
     String content = "";
     Wcks wcks;
     List<WcksBean> list;
     keshi_Adapter adapter;

    View loadmore;
    int more=0;

     Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson=new Gson();
            wcks=gson.fromJson(json,Wcks.class);
            String message=wcks.getMsg();
            String code=wcks.getCode();
            if (code!=null&&message!=null){
                if(code.equals("1")){
                    if(!wcks.getWcks().equals("")){
                        for(int i=0;i<wcks.getWcks().size();i++){
                            WcksBean bean=wcks.getWcks().get(i);
                            list.add(bean);
                        }
                        adapter.notifyDataSetChanged();
                        loadmore.setVisibility(View.VISIBLE);
                    }
                }else if(("0").equals(code)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WcksActivity.this);
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
                                    Intent intent = new Intent(WcksActivity.this, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).show();
                } else {
                    Toast.makeText(WcksActivity.this,wcks.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(WcksActivity.this,getResources().getString(R.string.error),Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
     Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson=new Gson();
            wcks=gson.fromJson(json,Wcks.class);
            if(wcks!=null) {
                ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
                String code=wcks.getCode();
                if(code!=null){
                    if (code.equals("1")||code.equals("0")){
                        list=wcks.getWcks();
                        if (list!=null&&list.size()>0){
                            adapter = new keshi_Adapter(WcksActivity.this, list);
                            mlistview.setAdapter(adapter);
                            loadmore.setVisibility(View.VISIBLE);
                        }else {
                            loadmore.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        Toast.makeText(WcksActivity.this,wcks.getMsg(),Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(WcksActivity.this,wcks.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }else {
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wcks);
        ButterKnife.bind(this);

        initView();
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


     void initView() {

        list = new ArrayList<WcksBean>();
        ptrl = ((PullToRefreshLayout)findViewById(R.id.refresh_view));
        reqid = getIntent().getStringExtra("reqid");
        JSONObject object;
        more=0;
        if(!("").equals(reqid)) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("more",more);
                content = String.valueOf(object);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            new ThreadUtils(WcksActivity.this, url, content, handler).start();
        }else {
            Toast.makeText(WcksActivity.this,"请重新登录",Toast.LENGTH_SHORT).show();
        }

        ptrl.setOnRefreshListener(new MyListener());
        loadmore=ptrl.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

     PullToRefreshLayout ptrl;

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
more=0;
                    JSONObject object;
                    if(!("").equals(reqid)) {
                        try {
                            object = new JSONObject();
                            object.put("reqid", reqid);
                            object.put("more",more);
                            content = String.valueOf(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new ThreadUtils(WcksActivity.this, url, content, handler).start();
                    }else {
                        Toast.makeText(WcksActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    }

                    // 千万别忘了告诉控件刷新完毕了哦！

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

                    JSONObject object;
                    if(!("").equals(reqid)) {
                        more++;
                        try {
                            object = new JSONObject();
                            object.put("reqid", reqid);
                            object.put("more",more);
                            content = String.valueOf(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new ThreadUtils(WcksActivity.this, url, content, xhandler).start();
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }}

}
