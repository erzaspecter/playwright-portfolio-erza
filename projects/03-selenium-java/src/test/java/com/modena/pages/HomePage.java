package com.modena.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Tambahkan method yang dipanggil di test
    public void goToTracking() {
        // implementasi navigasi ke halaman tracking
    }

    public boolean isTrackingVisible() {
        // implementasi pengecekan elemen tracking
        return true; // sementara
    }
}