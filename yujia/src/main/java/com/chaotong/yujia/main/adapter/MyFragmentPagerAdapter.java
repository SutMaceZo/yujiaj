package com.chaotong.yujia.main.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chaotong.yujia.main.entity.HuiYuanBean.huiyuanBean;

import java.util.List;

/**
 * Created by Jay on 2015/8/31 0031.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list;

    String reqid;
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list,String reqid) {
        super(fm);
        this.list=list;

        this.reqid=reqid;
    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("reqid",reqid);
        list.get(position).setArguments(bundle);
        return list.get(position);
    }@Override
     public int getCount() {
        return list.size();
    }

}

