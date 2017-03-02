package com.oribo.android365.testcase;

import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.oribo.dataprovider.Constant;
import com.oribo.common.TestcaseFrame;
import com.oribo.common.ToolFunctions;
import com.oribo.common.EditText;
import com.oribo.common.AppOperate;
import com.oribo.dataprovider.AppBean;
import com.oribo.ReadExcelCase.DataBean;
import com.oribo.ReadExcelCase.ExcelData;
import com.oribo.dataprovider.UserInfo;
import com.oribo.log.Log;
import com.oribo.monitor.ActionListener;
import com.oribo.monitor.Even;
import com.oribo.report.TestResultListener;
import com.oribo.utils.FileOperate;
import com.oribo.common.ToolFunctions;
import com.oribo.utils.compareimage;
import com.oribo.ReadExcelCase.ReadExcel;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
@Listeners({TestResultListener.class})
public class Personcenter extends TestcaseFrame{
	AndroidDriver<AndroidElement> driver;
	public String receiver;//测试报告邮件接收人
	AppBean  appbean = AppBean.getAppBean();
	static UserInfo account=UserInfo.getUserInfo();
	 
	 
	/**
	 * 获取被测APP的信息以及APP的登录帐户信息
	 * 设置测试环境
	 * @param port  启动的端口好
	 * @param udid 手机的IDID
	 * @param platformVersion  手机系统
	 * @param apk  测试的apk
	 */
	@BeforeClass(alwaysRun=true)
	@Parameters({ "port","udid","phone","platformVersion", "apk","testaccount","testpassword","reportreceiver"})
	public void beforeSuite(String port, String udid,String phone,String platformVersion ,String apk,String testaccount,String testpassword,String reportreceiver ){
		
		
		//保存app的基础信息		
		appbean.setUid(udid);
		appbean.setPort( port);
		appbean.setPhone(phone);
		appbean.setApk(apk);
		appbean.setPlatformVersion(platformVersion);
		//保存登录账号信息
		UserInfo account=UserInfo.getUserInfo();
		//从Excel读取账号信息
		System.out.println("账号是:"+ReadExcel.readsimpledata(1,1,0)+"密码是:"+ReadExcel.readsimpledata(1,1,1));
		account.setAccount(ReadExcel.readsimpledata(1,1,0));
		account.setPassWord(ReadExcel.readsimpledata(1,1,1));
	/*	account.setAccount(testaccount);
		account.setPassWord(testpassword);*/
		FileOperate.delectLogFiles();

	 
	    
		
	}
	
	@BeforeClass(alwaysRun=true)
	public void beforeClass()
	{
		ReadExcel  excel =  new ReadExcel();
		ExcelData  excelData =  new ExcelData();
		excelData.setNumerSheet(0);
		excelData.setCaseType(Constant.CASETYPE_ANDROID);
		excel.readXls("personInfo_TestData.xls", excelData,bean);
		FileOperate.delectLogFiles();
		
	}
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod()
	{   
		super.testSetUp();
		driver=super.getDriver();
		logging();

	}
	/**
	 * 登录
	 */
	

	public void logging() {
		newSleep(2);
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
		     AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/text"), 20);
		     AppOperate.waitForText(20, "我的");
		}

	}
	
	/**
	 * 个人中心检查我的主机
	 */
	@Test(groups={"个人中心","我的主机"})
	private void CheckMyHub() {
		DataBean  data = bean.get(0);
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
	    AndroidElement  	HostName = (AndroidElement) driver.findElementByAndroidUIAutomator("text(\"我的主机\")");
	}			
	       
