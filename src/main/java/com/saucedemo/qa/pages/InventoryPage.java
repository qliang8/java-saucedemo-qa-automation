package com.saucedemo.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private final WebDriver driver;
    private final  WebDriverWait wait;

    @FindBy(id = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement openMenuButton;

    @FindBy(className = "bm-menu-wrap")
    private WebElement menuWrapper;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOf(inventoryContainer));
    }

    public boolean isInventoryContainerVisible() {
        try {
            return inventoryContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void addFirstProductToCart() {
        if (!addToCartButtons.isEmpty()) {
            addToCartButtons.get(0).click();
        }
    }

    public void addProductToCartByIndex(int index) {
        if (index >= 0 && index < addToCartButtons.size()) {
            addToCartButtons.get(index).click();
        }
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToCart() {
        cartIcon.click();
    }

    public void logout(){
        // 1️⃣ Click menu button
        wait.until(ExpectedConditions.elementToBeClickable(openMenuButton)).click();

        // 2️⃣ Wait for menu animation to finish
        wait.until(ExpectedConditions.attributeContains(menuWrapper, "aria-hidden", "false"));

        // 4️⃣ Scroll into view (optional, prevents overlay issues)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutLink);

        // 5️⃣ Click logout
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
