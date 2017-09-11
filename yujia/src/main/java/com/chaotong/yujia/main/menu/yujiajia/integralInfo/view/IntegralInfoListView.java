package com.chaotong.yujia.main.menu.yujiajia.integralInfo.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.SZ;
import com.chaotong.yujia.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-20.
 */
public class IntegralInfoListView extends ListView{

    List<SZ> list;
    ListView listView;
    public IntegralInfoListView(Context context, List<SZ> list) {
        super(context);
        this.list=list;
    }


    public void run(Activity activity, Context context){
        listView = (ListView) activity.findViewById(R.id.record_listView);
        setListView(activity,context);
    }

    /**
     * 测试用的data,实际信息都是数据库来的
     */


    private void setListView(final Activity activity, final Context context) {

        ListViewAdapter adapter = new ListViewAdapter(context,
                list);
        listView.setAdapter(adapter);
    }

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
            tv_name.setText(list.get(position).getReason());
            tv_num.setText(list.get(position).getJf());
            return convertView;
        }
    }

}
