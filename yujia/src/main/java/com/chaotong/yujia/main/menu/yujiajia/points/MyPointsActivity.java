package com.chaotong.yujia.main.menu.yujiajia.points;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.JiFengBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.Wdjf;
import com.chaotong.yujia.main.entity.HuiYuanBean.YuHuiJuanBean;
import com.chaotong.yujia.main.menu.yujiajia.integralInfo.IntegralInfoActivity;
import com.chaotong.yujia.main.menu.yujiajia.jfgz.JfgzActivity;
import com.chaotong.yujia.main.menu.yujiajia.points.horListView.PointListView;
import com.chaotong.yujia.main.menu.yujiajia.record.RecordActivity;
import com.chaotong.yujia.main.menu.yujiajia.yhj.view.YhjActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的积分
 */

public class MyPointsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Bind(R.id.bt_skip)
     TextView bt_skip;
    @Bind(R.id.bt_szmx)
     TextView bt_szmx;
    @Bind(R.id.bt_dhjl)
     TextView bt_dhjl;
    @Bind(R.id.bt_jfgz)
     TextView bt_jfgz;
    String reqid = "";
    UrlPath urlPath = UrlPath.getUrlPath();
    String url = urlPath.getUrl() + "WdjfServlet";
    JiFengBean JF;
    Wdjf wdjf;
    List<YuHuiJuanBean> yhjs;
    @Bind(R.id.textView20)
     TextView jifen_num;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            JF = gson.fromJson(json, JiFengBean.class);
            wdjf = JF.getWdjf();
            if(wdjf!=null){
                init();
                initView();
                jf_srfl.setRefreshing(false);
                progress.setVisibility(View.INVISIBLE);
            }
            super.handleMessage(msg);
        }
    };

    private void initView() {
        if (wdjf == null) {
            jifen_num.setText(0);
        } else {
            jifen_num.setText(wdjf.getJf());
        }

    }

    @Bind(R.id.jf_srfl)
     SwipeRefreshLayout jf_srfl;

    @Bind(R.id.progress)
     ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_points);
        ButterKnife.bind(this);

        initEvent();
        reqid = getIntent().getStringExtra("reqid");
        initData();
        progress.setVisibility(View.VISIBLE);
        jf_srfl.setColorSchemeResources(R.color.lavender,R.color.red,
                R.color.black,R.color.blue);
        jf_srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                },2000);
            }
        });
    }

    private void initEvent() {
        rl_bt_back.setOnClickListener(this);
        bt_skip.setOnClickListener(this);
        bt_szmx.setOnClickListener(this);
        bt_dhjl.setOnClickListener(this);
        bt_jfgz.setOnClickListener(this);
    }

    private void init() {
        PointListView lv = new PointListView(JF.getYhjs());
        lv.run(this, getApplicationContext());
    }

    private void initData() {
        String content = "";
        if (!("").equals(reqid) && reqid != null) {
            JSONObject object = new JSONObject();
            try {
                object.put("reqid", reqid);
                content = String.valueOf(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ThreadUtils(this, url, content, handler).start();
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rl_bt_back:
               finish();
                break;
         /*   积分商城*/
            case R.id.bt_skip:
               startActivity(new Intent(MyPointsActivity.this, YhjActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                break;
            /*收支明细*/
            case R.id.bt_szmx:
                startActivity(new Intent(MyPointsActivity.this, IntegralInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                break;
            /*兑换记录*/
            case R.id.bt_dhjl:
                startActivity(new Intent(MyPointsActivity.this, RecordActivity.class).putExtra("reqid", reqid).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                break;
            /*积分规则*/
            case R.id.bt_jfgz:
                startActivity(new Intent(MyPointsActivity.this, JfgzActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                break;
        }


    }
}
