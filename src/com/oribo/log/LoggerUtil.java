package com.oribo.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;
import org.testng.reporters.EmailableReporter;

import com.oribo.dataprovider.Constant;
import com.oribo.ReadExcelCase.DataBean;
import com.oribo.common.ToolFunctions;
import com.oribo.log.LoggerUtil.LoggerFormater;
import com.oribo.log.LoggerUtil.loggerLevel;
import com.oribo.utils.FileOperate;
import com.oribo.utils.TimeUtils;

/**
 * log 的日志
 * @author Administrator
 *
 */
public class LoggerUtil {
	private static String filePath = "src/log4j.properties";
	/**
	 * 获得Logger
	 * @param mclass
	 * @return
	 */
	public static Logger  getLogger(Class  mclass){
		Logger logger = Logger.getLogger(mclass.getName());
		return  logger;
	}

	/**
	 * 获得FileHandler
	 * @param path
	 * @return
	 */
	public static FileHandler getFileHandler(String path){
		FileHandler fileHandler = null ;
		try {
			//fileHandler = new FileHandler( AppiumBaseLibLog);
			//fileHandler.setLevel(Level.INFO);
			//fileHandler.setFormatter(new SimpleFormatter());
			// 创建AppiumBaseLibLog.log
			fileHandler = new FileHandler(path);//path name  AppiumBaseLibLog
			fileHandler.setLevel(Level.INFO);//Level.INFO
			fileHandler.setFormatter(new SimpleFormatter());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return fileHandler;
	}
    
	/**
	 * 创建日志文件
	 * @param modelfile
	 * @param loggername
	 * @param mclass
	 * @return
	 */
	public static Logger getwriteFileLog(String modelfile,String loggername,Class  mclass){
		PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
		Logger  logger= null;
		try {
			logger = getLogger( mclass);
			//logger.setLevel(org.apache.log4j.Level.);
			FileHandler  handler = getFileHandler(FileOperate.getInfolgPath(FileOperate.getlogFilePath(modelfile), loggername));
			handler.setFormatter(new MyLogHander());
			logger.addHandler(handler);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(loggername+"创建日志失败");
		}    
		return logger;
	}

	/*public static void errorInfo(Logger  logger,String errorinfo){
		logger.info(Constant.ERROR+ errorinfo);    
	}
	 */
	public interface loggerLevel{
		public int LEVLEINFO=1;
		public int LEVELSERVER=2;
		public int LEVEL_WARMING = 3;

	}
	
	/**
	 * 设置日志等级，添加日志备注
	 * @param logger
	 * @param level
	 * @param info
	 */

	public static  void logOutput(Logger  logger,int level,String info){
		switch (level) {
		case 1:
			logger.setLevel(Level.INFO);
			logger.info(info);
			break;
		case 2:
			logger.setLevel(Level.SEVERE);
			logger.severe(info);
			break;
		case 3:
			logger.setLevel(Level.WARNING);
			logger.warning(info);
			break;
		default:
			break;
		}
	}

	/**
	 * 记录Info级别日志。
	 *
	 * @param message the message object.
	 */
	public static void logInfo(Object message,Logger  logger) {
		logger.info("[INFO] " + message);
		Reporter.log(ToolFunctions.getSimpleDateFormat() + " : " + "[INFO] " + message);
	}

	/**
	 * 记录测试步骤信息。
	 *
	 * @param message the message object.
	 */
	public static void logStep(Object message,Logger  logger) {
		logger.info("[STEP] " + message);
		Reporter.log(ToolFunctions.getSimpleDateFormat()+ " : " + "[STEP] " + message);
	}

	/**
	 * 记录测试流日志。
	 *
	 * @param message the message object.
	 */
	public static void logFlow(Object message,Logger  logger) {
		logger.info("[FLOW] " + message);
		Reporter.log(ToolFunctions.getSimpleDateFormat() + " : " + "[FLOW] " + message);
	}

	/**
	 * 记录Error级别日志。
	 *
	 * @param message the message object.
	 */
	public static void logError(Object message,Logger  logger) {
		logger.severe("[ERROR]   " + message);
		Reporter.log(ToolFunctions.getSimpleDateFormat() + " : " + "[ERROR]   " + message);
	}

	/**
	 * 记录Warn级别日志。
	 *
	 * @param message the message object.
	 */
	public static void logWarn(Object message,Logger  logger) {
		logger.warning("[WARN] " + message);
		Reporter.log(ToolFunctions.getSimpleDateFormat() + " : " + "[WARN] " + message);
	}

	/**
	 * 输出测试数据
	 * @param bean
	 * @param logger
	 */
	public static void  loggerdata(DataBean bean, Logger logger) {
		LoggerUtil.logOutput(logger,  loggerLevel.LEVLEINFO, bean.getTestCaseName() +"测试数据---" +bean.getData());
	}

	public static void closeLogger(Logger logger){
		logger.setLevel(Level.OFF);	
	}

	/**
	 * 自定义日志的格式
	 * @author Administrator
	 *
	 */
	static class MyLogHander extends Formatter { 	
		@Override
		public String format(LogRecord record) {
			// TODO Auto-generated method stub
			String time = TimeUtils.timeStamp2Date(String.valueOf(System.currentTimeMillis()),null);

			if (record.getMessage().contains(LoggerFormater.CASESTART)) {

				return  "\r\n"+"\r\n"+"\r\n"+"********"+record.getMessage()+"********"+"\r\n";
			} else  if (record.getMessage().contains(LoggerFormater.CASESEND)){
				return  "********"+record.getMessage()+"********"+"\r\n";
			} else{
				return "时间"+time+"\r\n"+
						"级别："+record.getLevel()+"\r\n"+
						"日志信息:" + record.getMessage()+"\r\n"+"\r\n"+"\r\n"; 	
			}
		}
	}


	/**
	 *返回的日志模式
	 * @author Administrator
	 *
	 */
	public interface LoggerFormater{
		public String CASESTART =" CASESTART";//测试用例开始
		public String CASESEND =" CASESEND";//测试用例开始
		public String STEPS ="STEPS";//测试用例开始

	}
	
	
	/**
	 * 开始测试用例
	 * @param testcase
	 */
	public static void startTest(String testcase,Logger logger){
		LoggerUtil.logOutput(logger, loggerLevel.LEVLEINFO, testcase+LoggerFormater.CASESTART);	
	}
	
	/**
	 * 开始测试用例
	 * @param testcase
	 */
	public static void endTest(String testcase,Logger logger){
		LoggerUtil.logOutput(logger, loggerLevel.LEVLEINFO, testcase+LoggerFormater.CASESEND );	
	}
	
	
	
}
