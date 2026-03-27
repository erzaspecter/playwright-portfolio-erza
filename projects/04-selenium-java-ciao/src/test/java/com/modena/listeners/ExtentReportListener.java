package com.modena.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.modena.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;  // ✅ Import ditambahkan

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportListener implements ITestListener {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static Map<String, ExtentTest> testMap = new HashMap<>();
    private static String reportPath;
    private static String timestamp;
    
    @Override
    public void onStart(ITestContext context) {
        timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        reportPath = "reports/extent-reports/ExtentReport_" + timestamp + ".html";
        
        extent = new ExtentReports();
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("CIAO Modena Automation Report");
        sparkReporter.config().setReportName("Selenium Test Execution Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        sparkReporter.config().setEncoding("UTF-8");
        
        extent.attachReporter(sparkReporter);
        
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", System.getProperty("env", "Development"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "Chrome"));
        extent.setSystemInfo("Test Suite", context.getSuite().getName());
        
        System.out.println("📊 Extent Report initialized: " + reportPath);
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        String className = result.getTestClass().getName();
        String description = result.getMethod().getDescription();
        
        ExtentTest test = extent.createTest(testName);
        if (description != null && !description.isEmpty()) {
            test.assignCategory(className);
            test.info("Description: " + description);
        } else {
            test.assignCategory(className);
        }
        
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            StringBuilder params = new StringBuilder();
            for (Object param : parameters) {
                params.append(param.toString()).append(", ");
            }
            test.info("Test Parameters: " + params.toString());
        }
        
        extentTest.set(test);
        testMap.put(testName, test);
        
        test.log(Status.INFO, "🚀 Test started at: " + new Date());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        ExtentTest test = testMap.get(testName);
        
        if (test != null) {
            long duration = result.getEndMillis() - result.getStartMillis();
            test.log(Status.PASS, MarkupHelper.createLabel("✅ TEST PASSED", ExtentColor.GREEN));
            test.log(Status.INFO, "Duration: " + duration + " ms");
            test.log(Status.INFO, "Test completed successfully at: " + new Date());
            addLogsToReport(test, result);
        }
        
        extentTest.remove();
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        ExtentTest test = testMap.get(testName);
        
        if (test != null) {
            long duration = result.getEndMillis() - result.getStartMillis();
            Throwable throwable = result.getThrowable();
            
            test.log(Status.FAIL, MarkupHelper.createLabel("❌ TEST FAILED", ExtentColor.RED));
            test.log(Status.INFO, "Duration: " + duration + " ms");
            test.log(Status.INFO, "Failed at: " + new Date());
            test.log(Status.FAIL, "Error: " + throwable.getMessage());
            
            String stackTrace = getStackTraceAsString(throwable);
            test.log(Status.FAIL, "Stack Trace:\n" + stackTrace);
            
            attachScreenshotOnFailure(result, test);
            addLogsToReport(test, result);
        }
        
        extentTest.remove();
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        ExtentTest test = testMap.get(testName);
        
        if (test != null) {
            // ✅ Perbaikan: gunakan getThrowable() bukan getSkipReason()
            Throwable throwable = result.getThrowable();
            String skipReason = (throwable != null) ? throwable.getMessage() : "Test was skipped";
            
            test.log(Status.SKIP, MarkupHelper.createLabel("⏭️ TEST SKIPPED", ExtentColor.YELLOW));
            if (skipReason != null && !skipReason.isEmpty()) {
                test.log(Status.SKIP, "Reason: " + skipReason);
            }
        }
        
        extentTest.remove();
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getName();
        ExtentTest test = testMap.get(testName);
        
        if (test != null) {
            test.log(Status.WARNING, MarkupHelper.createLabel("⚠️ PARTIALLY FAILED", ExtentColor.ORANGE));
            test.log(Status.WARNING, "Test failed but within success percentage");
        }
    }
    
    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
            System.out.println("📊 Extent Report generated: " + reportPath);
            System.out.println("   Open in browser: " + new File(reportPath).getAbsolutePath());
        }
    }
    
    private void attachScreenshotOnFailure(ITestResult result, ExtentTest test) {
        try {
            Object testClass = result.getInstance();
            WebDriver driver = getDriverFromTestClass(testClass);
            
            if (driver != null) {
                ScreenshotUtil screenshotUtil = new ScreenshotUtil(driver);
                byte[] screenshotBytes = screenshotUtil.takeScreenshotAsBytes();
                
                if (screenshotBytes != null) {
                    test.addScreenCaptureFromBase64String(
                        java.util.Base64.getEncoder().encodeToString(screenshotBytes),
                        "Screenshot on failure"
                    );
                    test.log(Status.FAIL, "📸 Screenshot captured on failure");
                }
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    private void addLogsToReport(ExtentTest test, ITestResult result) {
        // ✅ Reporter sudah di-import
        java.util.List<String> logs = Reporter.getOutput(result);
        if (!logs.isEmpty()) {
            test.log(Status.INFO, "Test Logs:");
            for (String log : logs) {
                test.log(Status.INFO, "  - " + log);
            }
        }
    }
    
    private String getStackTraceAsString(Throwable throwable) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
    
    private WebDriver getDriverFromTestClass(Object testClass) {
        try {
            java.lang.reflect.Field driverField = testClass.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(testClass);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                java.lang.reflect.Field driverField = testClass.getClass().getDeclaredField("driver");
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(testClass);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                return null;
            }
        }
    }
    
    public static ExtentReports getExtentReports() {
        return extent;
    }
    
    public static ExtentTest getExtentTest(String testName) {
        return testMap.get(testName);
    }
}