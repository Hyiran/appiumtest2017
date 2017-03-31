package com.oribo.android365.testcase;
/**
 * 用来做测试前初始化或收尾的工作，如设置手机休眠、保存APP信息、输入法的切换、测试完成后发送邮件等
 */
import org.testng.annotations.Test;

import com.oribo.common.AppOperate;
import com.oribo.dataprovider.UserInfo;
import com.oribo.log.Log;
import com.oribo.common.TestcaseFrame;
import com.oribo.common.ToolFunctions;
import com.oribo.dataprovider.AppBean;
import com.oribo.utils.FileOperate;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
public class Initializtion extends TestcaseFrame {
	public String appport ;
	public String appudid;
	public String testphone;//  测试手机
	public String appplatformVersion;//手机的版本号
	public String appapk;//测试的App 包
	public String receiver;//测试报告邮件接收人
	AndroidDriver driver=null;
	AppBean  appbean = AppBean.getAppBean();
	

	@BeforeTest(alwaysRun=true)
	@Parameters({ "port","udid","phone","platformVersion", "apk","testaccount","testpassword","reportreceiver"})
	public void beforeSuit(String port, String udid,String phone,String platformVersion ,String apk,String testaccount,String testpassword,String reportreceiver)
	{   
		FileOperate.delectLogFiles();

		//设置手机休眠时间为10分钟
		ToolFunctions.setdevsleeptime("300000");
		//保存app的基础信息		
		appbean.setUid(udid);
		appbean.setPort( port);
		appbean.setPhone(phone);
		appbean.setApk(apk);
		appbean.setPlatformVersion(platformVersion);
		receiver=reportreceiver;
	}

	
	@AfterTest(alwaysRun=true)
	public void afterSuite()
	{   
		
		//发送邮件
		sendReport(testphone+appplatformVersion+appapk,receiver);
		 //切换手机输入法为非APPIUM输入法
		   ToolFunctions.setinputmethod();

	}
	


}
