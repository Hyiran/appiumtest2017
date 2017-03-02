package com.oribo.common;

import io.appium.java_client.android.AndroidElement;

/**
公用的按钮获取
 * @author Administrator
 *
 */
public class CommonButton {
	
	/**
	 * 返回按钮获取点击
	 */
	public static void backButton(){
		AndroidElement   backIcon =  Driver.getAndroidDriver(null).findElementById("com.orvibo.homemate:id/leftTextView");
		  backIcon.click();
	}

}
