// src/test/java/tests/LoginTest.java
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    
    @Test
    @Parameters("browser")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
    
    @Test
    @Parameters("browser")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "wrong_pass");
        
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match"));
    }
}