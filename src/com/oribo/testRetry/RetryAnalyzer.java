package com.oribo.testRetry;

/**
 * 设置重跑次数
 */
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

	private static int retryCount= 1;
	private static int maxRetryCount=3;
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
	     if (retryCount <maxRetryCount) {
	            retryCount++;
	            return true;
	        }
		return false;
	}
	
}
