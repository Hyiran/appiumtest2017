package com.oribo.android365.testcase;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.oribo.ReadExcelCase.ReadExcel;
import com.oribo.common.AppOperate;
import com.oribo.common.EditText;
import com.oribo.common.TestcaseFrame;
import com.oribo.common.ToolFunctions;
import com.oribo.dataprovider.AppBean;
import com.oribo.log.Log;
import com.oribo.utils.FileOperate;
import com.oribo.utils.compareimage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
/**
 * 登录界面的忘记密码已在Personcenter中实现
 * 帐号和邮箱登录则在TestcaseFrame中实现，每次执行用例前都判断当前的的登录状态，未登录则进行登录
 * @author test1
 *
 */
public class SignIn extends TestcaseFrame{
	AndroidDriver<AndroidElement> driver;
	AppBean  appbean = AppBean.getAppBean();
	String phone=ReadExcel.readsimpledata(1, 1, 3);
	
	
	String mac=null;
	@BeforeClass(alwaysRun=true)
	@Parameters({ "port","udid","phone","platformVersion", "apk","testaccount","testpassword","reportreceiver"})
	public void init(String port, String udid,String phone,String platformVersion ,String apk,String testaccount,String testpassword,String reportreceiver )
	{
		//保存app的基础信息		
				appbean.setUid(udid);
				appbean.setPort( port);
				appbean.setPhone(phone);
				appbean.setApk(apk);
				appbean.setPlatformVersion(platformVersion);	
				resetapp(getApppackagename());
		
	}
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod()
	{   
		super.testSetUp();
		driver=super.getDriver();
		

	}
	
	/**
	 * 手机号注册
	 */
	@Test(groups={"注册"})
	public void a_phoneregister()
	{   
		//如果已登录则退出登录
		if(!driver.currentActivity().equals(".user.LoginActivity"))
		{
			b_logff();
		}
		//删除此帐号,并删除应用数据
		Log.logInfo("注册要使用的手机号为："+phone);
		deleteaccount(phone);
		newSleep(3);
		//登录界面点击注册按扭
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/register_tv")), "点击注册按扭");
		//默认为手机号注册
		AndroidElement edit=driver.findElement(By.className("android.widget.EditText"));
		Assert.assertTrue(edit.getText().equals("手机号"), "默认应为手机号注册");
		//点击用户协议看是否能跳转
		AndroidElement useragreement=driver.findElement(By.id("com.orvibo.homemate:id/registerNextTip"));
		Assert.assertTrue(useragreement.getText().contains("用户协议"));
		AppOperate.clickbycoordinate(driver, 800, 700);
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/title_tv")).getText().equals("用户协议"));
		//返回至帐号注册界面
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/left_iv")), "返回至帐号注册界面");
		
		//输入要注册的手机号
		AppOperate.sendKeys(edit,"输入手机号", phone);
		//点击下一步
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")), "点击下一步");
		//判断是否会跳转到安全验证界面
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/codeEditText")).getText().equals("短信验证码"), "未跑转至安全验证界面");
		//下拉通知栏判断是否能查看到验证码,等待60S
		driver.openNotifications();
		boolean flag=false;
		int count=13;
		while(!flag)
		{  
			count--;
			if(count==0)
				break;
			
			try{
			Assert.assertTrue(driver.findElement(By.id("com.android.mms:id/primary_text")).getText().equals("1069075544029751957"));
			flag=true;
			}
			catch(Exception e)
			{
				newSleep(5);
				
			}
			
		}
		Assert.assertTrue(flag, "60s内未收到验证码");
		//获取验证码
		String mms=driver.findElement(By.id("com.android.mms:id/secondary_text")).getText();
		String submms=mms.split("，")[0];
		String verificode=submms.substring(submms.lastIndexOf("码", submms.length())+1);
		//输入验证码
		AppOperate.sendKeyEvent(4, "关闭通知栏", driver);
		AndroidElement code=driver.findElement(By.id("com.orvibo.homemate:id/codeEditText"));
		AppOperate.sendKeys(code, "输入验证码", verificode);
		//点击下一步
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/finishButton")), "安全验证界面点击下一步");
		//判断是否跳 转到设置新密码界面,输入密码为123456
		AndroidElement password=driver.findElement(By.id("com.orvibo.homemate:id/passwordEditText"));
		AppOperate.sendKeys(password, "输入密码", "123456");
		//点击提交
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"提交\")"), "点击提交");
		//判断注册后是否登录成功
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/roomTextView")).isDisplayed(),"未注册成功");
		
	}
	
	/**
	 * 登录界面点击忘记密码是否会跳转
	 * 登录界面点击三方授权是否能跳转
	 */
	@Test(groups={"登录界面"})
	public void checkother()
	{   
		//如果已登录则退出登录
		if(!driver.currentActivity().equals(".user.LoginActivity"))
		{
			b_logff();
		}
		//登录界面点击忘记密码是否会跳转
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/forget_password_tv")), "点击忘记密码");
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/title_tv")).getText().equals("重置登录密码"));
		//返回到登录界面
		AppOperate.sendKeyEvent(4, "返回到登录界面", driver);
		//登录界面分别点击三个授权三方应用图标,判断是否能跳转
		//点击微信
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/wechat_login")), "点击微信图标");
		Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w |grep name=", "com.tencent.mm"));
		//点击QQ
		AppOperate.sendKeyEvent(4, "返回到登录界面", driver);
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/qq_login")), "点击QQ图标");
		AppOperate.waitForTextDisappear(5, "com.orvibo.homemate:id/login_btn", driver);
		Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w |grep name=", "com.tencent.mobileqq"));
	
	}
	
	/**
	 * 授权登录微博,测试之前手机安装微博且已登录帐号
	 */
	@Test(groups={"登录界面"})
	public void weibologging()
	{   
		
		if(!driver.currentActivity().equals(".user.LoginActivity"))
		{
			b_logff();
		}
		
			AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/sina_login")), "点击微博图标");
			//判断是否登录成功
			Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/roomTextView")).isDisplayed(),"未登录成功");
			//授权登录后名称 和头像有没有变化
			String weiboname=ReadExcel.readsimpledata(1, 1, 4);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击我的");
			Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/tv_personal_userName")).getText().equals(weiboname), "授权登录后帐号名称未变");
			//进行截图,对比头像
			screenCapCompare(driver, "defaultphoto");
			//对比是否和默认的一样
			boolean ifsame=compareimage.sameAs(FileOperate.getTestDatapFilePath()+File.separator+"percompare.png", FileOperate.getScreencapFilePath()+File.separator+"defaultphoto.png", 0.98);
			Assert.assertFalse(ifsame, "头像未切换为三方头像");
		
	
	}
	
	
	
	/**
	 * 退出登录
	 */
	@Test(dependsOnMethods={"a_phoneregister"},groups={"登录界面","错误2"})
	public void b_logff()
	{
		enterPersoninfo();
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/userLogoutButton")), "点击退出登录");
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/rightButton")), "提示框中点击确定");
		//判断是否跳回到登录界面
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/login_btn")).isDisplayed());
		
		
	}
	
	
	/**
	 * 清除帐号数据
	 */
	public void resetapp(String packagename)
	{
		try {
			Runtime.getRuntime().exec("adb shell pm clear "+packagename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown(){
		//关闭appium 资源
		Log.logInfo("**********");
		driver.quit();
		
	}

}