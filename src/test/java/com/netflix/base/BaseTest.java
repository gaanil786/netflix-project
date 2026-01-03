package com.netflix.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest<T extends BaseTest<?>> {

	protected WebDriver driver;

	@BeforeSuite
	public void setUpSuite() {
		// Initialize driver once for the entire suite
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterSuite
	public void tearDownSuite() {
		if (driver != null) {
			driver.quit();
		}
	}

	@SuppressWarnings("unchecked")
	public T init() {
		this.driver = DriverManager.getDriver();
		return (T) this;
	}

}
