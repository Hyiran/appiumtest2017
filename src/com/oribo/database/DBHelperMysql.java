package com.oribo.database;

/**
 * 打开/关闭数据库
 */
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

public class DBHelperMysql {
	   // public static final String url = "jdbc:mysql://192.168.2.20/vihome_cloud";  
	    public static final String name = "com.mysql.jdbc.Driver";  
	 //   public static final String user = "root";  
	 //   public static final String password = "orvibo888";  
	  
	    public static Connection conn = null;  
	    public PreparedStatement pst = null;  
	    
	    public DBHelperMysql(String sql,String url,String user,String password) {  
	        try {  
	            Class.forName(name);//指定连接类型  
	            conn =DriverManager.getConnection(url, user, password);//获取连接
	            System.out.println("连接成功");
	            pst = conn.prepareStatement(sql);//准备执行语句  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    public static void main(String args[])
	    {
	        PreparedStatement pst1 = null;  
	    	try {
	    		System.out.println("0000");
				Class.forName("com.mysql.jdbc.Driver");
				 conn =DriverManager.getConnection("jdbc:mysql://5592482b7cb3f.gz.cdb.myqcloud.com/vihome_cloud", "root", "testDBewq#@!");//获取连接
		            System.out.println("连接成功");
		            ResultSet  ret = conn.prepareStatement("select * from account2 where email='15079034630@126.com").executeQuery();//准备执行语句
		            while(ret.next())
		            {
		            	System.out.println(ret.getString(3));
		            }
			} catch (Exception e) {
				
			}//指定连接类型 
	    	
	    		
	    }

}
