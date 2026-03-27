package com.modena.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    
    private WebDriver driver;
    private String screenshotPath;
    private SimpleDateFormat dateFormat;
    
    public ScreenshotUtil(WebDriver driver) {
        this.driver = driver;
        // ✅ Gunakan ConfigReader untuk mendapatkan path
        this.screenshotPath = ConfigReader.getInstance().getScreenshotPath();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        
        // Create screenshot directory if not exists
        File directory = new File(screenshotPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
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
    
    public byte[] takeScreenshotAsBytes() {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public String captureOnFailure(String testName, Throwable throwable) {
        String fileName = generateFileName(testName + "_FAILED");
        String filePath = screenshotPath + fileName;
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            System.err.println("Screenshot captured on failure: " + filePath);
            return filePath;
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot on failure: " + e.getMessage());
            return null;
        }
    }
    
    private String generateFileName(String testName) {
        String timestamp = dateFormat.format(new Date());
        String sanitizedName = testName.replaceAll("[^a-zA-Z0-9]", "_");
        return sanitizedName + "_" + timestamp + ".png";
    }
}