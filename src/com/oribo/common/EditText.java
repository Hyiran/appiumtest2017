package com.oribo.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.oribo.ReadExcelCase.writeExcel;
import com.oribo.android365.testcase.Personcenter;
import com.oribo.log.Log;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 编辑框的常用操作，如输入汉字、英文、特殊字符、边界值等
 * @author test1
 *
 */
public class EditText {
	 AndroidElement edittext;
	
	//16个汉字
	private  final String MAXCHINA="我是中国人我爱我的祖国我们是一家";
	//32个英文字符
	private  final String MAXENG="qwertyuiopasdfghjklzxcvbnmqwerty";
	private  final String ONECHAR="a";
	//超过32个英文字符
	private  final String GREMAXENG="qwertyuiopasdfghjklzxcvbnmqwertya";
	//11位已绑定的手机号
	private final String PHONE="13088884762";
	//大于11位手机号
	private String bigphonenumber;
	//不是手机号码的11位数字
	private final String NOTPHONE="56789012345";
	//空格
	private final String NULL="  ";
	//已绑定的邮箱
	private final String EMAIL="15079034630@126.com";
	//特殊字符
	private final String SPECIL1="++++++++.-(";
	String strfive=null;
	String fivechar=null;
	
	public EditText(AndroidElement edittext)
	{
		this.edittext=edittext;
	}
	
   
	/**
	 * 输入旧密码
	 */
	
	public void inputoldpassword(AndroidDriver driver)
	{  
		String old=Personcenter.getaccount().getPassWord();
		AppOperate.sendKeys(edittext, "输入旧密码",old );
		Log.logInfo("旧密码为:"+old);
		AppOperate.submit(edittext, "确认输入",driver);
		Assert.assertEquals(edittext.getText().trim(),old);
	}
	/**
	 * 输入登录的账号
	 */
	public void inputaccount(AndroidDriver driver)
	{   
		String account=Personcenter.getaccount().getAccount();
		AppOperate.sendKeys(edittext, "输入正确的账号",account);
		Log.logInfo("登录的帐号为:"+account);
		Assert.assertEquals(edittext.getText().trim(),Personcenter.getaccount().getAccount());
	}
	
	
	/**
	 * 输入16个汉字
	 */
	public  void inputmaxchina(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入16个字汉", MAXCHINA);
		Log.logInfo("输入的汉字为:"+MAXCHINA);
		Assert.assertEquals(edittext.getText().trim(), MAXCHINA);
		
	}
	/**
	 * 输入空格
	 * @return
	 */
	public void inputnull(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入空格", NULL);
		AppOperate.submit(edittext, "确认输入",driver);
	}
	
