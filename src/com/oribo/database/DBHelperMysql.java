package com.oribo.database;

/**
 * 打开/关闭数据库
 */
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException; 

public class DBHelperMysql {
	    public static final String url = "jdbc:mysql://192.168.2.20/vihome_cloud";  
	    public static final String name = "com.mysql.jdbc.Driver";  
	    public static final String user = "root";  
	    public static final String password = "orvibo888";  
	  
	    public static Connection conn = null;  
	    public PreparedStatement pst = null;  
	    
	    public DBHelperMysql(String sql) {  
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

}
