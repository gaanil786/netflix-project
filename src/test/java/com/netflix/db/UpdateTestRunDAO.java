package com.netflix.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTestRunDAO {

    private static final String UPDATE_TEST_RUNS =
        "UPDATE test_runs SET status = ?, end_time = NOW(), error_message = ? WHERE test_run_id = ?";

    public static boolean updateTestRun(int testRunId, String status, String errorMessage) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_TEST_RUNS)) {

            ps.setString(1, status);
            ps.setString(2, errorMessage == null ? "" : errorMessage);
            ps.setInt(3, testRunId);

            int rowsUpdated = ps.executeUpdate();

            System.out.println("UpdateTestRunDAO → testRunId=" + testRunId + ", status=" + status
                    + ", rowsUpdated=" + rowsUpdated);

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
