package org.sg.util;


import io.cucumber.java.Scenario;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class TestParameters {

    @Getter(lazy = true)
    private static final TestParameters instance = new TestParameters();
    private String screenshotPath;
    private String environment;
    private String browser;
    private String mode;
    private XSSFWorkbook testDataFile;
    private Scenario scenario;

    public void reset() {
        screenshotPath = null;
        scenario = null;
    }
}