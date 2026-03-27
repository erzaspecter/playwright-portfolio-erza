package com.modena.tests.Tracking;

import com.modena.base.BaseTest;
import com.modena.pages.TrackingPage;
import com.modena.utils.ExcelDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrackingTests extends BaseTest {
    
    @Test(description = "Valid tracking dengan email terdaftar")
    public void testTrackWithValidEmail() {
        TrackingPage trackingPage = new TrackingPage(driver);
        
        trackingPage
            .enterEmail("valid.email@modena.com")
            .clickTrack();
        
        Assert.assertTrue(trackingPage.isTrackingResultDisplayed(), 
            "Tracking result should be displayed");
        Assert.assertTrue(trackingPage.getTrackingEntriesCount() > 0, 
            "Should have at least 1 tracking entry");
    }
    
    @Test(description = "Tracking dengan email tidak terdaftar")
    public void testTrackWithInvalidEmail() {
        TrackingPage trackingPage = new TrackingPage(driver);
        
        trackingPage
            .enterEmail("unregistered@email.com")
            .clickTrack();
        
        String errorMsg = trackingPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("tidak ditemukan") || 
                          errorMsg.contains("not found"),
                          "Should show 'not found' error message");
    }
    
    @Test(description = "Tracking dengan format email invalid")
    public void testTrackWithInvalidEmailFormat() {
        TrackingPage trackingPage = new TrackingPage(driver);
        
        trackingPage
            .enterEmail("invalid-email")
            .clickTrack();
        
        Assert.assertTrue(trackingPage.getErrorMessage().contains("valid"), 
            "Should show format validation error");
    }
    
    @Test(description = "Tracking dengan nomor HP valid", dataProvider = "phoneData", 
          dataProviderClass = TrackingDataProvider.class)
    public void testTrackWithValidPhone(String phoneNumber, String expectedStatus) {
        TrackingPage trackingPage = new TrackingPage(driver);
        
        String result = trackingPage
            .trackByPhone(phoneNumber)
            .getTrackingResult();
        
        Assert.assertTrue(result.contains(expectedStatus), 
            "Status should match expected: " + expectedStatus);
    }
    
    @Test(description = "Tracking tanpa input apapun")
    public void testTrackWithEmptyInput() {
        TrackingPage trackingPage = new TrackingPage(driver);
        
        trackingPage.clickTrack();
        
        Assert.assertTrue(trackingPage.getErrorMessage().contains("diperlukan") || 
                          trackingPage.getErrorMessage().contains("required"),
                          "Should show required field error");
    }
}