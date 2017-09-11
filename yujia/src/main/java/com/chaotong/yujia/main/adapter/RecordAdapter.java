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
import com.chaotong.yujia.main.entity.HuiYuanBean.Hg_list;
import com.chaotong.yujia.main.entity.HuiYuanBean.YHJ;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class RecordAdapter extends BaseAdapter{
    Context context;
    List<Hg_list> list;
    LayoutInflater layoutInflater;

    public RecordAdapter(Context context, List<Hg_list> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder hodler;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.dhjl, parent,false);
            hodler = new viewHolder();
            hodler.image = (SimpleDraweeView) convertView.findViewById(R.id.jiaolian_image);
            hodler.yhj_name = (TextView) convertView.findViewById(R.id._name);
            hodler.yhj_date = (TextView) convertView.findViewById(R.id.yhj_date);
            hodler.yhj_sm = (TextView) convertView.findViewById(R.id.yhj_sm);
            hodler.hg_jf = (TextView) convertView.findViewById(R.id.pj);
            convertView.setTag(hodler);
        } else {
            hodler = (viewHolder) convertView.getTag();
        }
        YHJ yhj=list.get(position).getYhj();

        if (yhj.getYhj_pic()!=null&&!yhj.getYhj_pic().equals("")){
            hodler.image.setImageURI(Uri.parse(yhj.getYhj_pic()));
        }


           hodler.yhj_name.setText(yhj.getYhj_name());
            hodler.yhj_sm.setText(yhj.getYhj_sm());
            hodler.yhj_date.setText(list.get(position).getHg_date());
            hodler.hg_jf.setText("-"+list.get(position).getHg_jf());
            Log.i("thread",hodler.yhj_name.getText().toString()+"...");

        return convertView;
    }
    class viewHolder {
        SimpleDraweeView image;
        TextView yhj_name, yhj_sm, yhj_date, hg_jf;
    }
}
