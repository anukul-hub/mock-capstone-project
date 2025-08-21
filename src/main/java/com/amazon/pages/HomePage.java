package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    // Locators
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
//    private By suggestionList = By.cssSelector("div.s-suggestion-container"); // Amazon suggestions container
    private By suggestionList = By.xpath("//div[@class='s-suggestion-container'] | //div[@class='s-suggestion']");
    
    public void waitForSuggestions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(suggestionList, 0));
    }


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchText(String product) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(product);
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }


    public int getSuggestionsCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionList));
        return driver.findElements(suggestionList).size();
    }

    public List<WebElement> getSuggestions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionList));
        return driver.findElements(suggestionList);
    }


    // Select first suggestion
    public void selectFirstSuggestion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> suggestions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionList)
        );
        if (!suggestions.isEmpty()) {
            suggestions.get(0).click();
        } else {
            throw new RuntimeException("No suggestions found to select.");
        }
    }

}
