package com.chaotong.yujia.main.menu.login.regeist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.foamtrace.photopicker.Image;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/26 0026.
 */

public class registActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.huiyuan)
     ImageView huiyuan_Layout;
    @Bind(R.id.jiaolian)
    ImageView jiaolian_Layout;
    @Bind(R.id.guanyuan)
    ImageView guanyuan_Layout;
    @Bind(R.id.guanzhu)
    ImageView guanzhu_Layout;
    @Bind(R.id.rl_back01)
    RelativeLayout rl_back01;
    @Bind(R.id.back01)
    ImageView back01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        ButterKnife.bind(this);

        huiyuan_Layout.setOnClickListener(this);
        jiaolian_Layout.setOnClickListener(this);
        guanyuan_Layout.setOnClickListener(this);
        guanzhu_Layout.setOnClickListener(this);
        rl_back01.setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent in = new Intent();
        switch (view.getId()) {
            case R.id.huiyuan:
                in.setClass(registActivity.this, huiyuanRegist.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.jiaolian:
                in.setClass(registActivity.this, jiaolianRegist.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.guanyuan:
                in.setClass(registActivity.this, guanyuanRegist.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.guanzhu:
                in.setClass(registActivity.this, guanzhuRegist.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                break;
            case R.id.rl_back01:
                finish();
                break;
        }
    }
}
