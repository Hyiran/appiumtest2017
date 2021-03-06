package com.oribo.ReadExcelCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.oribo.ReadExcelCase.DataBean;
import com.oribo.dataprovider.Constant;
import com.oribo.log.Log;
import com.oribo.ReadExcelCase.ExcelData;
import java.io.File;  
import java.util.Date;  

/**
 * 读取excel表的测试用例
 * @author cindy 2016-10-11
 *
 */
public class ReadExcel {

	/**
	 * 读取excel 状态监控
	 * @author cindy
	 *
	 */
	public interface ReadExcelListener{
		/**
		 * 读取到哪一行
		 * @param row
		 */
		void readIng(int row);

		/**
		 * 读取错误的信息
		 * @param message
		 */
		void readFail(String message);

		/**
		 * 读取成功
		 */
		void readSucces(Map<Integer, DataBean>  data);
	}
	

	private ReadExcelListener   readExcelListener;


	public ReadExcelListener getReadExcelListener() {
		return readExcelListener;
	}
    
	
	public void setReadExcelListener(ReadExcelListener readExcelListener) {
		this.readExcelListener = readExcelListener;
	}

	

	/**
	 * Read the Excel 2003-2007  读取整个Excel,传入的numerSheet <0 就是读取整张表，大于0 读取第几个表，传送的的读取 index 开始读取数据的行和结束行
	 * @param path the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public void readXls(String path,ExcelData  data,Map<Integer, DataBean>list)  {
		InputStream is;
		HSSFWorkbook hssfWorkbook;	
		try {
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "testdata");
			File filedata = new File(appDir, path);//此处path指文件名
			is = new FileInputStream(filedata );
			hssfWorkbook = new HSSFWorkbook(is);
			if (data.getNumerSheet()>=0) {
	//			System.out.println("读取一张表");
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					if (numSheet == data.getNumerSheet()) {
						operateExcels( hssfWorkbook , data,list);
						break;
					}
					
			}
					
			}  else{
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					operateExcels( hssfWorkbook , data,list);
				}	
			}

		} catch (Exception e) {
			// TODO: handle exception
			if (readExcelListener != null) {
				readExcelListener.readFail("读取数据异常");
			}			
		}
		//return list;
	}
/**
 * 读取Excel批定表格、指定单元格数据
 */
	public static String readsimpledata(int numbersheet,int row,int column)
	{   
		HSSFCell cell = null;

		try {
			InputStream is;
			HSSFWorkbook hssfWorkbook;	
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "testdata");
			File filedata = new File(appDir, "personInfo_TestData.xls");//此处path指文件名
			is = new FileInputStream(filedata);
			hssfWorkbook = new HSSFWorkbook(is);
			HSSFSheet sheet=hssfWorkbook.getSheetAt(numbersheet);
			cell=sheet.getRow(row).getCell(column);		
			//sheet.getRow(row).createCell(3).setCellValue("345345");
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return cell.toString();
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
		
	}
	/**
	 * 写入Excel指定更表格，指定单元格
	 */
	
	public static void writeExcel()
	{

	}


