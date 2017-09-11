package com.chaotong.yujia.main.menu.login.regeist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.changguanlist_adapter;
import com.chaotong.yujia.main.entity.changguanList;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.thread.guanyuanRegist_Thread;
import com.chaotong.yujia.main.thread.testPhoneThread;
import com.chaotong.yujia.main.utils.CountDownTimerUtils;
import com.chaotong.yujia.main.utils.Md5Utils;
import com.chaotong.yujia.main.utils.testUtils;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

public class guanyuanRegist extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rl_back08)
    RelativeLayout rl_back08;
    @Bind(R.id.back08)
     ImageView back08;
    @Bind(R.id.guanyuan_regist)
     Button registBtn;
    @Bind(R.id.progress)
     ProgressBar progress;

    @Bind(R.id.guanyuan_touxiang)
     ImageView true_image;

    @Bind(R.id.guanyuan_layout_touxiang)
     RelativeLayout touxiang_layout;

    @Bind(R.id.guanyuan_layout_xinbie)
     RelativeLayout sex_layout;
    @Bind(R.id.guanyuan_layout_nianling)
     RelativeLayout age_layout;
    @Bind(R.id.guanyuan_layout_phone)
     RelativeLayout phone_layout;
    @Bind(R.id.guanyuan_layout_changguan)
     RelativeLayout changguan_layout;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private File tempFile;
    private Bitmap bitmap = null;
    private int index01, index02;

    private TextView true_phone, true_sex, true_age, true_changguan;

    @Bind(R.id.guanyuan_name)
     EditText true_name;
    private static final String PHOTO_FILE_NAME = "temp_phione.png";// 结果 PHOTO_FILE_NAME

    private static final int SELECT_CHANGGUAN_GUANYUAN = 5;
    private static final int SELECT_CHANGGUAN = 4;

    private AutoCompleteTextView select_edit;
    private Button select;
    private ListView listview;
    private changguanlist_adapter adapter;
    private List<changguanList> mlist;


    LayoutInflater inflater;
    private EditText dialog_phone, dialog_password, dialog_repassword, dialog_testnum;
    private Button dialog_yanzheng, dialog_makesure;
    private String guanyuan_password = "";
    private ProgressBar pb;

    UrlPath urlPath = UrlPath.getUrlPath();
    private String url = urlPath.getUrl() + "RegServlet";
    private String test_changguan_url = urlPath.getUrl() + "AllStadiums";
    private String testPhone_url = urlPath.getUrl() + "VerificationTel";

    private View layout01;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json != null) {
                progress.setVisibility(View.INVISIBLE);
                registBtn.setEnabled(true);
                JSONObject object;
                try {
                    object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("message");
                    if (code.equals("1")) {
                        Toast.makeText(guanyuanRegist.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent inte = new Intent(guanyuanRegist.this, loginActivity.class);
                        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        inte.putExtra("tel",true_phone.getText().toString());
                        inte.putExtra("psw",guanyuan_password);

                        startActivity(inte);
                        finish();
                    } else {
                        Toast.makeText(guanyuanRegist.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanyuan_reg);
        ButterKnife.bind(this);

        inflater = LayoutInflater.from(this);
        true_age = (TextView) findViewById(R.id.guanyuan_nianling);
        true_sex = (TextView) findViewById(R.id.guanyuan_xinbie);
        true_changguan = (TextView) findViewById(R.id.guanyuan_changguan);
        true_phone = (TextView) findViewById(R.id.guanyuan_phone);

        initEvent();
    }

    private void initEvent() {
        touxiang_layout.setOnClickListener(this);
        sex_layout.setOnClickListener(this);
        age_layout.setOnClickListener(this);
        changguan_layout.setOnClickListener(this);
        phone_layout.setOnClickListener(this);
        rl_back08.setOnClickListener(this);
        registBtn.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(guanyuanRegist.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if(data!=null){
                try {
                    bitmap = data.getParcelableExtra("data");
                    this.true_image.setImageBitmap(bitmap);
                    boolean delete = tempFile.delete();
                    System.out.println("delete = " + delete);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else if (requestCode == SELECT_CHANGGUAN_GUANYUAN) {
            if (data!=null){
                true_changguan.setText(data.getStringExtra("sp_name"));
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guanyuan_layout_touxiang:
                String[] item = {"获取图库照片", "拍照获取照片"};
                new AlertDialog.Builder(this).
                        setTitle("请选择：").
                        setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                                        break;
                                    case 1:
                                        Intent intent01 = new Intent("android.media.action.IMAGE_CAPTURE");
                                        // 判断存储卡是否可以用，可用进行存储
                                        if (hasSdcard()) {
                                            intent01.putExtra(MediaStore.EXTRA_OUTPUT,
                                                    Uri.fromFile(new File(Environment
                                                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
                                        }
                                        startActivityForResult(intent01, PHOTO_REQUEST_CAMERA);
                                        break;
                                }
                            }
                        }).show();
                break;
            case R.id.guanyuan_layout_xinbie:
                final String[] type01 = {"男", "女"};
                new AlertDialog.Builder(this).setTitle("请选择性别：").
                        setSingleChoiceItems(type01, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index01 = which;
                            }
                        }).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                true_sex.setText(type01[index01]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.guanyuan_layout_changguan:
                Intent in02 = new Intent(guanyuanRegist.this, CgSearchActivity.class);
                in02.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(in02, SELECT_CHANGGUAN_GUANYUAN);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.guanyuan_layout_phone:
                phone_get();
                break;
            case R.id.guanyuan_layout_nianling:
                final String[] ages = {"90后","80后","70后"};
                new AlertDialog.Builder(this).setTitle("请选择年龄：").
                        setSingleChoiceItems(ages, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index02 = which;
                            }
                        }).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                true_age.setText(ages[index02]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;

            case R.id.rl_back08:
                finish();
                break;
            case R.id.guanyuan_regist:
                String name = true_name.getText().toString();


                String sex = true_sex.getText().toString();
                String age = true_age.getText().toString();
                String tel = true_phone.getText().toString();
                String stadiums = true_changguan.getText().toString();
                String type = "馆员注册";
                String format = "PNG";
                String pic = "";
                if (bitmap != null) {
                    try {
                        ByteArrayOutputStream out01 = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out01);
                        out01.flush();
                        out01.close();
                        byte[] buffer = out01.toByteArray();
                        byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
                        pic = new String(encode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!("").equals(name) && !("").equals(guanyuan_password) && !("").equals(sex) &&
                            !("").equals(stadiums) && !("").equals(age) && !("").equals(pic)) {

                        String password = Md5Utils.string2MD5(guanyuan_password);
                        progress.setVisibility(View.VISIBLE);
                        registBtn.setEnabled(false);
                        new guanyuanRegist_Thread(url, type, name, password, sex, tel, stadiums, age, pic, format, mhandler).start();
                    } else {
                        Toast.makeText(guanyuanRegist.this, "请确定信息是否全部填充", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(guanyuanRegist.this, "请确定信息是否全部填充", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    ImageView back;
    CountDownTimerUtils countDownTimerUtils;
    private void phone_get() {
        View layout = inflater.inflate(R.layout.test_phone, null);
        final Dialog dialog = new AlertDialog.Builder(guanyuanRegist.this, R.style.Dialog_Fullscreen)
                .setView(layout)
                .show();
        back = (ImageView) layout.findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog_phone = (EditText) layout.findViewById(R.id.phone_number);
        dialog_password = (EditText) layout.findViewById(R.id.password_number);
        dialog_repassword = (EditText) layout.findViewById(R.id.re_password_number);
        dialog_testnum = (EditText) layout.findViewById(R.id.test_number);
        dialog_yanzheng = (Button) layout.findViewById(R.id.get_btn);
        dialog_makesure = (Button) layout.findViewById(R.id.make_sure02);
        pb= (ProgressBar) layout.findViewById(R.id.pb);
        dialog_yanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_phone.equals("")) {
                    Toast.makeText(guanyuanRegist.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Boolean S = testUtils.isPhone(dialog_phone.getText().toString());
                    if (S == false) {
                        Toast.makeText(guanyuanRegist.this, "请检查你的手机号是否输入正确", Toast.LENGTH_LONG).show();
                    } else {
                        countDownTimerUtils = new CountDownTimerUtils(dialog_yanzheng, 60000, 1000);
                        countDownTimerUtils.start();
                        new testPhoneThread(guanyuanRegist.this, dialog_phone.getText().toString(), mHandler, testPhone_url).start();
                    }
                }
            }
        });
        dialog_makesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PasswordNumber = dialog_password.getText().toString();
                String Re_PasswordNumber = dialog_repassword.getText().toString();
                String code = dialog_testnum.getText().toString();
                if (!("").equals(code) && code != null) {


                    if (PasswordNumber.equals("") || Re_PasswordNumber.equals("")) {
                        Toast.makeText(guanyuanRegist.this, "密码或确认密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        if (PasswordNumber.equals(Re_PasswordNumber)) {
                            true_phone.setText(dialog_phone.getText().toString());
                            guanyuan_password = PasswordNumber;
                            dialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(guanyuanRegist.this, "请确认手机号是否验证成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json!=null){
              /*  pb.setVisibility(View.INVISIBLE);
                dialog_yanzheng.setEnabled(true);*/

                countDownTimerUtils.onFinish();

                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message=object.getString("msg");
                    String verificationCode = object.getString("verificationCode");

                    if (code.equals("1")) {
                        dialog_testnum.requestFocus();
                        Toast.makeText(guanyuanRegist.this, "请稍后...", Toast.LENGTH_SHORT).show();
                       // vercode=verificationCode;
                    } else if (code.equals("2")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(guanyuanRegist.this, message, Toast.LENGTH_SHORT).show();
                    } else if (code.equals("-1")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(guanyuanRegist.this, "服务器出现异常", Toast.LENGTH_SHORT).show();
                    }else if(code.equals("4")){
                        if (verificationCode!=null&&!("").equals(verificationCode)){
                            dialog_testnum.setText(verificationCode);
                            dialog_testnum.setEnabled(false);
                            countDownTimerUtils.cancel();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}