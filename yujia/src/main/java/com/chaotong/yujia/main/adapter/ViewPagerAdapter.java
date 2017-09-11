package com.chaotong.yujia.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList=new ArrayList<>();
    private List<String> mtitle=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public  void addFrag(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mtitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtitle.get(position);
    }
}
