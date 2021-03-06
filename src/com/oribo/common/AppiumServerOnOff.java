package com.oribo.common;

import java.util.concurrent.Executor;

import org.apache.commons.exec.*;
import org.testng.annotations.Test;

import com.oribo.utils.FileOperate;

import java.io.File;
import java.io.IOException;

/**
 * 开启或关闭appium服务
 * Created by penghong on 17/2/17.
 */
public class AppiumServerOnOff extends Thread {
	
	/**
	 * 开启Appium服务
	 */
	

	public static void startAppium() { 
		String logpath=FileOperate.getLogPath()+File.separator+"appium.log";
        try {
        	System.out.println("开启appium服务");
			Runtime.getRuntime().exec("cmd /k appium --log "+logpath);
			Thread.sleep(10000);
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
      
        
	}

  @Override
	public void run() {
	  while(true)
	  {
	  startAppium();
	  }
		
	}

/**
   * 关闭appium服务
   */
   public static  void closeAppium() { 
       try {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
	    
	} catch (IOException e) {
		System.out.println(e.getMessage());
	    
	} 

   }
   
   public static void main(String args[])
   {
	 
	   new AppiumServerOnOff().start();

   }
   
}
