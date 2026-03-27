package com.modena.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.modena.utils.WaitUtil;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    protected void click(By locator) {
        waitForElementClickable(locator);
        findElement(locator).click();
    }
    
    protected void sendKeys(By locator, String text) {
        waitForElementVisible(locator);
        findElement(locator).clear();
        findElement(locator).sendKeys(text);
    }
    
    protected String getText(By locator) {
        waitForElementVisible(locator);
        return findElement(locator).getText();
    }
    
    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected void scrollToElement(By locator) {
        js.executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }
    
    protected void waitForPageLoad() {
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
    }
    
    private WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
    
    protected void uploadFile(By locator, String filePath) {
        findElement(locator).sendKeys(filePath);
    }
}