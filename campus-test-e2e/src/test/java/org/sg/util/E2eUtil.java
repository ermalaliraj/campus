package org.sg.util;

import com.google.common.io.Files;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.sg.util.Configuration.BUFFER_SIZE;

@Slf4j
@UtilityClass
@SuppressWarnings({"squid:S2925"})
public class E2eUtil {

    private static final Logger logger = LoggerFactory.getLogger(E2eUtil.class);

    private final Configuration config = new Configuration();
    private final Cryptor td = new Cryptor();

    // To get the timestamp for unique id creation of dossier or other
//    public String getTimeStamp() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();
//        return dateFormat.format(date);
//    }

    public void scrollandClick(WebDriver driver, By element) {
        try {
            WebElement webElement = driver.findElement(element);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", webElement);
            executor.executeScript("arguments[0].click();", webElement);
            logger.info("Element {} clicked", webElement);
        } catch (StaleElementReferenceException ignore) {
            logger.info("Exception occured while clicking element, but trying click again");
            WebElement webElement = driver.findElement(element);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", webElement);
            executor.executeScript("arguments[0].click();", webElement);
            logger.info("Exception occured while clicking element");
        } catch (Exception e) {
            logger.info("Exception occured while clicking element");
            throw e;
        }
    }

    // To take the screenshots of the application in case if validation needed at a particular screen
    // based on the configuration value provided the screenshot will be taking or else wont
    public void takeSnapShot(WebDriver driver, String status) {

        if (config.getProperty("takeScreenshots.pass").contains("TRUE") && status.contains("PASS")) {
            copyScreenshot(driver);
        }

        if (config.getProperty("takeScreenshots.fail").contains("TRUE") && status.contains("FAIL")) {
            copyScreenshot(driver);
        }
    }

