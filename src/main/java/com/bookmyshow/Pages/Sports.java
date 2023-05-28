package com.bookmyshow.Pages;


import org.testng.annotations.Test;
import java.io.IOException;
import com.bookmyshow.Base.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Sports extends Base {
	
	WebDriverWait wait;
	JavascriptExecutor js;
	WebDriver driver;
	
	//Locators
	By sports = By.xpath("//a[.='Sports']");
	By weekend = By.xpath("//div[contains(@class,'sc-7o7nez') and text()='This Weekend']");
	By price = By.xpath("//div[contains(@class,'sc-133848s') and text()='Price']");
	By free = By.xpath("//div[contains(@class,'sc-7o7nez') and text()='Free']");
	By lowPrice = By.xpath("//div[contains(@class,'sc-7o7nez') and text() = '0 - 500']");
	
	@Test
	public void sports() throws IOException, InterruptedException {
		invokeBrowser();  //invokingBrowser
		driver = homePage();  //Driver initialization 
		logger = report.createTest("Displaying Sports Activities");
		waitClickable(sports); 
		driver.findElement(sports).click();  //Selecting sports from section.
		reportPass("Sports Option is selected");

		Thread.sleep(3000);
		ScreenShot("sportspage1.jpg");   //Taking screenshot of Sports page.
		scroll();
		
		waitClickable(weekend);
		driver.findElement(weekend).click(); // Selecting "weekend" Filter
		reportPass("Weekend Filter is selected");
		Thread.sleep(1000);
		
		waitClickable(price);
		driver.findElement(price).click(); 		// Clicking "Price" drop-down
		reportPass("Price Dropdown is selected");
		Thread.sleep(1000);
		
		waitClickable(free);
		driver.findElement(free).click();		// Selecting "free" filter.
		reportPass("Free Option is selected");
		Thread.sleep(1000);
		
		waitClickable(lowPrice);
		driver.findElement(lowPrice).click();	// Selecting "0-500" range as the Lowest Price.
		reportPass("0-500 is selected");
		Thread.sleep(1000);
		
		scroll();
		
		//Taking Screenshots
		ScreenShot("SportsFilter.jpg");
		Thread.sleep(2000);
		ScreenShot("SportsActivities.jpg");
		
		System.out.println("Success");
		
		//Saving data to Excel File
		writeExcel(": Sports Activities Executed Successfully", 1);
		endReport();
		//tearDown();
		Thread.sleep(2000);
	}

}
