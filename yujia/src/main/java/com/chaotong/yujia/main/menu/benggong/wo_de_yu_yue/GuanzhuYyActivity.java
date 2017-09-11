package com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Guanzhu.Gzyy;
import com.chaotong.yujia.main.entity.Guanzhu.Info;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.testUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.apache.commons.lang.StringEscapeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class GuanzhuYyActivity extends BaseActivity implements View.OnClickListener{
    String reqid="";

    @Bind(R.id.rl_back)
    RelativeLayout rl_back;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.hy_phone)
     EditText hy_phone;
    @Bind(R.id.hy_find)
      Button hy_find;
    @Bind(R.id.hy_image)
     SimpleDraweeView hy_image;
    @Bind(R.id.hy_age)
     TextView hy_age;
    @Bind(R.id.hy_sex)
     TextView hy_sex;
    @Bind(R.id.hy_name)
     TextView hy_name;

    @Bind(R.id.hy_yy)
     Button hy_yy;

    @Bind(R.id.lay)
     LinearLayout lay;

    @Bind(R.id.progress)
    ProgressBar progress;


     String hy_tel="";
    Gzyy gzyy;
    UrlPath urlPath = UrlPath.getUrlPath();
     String findUrl= urlPath.getUrl()+"GzYyServlet?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gzyy);
        ButterKnife.bind(this);
        init();
        initEvent();
    }

   public  void initEvent() {
       rl_back.setOnClickListener(this);
        hy_find.setOnClickListener(this);
        hy_yy.setOnClickListener(this);
    }

    public  void init() {
        lay.setVisibility(View.INVISIBLE);
        reqid=getIntent().getStringExtra("reqid");
    }

      Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String json=msg.obj.toString();
            if (json!=null){
                progress.setVisibility(View.INVISIBLE);
                Gson gson=new Gson();
                gzyy=gson.fromJson(json,Gzyy.class);
                Log.i("----bangzhu---------",json);
                String code=gzyy.getCode();
                String message=gzyy.getMsg();
                String exist_code=gzyy.getExist_code();
                String exist_msg=gzyy.getExist_msg();
                if (code!=null&&code.equals("1")){
                    ToastUtils.showToast(GuanzhuYyActivity.this,exist_msg);
                    if (exist_code!=null&&exist_code.equals("1")){
                        initData();
                    }else {
                        lay.setVisibility(View.INVISIBLE);
                    }
                }else if (("0").equals(code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuanzhuYyActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle(getResources().getString(R.string.app_name) + "")
                            .setMessage(getResources().getString(R.string.message) + "")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sp = getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putBoolean("isLogin", false);
                                    editor.putString("reqid", "");
                                    editor.putString("type", "");
                                    editor.commit();
                                    Intent intent = new Intent(GuanzhuYyActivity.this, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
                else {
                    ToastUtils.showToast(GuanzhuYyActivity.this,message);
                }

            }

            super.handleMessage(msg);
        }
    };

      int GZ_HY_YY=01;
    public void initData() {
        final Info info=gzyy.getInfo();
            lay.setVisibility(View.VISIBLE);
            if (info.getPic()!=null&&!info.getPic().equals("")){
             hy_image.setImageURI(Uri.parse(info.getPic()));
            }

            String face= StringEscapeUtils.unescapeJava(info.getName());
            hy_name.setText(face);
            hy_age.setText(info.getAge());
            hy_sex.setText(info.getSex());

            String sh_code=info.getFlag();
            String
                    card_code=info.getCard_flag();
            if(!("1").equals(sh_code)){
                    hy_yy.setEnabled(false);
                    hy_yy.setText(info.getFlag_msg());
            }else if(!("3").equals(card_code)){
                hy_yy.setEnabled(false);
                hy_yy.setText(info.getCard_msg());
            }else {
                hy_yy.setText("去帮她(他)预约吧");
                hy_yy.setEnabled(true);
                hy_yy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(GuanzhuYyActivity.
                                this,GzHelpHyActivity.class);
                        intent.putExtra("reqid",reqid);
                        intent.putExtra("id",info.getId());
                        startActivityForResult(intent,GZ_HY_YY);
                    }
                });
            }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hy_find:
                hy_tel=hy_phone.getText().toString();
                if(hy_tel!=null&&!("").equals(hy_tel)){
                    Boolean F= testUtils.isPhone(hy_tel);
                    if(F){
                       String url=findUrl+"reqid="+reqid+"&hy_tel="+hy_tel;
                        progress.setVisibility(View.VISIBLE);
                        new ThreadUtils_no(GuanzhuYyActivity.this,url,handler).start();
                    }else {
                        ToastUtils.showToast(GuanzhuYyActivity.this,"请检查账号是否输入正确");
                    }
                }else {
                    Toast.makeText(GuanzhuYyActivity.this,
                            "请输入会员手机号",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
