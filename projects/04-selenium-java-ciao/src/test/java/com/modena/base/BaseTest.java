package com.modena.base;

import com.modena.listeners.ExtentReportListener;
import com.modena.listeners.TestListener;
import com.modena.utils.ConfigReader;
import com.modena.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Listeners({TestListener.class, ExtentReportListener.class})
public class BaseTest {
    
    protected WebDriver driver;
    protected ConfigReader configReader;
    
    @BeforeSuite
    public void setUpSuite() {
        // Inisialisasi ConfigReader di BeforeSuite
        configReader = ConfigReader.getInstance();
        System.out.println("ConfigReader initialized");
    }
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        System.out.println("Starting test with browser: " + browser);
        
        // Pastikan configReader tidak null
        if (configReader == null) {
            configReader = ConfigReader.getInstance();
        }
        
        String browserToUse = (browser == null || browser.isEmpty()) ? "chrome" : browser;
        
        driver = WebDriverFactory.createDriver(browserToUse);
        driver.manage().window().maximize();
        
        // Ambil URL dari config
        String baseUrl = configReader.getBaseUrl();
        System.out.println("Navigating to: " + baseUrl);
        driver.get(baseUrl);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Browser closed successfully");
            } catch (Exception e) {
                System.out.println("Error closing browser: " + e.getMessage());
            }
        }
    }
}