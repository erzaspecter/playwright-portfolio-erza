package com.modena.tests.tracking;

import com.modena.base.BaseTest;
import com.modena.pages.LoginPage;
import com.modena.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrackingTests extends BaseTest {

    @Test
    public void testTrackingFunctionality() {
        // Inisialisasi LoginPage
        LoginPage loginPage = new LoginPage(driver);
        
        // Login
        HomePage homePage = loginPage.loginApplication("tomsmith", "SuperSecretPassword!");
        
        // Verifikasi bahwa homePage tidak null
        Assert.assertNotNull(homePage, "HomePage should not be null after login");
        
        // Navigasi ke tracking (pastikan method ini ada)
        homePage.goToTracking();
        
        // Verifikasi tracking visible
        Assert.assertTrue(homePage.isTrackingVisible(), "Tracking page should be visible");
    }
}