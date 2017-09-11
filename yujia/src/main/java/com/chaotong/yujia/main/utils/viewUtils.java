package com.chaotong.yujia.main.utils;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文件名: com.goodwin.finance.util.ViewUtil
 * 作者:  熊杰 Wilson
 * 日期: 14-9-30
 * 时间: 14:28
 * 开发工具: IntelliJ IDEA 12.1.1
 * 开发语言: Java,Android
 * 开发框架:
 * 版本: v0.1
 * <strong>软件中所有与布局相关的工具类</strong>
 * <p></p>
 */
public class viewUtils {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取Listview的高度，然后设置ViewPager的高度
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildren1(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }

    public static  void ToastUtils(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static String corpString(String value) {
        //value=value.replace("市","");
        value=value+"市";
        return value;
    }
    public  static String subString(String value){
        value=value.replace("市","");
        return value;
    }

    public  static String Region(String value){
        value=value.replace("市","");
        value=value+"市";
        return value;
    }
    public static String  DeviceId(Context context){
        TelephonyManager tm= (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        tm.getDeviceId();
        return tm.getDeviceId();
    }

    public static String CropString(String value){
        value=value.substring(0,10);
        return value;
    }

    public static String CompressBitmap(String path){
      /* *//* Uri imageUri = uri;
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(imageUri, filePathColumns, null, null, null);
        c.moveToFirst();*//*
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String imagePath = c.getString(columnIndex);
        c.close();*/

// 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width= options.outWidth;
        int inSampleSize = 3; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if(minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float)minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = (int)ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(path, options); // 解码文件
        String pic=convertIconToString(bm);
        if (bm!=null&&!bm.isRecycled()){
            bm.recycle();
        }

       /* imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(bm);*/
        return pic;
    }

    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }
    public static Bitmap convertStringToIcon(String st)
    {

        Bitmap bitmap = null;
        try
        {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Bitmap  CameraPhoto(String path,ImageView imageView){
        FileInputStream fis = null;
        Bitmap bitmap=null;
        try {
            //把图片转化为字节流
            fis = new FileInputStream(path);
            //把流转化图片
            bitmap = BitmapFactory.decodeStream(fis);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
                fis.close();//关闭流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * API19以下获取图片路径的方法
     * @param uri
     */
    private String getFilePath_below19(Context context,Uri uri) {
        //这里开始的第二部分，获取图片的路径：低版本的是没问题的，但是sdk>19会获取不到
        String[] proj = {MediaStore.Images.Media.DATA};
        //好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor =context.getContentResolver().query(uri, proj, null, null, null);
        //获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        System.out.println("***************" + column_index);
        //将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        //最后根据索引值获取图片路径   结果类似：/mnt/sdcard/DCIM/Camera/IMG_20151124_013332.jpg
        String path = cursor.getString(column_index);
        System.out.println("path:" + path);
        return path;
    }



}