/**
	 * 
	 * 设备信息检查主机Mac地址
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CheckMyHubMac() {
		DataBean  data = bean.get(1);
		enterMyhost();
		AppOperate.exitElement(data.getExpectValue(), driver);
	}
	
	/**
	 * 
	 * 设备信息检查主机ip
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CheckMyHubIpAddress() {
		DataBean  data = bean.get(2);
		enterMyhost();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/info2TextView");//主机ip地址
		Assert.assertEquals(HostName.getText(), data.getExpectValue(), "MAC地址与期望值不符");
				
	
		
	}
	
	/**
	 * 
	 * 设备信息检查主机固件版本
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CheckMyHubFirmwareVersion() {
		DataBean  data = bean.get(3);
		enterMyhost();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/info3TextView");//主机固件版本
		Assert.assertEquals(HostName.getText(), data.getExpectValue(), "固件版本与期望值不符");
	}
		

		
	
	
    /**
	 *  设备信息检查主机版本后返回
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void CheckMyHubBack() {
		DataBean  data = bean.get(4);
		enterMyhost();
		AndroidElement  HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/left_iv");//返回按扭
		Assert.assertTrue(HostName.isDisplayed(), "未找到返回按钮");
		AppOperate.click(HostName, "点击返回按扭");;//返回我的页面
		AndroidElement  	PageName = (AndroidElement) driver.findElementByAndroidUIAutomator("text(\"我的\")");//
		Assert.assertNotNull(PageName, "返回个人信息页面失败");
			

	}
	
	/**
	 * 
	 * 更多页面检查删除主机
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void ClickMore() {
		DataBean  data = bean.get(5);	
		enterMyhost();
		AppOperate.swipeToUp(driver, "向上滑屏");
		AndroidElement  	MoreName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/moreTextView");//主机设备信息“更多”
		Assert.assertNotNull(MoreName, "找不到“更多”");
        AppOperate.click(MoreName, "点击更多");
		Assert.assertNotNull(driver.findElementByAndroidUIAutomator("text(\"删除主机\")"),"点击‘更多’跳转失败");
			
	

	}
	
	/**
	 * 
	 *检查删除主机弹窗
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CheckDeleteHost() {
		DataBean  data = bean.get(6);
		enterMore();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/deleteTextView");//更多页面检查删除主机
	    AppOperate.click(HostName, "点击‘删除主机’");
	    AndroidElement  	noticeText = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/contentTextView");//点击删除主机弹窗提示文案
	    Assert.assertNotNull(noticeText, "点击删除主机未弹出提示框");
	    Assert.assertEquals(noticeText.getText(), "主机和所有设备均将被删除，需要重新添加后才能使用，确实要删除吗？");
	        

	}
	
	/**
	 * 
	 * 取消删除主机弹窗
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CancelDeleteHostNotice() {
		DataBean  data = bean.get(7);
		enterMore();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/deleteTextView");//更多页面检查删除主机
	    AppOperate.click(HostName, "点击‘删除主机’");
	    AndroidElement  	noticeText = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/contentTextView");//点击删除主机弹窗提示文案
	    Assert.assertNotNull(noticeText, "点击删除主机未弹出提示框");
	    Assert.assertEquals(noticeText.getText(), "主机和所有设备均将被删除，需要重新添加后才能使用，确实要删除吗？");
		AndroidElement  	CancelButton = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/rightButton");//点击删除主机弹窗提示文案
        AppOperate.click(CancelButton, "点击‘取消’");
        Assert.assertFalse(CancelButton.isDisplayed(), "点击弹窗中的‘取消’按扭，弹窗并未消失");
		

	}
	
	/**
	 * 
	 * 删除主机
	 */
	@Test(groups={"个人中心","我的主机"},enabled=false)
	private void  DeleteHost() {
		DataBean  data = bean.get(8);
		enterMore();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/deleteTextView");//更多页面检查删除主机
	    AppOperate.click(HostName, "点击‘删除主机’");
	    AndroidElement  	noticeText = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/contentTextView");//点击删除主机弹窗提示文案
	    Assert.assertNotNull(noticeText, "点击删除主机未弹出提示框");
	    Assert.assertEquals(noticeText.getText(), "主机和所有设备均将被删除，需要重新添加后才能使用，确实要删除吗？");
		AndroidElement  	DeleteButton = (AndroidElement) driver.findElementByAndroidUIAutomator("text(\"删除\")");//删除按钮		
		AppOperate.click(DeleteButton, "确认删除");
		AppOperate.waitForTextDisappear(10, "努力奔跑中，快到终点喽~",driver);
		Assert.assertFalse(driver.getPageSource().contains("努力奔跑中，快到终点喽~"),"等待10S后删除主机失败");
		Assert.assertFalse(HostName.isDisplayed(),"删除主机失败");
			



	}
	
    /**
	 * 输入为空
	 */
	@Test(groups={"个人中心","个人信息","昵称"})
	public void nickNull()
	{   
		//DataBean  data = bean.get(11);
		//System.out.println(data.toString());
		enterPersoninfo();
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"昵称\")"), "点击昵称");
     	AndroidElement edittext=(AndroidElement) driver.findElement(By.id("com.orvibo.homemate:id/userNicknameEditText"));
		AppOperate.clear(edittext, "清空昵称文本框");
		Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/saveButton")).isEnabled(), "昵称为空时保存按扭可点击");
		
	}
	
	/**
	 *输入16个汉字能保存成功
	 */
	@Test(groups={"个人中心","个人信息","昵称"})
	public void nickmaxchina()
	{
		//DataBean  data = bean.get(12);
		//System.out.println(data.getTestCaseName());
		enterPersoninfo();
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"昵称\")"), "点击昵称");
		EditText edittext=new EditText((AndroidElement) driver.findElement(By.id("com.orvibo.homemate:id/userNicknameEditText")));
		edittext.inputmaxchina(driver);
		AndroidElement save=(AndroidElement) driver.findElementByAndroidUIAutomator("text(\"保存\")");
		Assert.assertTrue(save.isEnabled());
		AppOperate.confirmButton(driver.findElementByAndroidUIAutomator("text(\"保存\")"), "点击保存按扭");
		//检查是否保存成功
		Assert.assertEquals(driver.findElement(By.id("com.orvibo.homemate:id/userNicknameTextView")).getText().trim(), edittext.getmaxchina());
		

	}
	
	/**
	 * 输入32个英文字母能保存成功
	 */
	@Test(groups={"个人中心","个人信息","昵称"})
	public void nickmaxeng()
	{
		//DataBean  data = bean.get(12);
		//System.out.println(data.getTestCaseName());
		enterPersoninfo();
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"昵称\")"), "点击昵称");
		EditText edittext=new EditText((AndroidElement) driver.findElement(By.id("com.orvibo.homemate:id/userNicknameEditText")));
		edittext.inputmaxeng(driver);
		AndroidElement save=(AndroidElement) driver.findElementByAndroidUIAutomator("text(\"保存\")");
		Assert.assertTrue(save.isEnabled());
		AppOperate.confirmButton(driver.findElementByAndroidUIAutomator("text(\"保存\")"), "点击保存按扭");
		//检查是否保存成功
		Assert.assertEquals(driver.findElement(By.id("com.orvibo.homemate:id/userNicknameTextView")).getText().trim(), edittext.getmaxeng());

	}
	
	/**
	 * 修改，输入一个字符
	 */
	@Test(groups={"个人中心","个人信息","昵称"})
	public void nickmodify()
	{
		//DataBean  data = bean.get(13);
		//System.out.println(data.getTestCaseName());
		enterPersoninfo();
		AndroidElement nick=(AndroidElement) driver.findElement(By.id("com.orvibo.homemate:id/userNicknameTextView"));
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/userNicknameTextView")), "点击昵称");
		EditText edittext=new EditText((AndroidElement) driver.findElement(By.id("com.orvibo.homemate:id/userNicknameEditText")));
		edittext.inputonechar(driver);
		AndroidElement save=(AndroidElement) driver.findElementByAndroidUIAutomator("text(\"保存\")");
		Assert.assertTrue(save.isEnabled());
		AppOperate.confirmButton(driver.findElementByAndroidUIAutomator("text(\"保存\")"), "点击保存按扭");
		//检查是否保存成功
		Assert.assertEquals(driver.findElement(By.id("com.orvibo.homemate:id/userNicknameTextView")).getText().trim(), edittext.getonechar());
		
		
		
	}
	/**
	 * 邮箱登录，手机号检查、手机登录检查邮箱绑定
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"},enabled=false)
	public void checkbind()
	{   
		//DataBean  data = bean.get(24);
		enterPersoninfo();
		System.out.println("登录类型"+account.getLogingType());
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{   
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "点击进入邮箱绑定界面");
			AppOperate.exitElement("更换邮箱", driver);
			AppOperate.sendKeyEvent(4, "点击手机返回键",driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			AppOperate.notExitElement("更换手机号", driver);	
			
		}
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			AppOperate.exitElement("更换手机号", driver);
			AppOperate.sendKeyEvent(4, "点击手机返回键",driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "点击进入邮箱绑定界面");
			AppOperate.notExitElement("更换邮箱", driver);		
		}
		
	}
	
	/**
	 * 输入为空（手机号绑定、邮箱绑定界面）
	 * 
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void phonebind()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			//输入为空
			edit.inputnull(driver);
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入空格下一步可点击");
			
		}
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "点击进入邮箱绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			//输入为空
			edit.inputnull(driver);
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入空格下一步可点击");
		}
		
	}
	
	@Test(groups={"个人中心","个人信息","账号绑定"})
	/**
	 * 输入非数字字符(输入非邮箱格式、输入非数字字符）
	 */
	public void  specialchar()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputSpecialchar2(driver);
			Assert.assertFalse(edit.gettext().trim().equals(edit.getSpecialchar2()), "能输入特殊字符");
		
		}
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "点击进入邮箱绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputphonenumber(driver);
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入非邮箱下一步可点击");
		}
		
	}
	
	/**
	 * 输入小于11位数字
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void lesphonenumber()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputlesphonenumber(driver);
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入小于11位手机号下一步可点击");
			
		}
		
	}
	
	/**
	 * 输入大于11位数字
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void grephonenumber()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputgrephonenumber(driver);
			Assert.assertTrue(edit.gettext().length()==11, "能输入超过11位的手机号");
			
		}
		
	}
	
	/**
	 * 输入不是电话号码的数字
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void notphonenumber()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.notphone(driver);
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入非手机号码的数字下一步可点击");
			AppOperate.exitElement("手机号码格式错误", driver);
		}
		
	}
	
	/**
	 * 输入11位正确的号码,点击取消、输入正确的邮箱格式、点击手机账号进入绑定账号页面，检查输入框是否保存刚刚输入的值、输入邮箱信息显示
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void rightaccount()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputphonenumber(driver);
			Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入正确的手机号码下一步不可点击");
			AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/left_iv")), "返回到个人信息界面");
			AppOperate.notExitElement(edit.getphonenumber(), driver);
		}
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "点击进入邮箱绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputemail(driver);
			Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入正确的邮箱下一步不可点击");
			AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/left_iv")), "返回到个人信息界面");
			AppOperate.notExitElement(edit.getemail(driver), driver);
			
		
		}
			
		
	}
	
	/**
	 * 输入没有绑定的正确邮箱、输入11为正确的手机、点击手机账号进入绑定账号页面，按钮不能点击
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void rightaccountnext()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "点击进入手机绑定界面");
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "点击手机账号进入绑定账号页面，按钮可点击");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			edit.inputphonenumber2(driver);
			Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入正确的手机号码下一步不可点击");
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "跳到安全验证界面");
			AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/codeEditText"), 10);
			AppOperate.exitElement("短信验证码", driver);
			
		}
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "点击进入邮箱绑定界面");
			EditText edit=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
			Assert.assertFalse(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "点击邮箱账号进入绑定账号页面，按钮可点击");
			edit.inputemail(driver);
			Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/nextButton")).isEnabled(), "输入正确的邮箱下一步不可点击");
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "跳到安全验证界面");
			AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/codeEditText"), 10);
			AppOperate.exitElement("重新发送", driver);
			
		
		}
		
	}
	
	/**
	 *邮箱登录： 输入错误的验证码、倒计时间结束，按钮显示"重新发送"、发送按钮倒计时间能正常显示、点击发送、验证码为空
	 *手机登录： 输入已绑定邮箱(去掉)、验证码为空、点击发送、发送按钮倒计时间能正常显示、倒计时间结束，按钮显示"重新发送"、输入错误的验证码
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void identifycode()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			try{
				AppOperate.exitElement("未绑定", driver);
				Log.logInfo("登录的账号为邮箱登录，且未绑定手机号");
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "进入 手机号绑定界面");
				EditText edit1=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
	/*			//输入已绑定的手机号
				edit1.inputphonenumber(driver);
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
				//输入已绑定的手机号会有提示
				AppOperate.exitElement("此手机号已绑定，请更换其他手机号。", driver);
				//清空，重新输入未绑定的手机号
				edit1.clear();*/
				edit1.inputphonenumber2(driver);
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
				AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/codeEditText"), 10);
				//验证码为空时下一步不可点击
				Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(),"验证为空时下一步可点击");
				AppOperate.waitForTextDisappear(10, "获取验证码…", driver);
				long t1= System.currentTimeMillis();
				//检查倒计时
				AppOperate.exitElement("重新发送", driver);
				//未输入验证码时，下一步不可点击
				Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(), "验证码为空时下一步可点击");
				AppOperate.exitElement("(", driver);
				//输入错误的验证码
				EditText edit2=new EditText(driver.findElementById("com.orvibo.homemate:id/codeEditText"));
				edit2.inputlesphonenumber(driver);
				//判断下一步是否可点击
				Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(), "输入验证码后下一步不可点击");
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
				//输入错误 的验证码有提示
				AppOperate.exitElement("验证码错误", driver);
				long t2 = System.currentTimeMillis();
				boolean flag=true;
				
				//等待60秒后，检查倒计时是否消失
				while(flag)
				{   
					newSleep(2);
					if(t2-t1>1000*60)
					{
						flag=false;
											
					}
					t2 = System.currentTimeMillis();
					
				}
				//判定60秒后不应有倒计时
				AppOperate.notExitElement("(", driver);
				
			}
			catch(Exception e)
			{
				Log.logInfo("登录的账号为邮箱登录，且已绑定手机号，此条用例无法测试");
				
			}

		}
		//手机登录时安全验证
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{

			try{
				AppOperate.exitElement("未绑定", driver);
				Log.logInfo("登录的账号为手机登录，且未绑定邮箱");
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "进入邮箱绑定界面");
				EditText edit1=new EditText(driver.findElementById("com.orvibo.homemate:id/userPhoneEmailEditText"));
				//输入已绑定邮箱
				edit1.inputemailband(driver);
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
				AppOperate.waitForTextDisappear(10, "邮箱绑定后可用于登录或找回密码。", driver);
				//输入已经绑定的邮箱有提示语
				AppOperate.exitElement("此邮箱已绑定，请更换其他邮箱。", driver);
				//清空并输入未绑定的邮箱
				edit1.clear();
				edit1.inputemail(driver);
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
				long t1 = System.currentTimeMillis();
				//验证码为空时下一步不可点击
				Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(), "验证码为空时下一步可点击");
				//输入错误的验证码
				EditText edit2=new EditText(driver.findElementById("com.orvibo.homemate:id/codeEditText"));
				edit2.inputlesphonenumber(driver);
				//判断下一步是否可点击
				Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(), "输入验证码后下一步不可点击");
				AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
				//输入错误 的验证码有提示
				AppOperate.exitElement("验证码错误", driver);
			
                boolean flag=true;
                long t2 = System.currentTimeMillis();
				//等待60秒后，检查倒计时是否消失
				while(flag)
				{   
					newSleep(2);
					if(t2-t1>1000*60)
					{
						flag=false;
											
					}
					t2 = System.currentTimeMillis();
					
				}
				//判定60秒后不应有倒计时
				AppOperate.notExitElement("(", driver);
				
				
				
			}
			catch(Exception e)
			{
				Log.logInfo("登录的账号为手机登录，且已绑定邮箱");
				
			}
			
		}
		
		
	}
	
	
	/**
	 * 手机登录：
	 * 邮箱登录，
	 * 邮箱账号显示、
	 * 点击更换邮箱按钮，
	 * 成功跳转到安全验证页面、
	 * 当前手机信息显示
	 */
	@Test(groups={"个人中心","个人信息","账号绑定"})
	public void checkaccount()
	{
		enterPersoninfo();
		if(account.getLogingType()==Constant.LOGING_TYPE_EMAIL)
		{ 
			String email=driver.findElement(By.id("com.orvibo.homemate:id/userEmailTextView")).getText();
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"邮箱\")"), "进入邮箱绑定界面");
			AppOperate.exitElement(email, driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"更换邮箱\")"), "点击“更换邮箱”");
			//验证是否跳到安全验证界面
			AppOperate.exitElement("安全验证", driver);
			//检查当前手机号显示 是否正确
			AppOperate.exitElement(email, driver);
		}
		else if(account.getLogingType()==Constant.LOGING_TYPE_PHONE)
		{   
			//获取个人信息界面手机号
			String phone=driver.findElement(By.id("com.orvibo.homemate:id/userPhoneTextView")).getText();
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"手机号\")"), "进入 手机号绑定界面");
			AppOperate.exitElement(phone, driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"更换手机号\")"), "点击“更换手机号”");
			//验证是否跳到安全验证界面
			AppOperate.exitElement("安全验证", driver);
			//检查当前手机号显示 是否正确
			AppOperate.exitElement(phone, driver);
		}
		
	}
	
	/**
	 * 修改密码时，旧密码为空，按扭状态
	 * 修改密码时，新密码为空，按状态
	 * 点击完成
	 * 旧密码错误提示
	 * 修改密码时，输入的新密码少于6位
	 * 修改密码时，输入的新密码少于6位，完成按钮置灰不能点击
	 * 修改密码时，保存密码
	 */
	@Test(groups={"个人中心","个人信息","修改密码"})
	public void modifypassword()
	{
		enterPersoninfo();
		//点击“修改密码”
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"修改密码\")"), "点击修改密码");
		//判断“完成”按扭不可点击
		AndroidElement okbutton=driver.findElementByAndroidUIAutomator("text(\"完成\")");
		Assert.assertFalse(okbutton.isEnabled(), "密码为空时“完成按扭可点击”");
		List<AndroidElement> passwords=driver.findElements(By.className("android.widget.EditText"));
		Assert.assertEquals(passwords.size(), 2);
		EditText oldpassword=new EditText(passwords.get(0));
		EditText newpassword=new EditText(passwords.get(1));
		//旧密码框输入，新密码框不输入,判断完成按扭是否可点击
		oldpassword.inputlesphonenumber(driver);
		Assert.assertFalse(okbutton.isEnabled(), "只输入旧密码时“完成按扭可点击”");
		//新密码框输入，旧密码框不输入,判断完成按扭是否可点击
		oldpassword.clear();
		newpassword.inputlesphonenumber(driver);
		Assert.assertFalse(okbutton.isEnabled(), "只输入新密码时“完成按扭可点击”");
		//新旧密码都输入时,且旧密码错误时,完成按扭可点击
		oldpassword.inputlesphonenumber(driver);
		Assert.assertTrue(okbutton.isEnabled(),"新旧密码都输入时“完成按扭不可点击”");
		AppOperate.click(okbutton, "点击完成按扭");
		AppOperate.exitElement("旧密码错误", driver);
		//旧密码改为正确的，新密码少于6位，再查看完成
		oldpassword.clear();
		oldpassword.inputoldpassword(driver);
		newpassword.inputfivechar(driver);
		Assert.assertFalse(okbutton.isEnabled(), "新密码少于6位时“完成按扭可点击”");
		//新密码设置为6位
		newpassword.clear();
		newpassword.changepassword(driver);
	    AppOperate.click(okbutton, "点击完成按扭");
	    //判断是否回到首页
	    AppOperate.exitElement("我的", driver);
	    //检查有完成的toast
	   // Assert.assertNotNull(driver.findElementByXPath(".//*[contains(@text,'修改完成')]"));
	    //By.xpath(".//*[contains(@text,'Toast Test')]")))
		

	}
	/**
	 * 手机登录:
	 * 手机重置密码，获取输入框
	 * 手机重置密码，正常获取“下一步按钮”，默认不能点击
	 * 手机重置密码，输入存在的手机号
	 * 进入页面安发送按钮的状态检查
	 * 发送按钮倒计时间能正常显示
	 * 60 秒后，发送显示为“重新发送”
	 * 输入账号信息显示
	 * 邮箱登录：
	 * 获取输入框
	 * 正常获取“下一步按钮”，默认不能点击
	 * 输入正确的邮箱
	 * 发送按钮倒计时间能正常显示
	 * 倒计时间结束，按钮显示"重新发送"
	 */
	@Test(groups={"个人中心","个人信息","修改密码","忘记密码"})
	public void forgetpassword()
	{  
		enterPersoninfo();

		//点击“修改密码”
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"修改密码\")"), "点击修改密码");
		//点击“忘记旧密码”
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"忘记旧密码\")"), "点击忘记旧密码");
		//判断有否有重手机号/邮箱输入框
		AndroidElement next=driver.findElementByAndroidUIAutomator("text(\"下一步\")");
		AppOperate.exitElement("手机号/邮箱", driver);
		//判断“下一步”是否可点击
		Assert.assertFalse(next.isEnabled(),"默认状态下“下一步可点击”");
		//输入正确的账号
		EditText edit=new EditText(driver.findElement(By.id("com.orvibo.homemate:id/userPhoneEmailEditText")));
		edit.inputaccount(driver);
		AppOperate.click(next, "点击下一步");
		long t1= System.currentTimeMillis();
		AppOperate.waitForText(10, "com.orvibo.homemate:id/title_tv");
		//检查倒计时
		AppOperate.exitElement("重新发送", driver);
		//未输入验证码时，下一步不可点击
		Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(), "验证码为空时下一步可点击");
		AppOperate.exitElement("(", driver);
		//输入错误的验证码
		EditText edit2=new EditText(driver.findElementById("com.orvibo.homemate:id/codeEditText"));
		edit2.inputlesphonenumber(driver);
		//判断下一步是否可点击
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"下一步\")").isEnabled(), "输入验证码后下一步不可点击");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"下一步\")"), "点击下一步");
		//输入错误 的验证码有提示
		AppOperate.exitElement("验证码错误", driver);
		long t2 = System.currentTimeMillis();
		boolean flag=true;
		
		//等待60秒后，检查倒计时是否消失
		while(flag)
		{   
			newSleep(2);
			if(t2-t1>1000*60)
			{
				flag=false;
									
			}
			t2 = System.currentTimeMillis();
			
		}
		//判定60秒后不应有倒计时
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"重新发送\")").isDisplayed());
		
	  

	}
	
   /**
    * 未设置头像，检查个人信息
    */
	@Test(groups={"个人中心","个人信息","头像"},enabled=false)
	public void checkdefaultphoto()
	{
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		//进行截图,对比默认头像
		screenCapCompare(driver, "defaultphoto");
		//对比是否和默认的一样
		boolean ifsame=compareimage.sameAs(FileOperate.getTestDatapFilePath()+File.separator+"percompare.png", FileOperate.getScreencapFilePath()+File.separator+"defaultphoto.png", 0.97);
		Assert.assertTrue(ifsame, "默认头像与与其不符");
		
	}
	
	/**
	 * 个人信息页面，点击头像选项
	 * 个人信息页面，点击头像后，点击取消
	 * 个人信息页面，点击头像后，点击拍照
	 * 刚进入拍照状态，取消
	 * 进入拍照状态，点击拍照
	 * 已拍照，点击拍照页面中间的撤回按钮
	 * 已拍照，点击拍照页面右侧的√
	 * 相片裁剪页面，拉伸或不拉伸方框区域，点击确定
	 * 相片裁剪页面，点击返回或取消按钮
	 * 个人信息点击头像，点击从相册选择
	 */
	
	@Test(groups={"个人中心","个人信息","头像"})
	public void setphotobycamera()
	{
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		//进行截图,以备后续对比
		String photo1=ToolFunctions.getRandomstring(2);
		screenCapCompare(driver, photo1);
		//进入个人信息界面
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/iv_personal_user_icon")), "点击个人信息头像");
		//点击头像按扭，查看是否有图片选择
		AndroidElement photo=driver.findElement(By.id("com.orvibo.homemate:id/userPicImageView"));
		AppOperate.click(photo, "个人信息中点击“头像”");
		AppOperate.waitelementexit(driver,By.className("android.widget.TextView"), 3);
		//判断是否弹出选择框
		List<AndroidElement> list=driver.findElementsByClassName("android.widget.TextView");
		Assert.assertEquals(list.size(), 3);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"取消\")"), "点击选择框中的“取消”按扭");
		//点击取消，弹框是否消失
		AppOperate.waitForTextDisappear(5, "取消", driver);
		Assert.assertFalse(driver.getPageSource().contains("拍照"), "点击取消选择框未消失");
		//再次操作，点击拍照
		AppOperate.click(photo, "个人信息中点击“头像”");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"拍照\")"), "点击选择框中的“拍照”按扭");
		//进入拍照界面点击取消
		AppOperate.click(driver.findElement(By.id("com.android.camera:id/v6_btn_cancel")), "拍照界面点击取消");
		//判断是否回到个人信息界面
		AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/title_tv"), 5);
		//重新进入拍照界面
	    entercamera();
		//点击拍照按扭
		AppOperate.click(driver.findElement(By.id("com.android.camera:id/v6_shutter_button_internal")), "点击拍照按扭");
		AppOperate.waitelementexit(driver, By.id("com.android.camera:id/v6_btn_done"), 10);
		AppOperate.click(driver.findElement(By.id("com.android.camera:id/v6_btn_done")), "点击选择按扭");
		//裁剪界面点击取消
		AppOperate.waitelementexit(driver, By.id("com.miui.gallery:id/cancel"), 10);
		AppOperate.click(driver.findElement(By.id("com.miui.gallery:id/cancel")), "裁剪界面点击“取消”");
		//判断是否回到个人信息界面
		AppOperate.exitElement("个人信息", driver);
		
		//重新点击头像进入 拍照
        entercamera();
		AppOperate.click(driver.findElement(By.id("com.android.camera:id/v6_shutter_button_internal")), "点击拍照按扭");
		AppOperate.waitelementexit(driver, By.id("com.android.camera:id/v6_btn_done"), 10);
		AppOperate.click(driver.findElement(By.id("com.android.camera:id/v6_btn_done")), "点击选择按扭");
		//裁剪界面点击确定
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"确定\")"), "裁剪界面点击确定");
		//判定是否回到个人信息界面
		AppOperate.waitForTextDisappear(10, "修改头像中", driver);
		AppOperate.sendKeyEvent(4, "点击手机返回键", driver);
		//判断头像是否设置成功，截图对比
		String photo2=ToolFunctions.getRandomstring(2);
		screenCapCompare(driver, photo2);
		boolean ifsame=compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+photo2+".png", FileOperate.getScreencapFilePath()+File.separator+photo1+".png", 0.97);
		Assert.assertFalse(ifsame, "头像未设置成功");

	}
	/**
	 *进入相册，点击取消
	 *进入相册，选择图片
	 *图片裁剪状态，点击返回或取消按钮
	 *相片裁剪页面，拉伸或不拉伸方框区域，点击确定
	 *已设置头像，更换头像成功
	 */
	@Test(groups={"个人中心","个人信息","头像"})
	public void setphotobyalbum(){
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		//进行截图,以备后续对比
		String photo1=ToolFunctions.getRandomstring(1);
		screenCapCompare(driver, photo1);
		//进入个人信息界面
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/iv_personal_user_icon")), "点击个人中心头像");
		//点击头像按扭，查看是否有图片选择
		AndroidElement photo=driver.findElement(By.id("com.orvibo.homemate:id/userPicImageView"));
		AppOperate.click(photo, "个人信息中点击“头像”");
		AppOperate.waitelementexit(driver,By.className("android.widget.TextView"), 3);
		AndroidElement album=driver.findElementByAndroidUIAutomator("text(\"从相册选择\")");
		AppOperate.click(album, "点击从相册选择");
		AppOperate.notExitElement("从相册选择", driver);
		//图片选择界面点击返回
		AppOperate.sendKeyEvent(4, "图片选择界面点击返回", driver);
		//判断是否回到个人信息界面
		AppOperate.exitElement("退出登录", driver);
		//重新进入相册选择界面
		AppOperate.click(photo, "个人信息中点击“头像”");
		AppOperate.waitelementexit(driver,By.className("android.widget.TextView"), 3);
		AppOperate.click(album, "点击从相册选择");
		//选择一张图片
		AppOperate.click(driver.findElement(By.id("com.miui.gallery:id/pick_num_indicator")), "点击选择一张图片");
		//图像裁剪界面点击返回
		AppOperate.sendKeyEvent(4, "图片选择界面点击返回", driver);
		//判断是否回到个人信息界面
		AppOperate.exitElement("个人信息", driver);
		//重新进入图片裁剪界面
		AppOperate.click(photo, "个人信息中点击“头像”");
		AppOperate.waitelementexit(driver,By.className("android.widget.TextView"), 3);
		AppOperate.click(album, "点击从相册选择");
		List<AndroidElement> list=driver.findElements(By.id("com.miui.gallery:id/pick_num_indicator"));
		AppOperate.click(list.get((int) (Math.random()*list.size())), "点击选择一张图片");
		//图片裁剪界面点击确定
		AppOperate.exitElement("确定", driver);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"确定\")"), "图片裁剪界面点击确定");
		AppOperate.exitElement("个人信息", driver);
		//返回到个人中心界面截图，对比查看头像是否设置成功
		AppOperate.sendKeyEvent(4, "图片选择界面点击返回", driver);
		String photo2=ToolFunctions.getRandomstring(1);
		screenCapCompare(driver, photo2);
		boolean ifsame=compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+photo2+".png", FileOperate.getScreencapFilePath()+File.separator+photo1+".png", 0.97);
		Assert.assertFalse(ifsame, "头像未设置成功");
	}
	
	/**
	 * 前提条件：账号清空
	 *授权登录，未绑定账号
	 */
	@Test(groups={"个人中心","个人信息","授权登录"})
	public void authorizationentrance()
	{   
		enterPersoninfo();
		//判定是否有授权登录的入口
		AppOperate.exitElement("授权登录", driver);
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/qqNormalImageView")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/weChatNormalImageView")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/sinaNormalImageView")).isDisplayed());
		
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"授权登录\")"), "点击进入授权登录界面");
		//判定是否有3个授权登录的入口
		AppOperate.exitElement("微信账号", driver);
		AppOperate.exitElement("QQ账号", driver);
		AppOperate.exitElement("微博账号", driver);
		//判断是否显示未绑定
		List<AndroidElement> nobind=driver.findElementsByAndroidUIAutomator("text(\"未绑定\")");
		Assert.assertEquals(nobind.size(), 3);
	}
	
	/**
	 * 参数化测试
	 */
	@DataProvider(name = "authorizationdataprovider")
	public Object[][] dataprovide(){
	    return new Object[][]{
	    	{"com.tencent.mm","安装了微信","微信账号","点击微信账号","绑定微信","点击绑定微信","跳转到微信界面","微信",},
	    	{"com.sina.weibo","安装了微博","微博账号","点击微博账号","绑定微博","点击绑定微博","跳转到微博界面","微博"},
	    	{"com.tencent.mobileqq","安装了QQ","QQ账号","点击QQ账号","绑定QQ","点击绑定QQ","跳转到QQ界面","QQ"},
	    	};                                      
	    	 }       
	
	/**
	 * 前提条件：安装的账号都已登录
	 * 用例：微信授权登录页面检查
	 * QQ授权登录页面检查
	 * 微信授权登录页面检查
	 * 微信授权登录页面检查
	 * 第三方授权登陆，查看“个人信息”页面
	 * 授权登陆，绑定第三方授权登陆
	 * “我的”页面查看授权登陆
	 */
	@Test(dataProvider="authorizationdataprovider",groups={"个人中心","个人信息","授权登录"},enabled=false)
	public void authorization(String packagename,String loginfo1,String clickname1,String loginfo2,String clickname2,String loginfo3,String assertinfo,String assertinfo2)
	{
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		 //截图对比
	    String photo5=ToolFunctions.getRandomstring(1);
	    screenCapCompare(driver, photo5);
		
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/iv_personal_user_icon")), "点击个人信息头像");
		 //截图对比
		String photo3=ToolFunctions.getRandomstring(1);
		screenCapCompare(driver, photo3);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"授权登录\")"), "点击进入授权登录界面");
		//判断是否安装了微信
		if(ToolFunctions.ifinstallthirdapp(packagename))
		{
			Log.logInfo(loginfo1);
            //截图对比
			String photo1=ToolFunctions.getRandomstring(1);
			screenCapCompare(driver, photo1);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\""+clickname1+"\")"), loginfo2);
			
		   if(!AppOperate.ifexitElement("解除绑定", driver))
		   {
			   newSleep(1);
			   AppOperate.exitElement("绑定"+assertinfo2+"可用于登录智家365", driver);
			//点击绑定
			   AppOperate.click(driver.findElementByAndroidUIAutomator("text(\""+clickname2+"\")"), loginfo3);
				if(!packagename.equals("com.sina.weibo"))
					{
			newSleep(5);
			//判断是否跳转到三方界面
			Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w|grep name=|grep "+packagename, packagename), assertinfo);
			System.out.println("当前activity："+driver.currentActivity());
			//判定已安装的三方应用是否登录，如果登录则直接点击登录
					if(!packagename.equals("com.tencent.mm"))
						{
						AndroidElement button=driver.findElement(By.className("android.widget.Button"));
						Assert.assertNotNull(driver.findElement(By.className("android.widget.Button")), "应用未登录，请登录应用再测试");
						AppOperate.click(button, "点击登录按扭");
						}
					else
						{
						//如果是微信，则坐标点击
						AppOperate.clickbycoordinate(driver, 592, 1088);
						}
					}
				else
					{
					AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/bindChangeButton"), 10);
					}
		    //返回到授权登录界面,查看是否会显示账号名称(截图对比)
		    AppOperate.sendKeyEvent(4, "返回到授权登录界面", driver);
			String photo2=ToolFunctions.getRandomstring(1);
			screenCapCompare(driver, photo2);
			System.out.println("图片1名称:"+photo1+"图片2名称:"+photo2);
			Assert.assertFalse(compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+photo2+".png", FileOperate.getScreencapFilePath()+File.separator+photo1+".png", 0.999), "授权登录后授权界面还显示未登录");
			
			//返回到个人信息界面截图对比
		    AppOperate.sendKeyEvent(4, "返回到授权登录界面", driver);
			String photo4=ToolFunctions.getRandomstring(1);
			screenCapCompare(driver, photo4);
			Assert.assertFalse(compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+photo3+".png", FileOperate.getScreencapFilePath()+File.separator+photo4+".png", 0.9995), "授权登录后个人信息界面三方图标还进显示灰色");
			//返回到我的界面截图对比
			AppOperate.sendKeyEvent(4, "返回到授权登录界面", driver);
			String photo6=ToolFunctions.getRandomstring(1);
			screenCapCompare(driver, photo6);
			Assert.assertFalse(compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+photo5+".png", FileOperate.getScreencapFilePath()+File.separator+photo6+".png", 0.97), "授权登录后我的界面不显示三方头像和名称");
			
			}
		   	else
		   		{
		   			Log.logInfo(clickname1+"已绑定");
		   		}
			
			
		
		}
		else
		{
			Log.logInfo("未"+loginfo1);
			System.out.println("text('"+clickname1+"')");
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\""+clickname1+"\")"), loginfo2);
			AppOperate.exitElement("绑定"+assertinfo2+"可用于登录智家365", driver);
			//点击绑定QQ
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\""+clickname2+"\")"), loginfo3);
			newSleep(5);
			//判断是否跳转到QQ界面
			Assert.assertFalse(ToolFunctions.cmdmessage("adb shell dumpsys window w|grep name=|grep "+packagename, packagename), assertinfo);
			
		}
		
	}
	/**
	 * 授权登陆，解除绑定第三方授权登陆，解绑成功
	 */
	@Test(groups={"个人中心","个人信息","授权登录"})
	public void Unboundauthorization()
	{
		enterPersoninfo();
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"授权登录\")"), "点击进入授权登录界面");
		AndroidElement weixin=driver.findElement(By.id("com.orvibo.homemate:id/weChatNickNameTextView"));
		AndroidElement qq=driver.findElement(By.id("com.orvibo.homemate:id/qqNickNameTextView"));
		AndroidElement weibo=driver.findElement(By.id("com.orvibo.homemate:id/sinaNickNameTextView"));
		if(!qq.getText().equals("未绑定"))
		{
			AppOperate.click(qq, "授权界面点击QQ");
			//点击解除绑定
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"解除绑定\")"), "点击解除绑定");
			//判断是否弹出提示框
			AppOperate.exitElement("下次请使用其他绑定帐号登录哦", driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"知道了\")"), "点击知道了");
			//判断弹框是否消失
			AppOperate.notExitElement("知道了", driver);
			//查看是否重新显示为绑定账号
			AppOperate.exitElement("绑定QQ", driver);
			//点击返回
			AppOperate.sendKeyEvent(4, "返回到授权登录界面", driver);
			
		}
		
		if(!weixin.getText().equals("未绑定"))
		{
			AppOperate.click(weixin, "授权界面点击微信");
			//点击解除绑定
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"解除绑定\")"), "点击解除绑定");
			//判断是否弹出提示框
			AppOperate.exitElement("下次请使用其他绑定帐号登录哦", driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"知道了\")"), "点击知道了");
			//判断弹框是否消失
			AppOperate.notExitElement("知道了", driver);
			//查看是否重新显示为绑定账号
			AppOperate.exitElement("绑定微信", driver);
			//点击返回
			AppOperate.sendKeyEvent(4, "返回到授权登录界面", driver);
			
		}
		
		if(!weibo.getText().equals("未绑定"))
		{
			AppOperate.click(weibo, "授权界面点击微信");
			//点击解除绑定
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"解除绑定\")"), "点击解除绑定");
			//判断是否弹出提示框
			AppOperate.exitElement("下次请使用其他绑定帐号登录哦", driver);
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"知道了\")"), "点击知道了");
			//判断弹框是否消失
			AppOperate.notExitElement("知道了", driver);
			//查看是否重新显示为绑定账号
			AppOperate.exitElement("绑定微博", driver);
			
		}
	}
	
	/**
	 * 意见反馈
	 */
	@Test(groups={"个人中心","个人信息","意见反馈"})
	public void feedback()
	{
		 newSleep(2);
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"意见反馈\")"), "点击'意见反馈'");
		 AppOperate.waitForTextDisappear(20, "我的", driver);
		 //截图对比是否一致
		 String feedbackphoto=ToolFunctions.getRandomstring(1);
		 screenCapCompare(driver, feedbackphoto);
		 boolean ifsame=compareimage.sameAs(FileOperate.getTestDatapFilePath()+File.separator+"feedback.png", FileOperate.getScreencapFilePath()+File.separator+feedbackphoto+".png", 0.99);
		 Assert.assertTrue(ifsame, "意见反馈界面与预期不符");
		 driver.context("WEBVIEW");
		 
	
		 AppOperate.sendKeys(driver.findElement(By.xpath("//*[@id=\"feedback-content\"]")), "反馈内容框中输入内容", "反馈内容");
	
		 //点击添加图片按扭
		 AppOperate.click(driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[2]/div/div")), "点击添加图片按扭");


	    AppOperate.tapByadb(189, 1060);
		 //判断是否有弹框弹出
	//	 Assert.assertTrue(driver.findElement(By.className("img-selector-wrapper")).isDisplayed());
		 //从相册中选择一张图片
/*		AppOperate.click(driver.findElement(By.xpath("//*[@id=\"img-selector\"]/div[2]/div[1]")), "点击从相册中选取");
		AppOperate.click(driver.findElement(By.className("photo")), "点击从相册中选取");*/
		AppOperate.tapByadb(542,1711);
          driver.context("NATIVE_APP");
		 //判断相册图片是否为空s
		 try
		 {
		 Assert.assertTrue(driver.findElements(By.id("com.miui.gallery:id/pick_num_indicator")).size()>0);
		 }
		 catch(Exception e)
		 {
			 Log.logInfo("相册中图片为空！！");
		 }
		 AppOperate.click(driver.findElement(By.id("com.miui.gallery:id/pick_num_indicator")), "选中一张照片");
		 driver.context("WEBVIEW");
		 //判断是否有添加一张图片
	/*	 AppOperate.exitElement("feedback-img", driver);*/
		 AppOperate.sendKeys(driver.findElement(By.id("feedback-contact")), "输入联系方式", "13088884762");
         //点击提交 
		 driver.findElement(By.xpath("//*[@id=\"submit-btn\"]")).click();
		 //判定会弹出toast
		// Assert.assertTrue(driver.findElement(By.id("toast")).getText().equals("内容至少5个字"));
		 //多输入几个字
		 AppOperate.sendKeys(driver.findElement(By.xpath("//*[@id=\"feedback-content\"]")), "反馈内容框中输入内容", "多输入几个字看看");
		 newSleep(2);
		 driver.findElement(By.xpath("//*[@id=\"submit-btn\"]")).click();
		// Assert.assertTrue(AppOperate.ifexitElement("提交 中", driver));
		 //点击提交按扭
		 AppOperate.tapByadb(526, 1851);
		 AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/title_text"), 5);
		 
			
	}
	
	
	/**
	 * 没有成员，检查家庭管理
	 * 家庭管理页面，点击右上角邀请家人
	 * 邀请家人页面，用户账号输入非手机号和非邮箱账号，点击发起邀请
	 * 选择姓名下的第二个手机号
	 * 主账号登录已注册账号，有设备，邀请未注册的手机号或邮箱
	 * 主账号登录已注册账号，有设备，邀请不合规范的手机号
	 * 主账号登录已注册账号，有设备，邀请已注册的手机号，手动输入手机号，发送邀请成功
	 * 发送邀请成功，点击确定
	 * 家庭成员页面已显示手机号成员，点击手机号成员右侧删除按钮
	 */
	@Test(groups={"个人中心","个人信息","家庭管理"})
	public void homemanage()
	{
		 newSleep(2);
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭管理\")"), "点击'家庭管理'");
		 //判断“发起邀请”是否可点击
		 Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"发起邀请\")").isEnabled(), "发起邀请默认是激活的");
		 //点击联系人选择按扭
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/contactImageView")), "点击添加联系人选择按扭");
		 //判断是否跳 转到联系人选择界面
		Assert.assertTrue( ToolFunctions.cmdmessage("adb shell dumpsys window w|grep name=", "contacts"), "未跳转到联系人选择界面");
		//返回家庭管理界面
		AppOperate.sendKeyEvent(4, "返回家庭管理界面", driver);
		//手动输入号码,分别输入小于11位，11位，大于11位
		EditText edit=new EditText(driver.findElement(By.id("com.orvibo.homemate:id/accountEditText")));
		edit.inputlesphonenumber(driver);
		//输入号码小于11位时检查发起邀请按扭是否激活
		 Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"发起邀请\")").isEnabled(), "号码小于11位发起邀请默认是激活的");
		 //清掉再输入大于11位
		 edit.clear();
		 edit.inputgrephonenumber(driver);
		 Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"发起邀请\")").isEnabled(), "号码大于11位发起邀请默认是激活的");
		 //清掉再输入11位未注册的手机号
		 edit.clear();
		 edit.inputphonenumber(driver);
		 Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"发起邀请\")").isEnabled(), "号码等于11位发起邀请未被激活");
		 //点击发起邀请
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/addTextView")), "点击发起邀请按钮");
		 //判断是否会弹出未注册的提示框
		 AppOperate.waitForText(10, "com.orvibo.homemate:id/titleTextView");
		 AppOperate.exitElement("该用户尚未注册", driver);
		 AppOperate.exitElement("试试微信快速邀请吧", driver);
		 //点击微信邀请，判断是否会跳 转到微信界面
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/rightButton")), "点击微信邀请");
		 Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w|grep name=", "com.tencent.mm"), "未跳转到微信界面");
		//返回家庭管理界面
		AppOperate.sendKeyEvent(4, "返回家庭管理界面", driver);
		//再次点击发送邀请，弹出提示后取消
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/addTextView")), "点击发起邀请按钮");
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/leftButton")), "点击取消");
		//判断弹框是否消失
		Assert.assertFalse(AppOperate.ifexitElement("取消", driver), "点击取消弹框未消失");
	}
	
	/**
	 * 关于
	 */
	@Test(groups={"个人中心","个人信息","关于"})
	public void about()
	{
		 newSleep(2);
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"关于\")"), "点击'关于'");
		 //截图对比是否一致
		 String about=ToolFunctions.getRandomstring(1);
		 screenCapCompare(driver, about);
		 boolean ifsame=compareimage.sameAs(FileOperate.getTestDatapFilePath()+File.separator+"about.png", FileOperate.getScreencapFilePath()+File.separator+about+".png", 0.97);
		 Assert.assertTrue(ifsame, "关于界面与预期不符");
		
	}
	
	/**
	 * 个人信息进入相机拍照界面
	 */
	
	public void entercamera()
	{
		AndroidElement photo=driver.findElement(By.id("com.orvibo.homemate:id/userPicImageView"));
		AppOperate.click(photo, "个人信息中点击“头像”");
		AppOperate.waitelementexit(driver,By.className("android.widget.TextView"), 3);
		//判断是否弹出选择框
		List<AndroidElement> list=driver.findElementsByClassName("android.widget.TextView");
		Assert.assertEquals(list.size(), 3);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"拍照\")"), "点击选择框中的“拍照”按扭");
		
	
		
	}
	
	/**
	 * 进入个人信息
	 */
	
	public void enterPersoninfo()
	{   newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/iv_personal_user_icon")), "点击个人信息头像");

		
	}
	
	
	
	
	
	
	public void enterMyhost()
	{   
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的主机\")"), "点击'我的主机'");
		newSleep(2);
		
	}
	public void enterMore()
	{
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的主机\")"), "点击'我的主机'");
		AppOperate.swipeToUp(driver, "向上滑屏");
		AppOperate.click((AndroidElement) driver.findElementById("com.orvibo.homemate:id/moreTextView"), "点击更多");
		newSleep(2);
	}
	


	@AfterMethod(alwaysRun=true)
	public void tearDown(){
		//关闭appium 资源
		System.out.println("**********");
		driver.quit();
		
	}
	
	public static UserInfo getaccount()
	{
		return account;
	}
	
	public static void main(String[] args)
	{
	
/*		Map<Integer, DataBean>  bean = new HashMap<>();
	    ReadExcel  excel =  new ReadExcel();
		ExcelData  excelData =  new ExcelData();
		excelData.setNumerSheet(5);
		excelData.setCaseType(Constant.CASETYPE_ANDROID);
		excelData.setLastIndex(21);
		excel.readXls("personInfo_TestData.xls", excelData,bean);
		System.out.println("读了多少条用例"+bean.size());
		System.out.println(bean.size());

		
		for (int i = 0; i < bean.size(); i++) {
			System.out.println("用例名称"+bean.get(i).getTestCaseName());
		}*/
		FileOperate.delectLogFiles();
		
	}



		
	

}
