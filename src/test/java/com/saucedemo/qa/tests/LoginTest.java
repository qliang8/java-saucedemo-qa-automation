package com.saucedemo.qa.tests;

import com.saucedemo.qa.pages.InventoryPage;
import com.saucedemo.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(groups = {"smoke", "regression", "functional"})
    public void validLoginTest() {
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed");
    }

    @Test(groups = {"regression", "performance"})
    public void loginPerformanceTest() {

        long start = System.currentTimeMillis();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();
        long duration = System.currentTimeMillis() - start;

        Assert.assertTrue(duration <= 2000, "Login time is larger than 2000ms for standard_user");
    }

    @Test(groups = {"regression", "functional"})
    public void emptyUsernameLoginTest() {
        loginPage.login("", "");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Username is required"));
    }

    @Test(groups = {"regression", "functional"})
    public void emptyPasswordLoginTest() {
        loginPage.login("standard_user", "");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Password is required"));
    }

    @Test(groups = {"regression", "functional"})
    public void invalidLoginTest() {
        loginPage.login("standard_user", "wrong_password");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test(groups = {"regression", "functional"})
    public void lockedOutUserLoginTest() {
        loginPage.login("locked_out_user", "secret_sauce");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Epic sadface: Sorry, this user has been locked out."));
    }
}
