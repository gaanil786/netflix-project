package com.netflix.tests;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.netflix.base.BaseTest;
import com.netflix.base.DriverManager;
import com.netflix.listeners.RetryAnalyzer;
import com.netflix.listeners.TestListener;
import com.netflix.pages.LoginPage;
import com.netflix.utils.DataProviderUtils;

@Listeners(TestListener.class) // or through xml runner file
public class LoginTests extends BaseTest<LoginTests> {

	private LoginPage loginPage;
	
	
	// Negative Test Cases

	@BeforeMethod
	public void setUp() {
		// WebDriver driver = new ChromeDriver();
		DriverManager.setDriver(driver);
		driver.get("https://netflix.com/login");
	
		loginPage = new LoginPage(DriverManager.getDriver());

	}

//	@Test
//	public void loginFluentTest() {
//
//		new LoginPage(DriverManager.getDriver()).init().enterEmail("valid@user.com").enterPassword("validpass").clickLogin().openSearch()
//				.searchBar().search("Breaking Bad").goToResults().openFirstResult().play();
//	}

	@Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class, retryAnalyzer = RetryAnalyzer.class)
	public void LoginFunctionality(String email, String password, String expectedResult) throws IOException, InterruptedException, AWTException {
		loginPage.enterEmail(email).enterPassword(password).clickLogin();
		
		switch (expectedResult) {
      
        case "WrongPassword":
            assertEquals("You canuse a sign-in code, reset your password or try again.",
                loginPage.getErrorMessage(LoginPage.userNameError));
            break;  

        case "InvalidEmail":
            assertEquals("Sorry, we can't find an account with this email address. Please try again orcreate a new account.",
                loginPage.getErrorMessage(LoginPage.userNameError));
            break;

        case "EmptyFields1":
        	assertEquals("Please enter a valid email or mobile number.",
    				loginPage.getErrorMessage(LoginPage.emptyEmailError));
        	break;

        case "EmptyFields2":
    		assertEquals("Your password must contain between 4 and 60 characters.",
    				loginPage.getErrorMessage(LoginPage.emptyPasswordError));
            break;

        case "SqlInjection":
        	String expected = "The password you just used was found in a data breach. Google Password Manager recommends changing your password now.";
    		String alertText = loginPage.extractTextFromScreenshot(DriverManager.getDriver());
    		System.out.println("Alert OCR: " + alertText);
    		String actual = alertText.replaceAll("\\s+", " ").trim();
    		assertEquals(expected,actual);
            break;
            
        default:
            throw new IllegalArgumentException("Unexpected result type: " + expectedResult);
            
		}

	}

	
	// Positive Test Cases
	


}
