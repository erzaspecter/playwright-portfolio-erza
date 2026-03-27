package com.erza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductPage {
    private WebDriver driver;

    // 1. Locators
    private By productTitle = By.className("inventory_item_name");
    private By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private By shoppingCartBadge = By.className("shopping_cart_badge");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // 2. Methods / Actions
    public String getFirstProductTitle() {
        return driver.findElement(productTitle).getText();
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public String getCartCount() {
        return driver.findElement(shoppingCartBadge).getText();
    }
}