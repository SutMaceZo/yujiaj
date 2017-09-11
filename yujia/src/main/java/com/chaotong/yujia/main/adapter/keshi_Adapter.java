package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;

import com.chaotong.yujia.main.entity.HuiYuanBean.WcksBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;


import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class keshi_Adapter extends BaseAdapter {
    Context context;
    List<WcksBean> list;
    LayoutInflater layoutInflater;

    public keshi_Adapter(Context context, List<WcksBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if (view == null) {
            holder = new viewHolder();
            view = layoutInflater.inflate(R.layout.wcks_list2, null);
            holder.ks_changguan = (TextView) view.findViewById(R.id.pj_changguan);
            holder.ks_time = (TextView) view.findViewById(R.id.pj_time);
            holder.ks_class= (TextView) view.findViewById(R.id.pj_class);
            holder.imageView2= (ImageView) view.findViewById(R.id.imageView2);
            holder.ct_min= (TextView) view.findViewById(R.id.ct_min);
            holder.ct_max= (TextView) view.findViewById(R.id.ct_max);
            holder.ct_finish= (TextView) view.findViewById(R.id.ct_finish);
            holder.ct_df= (TextView) view.findViewById(R.id.ct_df);

            holder.jiaolian_image= (SimpleDraweeView) view.findViewById(R.id.jiaolian_image);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        holder.ks_class.setText(list.get(i).getClassname());
        holder.ks_time.setText(list.get(i).getDate()+" "+list.get(i).getTime());
        holder.ks_changguan.setText(list.get(i).getStadium());
        holder.ct_max.setText("约课人数："+list.get(i).getCt_num());
        holder.ct_min.setText("到课人数："+list.get(i).getCt_finish());
        holder.ct_finish.setText("评论人数："+list.get(i).getPj_num());

        holder.ct_df.setText("老师得分："+list.get(i).getPj_fs());

        if (list.get(i).getClassstatu()!=null&&list.get(i).getClassstatu().equals("已完成")){
            holder.imageView2.setBackgroundResource(R.mipmap.wancheng);
        }else {
            holder.imageView2.setBackgroundResource(R.mipmap.wwc);
        }


        if(list.get(i).getTrainerpic()!=null&&!list.get(i).getTrainerpic().equals("")){
        holder.jiaolian_image.setImageURI(Uri.parse(list.get(i).getTrainerpic()));
        }

        return view;
    }

    class viewHolder {
        TextView ks_class,ks_time,ks_changguan,ct_max,ct_min,ct_finish,ct_df;
        SimpleDraweeView jiaolian_image;
        ImageView imageView2;

    }
}
