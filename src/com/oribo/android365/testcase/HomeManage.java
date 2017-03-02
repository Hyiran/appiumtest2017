package com.oribo.android365.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.oribo.common.AppOperate;
import com.oribo.common.TestcaseFrame;
import com.oribo.database.Query;
import com.oribo.dataprovider.AppBean;
import com.oribo.dataprovider.Constant;
import com.oribo.log.Log;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class HomeManage extends TestcaseFrame{
	AndroidDriver<AndroidElement> driver;
	AppBean  appbean = AppBean.getAppBean();
	
	@BeforeClass
	@Parameters({ "port","udid","phone","platformVersion", "apk","testaccount","testpassword","reportreceiver"})
	public void init(String port, String udid,String phone,String platformVersion ,String apk,String testaccount,String testpassword,String reportreceiver )
	{
		//保存app的基础信息		
				appbean.setUid(udid);
				appbean.setPort( port);
				appbean.setPhone(phone);
				appbean.setApk(apk);
				appbean.setPlatformVersion(platformVersion);
		
	}
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod()
	{   
		super.testSetUp();
		driver=super.getDriver();
		logging();

	}
	
	@Test
	public void checkdefault()
	{   
		enterhomemanage();
		//获取帐号的名称
		String sql="select * from account2 where email='"+TestcaseFrame.getaccount().getAccount()+"' or phone='"+TestcaseFrame.getaccount().getAccount()+"'";
		String expectedname=Query.executSql(sql,2,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		//判断默认显示是否正确，如管理员名称是否显示正确
		AssertJUnit.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/memberName")).getText().equals(expectedname));
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"默认\")").isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/familyCheck_layout")).isDisplayed());

		
		

	}
	
	public void enterhomemanage()
	{
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭管理\")"), "点击'我的'");
	}
	
	
	
	

	@AfterMethod
	public void tearDown(){
		//关闭appium 资源
		Log.logInfo("**********");
		driver.quit();
		
	}

}
