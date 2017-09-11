package com.chaotong.yujia.main.menu.benggong.she_zi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Verson;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.utils.Md5Utils;
import com.chaotong.yujia.main.utils.testUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/15 0015.
 */

public class updatePassword extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.phone_Edit)
     EditText phone;

    @Bind(R.id.old_password)
     EditText old_password;

    @Bind(R.id.new_password)
     EditText new_password;

    @Bind(R.id.new_password2)
     EditText new_password2;

    @Bind(R.id.sureBtn)
     Button sureBtn;

    @Bind(R.id.rl_back10)
    RelativeLayout rl_back10;
    @Bind(R.id.back10)
     ImageView back10;

    String username = "";
    String oldpass = "";
    String newpass = "";
    String repass="";
    UrlPath urlPath = UrlPath.getUrlPath();
    String url= urlPath.getUrl()+"UpdatePasswordServlet";

    SharedPreferences sp;

    @Bind(R.id.progress)
    ProgressBar pb;

    Verson versons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);
        sp=getSharedPreferences("Yuga",MODE_PRIVATE);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

     void initView() {
         sp=getSharedPreferences(MyApplication.SpName,MODE_PRIVATE);
        String Nphone=sp.getString(MyApplication.PHONENAME,"");
        if (Nphone!=null&&!Nphone.equals("")){
            phone.setText(Nphone);
            phone.setEnabled(false);
        }

    }

     void initEvent() {
        sureBtn.setOnClickListener(this);
        back10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back10:
                finish();
                break;
            case R.id.sureBtn:
                username = phone.getText().toString();
                oldpass = old_password.getText().toString();
                newpass = new_password.getText().toString();
                repass=new_password2.getText().toString();
                Boolean F= testUtils.isPassword(newpass);
                Boolean P=testUtils.isPhone(username);
                String content="";
                if (("").equals(username)||("").equals(oldpass)||("").equals(newpass)||("").equals(repass)){
                    Toast.makeText(updatePassword.this,"请检查是否全部输入",Toast.LENGTH_LONG).show();
                }
                else if(F){
                    if (newpass.equals(repass)){

                        String oldpw= Md5Utils.string2MD5(oldpass);
                        String newpw= Md5Utils.string2MD5(newpass);
                        JSONObject object=new JSONObject();
                        try {
                            object.put("username",username);
                            object.put("oldpass",oldpw);
                            object.put("newpass",newpw);
                            content=String.valueOf(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pb.setVisibility(View.VISIBLE);
                        sureBtn.setEnabled(false);
                        new ThreadUtils(this,url,content,handler).start();
                    }else {
                        Toast.makeText(updatePassword.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    }
                    }else {
                    Toast.makeText(updatePassword.this,"新密码格式不正确，请重新输入",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
     Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String json=msg.obj.toString();
            if (json!=null){
                pb.setVisibility(View.INVISIBLE);
                sureBtn.setEnabled(true);
                try {
                    JSONObject object=new JSONObject(json);
                    String code=object.getString("code");
                    String message=object.getString("msg");
                    if(("1").equals(code)){
                        Toast.makeText(updatePassword.this,"修改成功，请使用新密码重新登录",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putBoolean("isLogin",false);
                        editor.putString("reqid","");
                        editor.commit();
                        Intent intent1=new Intent(updatePassword.this,loginActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                    else {
                        Toast.makeText(updatePassword.this,message,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.handleMessage(msg);
        }
    };
}
