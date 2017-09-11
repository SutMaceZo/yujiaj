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
import com.chaotong.yujia.main.entity.collect.CollectChangguan;
import com.chaotong.yujia.main.menu.benggong.wo_de_shou_chang.ScBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class CollectChangguanAdapter extends BaseAdapter {
    private List<ScBean.WdsccgBean> list;
    Context context;
    LayoutInflater inflater;

    public CollectChangguanAdapter( Context context,List<ScBean.WdsccgBean> list) {
        this.list = list;
        this.context = context;
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
        final VHolder holder;
        if (view == null) {
            holder = new VHolder();
            view = inflater.inflate(R.layout.collect_changguan_item, viewGroup,false);
            holder.imageView = (SimpleDraweeView) view.findViewById(R.id.pic);
            holder.name_tx = (TextView) view.findViewById(R.id.jiaolian_name);
            holder.adress_tx = (TextView) view.findViewById(R.id.changguan_adress);
           // holder.distance_tx = (TextView) view.findViewById(R.id.changguan_distance);
            view.setTag(holder);
        } else {
            holder = (VHolder) view.getTag();
        }
        holder.name_tx.setText(list.get(i).getStadiumsname());
        holder.adress_tx.setText(list.get(i).getAdd());
    //    holder.distance_tx.setText(list.get(i).getDistance());

        if (list.get(i).getPic()!=null&&!list.get(i).getPic().equals("")){
            holder.imageView.setImageURI(Uri.parse(list.get(i).getPic()));
        }

        return view;
    }

    class VHolder {
        SimpleDraweeView imageView;
        TextView name_tx, adress_tx, distance_tx;
    }
}
