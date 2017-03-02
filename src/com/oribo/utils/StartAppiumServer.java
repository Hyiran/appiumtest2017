package com.oribo.utils;

import java.util.concurrent.Executor;

import org.apache.commons.exec.*;
import org.testng.annotations.Test;
import java.io.IOException;

/**
 * Created by yuyilong on 15/9/24.
 */
public class StartAppiumServer extends Thread {
	public static void startAppium() throws Exception { 
        Runtime.getRuntime().exec("taskkill /F /IM node.exe"); 
        Thread.sleep(3000);

        // 处理外部命令执行的结果，释放当前线程，不会阻塞线程 
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler(); 
        CommandLine commandLine = CommandLine.parse("cmd.exe /c node C:/\"Program Files (x86)\"/Appium/node_modules/appium/bin/appium.js"); 

       // 创建监控时间60s,超过60s则中断执行 
       ExecuteWatchdog dog = new ExecuteWatchdog(60 * 1000);
       DefaultExecutor executor = new DefaultExecutor(); 

       // 设置命令执行退出值为1，如果命令成功执行并且没有错误，则返回1 
       executor.setExitValue(1);
       executor.setWatchdog(dog);
       executor.execute(commandLine, resultHandler);
       resultHandler.waitFor(5000); 
       System.out.println("Appium server start"); }

   public static  void closeAppium() throws Exception { 
       Runtime.getRuntime().exec("taskkill /F /IM node.exe"); 
       System.out.println("关闭appium服务成功..."); 
   }
   
   public static void main(String args[])
   {
	   try {
		startAppium();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
}
