package com.oribo.ReadExcelCase;

/**
 * 测试用例数据类型
 * @author cindy  2016-10-11
 *
 */
public class DataBean {
	String instructions;//测试用例集
	String id;//序号
	String baseTestId;//1.6测试用例的序号
	String caseLevel;//测试用例等级
	String caseType;//用例类型：Android ,IOS
	String testCaseName;//测试用例名称
	String precondition;//前置条件
	String steps;//操作步骤
	String data;//输入值
	String checkPoint;//检查点
	String expectValue;//期望值
	String result;//结果
	String functionname;//脚本方法名
	
	/**
	 * 返回用例集名称
	 * @return
	 */
	public String getInstructions() {
		return instructions;
	}
	/**
	 * 设置用例集名称
	 * @param instructions
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	/**
	 * 获取用例的ID
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置用例的ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取手 工用例ID
	 * @return
	 */
	public String getBaseTestId() {
		return baseTestId;
	}
	/**
	 * 设置手工用例ID
	 * @param baseTestId
	 */
	public void setBaseTestId(String baseTestId) {
		this.baseTestId = baseTestId;
	}
	/**
	 * 获取用例等级
	 * @return
	 */
	public String getCaseLevel() {
		return caseLevel;
	}
	/**
	 * 设置用例等级
	 * @param caseLevel
	 */
	public void setCaseLevel(String caseLevel) {
		this.caseLevel = caseLevel;
	}
	/**
	 * 设置方法名
	 * @param functionname
	 */
	public void setFunctionName(String functionname)
	{
		this.functionname=functionname;
	}
	/**
	 * 获取方法名
	 * @return
	 */
	public String getFunctionName()
	{
		return functionname;
	}
	/**
	 * 获取用例名称
	 * @return
	 */
	public String getTestCaseName() {
		return testCaseName;
	}
	/**
	 * 设置用例名称
	 * @param testCaseName
	 */
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	/**
	 * 获取前置条件
	 * @return
	 */
	public String getPrecondition() {
		return precondition;
	}
	/**
	 * 设置前置条件
	 * @param precondition
	 */
	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}
	/**
	 * 获取执行步骤
	 * @return
	 */
	public String getSteps() {
		return steps;
	}
	/**
	 * 设置执行步骤
	 * @param steps
	 */
	public void setSteps(String steps) {
		this.steps = steps;
	}

	/**
	 * 返回测试输入值
	 * @return
	 */
	public String getData() {
		return data;
	}
	/**
	 * 设置测试输入值
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * 返回检查点
	 * @return
	 */
	public String getCheckPoint() {
		return checkPoint;
	}
	/**
	 * 设置检查点
	 * @param checkPoint
	 */
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	/**
	 * 返回期望值
	 * @return
	 */
	public String getExpectValue() {
		return expectValue;
	}
	/**
	 * 设置期望值
	 * @param expectValue
	 */
	public void setExpectValue(String expectValue) {
		this.expectValue = expectValue;
	}
	/**
	 * 获取执行结果
	 * @return
	 */
	public String getResult() {
		return result;
	}
	/**
	 * 设置执行结果
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * 获取用例类型
	 * @return
	 */
	public String getCaseType() {
		return caseType;
	}
	/**
	 * 设置用例类型
	 * @param caseType
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	
	/**
	 * 获取测试用例名称
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return testCaseName;
	}
	
}
