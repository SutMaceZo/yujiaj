package com.chaotong.yujia.main.menu.xiulian.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.property.yujiajia.Stadiumspj;
import com.chaotong.yujia.main.property.yujiajia.Trainerspj;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class PjAdapter extends BaseAdapter {

    private List<Object> list;
    private Context context;
    private String type;

    public PjAdapter(Context context, List<Object> list, String type) {
        this.type = type;
        this.context = context;
        this.list = list;
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
        return 0;
    }

    class ViewHolder {
        private SimpleDraweeView pic;
        private TextView name, time, content, classname, data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.pj_item, parent,false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.pj_name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.pj_time);
            viewHolder.content = (TextView) convertView.findViewById(R.id.pj_content);
            viewHolder.classname = (TextView) convertView.findViewById(R.id.pj_class_name);
            viewHolder.data = (TextView) convertView.findViewById(R.id.pj_data);
            viewHolder.pic = (SimpleDraweeView) convertView.findViewById(R.id.pj_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (("cg").equals(type)) {
            Stadiumspj stadiu = (Stadiumspj) list.get(position);

            String ss= StringEscapeUtils.unescapeJava(stadiu.getName());
            String sss=StringEscapeUtils.unescapeJava(stadiu.getContent());

            viewHolder.name.setText(ss);
            viewHolder.time.setText(stadiu.getPj_fs()+"分");
            viewHolder.content.setText(sss);
            if (stadiu.getClassname()!=null){
                viewHolder.classname.setText("课程："+stadiu.getClassname());
            }else {
                viewHolder.classname.setVisibility(View.GONE);
            }
            viewHolder.data.setText(stadiu.getDate());
            if (stadiu.getPic()!=null&&!stadiu.getPic().equals("")){
                viewHolder.pic.setImageURI(Uri.parse(stadiu.getPic()));
            }


        } else if (("jl").equals(type)) {
            Trainerspj trainer = (Trainerspj) list.get(position);
            String jj=StringEscapeUtils.unescapeJava(trainer.getName());

            viewHolder.name.setText(jj);
            String jjj=StringEscapeUtils.unescapeJava(trainer.getContent());

            viewHolder.time.setText(trainer.getTime());
            viewHolder.content.setText(jjj);
            viewHolder.classname.setText(trainer.getClassname());
            viewHolder.data.setText(trainer.getDate());

            if (trainer.getPic()!=null&&!trainer.getPic().equals("")){
                viewHolder.pic.setImageURI(Uri.parse(trainer.getPic()));
            }
        }
        return convertView;
    }
}
