package com.saucedemo.qa.pages;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(id = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "inventory_item")
    private List<WebElement> items;

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
        super(driver);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOf(inventoryContainer));
    }

    public boolean isLoaded() {
        return getCurrentUrl().contains("inventory.html");
    }

    public boolean isInventoryContainerVisible() {
        try {
            return inventoryContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Add item by its name
    public void addItemByName(String itemName) {
        for (WebElement item : items) {
            // get the item name text
            String name = item.findElement(By.className("inventory_item_name")).getText();

            if (name.equalsIgnoreCase(itemName)) {
                // click add to cart button inside this item
                WebElement button = item.findElement(By.tagName("button"));
                if(button.getText().equalsIgnoreCase("Add to cart")){
                    click(button);
                }
                break; // exit after adding the item
            }
        }
    }

    // Add item by its name
    public void removeItemByName(String itemName) {
        for (WebElement item : items) {
            // get the item name text
            String name = item.findElement(By.className("inventory_item_name")).getText();

            if (name.equalsIgnoreCase(itemName)) {
                // click add to cart button inside this item
                WebElement button = item.findElement(By.tagName("button"));
                if(button.getText().equalsIgnoreCase("Remove")){
                    click(button);
                }
                break; // exit after adding the item
            }
        }
    }

    public int getCartCount() {
        By cartCount = By.className("shopping_cart_badge");
        try {
            return Integer.parseInt(driver.findElement(cartCount).getText());
        } catch (Exception e) {
            return 0; // no badge = 0 items
        }
    }

    public void goToCart() {
        cartIcon.click();
    }

    public LoginPage logout(){
        openMenuButton.click();
        wait.until(ExpectedConditions.attributeToBe(By.className("bm-menu-wrap"), "aria-hidden", "false"));
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logout.click();
        return new LoginPage(driver);
    }
}
