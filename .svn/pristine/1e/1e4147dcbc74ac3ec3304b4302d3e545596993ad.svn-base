package com.oribo.monitor;

import org.openqa.selenium.By;

import com.oribo.common.AppOperate;
import com.oribo.common.TestcaseFrame;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class PromptListener extends Thread{
	AndroidDriver driver;
	public  boolean exit=false;
	public PromptListener(AndroidDriver driver,boolean ifexit)
	{
		this.driver=driver;
		this.exit=ifexit;
	}
	public void setexit(boolean ifexit)
	{
		this.exit=ifexit;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!exit)
		{
			TestcaseFrame.newSleep(500);
	
			try{
				AndroidElement prot=(AndroidElement) driver.findElementByAndroidUIAutomator("text(\"发现新设备\")");
				if(prot.isDisplayed())
				{   
					System.out.println("找到弹框了吧");
					AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"取消\")"), "发现新设备点击取消");
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
	}

}
