package com.chaotong.yujia.main.menu.xiulian.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.OrderPagerAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.base.CgFragment;
import com.chaotong.yujia.main.menu.xiulian.main.base.JlFragment;
import com.chaotong.yujia.main.menu.xiulian.main.base.SearCgFragment;
import com.chaotong.yujia.main.menu.xiulian.main.base.SearJlFragment;
import com.chaotong.yujia.main.menu.xiulian.main.view.MyButton;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView back;
    @Bind(R.id.et_search)
    EditText mSearchText;
    @Bind(R.id.search_cg)
    Button mSearch_cg;
    @Bind(R.id.search_jl)
    Button mSearch_jl;
    String type="";

    public static final String fragment1Tag = "fragment01";
    public static final String fragment2Tag = "fragment02";

    Fragment  fragment01,fragment02;

    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ButterKnife.bind(this);
        initView();

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = new SearCgFragment();
            Bundle  bundle=new Bundle();

            bundle.putString("search",mSearchText.getText().toString());
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.search_frame, fragment, fragment1Tag).commit();
        }

    }

    private void initView() {

        mSearch_cg.setOnClickListener(this);
        mSearch_jl.setOnClickListener(this);
        rl_bt_back.setOnClickListener(this);

        fm = getSupportFragmentManager();
        fragment01 = fm.findFragmentByTag(fragment1Tag);
        fragment02 = fm.findFragmentByTag(fragment2Tag);
        //在此处设置edittext随着输入进行查询，后期修改
//        mSearchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {

        ft = fm.beginTransaction();

        switch (view.getId()){
            case R.id.rl_bt_back:
                finish();
                break;
            case R.id.search_cg:
                mSearch_cg.setBackgroundResource(R.drawable.circle_left);
                mSearch_jl.setBackgroundResource(R.drawable.circle_right_01);
                mSearch_cg.setTextColor(getResources().getColor(R.color.white));
                mSearch_jl.setTextColor(getResources().getColor(R.color.black));
                    fragment01 = new SearCgFragment();
                    Bundle  bundle=new Bundle();
                    bundle.putString("search",mSearchText.getText().toString());
                    fragment01.setArguments(bundle);
                    ft.replace(R.id.search_frame, fragment01, fragment1Tag);

                break;

            case R.id.search_jl:
                mSearch_cg.setBackgroundResource(R.drawable.circle_left_01);
                mSearch_jl.setBackgroundResource(R.drawable.circle_right);
                mSearch_cg.setTextColor(getResources().getColor(R.color.black));
                mSearch_jl.setTextColor(getResources().getColor(R.color.white));
                    fragment02 = new SearJlFragment();
                    Bundle  bundle1=new Bundle();
                    bundle1.putString("search",mSearchText.getText().toString());
                    fragment02.setArguments(bundle1);
                    ft.replace(R.id.search_frame,fragment02, fragment2Tag);

                break;
        }
    ft.commit();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; i < 2; i++) {
           /* RadioButton mTab = (RadioButton) radioGroup.getChildAt(i);*/
            MyButton button= new MyButton(SearchActivity.this);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag((String) button.getTag());
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment != null) {
                if (!button.checkPress()) {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }

}