	/**
	 * 输入11位手机号,此手机号为常用手机号（13088884762）
	 * @return
	 */
	public void inputphonenumber(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入11位手机号", PHONE);
		Log.logInfo("输入的手机号为:"+PHONE);
		Assert.assertEquals(edittext.getText().trim(), PHONE);
		
	}

	
	/**
	 * 输入11位随机手机号
	 * @return
	 */
	public void inputphonenumber2(AndroidDriver driver)
	{   
		String phone=ToolFunctions.getPhoneNum(8);
		AppOperate.sendKeys(edittext, "输入11位手机号", phone);
		Log.logInfo("输入的随机手机号为："+phone);
		Assert.assertEquals(edittext.getText().trim(), phone);
		
	}
	/**
	 *随机输入邮箱
	 */
	public void inputemail(AndroidDriver driver)
	{   
		String email=ToolFunctions.getEmail();
		AppOperate.sendKeys(edittext, "输入邮箱", email);
		Log.logInfo("输入的随机邮箱为:"+email);
		Assert.assertEquals(edittext.getText().trim(), email);
		
	}
	
	
	/**
	 * 输入邮箱,此邮箱为常用邮箱15079034630@126.com，且已绑定
	 */
	public void inputemailband(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入邮箱", EMAIL);
		Log.logInfo("输入的随机邮箱为:"+EMAIL);
		Assert.assertEquals(edittext.getText().trim(), EMAIL);
		
	}
	/**
	 * 获取输入的邮箱（此邮箱已被绑定）
	 */
	public String getemail(AndroidDriver driver)
	{
		return EMAIL;
	}
	/**
	 * 随机输入小于11位的手机号
	 * @return
	 */
	public void inputlesphonenumber(AndroidDriver driver)
	{   
		String miniphone=ToolFunctions.getPhoneNum(7);
		AppOperate.sendKeys(edittext, "输入小11位手机号", miniphone);
		Log.logInfo("输入的小于11位手机号为:"+miniphone);
		Assert.assertEquals(edittext.getText().trim(), miniphone);
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public void changepassword(AndroidDriver driver)
	{   
		String str=ToolFunctions.getRandomstring(6);
		AppOperate.sendKeys(edittext, "输入新密码", str);
		Log.logInfo("新密码为:"+str);
		Assert.assertEquals(edittext.getText().trim(), str);
		Personcenter.getaccount().setPassWord(str);
		Map<String,String> map=new HashMap<>();
    	map.put("1", Personcenter.getaccount().getAccount());
    	map.put("2", str);
    	writeExcel.writeexcel(map,System.getProperty("user.dir")+File.separator+"testdata\\personInfo_TestData.xls",1);
    	
	}
	
	/**
	 * 随机输入大于11位手机号
	 */
	public void inputgrephonenumber(AndroidDriver driver)
	{   
		bigphonenumber=ToolFunctions.getPhoneNum(9);
		AppOperate.sendKeys(edittext, "输入大于11位手机号", bigphonenumber);
		AppOperate.submit(edittext, "确认输入",driver);

	}
	
	/**
	 * 输入特殊字符$%^##
	 * @return
	 */
	public void inputSpecialchar(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入特殊字符", SPECIL1);
		AppOperate.submit(edittext, "确认输入",driver);
		Assert.assertEquals(edittext.getText().trim(), SPECIL1);
		
	}
	
	/**
	 * 输入5位中英文字符
	 * @param driver
	 */
	public void inputSpecialchar2(AndroidDriver driver)
	{   
		strfive=ToolFunctions.getRandomstring(5);
		AppOperate.sendKeys(edittext, "输入特殊字符", strfive);
		AppOperate.submit(edittext, "确认输入",driver);		
	}
	
	public String getSpecialchar2()
	{
		return strfive;
	}
	/**
	 * 输入非手机号码数字
	 * @return
	 */
   public void notphone(AndroidDriver driver)
   {
		AppOperate.sendKeys(edittext, "输入特殊字符", NOTPHONE);
		AppOperate.submit(edittext, "确认输入",driver);
		Assert.assertEquals(edittext.getText().trim(), NOTPHONE);
	   
   }
	public String getmaxchina()
	{
		return MAXCHINA;
	}
	
	public String getphonenumber()
	{
		return PHONE;
	}
	
	
	/**
	 * 输入32个英文
	 */
	public void inputmaxeng(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入32个英文",MAXENG);
		AppOperate.submit(edittext, "确认输入",driver);
		Assert.assertEquals(edittext.getText().trim(), MAXENG);
	}
	public String getmaxeng()
	{
		return MAXENG;
	}
	
	/**
	 * 输入超过32个字符能否再输入
	 */
	public boolean inputgremax(AndroidDriver driver)
	{  
		edittext.sendKeys(GREMAXENG);
		AppOperate.submit(edittext, "确认输入",driver);
		if(edittext.getText().length()>32)
			return false;
		else 
			return true;
	
	}
	
	/**
	 * 输入一个字符
	 * @return
	 */
	public void inputonechar(AndroidDriver driver)
	{
		AppOperate.sendKeys(edittext, "输入1个字符",ONECHAR);
		AppOperate.submit(edittext, "确认输入",driver);
	}
	
	/**
	 * 输入5个英文
	 * @return
	 */
	public void inputfivechar(AndroidDriver driver)
	{   
		fivechar=ToolFunctions.getRandomstring(5);
		AppOperate.sendKeys(edittext, "输入5个字符",fivechar);
		AppOperate.submit(edittext, "确认输入",driver);
		Assert.assertTrue(edittext.getText().equals(fivechar));
	}
	/**
	 * 输入随机的手机号
	 */
	public void inputrandomphone(AndroidDriver driver)
	{
		String phone=ToolFunctions.getPhoneNum(8);
		AppOperate.sendKeys(edittext, "输入11位随机手机号",phone);
		AppOperate.submit(edittext, "回车",driver);
		Assert.assertTrue(edittext.getText().equals(phone));
	}
	/**
	 * 返回输入的5个英文
	 */
	public String getfivechar()
	{
		return fivechar;
	}
	/**
	 * 返回单个字符的内容
	 * @return
	 */
	public String getonechar()
	{
		return ONECHAR;
	}
	
	/**
	 * 获取文本框的内容
	 */
	public String gettext()
	{
		return edittext.getText().trim();
	}
	
	/**
	 * 清空文本框
	 */
	public void clear()
	{
		AppOperate.clear(edittext, "清空文本框");

	}
	


}
