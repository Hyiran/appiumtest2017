package com.oribo.dataprovider;

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * app 信息封装  单利模式来显示（测试端对象）
 * @author Administrator
 *
 */
public class AppBean {
	private  AppBean() {}  
	private static AppBean appbean = null;
	public static AppBean getAppBean( ) {  
		if (appbean  == null ) { 
			try {
				appbean  = new AppBean();	
			} catch (Exception e) {
				e.printStackTrace();
			}       
		}  
		return appbean;  
	}
	
	private String apk;//apk 的名字
	private String platformVersion;//手机系统的类型
	private String phone;
	private String uid;//手机的类型
	private String port;//启动appium 的端口号
	public String getApk() {
		return apk;
	}
	public void setApk(String apk) {
		this.apk = apk;
	}
	public String getPlatformVersion() {
		return platformVersion;
	}
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
