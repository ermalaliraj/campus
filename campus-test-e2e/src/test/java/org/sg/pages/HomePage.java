package org.sg.pages;

import org.openqa.selenium.By;

public class HomePage {
    public static final By PREFACE = By.xpath("//*[text()='Preface']");
    public static final By BODY = By.xpath("//*[text()='Body']");
    public static final By TOC_CANCEL_BUTTON = By.xpath("//img[contains(@src,'toc-cancel.png')]");
    public static final By TOC_SAVE_BUTTON = By.xpath("//img[contains(@src,'toc-save.png')]");
    public static final By TOC_SAVE_AND_CLOSE_BUTTON = By.xpath("//img[contains(@src,'toc-save-close.png')]");
    public static final By SHOW_ALL_ACTIONS = By.xpath("//*[@title='Show all actions' and @style='display: inline-block;']");
    public static final By SHOW_ALL_ACTIONS_INSERT_BEFORE = By.xpath("//*[@data-widget-type='insert.before' and @style='display: inline-block;']");
    public static final By SHOW_ALL_ACTIONS_INSERT_AFTER = By.xpath("//*[@data-widget-type='insert.after' and @style='transform: rotate(180deg); display: inline-block;']");
    public static final By SHOW_ALL_ACTIONS_EDIT = By.xpath("//*[@data-widget-type='edit' and @style='display: inline-block;']");
    public static final By SHOW_ALL_ACTIONS_DELETE = By.xpath("//*[@data-widget-type='delete' and @style='display: inline-block;']");
    public static final By CLOSE_BUTTON = By.xpath("//span[text()='Close']");
    public static final By ANNEX_DELETION_BUTTON = By.xpath("//*[text()='Annex deletion: confirmation']//ancestor::div[@class='popupContent']//*[text()='Delete']");

    public static final String SHOW_ALL_ACTIONS_ICON = "//following-sibling::div[1][@class='leos-actions Vaadin-Icons']/span[@title='Show all actions']";
    public static final String TOC_TABLE_TR = "//table[@role='treegrid']//tbody//tr";
    public static final String LEVEL = "//level";
    public static final String AKNP = "//aknp";
    public static final String XPATH_TEXT_1 = "//*[text()='";
    public static final String XPATH_TEXT_2 = "']";

    public static final By REPOSITORY_BROWSER_TEXT = By.xpath("//*[text()='Repository Browser']");
}