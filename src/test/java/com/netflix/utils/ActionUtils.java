package com.netflix.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.netflix.base.DriverManager;

public class ActionUtils {
	 public static void hover(WebElement element) {
	        Actions actions = new Actions(DriverManager.getDriver());
	        actions.moveToElement(element).perform();
	    }
}
