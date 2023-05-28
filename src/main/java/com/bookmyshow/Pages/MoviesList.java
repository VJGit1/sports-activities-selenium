package com.bookmyshow.Pages;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bookmyshow.Base.Base;

public class MoviesList extends Base {
	WebDriverWait wait;
	JavascriptExecutor js;
	WebDriver driver;
	
	//Locators
	By movies = By.xpath("//a[text()='Movies']");
	//By name=By.xpath("//*[@id=\"super-container\"]/div[2]/div[4]/div/div/div/div/div[2]/a/div/div[3]/div[1]/div");
	By name=By.xpath("//*[@id=\"super-container\"]/div[2]/div[3]/div[2]/div[2]/div");
	//By lang=By.xpath("//*[@id=\"super-container\"]/div[2]/div[4]/div[2]/div/div/div/div[2]/a/div/div/div[3]/div");
	By lang=By.xpath("//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]");

	@Test
	public void moviesList() throws InterruptedException{
		invokeBrowser();
		driver = homePage();
		logger = report.createTest("Displaying movies");
		waitClickable(movies);
		driver.findElement(movies).click();
		reportPass("Movies section is entered");
		scroll();
		Thread.sleep(2000);
		List<WebElement> names = driver.findElements(name);
		//List<WebElement> langs = driver.findElements(lang);
		for(int j=0;j<names.size();j++) {
			//System.out.println(names.get(j).getText()+ " - "+ langs.get(j).getText());
			System.out.println(names.get(j).getText());
			reportPass("All the languages for movies is printed in console");
			writeExcel(names.get(j).getText(), 3);
		}
		
		scroll(300);
		try {
			ScreenShot("Movies.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//tearDown();
		Thread.sleep(2000);
		endReport();
		writeExcel(": Movies Languages extracted Successfully", 2);
	}

}
