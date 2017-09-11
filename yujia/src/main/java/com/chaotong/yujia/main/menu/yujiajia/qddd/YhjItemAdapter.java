package com.chaotong.yujia.main.menu.yujiajia.qddd;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.property.yujiajia.YhjPro;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class YhjItemAdapter extends BaseAdapter {

    private Context context;
    private List<YhjPro> list;

    public YhjItemAdapter(Context context, List<YhjPro> url) {
        this.context = context;
        this.list = url;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private SimpleDraweeView url;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.yhj_image, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.url = (SimpleDraweeView) convertView.findViewById(R.id.yhj_image);//优惠卷图片
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
     if (list.get(position).getYhj_pic()!=null&&!list.get(position).getYhj_pic().equals("")){
         viewHolder.url.setImageURI(Uri.parse(list.get(position).getYhj_pic()));
     }
        return convertView;
    }

}
