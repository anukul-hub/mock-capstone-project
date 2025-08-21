package com.amazon.tests;

import com.amazon.base.BaseTest;
import com.amazon.pages.HomePage;
import com.amazon.reports.ExtentManager;
import com.amazon.utils.ScreenshotUtils;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;

public class SearchBoxTest extends BaseTest {

    HomePage homePage;

    @BeforeClass
    public void setup() throws Exception {
        initialize();
        homePage = new HomePage(driver);
    }

    // ==================  Hooks =====================

    @BeforeMethod
    public void startTest(Method method) {
        ExtentManager.test = ExtentManager.extent.createTest(method.getName());
    }

    @AfterMethod
    public void logTestStatus(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getName());
            ExtentManager.test.fail("Test Failed",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            ExtentManager.test.log(Status.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getName());
            ExtentManager.test.pass("Test Passed",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            ExtentManager.test.skip("Test Skipped");
        }
    }

    @AfterClass
    public void cleanup() {
        tearDown();
    }


    // ==================  Test Cases  =====================

    @Test(priority = 1)
    public void testValidProductSearch() {
        homePage.enterSearchText("logitech mx anywhere 3");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("logitech"));
    }

    @Test(priority = 2)
    public void testEmptySearch() {
        homePage.enterSearchText("");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("amazon"));
    }

    @Test(priority = 3)
    public void testSingleCharSuggestions() {
        homePage.enterSearchText("a");
        homePage.waitForSuggestions();
        int count = homePage.getSuggestionsCount();
        ExtentManager.test.log(Status.INFO, "Suggestion count: " + count);
        Assert.assertTrue(count > 0, "No suggestions shown");
    }

    @Test(priority = 4)
    public void testSingleCharTenSuggestions() {
        homePage.enterSearchText("a");
        int count = homePage.getSuggestionsCount();
        ExtentManager.test.log(Status.INFO, "Suggestion count: " + count);
        Assert.assertTrue(count <= 10, "More than 10 suggestions shown");
    }

    @Test(priority = 5)
    public void testSelectSuggestion() {
        homePage.enterSearchText("a");
        homePage.selectFirstSuggestion();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title after selecting suggestion: " + title);
        Assert.assertFalse(title.isEmpty());
    }

    @Test(priority = 6)
    public void testTwoCharSuggestions() {
        homePage.enterSearchText("ab");
        homePage.waitForSuggestions();
        int count = homePage.getSuggestionsCount();
        ExtentManager.test.log(Status.INFO, "Suggestion count: " + count);
        Assert.assertTrue(count > 0, "No suggestions for two characters");
    }

    @Test(priority = 7)
    public void testRepeatedWordSearch() {
        homePage.enterSearchText("trimmer trimmer trimmer");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("trimmer"));
    }

    @Test(priority = 8)
    public void testRandomCharacterSearch() {
        homePage.enterSearchText("xxxxzzzzqqq");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("no results") || title.toLowerCase().contains("amazon"));
    }

    @Test(priority = 9)
    public void testLongStringSearch() {
        String longStr = "abc".repeat(20);
        homePage.enterSearchText(longStr);
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);
        Assert.assertFalse(title.isEmpty());
    }

    @Test(priority = 10)
    public void testCombinationSearch() {
        homePage.enterSearchText("legion laptop 14650HX");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("legion"));
    }
}