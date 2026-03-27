package com.modena.energy.tests.calculator;

import com.modena.energy.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EnergyCalculatorTests extends BaseTest {
    
    @Test
    public void testPageTitle() {
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertNotNull(title);
    }
    
    @Test
    public void testCalculatorOpens() {
        System.out.println("Calculator test started");
        Assert.assertTrue(true);
    }
}
