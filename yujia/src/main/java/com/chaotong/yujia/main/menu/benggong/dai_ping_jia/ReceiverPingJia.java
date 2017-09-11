package com.chaotong.yujia.main.menu.benggong.dai_ping_jia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import com.chaotong.yujia.main.adapter.ReceiverPingJia_JiaoLian_Adapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.WcksBean;
import com.chaotong.yujia.main.entity.JiaoLianBean.receiverPingJia;
import com.chaotong.yujia.main.entity.SdPjBean;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.utils.PullToFlash.MyListener;
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
 * Created by Administrator on 2016/8/18 0018.
 */

public class ReceiverPingJia extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Bind(R.id.receiver_pingjia_jiaolian_list)
     PullableListView mlistview;
     String reqid = "";
     String trainerid = "";
     ReceiverPingJia_JiaoLian_Adapter adapter;
    String content = "";
     List<SdPjBean.WdpjBean> list;
    SdPjBean mSdpj;

    View loadmore;
    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.refresh_view)
     PullToRefreshLayout ptrl;
    private String url = urlPath.getUrl() + "WdpjServlet";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson=new Gson();
            mSdpj=gson.fromJson(json,SdPjBean.class);
            if (mSdpj!=null){
                String code=mSdpj.getCode();
                String message=mSdpj.getMsg();
                if(code.equals("1")){
                   init(mSdpj);
                }else if(("0").equals(code)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReceiverPingJia.this);
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
                                    Intent intent = new Intent(ReceiverPingJia.this, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).show();
                } else {
                    Toast.makeText(ReceiverPingJia.this,mSdpj.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }else {
                ToastUtils.showToast(ReceiverPingJia.this,getResources().getString(R.string.error));
            }
            super.handleMessage(msg);
        }
    };

    private void init(SdPjBean mSdpj) {

        if (more==0){
            if (list!=null&&list.size()>0&&adapter!=null){
                list.clear();
                adapter.notifyDataSetChanged();
            }
            if (mSdpj.getWdpj()!=null&&mSdpj.getWdpj().size()>0){
                list.addAll(mSdpj.getWdpj());
            }
            if (list!=null&&list.size()>0){
                adapter=new ReceiverPingJia_JiaoLian_Adapter(ReceiverPingJia.this,list);
                mlistview.setAdapter(adapter);
                loadmore.setVisibility(View.VISIBLE);
            }else {
                loadmore.setVisibility(View.INVISIBLE);
            }
        }else {
            if (mSdpj.getWdpj()!=null&&mSdpj.getWdpj().size()>0){
                list.addAll(mSdpj.getWdpj());
                adapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver_pingjia_jiaolian);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        rl_bt_back.setOnClickListener(this);
    }

    private void initView() {
        list = new ArrayList<>();
        reqid = getIntent().getStringExtra("reqid");
        initThread(0);
        ptrl.setOnRefreshListener(new MyListener());
        loadmore=ptrl.getChildAt(2);
        if (loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    private void initThread(int more) {
        JSONObject object;
        if (!("").equals(reqid)) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("more",more);
                content = String.valueOf(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ThreadUtils(ReceiverPingJia.this, url, content, handler).start();
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
more=0;
                    initThread(more);
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
                    initThread(more);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }

    int more=0;

    @Override
    public void onClick(View view) {
        finish();
    }

}
