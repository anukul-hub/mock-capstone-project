package com.amazon.tests;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;


public class SearchSteps {
    WebDriver driver;

    @Given("user is on Amazon home page")
    public void user_is_on_amazon_home_page() {
        driver = new ChromeDriver();
        driver.get("https://www.amazon.in/");
        driver.manage().window().maximize();
    }

    @When("user enters {string} in search box")
    public void user_enters_in_search_box(String query) {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.clear();
        searchBox.sendKeys(query);
    }

    @And("user clicks on search button")
    public void user_clicks_on_search_button() {
        WebElement searchBtn = driver.findElement(By.id("nav-search-submit-button"));
        searchBtn.click();
    }

    @Then("search results for {string} are displayed")
    public void search_results_are_displayed(String query) {
        WebElement results = driver.findElement(By.cssSelector("span.a-color-state.a-text-bold"));
        assertTrue(results.getText().contains(query));
        driver.quit();
    }
}

