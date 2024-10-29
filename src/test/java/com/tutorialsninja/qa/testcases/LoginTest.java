package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.utils.Utilities;

public class LoginTest extends Base{
	LoginPage loginPage;
	
	public LoginTest()
	{
		super();
	}
	public WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		loginPage = homePage.naviageToLoginPage();
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	@Test(priority=1,dataProvider = "validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email,String password)
	{
		AccountPage accountPage = loginPage.login(email, password); 		
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit your account information is not displayed");
	}
	
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData()
	{
		Object[][] data=Utilities.getTestDataFromExcel("Login");
		return data;
	}
	
	@Test(priority=2)
	public void verifyLoginWithInvalidCredentials()
	{
		loginPage.login(Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));
	
		String actualwarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWanringMessage = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertTrue(actualwarningMessage.contains(expectedWanringMessage),"Excepted warning message is not displayed");
	}
	
		
	@Test(priority=3)
	public void verifyLoginWithInvalidEmailAndValidPassword()
	{
		loginPage.login(Utilities.generateEmailWithTimeStamp(), prop.getProperty("validPassword"));

		String actualwarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWanringMessage = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertTrue(actualwarningMessage.contains(expectedWanringMessage),"Excepted warning message is not displayed");
	}
	
	@Test(priority=4)
	public void verifyLoginWithValidEmailAndInvalidPassword()
	{
		loginPage.login(prop.getProperty("validEmail"), "Team@12345");
			
		String actualwarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWanringMessage = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertTrue(actualwarningMessage.contains(expectedWanringMessage),"Excepted warning message is not displayed");
	}
	
	@Test(priority=5)
	public void verifyLoginWithoutProvidingCredentials()
	{
		loginPage.clickOnLoginButton();

		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		String actualwarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWanringMessage = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertTrue(actualwarningMessage.contains(expectedWanringMessage),"Excepted warning message is not displayed");
	
	// Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains("Warning: No match for E-Mail Address and/or Password."),"Excepted warning message is not displayed");
	//or you can write above way and you can reduce 93th , 94th line
	}
	
	

}
