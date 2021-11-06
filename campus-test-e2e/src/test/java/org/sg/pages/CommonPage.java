package org.sg.pages;

import org.openqa.selenium.By;

public class CommonPage {
    public static final By MIN_MAX_APP_HEADER_ICON = By.xpath("//*[@class='resize-button']//span[@class='v-icon Vaadin-Icons']");
    public static final By SUBTITLE_HEADER_ELEMENT = By.xpath("//*[@class='ec-header']/parent::div[contains(@style,'border-style: none; margin: 0px; padding: 0px; width: 100%; height: 48px;')]//div[@class='sub-title']");
    public static final By TITLE_HEADER_ELEMENT = By.xpath("//*[@class='ec-header']/parent::div[contains(@style,'border-style: none; margin: 0px; padding: 0px; width: 100%; height: 123px;')]//div[@class='title']");
    public static final By LOGOUT_BUTTON = By.xpath("//*[@class='logout-button']//*[@role='button']");
    public static final By LOADING_PROGRESS = By.xpath("//*[contains(@class,'v-loading-indicator') and @style='position: absolute; display: none;']");

    public static final String XPATH_TEXT_1 = "//*[text()='";
    public static final String XPATH_TEXT_2 = "']";
}
