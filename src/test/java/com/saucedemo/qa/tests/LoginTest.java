package com.saucedemo.qa.tests;

import com.saucedemo.qa.pages.InventoryPage;
import com.saucedemo.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"functional", "regression"})
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    // Performance + Regression
    @Test(groups = {"performance", "regression"})
    public void loginPerformanceTest() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        long start = System.currentTimeMillis();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.waitForPageLoad();
        long duration = System.currentTimeMillis() - start;

        Assert.assertTrue(duration <= 2000, "Login time is larger than 2000ms for standard_user");
    }

    @Test(groups = {"functional", "regression"})
    public void emptyUsernameLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Username is required"));
    }

    @Test(groups = {"functional", "regression"})
    public void emptyPasswordLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Password is required"));
    }

    @Test(groups = {"functional", "regression"})
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test(groups = {"functional", "regression"})
    public void lockedOutUserLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Sorry, this user has been locked out."));
    }
}
