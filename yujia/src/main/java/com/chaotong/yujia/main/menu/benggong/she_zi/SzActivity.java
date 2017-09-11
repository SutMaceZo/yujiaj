package com.chaotong.yujia.main.menu.benggong.she_zi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.utils.AppConfig;
import com.chaotong.yujia.main.utils.DataCleanManager;
import com.chaotong.yujia.main.utils.FileUtil;
import com.chaotong.yujia.main.utils.MethodsCompat;

import org.kymjs.kjframe.Core;

import java.io.File;
import java.util.Properties;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 设置
 */

public class SzActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.my_info)
     RelativeLayout my_info;

    @Bind(R.id.edit_password)
     RelativeLayout edit_password;

    @Bind(R.id.clear)
     RelativeLayout clear;

    @Bind(R.id.svn)
     RelativeLayout svn_info;

   /* @Bind(R.id.cancel_login)
     RelativeLayout cancel_login;*/

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;

    @Bind(R.id.cache)
    TextView tvCache;
    UrlPath urlPath = UrlPath.getUrlPath();
    SharedPreferences sp;
    String reqid="";
    String cancelUrl= urlPath.getUrl()+"DeleteRegistrationIDServlet?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sz);
        ButterKnife.bind(this);
        sp=getSharedPreferences("Yuga",MODE_PRIVATE);
        reqid=sp.getString("reqid","");
        addClick();
        caculateCacheSize();

       /* String verson=VersonUtils.getVersonCode(this);
        textView.setText(verson);*/


    }


     void addClick() {

        my_info.setOnClickListener(this);
        edit_password.setOnClickListener(this);
       // cancel_login.setOnClickListener(this);
        clear.setOnClickListener(this);
        svn_info.setOnClickListener(this);
         rl_bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_info:
                Intent intent01=new Intent(SzActivity.this,Edit_info.class);
                intent01.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent01);
                break;
            case R.id.edit_password:
                Intent intent=new Intent(SzActivity.this,updatePassword.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.clear:
                onClickCleanCache();
                break;
            case R.id.svn:
                Intent  intents=new Intent(SzActivity.this,SvnInfo.class);
                intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intents);
                break;
            case R.id.rl_bt_back:
                finish();
                break;
        }
    }

    private void onClickCleanCache() {
        getConfirmDialog( "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearAppCache();
                tvCache.setText("0KB");
            }
        }).show();
    }
    public  AlertDialog.Builder getConfirmDialog( String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog();
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
    public  AlertDialog.Builder getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder;
    }

    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getFilesDir();
        File cacheDir =getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(SzActivity.this);
            fileSize += FileUtil.getDirSize(externalCacheDir);
            fileSize += FileUtil.getDirSize(new File(
                    org.kymjs.kjframe.utils.FileUtils.getSDCardPath()
                            + File.separator + "KJLibrary/cache"));
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        tvCache.setText(cacheSize);
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    /**
     * 清除app缓存
     */
    public void myclearaAppCache() {
        DataCleanManager.clearAllCache(SzActivity.this);
        // 清除数据缓存
      /*  DataCleanManager.cleanInternalCache(SzActivity.this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(SzActivity.this));
        }*/
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        Core.getKJBitmap().cleanCache();
    }

    /**
     * 清除保存的缓存
     */
    public Properties getProperties() {
        return AppConfig.getAppConfig(SzActivity.this).get();
    }
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(SzActivity.this).remove(key);
    }
    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    myclearaAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = 2;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    ToastUtils.showToast(SzActivity.this,"清除失败");
                    break;
                case 1:
                    ToastUtils.showToast(SzActivity.this,"清除成功");
                    break;
            }
        };
    };

}
