package com.erza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // Locators untuk Herokuapp Login
    private By usernameField = By.id("username"); // Pakai 'username' (tanpa dash)
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.id("flash"); // Area pesan error/sukses di atas

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage loginApplication(String user, String pass) {
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginButton).click();
        return new HomePage(driver);
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}