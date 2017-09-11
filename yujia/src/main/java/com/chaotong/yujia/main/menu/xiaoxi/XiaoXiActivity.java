package com.chaotong.yujia.main.menu.xiaoxi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.property.xiaoxi.PushMessageNum;
import com.chaotong.yujia.main.property.xiaoxi.PushMsgNum;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.List;

public class XiaoXiActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout xiaoxi_yueke, xiaoxi_tuike, xiaoxi_xitong, xiaoxi_changguan;
    private TextView yueke_mun, tuike_num, xitong_num, changguan_num;
    private String reqid;

    private PushMessageNum pushMessageNum;
    private String push_type;
    private List<PushMsgNum> pushmsg_nums;
    private Intent intent;
    private ImageView xiaoxi_back;
    private RelativeLayout rl_xiaoxi_back;
    Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if (json != null) {
                Gson gson = new Gson();
                pushMessageNum = gson.fromJson(json, PushMessageNum.class);
                pushmsg_nums = pushMessageNum.getPushmsg_nums();
                if (pushmsg_nums != null && pushmsg_nums.size() > 0) {
                    for (int i = 0; i < pushmsg_nums.size(); i++) {
                        if (("约课消息").equals(pushmsg_nums.get(i).getPushmsg_type())) {
                            yueke_mun.setText(pushmsg_nums.get(i).getPushmsg_num());
                        } else if (("退课消息").equals(pushmsg_nums.get(i).getPushmsg_type())) {
                            tuike_num.setText(pushmsg_nums.get(i).getPushmsg_num());
                        } else if (("系统消息").equals(pushmsg_nums.get(i).getPushmsg_type())) {
                            xitong_num.setText(pushmsg_nums.get(i).getPushmsg_num());
                        } else if (("场馆消息").equals(pushmsg_nums.get(i).getPushmsg_type())) {
                            changguan_num.setText(pushmsg_nums.get(i).getPushmsg_num());
                        }
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_xi);
        initView();
        initListener();
    }


    private void initView() {
        SharedPreferences preferences = getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        reqid = preferences.getString("reqid", "defaultname");

        xiaoxi_yueke = (RelativeLayout) findViewById(R.id.xiaoxi_yueke);//约课
        xiaoxi_tuike = (RelativeLayout) findViewById(R.id.xiaoxi_tuike);//退课
        xiaoxi_xitong = (RelativeLayout) findViewById(R.id.xiaoxi_xitong);//系统
        xiaoxi_changguan = (RelativeLayout) findViewById(R.id.xiaoxi_changguan);//场馆
        xiaoxi_back = (ImageView) findViewById(R.id.xiaoxi_back);
        yueke_mun = (TextView) findViewById(R.id.yueke_num);
        tuike_num = (TextView) findViewById(R.id.tuike_num);
        xitong_num = (TextView) findViewById(R.id.xitong_num);
        changguan_num = (TextView) findViewById(R.id.changguan_num);
        rl_xiaoxi_back = (RelativeLayout)findViewById(R.id.rl_xiaoxi_back);
        rl_xiaoxi_back.setOnClickListener(this);
        xiaoxi_yueke.setOnClickListener(this);
        xiaoxi_tuike.setOnClickListener(this);
        xiaoxi_xitong.setOnClickListener(this);
        xiaoxi_changguan.setOnClickListener(this);
        //xiaoxi_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        intent = new Intent(XiaoXiActivity.this, MessageActivity.class);
        switch (v.getId()) {
            case R.id.xiaoxi_yueke://约课
                push_type = "约课消息";
                intent.putExtra("push_type", "约课消息");
                initActy();
                break;
            case R.id.xiaoxi_tuike://退课
                push_type = "退课消息";
                intent.putExtra("push_type", "退课消息");
                initActy();
                break;
            case R.id.xiaoxi_xitong://系统
                push_type = "系统消息";
                intent.putExtra("push_type", "系统消息");
                initActy();
                break;
            case R.id.xiaoxi_changguan://场馆
                push_type = "场馆消息";
                intent.putExtra("push_type", "场馆消息");
                initActy();
                break;
            case R.id.rl_xiaoxi_back:
                this.finish();
                break;
        }
    }
    UrlPath urlPath = UrlPath.getUrlPath();
    private void initActy() {
        String url = urlPath.getUrl()+ urlPath.XXDQ.replace("reqid=x", "reqid=" + reqid).replace("push_type=x", "push_type=" + push_type);
        Log.i("xiaoxi---------",url);
        new ThreadUtils_no(XiaoXiActivity.this, url, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivityForResult(intent, 100);
            }
        }).start();
    }

    private void initListener() {
        String url = urlPath.getUrl()+ urlPath.XXSL.replace("reqid=x", "reqid=" + reqid);
        Log.i("xiaoxi-----------",url);
        new ThreadUtils_no(XiaoXiActivity.this, url, hand).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            initView();
            initListener();
        }

    }
}
