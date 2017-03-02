package com.oribo.common;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import io.appium.java_client.android.AndroidDriver;


public class AppiumFrame {
	AndroidDriver driver;
	
	
	@BeforeClass
	public void beforeclass()
	{
	 
		
	}
	
	@BeforeMethod
	public void beforeMethod() throws Exception{
		DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "6.0.1");
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("appPackage", "com.orvibo.homemate");
        capabilities.setCapability("appActivity", ".common.launch.LaunchActivity");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//闅愬紡绛夊緟锛屽叏灞�绛夊緟10s涓嶇鍏冪礌鏄惁宸茬粡鍔犺浇     
    	

    }
	
	@AfterMethod
	public void teardown(){
		driver.quit();
	
	}
	
	@AfterClass
	public void afterClass()
	{
	


	}
	

}
