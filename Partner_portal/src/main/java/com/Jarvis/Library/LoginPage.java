package com.Jarvis.Library;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import bsh.This;

public class LoginPage extends BaseClass {
	public WebDriver driver;

	@FindBy(xpath = "//input[@name=\"email_id\"]")
	private WebElement Username;
	@FindBy(xpath = "//input[@name=\"password\"]")
	private WebElement Password;
	@FindBy(xpath = "//button[contains(.,'LOG')]")

	private WebElement ClickOnLoginButton;
	// span[contains(.,'Dashboard')]
	@FindBy(css = "//span[contains(.,'Dashboard')]")
	private WebElement dashboard;

	public LoginPage(WebDriver driver) {
		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	public void EnterEmailId(String email) throws InterruptedException, IOException {
		Thread.sleep(1000);

		String expectedUrl = "https://partner.jarvisinvest.com/";
		System.out.println(" expectedurl : " + expectedUrl);
		try {

			System.out.println("currenturl : " + driver.getCurrentUrl());

			Assert.assertEquals(expectedUrl, driver.getCurrentUrl());

			Assert.assertTrue(true, expectedUrl);

			System.out.println("Navigated to correct webpage");

		} catch (Throwable pageNavigationError) {
			System.out.println("Didn't navigate to correct webpage");
			SendAttachmentInEmailFail();

		}

		Username.sendKeys(email);
		Thread.sleep(1000);

	}
	public void Enterpassword(String password) {
		Password.sendKeys(password);

		System.out.println("entered password");
	}

	public void ClickOnLogin() throws InterruptedException {

		ClickOnLoginButton.click();

		Thread.sleep(3000);

		System.out.println("clicked on login button");
		try {

			WebElement dashboard = driver.findElement(By.xpath("//span[contains(.,'Dashboard')]"));
		
			Assert.assertEquals(true, dashboard.isDisplayed());
			System.out.println("Login successful");

			System.out.println("dahsboard is visible");

			String expectedUrl = "https://partner.jarvisinvest.com/profile";
			System.out.println(" expectedurl : " + expectedUrl);

			System.out.println("currenturl : " + driver.getCurrentUrl());

			Assert.assertEquals(expectedUrl, driver.getCurrentUrl());

			Assert.assertTrue(true, expectedUrl);

			System.out.println("Navigated to correct webpage");

			SendAttachmentInEmailPass();

		} catch (Throwable pageNavigationError) {
			System.out.println("dashboard is not visible");

			System.out.println("Didn't navigate to correct webpage");

			SendAttachmentInEmailFail();

		}

	}

}