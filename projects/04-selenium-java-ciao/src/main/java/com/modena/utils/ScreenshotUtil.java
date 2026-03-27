package com.modena.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * Utility class untuk mengambil screenshot
 * Mendukung full page screenshot dan element screenshot
 */
public class ScreenshotUtil {
    
    private WebDriver driver;
    private String screenshotPath;
    private SimpleDateFormat dateFormat;
    
    public ScreenshotUtil(WebDriver driver) {
        this.driver = driver;
        this.screenshotPath = ConfigReader.getInstance().getScreenshotPath();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        
        // Create screenshot directory if not exists
        File directory = new File(screenshotPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    /**
     * Mengambil screenshot seluruh halaman
     */
    public String takeScreenshot(String testName) {
        String fileName = generateFileName(testName);
        String filePath = screenshotPath + fileName;
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + filePath);
            return filePath;
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Mengambil screenshot dengan byte array (untuk Extent Reports)
     */
    public byte[] takeScreenshotAsBytes() {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Mengambil screenshot element tertentu
     */
    public String takeElementScreenshot(WebElement element, String testName) {
        String fileName = generateFileName(testName + "_element");
        String filePath = screenshotPath + fileName;
        
        try {
            File srcFile = element.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            return filePath;
        } catch (IOException e) {
            System.err.println("Failed to take element screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Mengambil screenshot full page (scroll capture)
     * Memerlukan Chrome DevTools Protocol untuk Selenium 4+
     */
    public String takeFullPageScreenshot(String testName) {
        String fileName = generateFileName(testName + "_fullpage");
        String filePath = screenshotPath + fileName;
        
        try {
            // Get full page height
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long pageHeight = (long) js.executeScript(
                "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);"
            );
            long windowHeight = (long) js.executeScript("return window.innerHeight;");
            
            // Scroll and capture
            js.executeScript("window.scrollTo(0, 0);");
            Thread.sleep(500);
            
            // Use Chrome DevTools Protocol for full page
            if (driver.getClass().getName().contains("ChromeDriver")) {
                return takeChromeFullPageScreenshot(fileName);
            } else {
                return takeScreenshot(testName); // Fallback to normal screenshot
            }
            
        } catch (Exception e) {
            System.err.println("Failed to take full page screenshot: " + e.getMessage());
            return takeScreenshot(testName);
        }
    }
    
    /**
     * Chrome DevTools Protocol full page screenshot
     */
    private String takeChromeFullPageScreenshot(String fileName) {
        // Implement with CDP if needed
        return takeScreenshot(fileName);
    }
    
    /**
     * Generate unique filename dengan timestamp
     */
    private String generateFileName(String testName) {
        String timestamp = dateFormat.format(new Date());
        String sanitizedName = testName.replaceAll("[^a-zA-Z0-9]", "_");
        return sanitizedName + "_" + timestamp + ".png";
    }
    
    /**
     * Screenshot pada saat test failed (auto capture)
     */
    public String captureOnFailure(String testName, Throwable throwable) {
        String fileName = generateFileName(testName + "_FAILED");
        String filePath = screenshotPath + fileName;
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            
            System.err.println("Screenshot captured on failure: " + filePath);
            System.err.println("Error: " + throwable.getMessage());
            
            return filePath;
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot on failure: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Clean old screenshots (lebih dari X hari)
     */
    public void cleanOldScreenshots(int daysOld) {
        File directory = new File(screenshotPath);
        if (!directory.exists()) return;
        
        long threshold = System.currentTimeMillis() - (daysOld * 24L * 60L * 60L * 1000L);
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.lastModified() < threshold) {
                    file.delete();
                }
            }
        }
    }
}