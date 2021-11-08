package org.sg.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Configuration {

    public static final int BUFFER_SIZE = 4096;
    public static final String FILE_SEPARATOR = "\\";
    public static final String SCREENSHOT = "target/results/Screenshots/Screenshot_" + getTimeStamp() + ".PNG";
    public static final String DRIVER_PATH = "resources/chromedriver2.exe";

//    public static final String SELENIUM_PROPERTIES = "selenium.properties";
//    public static final String USERS_PROPERTIES = "users.properties";
//    public static final String FILTERS = "filters";
//    public static final String DRIVER_PATH = "drivers";
//    public static final String CHROMEDRIVER = "chromedriver.exe";
//    public static final String ENV = "env";
//    public static final String SLASH = "/";
//    public static final String TEST_RESOURCES_LOCATION = "src/main/resources";
//    public static final String DATATABLE_LOCATION = TEST_RESOURCES_LOCATION + "/datatables";
//    public static final String RESULTS_LOCATION = "target/results";

    private static final String MAIN_CONFIG_FILE_PATH = "filters/selenium.properties";
    private static final String USERS_FILE_PATH = "filters/users.properties";
    private static final String ENVIRONMENT_CONFIG_FILE_PATH = "filters/env/" + TestParameters.getInstance().getEnvironment() + ".properties";

    private final Properties properties;

    public Configuration() {
        File configBaseFile = new File(this.getClass().getClassLoader().getResource(MAIN_CONFIG_FILE_PATH).getFile());
        File configUserFile = new File(this.getClass().getClassLoader().getResource(USERS_FILE_PATH).getFile());
        File environmentConfigFile = new File(this.getClass().getClassLoader().getResource(ENVIRONMENT_CONFIG_FILE_PATH).getFile());
        try {
            properties = new Properties();
            properties.load(new FileReader(configBaseFile));
            properties.load(new FileReader(configUserFile));
            properties.load(new FileReader(environmentConfigFile));
        } catch (IOException e) {
            throw new RuntimeException("The config file format is not as expected", e);
        }
    }

    public String getProperty(String value) {
        String propertyValue = getProperty(value, null);
        if (propertyValue != null) {
            return propertyValue;
        } else {
            throw new RuntimeException(value + " not specified in the selenium.properties file.");
        }
    }

    public String getProperty(String value, String defaultValue) {
        return properties.getProperty(value, defaultValue);
    }

    public static String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}