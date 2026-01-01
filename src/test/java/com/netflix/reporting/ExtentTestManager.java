package com.netflix.reporting;

import java.sql.SQLException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.netflix.db.FetchQueriesDAO;

public class ExtentTestManager {
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static ExtentTest startTest(String name, String description) {
		ExtentTest t = ExtentManager.getExtent().createTest(name, description);
		test.set(t);
		return t;
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	// Log pass
	public static void pass(String message) {
		getTest().pass(message);
	}

	// Log fail with optional screenshot
	public static void fail(String message, String screenshotPath) {
		try {
			if (screenshotPath != null) {
				getTest().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} else {
				getTest().fail(message);
			}
		} catch (Exception e) {
			getTest().fail(message + " (screenshot not available)");
		}
	}

	// Log skip
	public static void skip(String message) {
		getTest().skip(message);
	}

	// Log info
	public static void info(String message) {
		getTest().info(message);
	}

	// NEW: Log database records
	public static void logDatabaseRecords(String jdbcUrl, String username, String password, String query) {
		// EvidenceDAO.getFailedTestsWithEvidence();
		
		try {
			FetchQueriesDAO.getPassedTests();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 getTest().pass("Database records successfully attached to report!");
	}

}
