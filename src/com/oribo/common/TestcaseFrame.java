package com.oribo.common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.oribo.dataprovider.AppBean;
import com.oribo.dataprovider.Constant;
import com.oribo.dataprovider.UserInfo;
import com.oribo.log.Log;
import com.oribo.ReadExcelCase.DataBean;
import com.oribo.ReadExcelCase.ExcelData;
import com.oribo.ReadExcelCase.ReadExcel;
import com.oribo.report.CreateHtmlreport;
import com.oribo.report.MessageOutput;
import com.oribo.report.SendTestReportEMS;
import com.oribo.utils.FileOperate;
import com.oribo.utils.TimeUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestcaseFrame {
	public static  Map<Integer, DataBean>  bean = new HashMap<>();
	Logger mLogger;
	public String appport ;
	public String appudid;
	public String testphone;
	public String appplatformVersion;
	public String appapk;
	public String password;
	public static  String picname;
	FileHandler fileHandler;
	static Logger logger;
	AppBean   appbean = AppBean.getAppBean();
	static AndroidDriver<AndroidElement>  driver = null;
    
	
	public void testSetUp()
	{
		
		File classpathRoot = new File(System.getProperty("user.dir"));//本地的路径
		File appDir = new File(classpathRoot, "apps");//apk 存放的路径
		File app = new File(appDir, appbean.getApk());//apk 的名字	
		//android Appium 的 基础参数的设置
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android Emulator");	
		capabilities.setCapability("device", "Selendroid");//测试H5页页
		capabilities.setCapability("platformVersion", appbean.getPlatformVersion() );
		 capabilities.setCapability("udid", appbean.getUid());
	//	capabilities.setCapability("app", app.getAbsolutePath()); 	
		//capabilities.setCapability( "automationName","Selendroid");//这句话设置可以获取toast 消息
		 //capabilities.setCapability( "automationName","uiautomator2");
		capabilities.setCapability("appPackage", "com.orvibo.homemate");
		capabilities.setCapability("appActivity", "com.orvibo.homemate.common.launch.LaunchActivity");
		capabilities.setCapability("unicodeKeyboard", "True");  
		capabilities.setCapability("resetKeyboard", "True"); 
		capabilities.setCapability("sessionOverride", true); // 每次启动时覆盖session，否则第二次后运行会报错不能新建session
		//capabilities.setCapability("setWebContentsDebuggingEnabled", "True");	
		//capabilities.setCapability("noSign", "True");
		capabilities.setCapability("noReset", true);//实现app不是每次都安装
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);//全局等待5秒
		
	
	}
	
	public static AndroidDriver<AndroidElement> getDriver()
	{
		return driver;
	}
	
	
	/**
	 * 判断是否登录账号
	 * @return
	 */
	public boolean isLogin()
	{
		return true;
	}
	
	
	//睡眠
	public static void newSleep(int p_time)
	{
		try
		{
			Thread.sleep(p_time * 1000);
		} catch (InterruptedException e)
		{

		}

	}
	
	/**
	 * 按钮不能点击返回期望结果
	 * @param btn
	 * @param bean
	 * @param logge
	 * @return
	 */
	public String  btnunenanled(AndroidElement  btn,DataBean bean,Logger  logge){
		try {
			if (!btn.isEnabled()) {
				return bean.getExpectValue();
			} else{
				return btn.getText()+"--能点击"	;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}	

	}
	
	/**
	 * 按钮能点击返回期望结果
	 * @param btn
	 * @param bean
	 * @param logge
	 * @return
	 */
	public String  btniSenanled(AndroidElement  btn,DataBean bean,Logger  logge){
		try {
			if (btn.isEnabled()) {
				return bean.getExpectValue();
			} else{
				return btn.getText()+"--不能点击"	;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}
	
	/**
	 * 封装AssertEquals方法，判断是否与预期结果一致，如果正确actualValue传DataBean.getExpectValue()值，错误则传实际值
	 * @param driver
	 * @param info：验证信息说明
	 * @param expectedValue：期望结果
	 * @param actualValue：实际结果
	 */
	/*public static String newAssertEquals(AppiumDriver driver,DataBean  bean,Object actualValue,Logger mlogger,String loginfo)
	{

		bean.setActualValue(String.valueOf(actualValue));
		if (bean.getExpectValue().equals(bean.getActualValue()))
		{
			LoggerUtil.logOutput(mlogger, loggerLevel.LEVLEINFO, bean.getInstructions() +"用例结果  ：      "+MessageOutput.mActualResult+ MessageOutput.mExpectedResult + MessageOutput.mMatch);
			//mlogger.(loginfo+MessageOutput.mActualResult+ MessageOutput.mExpectedResult + MessageOutput.mMatch);
			CreateHtmlreport.writeToLog(bean.getTestCaseName(), bean.getInstructions(),bean.getExpectValue(), "跟期望值一样", "PASS");
			return MessageOutput.SUCCESS;
		} else
		{			
			
			LoggerUtil.logOutput(mlogger, loggerLevel.LEVELSERVER, bean.getInstructions() +"用例结果  ：      "+actualValue);
			mlogger.severe(bean.getInstructions() +"用例结果  ：      "+loginfo+MessageOutput.mActualResult+ MessageOutput.mExpectedResult + MessageOutput.mNot
					+ MessageOutput.mMatch);
			CreateHtmlreport.writeToLog(bean.getTestCaseName(),bean.getInstructions(),bean.getExpectValue(), bean.getActualValue(), "FAIL");
			screenShort(driver, bean.getTestCaseName() + MessageOutput.mActualResult+ MessageOutput.mExpectedResult 
					+ MessageOutput.mNot+ MessageOutput.mMatch + "__截图",bean.getInstructions());
			//driver.closeApp();
			//driver.quit();// Assert结果为false，脚本结束运行
			return MessageOutput.FAIL;
		}
	}*/
	
	/**
	 * 封装verifyEquals方法
	 * @param driver
	 * @param info：验证信息说明
	 * @param expectedValue：期望结果
	 * @param actualValue：实际结果
	 */
	public static String newVerifyEquals (AppiumDriver driver, String info,Object instrut,Object expectedValue, Object actualValue)
	{
		if (expectedValue != null & expectedValue.equals(actualValue))
		{
			logger.info(MessageOutput.mActualResult+ MessageOutput.mExpectedResult + MessageOutput.mMatch);
			CreateHtmlreport.writeToLog(info,instrut, expectedValue, actualValue, "PASS");
			return "通过";
		} else
		{
			logger.severe(MessageOutput.mActualResult+ MessageOutput.mExpectedResult + MessageOutput.mNot
					+ MessageOutput.mMatch);
			CreateHtmlreport.writeToLog(info, instrut,expectedValue, actualValue, "FAIL");
			screenShort(driver, info + MessageOutput.mActualResult+ MessageOutput.mExpectedResult
					+ MessageOutput.mNot+ MessageOutput.mMatch + "__截图",instrut.toString());
			return "失败";
		}
	}

	
	
	
	/**
	 *  自动截图并保存，参数指定某个具体的driver和图片的名称
	 *    调用截图方法需要将浏览器的语言手动设置成中文；
	 *    filename为测试用例名称，可通过databean获取
	 * @param driver2
	 * @param captureName
	 */
	public static void screenShort(AppiumDriver driver2, String captureName,String filname)
	{   
		File screenShortFile = driver2.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy_MM_dd_hh_mm_ss");
		String dateStr = dateFormat.format(new Date());
		try
		{
			//此FileUtils 为org.apache.commons.io下面的类，进行文件的操作，复制文件
			FileUtils.copyFile(screenShortFile, FileOperate.getPicturePath(filname+"_"+AppBean.getAppBean().getPhone()+"_"+AppBean.getAppBean().getApk(), dateStr, captureName));

		} catch (IOException e)
		{
			throw new RuntimeException("截图失败：" + e);
		}
	}
	/**
	 * 测试结果截图，第二个参数传递测试用例名称
	 * @author sunnypeng
	 * @param driver
	 * @param addinfo
	 */
	
	
	public static void screenCapresult(AndroidDriver driver,String addinfo)
	{   
		String path=FileOperate.getScreencapFilePath();
		File screenShot = driver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShot,new File(path,addinfo+TimeUtils.gettime()+".png"));
			picname=addinfo+TimeUtils.gettime()+".png";
		} catch (IOException e) {
			
			e.printStackTrace();
		}	

	}
	
	
	/**
	 * 测试对比截图
	 * @author sunnypeng
	 * @param driver
	 * @param addinfo
	 */
	
	
	public static void screenCapCompare(AndroidDriver driver,String filename)
	{   
		String path=FileOperate.getScreencapFilePath();
		File screenShot = driver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShot,new File(path,filename+".png"));
			picname=filename+".png";
			Log.logInfo("已截图,图片名称为:"+picname);
		} catch (IOException e) {
			
			e.printStackTrace();
		}	

	}
	
	
	
	public static String scrennfilename()
	{   
		System.out.println("截图文件名为:"+picname);
		return picname;
	}
	
	/**
	 * 发送测试报告
	 */
	public static void sendReport(String reportname,String receiver)
	{   
		String logpath=System.getProperty("user.dir")+File.separator+"Log";
		String logzip=System.getProperty("user.dir")+File.separator+"Log.zip";
		String reportpath=System.getProperty("user.dir")+File.separator+"test-output";
		String reportzip=System.getProperty("user.dir")+File.separator+"report.zip";
		
		//重命名测试报告名称
		FileOperate.copyFile(System.getProperty("user.dir")+File.separator+"test-output\\index.html",System.getProperty("user.dir")+File.separator+"test-output\\"+reportname+".html");
		
		//压缩测试报告
		FileOperate.zip1(reportzip, new File(reportpath));
		//压缩LOG
		FileOperate.zip1(logzip, new File(logpath));
		
		SendTestReportEMS ems=new SendTestReportEMS();
		ems.setAddress("15079034630@126.com", receiver, reportname+"测试报告");
		ems.setAffix(logzip, "Log.zip");
		ems.send("smtp.126.com", "15079034630@126.com", "penghong1987","本次测试已完成，附件为测试日志，测试报告路径为："+reportpath+File.separator+reportname+".html");
		System.out.println("测试报告发送完成");
	}
	


}