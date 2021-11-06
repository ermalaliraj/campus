package org.sg.stepdef;

import org.openqa.selenium.WebDriver;
import org.sg.util.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Steplib {
    private static final Logger logger = LoggerFactory.getLogger(Steplib.class);

    private static final Configuration config = new Configuration();

    public static void startApp(WebDriver driver, String applicationType) {
        String applicationURL = getAppUrl(applicationType);
        if (!driver.getCurrentUrl().trim().contains(applicationURL)) {
            driver.get(applicationURL);
        }
    }

    private static String getAppUrl(String applicationType) {
        String appUrl = "";
        if (applicationType.equalsIgnoreCase("Commission")) {
            appUrl = config.getProperty("edit.appUrl.ec");
        }
        if (applicationType.equalsIgnoreCase("Council")) {
            appUrl = config.getProperty("edit.appUrl.cn");
        }
        logger.info("Open application {} url {}", applicationType, appUrl);
        return appUrl;
    }
}
