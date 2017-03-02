package com.oribo.common;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
/**
 * 单利模式   AndroidDriver 只有一个实例模式
 * @author Administrator
 *
 */
public class Driver {
    private  Driver() {}  
    private static  AndroidDriver<AndroidElement>  androidDriver = null;
    public static   AndroidDriver<AndroidElement> getAndroidDriver(Capabilities capabilities ) {  
         if (androidDriver == null && capabilities !=null) { 
        	 try {
        		System.out.println(capabilities.getPort());
        		 androidDriver = new AndroidDriver<AndroidElement>(new URL(capabilities.getPort()), capabilities.getCapability());	
			} catch (Exception e) {
				e.printStackTrace();
			}       
         }  
          TestcaseFrame.newSleep(2);
        return androidDriver;  
    }
	
}
