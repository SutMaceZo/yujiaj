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
public class HyKsAdapter extends BaseAdapter {
    Context context;
    List<WcksBean> list;
    LayoutInflater layoutInflater;

    public HyKsAdapter(Context context, List<WcksBean> list) {
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
            view = layoutInflater.inflate(R.layout.
                    hywcks, viewGroup,false);
            holder.text1 = (TextView) view.findViewById(R.id.yytext1);
            holder.text2 = (TextView) view.findViewById(R.id.yytext2);
            holder.text3 = (TextView) view.findViewById(R.id.yytext3);
            holder.text4 = (TextView) view.findViewById(R.id.yytext4);
            holder.text5 = (TextView) view.findViewById(R.id.yytext5);
            holder.text6= (TextView) view.findViewById(R.id.yytext6);
            holder.image1= (ImageView) view.findViewById(R.id.image1);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        String face= StringEscapeUtils.unescapeJava(list.get(i).getTrainer());
        holder.text4.setText(face);
        holder.text1.setText(list.get(i).getDate());
        holder.text2.setText(list.get(i).getWeek());
        holder.text3.setText(list.get(i).getClassname());
        holder.text5.setText(list.get(i).getTime());
        holder.text6.setText(list.get(i).getStadium());


        if (list.get(i).getClassstatu()!=null&&!list.get(i).getClassstatu().equals("")){
            holder.image1.setVisibility(View.VISIBLE);
            if (list.get(i).getClassstatu().equals("已完成")){
               holder.image1.setBackgroundResource(R.mipmap.wancheng);
           }else {
               holder.image1.setBackgroundResource(R.mipmap.wwc);
           }
        }else {
            holder.image1.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    class viewHolder {
        TextView text1, text2, text3, text4, text5, text6;
        ImageView image1;

    }
}
