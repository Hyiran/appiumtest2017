package com.oribo.report;
/**
 * 该类用于生成HTML测试报告
 * @author 杨少佳
 * 2015-11-11
 */
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import com.oribo.dataprovider.AppBean;

//import java.util.logging.SimpleFormatter;

public class HTMLReport extends Formatter
{
	private int i = 0; // 记录步骤数目
	private long setStartTime; // log开始时间
	private long setEndTime; // log结束时间
	private static int p_pass = 0; // Pass用例的个数
	private static int p_fail = 0; // Fail用例的个数
	private static String result = ""; // case结果
	private static Object expected = ""; // 期待值
	private static Object instruct ="";//指令
	private static Object actual = ""; // 实际值
	private static Logger logger = Logger.getLogger(HTMLReport.class.getName());
	static private FileHandler fileHTML;
	static private Formatter formatterHTML;
	private static boolean endTest = false;

	static final String HTML_HEADER = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
			+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
			+ "<META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\">"
			+ "<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">"
			+ "<link rel=\"stylesheet\" href=\"demo_report_style.css\"/>"
			+ "<html><head><title>测试报告</title></head>"
			+ "<body>"
			+ "<div class=\"page_title\"><center>"
			+ "<h1>测试报告</h1></center></div>"
			+ "<div class=\"statistics\"><table id=\"statistics_table\" class=\"sortable\" align=\"center\" border=\"5\"  style=\"width:100%;\"><tr>"
			+ "<th><b>序号</b></th>"
			+ "<th><b>模块</b></th>"
			+ "<th><b>用例描述</b></th>"
			+ "<th><b>期待结果</b></th>"
			+ "<th><b>实际结果</b></th>"
			+ "<th><b>执行时间</b></th>" + "<th><b>状态</b></th>" + "</tr>";

	// HTML 文件格式
	public void setup(String p_logName) throws IOException
	{
		fileHTML = new FileHandler(p_logName);
		// Create HTML Formatter
		fileHTML.setEncoding("UTF-8");
		formatterHTML = new HTMLReport();	
		fileHTML.setFormatter(formatterHTML);
		logger.addHandler(fileHTML);
	}

	public void closeLog()
	{
		if(fileHTML==null)
		{
			return;
		}
			fileHTML.close();
			p_pass = 0; // Pass用例的个数
			p_fail = 0; // Fail用例的个数
			result = ""; // case结果
			expected = ""; // 期待值
			instruct= "";//指令
			actual = ""; // 实际值
		
	}

	public void logWriter(String p_info,Object p_instruct,Object p_expected, Object p_actual,
			String p_result  )
	{
		result = p_result;
		actual = p_actual;
		expected = p_expected;
		instruct =p_instruct ;
		try
		{
			logger.info(p_info);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(" logger write exception!");
		}

	}

	public void logWriter(String p_info, String p_result)
	{
		result = p_result;
		try
		{
			logger.info(p_info);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(" logger write exception!");
		}

	}

	public void logWriter(String p_info) throws IOException
	{

		logger.info(p_info);

	}

