package com.netflix.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.netflix.base.BaseTest;
import com.netflix.utils.WaitUtils;

public class VideoDetailsPage extends BaseTest<VideoDetailsPage>  {
	 @FindBy(css = ".play-button")
	    private WebElement playButton;

	    public VideoDetailsPage play() {
	    	WaitUtils.waitForVisibility(playButton); // changed here
	        playButton.click();
	        return this;
	    }
}
