package com.oribo.common;


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
		if (waitForText(20, "记住我的选择", "禁止", "允许")) {
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
		return waitForText(10, TargetText);
	}

	/**
	 * 返回至首页。
	 */
	@Override
	public void backToHomePage(String[] contents) {
		int times = 0;
		while (!waitForText(5, contents)) {
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
	public static List<AndroidElement>  getElementsByType (String locationElement,String attribute)throws Exception{
		//Driver.getAndroidDriver(null).manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS); //等待元素出现
		List<AndroidElement>  list = null;
		if (locationElement.equals(ElemenAttribute.BYNAME)) {
			list =(List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByName(attribute);
		} 
		else if (locationElement.equals(ElemenAttribute.BYCLASSNAME)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByClassName(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYID)) {
			list = (List<AndroidElement>) Driver.getAndroidDriver(null).findElementsById(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYACCESSID)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByAccessibilityId(attribute);
		}
		else if (locationElement.equals(ElemenAttribute.BYCSSSELECTOR)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByCssSelector(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYLINKTEXT)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByLinkText(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYPARTEXT)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByPartialLinkText(attribute);
		}
		else if (locationElement.equals(ElemenAttribute.BYTAGNAME)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByTagName(attribute);	
		}
		else if (locationElement.equals( ElemenAttribute.BYXPATH)) {
			list = (List<AndroidElement>)Driver.getAndroidDriver(null).findElementsByXPath(attribute);		
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
	public static AndroidElement getElementByType (String locationElement,String attribute)throws Exception{
		AndroidElement   list = null ;
		System.out.println("定位元素");
		if (locationElement.equals(ElemenAttribute.BYNAME)) {
			list =  Driver.getAndroidDriver(null).findElementByName(attribute);		
		} 
		else if (locationElement.equals(ElemenAttribute.BYCLASSNAME)) {
			list = Driver.getAndroidDriver(null).findElementByClassName(attribute);		
		}
		else if (locationElement.equals(ElemenAttribute.BYID)) {
			list =  Driver.getAndroidDriver(null).findElementById(attribute);
		}
		else if (locationElement.equals(ElemenAttribute.BYACCESSID)) {
			list = Driver.getAndroidDriver(null).findElementByAccessibilityId(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYCSSSELECTOR)) {
			list =  Driver.getAndroidDriver(null).findElementByCssSelector(attribute);	
		}
		else if (locationElement.equals(ElemenAttribute.BYLINKTEXT)) {
			list = Driver.getAndroidDriver(null).findElementByLinkText(attribute);		
		}
		else if (locationElement.equals(ElemenAttribute.BYPARTEXT)) {
			list =  Driver.getAndroidDriver(null).findElementByPartialLinkText(attribute);		
		}
		else if (locationElement.equals(ElemenAttribute.BYTAGNAME)) {
			list =  Driver.getAndroidDriver(null).findElementByTagName(attribute);		
		}
		else if (locationElement.equals( ElemenAttribute.BYXPATH)) {
			list =  Driver.getAndroidDriver(null).findElementByXPath(attribute);	
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

