package com.oribo.ReadExcelCase;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.OutputStream;  
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  

  
public class writeExcel {  
    private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx";  
  
    public static void writeexcel(Map<String, String> map, String finalXlsxPath,int sheetindex){  
        OutputStream out = null;  
        try {  
            // 读取Excel文档  
            HSSFWorkbook workBook = new HSSFWorkbook( new FileInputStream(finalXlsxPath));   
            // 获取工作表 
            Sheet sheet = workBook.getSheetAt(sheetindex); 
            
            //往excel中写新数据  
            Collection<String> coll=map.values();
            Iterator<String> it = coll.iterator();
            int i=0;  
            String str;
            while(it.hasNext())
             {    
            	  str=it.next();
                  Row row = sheet.getRow(1); 
                  Cell first = row.createCell(i);  
                  first.setCellValue(str);  
                  i++;
                  
            }  
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效  
            out =  new FileOutputStream(finalXlsxPath);  
          //  out =  new FileOutputStream("D:\\2.xls");  //可实现复制的功能
            workBook.write(out);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally{  
            try {  
                if(out != null){  
                    out.flush();  
                    out.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    
    public static void main(String args[])
    {   
    	Map<String,String> map=new HashMap<>();
    	map.put("1", "u55511111uuu");
    	map.put("2", "jk555hh1111");
    	writeexcel(map,"D:\\1.xls",0);
    }
    
    


}

