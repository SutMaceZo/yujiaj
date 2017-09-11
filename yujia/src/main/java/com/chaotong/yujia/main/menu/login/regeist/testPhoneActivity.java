package com.chaotong.yujia.main.menu.login.regeist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.thread.testPhoneThread;
import com.chaotong.yujia.main.utils.testUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/29 0029.
 */

public class testPhoneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.phone_number)
     EditText phone_number;
    @Bind(R.id.test_number)
     EditText test_number;
    @Bind(R.id.password_number)
     EditText password_number;
    @Bind(R.id.re_password_number)
     EditText re_password_number;
    @Bind(R.id.get_btn)
     Button get_number_btn;
    @Bind(R.id.make_sure02)
     Button make_sure02;

    @Bind(R.id.bt_back)
     Button bt_back;
    UrlPath urlPath = UrlPath.getUrlPath();
    String Url= urlPath.getUrl()+"Verification";

    String phoneNum;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
          String json=msg.obj.toString();
            try {
                JSONObject object=new JSONObject(json);
                String code=object.getString("code");
                String verificationCode=object.getString("verificationCode");
                if(code.equals("1")){
                    Toast.makeText(testPhoneActivity.this,"验证通过",Toast.LENGTH_LONG).show();
                    test_number.setText(verificationCode);
                }else if(code.equals("2")){
                    Toast.makeText(testPhoneActivity.this,"抱歉，该手机号已被注册",Toast.LENGTH_LONG).show();
                }else if(code.equals("-1")){
                    Toast.makeText(testPhoneActivity.this,"服务器出现异常",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_phone);
        ButterKnife.bind(this);
        initEvent();
    }

    public void initEvent() {
        get_number_btn.setOnClickListener(this);
        make_sure02.setOnClickListener(this);
        bt_back.setOnClickListener(this);
    }
    public  static int PHONECODE=001;
    public static int JLPHONECODE=002;
    public static int GYPHONECODE=003;
    public static int GZPHONECODE=004;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_btn:

                if(phone_number.equals("")){
                    Toast.makeText(testPhoneActivity.this,"手机号不能为空",Toast.LENGTH_LONG).show();
                }else {
                    phoneNum=phone_number.getText().toString();
                    System.out.print("phone----"+phoneNum);
                    Boolean S= testUtils.isPhone(phoneNum);

                    if(S==false){
                        Toast.makeText(testPhoneActivity.this,"请检查你的手机号是否输入正确",Toast.LENGTH_LONG).show();
                    }else {
                        new testPhoneThread(testPhoneActivity.this, phoneNum, mHandler,Url).start();
                    }
                }
                break;
            case R.id.make_sure02:
                String PasswordNumber=password_number.getText().toString();
                String Re_PasswordNumber=re_password_number.getText().toString();
                Boolean P=testUtils.isPassword(PasswordNumber);
                if(PasswordNumber.equals("")||Re_PasswordNumber.equals("")){
                    Toast.makeText(testPhoneActivity.this,"密码或确认密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(P==false){
                    Toast.makeText(testPhoneActivity.this,"密码格式不正确，请重新输入",Toast.LENGTH_SHORT).show();
                }else if(PasswordNumber.equals(Re_PasswordNumber)){
                                Intent intent01 =getIntent();
                                intent01.putExtra("phone", phoneNum);
                                intent01.putExtra("password", PasswordNumber);
                                setResult(PHONECODE,intent01);
                                finish();
                                break;

                    }else {
                    Toast.makeText(testPhoneActivity.this,"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_back:
              finish();
                break;
        }
    }
}