	/**
	 * 操作excel 表
	 * @param hssfWorkbook  
	 * @param numSheet
	 */
	public void operateExcels(HSSFWorkbook hssfWorkbook ,ExcelData  data,Map<Integer, DataBean>list){
		DataBean  mbean  = null;
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(data.getNumerSheet());
		//设置读取的位置
		if (data.getLastIndex() ==0) {
			data.setLastIndex(hssfSheet.getLastRowNum());
		} else{
			data.setLastIndex(data.getLastIndex()-1);
		}
		if (data.getFirstIndex()!=1) {
			data.setFirstIndex(data.getFirstIndex()-1);
		}	
		//System.out.println("index "+index+"setLastIndex"+data.getLastIndex());
		// Read the Row 行
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			//Read the column  列	
			if (rowNum>=data.getFirstIndex() && rowNum <=data.getLastIndex()) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);	
				if (hssfRow ==null) {
					if (readExcelListener !=null) {
						readExcelListener.readFail("读取数据为空");	
					}
				} else if (hssfRow != null) {
					//获取总列数
					int columnNum = hssfRow.getPhysicalNumberOfCells();
					mbean = new DataBean();
					Class userCla = (Class) mbean.getClass(); 
					//得到类中的所有属性集合
					Field[] classfs = userCla.getDeclaredFields(); 
					int length = classfs.length;
					List<HSSFCell> listcell = new ArrayList<>();
					//获取一行的数据封装在 list中
					for (int i = 0; i < columnNum; i++) {
						listcell.add(i,  hssfRow.getCell(i));
						//System.out.println(getValue(listcell.get(i)));
					}	
					//System.out.println("数据封装在读取对象中");
					//测试用例集
				    mbean.setInstructions(getValue(listcell.get(0)) );
				    //用例序号
				    mbean.setId(getValue(listcell.get(1)));
				    //手工测试用例依据
				    mbean.setBaseTestId(getValue(listcell.get(2)));
				    //用例等级
				    mbean.setCaseLevel(getValue(listcell.get(3)));
				    mbean.setCaseType(data.getCaseType());
				    //用例名称
				    mbean.setTestCaseName(getValue(listcell.get(5)));
				    //前置条件
				    mbean.setPrecondition(getValue(listcell.get(6)));
				    //执行步骤
				    mbean.setSteps(getValue(listcell.get(7)));
				    
				    
				 /*   mbean.setActions(getValue(listcell.get(8)));*/
			/*	    if (data.getCaseType().equals(Constant.CASETYPE_ANDROID)) {
				    	mbean.setLocationElement(getValue(listcell.get(9)));
					    mbean.setElementAttribute(getValue(listcell.get(10)));	
						
					} else{
						mbean.setLocationElement(getValue(listcell.get(11)));
					    mbean.setElementAttribute(getValue(listcell.get(12)));	
					}*/
				    //输入数值(去掉含有.0 的字符串)
				    String str = getValue( listcell.get(8));
                    if (str.indexOf(".0")>0) {
                    	mbean.setData(str.substring(0, str.indexOf(".0")));	
					} else if(str.indexOf(".")>0 && str.indexOf("E")>0){
						BigDecimal bd = new BigDecimal(str);//读取excel 表出现科学计数转换为字符窜 
						mbean.setData(bd.toString());
					}else{
						mbean.setData(str);	
					}
                    //检查点
				    mbean.setCheckPoint(getValue(listcell.get(9)));
				    //期望值
				    mbean.setExpectValue(getValue(listcell.get(10)));	
                    mbean.setFunctionName(getValue(listcell.get(13)));
					//列的封装添加
					list.put( rowNum-data.getFirstIndex(),mbean);
					if (readExcelListener != null) {						
						if (rowNum==hssfSheet.getLastRowNum()) {
							readExcelListener.readSucces(list);
						} else{
							readExcelListener.readIng(rowNum);	
						}
					}				
				}
			}
			
		}
	}
	



	/**
	 * 返回excel 列值
	 * @param hssfCell
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 测试
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Map<Integer, DataBean>  bean = new HashMap<>();
	    ReadExcel  excel =  new ReadExcel();
		ExcelData  excelData =  new ExcelData();
		excelData.setNumerSheet(0);
		excelData.setCaseType(Constant.CASETYPE_ANDROID);
		excelData.setLastIndex(21);
		excel.readXls("personInfo_TestData.xls", excelData,bean);
		System.out.println("读了多少条用例"+bean.size());
		System.out.println(bean.size());
		for (int i = 0; i < bean.size(); i++) {
			System.out.println("用例名称"+bean.get(i).getTestCaseName());
		}
		
		System.out.println(readsimpledata(1, 1, 1));
		
		
		
	}

	
}


