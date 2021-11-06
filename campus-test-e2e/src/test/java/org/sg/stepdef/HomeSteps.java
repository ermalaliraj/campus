package org.sg.stepdef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sg.pages.CommonPage;
import org.sg.pages.HomePage;
import org.sg.util.BaseDriver;
import org.sg.util.Common;
import org.sg.util.WebDriverFactory;
import org.testng.Assert;

public class HomeSteps extends BaseDriver {
    @Then("{string} Annex page is displayed")
    public void annexPageIsDisplayed(String arg0) {
        WebElement ele = Common.waitForElementTobePresent(driver, CommonPage.LOADING_PROGRESS);
        if (null != ele) {
            boolean bool = Common.verifyElement(driver, By.xpath(HomePage.XPATH_TEXT_1 + arg0 + HomePage.XPATH_TEXT_2));
            Assert.assertTrue(bool, arg0 + " Annex page is not displayed");
        } else {
            Assert.fail("unable to load the page in the specified time duration");
        }
    }

    @Then("^navigate to Repository Browser page$")
    public void NavigateToRepositoryBrowserPage() {
        WebElement ele = Common.waitForElementTobePresent(driver, CommonPage.LOADING_PROGRESS);
        if (null != ele) {
            Common.verifyElement(driver, HomePage.REPOSITORY_BROWSER_TEXT);
        } else {
            Assert.fail("unable to load the page in the specified time duration");
        }
    }


    @When("^navigate to ([^\"]*) edit application$")
    @Given("^navigate to \"([^\"]*)\" edit application$")
    public void invokeApp(String appType) {
        Steplib.startApp(WebDriverFactory.getInstance().getWebdriver(), appType);
    }

    @And("close the browser")
    public void quitTheBrowser() {
        WebDriverFactory.getInstance().getWebdriver().quit();
    }


}