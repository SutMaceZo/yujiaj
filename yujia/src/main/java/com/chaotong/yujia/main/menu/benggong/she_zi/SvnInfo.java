package com.chaotong.yujia.main.menu.benggong.she_zi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.utils.VersonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/24 0024.
 */
public class SvnInfo extends BaseActivity {

    @Bind(R.id.verson_name)
    TextView mVersonName;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svn);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mVersonName.setText("瑜伽伽 v"+VersonUtils.getVersonCode(this));
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
