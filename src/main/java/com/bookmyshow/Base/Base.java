package com.bookmyshow.Base;

import org.testng.annotations.AfterMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.extentreport.ExtentReport;
import com.google.common.io.Files;

public class Base {
	String url= null;
	String location = null;
	public String userId = null;
	
	WebDriverWait wait;
	static WebDriver driver;
	JavascriptExecutor js;
	Properties prop;
	
	static String inputFilePath = System.getProperty("user.dir") +"//src//main//java//DataTables//Input.xlsx";
	static String outputFilePath = System.getProperty("user.dir") +"//src//main//java//DataTables//Output.xlsx";
	static XSSFWorkbook wb;
	static XSSFSheet sh;
	static File file;
	static FileInputStream fileInput;
	static FileOutputStream fileOutput;
	public ExtentReports report = ExtentReport.getReportInstance();
	public ExtentTest logger;
	
	static int browserInvokeCount=1;
	
	public void readExcel()
	{
		try {
			file = new File(inputFilePath);
			fileInput = new FileInputStream(file);
			wb = new XSSFWorkbook(fileInput);
			sh = wb.getSheetAt(0);
			Row row1 = sh.getRow(0);
			Row row2 = sh.getRow(1);
			Row row3 = sh.getRow(2);
			Cell cell1 = row1.getCell(0);
			Cell cell2 = row2.getCell(0);
			Cell cell3 = row3.getCell(0);
			url = cell1.getStringCellValue(); // Fetching date format values from excel
			System.out.println("url = "+url);
			location = cell2.getStringCellValue();;
			System.out.println("Location = "+location);
			userId = cell3.getStringCellValue();
			System.out.println("UserId = "+userId);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeExcel(String data, int rownum) {
		try {
			
			fileInput = new FileInputStream(new File(outputFilePath));
			wb = new XSSFWorkbook(fileInput);
			sh = wb.getSheetAt(0);
			Row row = sh.getRow(rownum-1);
			if(row == null) {
				row = sh.createRow(rownum-1);
			}
			Cell cell = row.getCell(1);
			if (cell == null) {
				cell = row.createCell(1);
			}
			if (cell.getCellType() == CellType.BLANK) {
                cell.setCellValue(data);
            }
			fileOutput = new FileOutputStream(outputFilePath);
			wb.write(fileOutput);
			fileOutput.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
     public void invokeBrowser() {
    	prop = new Properties();

 		try {
 			prop.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/ObjectRepository/projectConfig.properties"));
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		if (prop.getProperty("browserName").matches("chrome")) {
			System.getProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (prop.getProperty("browserName").matches("edge")) {
			System.getProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		else if (prop.getProperty("browserName").matches("firefox")){
			System.getProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else {
			System.out.println("Check the Browser Name");
		}
		
		driver.manage().window().maximize();
		js = (JavascriptExecutor)driver;
		
		if(browserInvokeCount == 1) {
			logger = report.createTest("Setting up Browser");
			reportPass("Browser is Invoked");
		}
		
	}
    public void endReport() {
 		report.flush();
 	}
    
    public void reportFail(String report) {
 		logger.log(Status.FAIL, report);
 	}

 	// Function to show the passed test cases in the report
 	public void reportPass(String report) {
 		logger.log(Status.PASS, report);
 	}
 	
	public WebDriver homePage() throws InterruptedException {
		readExcel();
		driver.get(url);
		if(browserInvokeCount == 1)
		{
			logger = report.createTest("City is selected");
		}
		//logger = report.createTest("City is selected");
		driver.findElement(By.xpath("//input[@placeholder='Search for your city']")).sendKeys(location);
		Thread.sleep(1000);
		By city = By.xpath("//div/ul/li[1]/span");
		//waitClickable(city);
		driver.findElement(city).click();
		
		if(browserInvokeCount == 1)
		{
			reportPass("Chennai City is selected");
		}
		
		browserInvokeCount++;
		try {
			ScreenShot("City.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public void waitClickable(By xpath) {
		wait = new WebDriverWait(driver,Duration.ofSeconds(100));
		wait.until(ExpectedConditions.elementToBeClickable(xpath));
	}
	
	public void ScreenShot(String name) throws IOException {
		File file =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file, new File(System.getProperty("user.dir")+"\\Screenshots\\"+name));
	}
	
	public void scroll() {
		js.executeScript("window.scrollBy(0,100)", "");
	}
	
	public void scroll(int x) {
		js.executeScript("window.scrollBy(0,"+x+")", "");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}


