package com.amazon.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void initReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    public static void flushReport() {
        extent.flush();
    }
}
