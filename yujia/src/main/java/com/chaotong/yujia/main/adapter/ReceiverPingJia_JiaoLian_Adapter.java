package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.JiaoLianBean.receiverPingJia;
import com.chaotong.yujia.main.entity.SdPjBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class ReceiverPingJia_JiaoLian_Adapter extends BaseAdapter {
    Context context;
    List<SdPjBean.WdpjBean> list;
    LayoutInflater inflater;

    public ReceiverPingJia_JiaoLian_Adapter(Context context, List<SdPjBean.WdpjBean> list) {
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
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.pj_item, null);
            viewHolder.name = (TextView) view.findViewById(R.id.pj_name);
            viewHolder.time = (TextView) view.findViewById(R.id.pj_time);
            viewHolder.content = (TextView) view.findViewById(R.id.pj_content);
            viewHolder.classname = (TextView) view.findViewById(R.id.pj_class_name);
            viewHolder.data = (TextView) view.findViewById(R.id.pj_data);
            viewHolder.pic = (SimpleDraweeView) view.findViewById(R.id.pj_pic);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String s= StringEscapeUtils.unescapeJava(list.get(i).getName());
        viewHolder.name.setText(s);

        viewHolder.data.setText(list.get(i).getDate());
        viewHolder.time.setText(list.get(i).getTime());

        String jjj=StringEscapeUtils.unescapeJava(list.get(i).getContent());
        viewHolder.content.setText(jjj);

        viewHolder.classname.setText(list.get(i).getClassname());



       if (list.get(i).getPic()!=null&&!list.get(i).getPic().equals("")){
           viewHolder.pic.setImageURI(Uri.parse(list.get(i).getPic()));
       }


        return view;
    }

    class ViewHolder {
        private SimpleDraweeView pic;
        private TextView name, time, content, classname, data;

    }
}
