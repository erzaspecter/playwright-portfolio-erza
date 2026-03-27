package com.modena.listeners;

import com.modena.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * TestNG Listener untuk menangani event selama test execution
 * - Mencatat start/finish test
 * - Screenshot saat test fail
 * - Logging test results
 */
public class TestListener implements ITestListener {
    
    private ScreenshotUtil screenshotUtil;
    private long startTime;
    private long endTime;
    
    @Override
    public void onTestStart(ITestResult result) {
        startTime = System.currentTimeMillis();
        String testName = result.getName();
        String className = result.getTestClass().getName();
        
        System.out.println("========================================");
        System.out.println("🚀 TEST STARTED: " + testName);
        System.out.println("   Class: " + className);
        System.out.println("   Time: " + new java.util.Date());
        System.out.println("========================================");
        
        // Log ke TestNG Reporter
        Reporter.log("Test started: " + testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String testName = result.getName();
        
        System.out.println("========================================");
        System.out.println("✅ TEST PASSED: " + testName);
        System.out.println("   Duration: " + duration + " ms");
        System.out.println("========================================");
        
        Reporter.log("Test passed: " + testName + " (Duration: " + duration + "ms)");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String testName = result.getName();
        Throwable throwable = result.getThrowable();
        
        System.out.println("========================================");
        System.out.println("❌ TEST FAILED: " + testName);
        System.out.println("   Duration: " + duration + " ms");
        System.out.println("   Error: " + throwable.getMessage());
        System.out.println("========================================");
        
        // Print stack trace
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        System.out.println(sw.toString());
        
        // Take screenshot on failure
        takeScreenshotOnFailure(result);
        
        // Log ke TestNG Reporter
        Reporter.log("Test failed: " + testName);
        Reporter.log("Error: " + throwable.getMessage());
    }
    
    
    @Override
public void onTestSkipped(ITestResult result) {
    String testName = result.getName();
    // ✅ Gunakan getThrowable() untuk TestNG 7.8.0
    Throwable throwable = result.getThrowable();
    String skipReason = (throwable != null) ? throwable.getMessage() : "Test was skipped";
    
    System.out.println("========================================");
    System.out.println("⏭️ TEST SKIPPED: " + testName);
    if (skipReason != null && !skipReason.isEmpty()) {
        System.out.println("   Reason: " + skipReason);
    }
    System.out.println("========================================");
    
    Reporter.log("Test skipped: " + testName);
}
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getName();
        System.out.println("⚠️ TEST FAILED BUT WITHIN SUCCESS PERCENTAGE: " + testName);
        Reporter.log("Test partially failed: " + testName);
    }
    
    @Override
    public void onStart(ITestContext context) {
        String suiteName = context.getSuite().getName();
        String testName = context.getName();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏁 TEST SUITE STARTED");
        System.out.println("   Suite: " + suiteName);
        System.out.println("   Test: " + testName);
        System.out.println("   Time: " + new java.util.Date());
        System.out.println("=".repeat(60) + "\n");
        
        Reporter.log("Suite started: " + suiteName);
    }
    
    @Override
    public void onFinish(ITestContext context) {
        String suiteName = context.getSuite().getName();
        int passedTests = context.getPassedTests().size();
        int failedTests = context.getFailedTests().size();
        int skippedTests = context.getSkippedTests().size();
        int totalTests = passedTests + failedTests + skippedTests;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏁 TEST SUITE FINISHED");
        System.out.println("   Suite: " + suiteName);
        System.out.println("   Total Tests: " + totalTests);
        System.out.println("   ✅ Passed: " + passedTests);
        System.out.println("   ❌ Failed: " + failedTests);
        System.out.println("   ⏭️ Skipped: " + skippedTests);
        System.out.println("   Time: " + new java.util.Date());
        System.out.println("=".repeat(60) + "\n");
        
        // Print summary
        if (failedTests > 0) {
            System.out.println("Failed tests:");
            context.getFailedTests().getAllResults().forEach(result -> {
                System.out.println("   - " + result.getName());
                System.out.println("     " + result.getThrowable().getMessage());
            });
        }
        
        Reporter.log("Suite finished: " + suiteName);
        Reporter.log("Summary - Passed: " + passedTests + ", Failed: " + failedTests + ", Skipped: " + skippedTests);
    }
    
    /**
     * Take screenshot when test fails
     */
    private void takeScreenshotOnFailure(ITestResult result) {
        try {
            // Get WebDriver instance from test class
            Object testClass = result.getInstance();
            WebDriver driver = getDriverFromTestClass(testClass);
            
            if (driver != null) {
                screenshotUtil = new ScreenshotUtil(driver);
                String testName = result.getName();
                String screenshotPath = screenshotUtil.captureOnFailure(testName, result.getThrowable());
                
                if (screenshotPath != null) {
                    System.out.println("📸 Screenshot saved: " + screenshotPath);
                    Reporter.log("Screenshot: " + screenshotPath);
                    
                    // Attach screenshot to TestNG report (HTML)
                    String htmlImage = "<img src='" + screenshotPath + "' height='200' width='300'/>";
                    Reporter.log(htmlImage);
                }
            } else {
                System.out.println("⚠️ Could not take screenshot: WebDriver not available");
            }
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Extract WebDriver from test class instance
     * Assumes test class has a 'driver' field
     */
    private WebDriver getDriverFromTestClass(Object testClass) {
        try {
            java.lang.reflect.Field driverField = testClass.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(testClass);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                // Try to get from current class if not in superclass
                java.lang.reflect.Field driverField = testClass.getClass().getDeclaredField("driver");
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(testClass);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                return null;
            }
        }
    }
}