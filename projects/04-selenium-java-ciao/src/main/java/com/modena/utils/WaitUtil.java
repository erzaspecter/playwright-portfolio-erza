package com.modena.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

/**
 * Utility class untuk menangani berbagai jenis wait di Selenium
 */
public class WaitUtil {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;
    private int defaultTimeout = 15;
    private int pollingInterval = 500;
    
    public WaitUtil(WebDriver driver) {
        this.driver = driver;
        this.defaultTimeout = ConfigReader.getInstance().getExplicitWait();
        initializeWaits();
    }
    
    public WaitUtil(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.defaultTimeout = timeoutInSeconds;
        initializeWaits();
    }
    
    private void initializeWaits() {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout));
        this.fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(defaultTimeout))
                .pollingEvery(Duration.ofMillis(pollingInterval))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }
    
    /**
     * Wait hingga element visible
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait hingga element clickable
     */
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait hingga element present di DOM
     */
    public WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait hingga element tidak visible
     */
    public boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Generic fluent wait dengan custom condition
     */
    public <V> V waitUntil(Function<? super WebDriver, V> condition) {
        return fluentWait.until(condition);
    }
    
    /**
     * Wait hingga page ready state complete
     */
    public void waitForPageLoad() {
        waitUntil(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String readyState = js.executeScript("return document.readyState").toString();
            return readyState.equals("complete");
        });
    }
    
    /**
     * Hard sleep (hanya untuk debugging)
     */
    public void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Click ketika element ready
     */
    public void clickWhenReady(By locator) {
        waitForElementClickable(locator).click();
    }
    
    /**
     * Send keys ketika element ready
     */
    public void sendKeysWhenReady(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text ketika element ready
     */
    public String getTextWhenReady(By locator) {
        return waitForElementVisible(locator).getText();
    }
    
    /**
     * Check if element displayed dengan wait
     */
    public boolean isElementDisplayedWithWait(By locator) {
        try {
            return waitForElementVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Refresh dan tunggu load
     */
    public void refreshAndWait() {
        driver.navigate().refresh();
        waitForPageLoad();
    }
}