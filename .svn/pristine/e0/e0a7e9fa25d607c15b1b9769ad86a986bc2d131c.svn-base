package com.oribo.database;

import java.sql.ResultSet;  
import java.sql.SQLException;

import com.mysql.jdbc.Statement;  
public class Query {
	static String sql = null;  
    static DBHelperSqlite db = null;  
   // static DBHelperMysql db = null;  
    static ResultSet ret = null;  
  
    public static void main(String[] args) {  
        sql = "select * from thirdAccount";//SQL语句  
        /*db= new DBHelperMysql(sql);//创建DBHelper对象 */   
        db=new DBHelperSqlite(sql);
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) {  
                String uid = ret.getString(1);  
                String ufname = ret.getString(2);  
                String ulname = ret.getString(3);  
                String udate = ret.getString(4);  
                System.out.println(uid + "\t" + ufname + "\t" + ulname + "\t" + udate );  
            }//显示数据  
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

}
