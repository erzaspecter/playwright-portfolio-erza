package com.erza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    // Di Herokuapp, kita verifikasi tombol logout dan pesan sukses
    private By logoutButton = By.cssSelector("a.button.secondary.radius");
    private By welcomeMessage = By.id("flash");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProfileButtonDisplayed() {
        // Kita anggap tombol Logout sebagai bukti sudah masuk (Profile Button)
        return driver.findElement(logoutButton).isDisplayed();
    }

    public String getWelcomeText() {
        // Herokuapp memberikan pesan "You logged into a secure area!"
        return driver.findElement(welcomeMessage).getText();
    }
}