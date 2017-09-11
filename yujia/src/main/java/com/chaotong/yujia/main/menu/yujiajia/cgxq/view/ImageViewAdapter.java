package com.chaotong.yujia.main.menu.yujiajia.cgxq.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class ImageViewAdapter extends PagerAdapter {


    private List<View> viewList;

    public ImageViewAdapter(List<View> viewList){
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return  viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
