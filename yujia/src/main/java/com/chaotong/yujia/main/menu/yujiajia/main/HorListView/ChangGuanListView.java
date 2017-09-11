package com.chaotong.yujia.main.menu.yujiajia.main.HorListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.CgxqActivity;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.CgListView;
import com.chaotong.yujia.main.property.yujiajia.Data;
import com.chaotong.yujia.main.property.yujiajia.Stadiums;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-20.
 */
public class ChangGuanListView extends ListView {

    List<Item> itemList;
    ListView listView;
    private Data data;
    private List<Stadiums> stadiums;

    public ChangGuanListView(Context context, Data data) {
        super(context);
        this.data = data;
        stadiums = new ArrayList<Stadiums>();
        stadiums = data.getStadiums();
    }


    public void run(Activity activity, Context context) {
        listView = (ListView) activity.findViewById(R.id.cg_lv);
        setData();
        setListView(activity, context);
    }


    /*
    * 场馆list*/
    private void setData() {
        itemList = new ArrayList<Item>();
        for (int i = 0; i < stadiums.size(); i++) {
            Item item = new Item();
            item.setListPhoto(stadiums.get(i).getSp_pic());
            item.setListName(stadiums.get(i).getSp_name());
            itemList.add(item);
        }
    }

    /*
    * 场馆
    * */
    private void setListView(final Activity activity, final Context context) {

       /* ListViewAdapter adapter = new ListViewAdapter(context,
                itemList);
        listView.setAdapter(adapter);
        new Utility().setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, CgxqActivity.class);
                intent.putExtra("id", stadiums.get(i).getSp_id());
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });*/
    }

    public class ListViewAdapter extends BaseAdapter {
        Context context;
        List<Stadiums> list;

        public ListViewAdapter(Context _context, List<Stadiums> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            int i = list.size();
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

        class ViewHelder {

            SimpleDraweeView imageView;
            TextView textView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final ViewHelder viewHelder;
            if (convertView == null) {
                viewHelder = new ViewHelder();
                convertView = layoutInflater.inflate(R.layout.f_yjj_venues_list_item, parent, false);
                viewHelder.textView = (TextView) convertView.findViewById(R.id.tvList2);
                viewHelder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.ItemImage2);
                convertView.setTag(viewHelder);
            } else {
                viewHelder = (ViewHelder) convertView.getTag();
            }


            viewHelder.textView.setText(list.get(position).getSp_name());

            if (list.get(position).getSp_pic() != null && !list.get(position).getSp_pic().equals("")) {
                viewHelder.imageView.setImageURI(Uri.parse(list.get(position).getSp_pic()));
            }
            return convertView;
        }
    }

    public class Item {
        private String listPhoto;
        private String listName;

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }

        public String getListPhoto() {
            return listPhoto;
        }

        public void setListPhoto(String listPhoto) {
            this.listPhoto = listPhoto;
        }
    }


}
