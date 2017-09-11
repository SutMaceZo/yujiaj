package com.chaotong.yujia.main.menu.xiulian.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class OrderPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mlist;
    public OrderPagerAdapter(FragmentManager fm, List<Fragment> mlist) {
        super(fm);
        this.mlist=mlist;
    }
    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
