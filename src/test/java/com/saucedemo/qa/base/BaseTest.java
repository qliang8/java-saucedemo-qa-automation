package com.saucedemo.qa.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.Reporter;

public class BaseTest {

    protected WebDriver driver;

    // Init a driver every test method
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        //DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
        Reporter.log("=== @BeforeMethod called ===", true);
    }

    // Tear down the driver after the test
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}