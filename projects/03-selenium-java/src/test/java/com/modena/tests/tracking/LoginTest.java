package com.modena.tests.tracking;

import com.modena.base.BaseTest;
import com.modena.pages.LoginPage;
import com.modena.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccessfully() {
        // 1. Inisialisasi Page Object
        LoginPage loginPage = new LoginPage(driver);
        
        // 2. Jalankan login (INI YANG MISSING!)
        HomePage homePage = loginPage.loginApplication("tomsmith", "SuperSecretPassword!");
        
        // 3. Assertion: pastikan login berhasil (misal ada elemen welcome message)
        Assert.assertNotNull(homePage, "HomePage should not be null after login");
    }
}