package com.saucedemo.qa.tests;

import com.saucedemo.qa.pages.InventoryPage;
import com.saucedemo.qa.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class InventoryTest extends BaseTest{

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void inventoryPageLoadTest() {
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isInventoryContainerVisible(),"Inventory page did not load successfully");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void productDisplayTest() {
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertFalse(inventoryPage.getProductNames().isEmpty(), "Product did not display");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void addFirstProductTest() {
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addFirstProductToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Cart item is not 1 after adding first product");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void addMultipleProductsTest() {
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addProductToCartByIndex(0);
        inventoryPage.addProductToCartByIndex(1);
        inventoryPage.addProductToCartByIndex(2);
        Assert.assertEquals(inventoryPage.getCartItemCount(), 3, "Cart item is not 3 after adding 3 items");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void navigateToCartTest() {
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.goToCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Direct to cart page failed");
    }

////    @Test(groups = {"smoke", "regression", "functional"})
////    public void logoutTest() {
////        LoginPage loginPage = new LoginPage(driver);
////        loginPage.login("standard_user", "secret_sauce");
////
////        InventoryPage inventoryPage = new InventoryPage(driver);
////        inventoryPage.waitForPageLoad();
////
////        inventoryPage.logout();
////
////        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
////
////        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Logout failed: Login page not displayed");
////    }
}
