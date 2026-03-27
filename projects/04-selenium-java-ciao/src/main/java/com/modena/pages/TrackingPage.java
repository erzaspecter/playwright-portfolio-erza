package com.modena.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrackingPage extends BasePage {
    
    // Locators
    @FindBy(css = "input[placeholder*='e-mail'], input[type='email']")
    private WebElement emailInput;
    
    @FindBy(css = "input[placeholder*='hp'], input[type='tel']")
    private WebElement phoneInput;
    
    @FindBy(css = "button[type='submit'], button:contains('Lacak')")
    private WebElement trackButton;
    
    @FindBy(css = ".tracking-result, .status-card")
    private WebElement trackingResult;
    
    @FindBy(css = ".error-message, .alert-danger")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//table/tbody/tr")
    private java.util.List<WebElement> trackingRows;
    
    private final By trackingTable = By.cssSelector("table");
    
    public TrackingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public TrackingPage enterEmail(String email) {
        waitForElementVisible(By.cssSelector("input[placeholder*='e-mail']"));
        emailInput.sendKeys(email);
        return this;
    }
    
    public TrackingPage enterPhoneNumber(String phone) {
        phoneInput.sendKeys(phone);
        return this;
    }
    
    public TrackingPage clickTrack() {
        trackButton.click();
        waitForPageLoad();
        return this;
    }
    
    public String getTrackingResult() {
        waitForElementVisible(By.cssSelector(".tracking-result"));
        return trackingResult.getText();
    }
    
    public boolean isTrackingResultDisplayed() {
        try {
            return trackingResult.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        waitForElementVisible(By.cssSelector(".error-message"));
        return errorMessage.getText();
    }
    
    public int getTrackingEntriesCount() {
        waitForElementVisible(trackingTable);
        return trackingRows.size();
    }
    
    public void clearInputs() {
        emailInput.clear();
        phoneInput.clear();
    }
    
    public TrackingPage trackByEmail(String email) {
        return enterEmail(email).clickTrack();
    }
    
    public TrackingPage trackByPhone(String phone) {
        return enterPhoneNumber(phone).clickTrack();
    }
}