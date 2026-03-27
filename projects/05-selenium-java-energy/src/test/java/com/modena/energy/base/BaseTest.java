package com.modena.energy.base;

import com.modena.energy.utils.ConfigReader;
import com.modena.energy.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {
    
    protected WebDriver driver;  // ← Ini yang digunakan oleh child class
    protected ConfigReader configReader;
    
    @BeforeSuite
    public void setUpSuite() {
        configReader = ConfigReader.getInstance();
        System.out.println("ConfigReader initialized");
    }
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        String browserToUse = (browser == null || browser.isEmpty()) ? "chrome" : browser;
        
        driver = WebDriverFactory.createDriver(browserToUse);
        driver.manage().window().maximize();
        
        String baseUrl = configReader.getBaseUrl();
        System.out.println("Navigating to: " + baseUrl);
        driver.get(baseUrl);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}