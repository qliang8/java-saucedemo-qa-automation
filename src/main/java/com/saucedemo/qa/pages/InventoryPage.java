package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final  WebDriverWait wait;

    @FindBy(id = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButtons;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOf(inventoryContainer));
    }
}
