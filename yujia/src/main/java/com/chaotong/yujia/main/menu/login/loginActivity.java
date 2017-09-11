package com.chaotong.yujia.main.menu.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;


import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.login.regeist.registActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.loginThread;
import com.chaotong.yujia.main.utils.Md5Utils;
import com.chaotong.yujia.main.utils.testUtils;
import com.chaotong.yujia.main.utils.viewUtils;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


public class loginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.login_edit)
    EditText login_edit;
    @Bind(R.id.password_edit)
    EditText password_edit;
    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.regist_num)
    TextView regist_tx;

    @Bind(R.id.forget_psw)
    TextView forget_tx;

    @Bind(R.id.rl_cancle_tx)
    RelativeLayout rl_cancle_tx;
    @Bind(R.id.cancle_tx)
    ImageView cancle_tx;
    UrlPath urlPath = UrlPath.getUrlPath();
    private String loginName;
    private String Password;
    private String URL = urlPath.getUrl() + "LoginServlet";
    private SharedPreferences sp;
    @Bind(R.id.progress)
    ProgressBar progress;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (!json.equals("") && json != null) {
                progress.setVisibility(View.INVISIBLE);
                loginButton.setEnabled(true);
                JSONObject object;
                try {
                    object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    String type = object.getString("type");
                    String reqid = object.getString("reqid");
                    String yh_type = object.getString("yh_type");
                    if (code.equals("1")) {
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isLogin", false);
                        editor.putString("type", type);
                        editor.putString("reqid", reqid);
                        editor.putString("yh_type", yh_type);
                        if (loginName != null && !("").equals(loginName)) {
                            editor.putString(MyApplication.PHONENAME, loginName);

                        }
                        editor.commit();

                        urlPath.getData(yh_type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
//                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else {
                        Toast.makeText(loginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        sp = getSharedPreferences("Yuga", MODE_PRIVATE);

        Intent intent = getIntent();
        if (intent.getStringExtra("tel") != null) {
            login_edit.setText(intent.getStringExtra("tel"));
        }
        if (intent.getStringExtra("psw") != null) {
            password_edit.setText(intent.getStringExtra("psw"));
        }
        initEvent();

        //四个不同的类型，四个不同的fragment..
    }

    public void initEvent() {
        loginButton.setOnClickListener(this);
        regist_tx.setOnClickListener(this);
        forget_tx.setOnClickListener(this);
        rl_cancle_tx.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            intent.putExtra("x", 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_button:

                loginName = login_edit.getText().toString();
                Password = password_edit.getText().toString();
                boolean T = testUtils.isPhone(loginName);
                boolean P = testUtils.isPassword(Password);
                if (T) {
                    if (P) {
                        String password = Md5Utils.string2MD5(Password);
                        //  String registrationID = viewUtils.DeviceId(loginActivity.this);
                        String registrationID = JPushInterface.getRegistrationID(loginActivity.this);
                        Log.i("info", "registrationID:" + registrationID);
                        progress.setVisibility(View.VISIBLE);
                        loginButton.setEnabled(false);

                        new loginThread(loginActivity.this, loginName, password, registrationID, URL, mHandler).start();
                    } else {
                        ToastUtils.showToast(loginActivity.this, "请检查密码是否输入正确");
                    }
                } else {
                    ToastUtils.showToast(loginActivity.this, "请检查手机号是否输入正确");
                }

                break;
            case R.id.regist_num:
                Intent intent = new Intent();
                intent.setClass(loginActivity.this, registActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.forget_psw:
                Intent intent1 = new Intent();
                intent1.setClass(loginActivity.this, forgetPasswordActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.rl_cancle_tx:

                Intent intent2 = new Intent(loginActivity.this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("x", 1);
                startActivity(intent2);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;

        }


    }
}
