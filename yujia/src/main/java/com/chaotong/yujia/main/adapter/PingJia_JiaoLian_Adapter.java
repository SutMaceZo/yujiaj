package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.JiaoLianBean.receiverPingJia;
import com.chaotong.yujia.main.property.yujiajia.Jlpj;
import com.chaotong.yujia.main.property.yujiajia.Trainerspj;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class PingJia_JiaoLian_Adapter extends BaseAdapter {
    Context context;
    List<Trainerspj> list;
    LayoutInflater inflater;

    public PingJia_JiaoLian_Adapter(Context context, List<Trainerspj> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.receiver_pingjia_jiaolian_item, null);
            holder = new ViewHolder();
            holder.name_tv = (TextView) view.findViewById(R.id.name);
            holder.classes_tv = (TextView) view.findViewById(R.id.classes);
            holder.time_tv = (TextView) view.findViewById(R.id.time);
            holder.imageView = (SimpleDraweeView) view.findViewById(R.id.pic);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Log.i("thread", "list1:" + list.size());
        holder.name_tv.setText(list.get(i).getName());
        holder.classes_tv.setText(list.get(i).getClassname());
        holder.time_tv.setText(list.get(i).getDate() + " " + list.get(i).getTime());

        if (list.get(i).getPic() != null && !list.get(i).getPic().equals("")) {
            holder.imageView.setImageURI(Uri.parse(list.get(i).getPic()));
        }

        return view;
    }

    class ViewHolder {
        SimpleDraweeView imageView;
        TextView name_tv, classes_tv, time_tv;

    }
}
