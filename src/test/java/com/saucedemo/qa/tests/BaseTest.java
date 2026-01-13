package com.saucedemo.qa.tests;

import com.saucedemo.qa.base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    // Init a driver every test method
    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    // Tear down the driver after the test
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}