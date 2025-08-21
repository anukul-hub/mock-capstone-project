package com.amazon.tests;

import com.amazon.base.BaseTest;
import com.amazon.pages.HomePage;
import com.amazon.reports.ExtentManager;
import com.amazon.utils.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class SearchBoxTest extends BaseTest {

    HomePage homePage;

    @BeforeClass
    public void setup() throws Exception {
        initialize();
        homePage = new HomePage(driver);
    }

    // ==================  Test Cases =====================

    @Test(priority = 1)
    public void testValidProductSearch() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Valid Product Search");
        homePage.enterSearchText("logitech mx anywhere 3");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);

        // Take screenshot
        String path = ScreenshotUtils.takeScreenshot(driver, "ValidProductSearch");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(title.toLowerCase().contains("logitech"));
    }

    @Test(priority = 2)
    public void testEmptySearch() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Empty Search");
        homePage.enterSearchText("");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);

        // Screenshot
        String path = ScreenshotUtils.takeScreenshot(driver, "EmptySearch");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(title.toLowerCase().contains("amazon"));
    }

    @Test(priority = 3)
    public void testSingleCharSuggestions() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Single Character Suggestions");
        homePage.enterSearchText("a");
        homePage.waitForSuggestions();
        int count = homePage.getSuggestionsCount();
        ExtentManager.test.log(Status.INFO, "Suggestion count: " + count);

        // Screenshot
        String path = ScreenshotUtils.takeScreenshot(driver, "SingleCharSuggestions");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(count > 0, "No suggestions shown");
    }

    @Test(priority = 4)
    public void testSingleCharTenSuggestions() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Single Character Max 10 Suggestions");
        homePage.enterSearchText("a");
        int count = homePage.getSuggestionsCount();
        ExtentManager.test.log(Status.INFO, "Suggestion count: " + count);

        String path = ScreenshotUtils.takeScreenshot(driver, "SingleCharMax10Suggestions");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(count <= 10, "More than 10 suggestions shown");
    }

    @Test(priority = 5)
    public void testSelectSuggestion() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Select Suggestion");
        homePage.enterSearchText("a");
        homePage.selectFirstSuggestion();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title after selecting suggestion: " + title);

        String path = ScreenshotUtils.takeScreenshot(driver, "SelectSuggestion");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertFalse(title.isEmpty());
    }

    @Test(priority = 6)
    public void testTwoCharSuggestions() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Two Character Suggestions");
        homePage.enterSearchText("ab");
        homePage.waitForSuggestions();
        int count = homePage.getSuggestionsCount();
        ExtentManager.test.log(Status.INFO, "Suggestion count: " + count);

        String path = ScreenshotUtils.takeScreenshot(driver, "TwoCharSuggestions");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(count > 0, "No suggestions for two characters");
    }

    @Test(priority = 7)
    public void testRepeatedWordSearch() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Repeated Word Search");
        homePage.enterSearchText("trimmer trimmer trimmer");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);

        String path = ScreenshotUtils.takeScreenshot(driver, "RepeatedWordSearch");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(title.toLowerCase().contains("trimmer"));
    }

    @Test(priority = 8)
    public void testRandomCharacterSearch() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Random Character Search");
        homePage.enterSearchText("xxxxzzzzqqq");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);

        String path = ScreenshotUtils.takeScreenshot(driver, "RandomCharacterSearch");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(title.toLowerCase().contains("no results") || title.toLowerCase().contains("amazon"));
    }

    @Test(priority = 9)
    public void testLongStringSearch() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Long String Search");
        String longStr = "abc".repeat(20);
        homePage.enterSearchText(longStr);
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);

        String path = ScreenshotUtils.takeScreenshot(driver, "LongStringSearch");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertFalse(title.isEmpty());
    }

    @Test(priority = 10)
    public void testCombinationSearch() throws Exception {
        ExtentManager.test = ExtentManager.extent.createTest("Combination of Character & Number");
        homePage.enterSearchText("legion laptop 14650HX");
        homePage.clickSearchButton();
        String title = homePage.getPageTitle();
        ExtentManager.test.log(Status.INFO, "Page Title: " + title);

        String path = ScreenshotUtils.takeScreenshot(driver, "CombinationSearch");
        ExtentManager.test.addScreenCaptureFromPath(path);

        Assert.assertTrue(title.toLowerCase().contains("legion"));
    }

    // ==================  Hooks =====================

    @AfterMethod
    public void captureFailure(ITestResult result) throws Exception {
        if (ITestResult.FAILURE == result.getStatus()) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getName());
            ExtentManager.test.fail("Test Failed - Screenshot attached")
                    .addScreenCaptureFromPath(screenshotPath);
        }
    }

    @AfterClass
    public void cleanup() {
        tearDown();
    }
}
