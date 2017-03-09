package com.oribo.android365.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.oribo.ReadExcelCase.DataBean;
import com.oribo.ReadExcelCase.ReadExcel;
import com.oribo.common.AppOperate;
import com.oribo.common.EditText;
import com.oribo.common.TestcaseFrame;
import com.oribo.common.ToolFunctions;
import com.oribo.database.DBHelperMysql;
import com.oribo.database.Query;
import com.oribo.dataprovider.AppBean;
import com.oribo.dataprovider.Constant;
import com.oribo.log.Log;
import com.oribo.report.TestResultListener;
import com.oribo.utils.FileOperate;
import com.oribo.utils.compareimage;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
@Listeners({TestResultListener.class})
public class HomeManage extends TestcaseFrame{
	AndroidDriver<AndroidElement> driver;
	AppBean  appbean = AppBean.getAppBean();
	
	
	String mac=null;
	@BeforeClass(alwaysRun=true)
	@Parameters({ "port","udid","phone","platformVersion", "apk","testaccount","testpassword","reportreceiver"})
	public void init(String port, String udid,String phone,String platformVersion ,String apk,String testaccount,String testpassword,String reportreceiver )
	{
		//保存app的基础信息		
				appbean.setUid(udid);
				appbean.setPort( port);
				appbean.setPhone(phone);
				appbean.setApk(apk);
				appbean.setPlatformVersion(platformVersion);
		
	}
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod()
	{   
		super.testSetUp();
		driver=super.getDriver();
		logging();

	}
	
	/**
	 * 个人中心检查我的主机
	 */
	@Test(groups={"个人中心","我的主机00"})
	public  void CheckMyHub() {

		if(ifhashub())
		{
			Log.logInfo("有主机");
    		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
    		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/myHost")).isDisplayed());
			
		}
	
	
	}			
	       

	
	/**
	 * 
	 * 更多页面检查删除主机
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void ClickMore() {
		DataBean  data = bean.get(5);	
		enterMyhost();
		AppOperate.swipeToUp(driver, "向上滑屏");
		AndroidElement  	MoreName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/moreTextView");//主机设备信息“更多”
		Assert.assertNotNull(MoreName, "找不到“更多”");
        AppOperate.click(MoreName, "点击更多");
		Assert.assertNotNull(driver.findElementByAndroidUIAutomator("text(\"删除主机\")"),"点击‘更多’跳转失败");
			
	

	}
	
	/**
	 * 
	 *检查删除主机弹窗
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CheckDeleteHost() {
		DataBean  data = bean.get(6);
		enterMore();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/deleteTextView");//更多页面检查删除主机
	    AppOperate.click(HostName, "点击‘删除主机’");
	    AndroidElement  	noticeText = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/contentTextView");//点击删除主机弹窗提示文案
	    Assert.assertNotNull(noticeText, "点击删除主机未弹出提示框");
	    Assert.assertEquals(noticeText.getText(), "主机和所有设备均将被删除，需要重新添加后才能使用，确实要删除吗？");
	        

	}
	
	/**
	 * 
	 * 取消删除主机弹窗
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  CancelDeleteHostNotice() {
		enterMore();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/deleteTextView");//更多页面检查删除主机
	    AppOperate.click(HostName, "点击‘删除主机’");
	    AndroidElement  	noticeText = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/contentTextView");//点击删除主机弹窗提示文案
	    Assert.assertNotNull(noticeText, "点击删除主机未弹出提示框");
	    Assert.assertEquals(noticeText.getText(), "主机和所有设备均将被删除，需要重新添加后才能使用，确实要删除吗？");
		AndroidElement  	CancelButton = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/rightButton");//点击删除主机弹窗提示文案
        AppOperate.click(CancelButton, "点击‘取消’");
        Assert.assertFalse(CancelButton.isDisplayed(), "点击弹窗中的‘取消’按扭，弹窗并未消失");
		

	}
	
	/**
	 * 
	 * 删除主机
	 */
	@Test(dependsOnMethods={"CheckMyHub"},groups={"个人中心","我的主机"})
	private void  DeleteHost() {
		enterMore();
		AndroidElement  	HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/deleteTextView");//更多页面检查删除主机
	    AppOperate.click(HostName, "点击‘删除主机’");
	    AndroidElement  	noticeText = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/contentTextView");//点击删除主机弹窗提示文案
	    Assert.assertNotNull(noticeText, "点击删除主机未弹出提示框");
	    Assert.assertEquals(noticeText.getText(), "主机和所有设备均将被删除，需要重新添加后才能使用，确实要删除吗？");
		AndroidElement  	DeleteButton = (AndroidElement) driver.findElementByAndroidUIAutomator("text(\"删除\")");//删除按钮		
		AppOperate.click(DeleteButton, "确认删除");
		AppOperate.waitForTextDisappear(10, "努力奔跑中，快到终点喽~",driver);
		Assert.assertFalse(driver.getPageSource().contains("努力奔跑中，快到终点喽~"),"等待10S后删除主机失败");
		Assert.assertFalse(HostName.isDisplayed(),"删除主机失败");
			



	}
	/**
	 * 设备信息检查主机Mac地址
	 * 设备信息检查主机ip
	 * 设备信息检查主机固件版本
	 * 设备信息检查主机版本后返回
	 */
	
