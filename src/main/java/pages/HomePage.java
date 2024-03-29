package pages;

import utils.EventUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.BaseClass;

public class HomePage extends BaseClass {
    public WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By ICON_HAMBURGER_MENU = By.cssSelector("div[id='nav-main'] div[class='nav-left']");
    By DIV_LEFT_PANEL = By.cssSelector("div[id=\"hmenu-content\"]");
    By A_BACK_TO_MAIN_MENU = By.cssSelector("a[aria-label=\"Back to main menu\"]");
    By SPAN_SORT = By.cssSelector("span[class=\"a-dropdown-container\"]");
    By SPAN_SORT_TEXT = By.cssSelector("span[class=\"a-dropdown-prompt\"]");
    String menuItem = "//a[@class='hmenu-item' and contains(., '%s')]";

    public void checkHomePageOpened() {
        EventUtilities.applyExplicitWaitBy(driver, ICON_HAMBURGER_MENU);
        Assert.assertTrue(EventUtilities.isElementPresent(driver, ICON_HAMBURGER_MENU), "Verify Home page opened successfully.");
    }

    public void clickOnHamburgerMenu() {
        EventUtilities.applyExplicitWaitBy(driver, ICON_HAMBURGER_MENU);
        EventUtilities.clickOnElement(driver, ICON_HAMBURGER_MENU);
        EventUtilities.applyExplicitWaitBy(driver, DIV_LEFT_PANEL);
        Assert.assertTrue(EventUtilities.isElementPresent(driver, DIV_LEFT_PANEL), "Verify user can see departments.");
    }

    public void clickOnShopWithDepartment(String department) {
        String xpath = String.format(menuItem, department);
        EventUtilities.applyExplicitWait(driver, By.xpath(xpath));
        EventUtilities.scrollElementIntoView(driver, xpath);
        EventUtilities.clickOnWithVisibleText(driver, xpath);
        Assert.assertTrue(EventUtilities.isElementPresent(driver, A_BACK_TO_MAIN_MENU), "Verify department is selected");
    }

    public void clickOnProductType(String productType) {
        String xpath = String.format(menuItem, productType);
        EventUtilities.applyExplicitWait(driver, By.xpath(xpath));
        EventUtilities.scrollElementIntoView(driver, xpath);
        EventUtilities.clickOnWithVisibleText(driver, xpath);
        Assert.assertTrue(driver.findElements(A_BACK_TO_MAIN_MENU).size() < 1, "Verify product type is selected");
    }

    public void clickOnBrand(String brand){
        String xpath = "//li[@class='a-spacing-micro']//span[text()='" + brand + "']";
        int counter = 0;
        while(counter < 5){
            if(driver.findElements(By.xpath(xpath)).isEmpty()){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                EventUtilities.scrollBy(driver, 0, 500);
                counter++;
            }else{
                EventUtilities.scrollElementIntoView(driver, xpath);
                break;
            }
        }
        EventUtilities.clickOnWithVisibleText(driver, xpath);
        EventUtilities.applyExplicitWaitForVisibility(driver, By.xpath(xpath));
        EventUtilities.applyExplicitWait(driver, By.xpath(xpath));
        String checkboxXpath = "//li[@class='a-spacing-micro']//span[text()='"+ brand +"']//parent::a//input";
        Assert.assertTrue(driver.findElement(By.xpath(checkboxXpath)).isSelected(), "Verify brand is selected");
    }

    public void selectSortingMethod(String sorting) {
        EventUtilities.applyExplicitWait(driver, SPAN_SORT);
        EventUtilities.clickOnElement(driver, SPAN_SORT);
        String xpath = "//li[contains(., '" + sorting + "')]";
        EventUtilities.applyExplicitWait(driver, By.xpath(xpath));
        EventUtilities.clickOnWithVisibleText(driver, xpath);
        Assert.assertTrue(driver.findElement(SPAN_SORT_TEXT).getText().equals(sorting), "Verify product is sorted as " + sorting);
    }

    public void selectProduct(String productIndex) {
        String cssSelector = "div[data-cel-widget='search_result_" + productIndex + "'][data-component-type='s-search-result']";
        EventUtilities.applyExplicitWait(driver, By.cssSelector(cssSelector));
        EventUtilities.clickOnElement(driver, By.cssSelector(cssSelector));
    }

}
