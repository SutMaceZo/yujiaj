package com.chaotong.yujia.main;

import java.security.SecureRandom;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chaotong.yujia.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/3.
 */
public class UrlPath extends BaseActivity{
    public  String Contant2 = "http://223.72.192.176:8080/YuJia/";
  // private static String Contant2 = "http://192.168.0.5:8080/YuJia/";
  // public  String Contant2 = "http://192.168.0.159:8080/YuJia/";
    //http://223.72.192.176:8080/yy/SelectSp

 // public  String Contant2 = "http://chaotong.tunnel.2bdata.com/YuJia";线上正式地址0
 //   public  String Contant2 = "http://chaotong.tunnel.2bdata.com/YuJiaTest";//线上测试地址1

  private static UrlPath urlPath=null;
    public static UrlPath getUrlPath(){
        if (urlPath == null){
            urlPath = new UrlPath();
            return urlPath;
        }
        return urlPath;
    }
    Context context;
    String yh_type = "0";
    public String getData(String yh_type){
        this.yh_type = yh_type;
        return yh_type;
    }
    public String getUrl(){

        // SharedPreferences preferences=getSharedPreferences("Yuga", Context.MODE_PRIVATE);
        //preferences = getSharedPreferences("Yuga", MODE_PRIVATE);
        //preferences.getString("yh_type","");
        //context.Log.i("-----------==-----",yh_type);
        if(yh_type==null|| yh_type.equals("") ||yh_type.equals("0")){
            Contant2 = "http://223.72.192.176:8080/YuJia/";
            //Contant2 = "http://chaotong.tunnel.2bdata.com/YuJiaTest/";
            return Contant2;
        }
        Contant2 = "http://223.72.192.176:8080/YuJia/";
        //Contant2 = "http://223.72.192.176:8080/YuJia/";
        return Contant2;
    }
   // public  String ZY_URL = getUrl() + "YuJia/TestServlet?region=x&reqid=x";//主页请求
   public  String ZY_URL =  "TestServlet?region=x&reqid=x";//主页请求
    public  String ZY_JLXQ ="TrainersDetailServlet?trainerid=x&reqid=x";//教练详情
    public  String ZY_CGXQ = "StadiumsDetailServlet?stadiumid=x&reqid=x";//场馆详情
    public  String ZY_FIND_MORE_TRANTERS =  "MoreTrainersServlet?more=x&region=x";//查看全部教练
    public  String YK_CGLB = "YuJia/StadiumsServlet?region=x&more=x&longitude=x&latitude=x";//场馆列表
    public  String YK_JLLB = "YuJia/TrainersServlet?more=x&region=x&type=x";//教练列表
    public  String YK_JLPJ =  "YuJia/TrainersPjServlet?trainerid=x&more=x";//教练评价
    public  String YK_CGPJ =  "YuJia/StadiumsPjServlet?stadiumid=x&more=x";//场馆评价
    public  String YK_FIND_CORJ = "YuJia/SearchServlet?search=x&more=x&type=x&region=x&longitude=x&latitude=x";//搜索场馆或教练
    public  String YK_KECHEGN_FIND_PRO = "YuJia/ClassServlet?&region=x";//课程查询条件
    public  String YK_KECHENG = "YuJia/ClassSearchServlet?reqid=x&region=x&business=x&date=x&classtype=x&longitude=x&latitude=x&more=x";//课程查询
    public  String YJL_JLLB = "YuJia/YjlTrainersServlet?more=x&region=x&type=x";//约教练
    public  String YJJ_YHJ =  "YuJia/MyYhjServlet?reqid=x";//优惠卷列表
    public  String YK_DDXX = "YuJia/OrderInfoServlet?classid=x";//订单信息
    public  String YY_FS = "YuJia/YyTypeServlet?yytype=x&reqid=x&stadiumid=x";//获取预约方式的ID
    public  String YY_DDXQ = "YyServlet?reqid=x&classid=x&orderid=x&yytype=x&yhj_id=x";//预约支付
    public  String YY_YHJLQ = "YhjListServlet?reqid=x&more=x";//优惠卷领取列表
    public  String YY_YHJDJ = "GetYhjServlet?reqid=x&yhj_id=x&dh_code=x&jf=x";//领取优惠卷
    public  String XXTS = "PushMsgServlet?reqid=x&push_type=x&more=x";//消息推送
    public  String XXSL = "eachonePushTypeNumServlet?reqid=x";//消息数量
    public  String XXDQ = "ChangeFlagServlet?reqid=x&id=1&push_type=x";//消息读取


}


