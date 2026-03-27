package com.modena.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrackingPage extends BasePage {
    
    // Perbaiki selector - gunakan By.xpath untuk text contains
    private final By trackButton = By.xpath("//button[contains(text(),'Lacak')]");
    private final By emailInput = By.cssSelector("input[type='email'], input[name='email']");
    private final By phoneInput = By.cssSelector("input[type='tel'], input[name='phone']");
    private final By trackingResult = By.cssSelector(".tracking-result, .status-card, .result");
    private final By errorMessage = By.cssSelector(".error-message, .alert-danger");
    
    public TrackingPage(WebDriver driver) {
        super(driver);
    }
    
    public TrackingPage enterEmail(String email) {
        waitUtil.sendKeysWhenReady(emailInput, email);
        return this;
    }
    
    public TrackingPage enterPhoneNumber(String phone) {
        waitUtil.sendKeysWhenReady(phoneInput, phone);
        return this;
    }
    
    public TrackingPage clickTrack() {
        waitUtil.clickWhenReady(trackButton);
        return this;
    }
    
    public TrackingPage trackByEmail(String email) {
        return enterEmail(email).clickTrack();
    }
    
    public TrackingPage trackByPhone(String phone) {
        return enterPhoneNumber(phone).clickTrack();
    }
    
    public boolean isTrackingResultDisplayed() {
        return waitUtil.isElementDisplayedWithWait(trackingResult);
    }
    
    public String getTrackingResult() {
        return waitUtil.getTextWhenReady(trackingResult);
    }
    
    public String getErrorMessage() {
        return waitUtil.getTextWhenReady(errorMessage);
    }
    
    public int getTrackingEntriesCount() {
        return driver.findElements(By.cssSelector("table tbody tr")).size();
    }
}