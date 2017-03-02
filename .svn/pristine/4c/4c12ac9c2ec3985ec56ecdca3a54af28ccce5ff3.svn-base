package com.oribo.report;

import java.io.File;
import java.io.IOException;

import com.oribo.utils.FileOperate;
import com.oribo.common.*;

public class CreateHtmlreport {
	static HTMLReport hr = new HTMLReport();
	public static String HtmlLog;
	public static String AppiumBaseLibLog;
	// 创建HTML类型的测试报告页面信息
	public static void createHtmlLog()
	{
		String resultsPath= FileOperate.getlogFilePath();
		String resultHtmlFileName = null;
		try {
			resultHtmlFileName = resultsPath + File.separator + FileOperate.getAppSavePath()
					+ "_" + ToolFunctions.getCurrentTime() + "_"+ "_log.html";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HtmlLog = resultHtmlFileName;
		System.out.println(resultHtmlFileName);
		String resultAppiumLogFileName =FileOperate. getInfolgPath(resultsPath,  FileOperate.getAppSavePath());
		AppiumBaseLibLog = resultAppiumLogFileName;
		//System.out.println(AppBean.getAppBean().getApk()+"_"+AppBean.getAppBean().getPhone());
		System.out.println("创建 HTML日志 ---" + resultHtmlFileName);
		try {
			hr.setup(resultHtmlFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closeLog()
	{
		hr.closeLog();
	}
	
	public static void writeToLog(String info,Object instrut, Object expected, Object actual,String result)
	{
		hr.logWriter(info, instrut,expected, actual, result);
	}
 


}
