package com.chaotong.yujia.main.adapter;

import android.content.Context;

import android.graphics.Bitmap;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.chaotong.yujia.main.R;

import com.chaotong.yujia.main.entity.JiaoLianBean.WdkcBean;

import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;


import java.util.List;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class WdkcAdapter extends BaseAdapter{

    Context context;
    List<WdkcBean> list;
    LayoutInflater layoutInflater;
    String reqid;

    public WdkcAdapter(List<WdkcBean> list, Context context, String reqid) {
        this.list = list;
        this.context = context;
        this.reqid = reqid;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final viewHolder holder;
        if (view == null) {
            holder = new viewHolder();
            view = layoutInflater.inflate(R.layout.wcks_list2, null);

            holder.name_tv = (TextView) view.findViewById(R.id.ct_df);
            holder.yuyue = (TextView) view.findViewById(R.id.pj);
            holder.changguan_tv = (TextView) view.findViewById(R.id.pj_changguan);
            holder.time_tv = (TextView) view.findViewById(R.id.pj_time);
            holder.class_tv = (TextView) view.findViewById(R.id.pj_class);
            holder.jiaolian_image = (SimpleDraweeView) view.findViewById(R.id.jiaolian_image);
            holder.ct_max= (TextView) view.findViewById(R.id.ct_max);
            holder.ct_min= (TextView) view.findViewById(R.id.ct_min);
            holder.ct_num= (TextView) view.findViewById(R.id.ct_finish);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        holder.time_tv.setText(list.get(i).getDate()+" "+list.get(i).getTime());
        String s= StringEscapeUtils.unescapeJava(list.get(i).getTrainername());
        Log.i("------------------",s);
        holder.name_tv.setText(s.toString());
        holder.changguan_tv.setText(list.get(i).getStadiumname());
        holder.class_tv.setText(list.get(i).getClassname());
        holder.ct_max.setText("最大人数："+list.get(i).getCt_max());
        holder.ct_min.setText("最小人数："+list.get(i).getCt_min());
        holder.ct_num.setText("已约人数："+list.get(i).getCt_num());

        if (!list.get(i).getPic().equals("")&&list.get(i).getPic()!=null){
            holder.jiaolian_image.setImageURI(Uri.parse(list.get(i).getPic()));
        }


       // holder.yuyue.setVisibility(View.INVISIBLE);
        return view;
    }
    class viewHolder {
        TextView name_tv,class_tv,time_tv,changguan_tv,yuyue,ct_max,ct_min,ct_num;

        SimpleDraweeView jiaolian_image;

    }
}
