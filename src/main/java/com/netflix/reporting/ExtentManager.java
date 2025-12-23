package com.netflix.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;

	public static ExtentReports getExtent() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/reports/ExtentReports.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			extent = new ExtentReports();
			spark.config().setDocumentTitle("Automation Report");
			spark.config().setReportName("Test Execution Results");
			spark.config().setTheme(Theme.STANDARD);
			extent.attachReporter(spark);

			// Add system info for stakeholders
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("User", System.getProperty("user.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("Browser", "Chrome"); // you can make this dynamic

		}
		return extent;
	}
}
