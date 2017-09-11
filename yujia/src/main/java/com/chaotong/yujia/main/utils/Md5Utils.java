package com.chaotong.yujia.main.utils;

import java.security.MessageDigest;

/**
 * ����MD5���ܽ���
 * @author tfq
 * @datetime 2011-10-13
 */
public class Md5Utils {

	/***
	 * MD5加密，32位
	 */
	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		String ss=hexValue.toString();
		String sss=fixString(ss);
		return sss;

	}
	public static String  fixString(String ss) {
		// TODO Auto-generated method stub
		char item01=ss.charAt(0);
		char item02=ss.charAt(1);
		String sss=ss.substring(2,ss.length());
		String ssss=sss+item01+item02;
		System.out.println("ssss:"+ssss);
		return ssss;
	}
	/**
	 * 加密一次，解密两次还原
	 */ 
	public static String convertMD5(String inStr){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	/*public static void main(String args[]) {
		String s = new String("123456");
		System.out.println("old:" + s);
		System.out.println("MD5:" + string2MD5(s));
		System.out.println("加密：" + convertMD5(s));
		System.out.println("解密：" + convertMD5(convertMD5(s)));

	}*/

}
