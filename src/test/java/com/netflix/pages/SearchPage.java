package com.netflix.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.netflix.base.BaseTest;
import com.netflix.utils.WaitUtils;

public class SearchPage extends BaseTest<SearchPage> {
	@FindBy(css = ".title-card-container")
    private WebElement firstResult;

    public VideoDetailsPage openFirstResult() {
    	WaitUtils.waitForVisibility(firstResult); // changed here
        firstResult.click();
        return new VideoDetailsPage().init();
    }
}
