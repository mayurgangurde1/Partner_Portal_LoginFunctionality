package com.logintest;

import java.io.IOException;

import org.testng.annotations.Test;

import com.Jarvis.Library.UtilityClass;
import com.jarvis.testutility.BaseTest;

public class TestClass_login extends BaseTest {

	@Test(priority = 1)
	public void VerifyLoginFunctionality() throws Exception {

//		// *********login with password************//
		loginpage.EnterEmailId(UtilityClass.getDatafromPropertyFile("Login_Email"));
		loginpage.Enterpassword(UtilityClass.getDatafromPropertyFile("Password"));
		loginpage.ClickOnLogin();

	}

	
	
}
