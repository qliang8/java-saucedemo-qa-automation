package com.saucedemo.qa.tests;

import com.saucedemo.qa.base.BaseTest;
import com.saucedemo.qa.pages.InventoryPage;
import com.saucedemo.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login("standard_user", "secret_sauce");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void inventoryPageLoadTest() {
        Assert.assertTrue(inventoryPage.isInventoryContainerVisible(),"Inventory page load failed");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void addFirstItemTest() {
        inventoryPage.waitForPageLoad();
        inventoryPage.addItemByName("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartCount(), 1, "Cart item is not 1 after adding first product");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void addMultipleItemsTest() throws InterruptedException {
        inventoryPage.waitForPageLoad();
        inventoryPage.addItemByName("Sauce Labs Backpack");
        inventoryPage.addItemByName("Sauce Labs Bike Light");
        inventoryPage.addItemByName("Sauce Labs Bolt T-Shirt");

        Assert.assertEquals(inventoryPage.getCartCount(), 3, "Cart item is not 3 after adding 3 items");

        inventoryPage.removeItemByName("Sauce Labs Backpack");
        inventoryPage.removeItemByName("Sauce Labs Bike Light");
        inventoryPage.removeItemByName("Sauce Labs Bolt T-Shirt");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void removeItemTest() {
        inventoryPage.waitForPageLoad();
        inventoryPage.addItemByName("Sauce Labs Backpack");
        inventoryPage.addItemByName("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryPage.getCartCount(), 2, "Added item does not match with the Cart");

        inventoryPage.removeItemByName("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartCount(), 1, "Item does not remove");

        inventoryPage.removeItemByName("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryPage.getCartCount(), 0, "Item does not remove");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void navigateToCartTest() {
        inventoryPage.goToCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Direct to cart page failed");
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void logoutTest() {
        inventoryPage.waitForPageLoad();
        LoginPage loginPage = inventoryPage.logout();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Logout failed: Login page not displayed");
    }
}
