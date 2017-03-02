package com.oribo.common;

import java.util.List;
import java.util.logging.Logger;
import org.apache.http.util.TextUtils;
import com.oribo.ReadExcelCase.DataBean;
import io.appium.java_client.android.AndroidElement;

/**
 * 设置测试数据，如清空文本框
 * @author Administrator
 *
 */


public class SetData {
	
	/**
	 * 连续清空Edittext文本
	 * 
	 */
	public static void clearTest(List<AndroidElement> edittext){
		for (int i = 0; i < edittext.size(); i++) {
			edittext.get(i).clear();			
		}
		
	}
	
	/**
	 * 设置数据第一个输入框的数据
	 * @param edittext
	 * @param logger
	 * @param inputdata
	 * @param underlineIndext
	 * @param info
	 */
	public static void setDatafirst(List<AndroidElement> edittext,Logger  logger ,String inputdata,String info){		
		String email = inputdata.substring(0,inputdata.indexOf("-"));
		System.out.println("email"+	email);
		logger.info(info+email);
		if (inputdata.indexOf("-")>0&&(!TextUtils.isEmpty(email )||!email.equals("null"))) {
			edittext.get(0).sendKeys(email);
		} 
	}

	/**
	 * 设置第二个输入框数据
	 * @param edittext
	 * @param logger
	 * @param inputdata
	 * @param underlineIndext
	 * @param info
	 */
	public static void setDatatwo( List<AndroidElement> edittext,Logger  logger,String inputdata,String info){
		System.out.println("setDatatwo");
		String password = inputdata.substring(inputdata.indexOf("-")+1,inputdata.length()); 
		logger.info(info+password);
		System.out.println("password "+	password );
		if (inputdata.indexOf("-")>0&&(!TextUtils.isEmpty(password )||!password.equals("null"))) {
			System.out.println("密码开始");
			edittext.get(1).sendKeys(password);
			// newSleep(1);
			//System.out.println(edittext.get(1).getText());
		} 
		//System.out.println("setpassword");
	}
	
	
}
