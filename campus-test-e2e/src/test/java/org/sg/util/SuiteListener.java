package org.sg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

    private static final Logger logger = LoggerFactory.getLogger(SuiteListener.class);

    @Override
    public void onStart(ISuite suite) {
        logger.debug("SuiteListener started.");
        String browser = suite.getParameter("browser");
        String environment = suite.getParameter("environment");
        String mode = suite.getParameter("mode");
        TestParameters.getInstance().setEnvironment(environment);
        TestParameters.getInstance().setBrowser(browser);
        TestParameters.getInstance().setMode(mode);
        logger.debug("ENV parameters browser={}, environment={}, mode={}", browser, environment, mode);
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.debug("SuiteListener finished");
    }
}