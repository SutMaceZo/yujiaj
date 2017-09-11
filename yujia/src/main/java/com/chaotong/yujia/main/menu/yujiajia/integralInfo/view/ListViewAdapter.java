package com.chaotong.yujia.main.menu.yujiajia.integralInfo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.SZ;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class ListViewAdapter extends BaseAdapter {
    Context context;
    List<SZ> list;

    public ListViewAdapter(Context _context, List<SZ> _list) {
        this.list = _list;
        this.context = _context;
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



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.integral_info_list_item, null);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
        tv_date.setText(list.get(position).getDate());
        String face= StringEscapeUtils.unescapeJava(list.get(position).getReason());
        tv_name.setText(face);
        tv_num.setText("+"+list.get(position).getJf());
        return convertView;
    }
}
