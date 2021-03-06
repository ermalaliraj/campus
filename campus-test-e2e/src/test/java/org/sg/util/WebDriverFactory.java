package org.sg.util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.sg.util.Common.TIME_OUT;

public class WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String IE = "internetexplorer";
    public static final String EDGE = "edge";
    private String testBrowser;
    private WebDriver driver;
    private static WebDriverFactory instance;
    private static final Configuration config = new Configuration();

    private WebDriverFactory() {
    }

    public static WebDriverFactory getInstance() {
        if (instance == null) {
            instance = new WebDriverFactory();
            instance.setTestBrowser(TestParameters.getInstance().getBrowser());
        }
        return instance;
    }

    public void setTestBrowser(String testBrowser) {
        this.testBrowser = testBrowser;
    }

    public WebDriver getWebdriver() {
        return driver;
    }

    public void setWebDriver() {
        String exeMode = TestParameters.getInstance().getMode();
        String gridUrl = config.getProperty("grid.url");
        switch (exeMode) {
            case "local":
                localDriver(testBrowser);
                return;
            case "remote":
                remoteDriver(testBrowser, gridUrl);
                return;
            default:
        }
    }

    public void remoteDriver(String browser, String gridUrl) {
        DesiredCapabilities capability;

        switch (browser) {
            case FIREFOX:
                try {
                    capability = DesiredCapabilities.firefox();
                    capability.setBrowserName(FIREFOX);
                    capability.setPlatform(Platform.ANY);
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                    driver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.manage().deleteAllCookies();
                } catch (MalformedURLException e) {
                    logger.error(e.getMessage(), e);
                }
                break;

            case IE:
                try {
                    capability = DesiredCapabilities.internetExplorer();
                    capability.setBrowserName("internet explorer");
                    capability.setPlatform(Platform.ANY);
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                    driver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.manage().deleteAllCookies();
                } catch (MalformedURLException e) {
                    logger.error(e.getMessage(), e);
                }
                break;

            case EDGE:
                try {
                    capability = DesiredCapabilities.edge();
                    capability.setBrowserName("MicrosoftEdge");
                    capability.setPlatform(Platform.ANY);
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                    driver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.manage().deleteAllCookies();
                } catch (MalformedURLException e) {
                    logger.error(e.getMessage(), e);
                }
                break;

            default:
                try {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("enable-automation");
                    options.addArguments("--disable-browser-side-navigation");
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("download.default_directory", config.getProperty("path.remote.download"));
                    options.setExperimentalOption("prefs", prefs);
                    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                    capability = DesiredCapabilities.chrome();
                    capability.setBrowserName(CHROME);
                    capability.setPlatform(Platform.ANY);
                    capability.setCapability("ignoreZoomSetting", true);
                    options.merge(capability);
                    driver = new RemoteWebDriver(new URL(gridUrl), options);
                    driver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.manage().deleteAllCookies();
                } catch (MalformedURLException e) {
                    logger.error(e.getMessage(), e);
                }
                break;
        }
    }

    public void localDriver(String browser) {
        DesiredCapabilities capability;

        switch (browser) {
            case FIREFOX: // If user choose Firefox driver has been changed to Firefox
                driver = new FirefoxDriver();
                break;
            case IE: // If user choose InternetExplorer driver has been changed to IE
                driver = new InternetExplorerDriver();
                break;
            case EDGE: // If user choose edge driver has been changed to EDGE
                driver = new EdgeDriver();
                break;
            default:// If user choose Chrome driver has been changed to Chrome
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("enable-automation");
                options.addArguments("--disable-browser-side-navigation");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", config.getProperty("path.local.download"));
                options.setExperimentalOption("prefs", prefs);
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                capability = DesiredCapabilities.chrome();
                capability.setBrowserName(CHROME);
                capability.setPlatform(Platform.ANY);
                capability.setCapability("ignoreZoomSetting", true);
                options.merge(capability);
                File configBaseFile = new File(Configuration.DRIVER_PATH);
                String absolutePath = configBaseFile.getAbsolutePath();
                System.setProperty("webdriver.chrome.driver", absolutePath);
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
                driver.manage().window().maximize();
        }
    }
}
