package org.sg.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.sg.util.BaseDriver;
import org.sg.util.WebDriverFactory;

public class HomeSteps extends BaseDriver {

    @Given("today is Sunday")
    public void invokeApp(String appType) {
        Steplib.startApp(WebDriverFactory.getInstance().getWebdriver(), appType);
    }

    @When("I ask")
    public void iAsk() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("close the browser")
    public void closeTheBrowser() {
        WebDriverFactory.getInstance().getWebdriver().quit();
    }


}