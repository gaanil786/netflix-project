package com.netflix.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.netflix.base.BaseTest;

public class VideoDetailsPage extends BaseTest<VideoDetailsPage>  {
	 @FindBy(css = ".play-button")
	    private WebElement playButton;

	    public VideoDetailsPage play() {
	        waitForClickable(playButton);
	        playButton.click();
	        return this;
	    }
}
