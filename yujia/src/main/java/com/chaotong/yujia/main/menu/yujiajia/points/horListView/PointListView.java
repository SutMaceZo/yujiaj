package com.chaotong.yujia.main.menu.yujiajia.points.horListView;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.YuHuiJuanBean;
import com.chaotong.yujia.main.property.yujiajia.Trainers;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-20.
 */
public class PointListView {
    GridView gridView;
    List<YuHuiJuanBean> list;
    public PointListView(List<YuHuiJuanBean> list){
        this.list = list;
    }

    public void run(Activity activity,Context context){
        gridView = (GridView) activity.findViewById(R.id.grid);
        setGridView(activity,context);
    }

    /**设置GirdView参数，绑定数据*/
    private void setGridView(Activity activity, final Context context) {

        int size = list.size();

        int length = 120;

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); //  设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); //设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(context,
                list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,"点击了"+(i+1),Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**GirdView 数据适配器*/
    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<YuHuiJuanBean> list;

        public GridViewAdapter(Context _context, List<YuHuiJuanBean> _list) {
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
            convertView = layoutInflater.inflate(R.layout.my_points_item, null);
            TextView yhj_name = (TextView) convertView.findViewById(R.id.yhj_name);
            TextView yhj_money = (TextView) convertView.findViewById(R.id.yhj_money);
            SimpleDraweeView iv_pic = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
          //  Item item = list.get(position);
            yhj_name.setText(list.get(position).getSp_name());
            yhj_money.setText(list.get(position).getYhj_money());
            if (list.get(position).getYhj_pic()!=null&&!list.get(position).getYhj_pic().equals("")){
               iv_pic.setImageURI(Uri.parse(list.get(position).getYhj_pic()));
            }

            return convertView;
        }
    }

}
