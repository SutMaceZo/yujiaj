package com.chaotong.yujia.main.menu.xiulian.main.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.Order.SelectBean;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * �Զ���PopupWindow  ��Ҫ������ʾListView
 *
 * @param <T>
 * @param <T>
 * @author Ansen
 * @create time 2015-11-3
 */
public class SpinerPopWindowTwo<T> extends PopupWindow {
   /* private LayoutInflater inflater;
    private ListView mListView;
    private ListView mListView2;
    private List<SelectBean.DistrictsBean> list;
    private List<String> mlist;
    private int clickTemp = -1;

    private MyAdapter mAdapter;
    private MyAdapter2 myAdapter2;
    private int parentId = 0;

    public SpinerPopWindowTwo(Context context, List<SelectBean.DistrictsBean> list, OnItemClickListener clickListener) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.list = list;
        init(clickListener);
    }

    private void init(OnItemClickListener clickListener) {
        View view = inflater.inflate(R.layout.spiner_window_layout2, null);
        mlist = new ArrayList<String>();
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.l1);
        mListView2 = (ListView) view.findViewById(R.id.l2);

        if (list.get(parentId).getBusiness() != null) {
            mlist.addAll(list.get(parentId).getBusiness());
        }
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        myAdapter2 = new MyAdapter2(mlist);
        mListView2.setAdapter(myAdapter2);
        mListView2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        setSelection(0);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                parentId = i;
                setSelection(parentId);
                mAdapter.notifyDataSetChanged();
                if (mlist != null && mlist.size() > 0 && myAdapter2 != null) {
                    mlist.clear();
                    myAdapter2.notifyDataSetChanged();
                }

                if (list.get(parentId).getBusiness() != null
                        && list.get(parentId).getBusiness().size() >= 0) {
                    mlist.addAll(list.get(parentId).getBusiness());
                    myAdapter2.notifyDataSetChanged();
                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

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
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.spiner_item_layout, parent, false);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.i("result","clickTemp="+clickTemp);
            if (clickTemp == position) {
                holder.tvName.setBackgroundResource(R.color.white);
            } else {
                holder.tvName.setBackgroundResource(R.color.gainsboro);
            }
            holder.tvName.setText(list.get(position).getDistrict());
            return convertView;
        }
    }

    public void setSelection(int i) {
        clickTemp = i;
    }
    private class ViewHolder {
        private TextView tvName;
    }


    private class MyAdapter2 extends BaseAdapter {
        List<String> mlist;

        public MyAdapter2(List<String> slist) {
            this.mlist = slist;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder2 holder = null;
            if (convertView == null) {
                holder = new ViewHolder2();
                convertView = inflater.inflate(R.layout.spiner_item_layout, parent, false);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder2) convertView.getTag();
            }
            holder.tvName.setText(mlist.get(position));
            return convertView;
        }
    }

    private class ViewHolder2 {
        private TextView tvName;
    }
*/
}
