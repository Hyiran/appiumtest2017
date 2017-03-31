package com.oribo.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.oribo.utils.FileOperate;
import com.oribo.utils.TimeUtils;

/**
 * 抓取错误日志
 * @author test1
 *
 */
public class ErrorLog {
	static Process logProcess;
	static String logFilePath;
	static String logFileName;
	
	public static  void catchLog(String casename)
	{   

		logFilePath=FileOperate.getAnrlogFilePath();
		File path = new File(logFilePath);
		
		try {
			File logFile=new File(path,TimeUtils.gettime()+casename+".txt");
			logFileName=logFile.getName();
			if(!logFile.exists())
					logFile.createNewFile();
			else
				logFile.delete();
			
			
			logProcess=Runtime.getRuntime().exec("cmd /k adb logcat -v threadtime");
			InputStreamReader ir=new InputStreamReader(logProcess.getInputStream());
			BufferedReader br=new BufferedReader(ir);
			String str;
			
			long t1= System.currentTimeMillis();
			FileWriter fw=new FileWriter(logFile,true);
			while((str=br.readLine())!=null)
			{   
				long t2 = System.currentTimeMillis();

				
				fw.write(str);
				fw.write("\n");
				fw.flush();
				if(t2-t1>1000*20)
				{   
					
					break;
				}
			}
			ir.close();
			br.close();
			fw.close();
		
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	public static String getLogFilename()
	{
		return logFileName;
	}
	
	public static void clearLog()
	{
		try {
			Runtime.getRuntime().exec("cmd /k adb logcat -c");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void delLog()
	{
		File file=new File(logFileName);
		file.delete();
	}
	
/*	public static void main(String[] args)
	{   
		clearLog();
		catchLog("登录");
	}*/

}
