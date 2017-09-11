package com.chaotong.yujia.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Looper;


import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.UrlPath;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @ClassName: UploadCrashFileTask
 * @Description: 上传崩溃log
 * @author guojiandong@koolearn-inc.com
 * @date 2015年5月18日 下午7:12:30
 */
public class UploadCrashFileTask {

	private static Context context;
	 String filePath;

	String BOUNDARY = java.util.UUID.randomUUID().toString();
	String PREFIX = "--", LINEND = "\r\n";
	String MULTIPART_FROM_DATA = "multipart/form-data";


	private static UploadCrashFileTask uploadCrashFileTask;

	public static UploadCrashFileTask getInstance(Context context, String filePath) {

		uploadCrashFileTask = new UploadCrashFileTask(context, filePath);
		return uploadCrashFileTask;

	}
	UrlPath urlPath = UrlPath.getUrlPath();
	private UploadCrashFileTask(Context context, String filePath) {
		super();
		this.filePath = filePath;
		UploadCrashFileTask.context = context;
	}
	String url= urlPath.getUrl()+"SaveException";

	public void startAsyncTask() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Looper.prepare();
					uploadFile(url, filePath, "android");
					//upload(url,new File(filePath));
					//doPost(url,filePath,"android");
					Looper.loop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * 上传头像
	 * 
	 *
	 */

	public static void uploadFile(String url, String filePath, String type) {

		InputStream ins = null;
		ByteArrayOutputStream bos = null;
		HttpClient client = null;
		PostMethod post = null;

		SharedPreferences sp=context.getSharedPreferences(MyApplication.SpName,Context.MODE_PRIVATE);

		try {
			client = new HttpClient();
			post = new PostMethod(url);
			StringPart types = new StringPart("type", type, "UTF-8");
			StringPart memberId = new StringPart("memberId", sp.getString("reqid",""), "UTF-8");
			StringPart memberPhone = new StringPart("memberPhone",sp.getString("phonename",""), "UTF-8");
			StringPart phoneType = new StringPart("phoneType",Build.MODEL, "UTF-8");
			StringPart versionNum = new StringPart("versionNum", VersonUtils.getVersonCode(context), "UTF-8");
			StringPart phoneNumber = new StringPart("phoneNumber","", "UTF-8");

			MultipartRequestEntity requestEntity = null;
			FilePart filePart = new FilePart("exception", new File(filePath), null, "UTF-8");


			requestEntity = new MultipartRequestEntity(
					new Part[] { versionNum, types,phoneType,phoneNumber,memberId,memberPhone,filePart }, post.getParams());
			post.setRequestEntity(requestEntity);

			client.executeMethod(post);
			// 获取服务器响应的数据
			ins = post.getResponseBodyAsStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream();
			while ((len = ins.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			byte[] byteArray = bos.toByteArray();
			String jsonStr = new String(byteArray, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}

	}

	/**
	 * 网络请求
	 * 
	 *
	 */

	public static String upload(String url, File uploadFile) throws Exception {

		String CHARSET = "UTF-8";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";


		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(10 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		// for (Map.Entry<String, String> entry : params.entrySet()) {
		sb.append(PREFIX);
		sb.append(BOUNDARY);
		sb.append(LINEND);
		sb.append("Content-Disposition: form-data; name=\"" + "type" + "\"" + LINEND);
		sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
		sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
		sb.append(LINEND);
		sb.append("android");
		sb.append(LINEND);
		// }

		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		// if (files != null)
		// for (Map.Entry<String, File> file : files.entrySet()) {
		StringBuilder sb1 = new StringBuilder();
		sb1.append(PREFIX);
		sb1.append(BOUNDARY);
		sb1.append(LINEND);
		sb1.append("Content-Disposition: form-data; name=\"" + "file2" + "\"; filename=\"" + uploadFile.getName() + "\"" + LINEND);
		sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
		sb1.append(LINEND);
		outStream.write(sb1.toString().getBytes());

		InputStream is = new FileInputStream(uploadFile);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}

		is.close();
		outStream.write(LINEND.getBytes());
		// }

		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			int ch;
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2.toString();
	}

}
