package com.oribo.dataprovider;

import java.io.File;
import java.util.concurrent.TimeUnit;
import com.oribo.dataprovider.AppBean;


/**
 * app  的常量值存放
 * @author cindy
 *
 */
public class Constant {
	/****界面元素的Id  start********/
	//main  主界面
	public static final String BOTTOMBTN_PERSONCENT ="com.orvibo.homemate:id/userInfoLayout";

	/****界面元素的Id  end ********/


	//public static String SAVEPATH = AppBean.getAppBean().getApk()+"_"+AppBean.getAppBean().getPhone();
	//测试端的名字
	public static final int  INPUT_MAX = 16;
	

	//日志存在存在的文件夹中
	public static final String LOGGERFILEPATH = "Log" + File.separator + "loggingResults"+File.separator;
	public static final String EVENTLOG="Log"+File.separator;
	public static final String ANRLOG="Log"+File.separator+"AnrLog"+File.separator;
	public static final String SCREENCAP="Log"+File.separator+"ScreenCap"+File.separator;
	public static final String TESTDATA = "testdata"+File.separator;
	public static final String TAG ="********";
	public static final String STARTTAG ="********";
	public static final String ENDTAG ="********";
	public static final String ERROR = "error--------";
    
	//内网服务器位置
	public static final String INTERNALURL="jdbc:mysql://192.168.2.20/vihome_cloud";
	//数据库账号和密码
	public static final String DATABASEACCOUNTINTER="root";
	public static final String DATABASEACCOUNTPASSWORD="orvibo888";
	
	/*//外网服务器地址
	public static final String INTERNALURL="jdbc:mysql://5592482b7cb3f.gz.cdb.myqcloud.com/vihome_cloud";
	public static final String DATABASEACCOUNTINTER="root";
	public static final String DATABASEACCOUNTPASSWORD="testDBewq#@!";*/
	/**
	 * 记录待测试App的路径,名称,URL端口等
	 */
	public static String AppPath = "C:\\Users\\CYsuncheng\\Downloads";
	public static String AppName = "ffan.apk";
	public static String SessionURL = "http://127.0.0.1:4723/wd/hub";

	/**
	 * 记录截图存放路径
	 */
	public static String ScreenShotPath = "G:\\Apptest\\test-output\\Screenshot\\";

	/**
	 * 记录数据存储的路径
	 */
	public static String XMLDataPath = "G:\\Apptest\\data\\TestData.xml";
	public static String ExcelDataPath = "G:\\Apptest\\data\\TestCaseData.xls";
	
	
	//
	public static final String CLASSNAME_EDITTEST ="android.widget.EditText";

	/**
	 * 登录页面用户名和密码resourceid
	 */
	public static String UN = "com.wanda.app.wanhui:id/edit_login_name";
	public static String PW = "com.wanda.app.wanhui:id/widget_input";
	//mainactivitytag
	public static final String MAINTAG_PERSIONCENTER = "个人中心";

	//邮注册页面的字符
	public static  final  String RIGSTRERBYEAM = "邮箱注册";
	public static  final  String RIGSTRFILE = "register_byemail_logger";
	public static  final  String  CONTINNE= "继续注册";
	public static final String REGISTERBTN = "立即注册";
	public static  final  String  RETURN_MODIFY = "返回修改";
	public static  final  String  USEEMALI_TIP="此帐号已注册，您可以直接登录";
	public static final  String REGISTER_EERRORID ="com.orvibo.homemate:id/tip1ErrorTextView";//用已经注册邮箱注册的提示语


	//登陆页面
	
	public static  final  String LOGIN = "登录";

	//个人中心
	public static  final  String PENGHONG = "penghong";
	public static  final  String NICKNAMEFILE = "nickname_logger";
	public static  final  String BINDACCOUNTFILE = "bindaccount_logger";
	public static  final  String AUTHORIZED = "authorized_logger";
	public static  final  String MYHOST = "myhost_logger";
	public static  final  String LOGINFILE = "login_logger";
	public static  final  String NICKNAME_SAVE= "保存";
	public static  final  String LOGOUT= "退出登录";
	public static  final  String ACCOUNTCHECK= "accoutshow_logger";
	public static  final  String CHANGEPW= "changePw_logger";
	public static  final  String FORGETPW= "forgetPw_logger";
	public static  final  String NEXT= "下一步";

	//服务器接口地址
	public static final String HTTPURl="  https://homemate.orvibo.com/getAdvInfo";
	
	
	//字符串输入的位数限制
	public static int CHINESE_NUMBER = 16;//汉字16位
	public static int ENGlISH_NUMBER = 32;//英文32位
	
	
	//字符串输入的位数限制
	public static String CASETYPE_ANDROID= "Android";//汉字16位
	public static String CASETYPE_IOS = "IOS";//英文32位
	
	//登录类型
	public static  int LOGING_TYPE_EMAIL = 1;
	public static  int LOGING_TYPE_PHONE =2;
	
}
