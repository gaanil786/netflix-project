package com.netflix.pages;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.netflix.base.BaseTest;
import com.netflix.utils.WaitUtils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class LoginPage extends BaseTest<LoginPage> {
	@FindBy(name = "userLoginId")
	private WebElement emailField;

	@FindBy(name = "password")
	private WebElement passwordField;

	@FindBy(className = "ea2wixt2")
	private WebElement loginBtn;

	public static final By userNameError = By.xpath("(//div[@class='textWithTags'])[2]");
	public static final By emptyEmailError = By.xpath("//div[contains(text(),'Please enter a valid email')]");
	public static final By emptyPasswordError = By
			.xpath("//div[contains(text(),'Your password must contain between 4')]");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public LoginPage enterEmail(String email) {
		waitForVisible(emailField);
		emailField.clear();
		// Extra check when performing sql injection attempts
		emailField.sendKeys(Keys.CONTROL + "a");
		emailField.sendKeys(Keys.DELETE);

		emailField.sendKeys(email);
		return this;
	}

	public LoginPage enterPassword(String password) {
		waitForVisible(passwordField);
		// Extra check when performing sql injection attempts
		passwordField.sendKeys(Keys.CONTROL + "a");
		passwordField.sendKeys(Keys.DELETE);
		passwordField.sendKeys(password);
		return this;
	}

	public HomePage clickLogin() {
		waitForClickable(loginBtn);
		loginBtn.click();
		return new HomePage(driver).init();

	}

	public String getErrorMessage(By locator) {

		WaitUtils.waitForPresence(locator);
		System.out.println(driver.findElement(locator).getText());

		return driver.findElement(locator).getText();
	}

	public String extractTextFromScreenshot(WebDriver driver) throws IOException, InterruptedException, AWTException {
		try {
			// Take screenshot
			Thread.sleep(10000);
			Robot robot = new Robot();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenCapture = robot.createScreenCapture(screenRect);
			ImageIO.write(screenCapture, "png", new File("C:\\Screenshots\\page.png"));
			BufferedImage gray = new BufferedImage(screenCapture.getWidth(), screenCapture.getHeight(),
					BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = gray.getGraphics();
			g.drawImage(screenCapture, 0, 0, null);
			g.dispose();

			int x = 323, y = 263, width = 417, height = 58;

			BufferedImage croppedImg = screenCapture.getSubimage(x, y, width, height);

			ITesseract tesseract = new Tesseract();
			// Set language (optional, defaults to English)
			tesseract.setDatapath("C:\\Users\\Gaurav\\Tesseract-OCR");
			tesseract.setLanguage("eng");

			// Perform OCR
			String result = tesseract.doOCR(croppedImg);
			System.out.println("Extracted text: " + result);
			return result;

		} catch (TesseractException e) {
			System.err.println("OCR failed: " + e.getMessage());
			return null;
		}
	}

}
