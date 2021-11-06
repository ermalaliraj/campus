package org.sg.util;

import org.openqa.selenium.WebDriver;

public class BaseDriver {
    public WebDriver driver = WebDriverFactory.getInstance().getWebdriver();
    public static Configuration config = new Configuration();
}
