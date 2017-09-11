package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.changguanList;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class spanner_adapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    LayoutInflater inflater;

    public spanner_adapter(Context context, List<String> list) {
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }
    public void setDate(List<String> changguan){
        this.list=changguan;
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
        Holder holder;
        if(view==null){
            holder=new Holder();
            view=inflater.inflate(R.layout.hngguan_listitem,null);
            holder.textView= (TextView) view.findViewById(R.id.jiaolian_name);
            view.setTag(holder);
        }else {
            holder= (Holder) view.getTag();
        }
        String face= StringEscapeUtils.unescapeJava(list.get(i));
        holder.textView.setText(face);
        return view;
    }
    class  Holder{
        TextView textView;
    }
}
