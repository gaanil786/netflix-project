package com.netflix.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.netflix.base.BaseTest;
import com.netflix.pages.component.SearchBar;
import com.netflix.utils.WaitUtils;

public class HomePage extends BaseTest<HomePage> {
	@FindBy(css = "button.searchIcon")
	private WebElement searchButton;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public HomePage openSearch() {
		WaitUtils.waitForClickable(searchButton); // imported waitUtils
		searchButton.click();
		return this;
	}

	public SearchBar searchBar() {
		return new SearchBar().init();
	}
}
