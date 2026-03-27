// src/test/java/com/erza/tests/LoginTest.java
package com.erza.tests;

import com.erza.base.BaseTest;
import com.erza.pages.LoginPage;
import com.erza.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

// Mewarisi (Inherits) dari BaseTest (untuk otomatis buka/tutup browser)
public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccessfully() {
        // 1. Inisialisasi Page Object
        LoginPage loginPage = new LoginPage(driver);

        // 2. Jalankan Logika Bisnis (Flow Tes)
        // Kita login dan hasilnya adalah kita berada di HomePage
        HomePage homePage = loginPage.loginApplication("tomsmith", "SuperSecretPassword!");
        // 3. Assertions (Validasi)
        // Pastikan kita sudah masuk ke HomePage
        Assert.assertTrue(homePage.isProfileButtonDisplayed());
        String welcomeText = homePage.getWelcomeText();
        Assert.assertTrue(welcomeText.contains("You logged into a secure area!"));    }

    @Test
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);

        // Menjalankan flow login gagal
        loginPage.loginApplication("erza.akbar", "password_salah");

        // Validasi pesan error
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Your username is invalid!"), 
            "Pesan error tidak sesuai! Munculnya: " + error);    }
}