package com.chaotong.yujia.main.menu.yujiajia.record.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.Hg_list;
import java.util.List;

/**
 * 兑换记录
 */
public class RecordListView extends ListView {

    List<Hg_list> list;
    ListView listView;
    private Context context;
    LayoutInflater layoutInflater;


    public RecordListView(Context context, List<Hg_list> list) {
        super(context);
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }


    public void run(Activity activity, Context context) {
        this.context = context;
        listView = (ListView) activity.findViewById(R.id.record_listView);
        setListView(activity, context);
    }

    private void setListView(final Activity activity, final Context context) {

   /*     ListViewAdapter adapter = new ListViewAdapter(context,
                list);
        listView.setAdapter(adapter);*/

    }
   /* public class ListViewAdapter extends BaseAdapter {
        Context context;
        List<Hg_list> list;

        public ListViewAdapter(Context _context, List<Hg_list> _list) {
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final viewHolder hodler;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.dhjl, null);
                hodler = new viewHolder();
                hodler.image = (ImageView) convertView.findViewById(R.id.jiaolian_image);
                hodler.yhj_name = (TextView) convertView.findViewById(R.id._name);
                hodler.yhj_date = (TextView) convertView.findViewById(R.id.yhj_date);
                hodler.yhj_sm = (TextView) convertView.findViewById(R.id.yhj_sm);
                hodler.hg_jf = (TextView) convertView.findViewById(R.id.pj);
                convertView.setTag(hodler);
            } else {
                hodler = (viewHolder) convertView.getTag();
            }
            ImageSize size=new ImageSize(80,80);
            DisplayImageOptions options=new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().loadImage(yhj.getHg_pic(),size,options,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    hodler.image.setImageBitmap(loadedImage);
                }
            });

        *//*    hodler.yhj_name.setText(list.get(position).);
            hodler.yhj_sm.setText(yhj.getYhj_sm());
            hodler.yhj_date.setText(list.get(position).getHg_date());
            hodler.hg_jf.setText("-"+list.get(position).getHg_jf());
            Log.i("thread",hodler.yhj_name.getText().toString()+"...");*//*

            return convertView;
        }

        class viewHolder {
            ImageView image;
            TextView yhj_name, yhj_sm, yhj_date, hg_jf;
        }
    }*/


}
