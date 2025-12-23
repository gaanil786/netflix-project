package com.netflix.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.netflix.base.DriverManager;


public class WaitUtils {

    private static final int TIMEOUT = 10;

    public static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT));
    }

    public static void waitForVisibility(WebElement element) {
    	getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickable(WebElement element) {
    	getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public static void waitForPresence(By element) {
    	getWait().until(ExpectedConditions.presenceOfElementLocated(element));
    }
    
    public static void waitForAlert() {
    	getWait().until(ExpectedConditions.alertIsPresent());
    }
}
