package com.netflix.pages.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.netflix.base.BaseTest;
import com.netflix.pages.SearchPage;
import com.netflix.utils.WaitUtils;

public class SearchBar extends BaseTest<SearchBar> {

	@FindBy(css = "input.searchInput")
	private WebElement searchInput;

	@FindBy(css = "button.searchSubmit")
	private WebElement searchSubmit;

	public SearchBar search(String keyword) {
		WaitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(keyword);
		searchSubmit.click();
		return this;
	}

	public SearchPage goToResults() {
		return new SearchPage().init();
	}
}
