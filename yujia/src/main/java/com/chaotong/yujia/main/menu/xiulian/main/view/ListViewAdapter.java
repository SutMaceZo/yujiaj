package com.chaotong.yujia.main.menu.xiulian.main.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.property.yujiajia.Stadiums;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 16-5-20.
 */
public class ListViewAdapter extends BaseAdapter {
    Context context;
    private List<Stadiums> stadiumses;

    public ListViewAdapter(Context _context,  List<Stadiums> stadiumses) {
        this.stadiumses = stadiumses;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return stadiumses.size();
    }

    @Override
    public Object getItem(int position) {
        return stadiumses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHelder{

        SimpleDraweeView imageView;
        TextView textView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewHelder viewHelder;
        if(convertView == null){
            viewHelder = new ViewHelder();
            convertView = layoutInflater.inflate(R.layout.f_yjj_venues_list_item, null);
            viewHelder.textView = (TextView) convertView.findViewById(R.id.tvList2);
            viewHelder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.ItemImage2);
            convertView.setTag(viewHelder);
        }else{
            viewHelder = (ViewHelder) convertView.getTag();
        }

        Stadiums item = stadiumses.get(position);
        viewHelder.textView.setText(item.getSp_name());
        if (item.getSp_pic()!=null&&!item.getSp_pic().equals("")){
            viewHelder.imageView.setImageURI(Uri.parse(item.getSp_pic()));
        }
        return convertView;
    }
}

