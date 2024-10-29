package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.Utilities;

public class RegisterTest extends Base{
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	public RegisterTest()
	{
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		registerPage = homePage.naviageToRegisterPage();
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifyRegisterAnAccountWithMandatoryFields()
	{
		accountSuccessPage = registerPage.registerWithMandatoryFields("Test", "Here", Utilities.generateEmailWithTimeStamp(), "1234567890", "Team@123", "Team@123");
		
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertEquals(actualSuccessHeading,"Your Account Has Been Created!","Account success page is not displayed");
		
		//Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),"Your Account Has Been Created!","Account success page is not displayed");
		// You can remove the 45th line and implement into the 46th line.
		
	}
	
	@Test(priority=2)
	public void verifyRegisterAnAccountByProvidingAllFields()
	{
		accountSuccessPage = registerPage.registerWithAllFields("Test", "Here", Utilities.generateEmailWithTimeStamp(), "1234567890", "Team@123", "Team@123");
		
 		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertEquals(actualSuccessHeading,"Your Account Has Been Created!","Account success page is not displayed");
		
	}
	
	@Test(priority=3)
	public void verifyRegisterAccountWithExistingEmailAddress()
	{
		registerPage.registerWithAllFields("Test", "Here", "Manjiritest@yopmail.com", "1234567890", "Team@123", "Team@123");
		
		String actualWarning = registerPage.retrieveDuplicateEmailAddressWarning();
		Assert.assertTrue(actualWarning.contains("Warning: E-Mail Address is already registered!"),"Warning message regarding duplicate email address is not displayed");
		
	}
	
	@Test(priority=4)
	public void verifyRegisteringAnAccountWithoutFillingAnyDetails()
	{
		registerPage.clickOnContinueButton();
		
		
		String actualPrivacyPolicyWarning = registerPage.retrievePrivacyPolicyWarning();
		Assert.assertTrue(actualPrivacyPolicyWarning.contains("Warning: You must agree to the Privacy Policy!"),"Privacy policy warning message is not displayed");
		
		String actualFirstNameWarning = registerPage.retrieveFirstNameWarning();
		Assert.assertEquals(actualFirstNameWarning,"First Name must be between 1 and 32 characters!","First name warning is not displayed");
		
		String actualLastNameWarning = registerPage.retrieveLastNameWarning();
		Assert.assertEquals(actualLastNameWarning,"Last Name must be between 1 and 32 characters!","Last name warning is not displayed");
		
		String actualEmailWarning = registerPage.retrieveEmailWarning();
		Assert.assertEquals(actualEmailWarning,"E-Mail Address does not appear to be valid!","Email warning is not displayed");
		
		String actualTelephoneWarning = registerPage.retrieveTelephoneWarning();
		Assert.assertEquals(actualTelephoneWarning,"Telephone must be between 3 and 32 characters!","Telephone warning is not displayed");
		
		String actualPasswordWarning = registerPage.retrievePasswordWarning();
		Assert.assertEquals(actualPasswordWarning,"Password must be between 4 and 20 characters!","Password warning is not displayed");
		
		//Assert.assertEquals(registerPage.retrievePasswordWarning(),"Password must be between 4 and 20 characters!","Password warning is not displayed");
		//Like above sentences you can reduce all lines like above.
	}
	

}
