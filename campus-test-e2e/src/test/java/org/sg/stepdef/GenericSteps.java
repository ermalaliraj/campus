package org.sg.stepdef;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.sg.util.E2eUtil;
import org.sg.util.TestParameters;
import org.sg.util.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;

public class GenericSteps {

    private static final Logger logger = LoggerFactory.getLogger(GenericSteps.class);

    @Before
    public void startScenario(Scenario scenario) {
        logger.debug("Start Scenario");
        TestParameters.getInstance().setScenario(scenario);
        WebDriverFactory.getInstance().setWebDriver();
    }

    @After
    public void closeScenario() throws IOException {
        if (TestParameters.getInstance().getScenario().isFailed()) {
            logger.debug("Closing Scenario with FAILURES");
            E2eUtil.takeSnapShot(WebDriverFactory.getInstance().getWebdriver(), "FAIL");
            Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
            TestParameters.getInstance().getScenario().log("Click below for Screenshot");

            byte[] bytes = FileUtils.readFileToByteArray(new File(TestParameters.getInstance().getScreenshotPath()));
            TestParameters.getInstance().getScenario().attach(bytes, "image/png", "ErrorScreenshot");
            if (null != WebDriverFactory.getInstance().getWebdriver()) {
                WebDriverFactory.getInstance().getWebdriver().quit();
                TestParameters.getInstance().getScenario().log("Close Browser");
            }
        } else {
            logger.debug("Closing Scenario with SUCCESS");
            if (null != WebDriverFactory.getInstance().getWebdriver()) {
                WebDriverFactory.getInstance().getWebdriver().quit();
                TestParameters.getInstance().getScenario().log("Close Browser");
            }

        }
        TestParameters.getInstance().reset();
    }
}
