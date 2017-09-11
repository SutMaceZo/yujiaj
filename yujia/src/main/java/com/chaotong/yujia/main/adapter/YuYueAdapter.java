package com.chaotong.yujia.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.TextView;

import com.chaotong.yujia.main.R;

import com.chaotong.yujia.main.entity.HuiYuanBean.YuYueBean;

import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class YuYueAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
    List<YuYueBean> list;
    LayoutInflater layoutInflater;
    String reqid;

    callback callback;

    int position;


    public YuYueAdapter(Context context, List<YuYueBean> list, String reqid, callback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
        this.reqid = reqid;
        layoutInflater = LayoutInflater.from(context);
    }

    public interface callback {
        public void clickOk(View v);
    }

    @Override
    public void onClick(View v) {
        callback.clickOk(v);
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
            view = layoutInflater.inflate(R.layout.wdyy_list_item, viewGroup, false);
            holder.yuyue = (Button) view.findViewById(R.id.yybt1);
            holder.text1 = (TextView) view.findViewById(R.id.yytext1);
            holder.text2 = (TextView) view.findViewById(R.id.yytext2);
            holder.text3 = (TextView) view.findViewById(R.id.yytext3);
            holder.text4 = (TextView) view.findViewById(R.id.yytext4);
            holder.text5 = (TextView) view.findViewById(R.id.yytext5);
            holder.text6= (TextView) view.findViewById(R.id.yytext6);


            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }

        String s = StringEscapeUtils.unescapeJava(list.get(i).getTrainername());
        holder.text4.setText("("+s+")");
        holder.text1.setText(list.get(i).getDate());
        holder.text2.setText(list.get(i).getWeek());
        holder.text3.setText(list.get(i).getClassname());
        holder.text5.setText(list.get(i).getTime());
        holder.text6.setText(list.get(i).getStadiumname());

        holder.yuyue.setText(list.get(i).getKc_msg());
        final String code = list.get(i).getKc_code();
        position = i;
        holder.yuyue.setTag(R.id.qxyy,-1);
        if (("3").equals(code)) {
            holder.yuyue.setEnabled(false);
            holder.yuyue.setBackgroundResource(R.drawable.text_bg4);
        } else if (("2").equals(code)) {
            holder.yuyue.setEnabled(true);
            holder.yuyue.setBackgroundResource(R.drawable.text_bg2);
            holder.yuyue.setOnClickListener(this);
            holder.yuyue.setTag(R.id.qxyy, position);
        } else if (("0").equals(code)) {
            holder.yuyue.setEnabled(true);
            holder.yuyue.setBackgroundResource(R.drawable.text_bg2);
            holder.yuyue.setOnClickListener(this);
            holder.yuyue.setTag(R.id.hyqd, position);

        }
        return view;
    }

    class viewHolder {
        TextView text1, text2, text3, text4, text5, text6;
        Button yuyue;


    }
}
