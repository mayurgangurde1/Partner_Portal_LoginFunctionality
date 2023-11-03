package com.jarvis.testutility;

import java.awt.Desktop;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Driver;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.Jarvis.Library.BaseClass;
import com.Jarvis.Library.LoginPage;
import com.Jarvis.Library.UtilityClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class BaseTest extends BaseClass {

	@BeforeSuite
	@Parameters("browser")
	// reading browser value from testng.xml file

	public void setup(@Optional String browser) throws Exception {

		newDriver(browser);
	}

	@AfterSuite
	public void teardown() {
//		 driver.quit();
	}

	@BeforeTest
	public void beforeTest() {
		setExtentReport();
	}

	@AfterTest
	public void afterTest() throws IOException {
		extent.flush();
		Desktop.getDesktop().browse(new File("Extent Report.html").toURI());

		// sending Extend report on mail
	}

	@BeforeMethod
	public void createExtentTest(ITestResult result, Method m) {
		// test=extent.createExtentTest(m.getName());
		test = extent.createTest(m.getName());
	}

	@AfterMethod
	public void captureScreenshot(ITestResult result, Method m) {
		if (result.getStatus() == ITestResult.FAILURE) {
			UtilityClass.captureScreenshot(result.getMethod().getMethodName(), driver);
			test.log(Status.FAIL, m.getName());

		} else {
			test.log(Status.PASS, m.getName());
		}
	}

	public LoginPage loginpage;

	@BeforeClass
	public void pageobjects() {
		loginpage = new LoginPage(driver);

	}

}
