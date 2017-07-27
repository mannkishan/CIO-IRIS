package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.pages.LoginPage;
import com.utility.BrowserSelection;
import com.utility.ConfigReader;
import com.utility.DBDataRead;
import com.utility.TakeScreenshot;

/**
 * 
 * @author ajay.sopan.raut
 *
 */

public class LoginTest {

	WebDriver driver;
	LoginPage loginPage;
	ExtentReports rpt;
	ExtentTest logger;
	WebDriverWait wait;
	BrowserSelection browserSelect = new BrowserSelection();
	DBDataRead dbData=new DBDataRead();

	String passwd;
	ConfigReader conf = new ConfigReader();

	@Parameters({ "browser"} )
	@BeforeTest
	public void beforeTest(@Optional("IE") String browser) {

		driver = browserSelect.browserSelection(browser);

		loginPage = new LoginPage(driver);

		rpt = new ExtentReports("./TestReport.html", true);
		rpt.loadConfig(LoginTest.class, "resources", "config.xml");
       // passwd=loginPage.getPassword();
	}

	@Test(testName = "Launching the Billing&Receivables url")
	public void initiateBrowser() {
		try {
			logger = rpt.startTest("Browser Initialisation", "Launching URL")
					.assignCategory("Functional");
			// Opening the browser
			loginPage.openBrowser();
			logger.log(LogStatus.PASS,"URL Loaded Successufully for: " + conf.getApplicationURL());
			System.out.println("URL Loaded Successufully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods = "initiateBrowser")
	public void testLogin() {
		try {
			logger = rpt.startTest("Login Test",
					"Providing Credetials For Login").assignCategory(
					"Functional");
			
			// Log in to application
               Thread.sleep(10000);
			if (!driver.getTitle().equals("IRIS")) {
				loginPage.setUserName();
				logger.log(LogStatus.INFO, "UserName Entered");
				System.out.println("User Name Entered");
				//loginPage.getElement(conf.getElementPassword()).sendKeys(passwd);
				loginPage.setPassWord();
				logger.log(LogStatus.INFO, "Password Entered");
				System.out.println("Password entered");
				loginPage.clickSignInButton();
				logger.log(LogStatus.INFO, "Clicked on SignIn Button");
				System.out.println("Clicked on sing Button");
				new WebDriverWait(driver, 8000).until(ExpectedConditions
						.titleContains(conf.getHomePageTitle()));

				if (driver.getTitle().equals(conf.getHomePageTitle())) {
					logger.log(LogStatus.PASS,
							"Login Successful And HomePage Appeared!");
					System.out.println("Login Successful And HomePage Appeared!");
				} else {
					logger.log(LogStatus.FAIL, "Provide Valid Credentials");
					Reporter.getCurrentTestResult().setStatus(
							ITestResult.FAILURE);
					System.out.println("Login UnSuccessful! Provide Valid Credentials");
				}

			} else {
				new WebDriverWait(driver, 5000).until(ExpectedConditions
						.titleContains(conf.getHomePageTitle()));

				if (driver.getTitle().equals(conf.getHomePageTitle())) {
					logger.log(LogStatus.PASS,
							"Login Successful And HomePage Appeared!");
				} else {
					logger.log(LogStatus.FAIL, "Provide Valid Credentials");
					Reporter.getCurrentTestResult().setStatus(
							ITestResult.FAILURE);
				}

			}

		} catch (Exception e) {
			System.out.println("Page Didnt Load In Time");
		}

	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "ScreenShot: " + logger.addScreenCapture(TakeScreenshot.captureScreenShot(driver, result.getName())));
		} else {
			logger.log(LogStatus.ERROR,
					"Dependant test caese(s) will be skipped if any..! ");
			logger.log(LogStatus.FAIL, "ScreenShot: " + logger.addScreenCapture(TakeScreenshot.captureScreenShot(driver, result.getName())));
		}
		rpt.endTest(logger);
		rpt.flush();
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
		rpt.close();
	}

}
