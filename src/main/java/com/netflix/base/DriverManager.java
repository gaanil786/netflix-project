package com.netflix.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

//	public static void initDriver() {
//		if (driver.get() == null) {
//			WebDriverManager.chromedriver().setup();
//			driver.set(new ChromeDriver());
//		}
//	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	public static void setDriver(WebDriver driverInstance) {
		driver.set(driverInstance);
	}
}