	@Test(dependsOnMethods={"CheckMyHub"},groups={"我的主机0"})
	public void myhost()
	{   
		String sql=null;
		//读取主机的model id
		String modelid=ReadExcel.readsimpledata(1, 1, 2);
		String hostip=null,hostmac=null,hostfireware=null,hostname=null;
		//获取主机的主机名称、mac地址、IP地址、固件版本
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		   {
			 sql="select account2.email,gateway.model,gateway.homeName,gateway.uid,gateway.softwareVersion,gateway.localStaticIP "
			 		+ "from account2,gateway,userGatewayBind "
			 		+ "where account2.email='"+TestcaseFrame.getaccount().getAccount()+
			 		"' and account2.userId=userGatewayBind.userId and userGatewayBind.uid=gateway.uid and gateway.model='"+modelid+"'";
		   }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql="select account2.email,gateway.model,gateway.homeName,gateway.uid,gateway.softwareVersion,gateway.localStaticIP "
				 		+ "from account2,gateway,userGatewayBind "
				 		+ "where account2.phone='"+TestcaseFrame.getaccount().getAccount()+
				 		"' and account2.userId=userGatewayBind.userId and userGatewayBind.uid=gateway.uid and gateway.model='"+modelid+"'";
		 }
		 System.out.println(sql);
		 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) {
	        	 hostname=ret.getString(3);
	        	 mac=ret.getString(4);
	        	 hostmac=ToolFunctions.toMac(ret.getString(4).replaceAll("202020202020", ""));
	        	 hostfireware=ret.getString(5);
	        	 hostip=ret.getString(6);
	        	 Log.logInfo("主机的名称为:"+hostname+",主机的MAC地址为："+hostmac+"，主机的固件版本为："+hostfireware+",主机的IP地址为："+hostip);
	        	//进入我的主机检查相应信息是否正确 
	  		     enterMyhost();
	  		     Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/hubNameTv")).getText().equals(hostname));
	  		     Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/info1TextView")).getText().equals(hostmac));
	  		     Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/info2TextView")).getText().equals(hostip));
	  		     //上滑
	  		     AppOperate.swipeToUp(driver, "向上滑屏");
	  		     Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/info3TextView")).getText().indexOf(hostfireware)!=-1);
	           
	           }//显示数据 
	         
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		   AndroidElement  HostName = (AndroidElement) driver.findElementById("com.orvibo.homemate:id/left_iv");//返回按扭
		   Assert.assertTrue(HostName.isDisplayed(), "未找到返回按钮");
		   AppOperate.click(HostName, "点击返回按扭");;//返回我的页面
		   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"我的\")").isDisplayed());
		 
	}

	
	/**
	 * 有WIFI设备时查找节能提醒中是否有设置开关
	 * 无WIFI设备时查找节能提醒中是否有设置开关
	 * 有灯时查找节能提醒中是否有节能提醒设置开关
	 * 无灯时查找节能提醒中是否有节能提醒设置开关
	 * 定时执行提醒中默认开关
	 * 节能提醒中默认开关
	 * 节能提醒中设备数的显示
	 * WIFI和zigbee设备都为空时提醒设置界面显示
	 */
	@Test(groups={"提醒设置"})
	public void remindersettings()
	{
		//获取当前家庭
		String defaultname=getdefaultfamily();
		//判断当前家庭有无WIFI设备、小方设备,判断默认状态 ，默认为开启
		
		//判断当前家庭有无ZigBee开关，判断默认状态 ，默认为开启
		//判断当前WIFI或小方设备数，全部应展示在定时执行提醒中
		//判断当前ZigBee开关数,全部应展示在节能提醒中
		
		 String sqlzigbee=null,sqlwifi=null;
         int countwifi=0,countzigbee=0;
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sqlwifi="select  distinct userGatewayBind.uid from userGatewayBind,account2 , device "
			 		+ "where  account2.userId=userGatewayBind.userId and "
			 		+ "account2.email='"+TestcaseFrame.getaccount().getAccount()
			 		+"'  and account2.delFlag=0 and "
			 		+ " userGatewayBind.delFlag=0 and userGatewayBind.uid=device.gatewayUID and device.delFlag=0 and device.endpoint=-1";
			 
			 sqlzigbee="select device.deviceName from account2,userGatewayBind,device  "
				 		+ "where account2.userId=userGatewayBind.userId and "
				 		+ "account2.email='"+TestcaseFrame.getaccount().getAccount()
				 		+"' and account2.delFlag=0 "
				 		+ "and userGatewayBind.delFlag=0 and userGatewayBind.uid=device.gatewayUID and "
				 		+ "device.delFlag=0  and (device.deviceType=0 or device.deviceType=1 or device.deviceType=38 or device.deviceType=19 or device.deviceType=82)";
			 
			 
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sqlwifi="select  distinct userGatewayBind.uid from userGatewayBind,account2 , device "
				 		+ "where  account2.userId=userGatewayBind.userId and "
				 		+ "account2.phone='"+TestcaseFrame.getaccount().getAccount()
				 		+"'  and account2.delFlag=0 and "
				 		+ " userGatewayBind.delFlag=0 and userGatewayBind.uid=device.gatewayUID and device.delFlag=0 and device.endpoint=-1";
			 
			 sqlzigbee="select device.deviceName from account2,userGatewayBind,device  "
			 		+ "where account2.userId=userGatewayBind.userId and "
			 		+ "account2.phone='"+TestcaseFrame.getaccount().getAccount()
			 		+"' and account2.delFlag=0 "
			 		+ "and userGatewayBind.delFlag=0 and userGatewayBind.uid=device.gatewayUID and "
			 		+ "device.delFlag=0  and (device.deviceType=0 or device.deviceType=1 or device.deviceType=38 or device.deviceType=19 or device.deviceType=82)";
			
		 }
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"),"点击我的");
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"提醒设置\")"),"点击提醒 设置");
		 DBHelperMysql dbwifi=new DBHelperMysql(sqlwifi,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		
		   try {  
			   ResultSet  retwifi = dbwifi.pst.executeQuery();//执行语句，得到结果集 
			 
	           while (retwifi.next()) {
	        	  boolean ifcheckbutton=true;
	        	  //WIFI设备不为空，检查提醒 设备中是否有相应开关
	        	  if(ifcheckbutton)
	        	  {
	        		  Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"定时执行提醒\")").isDisplayed());
	        		  ifcheckbutton=false;  
	        	  }
	        	   countwifi++;
	     
	           }//显示数据
	           Log.logInfo("WIFI设备数为:"+countwifi);
	    	   if(countwifi==0)
			   {
				   try{
					  driver.findElementByAndroidUIAutomator("text(\"定时执行提醒\")").isDisplayed();
				   }
				   catch(Exception e)
				   {
					   
				   }
			   }
	    	   else
	    	   {
	     	  AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"定时执行提醒\")"), "进入定时执行提醒界面");
	        	 
        	  //判断开关是否默认打开
        	  Assert.assertTrue(driver.findElements(By.id("com.orvibo.homemate:id/deviceNameTextView")).size()>0);
        	  AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/left_iv")), "返回到提醒设置界面");
	    	   }
	         
	           retwifi.close();  
	           dbwifi.close();//关闭连接
	           
	           DBHelperMysql dbzigbee=new DBHelperMysql(sqlzigbee,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
	           ResultSet  retzigbee = dbzigbee.pst.executeQuery();//执行语句，得到结果集
	           while (retzigbee.next()) {
		        	  
	        	   boolean ifcheckbutton=true;
		        	  //灯设备不为空
		        	  if(ifcheckbutton)
		        	  {
		        		  Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"节能提醒\")").isDisplayed());
		        		  ifcheckbutton=false;
		        		  
		        	  }
		        	  countzigbee++;
		        	  
	      	     
	           }//显示数据
	           Log.logInfo("灯设备数为:"+countzigbee);
	           if(countzigbee==0)
			   {
				   try{
					  driver.findElementByAndroidUIAutomator("text(\"节能提醒\")").isDisplayed();
				   }
				   catch(Exception e)
				   {
					   
				   }
			   }
	           else
	           {
	        	   
	           
	     	  AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"节能提醒\")"), "进入节能提醒界面");
	   
	         //判断当前界面默认开关是否打开，且灯数量应小于等于服务灯数量
	           Assert.assertTrue(driver.findElements(By.id("com.orvibo.homemate:id/deviceName")).size()>0);
	          Assert.assertTrue(driver.findElements(By.id("com.orvibo.homemate:id/deviceName")).size()<=countzigbee); 
	           }
	           retzigbee.close();  
	           dbzigbee.close();
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		   
		   if(countwifi==0&& countzigbee==0)
		   {
			   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"还没有可设置提醒的设备哟~\")").isDisplayed());
		   }
		
		
	}

	/**
	 * 检查默认家庭在家庭管理主页面的显示
	 */
	
	@Test()
	public void checkdefault()
	{   
		enterhomemanage();
		//获取帐号的名称
		String expectedname=getaccountname();
		//判断默认显示是否正确，如管理员名称是否显示正确
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/memberName")).getText().equals(expectedname));
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"默认\")").isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("com.orvibo.homemate:id/familyCheck")).isDisplayed());
		//判断是否存在家庭管理的标题
		AppOperate.exitElement("com.orvibo.homemate:id/title_tv", driver);
		

	}
	
	/**
	 * 检查新建的家庭在家庭管理主页面的显示
	 */
	@Test(groups={"家庭管理"})
	public void addhome()
	{   
		
		enterhomemanage();
		//判断当前已自创多少个家庭
		int addnumber=driver.findElementsByAndroidUIAutomator("text(\""+getdefaultfamily()+"\")").size();
	
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/right_iv")), "点击新建家庭按扭");
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"创建家庭\")").isDisplayed());
		//输入家庭名称
		EditText familyName=new EditText(driver.findElement(By.id("com.orvibo.homemate:id/familyName")));
		familyName.inputfivechar(driver);
		//截图并保存
		String setbefore=ToolFunctions.getRandomstring(1);
		screenCapCompare(driver, setbefore);
		//设置家庭头像
		AndroidElement familyIcon=driver.findElement(By.id("com.orvibo.homemate:id/familyIcon"));
		//拍照设置头像
		setphotobycamera(familyIcon);
		//查看照片是否设置成功,截图并对比
		String setafter=ToolFunctions.getRandomstring(1);
		screenCapCompare(driver, setafter);
		Assert.assertFalse(compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+setbefore+".png", FileOperate.getScreencapFilePath()+File.separator+setafter+".png", 0.97));
		//点击创建家庭
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/familyAddBtn")), "点击创建家庭");
		AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/title_tv"), 10);
		//判断是否返回到家庭管理界面,并判断是否显示新建的家庭
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\""+familyName.getfivechar()+"\")").isDisplayed());
		
		
	}
	
	/**
	 * 修改默认家庭的家庭名称
	 */
	@Test(groups={"家庭管理"})
	public void edithome()
	{
		enterhomemanage();
		AppOperate.click(driver.findElements(By.id("com.orvibo.homemate:id/familyName")).get(0), "点击进入默认家庭");
		//点击进入默认家庭
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭名称\")"), "点击家庭名称w");
		AndroidElement name=driver.findElement(By.id("com.orvibo.homemate:id/input_family_nickname_edit"));
		//AppOperate.clear(name, "清除名称");
		String editname=ToolFunctions.getRandomstring(5);
		AppOperate.sendKeys(name, "重新输入家庭名称", editname);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"保存\")"), "点击保存按扭");
		AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/title_tv"), 10);
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\""+editname+"\")").isDisplayed());
		
		
	}
 /**
  * 检查默认家庭的排序
  */
	@Test()
	public void sort()
	{
		enterhomemanage();
		List<AndroidElement> list=driver.findElements(By.id("com.orvibo.homemate:id/familyName"));
		Assert.assertEquals(list.get(0).getText(), getdefaultfamily());
	}
	
	/**
	 *家庭成员页面检查——有成员
	 * 家庭成员页面检查——无成员
	 * 邀请家庭成员页面检查
	 * 家庭邀请手机号
	 * 发起邀请成功
	 * 编辑成员页面检查
	 * 编辑成员页面，删除成员
	 */
	@Test(groups={"家庭管理"})
	public void memberoffamily()
	{
		enterhomemanage();
		String defaultname=getdefaultfamily();
		//服务器获取默认家庭成员个数
		int familynumbers=getfamilynumbers(defaultname);
		Log.logInfo("当前家庭有"+familynumbers+"个成员");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\""+getdefaultfamily()+"\")"), "点击进入默认家庭");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭成员\")"), "进入家庭成员列表");
		//判断成员列表是否为空
		if(!AppOperate.ifexitElement("com.orvibo.homemate:id/tv_name", driver))
		{
			//为空界面默认应有提示语
			AppOperate.exitElement("com.orvibo.homemate:id/empty_iv", driver);
			Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"你还没有添加家庭成员\")").isDisplayed());
		}
		else{
			//编辑成员，点击任意成员
			AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/tv_name")), "点击任意成员");
			//判断是否有删除按扭
			AppOperate.exitElement("com.orvibo.homemate:id/info_family_delete", driver);
			//设置备注
			AndroidElement edit=driver.findElement(By.id("com.orvibo.homemate:id/userNicknameEditText"));
			String inputname=ToolFunctions.getRandomstring(5);
			edit.sendKeys(inputname);
			//点击保存
			AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/right_tv")), "点击保存按扭");
			AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/right_iv"), 5);
			//删除成员
			AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/iv_delete")), "点击删除按扭");
			//判断是否有弹框，有的话点击删除
			Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"提  示\")").isDisplayed());
			AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"删除\")"), "弹框内点击删除");
			//判断是否删除成功，服务器框默认家庭成员个数减1
			
			Assert.assertEquals(getfamilynumbers(defaultname), familynumbers-1);
		}
		//邀请成员
		AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/right_iv")), "点击添加成员按扭");
		
		 //判断“发起邀请”是否可点击
		 Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\"发起邀请\")").isEnabled(), "发起邀请默认是激活的");
		 //点击联系人选择按扭
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/contactImageView")), "点击添加联系人选择按扭");
		 //判断是否跳 转到联系人选择界面
		Assert.assertTrue( ToolFunctions.cmdmessage("adb shell dumpsys window w|grep name=", "contacts"), "未跳转到联系人选择界面");
		//返回邀请家人界面
		AppOperate.sendKeyEvent(4, "返回家庭管理界面", driver);
		//手动输入号码,分别输入小于11位，11位，大于11位
		EditText edit=new EditText(driver.findElement(By.id("com.orvibo.homemate:id/accountEditText")));
		 //清掉再输入11位未注册的手机号
		 edit.clear();
		 edit.inputphonenumber(driver);
		 Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"发起邀请\")").isEnabled(), "号码等于11位发起邀请未被激活");
		 //点击发起邀请
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/send_invite_text")), "点击发起邀请按钮");
		 
		 //判断是否会弹出未注册的提示框
		 AppOperate.waitForText(10, "com.orvibo.homemate:id/titleTextView");
		 AppOperate.exitElement("该用户尚未注册", driver);
		 AppOperate.exitElement("试试微信快速邀请吧", driver);
		 //点击微信邀请，判断是否会跳 转到微信界面
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/rightButton")), "点击微信邀请");
		 Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w|grep name=", "com.tencent.mm"), "未跳转到微信界面");
		//返回邀请家人界面
		AppOperate.sendKeyEvent(4, "返回家庭管理界面", driver);
	
	}
	

	/**
	 * 默认家庭数显示
	 * */
 @Test(groups={"家庭管理"})
 public void checkfamilynumber()
 {
	 int familynumber=familynumbers();
	 System.out.println("家庭数是："+familynumber);
	 enterhomemanage();
	 //判断展示家庭数是否与服务器一致
	 Assert.assertEquals(driver.findElements(By.id("com.orvibo.homemate:id/familyIcon")).size(), familynumber);
	 
	 
 }
	/**
	 * 检查加入的家庭在家庭管理主页面的显示
	 * 默认的家庭切换至新建的家庭
	 * 新建家庭成功，进入家庭详情
	 * 删除默认家庭
	 */
	 @Test(groups={"家庭管理"})
	 public void familydetails()
	 {     
		 enterhomemanage();
		 String sql=null;
		   int i=1;
		   String usertype=null,familyname=null,showindex=null;
		   
		   if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		   {
			   sql="select f.familyName,fu.userType,f.showIndex from family as f,familyUsers as fu,account2 as a "
				   		+ "where fu.familyId=f.familyId and fu.userId=a.userId and a.email='"+TestcaseFrame.getaccount().getAccount()+
				   		"' and fu.delFlag=0";
		   }
		   else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		   {
			   sql="select f.familyName,fu.userType,f.showIndex from family as f,familyUsers as fu,account2 as a "
				   		+ "where fu.familyId=f.familyId and fu.userId=a.userId and a.phone='"+TestcaseFrame.getaccount().getAccount()+
				   		"' and fu.delFlag=0";
		   }
		  
		   DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集 
	           while (ret.next()) { 
	
	        	   familyname=ret.getString(1); 
	        	   AndroidElement familyelement=driver.findElementByAndroidUIAutomator("text(\""+familyname+"\")");
	        	   Log.logInfo("第"+i+"个家庭,家庭名称为:"+familyname);
	        	   AppOperate.click(familyelement, "点击第"+i+"个家庭");
	        	   usertype=ret.getString(2);
	        	   showindex=ret.getString(3);
	        	   System.out.println("showindex为"+showindex);
	        	   //为0则为管理员权限家庭
	        	   if(usertype.equals("0"))
	        	   {   
	        		   
	        		   //showindex为1则为默认家庭
	        		   if(showindex.equals("1"))
	        		   {  
	        			   Log.logInfo(familyelement.getText()+"为默认家庭");
	        			 //权限为管理 员时，家庭详情没有删除或退出家庭按扭
		        		   AppOperate.notExitElement("android.widget.Button", driver);
		        		   AppOperate.sendKeyEvent(4, "返回到家庭管理界面", driver);
		 
	        		   }
	        		   else
	        		   {
	        		   Log.logInfo(familyelement.getText()+"为管理员家庭");
	        		   //非默认家庭且为自创家庭有删除家庭按钮,且权限显示为管理员
	        		   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"删除家庭\")").isDisplayed());
	        		   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"管理员\")").isDisplayed());
	        		   //删除家庭
	        		   AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"删除家庭\")"), "点击删除家庭");
	        		  
	        		   //弹出提示框时点击删除
	        		   AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"删除\")"), "弹出提示框点击删除");
	        		   //等待删除成功,以添加按扭为判定标准
	        		   AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/right_iv"), 7);
	        		   //查看是否删除成功，界面不再展示
	        	
	        		   try{
	        		   Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\""+familyname+"\")").isDisplayed());
	        		   Assert.assertTrue(false);
	        		   }
	        		   catch(Exception e)
	        		   {
	        			   Log.logInfo("家庭删除成功");
	        		   }
	        		   
	        		   }
	        	   }
	        	   //为1则为成员家庭
	        	   else if(usertype.equals("1"))
	        	   {   
	        		   Log.logInfo(familyelement.getText()+"为成员家庭");
	        		   //家庭名称不可点击
	        		   AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭名称\")"), "查看成员家庭家庭名称是否可点击");
	        		   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"家庭详情\")").isDisplayed());
	        		   //家庭头像不可点击
	        		   AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭头像\")"), "查看成员家庭家庭名称是否可点击");
	        		   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"家庭详情\")").isDisplayed());
	        		   
	        		   //查看权限显示
	        		   Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"家庭成员\")").isDisplayed());
	        		   //查看是否有退出家庭的按扭
	        		   AndroidElement exit=driver.findElementByAndroidUIAutomator("text(\"退出家庭\")");
	        		   Assert.assertTrue(exit.isDisplayed());
	        		   //点击退出家庭
	        		   AppOperate.click(exit, "点击退出家庭");
	        		   //弹出提示框后点击退出
	        		   AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"退出\")"), "点击确认退出");
	        		   
	        		   //等待删除成功,以添加按扭为判定标准 
	        		   AppOperate.waitelementexit(driver, By.id("com.orvibo.homemate:id/right_iv"), 7);
	        		   //查看是否删除成功，界面不再展示
	        		   try{
		        		   Assert.assertFalse(driver.findElementByAndroidUIAutomator("text(\""+familyname+"\")").isDisplayed());
		        		   Assert.assertTrue(false);
		        		   }
		        		   catch(Exception e)
		        		   {
		        			   Log.logInfo("退出家庭成功");
		        		   }
	        		   
	        	   }
	        
	        	   i++;
	        	   
	        	       
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
  
	 }
	 /**
	  * 编辑房间
	  */
	 
	 @Test(groups={"房间管理"})
	 public void defaultroom()
	 {
		 enterroommanage();
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/tag_tv")), "点击默认房间");
		 //进入房间编辑界面后，并点击默认图片
		 AndroidElement rompic=driver.findElement(By.id("com.orvibo.homemate:id/room_img_iv"));
		 AppOperate.click(rompic, "点击默认的房间图片");
		 //判断是否有弹框，弹出弹框后点击空白处，查看弹框是否消失
		 List<AndroidElement> menulist=driver.findElements(By.id("com.orvibo.homemate:id/menu_item_text"));
		 Assert.assertEquals(menulist.size(), 3);
		 //进入默认图库
		 AppOperate.click(menulist.get(0), "点击默认图库");
		 //判断默认图库图片不为0
		 //List<AndroidElement> piclist=driver.findElements(By.id("android.widget.ImageView"));
		 List<AndroidElement> piclist=driver.findElements(By.className("android.widget.ImageView"));
		 System.out.println("有多少张图片："+piclist.size());
		 Assert.assertTrue(piclist.size()>1);
		 //选择其中一张图片
		 AppOperate.click(piclist.get(2), "默认图库中选择一张图片");
		 //截图对比是否设置成功
		 String photo=ToolFunctions.getRandomstring(3);
		 screenCapCompare(driver, photo);
		 boolean ifsame=compareimage.sameAs(FileOperate.getScreencapFilePath()+File.separator+photo+".png", FileOperate.getTestDatapFilePath()+File.separator+"editrom.png", 0.90);
		 Assert.assertFalse(ifsame, "头像未设置成功");
		 //点击左上方的返回键返回到编辑房间界面
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/left_iv")), "点击左上方的返回键");
		 //再次点击默认房间图片,打开拍照看是否能调用相机
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/tag_tv")), "点击默认房间");
		 AppOperate.click(rompic, "点击默认的房间图片");
		 AppOperate.click(menulist.get(1), "点击选择框中的“拍照”按扭");
		 Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w |grep name=", "camera"));
		 //按返回键
		 AppOperate.sendKeyEvent(4, "点击返回", driver);
		 //点击从相册中选择，判断是否会跳转
		 AppOperate.click(rompic, "点击默认的房间图片");
		 AppOperate.click(menulist.get(2), "点击选择框中的“从相册中选择”按扭");
		 Assert.assertTrue(ToolFunctions.cmdmessage("adb shell dumpsys window w |grep name=", "gallery"));
		 AppOperate.sendKeyEvent(4, "返回到编辑房间界面", driver);
		 //输入房间名称
		 String roomname=ToolFunctions.getRandomstring(4);
		 driver.findElement(By.id("com.orvibo.homemate:id/room_name_et")).sendKeys(roomname);
		 //点击保存
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/save_tv")), "房间编辑界面点击保存");
	 }
	 /**
	  * 添加
	  */
	 @Test(groups={"房间管理1"})
	 public void addroom()
	 {
		 //判断当前家庭，并获取当前家庭名称
		 String nowfamilyname=driver.findElement(By.id("com.orvibo.homemate:id/familyTextView")).getText();
		 enterroommanage();
		 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"+添加房间\")"), "点击添加房间按扭");
		 //输入房间名称
		 String roomname1=ToolFunctions.getRandomstring(4);
		 driver.findElement(By.id("com.orvibo.homemate:id/room_name_et")).sendKeys(roomname1);
		 //点击保存
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/save_tv")), "点击保存按扭");
		 //判断跳转回房间管理 界面后，是否会显示已添加的房间
		 Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\"房间管理\")").isDisplayed());
		 Assert.assertTrue(driver.findElementByAndroidUIAutomator("text(\""+roomname1+"\")").isDisplayed());
		 
	 }
	 /**
	  * 删除房间
	  */
	 @Test(groups={"房间管理"})
	 public void delectroom()
	 {   
		 String nowfamilyname=driver.findElement(By.id("com.orvibo.homemate:id/familyTextView")).getText();
		 enterroommanage();
		 int roomnumber1=familyrooms(nowfamilyname);
		 Log.logInfo("房间数为"+roomnumber1);
		 int roomnumber2 = 0;
		 //如果房间数为0，则添加房间
		 if(roomnumber1<=0)
		 {
			 AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"+添加房间\")"), "点击添加房间按扭");
			 //输入房间名称
			 String roomname1=ToolFunctions.getRandomstring(4);
			 driver.findElement(By.id("com.orvibo.homemate:id/room_name_et")).sendKeys(roomname1);
			 //点击保存
			 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/save_tv")), "点击保存按扭");
			 roomnumber1=familyrooms(nowfamilyname);
		 }
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/delete_iv")), "删除一个房间");
		 newSleep(2);
		 roomnumber2=familyrooms(nowfamilyname);
		 Log.logInfo("删除后的房间数为:"+roomnumber2);
		 Assert.assertEquals(roomnumber2+1, roomnumber1);
		
	
	 }
	 
	 
	 /**
	  * 添加楼层
	  */
	 @Test(groups={"房间管理"})
	 public void addfloor()
	 {   
		 int beforefloor,afterfloor;
		 //判断当前家庭，并获取当前家庭名称
		 String nowfamilyname=driver.findElement(By.id("com.orvibo.homemate:id/familyTextView")).getText();
		 Log.logInfo("当前家庭为:"+nowfamilyname);
		 beforefloor=floornumbers(nowfamilyname);
		 Log.logInfo("添加楼层前的楼层数为:"+beforefloor);
		 enterroommanage();
		 //服务器获取楼层数
		 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/add_floor_tv")), "点击添加楼层的按扭");
		 //判断当前楼层是否加1
		 afterfloor=floornumbers(nowfamilyname);
		 Assert.assertEquals(afterfloor-1,beforefloor);
	 }
	 
	 /**
	  * 删除楼层
	  */
	 @Test(groups={"房间管理"})
	 public void delectfloor()
	 {   
		 String nowfamilyname=driver.findElement(By.id("com.orvibo.homemate:id/familyTextView")).getText();
		 Log.logInfo("当前家庭为:"+nowfamilyname);
		 enterroommanage();
		 List<AndroidElement> floolist,delectlist;
		 //判断是否有楼层
		 try{
		   floolist=driver.findElements(By.id("com.orvibo.homemate:id/floor_name_tv"));
		   delectlist=driver.findElements(By.id("com.orvibo.homemate:id/delete_tv"));
		   for(int i=0;i<floolist.size();i++)
		   {   
			   String floorname=floolist.get(i).getText();
			   //楼层有房间时会弹出提示框
			   if(ifroomonfloor(nowfamilyname,floorname)>0)
			   {
				   AppOperate.click(delectlist.get(i), "删除第"+i+1+"个家庭");
				   //判断是否会弹出提示框
				   AppOperate.exitElement("com.orvibo.homemate:id/contentTextView", driver);
				   //提示框中点击确认删除
				   AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/rightTextView")), "提示框中点击确认删除");
				   newSleep(2);
				   //判断界面不存在 此楼层
				   try{
					   driver.findElementByAndroidUIAutomator("text(\""+floorname+"\")").click();;
				   }
				   catch(Exception e)
				   {
					   Log.logInfo("删除成功");
				   }
				   
			   }
			   else
			   {
				   AppOperate.click(delectlist.get(i), "删除第"+i+1+"个家庭");
				   newSleep(2);
				 //判断界面不存在 此楼层
				   try{
					   driver.findElementByAndroidUIAutomator("text(\""+floorname+"\")").click();;
				   }
				   catch(Exception e)
				   {
					   Log.logInfo("删除成功");
				   }
			   }
		   }
		 }
		 catch(Exception e)
		 {
			 Log.logInfo("该房间没有楼层");
		 }
	     
	
		 
	 }
	 /**
	  * 托动楼层排序
	  */
	 @Test(groups={"房间管理"})
	 public void dragfloor()
	 {   
		 String nowfamilyname=driver.findElement(By.id("com.orvibo.homemate:id/familyTextView")).getText();
		 Log.logInfo("当前家庭为:"+nowfamilyname);
		 enterroommanage();
		 //服务器端拉取数据判断当前楼层个数
		 List<String> floorbefore=floorsort(nowfamilyname);
		 int floornumber=floorbefore.size();
		 Log.logInfo("操作前服务器获取楼层数为："+floornumber);
		 List<AndroidElement> floorlist1=driver.findElements(By.id("com.orvibo.homemate:id/floor_name_tv"));
		 Log.logInfo("前端展示楼层数为："+floorlist1.size());
		 //判断服务器与前端展现楼层数是否一样
		 Assert.assertEquals(floorlist1.size(), floornumber);
		 //如果楼层数小于3则添加楼层，大于删除则删除楼层到3层
		 if(floornumber<3)
		 {
		 while(floornumber<3)
		 {
			 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/add_floor_tv")), "楼层数小于3楼时添加楼层");
			 floornumber++;
		 }
		 }
		 else if(floornumber>3)
		 {  
			 while(floornumber>3)
			 {
				 AppOperate.click(driver.findElement(By.id("com.orvibo.homemate:id/delete_tv")), "楼层数小于3楼时删除楼层");
				 floornumber--;
			 }
		 }
		 List<String> floorafter=floorsort(nowfamilyname);
		 //再次判断前端展现楼层数
		 List<AndroidElement> floorlist=driver.findElements(By.id("com.orvibo.homemate:id/floor_name_tv"));
		 Log.logInfo("操作后前端楼层数为："+floorafter.size());
		 //拖动楼层，三楼托动到一楼
		 new TouchAction(driver).longPress(floorlist.get(2)).moveTo(floorlist.get(0)).release().perform();;
	
		 List<AndroidElement> floorlist2=driver.findElements(By.id("com.orvibo.homemate:id/floor_name_tv"));
		 //托动后判断排序
		 Log.logInfo("托动前第1层楼层为"+floorlist.get(0).getText());
		 Log.logInfo("托动后第1层楼层为"+floorlist2.get(0).getText());
		 Assert.assertFalse(floorlist.get(0).getText().equals(floorlist2.get(0).getText()));
	 }
       
	 /**
	  * 判断是否有主机
	  */
		public  boolean  ifhashub() {
			String sql=null;
			String modelid=ReadExcel.readsimpledata(1, 1, 2);
			boolean flag=false;
			//服务器查询该账号是否有主机
			if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
			{
				sql="select count(*) from account2,gateway,userGatewayBind where account2.email='"+TestcaseFrame.getaccount().getAccount()+
					   		"'  and account2.userId=userGatewayBind.userId and userGatewayBind.uid=gateway.uid and gateway.model='"+modelid+"'";
			   }
			else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
			   {
				sql="select count(*) from account2,gateway,userGatewayBind where account2.phone='"+TestcaseFrame.getaccount().getAccount()+
				   		"'  and account2.userId=userGatewayBind.userId and userGatewayBind.uid=gateway.uid and gateway.model='"+modelid+"'";
			   }
			 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
			   try {  
				   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
		           while (ret.next()) {
		        	  //不为0代表有主机，检查我的界面是否显示我的主机
		        	if(ret.getInt(1)!=0)
		        	{   
		        		flag=true;
		        	}	           
		           }//显示数据 
		         
		           ret.close();  
		           db.close();//关闭连接  
		       } catch (SQLException e) {  
		           e.printStackTrace();  
		       }  
		return flag;
		
		}
	 
	 /**
	  * 楼层排序
	  */
	 public static  List<String> floorsort(String familyname)
	 {
		 String sql=null;
		 List<String> list = new ArrayList();
		
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sql="select family.familyName ,floor.floorName from floor,account2,family where account2.userId=floor.userId and floor.delFlag=0 and account2.email='"+TestcaseFrame.getaccount().getAccount()
			 		+"'  and family.familyId=floor.familyId and family.delFlag=0 and family.familyName='"+familyname+"' order by floor.createTime";
			 System.out.println(sql);
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql="select family.familyName ,floor.floorName from floor,account2,family where account2.userId=floor.userId and floor.delFlag=0 and account2.phone='"+TestcaseFrame.getaccount().getAccount()
				 		+"'  and family.familyId=floor.familyId and family.delFlag=0 and family.familyName='"+familyname+"' order by floor.createTime";
		 }
		 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) {
	        	   list.add(ret.getString(2))  ;
	        	   
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		   return list;
	 }
	 
    /**
     * 某个家庭有多少个房间
     */
	 public int familyrooms(String familyname)
	 {
		 String sql=null;
		 int count=0;
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sql="select  count(*) from account2,floor,family,room "
			 		+ "where account2.userId=floor.userId and account2.email='"+TestcaseFrame.getaccount().getAccount()
			 		+"' and floor.delFlag=0 and family.familyId=floor.familyId"
			 		+ " and room.floorId=floor.floorId and room.delFlag=0 and family.familyName='"+familyname+"'";
			 
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql="select  count(*) from account2,floor,family,room "
				 		+ "where account2.userId=floor.userId and account2.phone='"+TestcaseFrame.getaccount().getAccount()
				 		+"' and floor.delFlag=0 and family.familyId=floor.familyId"
				 		+ " and room.floorId=floor.floorId and room.delFlag=0 and family.familyName='"+familyname+"'";
			 
		 }
		 System.out.println(sql);
		 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) { 
	        	   count=ret.getInt(1) ;   
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		 return count;
	 }
	 
	 /**
	  * 判断某个楼层有没有房间
	  */
	 public  static int ifroomonfloor(String familyname,String floorname)
	 {  
		 String sql=null;
		 int count=0;
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sql="select  count(*) from account2,floor,family,room "
			 		+ "where account2.userId=floor.userId and account2.email='"+TestcaseFrame.getaccount().getAccount()
			 		+"' and floor.delFlag=0 and family.familyId=floor.familyId"
			 		+ " and room.floorId=floor.floorId and room.delFlag=0 and family.familyName='"+familyname+"' and floor.floorName='"+floorname+"'";
			 
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql="select  count(*) from account2,floor,family,room "
				 		+ "where account2.userId=floor.userId and account2.phone='"+TestcaseFrame.getaccount().getAccount()
				 		+"' and floor.delFlag=0 and family.familyId=floor.familyId"
				 		+ " and room.floorId=floor.floorId and room.delFlag=0 and family.familyName='"+familyname+"' and floor.floorName='"+floorname+"'";
			 
		 }
		 System.out.println(sql);
		 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) { 
	        	   count=ret.getInt(1) ;   
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		 return count;
	 }
	 
	 /**
	  * 某个家庭有几个楼层
	  */
	public  int floornumbers(String familyname)
	{   
		String sql=null;
		int count=0;
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sql="select family.familyName,floor.floorName,account2.email from floor,account2,family "
			 		+ "where account2.userId=floor.userId and floor.delFlag=0 and account2.email='"+TestcaseFrame.getaccount().getAccount()+
			 		"' and family.familyId=floor.familyId and family.delFlag=0 and family.familyName='"+familyname+"'";
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql="select family.familyName,floor.floorName,account2.email from floor,account2,family "
				 		+ "where account2.userId=floor.userId and floor.delFlag=0 and account2.phone='"+TestcaseFrame.getaccount().getAccount()+
				 		"' and family.familyId=floor.familyId and family.delFlag=0 and family.familyName='"+familyname+"'";
		 }
		 System.out.println(sql);
		 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) { 
	        	   count++;     
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		
		return count;
	}

	 
	 /**
	  * 判断当前账号有几个家庭
	  */
	 public static int familynumbers()
	 {   
		 int count=0;
		 String sql = null;
		 //判断登录的什么类型的账号
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sql=" select f.familyName from family as f "
						+ "where familyId in"
						+ "("
						+ "select fu.familyId from account2 as a,familyUsers as fu "
						+ "where fu.userId=a.userId and a.email='"+TestcaseFrame.getaccount().getAccount()+"' and fu.delFlag=0"
						+")";
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql=" select f.familyName from family as f "
						+ "where familyId in"
						+ "("
						+ "select fu.familyId from account2 as a,familyUsers as fu "
						+ "where fu.userId=a.userId and a.phone='"+TestcaseFrame.getaccount().getAccount()+"' and fu.delFlag=0"
						+")";
		 }
		

	//	 System.out.println("查询语句是:"+sql);
		 DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) { 
	        	   count++;     
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
	   
		   return count;
		 
	 }
	 /**
	  * 判断第一个家庭的类型,1的话是子成员，0为管理 员
	  */
	 public String familytype()
	 {
	   String type=null;
	   String sql="select f.userType from account2 as a,familyUsers as f where f.userId=a.userId and "
			 		+ "email='"+TestcaseFrame.getaccount().getAccount()+"' or phone='"+TestcaseFrame.getaccount().getAccount()+"' and f.delFlag=0";
	   DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
	   try {  
		   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
           while (ret.next()) { 
        	   type=ret.getString(1);
        	       break;
     
           }//显示数据  
           ret.close();  
           db.close();//关闭连接  
       } catch (SQLException e) {  
           e.printStackTrace();  
       }  
		return type;
	 }
	 
	 /**
	  * 获取默认家庭的名称
	  */
	 public  String getdefaultfamily()
	 {  
		 String sql=null;
		 String defaultname=null;
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			sql="select f.familyName,fu.userType,f.showIndex from family as f,familyUsers as fu,account2 as a "
					+ "where fu.familyId=f.familyId and fu.userId=a.userId "
					+ "and a.email='"+TestcaseFrame.getaccount().getAccount()+"' and fu.delFlag=0 and f.showIndex=1";
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
				sql="select f.familyName,fu.userType,f.showIndex from family as f,familyUsers as fu,account2 as a "
						+ "where fu.familyId=f.familyId and fu.userId=a.userId "
						+ "and a.phone='"+TestcaseFrame.getaccount().getAccount()+"' and fu.delFlag=0 and f.showIndex=1";
		 }
		 
		   DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) { 
	        	   defaultname=ret.getString(1);
	     
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		 
		 return defaultname;
		 
	 }
	/**
	 * 获取家庭成员个数
	 * @return
	 */
	 public static int getfamilynumbers(String familyname)
	 {   
		 int number=0;
		 String sql="select count(*)  from family as f left join familyUsers as fu  "
		 		+ "on fu.familyId=f.familyId,account2 as a  where  f.familyName='"+familyname+
		 		"' and fu.delFlag=0 and f.delFlag=0 and fu.userId=a.userId";
		   DBHelperMysql db=new DBHelperMysql(sql,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		   try {  
			   ResultSet  ret = db.pst.executeQuery();//执行语句，得到结果集  
	           while (ret.next()) { 
	        	   number=ret.getInt(1);
	     
	     
	           }//显示数据  
	           ret.close();  
	           db.close();//关闭连接  
	       } catch (SQLException e) {  
	           e.printStackTrace();  
	       }  
		   return number-1;
		 
	 }
	 /**
	  * 获取登录的帐户名称
	  * @return
	  */
	public  String getaccountname()
	{
		String homename;
		//获取帐号的名称,如果userName为空则显示账号名称
		String sql1=null,sql2=null;
		
		
		 if(getaccount().getLogingType()==Constant.LOGING_TYPE_EMAIL)
		 {
			 sql1="select userName from account2 where email='"+TestcaseFrame.getaccount().getAccount()+"'";
	         sql2="select * from account2 where email='"+TestcaseFrame.getaccount().getAccount()+"'";
		 }
		 else if(getaccount().getLogingType()==Constant.LOGING_TYPE_PHONE)
		 {
			 sql1="select userName from account2 where phone='"+TestcaseFrame.getaccount().getAccount()+"'";
			 sql2="select * from account2 where phone='"+TestcaseFrame.getaccount().getAccount()+"'";
		 }
		 
		 //name为从数据库查询到的名称
		 String name=Query.executSql(sql1,1,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		 System.out.println("");
		if(name.trim().equals(""))
		{
		
		String expectedname=Query.executSql(sql2,1,Constant.INTERNALURL,Constant.DATABASEACCOUNTINTER,Constant.DATABASEACCOUNTPASSWORD);
		homename=expectedname;
		}
		else 
			homename=name;
		return homename;
		
	}
	
	public static void main(String args[])
	{
		//System.out.println("家庭数"+familynumbers());
		//sethomemanagename();
		//System.out.println(floornumbers("老家"));
		System.out.println(ifroomonfloor("老家","三楼"));

		
		
	}
	

	
	
	/**
	 * 进入家庭管理界面
	 */
	public void enterhomemanage()
	{
		newSleep(2);
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"我的\")"), "点击'我的'");
		AppOperate.click(driver.findElementByAndroidUIAutomator("text(\"家庭管理\")"), "点击'我的'");
	}
	
	
	
	

	@AfterMethod(alwaysRun=true)
	public void tearDown(){
		//关闭appium 资源
		Log.logInfo("**********");
		driver.quit();
		
	}

}
