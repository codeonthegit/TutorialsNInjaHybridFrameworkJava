package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;

public class SearchTest extends Base{
	SearchPage searchPage;
	HomePage homePage;
	
	public SearchTest()
	{
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		homePage = new HomePage(driver);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	@Test(priority = 1)
	public void verifySearchWithValidProduct()
	{
		searchPage = homePage.SearchForAProduct("HP");
		
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid product is not displayed in the search result");
	}
	
	@Test(priority = 2)
	public void verifySearchWithInvalidProduct()
	{
		searchPage = homePage.SearchForAProduct("Honda");
				
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,"There is no product that matches the search criteria.","No product message in search results is not displayed");
		
	}
	
	@Test(priority = 3)
	public void verifySearchWithoutAnyProduct()
	{		
		homePage.enterProductIntoSearchBoxField("");
		searchPage = homePage.clickOnSearchButton();
		
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,"There is no product that matches the search criteria.","No product message in search results is not displayed");
	
	//Assert.assertEquals(searchPage.retrieveNoProductMessageText(),"There is no product that matches the search criteria.","No product message in search results is not displayed");
	//You can reduce 64th line like above statement.
	}
	
	
	
}
