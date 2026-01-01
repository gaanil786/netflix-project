package com.netflix.db;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DBQueryTest {

	public DBQueryTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void verifyFailedTestsHaveEvidence() {
		List<Map<String, String>> failedTests = EvidenceDAO.getFailedTestsWithEvidence(); // inside InsertTestRunDAO

		// Assert that at least one failed test exists
		Assert.assertTrue(!failedTests.isEmpty(), "No failed tests found in DB!");

		// Assert that failed tests have screenshots
		boolean hasScreenshot = failedTests.stream()
				.anyMatch(t -> t.get("screenshot_path") != null && !t.get("screenshot_path").isEmpty());
		Assert.assertTrue(hasScreenshot, "No failed tests with screenshots found!");
	}

	@Test
	public void FetchAllFailedTests() throws SQLException {
		List<Map<String, Object>> allTests = FetchQueriesDAO.getFailedTests();

		assertFalse(allTests.isEmpty(), "All tests list should not be empty");

		for (Map<String, Object> row : allTests) {
			assertTrue(row.containsKey("test_name"), "Row should contain test_name");
			assertTrue(row.containsKey("status"), "Row should contain status");
		}
	}

	@Test
	public void verifyPassedTestsFetched() throws SQLException {
		FetchQueriesDAO dao = new FetchQueriesDAO();
		List<Map<String, Object>> passedTests = dao.getPassedTests();

		// Assert that we got results
		assertFalse(passedTests.isEmpty(), "Passed tests should not be empty");

		// Log into ExtentReports
//		String htmlTable = ExtentReportsUtils.convertToHtmlTable(passedTests);
//		ExtentTestManager.startTest("Verify Passed Tests Fetched", htmlTable);
//		ExtentTestManager.getTest().info("Passed Tests:<br>" + htmlTable);
//		ExtentTestManager.getTest().pass("Passed tests validation successful");
	}

}
