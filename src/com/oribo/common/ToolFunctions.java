package com.oribo.common;

/**
 * 一些功能函数，如生成随机字符串、获取APP版本号、是否安装某个APP、获取当前时间等
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.oribo.log.Log;

import io.appium.java_client.android.AndroidDriver;

public class ToolFunctions {
	// 创建随机字符串
	public static String getRandomstring(int length)
	{
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();  
	}
	
   /**
    * 获取APP的版本号
    */
	public static String getAppversion(String packagename)
	{   
		String version=null,str;
	
		try {
			Process process=Runtime.getRuntime().exec("adb shell dumpsys package "+packagename+" |grep versionName");
			BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
			while((str=br.readLine())!=null)
			{   
				if(getMatch(str, "versionName"))
				{   
					version=str.split("=")[1].trim();
					break;
				}

			}
			br.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	
	
	/**
	 * 随机生成手机号
	 * @return
	 * @throws Exception
	 */
	
	// 获取当前时间信息
	public static String getCurrentTime() throws Exception
	{

		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// 获取年份
		int month = ca.get(Calendar.MONTH);// 获取月份
		int day = ca.get(Calendar.DATE);//  获取日
		int minute = ca.get(Calendar.MINUTE);// 分
		int hour = ca.get(Calendar.HOUR);// 小时
		int second = ca.get(Calendar.SECOND);// 秒
		return (String.valueOf(year) + "-" + String.valueOf(month + 1) + "-"
				+ String.valueOf(day) + "-" + String.valueOf(hour) + "-"
				+ String.valueOf(minute) + "-" + String.valueOf(second));

	}
	
	/**
	 * 查看是否安装某三方应用
	 * @return
	 */
	public static boolean ifinstallthirdapp(String packagename)
	{   
		boolean flag=false;
		try {
			InputStream is=Runtime.getRuntime().exec("adb shell pm list packages -3|grep "+packagename).getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String str;
			if((str=br.readLine())!=null)
			{   
				System.out.println(str);
				flag=true;
			}
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	
	
	
	//获取当前日期
	public static int getCurrentDate()
	{
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.DATE);
	}
	
	/**
	 * 判断两个字符串是否相等
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String isStringEquals(String s1,String s2)
	{
		if(s1!=null && s2!=null)
		{
			if(s1.equals(s2))
			{
				return "通过";
			}else
			{
				return "不通过";
			}
		}else
		{
			return "不通过";
		}
	}
	
	
    private static String[] OperatorCode = {"134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "130", "131", "132", "155", "156", "185", "186", "133", "153", "180", "189"};
    private static String areaCodes[] = {"320102", "320103", "320104", "320105", "320106", "320107", "320111", "320113", "320114", "320115", "320116", "320124", "320125", "320202", "320203", "320204", "320205", "320206", "320211", "320281", "320282",
            "320302", "320303", "320304", "320305", "320311", "320321", "320322", "320323", "320324", "320381", "320382", "320402", "320404", "320405", "320411", "320412", "320481", "320482", "320502", "320503", "320504",
            "320505", "320506", "320507", "320581", "320582", "320583", "320584", "320585", "320602", "320611", "320612", "320621", "320623", "320681", "320682", "320684", "320703", "320705", "320706", "320721", "320722",
            "320723", "320724", "320802", "320803", "320804", "320811", "320826", "320829", "320830", "320831", "320902", "320903", "320921", "320922", "320923", "320924", "320925", "320981", "320982", "321002",
            "321003", "321011", "321023", "321081", "321084", "321088", "321088", "321102", "321111", "321112", "321181", "321182", "321183", "321202", "321203", "321281", "321282", "321283", "321284", "321302",
            "321311", "321322", "321323", "321324"};

    /**
     * 在目标字符串数组中查找匹配的字符串
     *
     * @param find   被查找的字符串
     * @param target 目标字符串数组
     * @return 是否查找到字符串
     * @author quqing
     */
    public static boolean isMatch(String find, String[] target) {
        for (String str : target) {
            if (find.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static String formatTime() {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHH");
        return time.format(new Date()).toString();
    }

/**
 * 查找子字符串
 * @param source
 * @param rex
 * @return
 */
    
	 public static boolean getMatch(String source, String rex) {
	        Pattern pattern = Pattern.compile(rex);
	        Matcher matcher = pattern.matcher(source);
	        return matcher.find();

	    }
    
	 /**
	  * 切换至H5
	  */
	 public static String h5context(AndroidDriver driver)
	 {
			//判断是否有 WEBVIEW
		 Set contextNames = driver.getContextHandles();
		 Iterator it=contextNames.iterator();
		 String context=null;
		 while(it.hasNext())
		 {
			 String str=(String) it.next();
			 if(str.contains("WEBVIEW"))
			 {   
				 
				 context=str; 
			 }
		 }

		 return context;
	 }
	 
	 /**
	  * 切换至本地应用
	  */
	 public static String nativecontext(AndroidDriver driver)
	 {
		 Set contextNames = driver.getContextHandles();
		 Iterator it=contextNames.iterator();
		 String context=null;
		 while(it.hasNext())
		 {
			 String str=(String) it.next();
			 if(str.contains("NATIVE"))
			 {   
				 System.out.println(str);
				 context=str; 
			 }
		 }

		 return context;
		 
	 }
    
	/**
	 * 查看adb输出流中是否包含某个字符串
	 */
	public static boolean cmdmessage(String command,String findmessage)
	{     
		String str;
		boolean flag=false;
		try {
			Process process=Runtime.getRuntime().exec(command);
			BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
			while((str=br.readLine())!=null)
			{   
				if(getMatch(str, findmessage))
				{   
					flag=true;
					break;
				}

			}
			br.close();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
		
	}
	/**
	 * udid转mac，toMac(uid.replaceAll("202020202020", ""))
	 * @param args
	 */
	public static String toMac(String mac) {
        return mac.substring(0, 2).toUpperCase() + ":" 
	+ mac.substring(2, 4).toUpperCase() + ":" + mac.substring(4, 6).toUpperCase() + ":" + 
        		mac.substring(6, 8).toUpperCase() + ":" + mac.substring(8, 10).toUpperCase() + ":" + mac.substring(10, 12).toUpperCase();
    }
	



    /**
     * 杀掉指定名称的进程
     *
     * @param processName 进程名
     * @author quqing
     */
    public static void killProcess(String processName) {
        try {
            String cmd;

            if (isWindows()) {
                cmd = "Taskkill /IM" + processName;
            } else {
                cmd = "killall \"" + processName + "\"";
            }
            cmdInvoke(cmd);
        } catch (Exception e) {
            Log.logInfo(e.getMessage());
        }
    }

    /**
     * 整块读取文件内容
     *
     * @param fname 文件名
     * @return
     * @throws FileNotFoundException
     * @author quqing
     */
    public static String readAll(String fname) throws FileNotFoundException,
            IOException {
        String content = "";
        File file = new File(fname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        reader.close();
        return content;
    }

    /**
     * 判断是否Windows操作系统
     *
     * @return boolean
     * @author quqing
     */
    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return (os.toLowerCase().startsWith("win")) ? true : false;
    }

    /**
     * 调用并执行控制台命令
     *
     * @param cmd 控制台命令
     * @return String
     * @author quqing
     */
    public static String cmdInvoke(String cmd) {
        String cmdOut = "";
        BufferedReader br = null;
        String filePath = "src/log4j.properties";

        try {
            Logger logger = Logger.getLogger("appium_log");
            PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
//			String[] command = { "/bin/sh", "-c", cmd };
            String[] command = {"sh", "-c", cmd};
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                cmdOut = line;
                logger.info(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return cmdOut;
    }

    /**
     * 生成手机号码,手机号码的规则是：运营商编码（3位）+地区编码（4位）+用户编码（4位）
     *
     * @param count 要获取的手机号码个数
     * @return String
     */
    public static String getPhoneNum(int count) {
        String PhoneNum = "";
        int num = (int) (Math.random() * OperatorCode.length);
        PhoneNum = PhoneNum + OperatorCode[num];

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            PhoneNum += random.nextInt(10);
        }
        return PhoneNum;
    }
    
    public static String[] emailtype={"@163.com","@126.com","@sina.com","@qq.com","@sohu.com"};
    public static String[] emailprefix={"1234567","abcdefg","123abc_^1","432abcdd"};
    
    /**
     * 随机生成邮箱 
     */
    public static String getEmail()
    {   
    	String email="";
    	email=emailprefix[new Random().nextInt(emailprefix.length)];
    	email=email+emailtype[new Random().nextInt(emailtype.length)];
    	return email;
    }

    /**
     * 生成身份证号
     *
     * @return String
     */
    public static String getIdentifyCode() {
        //市/区/县的名称与之对应的编号，目前仅支持江苏省
        Random random = new Random();
        String areaCode = areaCodes[random.nextInt(areaCodes.length)];
        int year = 1920 + random.nextInt(100);
        int month = random.nextInt(11);
        if (month == 0)
            month = 12;
        int day = 0;
        while (true) {
            day = random.nextInt(31);
            if (!((day == 0 || (month == 4 || month == 6 || month == 9 || month == 11)
                    && day > 30) || (month == 2 && (((year) % 4 > 0 && day > 28) || day > 29)))) {
                break;
            }
        }
        String birthday = String.valueOf(year * 10000 + month * 100
                + day);
        String randomCode = String.valueOf(1000 + random.nextInt(999))
                .substring(1);
        String verify = getVerify(areaCode + birthday + randomCode);
        String ret = areaCode + birthday + randomCode + verify;
        return ret;
    }

    /**
     * 计算身份证校验码
     *
     * @param cardId 身份证前17位
     * @return String
     */
    public static String getVerify(String cardId) {
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(cardId.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        return strVerifyCode;
    }

    /**
     * 输入1个字符串和1个长度，如果字符串长度小于输入长度,字符串前面补0
     *
     * @param str 字符串
     * @param len 长度
     * @return String
     */
    private static String valueOfString(String str, int len) {
        String string = "";
        for (int i = 0; i < len - str.length(); i++) {
            string = string + "0";
        }
        return (str.length() == len) ? (str) : (string + str);
    }

    /**
     * 返回当前时间，格式为：2014-12-18 15:11:50
     *
     * @return String
     */
    public static String getSimpleDateFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 返回当前时间戳
     *
     * @return String
     */
    public static String getTime() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * 生成一个长度为17的时间字符串，精确到毫秒
     *
     * @return String
     */
    public static String getTimeString() {
        Calendar calendar = new GregorianCalendar();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2);
        String day = valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2);
        String hour = valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2);
        String minute = valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)), 2);
        String second = valueOfString(String.valueOf(calendar.get(Calendar.SECOND)), 2);
        String millisecond = valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)), 3);
        return year + month + day + hour + minute + second + millisecond;
    }

    /**
     * 得到昨天的日期
     *
     * @return String
     */
    public static String getYestoryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }
	
    /**
     * 过滤字符串
     * @param args
     */

    /**
     * 设置手机休眠时间
     * @param args
     */
    public static void setdevsleeptime(String time)
    {
    	try {
    	    Log.logInfo("设置屏幕休眠时间");
			Runtime.getRuntime().exec("adb shell settings put system screen_off_timeout "+time);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
     * 切换输入法为非APPIUM输入法
     * @param args
     */
    public static void setinputmethod()
    {   
    	String str;
    	List<String> inputmethod=new ArrayList<>();
    	//列出当前所有非appium输入法
    	try {
			Process process=Runtime.getRuntime().exec("adb shell ime list -s");
			BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
			while((str=br.readLine())!=null)
			{   
				
				if(!str.contains("appium")&&str.contains("input"))
				{   
					System.out.println(str);
					inputmethod.add(str.trim());
				}

			}
			br.close();
			//设置输入法
			System.out.println("要设置的输入法为:"+inputmethod.get(0));
			Runtime.getRuntime().exec("adb shell settings put secure default_input_method "+inputmethod.get(0));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 杀掉Uiautomator进程
     * @param args
     */
    public static void killuiautomator()
    {    
    	Log.logInfo("杀掉uiautomator进程");
    	Process process;
    	String str,kill = null;
		try {
			process = Runtime.getRuntime().exec("adb shell ps |grep \"uiautomator\"");
			BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
		
			if((str=br.readLine())!=null)
			{   
				
				kill=str.split(" +")[1];
				System.out.println("进程名为："+kill);

			}
			br.close();
			 Runtime.getRuntime().exec("adb shell kill "+kill);
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
    
	public static void main(String[] args)
	{   
		setinputmethod();

	}
	

}
