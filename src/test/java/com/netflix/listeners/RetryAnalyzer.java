package com.netflix.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int count = 0;
	private final int retryLimit = 2;

	@Override
	public boolean retry(ITestResult result) {
		if (count < retryLimit) {
			count++;
			System.out.println("Retrying test " + result.getName() + " again, attempt " + retryLimit);
			return true;
		}
		return false;
	}
}
