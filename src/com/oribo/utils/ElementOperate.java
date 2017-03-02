package com.oribo.utils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oribo.dataprovider.Constant;
import com.oribo.common.Driver;
import com.oribo.common.TestcaseFrame;
import com.oribo.dataprovider.AppBean;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ElementOperate {

	private static Logger logger = Logger.getLogger(ElementOperate.class);

	private static String message; // 提示信息



	/**
	 * 根据控件类型不同，分别getElement
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */

	public static AndroidElement getElementByID(String value) throws Exception {
		AndroidElement element = Driver.getAndroidDriver(null).findElement(MobileBy.id(value));
		ElementOperate.sleep(1);
		return element;
	}

	public static AndroidElement getElementByName(String value) throws Exception {
		AndroidElement element =  Driver.getAndroidDriver(null).findElementByName(value);
		ElementOperate.sleep(1);
		return element;
	}

	public static AndroidElement getElementByClassName(String value) throws Exception {
		AndroidElement  element =  Driver.getAndroidDriver(null).findElementByClassName(value);
		ElementOperate.sleep(1);
		return element;
	}

	public static AndroidElement getElementByXPath(String value) throws Exception {
		AndroidElement element =  Driver.getAndroidDriver(null).findElementByXPath(value);
		ElementOperate.sleep(1);
		return element;
	}

	/**
	 * 根据控件类型获取元素列表
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static List<AndroidElement> getElementsByID(String value) throws Exception {
		List<AndroidElement> elements =  Driver.getAndroidDriver(null).findElements(MobileBy.id(value));
		ElementOperate.sleep(1);
		return elements;
	}

	public static List<AndroidElement> getElementsByName(String value) throws Exception {
		List<AndroidElement> elements =  Driver.getAndroidDriver(null).findElements(MobileBy.name(value));
		ElementOperate.sleep(1);
		return elements;
	}

	public static List<AndroidElement> getElementsByClassName(String value) throws Exception {
		List<AndroidElement> elements =  Driver.getAndroidDriver(null).findElements(MobileBy.className(value));
		ElementOperate.sleep(1);
		return elements;
	}

	public static List<AndroidElement> getElementsByXPath(String value) throws Exception {
		List<AndroidElement> elements =Driver.getAndroidDriver(null).findElements(MobileBy.xpath(value));
		ElementOperate.sleep(1);
		return elements;
	}

	/**
	 * 获取控件后进行点击操作
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static void clickByID(String value) throws Exception {
		getElementByID(value).click();
		ElementOperate.sleep(1);
	}

	public static void clickByName(String value) throws Exception {
		getElementByName(value).click();
		ElementOperate.sleep(1);
	}

	public static void clickByClassName(String value) throws Exception {
		getElementByClassName(value).click();
		ElementOperate.sleep(1);
	}

	public static void clickByXPath(String value) throws Exception {
		getElementByXPath(value).click();
		ElementOperate.sleep(1);
	}

	/**
	 * 根据index点击列表中的一项
	 * 
	 * @param value
	 * @param index
	 * @throws Exception
	 */
	public static void clickOneOfListByID(String value, int index) throws Exception {
		List<AndroidElement> list = ElementOperate.getElementsByID(value);
		list.get(index).click();
		ElementOperate.sleep(1);
	}

	public static void clickOneOfListByName(String value, int index) throws Exception {
		List<AndroidElement> list = ElementOperate.getElementsByName(value);
		list.get(index).click();
		ElementOperate.sleep(1);
	}

	public static void clickOneOfListByClassName(String value, int index) throws Exception {
		List<AndroidElement> list = ElementOperate.getElementsByClassName(value);
		list.get(index).click();
		ElementOperate.sleep(1);
	}

	/**
	 * 点击遍历列表中的每一项，并截图
	 * 
	 * @param value
	 * @throws Exception
	 */
	public static void clickEachByID(String value) throws Exception {
		List<AndroidElement> list = ElementOperate.getElementsByID(value);
		for (Iterator<AndroidElement> i = list.iterator(); i.hasNext();) {
			AndroidElement item = i.next();
			item.click();
			ElementOperate.sleep(2);
			ElementOperate.sleep(1);
			ElementOperate.back();
			ElementOperate.sleep(1);
		}
	}

	public static void clickEachByName(String value) throws Exception {
		List<AndroidElement> list = ElementOperate.getElementsByName(value);
		for (Iterator<AndroidElement> i = list.iterator(); i.hasNext();) {
			AndroidElement item = i.next();
			item.click();
			ElementOperate.sleep(2);
			ElementOperate.sleep(1);
			ElementOperate.back();
			ElementOperate.sleep(1);
		}
	}

	public static void clickEachByClassName(String value) throws Exception {
		List<AndroidElement> list = ElementOperate.getElementsByClassName(value);
		for (Iterator<AndroidElement> i = list.iterator(); i.hasNext();) {
			AndroidElement item = i.next();
			item.click();
			ElementOperate.sleep(2);
			ElementOperate.sleep(1);
			ElementOperate.back();
			ElementOperate.sleep(1);
		}
	}

	/**
	 * 根据控件进行输入操作
	 * 
	 * @param value
	 * @param inputvalue
	 * @throws Exception
	 */
	public static void inputByID(String value, String s) throws Exception {
		ElementOperate.getElementByID(value).sendKeys(s);
		ElementOperate.sleep(1);
	}

	public static void inputByClassName(String value, String s) throws Exception {
		ElementOperate.getElementByID(value).sendKeys(s);
		ElementOperate.sleep(1);
	}

	public static void inputByXPath(String value, String s) throws Exception {
		ElementOperate.getElementByXPath(value).sendKeys(s);
		ElementOperate.sleep(1);
	}

	/**
	 * 等待元素出现，时间为60s
	 * 
	 * @param value
	 * @param sec
	 * @throws Exception
	 */
	public static void waitByID(String value) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(null), 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value)));
		ElementOperate.sleep(1);
	}

	public static void waitByName(String value) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(null), 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(value)));
		ElementOperate.sleep(1);
	}

	public static void waitByClassName(String value) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(null), 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className(value)));
		ElementOperate.sleep(1);
	}

	public static void waitElementByXPath(String value) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(null), 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value)));
		ElementOperate.sleep(1);
	}

	/**
	 * 获取目标元素的文本
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getTextByID(String value) throws Exception {
		String text = ElementOperate.getElementByID(value).getText();
		ElementOperate.sleep(1);
		return text;
	}

	public static String getTextByName(String value) throws Exception {
		String text = ElementOperate.getElementByName(value).getText();
		ElementOperate.sleep(1);
		return text;
	}

	public static String getTextByClassName(String value) throws Exception {
		String text = ElementOperate.getElementByClassName(value).getText();
		ElementOperate.sleep(1);
		return text;
	}

	public static String getTextByXPath(String value) throws Exception {
		String text = ElementOperate.getElementByXPath(value).getText();
		ElementOperate.sleep(1);
		return text;
	}

	/**
	 * 返回键
	 * 
	 * @throws InterruptedException
	 */
	public static void back() throws Exception {
		logger.info("tap the back key");
		Driver.getAndroidDriver(null).pressKeyCode(4);
		ElementOperate.sleep(1);
	}

	/**
	 * 输入完成后隐藏键盘
	 * 
	 * @throws Exception
	 */
	public static void hideKeyboard() throws Exception {
		Driver.getAndroidDriver(null).hideKeyboard();
		ElementOperate.sleep(1);
	}

	/**
	 * 线程睡眠
	 * 
	 * @param sec
	 * @throws Exception
	 */
	public static void sleep(int sec) throws Exception {
		Thread.sleep(sec * 1000);
	}

	/**
	 * 封装根据坐标点击的方法，此方法适用于context 切换到webview时 x,y 分别是屏幕的坐标值
	 * 
	 * @param x
	 * @param y
	 * @param duration
	 * @param driver
	 */
	public static void clickScreen(int x, int y, int duration, AndroidDriver<?> driver) throws Exception {
		logger.info("tap the screen at " + "X : " + x + " " + "Y : " + y + " duration : " + duration);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Integer> tapObject = new HashMap<String, Integer>();
		tapObject.put("x", x);
		tapObject.put("y", y);
		tapObject.put("duration", duration);
		js.executeScript("mobile: tap", tapObject);
		ElementOperate.sleep(1);
	}

	/**
	 * 封装切换context到native方法
	 * 
	 * @throws Exception
	 */
	public void switchContextsToNative() throws Exception {
		logger.info("switch context from webview to native");
		Set<String> contextNames = Driver.getAndroidDriver(null).getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName);
			if (contextName.contains("NATIVE")) {
				Driver.getAndroidDriver(null).context(contextName);
			} else {
				System.out.println("no NATIVE");
			}
		}
		ElementOperate.sleep(1);
	}

	/**
	 * 封装切换context到webview方法
	 * 
	 * @throws Exception
	 */
	public void switchContextsToWebview() throws Exception {
		logger.info("switch context from native to webview");
		Set<String> contextNames = Driver.getAndroidDriver(null).getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName);
			if (contextName.contains("WEBVIEW")) {
				Driver.getAndroidDriver(null).context(contextName);
			} else {
				System.out.println("no WEBVIEW");
			}
		}
		ElementOperate.sleep(1);
	}

	/**
	 * 验证元素是否存在
	 * 
	 * @param actual
	 * @throws Exception
	 */
	public static void assertNullByID(String actual) throws Exception {
		org.testng.Assert.assertNull(ElementOperate.getElementByID(actual));
	}

	public static void assertNullByName(String actual) throws Exception {
		org.testng.Assert.assertNull(ElementOperate.getElementByName(actual));
	}

	public static void assertNotNullByName(String actual) throws Exception {
		org.testng.Assert.assertNotNull(ElementOperate.getElementByName(actual));
	}

	public static void assertNotNullByID(String actual) throws Exception {
		org.testng.Assert.assertNotNull(ElementOperate.getElementByID(actual));
	}

	/**
	 * 根据方法名调用
	 * 
	 * @param method
	 * @param value
	 * @throws Exception
	 */
	public static void invokeMethod(String method, String value) throws Exception {
		Method m = ElementOperate.class.getMethod(method, String.class);
		m.invoke(null, value);
		ElementOperate.sleep(1);
	}

	

	

	

	/**
	 * get postfix of the path
	 * @param path
	 * @return
	 */
	public static String getPostfix(String path) {
		if (path == null || Common.EMPTY.equals(path.trim())) {
			return Common.EMPTY;
		}
		if (path.contains(Common.POINT)) {
			return path.substring(path.lastIndexOf(Common.POINT) + 1, path.length());
		}
		return Common.EMPTY;
	}
	
	/**
	 * 根据具体坐标进行屏幕滑动
	 * @param driver
	 * @param startX：起始x坐标
	 * @param startY：起始y坐标
	 * @param endX：结束x坐标
	 * @param endY：结束y坐标
	 * @param repeat：需要滑动几次
	 */
	public void swipe(AppiumDriver driver,double startX,double startY,double endX,double endY,int repeat)
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		HashMap<String, Double> swipeObj = new HashMap<String, Double>();
		swipeObj.put("startX", startX);
		swipeObj.put("startY", startY);
		swipeObj.put("endX", endX);
		swipeObj.put("endY", endY);
		//滑动
		for (int i = 0; i <repeat; i++)
		{
			jse.executeScript("mobile: swipe", swipeObj);
		}
	}
	

	/**
	 * 封装屏幕滑动的方法
	 * @param direction：滑动的方向
	 */
	public void swipe(AppiumDriver driver,String direction)
	{
		//获取屏幕宽度和高度，为了兼容不同分辨率的屏幕，不使用坐标进行定位
		int width = driver.manage().window().getSize().width;//宽度
		int height = driver.manage().window().getSize().height;//高度
		if (direction!=null)
		{
			if ("left".equals(direction))
			{
				driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, 200);
			} 
			else if("right".equals(direction))
			{
				driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 200);
			}
			else if("up".equals(direction))
			{
				driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 200);
			}
			else if("down".equals(direction))
			{
				driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 200);
			}
			TestcaseFrame.newSleep(1);
		} else
		{
			throw new RuntimeException("请指定滑动的方向!");
		}
	}
	
	


}
