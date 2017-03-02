package com.oribo.ReadExcelCase;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 获取excel表的数据(过程封装)
 * @author Administrator
 *
 */

public class GetData {
	static String value = null;
	static ArrayList<String> list = new ArrayList<String>();
	static ArrayList<String> methods = new ArrayList<String>();
	static ArrayList<String> values = new ArrayList<String>();
	private static HSSFWorkbook workbook;

	/**
	 * 根据参数，获取Excel文档中的Value值
	 * 
	 * @param path
	 * @param testCaseName
	 * @param num
	 * @return
	 * @throws Exception
	 */
	//获取特定表格特定行的数据 
	public static String getExcelData(String path, String testCaseName, int num) throws Exception {
		list.clear();
		File file = new File(path);
		workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workbook.getSheet(testCaseName);//testCaseName指sheet名称
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 1; i < lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell(row.getLastCellNum() - 1);
			String value = cell.getStringCellValue();
			list.add(value);
		}
		return list.get(num);
	}

	/**
	 * 获取Excel的Methods列的所有值
	 * 
	 * @param path
	 * @param testCaseName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getMethods(String path, String testCaseName) throws Exception {
		methods.clear();
		File file = new File(path);
		workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workbook.getSheet(testCaseName);
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 1; i <= lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			HSSFCell cellM = row.getCell(2);
			String m = cellM.getStringCellValue();
			if (m == "") {
				break;
			} else {
				methods.add(m);
			}
		}
		return methods;
	}

	/**
	 * 获取Excel的Values列的所有值
	 * 
	 * @param path
	 * @param testCaseName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getValues(String path, String testCaseName) throws Exception {
		values.clear();
		File file = new File(path);
		workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workbook.getSheet(testCaseName);
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 1; i < lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			HSSFCell cellV = row.getCell(3);
			String v = cellV.getStringCellValue();
			if (v == "") {
				break;
			} else {
				values.add(v);
			}
		}
		return values;
	}

	/**
	 * 获取测试脚本的用例总数
	 * 
	 * @param path
	 * @param testCaseName
	 * @return
	 * @throws Exception
	 */
	public static int getSteps(String path, String testCaseName) throws Exception {
		File file = new File(path);
		workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workbook.getSheet(testCaseName);
		int i = sheet.getLastRowNum();
		return i;
	}
	
	//判断Excel单元格字符串类型
	private static String getValue (HSSFCell hssfCell)
	{
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN)
		{
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC)
		{
			// 返回数值类型的值
			return String.valueOf((long)(hssfCell.getNumericCellValue()));
		}else
		{
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}


	public static void main(String[] args) throws Exception {
/*		System.out.println(GetData.getMethods(Constant.ExcelDataPath, TestCaseName.loginNormal));
		System.out.println(GetData.getMethods(Constant.ExcelDataPath, TestCaseName.selectPlaza));
		System.out.println(GetData.getMethods(Constant.ExcelDataPath, TestCaseName.myOrders));
		System.out.println(GetData.getMethods(Constant.ExcelDataPath, TestCaseName.myRefund));
		System.out.println(GetData.getMethods(Constant.ExcelDataPath, TestCaseName.myTickets));*/
	}
}
