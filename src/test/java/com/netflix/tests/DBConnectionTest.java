package com.netflix.tests;

import java.sql.Connection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.netflix.db.DBConnection;

public class DBConnectionTest {
	  @Test
	    public void verifyDBConnection() {
	        Connection conn = DBConnection.getConnection();
	        Assert.assertNotNull(conn, "❌ DB connection failed!");
	        System.out.println("✅ DB connection verified successfully!");
	        try {
	            conn.close();
	            System.out.println("🔒 Connection closed cleanly.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
