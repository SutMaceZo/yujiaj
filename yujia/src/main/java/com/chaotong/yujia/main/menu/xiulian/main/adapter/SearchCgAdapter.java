package com.chaotong.yujia.main.menu.xiulian.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.xiulian.main.view.SearchCgBean;
import com.chaotong.yujia.main.property.yujiajia.Stadiums;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class SearchCgAdapter extends BaseAdapter{
    private List<SearchCgBean.StadiumsBean> list;
    Context context;
    LayoutInflater inflater;

    public SearchCgAdapter(Context context, List<SearchCgBean.StadiumsBean> list) {
        this.context = context;
        this.list=list;
        inflater=LayoutInflater.from(context);
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final ViewHolder viewHelder;
        if(view == null){
            viewHelder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.order_cg, viewGroup,false);
            viewHelder.textView = (TextView) view.findViewById(R.id.swim_cg_name);
            viewHelder.imageview = (SimpleDraweeView) view.findViewById(R.id.swim_cg_image);
            viewHelder.swim_cg_address= (TextView) view.findViewById(R.id.swim_cg_address);
            viewHelder.distence= (TextView) view.findViewById(R.id.distence);
            view.setTag(viewHelder);
        }else{
            viewHelder = (ViewHolder) view.getTag();
        }
        if (list.get(i).getSp_pic()!=null){
            viewHelder.imageview.setImageURI(Uri.parse(list.get(i).getSp_pic()));
        }
        viewHelder.distence.setText(list.get(i).getDistance());
        viewHelder.textView.setText(list.get(i).getSp_name());
        viewHelder.swim_cg_address.setText(list.get(i).getAddress());
        return view;
    }


    public class ViewHolder{
        TextView textView,swim_cg_address,distence;
        SimpleDraweeView imageview;

    }

}
