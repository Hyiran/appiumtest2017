package com.oribo.utils;

/**
 * 字符串的操作类
 * @author Administrator
 *
 */
public class StrUtils {
	/**
	 * 
	 * @param phone  手机号码
	 * @return  返回  “134****2317”类似的字符串
	 */
	public static String strPhone(String phone){
		return phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());	
	}

}
