package com.bookmyshow.Pages;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bookmyshow.Base.Base;

public class SignIn extends Base {
	WebDriverWait wait;
	JavascriptExecutor js;
	WebDriver driver;
	
	//Locators
	By signin = By.xpath("//div[@class='sc-fQejPQ jWuMjc']");
	By email = By.xpath("//div[@class='Xb9hP']/input[@type='email']");
	By next = By.xpath("//span[@jsname = 'V67aGc' and text()='Next']");
	//By error = By.xpath("//div[@class='o6cuMc']");
	By error = By.xpath("//div[@class='o6cuMc Jj6Lae']");
	
	
	@Test
	public void signin() throws InterruptedException, Exception {
		invokeBrowser();
		driver = homePage();
		logger = report.createTest("Checking Google SignIn");
		waitClickable(signin);
		driver.findElement(signin).click();
		reportPass("SignIn Option is selected");
		Thread.sleep(2000);
		By google = By.xpath("//img[@alt='google logo']");
		waitClickable(google);
		
		String mainWindow = driver.getWindowHandle();
		
		driver.findElement(google).click();
		reportPass("SignIn with google is selected");
		Thread.sleep(2000);
		
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		
		while(itr.hasNext()) {
			String window = itr.next();
			driver.switchTo().window(window);
			Thread.sleep(1000);
			if(driver.getTitle().equals("Sign in – Google accounts")) {
				
				driver.findElement(email).sendKeys(userId);
				reportPass("userId is entered");
				driver.findElement(next).click();
				reportPass("Next button is clicked");
				Thread.sleep(3000);
				
				waitClickable(error);
				String error_msg = driver.findElement(error).getText();
				System.out.println("The Obtained Error is: "+error_msg);
				reportPass("Error Message is printed in console");
				try {
					ScreenShot("SignIn.jpg");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tearDown();
			}
		}
		System.out.println("Success");
		writeExcel(": SignIn Activities Executed Successfully", 4);
		driver.switchTo().window(mainWindow);
		endReport();
		//tearDown();
		
		Thread.sleep(2000);
	}
	
}
