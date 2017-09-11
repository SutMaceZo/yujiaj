package com.chaotong.yujia.main.menu.xiulian.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chaotong.yujia.main.MyViewPager;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.adapter.ViewPagerAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.OrderPagerAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.base.CgFragment;
import com.chaotong.yujia.main.menu.xiulian.main.base.JlFragment;
import com.chaotong.yujia.main.menu.xiulian.main.base.KcFragment;
import com.chaotong.yujia.main.utils.NoDoubleClickListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/5 0005.
 * 约课页面
 */
public class XiuLianFragment extends Fragment {
    View view;
    @Bind(R.id.xiulian_l1)
    LinearLayout xiulian_l1;
    @Bind(R.id.xiulian)
    LinearLayout xiulian;
    @Bind(R.id.et_search)
    TextView findEdit;
    @Bind(R.id.order_vp)
    MyViewPager order_vp;
    @Bind(R.id.search)
    RelativeLayout search_layout;
    @Bind(R.id.xiulian_rela)
    RelativeLayout xiulian_rela;

    @Bind(R.id.tablayout)
    TabLayout mTablayout;

    CgFragment fragment01;
    JlFragment fragment02;
    KcFragment fragment03;
    List<Fragment> mlist;

    Context mContext;
    int mSearchType = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_xiulian, container, false);
        ButterKnife.bind(this, view);
        init();
        initEvent();
        return view;
    }

    private void initEvent() {

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("type", mSearchType);
                startActivity(intent);

            }
        });
        xiulian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        xiulian_l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i=tab.getPosition();
                order_vp.setCurrentItem(i);
                if (i==2){
                    search_layout.setVisibility(View.INVISIBLE);
                }else {
                    search_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init() {

        xiulian_rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fragment01 = new CgFragment();
        fragment02 = new JlFragment();
        fragment03 = new KcFragment();
        //Toast.makeText(getActivity(), "到这了", Toast.LENGTH_SHORT).show();
        mlist = new ArrayList<>();
        mlist.add(0, fragment01);
        mlist.add(1, fragment02);
        mlist.add(2, fragment03);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(mlist.get(0),"场馆");
        adapter.addFrag(mlist.get(1),"教练");
        adapter.addFrag(mlist.get(2),"课程");
        order_vp.setAdapter(adapter);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        mTablayout.setTabTextColors(getResources().getColor(R.color.textcolor),
                getResources().getColor(R.color.titlebar_color));
        mTablayout.setupWithViewPager(order_vp);
    }

}
