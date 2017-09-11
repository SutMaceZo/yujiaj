package com.chaotong.yujia.main.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class testUtils {
    public static Boolean isPhone(String phone_number) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher matcher = p.matcher(phone_number);
        return matcher.matches();
    }

    public static Boolean isPassword(String password) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = p.matcher(password);
        return matcher.matches();
    }
    /*验证能验证：

1：010-12345678  的情况

2：0123-12345678的情况。

3：正常的手机号13号段，15号段。18号段的号码。

4：能在固话后面添加分机号验证。 我当前设置的规则是只验证3到5位的分机号 如：010-12345678-0123 可以通过*/
    public  static  Boolean isTel(String tel){

      //  Pattern p= Pattern.compile("^(0\\d{2,3}-\\d{7,8}(-\\d{3,5}){0,1})|(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})$");
       Pattern p=Pattern.compile("^\\d{7,16}$");

        Matcher matcher=p.matcher(tel);
        return matcher.matches();
    }


    public static boolean isConn(Context context){
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }
}
