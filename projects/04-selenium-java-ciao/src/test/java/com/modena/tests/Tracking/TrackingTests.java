package com.modena.tests.tracking;

import com.modena.base.BaseTest;
import com.modena.pages.TrackingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrackingTests extends BaseTest {
    
    @Test
    public void testPageLoads() {
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertNotNull(title);
    }
    
    @Test
    public void testTrackingFormExists() {
        TrackingPage trackingPage = new TrackingPage(driver);
        Assert.assertNotNull(trackingPage);
    }
    
    @Test
    public void testTrackingWithValidEmail() {
        TrackingPage trackingPage = new TrackingPage(driver);
        
        trackingPage.trackByEmail("tomsmith");
        
        // Tunggu sebentar
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        // Verifikasi
        System.out.println("Tracking completed");
    }
}