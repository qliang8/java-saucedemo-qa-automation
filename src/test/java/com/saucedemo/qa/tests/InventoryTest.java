package com.saucedemo.qa.tests;

import com.saucedemo.qa.pages.InventoryPage;
import com.saucedemo.qa.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class InventoryTest extends BaseTest{

    @Test(groups = {"smoke", "regression", "functional"})
    public void inventoryPageLoadTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isInventoryContainerVisible(),"Inventory page did not load successfully");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void productDisplayTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertFalse(inventoryPage.getProductNames().isEmpty(), "Product did not display");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void addFirstProductTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addFirstProductToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Cart item is not 1 after adding first product");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void addMultipleProductsTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCartByIndex(0);
        inventoryPage.addProductToCartByIndex(1);
        inventoryPage.addProductToCartByIndex(2);
        Assert.assertEquals(inventoryPage.getCartItemCount(), 3, "Cart item is not 3 after adding 3 items");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void navigateToCartTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.goToCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Direct to cart page failed");
    }

//    @Test(groups = {"smoke", "regression", "functional"})
//    public void logoutTest() {
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("standard_user", "secret_sauce");
//
//        InventoryPage inventoryPage = new InventoryPage(driver);
//        inventoryPage.waitForPageLoad();
//
//        inventoryPage.logout();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
//
//        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Logout failed: Login page not displayed");
//    }
}
