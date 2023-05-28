package com.bookmyshow.test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.bookmyshow.Base.Base;

public class MoviesList extends Base {
	WebDriverWait wait;
	JavascriptExecutor js;
	WebDriver driver;
	
	//Locators
	By movies = By.xpath("//a[text()='Movies']");
	By name=By.xpath("//*[@id=\"super-container\"]/div[2]/div[4]/div/div/div/div/div[2]/a/div/div[3]/div[1]/div");
	By lang=By.xpath("//*[@id=\"super-container\"]/div[2]/div[4]/div[2]/div/div/div/div[2]/a/div/div/div[3]/div");
	
	
	@Test
	public void moviesList() throws InterruptedException, Exception {
		invokeBrowser();
		driver = homePage();
		
		waitClickable(movies);
		driver.findElement(movies).click();
		scroll();
		
		List<WebElement> names = driver.findElements(name);
		List<WebElement> langs = driver.findElements(lang);
		for(int j=0;j<names.size();j++) {
			System.out.println(names.get(j).getText()+ " - "+ langs.get(j).getText());
		}
		
		tearDown();
	}
}

