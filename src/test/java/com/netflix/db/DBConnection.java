package com.netflix.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/netflixautomation";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	// Utility method to get a live connection
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("✅ Connection established successfully!");
		} catch (SQLException e) {
			System.err.println("❌ Failed to establish connection!");
			e.printStackTrace();
		}
		return conn;
	}
}
