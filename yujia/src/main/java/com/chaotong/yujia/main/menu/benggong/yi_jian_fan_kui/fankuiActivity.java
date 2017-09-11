package com.chaotong.yujia.main.menu.benggong.yi_jian_fan_kui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.thread.ThreadUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 意见反馈
 */

public class fankuiActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.edit_fankui)
     EditText fankui_edit;

    @Bind(R.id.commint)
     Button commint;
    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    private String url = urlPath.getUrl() + "FeedbackServlet";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjfk);
        ButterKnife.bind(this);
        commint.setOnClickListener(this);
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onClick(View view) {
        String edit = fankui_edit.getText().toString();
        String reqid = getIntent().getStringExtra("reqid");
        String content = "";
        if (!("").equals(reqid)&&!("").equals(edit)) {
            JSONObject object = new JSONObject();
            try {
                object.put("reqid", reqid);
                object.put("content", edit);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            content = String.valueOf(object);
            new ThreadUtils(fankuiActivity.this, url, content, handler).start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String message = object.getString("msg");
                if (code.equals("1")) {
                    Toast.makeText(fankuiActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };
}
