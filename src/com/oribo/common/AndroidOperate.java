package com.oribo.common;

/**
 * 封装android的特性操作（脚本中暂未使用）
 */

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.oribo.log.Log;


/**
 * Created by cindy on 16/10/11.
 * android 的页面基础操作的封装，如查找元素
 */
public class AndroidOperate extends AppOperate {

	private static AndroidDriver driver;

	public AndroidOperate(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}



	public void acceptAlert() {
		Log.logInfo("accept alerts");
		if (waitForText(driver,20, "记住我的选择", "禁止", "允许")) {
			Log.logInfo("点击[允许]");
			driver.findElement(By.name("允许")).click();
		}
	}

	@Override
	public void scrollToUp(String TargetText) {
		scrollTo(TargetText);
	}

	@Override
	public void scrollToDown(String TargetText) {
		scrollTo(TargetText);
	}


	@Override
	public Boolean IdentifyIsDisplay(String[] TargetText, WebElement element) {
		Log.logInfo("等待输入验证码");
		return waitForText(driver,10, TargetText);
	}

	/**
	 * 返回至首页。
	 */
	@Override
	public void backToHomePage(String[] contents) {
		int times = 0;
		while (!waitForText(driver,5, contents)) {
			if (times >= 30) {
				Log.logInfo("尝试多次未能返回到首页,终止操作!");
				break;
			}
			Log.logInfo("点击返回按钮");
			driver.pressKeyCode(AndroidKeyCode.BACK);
			sendKeyEvent(AndroidKeyCode.BACK, "点击返回按钮",driver);
			times++;
		}
	}


	/**
	 * 根据Excel 的类型定位元素
	 * @param type appium 定位元素的类型
	 * @param attribute 属性值
	 * @return
	 * @throws Exception  抛出查找元素的异常
	 */
	@SuppressWarnings("unchecked")
	public static List<AndroidElement>  getElementsByType (String locationElement,String attribute,AndroidDriver driver)throws Exception{
		//Driver.getAndroidDriver(null).manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS); //等待元素出现
		List<AndroidElement>  list = null;
		if (locationElement.equals(ElemenAttribute.BYNAME)) {
			list =(List<AndroidElement>)driver.findElementsByName(attribute);
		} 
		else if (locationElement.equals(ElemenAttribute.BYCLASSNAME)) {
			list = (List<AndroidElement>)driver.findElementsByClassName(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYID)) {
			list = (List<AndroidElement>) driver.findElementsById(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYACCESSID)) {
			list = (List<AndroidElement>)driver.findElementsByAccessibilityId(attribute);
		}
		else if (locationElement.equals(ElemenAttribute.BYCSSSELECTOR)) {
			list = (List<AndroidElement>)driver.findElementsByCssSelector(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYLINKTEXT)) {
			list = (List<AndroidElement>)driver.findElementsByLinkText(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYPARTEXT)) {
			list = (List<AndroidElement>)driver.findElementsByPartialLinkText(attribute);
		}
		else if (locationElement.equals(ElemenAttribute.BYTAGNAME)) {
			list = (List<AndroidElement>)driver.findElementsByTagName(attribute);	
		}
		else if (locationElement.equals( ElemenAttribute.BYXPATH)) {
			list = (List<AndroidElement>)driver.findElementsByXPath(attribute);		
		}
		TestcaseFrame.newSleep(1);
		return list;	
	}


	/**
	 * 根据Excel 的类型定位元素
	 * @param type appium 定位元素的类型
	 * @param attribute 属性值
	 * @return
	 * @throws Exception  抛出查找元素的异常
	 */
	@SuppressWarnings("unchecked")
	public static AndroidElement getElementByType (String locationElement,String attribute,AndroidDriver driver)throws Exception{
		AndroidElement   list = null ;
		System.out.println("定位元素");
		if (locationElement.equals(ElemenAttribute.BYNAME)) {
			list =  (AndroidElement) driver.findElementByName(attribute);		
		} 
		else if (locationElement.equals(ElemenAttribute.BYCLASSNAME)) {
			list = (AndroidElement) driver.findElementByClassName(attribute);		
		}
		else if (locationElement.equals(ElemenAttribute.BYID)) {
			list =  (AndroidElement) driver.findElementById(attribute);
		}
		else if (locationElement.equals(ElemenAttribute.BYACCESSID)) {
			list = (AndroidElement) driver.findElementByAccessibilityId(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYCSSSELECTOR)) {
			list =  (AndroidElement) driver.findElementByCssSelector(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYLINKTEXT)) {
			list = (AndroidElement) driver.findElementByLinkText(attribute);		
		}
		else if (locationElement.equals(ElemenAttribute.BYPARTEXT)) {
			list =  (AndroidElement) driver.findElementByPartialLinkText(attribute);		
		}
		else if (locationElement.equals(ElemenAttribute.BYTAGNAME)) {
			list =  (AndroidElement) driver.findElementByTagName(attribute);		
		}
		else if (locationElement.equals( ElemenAttribute.BYXPATH)) {
			list =  (AndroidElement) driver.findElementByXPath(attribute);	
		}
		TestcaseFrame.newSleep(1);
		System.out.println("结束");
		return list;	
	}


	/**
	 * 定位方式接口，对应用例中的查找方式字段
	 * @author Administrator
	 *
	 */
	public interface ElemenAttribute{
		public String BYNAME = "ByName";
		public String BYCLASSNAME ="ByClassName";
		public String BYID="ById";
		public String BYACCESSID="ByAccessibilityId";
		public String BYCSSSELECTOR ="ByCssSelector";
		public String BYLINKTEXT = "ByLinkText";
		public String BYPARTEXT ="ByPartialLinkText";
		public String BYTAGNAME = "ByTagName";
		public String BYXPATH = "ByXPath";  	 	
	}

}

