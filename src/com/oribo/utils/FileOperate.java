package com.oribo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.oribo.dataprovider.Constant;
import com.oribo.common.ToolFunctions;
import com.oribo.dataprovider.AppBean;
/**
 * 文件操作类
 * @author Administrator
 *
 */
public class FileOperate {
	
	
	/**
	 * 获取报错日志路径
	 * @param name
	 * @return
	 */
	
	public static String getAnrlogFilePath()
	{
		String RESULTS_BASE_PATH = Constant.ANRLOG;
		File file =new File(RESULTS_BASE_PATH);
		if (!new File(RESULTS_BASE_PATH).exists())
		{
			  new File(RESULTS_BASE_PATH).mkdirs();
		}
		String resultsPath = file.getAbsolutePath();
		return  resultsPath;
		
		
	}
	
	/**
	 * 清空所有日志文件
	 */
	public static void delectLogFiles()
	{
		String anrpath=getAnrlogFilePath();
		String picpath=getScreencapFilePath();
		delectFile(new File(anrpath));
		delectFile(new File(picpath));
		delectFile(new File(getLogPath()+File.separator+"appium.log"));
		System.out.println("log文件清空完成");
		
	}
	
	/**
	 * 返回Log文件路径
	 * @return 
	 */
	public static String getLogPath()
	{
		String logpath=System.getProperty("user.dir")+File.separator+"Log";
		return logpath;
	}
	
	/**
	 * 获取事件日志路径
	 * @param name
	 * @return
	 */
	
	public static String getEventlogFilePath(String name)
	{
		String RESULTS_BASE_PATH = Constant.EVENTLOG+name;
		File file =new File(RESULTS_BASE_PATH);
		if (!new File(RESULTS_BASE_PATH).exists())
		{
			  new File(RESULTS_BASE_PATH).mkdirs();
		}
		String resultsPath = file.getAbsolutePath();
		return  resultsPath;
		
	}
	
	/**
	 * 获取截图文件路径
	 * @param name
	 * @return
	 */
	public static String getScreencapFilePath()
	{
		String RESULTS_BASE_PATH = Constant.SCREENCAP;
		File file =new File(RESULTS_BASE_PATH);
		if (!file.exists())
			  file.mkdir();
		
		String resultsPath = file.getAbsolutePath();
		return  resultsPath;
		
	}
	
	/**
	 * 获取测试数据路径
	 */
	public static String getTestDatapFilePath()
	{
		String RESULTS_BASE_PATH = Constant.TESTDATA;
		File file =new File(RESULTS_BASE_PATH);
		if (!file.exists())
			  file.mkdir();
		
		String resultsPath = file.getAbsolutePath();
		return  resultsPath;
		
	}
	
	
	
	/**
	 * 删除文件，参数为文件路径
	 * @param path
	 */
	public static void delectFile(File path)
	{
		  if (path.isDirectory()) 
		  {
			  File files[]= path.listFiles();
			  for(File file:files)
				  delectFile(file);
		  }
		  else
		  {
			  path.delete();
			  if(path.exists())
				  System.out.println("文件删除失败");
		  }
		
	}
	
	
	

	/**
	 * 返回日志存在的文件路径
	 * @return
	 */
	public static String getlogFilePath(String modelfile){
		String RESULTS_BASE_PATH = Constant.LOGGERFILEPATH+modelfile;
		File file =new File(RESULTS_BASE_PATH);
		if (!new File(RESULTS_BASE_PATH).exists())
		{
			  new File(RESULTS_BASE_PATH).mkdirs();
		}
		String resultsPath = file.getAbsolutePath();
		return  resultsPath;
	}

	
	
	
	
	/**
	 * 返回日志存在的文件路径
	 * @return
	 */
	public static String getlogFilePath(){
		String RESULTS_BASE_PATH = Constant.LOGGERFILEPATH;
		File file =new File(RESULTS_BASE_PATH);
		if (!new File(RESULTS_BASE_PATH).exists())
		{
			  new File(RESULTS_BASE_PATH).mkdirs();
		}
		String resultsPath = file.getAbsolutePath();
		return  resultsPath;
	}
	
	/**
	 * 返回打印日志的text 文件名
	 * @param resultsPath
	 * @param caseName
	 * @return
	 */
	public static String getInfolgPath(String resultsPath,String caseName ){
		String resultAppiumLogFileName =null;
		try {
			resultAppiumLogFileName = resultsPath + File.separator
					+ caseName + "_" +  ToolFunctions.getCurrentTime() + "_"
					+ "_appiumLog.log";
		} catch (Exception e) {
			// TODO: handle exception
		}

		return resultAppiumLogFileName; 
	}
	
	
	/**
	 * 返回错误截图的路径
	 * @param resultsPath
	 * @param caseName
	 * @return
	 */
	public static File getPicturePath(String caseName,String dateStr ,String captureName){
		String RESULTS_BASE_PATH  = "Log/ScreenCap/"+caseName+"/" +dateStr+ "_" + captureName;
		File file =new File(RESULTS_BASE_PATH);
		return file; 
	}
	
	
	/**
	 * 
	 * @return 返回  apk  和手机系统的版本
	 */
	public static String getAppSavePath(){
		return AppBean.getAppBean().getApk()+"_"+AppBean.getAppBean().getPhone();
	}
	
	/**
	 * 拷贝文件
	 * @param oldPath
	 * @param newPath
	 */
	
	public static void copyFile(String oldPath, String newPath) { 
		try { 
		int bytesum = 0; 
		int byteread = 0; 
		File oldfile = new File(oldPath); 
		if (oldfile.exists()) { //文件存在时 
		InputStream inStream = new FileInputStream(oldPath); //读入原文件 
		FileOutputStream fs = new FileOutputStream(newPath); 
		byte[] buffer = new byte[1444]; 
		int length; 
		while ( (byteread = inStream.read(buffer)) != -1) { 
		bytesum += byteread; //字节数 文件大小 
		fs.write(buffer, 0, byteread); 
		} 
		inStream.close(); 
		} 
		} 
		catch (Exception e) { 
		e.printStackTrace(); 

		} 

		} 
	
	/**
	 * 压缩文件
	 * @param args
	 */
	public  static void zip1(String zipFileName, File inputFile) {
        System.out.println("压缩中...");
   
        ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
	        BufferedOutputStream bo = new BufferedOutputStream(out);
	        zip2(out, inputFile, inputFile.getName(), bo);
	        bo.close();       
	        out.close(); // 输出流关闭
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("压缩完成");
    }    
	
	public static void zip2(ZipOutputStream out, File f, String base,BufferedOutputStream bo) throws Exception { 
	     int k=0;// 方法重载
	     	if (f.isDirectory()){
	     		File[] fl = f.listFiles();            
            if (fl.length == 0){               
            	out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            }            
            for (int i = 0; i < fl.length; i++) {
                zip2(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
            k++;
        } else {           
        	out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in); 
            int b;            
            while ((b = bi.read()) != -1) {
            bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();            
            in.close(); // 输入流关闭
        }
    }    
	
	
	
	
	public static void main(String[] args)
	{
		System.out.println(System.getProperty("user.dir")+File.separator+"Log");
	}
}