    // support function to take screenshot
    private void copyScreenshot(WebDriver driver) {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        String fileName = Configuration.SCREENSHOT;
        File destFile = new File(fileName);
        boolean copyScreenshots = Boolean.parseBoolean(System.getProperty("copyScreenshots"));
        if (copyScreenshots) {
            TestParameters.getInstance().setScreenshotPath(fileName);
        } else {
            TestParameters.getInstance().setScreenshotPath(destFile.getAbsolutePath());
        }
        try {
            FileUtils.copyFile(srcFile, destFile);
            logger.info("Screenshot captured at " + destFile);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // Definite wait needed at multiple place, used a function instead Thread.sleep method
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void scrollandDoubleClick(WebDriver driver, By element) {
        try {
            WebElement webElement = driver.findElement(element);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", webElement);
            executor.executeScript("var evt = document.createEvent('MouseEvents'); evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);arguments[0].dispatchEvent(evt);", element);
            logger.info("Element {} double clicked", webElement);
            E2eUtil.takeSnapShot(driver, "PASS");
        } catch (Exception e) {
            logger.info("Exception occured while double clicking element");
            E2eUtil.takeSnapShot(driver, "FAIL");
        }
    }

    public DiskShare smbConnect() {
        DiskShare share;
        try {
            SmbConfig sconfig = SmbConfig.builder()
                    .withTimeout(120, TimeUnit.SECONDS) // Timeout sets Read, Write, and Transact timeouts (default is 60 seconds)
                    .withSoTimeout(180, TimeUnit.SECONDS) // Socket Timeout (default is 0 seconds, blocks forever)
                    .build();
            SMBClient client = new SMBClient(sconfig);
            Connection connection = client.connect(config.getProperty("remote.machine.name"));
            AuthenticationContext ac = new AuthenticationContext(config.getProperty("user.remote.1.name"), td.decrypt(config.getProperty("user.remote.1.pwd")).toCharArray(), config.getProperty("domain"));
            Session session = connection.authenticate(ac);
            share = (DiskShare) session.connectShare(config.getProperty("remote.drive.name"));
            return share;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean unzip(String zipFilePath, String destDirectory, DiskShare share) {
        try {
            ZipInputStream zipIn = null;
            logger.info("before share.folderExists(destDirectory)");
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                if (!share.folderExists(destDirectory)) {
                    share.mkdir(destDirectory);
                    logger.info("destDir is created");
                }
                com.hierynomus.smbj.share.File file = share.openFile(zipFilePath,
                        EnumSet.of(AccessMask.FILE_READ_DATA),
                        null,
                        SMB2ShareAccess.ALL,
                        SMB2CreateDisposition.FILE_OPEN,
                        null);
                InputStream inputStream = file.getInputStream();
                zipIn = new ZipInputStream(inputStream);
                logger.info("zipIn is assigned");
            }
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
                logger.info("zipIn is assigned");
            }
            ZipEntry entry = zipIn.getNextEntry();
            logger.info("entry is assigned");
            String filePath = null;
            while (entry != null) {
                logger.info("entry is not null");
                if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                    logger.info("entry :" + entry.getName());
                    filePath = destDirectory + File.separator + entry.getName();
                }
                if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                    filePath = destDirectory + File.separator + entry.getName();
                }
                logger.info("filePath " + filePath);
                if (!entry.isDirectory()) {
                    logger.info("entry.isDirectory()");
                    extractFile(zipIn, filePath, share);
                } else {
                    if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                        share.mkdir(filePath);
                    }
                    if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                        File dir = new File(filePath);
                        dir.mkdirs();
                    }
                }
                zipIn.closeEntry();
                logger.info("zipIn closeEntry");
                entry = zipIn.getNextEntry();
                logger.info("zipIn getNextEntry");
            }
            zipIn.close();
            logger.info("zipIn close");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void extractFile(ZipInputStream zipIn, String filePath, DiskShare share) throws IOException {
        logger.info("entry extractFile");
        BufferedOutputStream bos = null;
        if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
            com.hierynomus.smbj.share.File file = share.openFile(filePath
                    , EnumSet.of(AccessMask.GENERIC_ALL)
                    , null, SMB2ShareAccess.ALL
                    , SMB2CreateDisposition.FILE_OVERWRITE_IF
                    , null);
            OutputStream outStream = file.getOutputStream();
            bos = new BufferedOutputStream(outStream);
        }
        if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
        }
        if (null != bos) {
            logger.info("bos BufferedOutputStream");
            byte[] bytesIn = new byte[BUFFER_SIZE];
            logger.info("bytesIn BufferedOutputStream");
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                logger.info("zipIn.read");
                bos.write(bytesIn, 0, read);
                logger.info("bos bytesIn");
            }
            logger.info("while completed");
            bos.close();
            logger.info("bos.close()");
        }
    }

    public String findFile(String fileType, String relativePath, DiskShare diskShare) throws IOException {
        long lastModifiedTime = Long.MIN_VALUE;
        String chosenFileName = null;
        try {
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                if (null != diskShare) {
                    if (null != diskShare.list(config.getProperty("path.remote.download.relative") + "/" + relativePath, "*." + fileType)) {
                        for (FileIdBothDirectoryInformation f : diskShare.list(config.getProperty("path.remote.download.relative") + "/" + relativePath, "*." + fileType)) {
                            if (f.getLastAccessTime().getWindowsTimeStamp() > lastModifiedTime) {
                                chosenFileName = f.getFileName();
                                lastModifiedTime = f.getLastAccessTime().getWindowsTimeStamp();
                            }
                        }
                    } else {
                        Assert.fail("Issue while finding files from a directory in remote location");
                    }
                } else {
                    try {
                        DiskShare share = smbConnect();
                        if (null != share) {
                            if (null != share.list(config.getProperty("path.remote.download.relative") + "/" + relativePath, "*." + fileType)) {
                                for (FileIdBothDirectoryInformation f : share.list(config.getProperty("path.remote.download.relative") + "/" + relativePath, "*." + fileType)) {
                                    if (f.getLastAccessTime().getWindowsTimeStamp() > lastModifiedTime) {
                                        chosenFileName = f.getFileName();
                                        lastModifiedTime = f.getLastAccessTime().getWindowsTimeStamp();
                                    }
                                }
                            } else {
                                Assert.fail("Issue while finding files from a directory in remote location");
                            }
                        } else {
                            Assert.fail("DiskShare is coming null");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                File directory = new File(config.getProperty("path.local.download") + "/" + relativePath);
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String extension = Files.getFileExtension(file.getName());
                        if (extension.equalsIgnoreCase(fileType)) {
                            if (file.lastModified() > lastModifiedTime) {
                                chosenFileName = file.getName();
                                lastModifiedTime = file.lastModified();
                            }
                        }
                    }
                } else {
                    Assert.fail("Issue while finding files from a directory in local path");
                }
            }
            if (null != chosenFileName) {
                String FilePath = "";
                if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                    FilePath = config.getProperty("path.remote.download") + File.separator + relativePath + "/" + chosenFileName;
                }
                if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                    FilePath = config.getProperty("path.local.download") + File.separator + relativePath +"/"+ chosenFileName;
                }
                logger.info("FilePath " + FilePath);
                return FilePath;
            }
        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            throw e;
        }
        if (null != diskShare) {
            diskShare.close();
        }
        return null;
    }

    public void findAndUnzipFile(String fileType, String relativePath, String searchFileType) throws IOException {
        long lastModifiedTime = Long.MIN_VALUE;
        String chosenFileName = null;
        DiskShare share = null;
        try {
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                try {
                    share = smbConnect();
                    if (null != share) {
                        logger.info("share is not null");
                        if (null != share.list(config.getProperty("path.remote.download.relative"), "*." + fileType)) {
                            logger.info("share.list is not null");
                            for (FileIdBothDirectoryInformation f : share.list(config.getProperty("path.remote.download.relative"), "*." + fileType)) {
                                logger.info("FileIdBothDirectoryInformation has value");
                                if (f.getLastAccessTime().getWindowsTimeStamp() > lastModifiedTime) {
                                    logger.info("lastModifiedTime is less");
                                    chosenFileName = f.getFileName();
                                    lastModifiedTime = f.getLastAccessTime().getWindowsTimeStamp();
                                }
                            }
                        } else {
                            Assert.fail("Issue while finding files from a directory in remote location");
                        }
                    } else {
                        Assert.fail("DiskShare is coming null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                File directory = new File(config.getProperty("path.local.download"));
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String extension = Files.getFileExtension(file.getName());
                        if (extension.equalsIgnoreCase(fileType)) {
                            if (file.lastModified() > lastModifiedTime) {
                                chosenFileName = file.getName();
                                lastModifiedTime = file.lastModified();
                            }
                        }
                    }
                } else {
                    Assert.fail("Issue while finding files from a directory in local path");
                }
            }
        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            throw e;
        }
        if (null != chosenFileName) {
            logger.info("chosenFileName is not null");
            String zipFilePath = "";
            String destDirectory = "";
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                zipFilePath = config.getProperty("path.remote.download") + Configuration.FILE_SEPARATOR + chosenFileName;
                destDirectory = config.getProperty("path.remote.download") + Configuration.FILE_SEPARATOR + relativePath;
            }
            if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                zipFilePath = config.getProperty("path.local.download") + Configuration.FILE_SEPARATOR + chosenFileName;
                destDirectory = config.getProperty("path.local.download") + Configuration.FILE_SEPARATOR + relativePath;
            }
            logger.info("zipFilePath " + zipFilePath);
            logger.info("destDirectory " + destDirectory);
            try {
                boolean bool = false;
                if (TestParameters.getInstance().getMode().equalsIgnoreCase("remote")) {
                    bool = unzip(config.getProperty("path.remote.download.relative") + Configuration.FILE_SEPARATOR + chosenFileName, config.getProperty("path.remote.download.relative") + Configuration.FILE_SEPARATOR + relativePath, share);
                }
                if (TestParameters.getInstance().getMode().equalsIgnoreCase("local")) {
                    bool = unzip(zipFilePath, destDirectory, null);
                }
                if (!bool) {
                    Assert.fail("Error while unzipping file");
                } else {
                    String FileNamePath = E2eUtil.findFile(searchFileType, relativePath, share);
                    if (null != FileNamePath) {
                        System.out.println("legFileNamePath " + FileNamePath);
                    } else {
                        Assert.fail("Unable to find the " + fileType + " file");
                    }
                }
            } catch (Exception | AssertionError ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        if (null != share) {
            share.close();
        }
    }

    public void highlightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('style', 'border:3px solid yellow;')", element);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsExecutor.executeScript("arguments[0].setAttribute('style', 'border:;')", element);
    }
}