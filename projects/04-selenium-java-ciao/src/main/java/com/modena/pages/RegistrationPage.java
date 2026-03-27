// src/test/java/com/erza/base/BaseTest.java
package com.erza.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    // WebDriver ini akan dipakai oleh semua subclass
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Logika untuk inisialisasi driver (misal: Chrome)
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    // @AfterMethod
    // public void tearDown() {
    //     // Tutup browser setelah selesai setiap test
    //     if (driver != null) {
    //         driver.quit();
    //     }
    // }
}