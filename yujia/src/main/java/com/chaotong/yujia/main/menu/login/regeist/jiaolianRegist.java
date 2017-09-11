package com.chaotong.yujia.main.menu.login.regeist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan.SsAddActivity;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.jiaolianRegist_Thread;
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
 * Created by Administrator on 2016/8/2 0002.
 */

public class jiaolianRegist extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rl_back05)
    RelativeLayout rl_back05;
    @Bind(R.id.back05)
     ImageView back05;
    @Bind(R.id.jiaolian_touxiang_img)
     ImageView touxiang_image;
    @Bind(R.id.jiaolian_layout_phone)
     RelativeLayout phone_layout;
    @Bind(R.id.jiaolian_layout_touxiang)
     RelativeLayout touxiang_layout;
    @Bind(R.id.jiaolian_layout_xinbie)
     RelativeLayout xinbie_layout;
    @Bind(R.id.jiaolian_layout_nianling)
     RelativeLayout nianling_layout;
    @Bind(R.id.jiaolian_layout_changguan)
     RelativeLayout changguan_layout;
    @Bind(R.id.jiaolian_layout_kecheng)
     RelativeLayout kechen_layout;

    @Bind(R.id.jiaolian_layout_adress)
     RelativeLayout jiaolian_layout_adress;
    private TextView true_phone, true_sex, true_age, true_changguan,true_adress;
    private EditText dialog_phone,dialog_password,dialog_repassword,dialog_testnum;
    private Button dialog_yanzheng,dialog_makesure;
    @Bind(R.id.jiaolian_name)
     EditText jiaolian_nichen;
    @Bind(R.id.jiaolian_regist)
     Button regist_button;

    private ProgressBar dialog_pb;

    @Bind(R.id.progress)
     ProgressBar progress;

    @Bind(R.id.jiaolian_kecheng)
     EditText true_kechen;
    private int index01, index02;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String PHOTO_FILE_NAME = "temp_phione.png";// 结果 PHOTO_FILE_NAME
    private static final int SELECT_CHANGGUAN= 4;
    private static final int SELECT_CHANGGUAN_JIAOLIAN= 5;
    private static final int SELECT_ADRESS=6;
    private File tempFile;
    private Bitmap bitmap=null;
    LayoutInflater inflater;

    SharedPreferences sp;

    private String jiaolian_password = "";
    String adress_privence="";
    String adress_city="";
    String adress_district="";
    UrlPath urlPath = UrlPath.getUrlPath();
    private  String url = urlPath.getUrl()+"RegServlet";
    private String testPhone_url=urlPath.getUrl()+"VerificationTel";

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json=msg.obj.toString();
            if (json!=null){
                progress.setVisibility(View.INVISIBLE);
                regist_button.setEnabled(true);
                JSONObject object;
                try {
                    object = new JSONObject(json);
                    String code=object.getString("code");
                    String message=object.getString("message");
                    if (("1").equals(code)){
                        Toast.makeText(jiaolianRegist.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Intent inte=new Intent(jiaolianRegist.this,loginActivity.class);
                        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        inte.putExtra("tel",true_phone.getText().toString());
                        inte.putExtra("psw",jiaolian_password);
                        startActivity(inte);
                        finish();
                    }else{
                        Toast.makeText(jiaolianRegist.this,message,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                ToastUtils.showToast(jiaolianRegist.this,getResources().getString(R.string.error));
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaolian_reg);
        ButterKnife.bind(this);
       inflater   = LayoutInflater.from(this);
        true_phone = (TextView) findViewById(R.id.jiaolian_phone);
        true_sex = (TextView) findViewById(R.id.jiaolian_xinbie);
        true_age = (TextView) findViewById(R.id.jiaolian_nianling);
        true_changguan = (TextView) findViewById(R.id.jiaolian_changguan);
        true_kechen = (EditText) findViewById(R.id.jiaolian_kecheng);
        true_adress= (TextView) findViewById(R.id.jiaolian_adress);
        initEvent();

    }

    private void initEvent() {
        phone_layout.setOnClickListener(this);
        touxiang_layout.setOnClickListener(this);
        xinbie_layout.setOnClickListener(this);
        nianling_layout.setOnClickListener(this);
        changguan_layout.setOnClickListener(this);
        regist_button.setOnClickListener(this);
        rl_back05.setOnClickListener(this);
        jiaolian_layout_adress.setOnClickListener(this);

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
                Toast.makeText(jiaolianRegist.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.touxiang_image.setImageBitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(requestCode==SELECT_CHANGGUAN_JIAOLIAN){

            if (data!=null){
                true_changguan.setText(data.getStringExtra("sp_name"));
            }

        }else if(requestCode==SELECT_ADRESS){
            adress_privence=data.getStringExtra("privence");
            adress_city=data.getStringExtra("city");
            adress_district=data.getStringExtra("district");
            true_adress.setText(adress_privence+" "+adress_city+" "+adress_district);
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
            case R.id.jiaolian_regist:
                String name = jiaolian_nichen.getText().toString();
                String sex = true_sex.getText().toString();
                String age = true_age.getText().toString();
                String tel = true_phone.getText().toString();
                String stadiums = true_changguan.getText().toString();
                String classtype = true_kechen.getText().toString();
                String type = "教练注册";
                String format = "PNG";
                String province=adress_privence;
                String city=adress_city;
                String district=adress_district;
                String pic = "";
                if(bitmap!=null){
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
                    if(!("").equals(name)&&!("").equals(sex)&&!("").equals(tel)&&!("")
                            .equals(age)&&!("").equals(classtype)&&!("").equals(jiaolian_password)&&!("").
                            equals(stadiums)&&!("").equals(province)&&!("").equals(city)&&!("").equals(district)&&!("").equals(pic)){

                        String password= Md5Utils.string2MD5(jiaolian_password);
                    progress.setVisibility(View.VISIBLE);
                        regist_button.setEnabled(false);
                        new jiaolianRegist_Thread(url, type, name, password, sex, tel, stadiums, age, pic, format, classtype,
                                province, city, district, mhandler).start();
                    }else {
                        Toast.makeText(jiaolianRegist.this,"请确定信息是否全部填充",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(jiaolianRegist.this,"请确定信息是否全部填充",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.jiaolian_layout_phone:
                phone_get();
                break;
            case R.id.jiaolian_layout_touxiang:
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

            case R.id.jiaolian_layout_nianling:
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
                                true_age.setText(ages[index02]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            case R.id.jiaolian_layout_changguan:
                Intent in02=new Intent(jiaolianRegist.this,CgSearchActivity.class);
                in02.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(in02,SELECT_CHANGGUAN_JIAOLIAN);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                break;

            case R.id.jiaolian_layout_xinbie:
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
            case R.id.rl_back05:
                finish();
                break;
            case R.id.jiaolian_layout_adress:
                Intent in03=new Intent(jiaolianRegist.this,adressJiaolian.class);
                startActivityForResult(in03, SELECT_ADRESS);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

private ImageView bt_back;

    CountDownTimerUtils countDownTimerUtils;
    private void phone_get() {
        View layout = inflater.inflate(R.layout.test_phone,null);

        final Dialog dialog= new AlertDialog.Builder(jiaolianRegist.this,R.style.Dialog_Fullscreen)
              .setView(layout)
                .show();
        dialog_phone= (EditText) layout.findViewById(R.id.phone_number);
        dialog_password= (EditText) layout.findViewById(R.id.password_number);
        dialog_repassword= (EditText) layout.findViewById(R.id.re_password_number);
        dialog_testnum= (EditText) layout.findViewById(R.id.test_number);
        dialog_yanzheng= (Button) layout.findViewById(R.id.get_btn);
        dialog_makesure= (Button) layout.findViewById(R.id.make_sure02);
        bt_back= (ImageView) layout.findViewById(R.id.bt_back);
        dialog_pb= (ProgressBar) layout.findViewById(R.id.pb);
        dialog_yanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog_phone.equals("")){
                    Toast.makeText(jiaolianRegist.this,"手机号不能为空",Toast.LENGTH_LONG).show();
                }else {
                    Boolean S= testUtils.isPhone(dialog_phone.getText().toString());
                    if(S==false){
                        Toast.makeText(jiaolianRegist.this,"请检查你的手机号是否输入正确",Toast.LENGTH_LONG).show();
                    }else {

                        countDownTimerUtils = new CountDownTimerUtils(dialog_yanzheng, 60000, 1000);
                        countDownTimerUtils.start();

                      /*  dialog_pb.setVisibility(View.VISIBLE);
                        dialog_yanzheng.setEnabled(false);*/
                        new testPhoneThread(jiaolianRegist.this, dialog_phone.getText().toString(), mHandler,testPhone_url).start();
                    }
                }
            }
        });
        dialog_makesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PasswordNumber=dialog_password.getText().toString();
                String Re_PasswordNumber=dialog_repassword.getText().toString();
                String code=dialog_testnum.getText().toString();
                if(!("").equals(code)&&code!=null) {
                    if (PasswordNumber.equals("") || Re_PasswordNumber.equals("")) {
                        Toast.makeText(jiaolianRegist.this, "密码或确认密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        if (PasswordNumber.equals(Re_PasswordNumber)) {
                            true_phone.setText(dialog_phone.getText().toString());
                            jiaolian_password = PasswordNumber;
                            dialog.dismiss();
                        }else {
                            Toast.makeText(jiaolianRegist.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(jiaolianRegist.this,"请确认手机号是否验证成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    String vercode="";
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String json=msg.obj.toString();
            if (json!=null){
                countDownTimerUtils.onFinish();
                try {
                    JSONObject object=new JSONObject(json);
                    String code=object.getString("code");
                    String message=object.getString("msg");
                    String verificationCode=object.getString("verificationCode");
                    if (code.equals("1")) {
                        dialog_testnum.requestFocus();
                        Toast.makeText(jiaolianRegist.this, "请稍后...", Toast.LENGTH_SHORT).show();
                        vercode=verificationCode;
                    } else if (code.equals("2")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(jiaolianRegist.this, message, Toast.LENGTH_SHORT).show();
                    } else if (code.equals("-1")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(jiaolianRegist.this, "服务器出现异常", Toast.LENGTH_SHORT).show();
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
            }else {
                ToastUtils.showToast(jiaolianRegist.this,getResources().getString(R.string.error));
            }

        }
    };
}
