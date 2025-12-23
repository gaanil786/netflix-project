package com.netflix.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
	public static ResultSet runQuery(String query) {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(query);
		} catch (Exception e) {
			return null;
		}
	}

	public static ResultSet executeQuery(String query, Object... params) throws SQLException {
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		return stmt.executeQuery();
	}
	
	public static List<Map<String, Object>> executeQuery(String sql) throws SQLException {
	    List<Map<String, Object>> results = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        ResultSetMetaData meta = rs.getMetaData();
	        int colCount = meta.getColumnCount();

	        while (rs.next()) {
	            Map<String, Object> row = new HashMap<>();
	            for (int i = 1; i <= colCount; i++) {
	                row.put(meta.getColumnName(i), rs.getObject(i));
	            }
	            results.add(row);
	        }
	    }
	    return results;
	}


	public static int executeUpdate(String query, Object... params) throws SQLException {
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		return stmt.executeUpdate();
	}
	
	public static void printResultSet(ResultSet rs) throws SQLException {
	    ResultSetMetaData meta = rs.getMetaData();
	    int colCount = meta.getColumnCount();

	    while (rs.next()) {
	        StringBuilder row = new StringBuilder();
	        for (int i = 1; i <= colCount; i++) {
	            String colName = meta.getColumnName(i);
	            Object value = rs.getObject(i);
	            row.append(colName).append("=").append(value).append(" | ");
	        }
	        System.out.println(row.toString());
	    }
	}

}
