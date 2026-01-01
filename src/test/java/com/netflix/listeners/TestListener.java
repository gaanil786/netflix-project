package com.netflix.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.netflix.db.DBConnection;
import com.netflix.db.EvidenceDAO;
import com.netflix.db.InsertTestRunDAO;
import com.netflix.db.UpdateTestRunDAO;
import com.netflix.reporting.ExtentManager;
import com.netflix.reporting.ExtentTestManager;
import com.netflix.utils.LoggerUtils;
import com.netflix.utils.ScreenshotUtils;

@Listeners(TestListener.class)
public class TestListener implements ITestListener {

	private static int currentRunId;

	@Override
	public void onStart(ITestContext context) {
		// Create a new parent run in TestRun at suite start
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO testrun (triggered_by) VALUES (?)",
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, context.getSuite().getName());
			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					currentRunId = rs.getInt(1);
					System.out.println("New TestRun created with run_id: " + currentRunId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// Start a new test node in Extent
		ExtentTestManager.startTest(testName, "Parameters: " + Arrays.toString(result.getParameters())

		);
		// DB Integration
		try {
			// InsertTestRunDAO.InsertTestRun(currentRunId, testName, "STARTED");
			int testRunId = InsertTestRunDAO.InsertTestRun(currentRunId, testName, "STARTED");
			result.setAttribute("testRunId", testRunId);
			result.setAttribute("runId", currentRunId); // optional, parent ID
			result.setAttribute("testName", testName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onTestFailure(ITestResult result) {
		String path = ScreenshotUtils.takeScreenshot(result.getName());

		if (path != null) {
			try {

				path = path.replace("\\", "/");

				ExtentTestManager.getTest().fail("Test Failed" + result.getName(),
						MediaEntityBuilder.createScreenCaptureFromPath(path).build());

			} catch (Exception e) {
				ExtentTestManager.getTest().fail("Test Failed, screenshot not available");
				e.printStackTrace();

			}

		} else {
			ExtentTestManager.getTest().fail("❌ Test Failed: " + result.getName());
		}

		// DB Integration
		int testRunId = (int) result.getAttribute("testRunId");
		boolean updated = UpdateTestRunDAO.updateTestRun(testRunId, "FAIL", result.getThrowable().getMessage());
		System.out.println("Update status for testRunId=" + testRunId + " → rows updated: " + updated);
		
		String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());
		String logSnippet = LoggerUtils.getLastLogSnippet(20); // last 20 lines
		int runId = (int) result.getAttribute("runId");
		try {
			EvidenceDAO.InsertEvidence(runId, screenshotPath, null, logSnippet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result.getThrowable() != null) {
			ExtentTestManager.getTest().fail(result.getThrowable());
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.getExtent().flush(); // finalize report
	}

	
	@Override
	public void onTestSuccess(ITestResult result) {
	    ExtentTestManager.getTest().pass("Test Passed");

	    // DB Integration
	    int testRunId = (int) result.getAttribute("testRunId");
	    UpdateTestRunDAO.updateTestRun(testRunId, "PASS", null);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().skip("Test Skipped");
	}

}