	public String format(LogRecord rec)
	{
		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING
		buf.append("<div class=\"statistics\">");
		buf.append("<tr>");
		buf.append("<td>");
		buf.append(recordStep());
		buf.append("</td>");
		buf.append("<td>");
		buf.append(instruct);
		buf.append("</td>");
		buf.append("<td>");
		// buf.append(calcDate(rec.getMillis()));
		// buf.append(' ');
		buf.append(formatMessage(rec));
		buf.append('\n');
		buf.append("</td>");		
		buf.append("<td>");
		buf.append(expected);
		buf.append("</td>");
		buf.append("<td>");
		buf.append(actual);
		buf.append("</td>");
		buf.append("<td>");
		buf.append(getCalcDate(rec.getMillis()));
		buf.append("</td>");
		buf.append("<td>");
		if (result.matches("Pass") || result.matches("PASS"))
		{
			p_pass = p_pass + 1;
			buf.append("<b>");
			buf.append("<font color=Green>");
			buf.append(result);
			buf.append("</font>");
			buf.append("</b>");
		} else if (result.matches("Fail") || result.matches("FAIL"))
		{
			p_fail = p_fail + 1;
			buf.append("<b>");
			buf.append("<font color=Red>");
			buf.append(result);
			buf.append("</font>");
			buf.append("</b>");

			/*
			 * //在report中加截图后，定义的html文件格式 buf.append("<tr>");
			 * buf.append("<td>"); buf.append("</td>"); buf.append("<td>");
			 * buf.append(
			 * "<a href=getScreenShotPath()><img src=getScreenShotPath() height=\"150\" /></a>"
			 * ); buf.append("</td>"); buf.append("<td>"); buf.append("</td>");
			 * buf.append("<td>"); buf.append("</td>"); buf.append("<td>");
			 * buf.append("</td>"); buf.append("<td>"); buf.append("</td>");
			 * buf.append("</tr>");
			 */
		} else
		{
			buf.append("<b>");
			// buf.append("<font color=Black>");
			buf.append("");
			buf.append("</b>");

		}
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</div>\n");
		try
		{
			Thread.sleep(10);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf.toString();
	}

	private int recordStep()
	{
		i = i + 1;
		return i;
	}

	private String getPercnet(double p_numerator, double p_denominator)
	{
		double percent = p_numerator / p_denominator;
		NumberFormat nt = NumberFormat.getPercentInstance();
		// 设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(1);
		return nt.format(percent);
	}

	private String getCalcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	private String getDeltaTime(long p_startTime, long p_endTime)
	{
		long day = (p_endTime - p_startTime) / (24 * 60 * 60 * 1000);
		long hour = ((p_endTime - p_startTime) / (60 * 60 * 1000) - day * 24);
		long min = (((p_endTime - p_startTime) / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = ((p_endTime - p_startTime) / 1000 - day * 24 * 60 * 60 - hour
				* 60 * 60 - min * 60);

		return day + "天" + hour + "小时" + min + "分" + s + "秒";
	}

	public String getHead(Handler h)

	{
		setStartTime = System.currentTimeMillis();
		return HTML_HEADER;
	}
	

	public String getTail(Handler h)
	{  
		String HTML_Tail;
		if (endTest) {
			return HTML_Tail="";
		}	
		setEndTime = System.currentTimeMillis();	
		int p_total = p_pass + p_fail;
		System.out.println(p_total);
		if (p_total > 0)
		{
			if (p_fail > 0)
			{
				// return HTML_Tail;
				HTML_Tail = "</table></PRE>" + "<br>&nbsp;开始时间   ："
						+ getCalcDate(setStartTime) + "<br>&nbsp;结束时间      ："
						+ getCalcDate(setEndTime) + "<br>&nbsp;运行时间      ："
						+ getDeltaTime(setEndTime, setStartTime)					
						+ "<br>&nbsp;执行用例      ：" + p_total
						+ "<br>&nbsp;用例成功         ：" + p_pass
						+ "<br>&nbsp;<font color=Red>用例失败      ：" + p_fail
						+ "</font>" + "<br>&nbsp;成功率(%) ："+ getPercnet(p_pass, p_total)
						+ "<br>&nbsp;<font color=Red>失败率(%) ："+ getPercnet(p_fail, p_total)
						+ "<br>&nbsp;运行手机信息：" +  AppBean.getAppBean().getPhone()+AppBean.getAppBean().getPlatformVersion()
						+ "<br>&nbsp;app 的版本信息：" + AppBean.getAppBean().getApk()
						 + "</font>" + "<br><br>"
						+ "</BODY></HTML>";
			} 
			else
			{
				HTML_Tail = "</table></PRE>" + "<br>&nbsp;开始时间   ："
						+ getCalcDate(setStartTime) + "<br>&nbsp;结束时间   ："
						+ getCalcDate(setEndTime) + "<br>&nbsp;运行时间   ："
						+ getDeltaTime(setEndTime, setStartTime)
						+ "<br>&nbsp;执行用例      ：" + p_total
						+ "<br>&nbsp;用例成功      ：" + p_pass
						+ "<br>&nbsp;用例失败      ：" + p_fail
						+ "<br>&nbsp;成功率(%) ：" + getPercnet(p_pass, p_total)
						+ "<br>&nbsp;失败率(%) ：" + getPercnet(p_fail, p_total)
						+ "<br>&nbsp;运行手机信息：" +   AppBean.getAppBean().getPhone()+AppBean.getAppBean().getPlatformVersion()
						+ "<br>&nbsp;app 的版本信息：" + AppBean.getAppBean().getApk()
						+ "<br><br>" + "</BODY></HTML>";
			}
		} 
		else
		{
			HTML_Tail = "</table></PRE>" + "<br>&nbsp;用例执行异常！" + "<br><br>"
					+ "</BODY></HTML>";
		}
		return HTML_Tail;
	}
}
