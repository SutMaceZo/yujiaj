package com.chaotong.yujia.main.menu.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Verson;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.password_Thread;
import com.chaotong.yujia.main.thread.testPhoneThread;
import com.chaotong.yujia.main.utils.CountDownTimerUtils;
import com.chaotong.yujia.main.utils.Md5Utils;
import com.chaotong.yujia.main.utils.testUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/26 0026.
 */

public class forgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rl_back10)
    RelativeLayout rl_back10;
    @Bind(R.id.back10)
     ImageView back10;
    @Bind(R.id.yanzhen)
     EditText yanzhen_num;
    @Bind(R.id.sureBtn)
     Button mimaBtn;
    @Bind(R.id.yanzhenbtn)
     Button yanzhenBtn;
    @Bind(R.id.phone_Edit)
     EditText phone_edit;
    @Bind(R.id.password_Edit)
     EditText password_edit;
    @Bind(R.id.password_Edit_two)
     EditText repassword_edit;

    UrlPath urlPath = UrlPath.getUrlPath();
    String phone = "";
    String password = "";
    String repassword = "";
    String url =urlPath.getUrl()+"ForgetPasswordServlet";
    String url01 =  urlPath.getUrl()+"ForgetPassVerification";


    private static final String TAG = "forgetPasswordActivity:";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Log.i("result",json);
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String message=object.getString("msg");
                String verificationCode = object.getString("verificationCode");
                if (code.equals("1")) {
                    countDownTimerUtils.onFinish();
                    countDownTimerUtils.cancel();
                    yanzhenBtn.requestFocus();
                    ToastUtils.showToast(forgetPasswordActivity.this, "请稍后...");
                } else if (code.equals("2")) {
                    countDownTimerUtils.onFinish();
                    countDownTimerUtils.cancel();
                    Toast.makeText(forgetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                } else if (code.equals("-1")) {
                    countDownTimerUtils.onFinish();
                    countDownTimerUtils.cancel();
                    Toast.makeText(forgetPasswordActivity.this, "服务器出现异常", Toast.LENGTH_LONG).show();
                }else if (code.equals("4")){
                    if (verificationCode!=null&&!("").equals(verificationCode)){
                        yanzhen_num.setText(verificationCode);
                       // yanzhen_num.setBackgroundResource(R.drawable.button_bg_4);
                        yanzhen_num.setEnabled(false);
                        countDownTimerUtils.onFinish();
                        countDownTimerUtils.cancel();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String message = object.getString("msg");
                System.out.print("code------" + code);
                if (code.equals("1")) {
                    Toast.makeText(forgetPasswordActivity.this, "修改成功,请登录", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(forgetPasswordActivity.this, loginActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    finish();
                }
                if (code.equals("2")) {
                    Toast.makeText(forgetPasswordActivity.this, "手机号不存在", Toast.LENGTH_LONG).show();
                }
                if (code.equals("-1")) {
                    Toast.makeText(forgetPasswordActivity.this, "服务器错误", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmzh);
        ButterKnife.bind(this);
        yanzhenBtn.setOnClickListener(this);
        rl_back10.setOnClickListener(this);
        mimaBtn.setOnClickListener(this);




    }

    CountDownTimerUtils countDownTimerUtils;
    @Override
    public void onClick(View view) {
        phone = phone_edit.getText().toString();
        password = password_edit.getText().toString();
        repassword = repassword_edit.getText().toString();
        switch (view.getId()) {
            case R.id.yanzhenbtn:
                if (("").equals(phone)) {

                    Toast.makeText(forgetPasswordActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Boolean S = testUtils.isPhone(phone);
                    if (S == false) {
                        Toast.makeText(forgetPasswordActivity.this, "请检查你的手机号是否输入正确", Toast.LENGTH_LONG).show();
                    } else {

                        countDownTimerUtils = new CountDownTimerUtils(yanzhenBtn, 60000, 1000);
                        countDownTimerUtils.start();
                        new testPhoneThread(forgetPasswordActivity.this,phone,mHandler,url01).start();
                    }

                }
                break;
            case R.id.sureBtn:
                if (("").equals(phone)) {
                    Toast.makeText(forgetPasswordActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                } else if (("").equals(password)) {
                    Toast.makeText(forgetPasswordActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                } else if (!password.equals(repassword)) {
                    Toast.makeText(forgetPasswordActivity.this, "密码不一致", Toast.LENGTH_LONG).show();
                } else if (!testUtils.isPassword(password)) {
                    Toast.makeText(forgetPasswordActivity.this, "密码格式不正确", Toast.LENGTH_LONG).show();
                } else {
                    String mima = Md5Utils.string2MD5(password);
                    new password_Thread(forgetPasswordActivity.this, phone, mima, url, handler).start();
                }
                break;
            case R.id.rl_back10:
                finish();
                break;
        }

    }
}
