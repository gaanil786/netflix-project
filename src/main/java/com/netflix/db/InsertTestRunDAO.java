package com.netflix.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTestRunDAO {

	 private static final String INSERT_TEST_RUNS =
		        "INSERT INTO test_runs (run_id, test_name, status, error_message, start_time) " +
		        "VALUES (?, ?, ?, ?, NOW())";

		    public static int InsertTestRun(int runId, String testName, String status, String errorMessage) throws SQLException {
		        int testRunId = -1;

		        try (Connection conn = DBConnection.getConnection();
		             PreparedStatement ps = conn.prepareStatement(INSERT_TEST_RUNS, Statement.RETURN_GENERATED_KEYS)) {

		            ps.setInt(1, runId);          // parent run_id from testrun
		            ps.setString(2, testName);    // test method name
		            ps.setString(3, status);      // usually "STARTED"
		            ps.setString(4, errorMessage == null ? "" : errorMessage);
		            ps.executeUpdate();           // execute the insert

		            try (ResultSet rs = ps.getGeneratedKeys()) {
		                if (rs.next()) {
		                    testRunId = rs.getInt(1); // capture the auto-generated test_run_id
		                    System.out.println("Inserted test_runs row with test_run_id: " + testRunId);
		                }
		            }
		        }

		        return testRunId;
		    }

		    // Overload for when errorMessage is not known yet
		    public static int InsertTestRun(int runId, String testName, String status) throws SQLException {
		        return InsertTestRun(runId, testName, status, null);
		    }
		}