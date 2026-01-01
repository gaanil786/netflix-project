package com.netflix.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.netflix.base.DriverManager;

public class ScreenshotUtils {

	public static String takeScreenshot(String name) {
		File src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "reports/screenshots/" + name + ".png";
		try {

			FileUtils.copyFile(src, new File(path));

		} catch (Exception e) {
			return e.getMessage();
		}
		return path;

	}
}
