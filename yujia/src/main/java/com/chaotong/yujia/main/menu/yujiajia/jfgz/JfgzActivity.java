package com.chaotong.yujia.main.menu.yujiajia.jfgz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 积分规则
 */

public class JfgzActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jfgz);
        ButterKnife.bind(this);
        rl_bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.rl_bt_back:
                finish();
                break;
        }
    }
}
