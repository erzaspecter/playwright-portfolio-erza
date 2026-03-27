package com.modena.base;

import com.modena.listeners.ExtentReportListener;
import com.modena.listeners.TestListener;
import com.modena.utils.ConfigReader;
import com.modena.utils.ScreenshotUtil;
import com.modena.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners({TestListener.class, ExtentReportListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected ConfigReader configReader;
    protected ScreenshotUtil screenshotUtil;
    
    @BeforeSuite
    public void setUpSuite() {
        configReader = new ConfigReader();
    }
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        driver = WebDriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(configReader.getProperty("base.url"));
        screenshotUtil = new ScreenshotUtil(driver);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}