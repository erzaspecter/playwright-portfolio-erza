package com.modena.tests.tracking;

import com.modena.utils.ExcelDataProvider;
import org.testng.annotations.DataProvider;

public class TrackingDataProvider {
    
    @DataProvider(name = "phoneData")
    public static Object[][] getPhoneData() {
        return new Object[][] {
            {"081234567890", "diproses"},
            {"087654321098", "selesai"},
            {"085555555555", "gugur"},
            {"000000000000", "tidak ditemukan"}
        };
    }
    
    @DataProvider(name = "emailData")
    public static Object[][] getEmailData() {
        // Bisa juga mengambil dari Excel
        return ExcelDataProvider.getData("src/main/resources/testdata/tracking_data.xlsx", "emails");
    }
}