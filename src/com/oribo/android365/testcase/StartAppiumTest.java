package com.oribo.android365.testcase;
import org.testng.annotations.Test;

import com.oribo.common.AppOperate;
import com.oribo.dataprovider.UserInfo;
import com.oribo.common.TestcaseFrame;
import com.oribo.dataprovider.AppBean;
import com.oribo.utils.FileOperate;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
public class StartAppiumTest extends TestcaseFrame {
	public String appport ;
	public String appudid;
	public String testphone;//  测试手机
	public String appplatformVersion;//手机的版本号
	public String appapk;//测试的App 包
	public String receiver;//测试报告邮件接收人
	AndroidDriver driver=null;
	/**
	 * 此类作用为启动APP，然后进行登录
	 * 设置测试环境
	 * @param port  启动的端口好
	 * @param udid 手机的IDID
	 * @param platformVersion  手机系统
	 * @param apk  测试的apk
	 */
	@BeforeTest(alwaysRun=true)
	@Parameters({ "port","udid","phone","platformVersion", "apk","testaccount","testpassword","reportreceiver"})
	public void beforeSuite(String port, String udid,String phone,String platformVersion ,String apk,String testaccount,String testpassword,String reportreceiver ){
	//	AppiumServerOnOff.startAppium();
		appport=port;
		appudid=udid;
		testphone = phone;
		appplatformVersion = platformVersion;
		appapk =apk;
		String account = testaccount;
		password = testpassword;
		receiver=reportreceiver;
		
		//开启appium服务
		
	}
	
	@BeforeTest(alwaysRun=true)
	public void startApp() {
	//保存app的基础信息		
		AppBean   appbean = AppBean.getAppBean();
		appbean.setUid(appudid);
		appbean.setPort( appport);
		appbean.setPhone(testphone);
		appbean.setApk(appapk);
		appbean.setPlatformVersion(appplatformVersion);
		String account = null;
		//保存登录账号信息
		UserInfo.getUserInfo().setAccount(account);
		UserInfo.getUserInfo().setPassWord(password);
		FileOperate.delectLogFiles();
		    super.testSetUp();
			driver = super.getDriver();
		//	newSetUp();

	
	
	}

	@Test(alwaysRun=true)
	public void test() {
		newSleep(5);
		System.out.println("当前界面是什么"+driver.currentActivity());
		if(driver.currentActivity().equals(".user.LoginActivity"))
		{
		//登录页面
			List<AndroidElement> edittext = driver.findElementsByClassName("android.widget.EditText"); 
			System.out.println("多少个输入框"+edittext.size());
			Assert.assertTrue(edittext.size()>0, "未找到输入框");
			 AppOperate.clear(edittext.get(0), "清除账号框");
			 AppOperate.sendKeys(edittext.get(0), "输入账号", UserInfo.getUserInfo().getAccount());
			 AppOperate.clear(edittext.get(1), "清除密码框");
			 AppOperate.sendKeys( edittext.get(1), "输入密码", UserInfo.getUserInfo().getPassWord());
		     AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/login_btn")), "点击登录按扭");
		     AppOperate.waitForTextDisappear(5, "等待登录成功", driver);
		}

	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass()
	{
		System.out.println("**********");
		driver.quit();
		
	}
	
	@AfterSuite(alwaysRun=true)
	public void afterSuite()
	{   
		
		//发送邮件
		//sendReport(testphone+appplatformVersion+appapk,receiver);
		//关闭appiume服务
		//AppiumServerOnOff.closeAppium();
	}
	

}
