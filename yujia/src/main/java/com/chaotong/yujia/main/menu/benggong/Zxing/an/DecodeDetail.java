package com.chaotong.yujia.main.menu.benggong.Zxing.an;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.DecodeKcxq;
import com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue.WdyyActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.google.gson.Gson;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/9/8 0008.
 */

public class DecodeDetail extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.dc_kc)
     TextView dc_kc;
    @Bind(R.id.dc_time)
     TextView dc_time;
    @Bind(R.id.dc_jl)
     TextView dc_jl;
    @Bind(R.id.dc_cg)
     TextView dc_cg;
    @Bind(R.id.bt_back)
    ImageView bt_back;

    @Bind(R.id.sign_in)
     Button qd_bt;

    UrlPath urlPath = UrlPath.getUrlPath();
    String url = urlPath.getUrl() + "ZxingServlet?";
    String classid = "";
    String reqid = "";
    DecodeKcxq data;

    @Bind(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decode_detail);
        ButterKnife.bind(this);
        init();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Gson gson = new Gson();
            data = gson.fromJson(json, DecodeKcxq.class);
            initView();
        }
    };

    private void initView() {

        code = data.getKc_code();
        qd_bt.setText(data.getKc_msg());
        if (("0").equals(code) || ("1").equals(code) || ("2").equals(code)||
                ("3").equals(code)||("6").equals(code)) {
            dc_kc.setText(data.getStadiumname());
            dc_time.setText(data.getDate() + " " + data.getTime());
            String face= StringEscapeUtils.unescapeJava(data.getTrainername());
            dc_jl.setText(face);
            dc_cg.setText(data.getPosition());
            if(("0").equals(code)||("1").equals(code)){
                qd_bt.setEnabled(true);
                qd_bt.setBackgroundResource(R.drawable.text_bg2);
                qd_bt.setOnClickListener(DecodeDetail.this);
            }
            if (("2").equals(code)||("3").equals(code)||("6").equals(code)) {
                qd_bt.setEnabled(false);
                qd_bt.setBackgroundResource(R.drawable.text_bg4);
            }
        }else {
            ToastUtils.showToast(DecodeDetail.this,data.getMsg());
            Intent intent=new Intent(DecodeDetail.this, WdyyActivity.class);
            intent.putExtra("reqid",reqid);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void init() {
        classid = getIntent().getStringExtra("classid");
        reqid = getIntent().getStringExtra("reqid");
        if (reqid.equals("")) {
            Toast.makeText(this, "请确认已登录", Toast.LENGTH_SHORT).show();
        } else {
            url = url + "reqid=" + reqid + "&classid=" + classid;
            new ThreadUtils_no(this, url, handler).start();
        }
        initEvent();
    }

    private void initEvent() {
        bt_back.setOnClickListener(this);
    }

    String code = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.sign_in:
                String sign_url = urlPath.getUrl() +
                        "ZxingDoServlet?reqid=" + reqid + "&kc_code=" + code +
                        "&classid=" + classid;
                progress.setVisibility(View.VISIBLE);
                qd_bt.setEnabled(false);
                qd_bt.setBackgroundResource(R.drawable.text_bg4);
                new ThreadUtils_no(DecodeDetail.this, sign_url, mhandler).start();
                break;
        }
    }

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json!=null){
                progress.setVisibility(View.INVISIBLE);
                qd_bt.setEnabled(true);
                qd_bt.setBackgroundResource(R.drawable.text_bg2);
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    String do_result = object.getString("do_result");
                    String do_reason = object.getString("do_reason");
                    if (code.equals("1")) {
                        Toast.makeText(DecodeDetail.this, do_reason+do_result, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(DecodeDetail.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.handleMessage(msg);
        }
    };
}
