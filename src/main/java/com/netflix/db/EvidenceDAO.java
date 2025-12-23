package com.netflix.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenceDAO {

//	private static final String FAILED_TEST_CASE_EVIDENCE = "SELECT tr.run_id, tr.test_name, tr.error_message, e.screenshot_path "
//			+ "FROM test_runs tr LEFT JOIN evidence e ON tr.run_id = e.run_id " + "WHERE tr.status = 'FAIL'";

	private static final String FAILED_TEST_CASE_EVIDENCE = "SELECT trn.run_id, ts.test_name, ts.error_message, e.screenshot_path "
			+ "FROM TestRun trn " + "JOIN test_runs ts ON trn.run_id = ts.run_id "
			+ "LEFT JOIN evidence e ON trn.run_id = e.run_id " + "WHERE ts.status = 'FAIL'";

	private static final String INSERT_EVIDENCE = "INSERT INTO evidence (run_id, screenshot_path, ocr_text, log_snippet) VALUES (?, ?, ?, ?)";

	public static int InsertEvidence(int runId, String screenshotPath, String ocrText, String logSnippet)
			throws SQLException {

		return DBUtils.executeUpdate(INSERT_EVIDENCE, runId, screenshotPath, ocrText, logSnippet);

	}

	public static List<Map<String, String>> getFailedTestsWithEvidence() {
		List<Map<String, String>> results = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(FAILED_TEST_CASE_EVIDENCE)) {

			while (rs.next()) {
				Map<String, String> row = new HashMap<>();
				row.put("run_id", String.valueOf(rs.getInt("run_id")));
				row.put("test_name", rs.getString("test_name"));
				row.put("error_message", rs.getString("error_message"));
				row.put("screenshot_path", rs.getString("screenshot_path"));
				results.add(row);

				// Debug print
				System.out.println("Run ID: " + row.get("run_id"));
				System.out.println("Test: " + row.get("test_name"));
				System.out.println("Error: " + row.get("error_message"));
				System.out.println("Screenshot: " + row.get("screenshot_path"));
				System.out.println("------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

}
