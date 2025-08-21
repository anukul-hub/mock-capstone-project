package com.amazon.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils {

    // Takes screenshot and returns path
    public static String takeScreenshot(WebDriver driver, String testName) {
        String dir = "test-output/screenshots/";
        try {
            Files.createDirectories(Paths.get(dir)); // create folder if not exists
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = dir + testName + "_" + System.currentTimeMillis() + ".png";
            File dest = new File(path);
            Files.copy(src.toPath(), dest.toPath());
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
