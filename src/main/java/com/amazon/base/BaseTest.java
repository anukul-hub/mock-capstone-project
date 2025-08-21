package com.amazon.base;

import com.amazon.reports.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    public static Properties prop;

    public void initialize() throws IOException {
        // Load config
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);

        // Launch browser
        if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.get(prop.getProperty("url"));

        // Initialize Extent
        ExtentManager.initReport();
    }

    public void tearDown() {
        driver.quit();
        ExtentManager.flushReport();
    }
}
