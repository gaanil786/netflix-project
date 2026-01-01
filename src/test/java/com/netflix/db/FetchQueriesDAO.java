package com.netflix.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchQueriesDAO {
	// Fetch all test execution records
	public static final String FETCH_ALL_TESTS = "SELECT test_name, status, error_message from test_runs";

	public static final String FETCH_FAILED_TESTS = "SELECT test_run_id, run_id, test_name, status FROM test_runs WHERE status = 'FAIL'";

	public static final String FETCH_PASSED_TESTS = "SELECT test_run_id, run_id, test_name, status FROM test_runs WHERE status = 'PASS'";

	// public static final String FETCH_PASSED_TESTS = "SELECT * FROM test_runs
	// WHERE status='PASS' AND run_id=(SELECT MAX(run_id) FROM testrun)";
	public static final String FETCH_LATEST_RUN_SUMMARY = "SELECT COUNT(*) AS total_tests, "
			+ "SUM(CASE WHEN status = 'PASSED' THEN 1 ELSE 0 END) AS passed, "
			+ "SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) AS failed "
			+ "FROM TestResults WHERE run_id = (SELECT MAX(run_id) FROM TestRun)";

	// Fetch all test execution records
	public static List<Map<String, Object>> getAllTests() throws SQLException {

		return (List<Map<String, Object>>) DBUtils.executeQuery(FETCH_ALL_TESTS);
	}
	// It was not static before down, now it is

	public static List<Map<String, Object>> getPassedTests() throws SQLException {

		// return (List<Map<String, Object>>) DBUtils.executeQuery(FETCH_PASSED_TESTS);
		// ------------
		List<Map<String, Object>> results = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(FETCH_PASSED_TESTS)) {

			System.out.println("-------- PASSED TEST CASES--------");
			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				row.put("test_run_id", rs.getInt("test_run_id"));
				row.put("run_id", String.valueOf(rs.getInt("run_id")));
				row.put("test_name", rs.getString("test_name"));
				row.put("status", rs.getString("status"));
				results.add(row);

				// Debug print
				System.out.println("Test Run ID" + row.get("test_run_id"));
				System.out.println("Run ID: " + row.get("run_id"));
				System.out.println("Test: " + row.get("test_name"));
				System.out.println("Status: " + row.get("status"));
				System.out.println("------");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	// Fetch failed tests
	public static List<Map<String, Object>> getFailedTests() throws SQLException {
		// return (List<Map<String, Object>>) DBUtils.executeQuery(FETCH_FAILED_TESTS);

		// ---------------------
		List<Map<String, Object>> results = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(FETCH_FAILED_TESTS)) {

			System.out.println("-------- FAILED TEST CASES--------");
			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				row.put("test_run_id", rs.getInt("test_run_id"));
				row.put("run_id", String.valueOf(rs.getInt("run_id")));
				row.put("test_name", rs.getString("test_name"));
				row.put("status", rs.getString("status"));
				results.add(row);

				// Debug print
				System.out.println("Test Run ID" + row.get("test_run_id"));
				System.out.println("Run ID: " + row.get("run_id"));
				System.out.println("Test: " + row.get("test_name"));
				System.out.println("Status: " + row.get("status"));
				System.out.println("------");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	// Fetch latest run summary
	public List<Map<String, Object>> getLatestRunSummary() throws SQLException {
		return (List<Map<String, Object>>) DBUtils.executeQuery(FETCH_LATEST_RUN_SUMMARY);
	}
}
