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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.thread.huiyuan_registThread;
import com.chaotong.yujia.main.thread.testPhoneThread;
import com.chaotong.yujia.main.utils.CountDownTimerUtils;
import com.chaotong.yujia.main.utils.Md5Utils;
import com.chaotong.yujia.main.utils.testUtils;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/26 0026.
 */

public class huiyuanRegist extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.touxiang)
     RelativeLayout touxiang_Layout;

    @Bind(R.id.xinbie)
    RelativeLayout xinbie_Layout;
    @Bind(R.id.nianling)
     RelativeLayout nianling_Layout;

    @Bind(R.id.regist_button)
     Button registbt_Layout;
    @Bind(R.id.phone)
     RelativeLayout phone_Layout;

    @Bind(R.id.hychangguan)
     RelativeLayout huiyuan_changguan;

    @Bind(R.id.rl_back02)
    RelativeLayout rl_back02;
    @Bind(R.id.back02)
     ImageView back02;

    @Bind(R.id.touxiang_img)
     ImageView touxiang_img;
    @Bind(R.id.true_name)
     EditText true_name;
    @Bind(R.id.true_kahao)
     EditText true_kaojao;

    @Bind(R.id.progress)
    ProgressBar progress;

    private TextView true_xinbie, true_nianling, true_phone, true_changguan;

    private int index01, index02;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final int SELECT_CHANGGUAN_HUIYUAN = 4;

    private static final String PHOTO_FILE_NAME = "temp_phione.png";// 结果 PHOTO_FILE_NAME

    private File tempFile;
    private Bitmap bitmap = null;
    UrlPath urlPath = UrlPath.getUrlPath();
    private String url =urlPath.getUrl() + "RegServlet";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            JSONObject object;
            if (json!=null){
                progress.setVisibility(View.INVISIBLE);
                registbt_Layout.setEnabled(true);
                try {
                    object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("message");
                    if (code.equals("1")) {
                        Toast.makeText(huiyuanRegist.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent inte = new Intent(huiyuanRegist.this, loginActivity.class);
                        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        inte.putExtra("tel",true_phone.getText().toString());
                        inte.putExtra("psw",passwords);
                        startActivity(inte);
                    } else {
                        Toast.makeText(huiyuanRegist.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huiyuan_reg);
        ButterKnife.bind(this);

        true_name = (EditText) findViewById(R.id.true_name);
        true_nianling = (TextView) findViewById(R.id.true_nianling);
        true_xinbie = (TextView) findViewById(R.id.true_xinbie);
        true_phone = (TextView) findViewById(R.id.true_phone);
        true_changguan = (TextView) findViewById(R.id.huiyuan_changguan);
        inflater = LayoutInflater.from(this);
        initEvent();
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
                Toast.makeText(huiyuanRegist.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.touxiang_img.setImageBitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == SELECT_CHANGGUAN_HUIYUAN) {
            if (data!=null){
                true_changguan.setText(data.getStringExtra("sp_name"));
            }
            String kind = true_changguan.getText().toString();
            if (("无所属场馆").equals(kind)) {
                true_kaojao.setText("无");
                true_kaojao.clearFocus();
                true_kaojao.setFocusable(false);
            } else {
                true_kaojao.setText("");
                true_kaojao.setFocusable(true);
                true_kaojao.setFocusableInTouchMode(true);
                true_kaojao.requestFocus();
                true_kaojao.findFocus();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    String passwords;

    public static int PHONECODE = 001;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_button:
                String name = true_name.getText().toString();
                String age = true_nianling.getText().toString();
             /*   String password = passwords;*/

                String tel = true_phone.getText().toString();
                String sex = true_xinbie.getText().toString();
                String cardnum = true_kaojao.getText().toString();
                String pic01 = "";
                String type = "会员注册";
                String format = "PNG";
                String stadiums = true_changguan.getText().toString();
                if (bitmap != null) {
                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        byte[] buffer = out.toByteArray();
                        byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
                        pic01 = new String(encode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!name.equals("") && !age.equals("") && !passwords.equals("") && !tel.equals("")
                            && !sex.equals("") && !cardnum.equals("") && !pic01.equals("")) {
                        String password = Md5Utils.string2MD5(passwords);
                        progress.setVisibility(View.VISIBLE);
                        registbt_Layout.setEnabled(false);

                        new huiyuan_registThread(huiyuanRegist.this, name, password, sex, age, tel, cardnum, pic01,
                                format, type, stadiums, handler, url).start();
                    } else {
                        Toast.makeText(huiyuanRegist.this, "请确定信息是否填写完整", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(huiyuanRegist.this, "请检查头像是否上传", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.touxiang:
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

            case R.id.xinbie:
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
                                true_xinbie.setText(type01[index01]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
            case R.id.nianling:
                final String[] ages = {"90后", "80后", "70后"};
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
                                true_nianling.setText(ages[index02]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            case R.id.phone:
                phone_get();
                break;
            case R.id.hychangguan:
                Intent in02 = new Intent(huiyuanRegist.this, CgSearchActivity.class);
                in02.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(in02, SELECT_CHANGGUAN_HUIYUAN);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.rl_back02:
                finish();
                break;
        }
    }

    private EditText dialog_phone, dialog_password, dialog_repassword, dialog_testnum;
    private Button dialog_yanzheng, dialog_makesure;
    private ProgressBar pb;
    LayoutInflater inflater;

    private String testPhone_url = urlPath.getUrl() + "VerificationTel";

    String vercode="";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if(json!=null){

                countDownTimerUtils.onFinish();
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message=object.getString("msg");
                    String verificationCode = object.getString("verificationCode");
                    if (code.equals("1")) {
                        dialog_testnum.requestFocus();
                        Toast.makeText(huiyuanRegist.this, "请稍后...", Toast.LENGTH_SHORT).show();
                        vercode=verificationCode;
                    } else if (code.equals("2")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(huiyuanRegist.this, message, Toast.LENGTH_SHORT).show();
                    } else if (code.equals("-1")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(huiyuanRegist.this, "服务器出现异常", Toast.LENGTH_SHORT).show();
                    }else if(code.equals("4")){
                        if (verificationCode!=null&&!("").equals(verificationCode)){
                            dialog_testnum.setText(verificationCode);
                            dialog_testnum.setEnabled(false);
                            vercode=verificationCode;
                            countDownTimerUtils.cancel();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    ImageView back;
    CountDownTimerUtils countDownTimerUtils;

    private void phone_get() {

        View layout = inflater.inflate(R.layout.test_phone, null);
        final Dialog dialog = new AlertDialog.Builder(huiyuanRegist.this, R.style.Dialog_Fullscreen)
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
                    Toast.makeText(huiyuanRegist.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Boolean S = testUtils.isPhone(dialog_phone.getText().toString());
                    if (S == false) {
                        Toast.makeText(huiyuanRegist.this, "请检查你的手机号是否输入正确", Toast.LENGTH_LONG).show();
                    } else {
                        countDownTimerUtils = new CountDownTimerUtils(dialog_yanzheng, 60000, 1000);
                        countDownTimerUtils.start();
                        new testPhoneThread(huiyuanRegist.this, dialog_phone.getText().toString(), mHandler, testPhone_url).start();
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
                if (!("").equals(code) && code != null&&code.equals(vercode)) {
                    if (PasswordNumber.equals("") || Re_PasswordNumber.equals("")) {
                        Toast.makeText(huiyuanRegist.this, "密码或确认密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        if (PasswordNumber.equals(Re_PasswordNumber)) {
                            true_phone.setText(dialog_phone.getText().toString());
                            passwords = PasswordNumber;
                            dialog.dismiss();
                        }else {
                            Toast.makeText(huiyuanRegist.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(huiyuanRegist.this, "请确认手机号是否验证成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    public void initEvent() {
        touxiang_Layout.setOnClickListener(this);
        xinbie_Layout.setOnClickListener(this);
        nianling_Layout.setOnClickListener(this);
        registbt_Layout.setOnClickListener(this);
        phone_Layout.setOnClickListener(this);
        huiyuan_changguan.setOnClickListener(this);
        rl_back02.setOnClickListener(this);
        true_kaojao.setFocusable(false);
    }

}
