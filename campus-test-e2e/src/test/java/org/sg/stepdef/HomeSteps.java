package org.sg.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sg.util.BaseDriver;
import org.sg.util.Common;
import org.sg.util.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;


public class HomeSteps extends BaseDriver {

    private static final Logger logger = LoggerFactory.getLogger(HomeSteps.class);

    public static final By XPATH_TITLE = By.xpath("//div[@id='headerDiv']/h3[normalize-space()='Campus']");
    public static final By XPATH_MENU = By.xpath("//form[@id='j_idt22']");
    public static final By XPATH_MENU_ITEMS = By.xpath("//form[@id='j_idt22']/child::a");
    public static final By XPATH_SEARCH_FORM = By.xpath("//form[@id='searchStudentForm']");
    public static final By XPATH_SEARCH_FORM_INPUT_NAME = By.xpath("//input[@id='searchStudentForm:name']");
    public static final By XPATH_SEARCH_FORM_BUTTON_SEARCH = By.xpath("//input[@id='searchStudentForm:j_idt45']");
    public static final By XPATH_ACTION_ADD = By.xpath("//a[@id='studentListForm:linkAddStudent']");
    public static final By XPATH_RESULT_FORM = By.xpath("//form[@id='studentListForm']");

    @Given("^navigate to \"([^\"]*)\" application")
    public void invokeApp(String appName) {
        Common.startApp(WebDriverFactory.getInstance().getWebdriver(), appName);
        logger.debug("Navigated to {} application", appName);
    }

    @Then("Home page is displayed")
    public void homePageDisplayed() {
        WebElement ele = Common.waitForElementTobePresent(driver, XPATH_TITLE);
        Assert.assertNotNull(ele, "Element " + XPATH_TITLE + " not found ");

        WebElement MENU = Common.waitForElementTobePresent(driver, XPATH_MENU);
        Assert.assertNotNull(MENU, "Element " + XPATH_MENU + " not found ");

        WebElement MENU_ITEMS = Common.waitForElementTobePresent(driver, XPATH_MENU_ITEMS);
        Assert.assertEquals(MENU_ITEMS.getText(), "Gestisci Studenti", "no same");
        logger.debug("All elements found in Homepage");
    }

    @When("Search student by name")
    public void searchStudentByName() {
        logger.debug("searchStudentByName");
    }

    @Then("Empty search result")
    public void emptySearchResult() {
        logger.debug("emptySearchResult");
    }

    @Then("Close the browser")
    public void closeTheBrowser() {
//        WebDriverFactory.getInstance().getWebdriver().quit();
    }

}