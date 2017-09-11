package com.chaotong.yujia.main.menu.login.regeist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

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
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.testPhoneThread;
import com.chaotong.yujia.main.utils.CountDownTimerUtils;
import com.chaotong.yujia.main.utils.Md5Utils;
import com.chaotong.yujia.main.utils.PhotoUtils;
import com.chaotong.yujia.main.utils.ZipUtils;
import com.chaotong.yujia.main.utils.testUtils;
import com.chaotong.yujia.main.utils.viewUtils;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

public class guanzhuRegist extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_back09)
    RelativeLayout rl_back09;
    @Bind(R.id.back09)
    ImageView back09;
    @Bind(R.id.guanzhu_regist)
    Button registBtn;
    @Bind(R.id.progress)
    ProgressBar progress;

    @Bind(R.id.guanzhu_touxiang)
    ImageView true_image;

    @Bind(R.id.guanzhu_layout_touxiang)
    RelativeLayout touxiang_layout;

    @Bind(R.id.guanzhu_layout_xinbie)
    RelativeLayout sex_layout;
    @Bind(R.id.guanzhu_layout_nianling)
    RelativeLayout age_layout;
    @Bind(R.id.guanzhu_layout_phone)
    RelativeLayout phone_layout;
    @Bind(R.id.guanzhu_layout_changguan)
    RelativeLayout changguan_layout;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final int RESULT_OK = 4;// 从changguan_info中得到的结果

    private File tempFile;
    private Bitmap bitmap = null;
    private int index01, index02;
    private TextView true_phone, true_sex, true_age, true_changguan;
    //场馆的三张图片的String码
    private List<String> pic = new ArrayList<String>();
    //场馆所在的地区及场馆名称
    private String provice = "";
    private String changguan_city = "";
    private String changguan_district = "";
    private String changguan_name = "";

    private ArrayList<String> imagePaths = null;
    private List<String> mPic;

    private String changguan_street = "";
    private String mobile = "";

    private String changguan_latitude = "";
    private String changguan_longitude = "";
    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.guanzhu_name)
    EditText true_name;
    private static final String PHOTO_FILE_NAME = "temp_phione.png";// 结果 PHOTO_FILE_NAME
    private  String url =urlPath.getUrl() + "RegServlet";

    LayoutInflater inflater;
    private EditText dialog_phone, dialog_password, dialog_repassword, dialog_testnum;
    private Button dialog_yanzheng, dialog_makesure;
    private String guanzhu_password = "";
    private ProgressBar dialog_pb;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanzhu_reg);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        true_age = (TextView) findViewById(R.id.guanzhu_nianling);
        true_sex = (TextView) findViewById(R.id.guanzhu_xinbie);
        true_changguan = (TextView) findViewById(R.id.guanzhu_changguan);
        true_phone = (TextView) findViewById(R.id.guanzhu_phone);
        initEvent();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void initEvent() {
        touxiang_layout.setOnClickListener(this);
        sex_layout.setOnClickListener(this);
        age_layout.setOnClickListener(this);
        changguan_layout.setOnClickListener(this);
        phone_layout.setOnClickListener(this);
        rl_back09.setOnClickListener(this);
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
                Toast.makeText(guanzhuRegist.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                try {
                    bitmap = data.getParcelableExtra("data");
                    this.true_image.setImageBitmap(bitmap);
                    boolean delete = tempFile.delete();
                    System.out.println("delete = " + delete);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CG_INFO_RESULT_OK) {
            if (data != null) {
                provice = data.getStringExtra("provice");
                changguan_city = data.getStringExtra("city");
                changguan_district = data.getStringExtra("district");
              /*  changguan_latitude = data.getStringExtra("latitude");
                changguan_longitude = data.getStringExtra("longitude");*/
                mobile = data.getStringExtra("mobile");
                changguan_street = data.getStringExtra("street");
                true_changguan.setText(data.getStringExtra("changguan_name"));
                imagePaths = data.getStringArrayListExtra("imagelist");
                changguan_latitude=data.getStringExtra("lat");
                changguan_longitude=data.getStringExtra("long");
            //    initLatlong(provice, changguan_city, changguan_district, changguan_street);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initLatlong(String provice, String city, String district, String street) {
        float latitude = 0;
        float longitude = 0;
        Geocoder coder = new Geocoder(this, Locale.CHINA);
        try {
            List<Address> list = coder.getFromLocationName(provice + city + district + street, 2);
            if (list.size() > 0) {
                changguan_latitude = String.valueOf((float) (list.get(0).getLatitude()));
                changguan_longitude = String.valueOf((float) (list.get(0).getLongitude()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("info", "lat:" + changguan_latitude + " long:" + changguan_longitude);
    }

    private static int CG_INFO_RESULT_OK = 007;

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
            case R.id.guanzhu_layout_touxiang:
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
            case R.id.guanzhu_layout_xinbie:
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
            case R.id.guanzhu_layout_changguan:
                Intent inte = new Intent(guanzhuRegist.this, changguan_info.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(inte, CG_INFO_RESULT_OK);
                break;
            case R.id.guanzhu_layout_phone:
                phone_get();
                break;
            case R.id.guanzhu_layout_nianling:
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
            case R.id.rl_back09:
                SharedPreferences sp = getSharedPreferences("Cg", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                finish();
                break;
            case R.id.guanzhu_regist:
                String name = true_name.getText().toString();
                String tel = true_phone.getText().toString();

                String sex = true_sex.getText().toString();
                String age = true_age.getText().toString();
                String type = "馆主注册";
                String pic = "";
                if (bitmap != null) {
                    try {
                        ByteArrayOutputStream out01 = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 30, out01);
                        out01.flush();
                        out01.close();
                        byte[] buffer = out01.toByteArray();
                        byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
                        pic = new String(encode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String format = "PNG";
                    String province = provice;
                    String city = changguan_city;
                    String district = changguan_district;
                    String latitude = changguan_latitude;
                    String longitude = changguan_longitude;
                    String stadiumsname = true_changguan.getText().toString();
                    String street = changguan_street;
                    String mobil = mobile;
                    if (!("").equals(name) && !("").equals(guanzhu_password) && !("").equals(sex) && !("").equals(tel) &&
                            !("").equals(mobil) && !("").equals(pic) && !("").equals(stadiumsname)
                            && !("").equals(province) && !("").equals(city) && !("").equals(district) && !("").equals(street)
                            && !("").equals(latitude) && !("").equals(longitude)) {

                        String password = Md5Utils.string2MD5(guanzhu_password);
                        JSONObject JSONObject = new JSONObject();

                        try {
                            JSONObject.put("name", name);
                            JSONObject.put("password", password);
                            JSONObject.put("sex", sex);
                            JSONObject.put("age", age);
                            JSONObject.put("tel", tel);
                            JSONObject.put("mobile", mobil);
                            JSONObject.put("pic", pic);
                            JSONObject.put("type", type);
                            JSONObject.put("format", format);
                            JSONObject.put("stadiums", stadiumsname);
                            JSONObject.put("province", province);
                            JSONObject.put("city", city);
                            JSONObject.put("district", district);
                            JSONObject.put("street", street);
                            JSONObject.put("longitude", longitude);
                            JSONObject.put("latitude", latitude);
                            JSONArray array = new JSONArray();
                            JSONArray array1 = new JSONArray();

                            for (int i = 0; i < imagePaths.size(); i++) {
                                final String path = imagePaths.get(i);
                                Log.i("info", "path:" + path);
                                final Uri uri;

                                if (path.startsWith("http")) {
                                    uri = Uri.parse(path);
                                } else {
                                    uri = Uri.fromFile(new File(path));
                                }
                              /*  String Picpath=initpic(uri);*/
                                String Picpath= PhotoUtils.getPath(guanzhuRegist.this,uri);
                               Log.i("info","path"+Picpath);
                                String picture= viewUtils.CompressBitmap(Picpath);
                                array.put(picture);
                                array1.put("PNG");
                            }
                            JSONObject.put("photos", array);
                            JSONObject.put("venueformat", array1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String content = String.valueOf(JSONObject);

                        progress.setVisibility(View.VISIBLE);
                        registBtn.setEnabled(false);

                          new ThreadUtils(this, url, content, handler).start();
                    } else {
                        registBtn.setEnabled(true);
                        Toast.makeText(guanzhuRegist.this, "请确定信息是否全部填充", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    registBtn.setEnabled(true);
                    Toast.makeText(guanzhuRegist.this, "请确定信息是否全部填充", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private String  initpic(Uri uri) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//4.4及以上
            String wholeID = DocumentsContract.getDocumentId(uri);

            final String[] split = wholeID.split(":");
            final String type = split[0];

            String[] column = {MediaStore.Images.Media.DATA} ;
            String sel =MediaStore.Images.Media._ID+"=?";
            Cursor cursor =getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,column,
                    sel,new String[]{type},null);
            int columnIndex=cursor.getColumnIndex(column[0]);
            String filePath="";
            if(cursor.moveToFirst())
            {
                filePath=cursor.getString(columnIndex);
            }
            cursor.close();
           return filePath;
        } else {//4.4以下，即4.4以上获取路径的方法
            String[] projection = { MediaStore.Images.Media.DATA } ;
            Cursor cursor =getContentResolver().query(uri,projection,null, null,null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath="";
            filePath=cursor.getString(column_index);
            return filePath;
        }
    }

    private Handler handler = new Handler() {
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
                    Log.i("guanzhu", "code:" + code);
                    if (code.equals("1")) {
                        Toast.makeText(guanzhuRegist.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent inte = new Intent(guanzhuRegist.this, loginActivity.class);

                        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        inte.putExtra("tel", true_phone.getText().toString());
                        inte.putExtra("psw", guanzhu_password);

                        SharedPreferences sp = getSharedPreferences("Cg", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                    /*    changguan_info.PIC01 = "";
                        changguan_info.PIC02 = "";
                        changguan_info.PIC03 = "";*/
                        startActivity(inte);
                        finish();
                    } else {
                        Toast.makeText(guanzhuRegist.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };

    private String testphone_url = urlPath.getUrl() + "VerificationTel";
    ImageView bt_back;
    CountDownTimerUtils countDownTimerUtils;

    private void phone_get() {
        View layout = inflater.inflate(R.layout.test_phone, null);
        final Dialog dialog = new AlertDialog.Builder(guanzhuRegist.this, R.style.Dialog_Fullscreen)
                .setView(layout)
                .show();
        bt_back = (ImageView) layout.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
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
        dialog_pb = (ProgressBar) layout.findViewById(R.id.pb);
        dialog_yanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_phone.equals("")) {
                    Toast.makeText(guanzhuRegist.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Boolean S = testUtils.isPhone(dialog_phone.getText().toString());
                    if (S == false) {
                        Toast.makeText(guanzhuRegist.this, "请检查你的手机号是否输入正确", Toast.LENGTH_LONG).show();
                    } else {
                       /* dialog_pb.setVisibility(View.INVISIBLE);
                        dialog_yanzheng.setEnabled(false);*/
                        countDownTimerUtils = new CountDownTimerUtils(dialog_yanzheng, 60000, 1000);
                        countDownTimerUtils.start();

                        new testPhoneThread(guanzhuRegist.this, dialog_phone.getText().toString(),
                                mHandler, testphone_url).start();
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
                    if (("").equals(dialog_phone.getText().toString()) || ("").equals(PasswordNumber) || ("").equals(Re_PasswordNumber)) {
                        Toast.makeText(guanzhuRegist.this, "请确认信息是否填写完整", Toast.LENGTH_SHORT).show();
                    } else {
                        if (PasswordNumber.equals(Re_PasswordNumber)) {
                            true_phone.setText(dialog_phone.getText().toString());
                            guanzhu_password = PasswordNumber;
                            dialog.dismiss();
                        } else {
                            ToastUtils.showToast(guanzhuRegist.this, "两次密码输入不一致");
                        }
                    }
                } else {
                    Toast.makeText(guanzhuRegist.this, "请确认手机号是否验证成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json != null) {
              /*  dialog_pb.setVisibility(View.INVISIBLE);
                dialog_yanzheng.setEnabled(true);*/
                countDownTimerUtils.onFinish();
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    String verificationCode = object.getString("verificationCode");

                    if (code.equals("1")) {
                        dialog_testnum.requestFocus();
                        Toast.makeText(guanzhuRegist.this, "请稍后...", Toast.LENGTH_SHORT).show();
                        // vercode=verificationCode;
                    } else if (code.equals("2")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(guanzhuRegist.this, message, Toast.LENGTH_SHORT).show();
                    } else if (code.equals("-1")) {
                        countDownTimerUtils.cancel();
                        Toast.makeText(guanzhuRegist.this, "服务器出现异常", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("4")) {
                        if (verificationCode != null && !("").equals(verificationCode)) {
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
