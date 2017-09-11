package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.YHJ;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class YouHuiJuanAdapter extends BaseAdapter {
    Context context;
    List<YHJ> mlist;
    LayoutInflater inflater;

    public YouHuiJuanAdapter(Context context, List<YHJ> mlist) {
        this.context = context;
        this.mlist = mlist;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      final ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.youhuijuan_item,viewGroup,false);
            holder.imageView= (SimpleDraweeView) view.findViewById(R.id.youhuijuan_image);
            holder.yhj_name= (TextView) view.findViewById(R.id.yhj_name);
            holder.yhj_sm= (TextView) view.findViewById(R.id.yhj_sm);
            view.setTag(holder);
        }else{
           holder= (ViewHolder) view.getTag();
        }

        holder.yhj_name.setText(mlist.get(i).getYhj_name());
       holder.yhj_sm.setText(mlist.get(i).getYhj_sm());
        if (mlist.get(i).getYhj_pic()!=null&&!mlist.get(i).getYhj_pic().equals("")){
            holder.imageView.setImageURI(Uri.parse(mlist.get(i).getYhj_pic()));
        }
        return view;
    }
    class ViewHolder{
        SimpleDraweeView imageView;
        TextView yhj_sm,yhj_name;
    }
}
