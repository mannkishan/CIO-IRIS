package com.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pages.IrisPage;
import com.relevantcodes.extentreports.LogStatus;
import com.utility.ExcelGeneric;

/**
 * 
 * @author ajay.sopan.raut
 *
 */

public class IrisTest extends LoginTest {

	WebElement copyrightMessage;
	IrisPage irisPage = new IrisPage(driver);
	ExcelGeneric excel = new ExcelGeneric(conf.getTestDataExcelPath());
	String value, cslCompPercentage,temp;
	ArrayList<String> dbValues;
	int count=0;

	@Test(dependsOnMethods = "testLogin")
	public void IrisHomePageUIValidation() throws InterruptedException {

		logger = rpt.startTest("IrisHomePageUIValidation",
				"UI Element Validation").assignCategory("Functional");
		// IRIS logo
		if (driver.findElement(By.xpath(conf.getElementIrisLogo())).getText()
				.equals("IRIS")) {
			logger.log(LogStatus.PASS, "IRIS Logo Is Present!");
			System.out.println("IRIS Logo Is Present!");
		} else {
			logger.log(LogStatus.FAIL, "IRIS Logo Is Not Present!");
			System.out.println("IRIS Logo Is Not Present!");
		}
		// User's Profile Pic
		WebElement profilePic = driver.findElement(By.xpath(conf
				.getElementProfilePic()));

		if (profilePic.isDisplayed()) {
			logger.log(LogStatus.PASS, "Profile Pic Is Present!");
			System.out.println("Profile Pic Is Present!");
		} else {
			logger.log(LogStatus.FAIL, "Profile Pic Is Not Present!");
			System.out.println("Profile Pic Is Not Present!");
		}

		// LoggedIn UserName
		String s = driver.findElement(
				By.xpath(conf.getElementloggedInUserName())).getText();
		String[] loggedInUserInfo = s.split("\\(|\\)");

		if (loggedInUserInfo[0].trim().equals(conf.getUserName())
				|| loggedInUserInfo[1].equals("DEx Admin")) {
			logger.log(LogStatus.PASS, "Logged In User Name and Role Is Valid!");
			System.out.println("Logged In User Name and Role Is Valid!");
			
		} else {
			logger.log(LogStatus.FAIL,
					"Logged In User Name and Role Is InValid!");
			System.out.println("Logged In User Name and Role Is InValid!");
			
		}

		// Logout link
		if (driver.findElement(By.xpath(conf.getElementLogout())).isDisplayed()) {
			logger.log(LogStatus.PASS, "Logout Link Is Present!");
			System.out.println("Logout Link Is Present!");
			
		} else {
			logger.log(LogStatus.FAIL, "Logout Link Is Not Present!");
			System.out.println("Logout Link Is Not Present!");
		}

		// Copyright Text
		copyrightMessage = driver
				.findElement(By.xpath(conf.getElementFooter()));
		if (copyrightMessage.getText().equals(conf.getElementFooterText())) {
			logger.log(LogStatus.PASS, "The Copyright Message Is Valid : "
					+ copyrightMessage.getText());
			System.out.println("The Copyright Message Is Valid" + copyrightMessage.getText());
		} else {
			logger.log(LogStatus.FAIL, "The Copyright Message Is InValid : "
					+ copyrightMessage.getText());
			System.out.println("The Copyright Message Is InValid" + copyrightMessage.getText());
		}
	}

	@Test(dependsOnMethods = "IrisHomePageUIValidation")
	public void VerticalMenuValidation() throws InterruptedException {

		logger = rpt.startTest("IrisVerticalMenuValidation",
				"Top Left Vartical Menu Validation").assignCategory(
				"Functional");

		driver.findElement(By.xpath(conf.getElementIrisMenu())).click();
		//Vertical Menu
		verifyData(conf.getTopLeftMenuSheetName(), conf.getVerticalMenu());
		System.out.println("IRSI menu is selected");

	}

	@Test(dependsOnMethods = "VerticalMenuValidation")
	public void DashboardMenuValidation() throws InterruptedException {

		logger = rpt.startTest("DashboardMenuValidation",
				"Dashboard Menu Validation").assignCategory("Functional");

		driver.findElement(By.xpath(conf.getElementDashboard())).click();
		//Dashboard Menu
		verifyData(conf.getDashboardSheetName(), conf.getDashboardMenu());
		System.out.println("Dashboard sub-menu is selected");
	}

	@Test(dependsOnMethods = "DashboardMenuValidation")
	public void VerifyCockpitReportOption() throws InterruptedException,
			IOException, ParseException, SQLException, AWTException {

		logger = rpt.startTest("VerifyCockpitReportOption",
				"Verification Of Cockpit Report Tabs").assignCategory(
				"Functional");

		driver.findElement(By.xpath(conf.getElementCockpit())).click();
		System.out.println("Clicked on Cokpit option from the menu");
		 /*new WebDriverWait(driver, 25).until(ExpectedConditions
		 .visibilityOfElementLocated(By.xpath(conf.getElementIoOperation())));*/
		 
         Thread.sleep(25000);

		// month verification
		String selectMonth = loginPage.splitValues(
				loginPage.getElement(conf.getElementCockpitMonth())
						.getAttribute("value").toString(), "-");

		if (selectMonth.equals(irisPage.getCurrentMonth())) {
			System.out.println("Month is valid : " + selectMonth);
		} else {
			System.out.println("Month is not valid : " + selectMonth);
		}
		 //Verify the active tab
		 WebElement activeTab = driver.findElement(By.xpath(conf.getElementIoOperation()));
				 
				 if (activeTab.getAttribute("class").trim()
				 .equals(conf.getElementActiveTab().trim())) {
				  
				  logger.log(LogStatus.PASS,
				  "IO Operations Report Tab Is In Focus and Selected By Default");
				  System.out.println("IO Operations Report Tab Is In Focus and Selected By Default");
				  } else { logger.log(LogStatus.FAIL,
				  "IO Operations Report Tab Is Not In Focus"); }
				 System.out.println("IO Operations Report Tab Is Not In Focus");
		
				 //Tab
		verifyData(conf.getCockpitReportSheetName(), conf.getCockpitReportTab());
		 
		driver.findElement(By.xpath(conf.getElementCriticalSLA())).click();
		
		System.out.println(driver.findElements(By.tagName("iframe")).size());
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("window.scrollBy(0,400)", "");
		
		WebElement ele = driver.findElement(By.xpath(conf.getElementDownload()));
		
		ele.click();
		
		js.executeScript("arguments[0].scrollIntoView()", ele);

		Thread.sleep(2000);
		
		driver.findElement(By.xpath(conf.getElementData())).click();
		
		String parentHandle = driver.getWindowHandle();

		driver.switchTo().frame(0);
		
		Thread.sleep(1000);
		
		// js.executeScript("arguments[0].click()", download);	

		// js.executeScript("arguments[0].click()", data);

		for (String currentWindowHandle : driver.getWindowHandles()) {
			driver.switchTo().window(currentWindowHandle);
		}

		driver.manage().window().maximize();

		/*
		 * int
		 * rowCount=loginPage.getElements("//table[@class='datatable']/tbody//tr"
		 * ).size(); System.out.println(rowCount);
		 */

		Thread.sleep(2000);

		List<WebElement> month = driver.findElements(By.xpath(conf
				.getElementNoOfMonth()));
		// System.out.println(month.size());

		String[] monthsname = irisPage.getmonthsname(month);

		for (int i = 0; i < monthsname.length; i++) {

			try {
				// TOTAL CSL

				String totalCSL = (driver.findElement(By.xpath(conf
						.getElementTotalCSL1()
						+ monthsname[i]
						+ conf.getElementTotalCSL2())).getText());
				dbValues = dbData.sqlConnection(conf.getCriticalSLAQuery1()
						+ irisPage.formatMonth(monthsname[i])
						+ conf.getCriticalSLAQuery2());
				System.out.println(totalCSL + "  " + dbValues.get(0));
				logger.log(LogStatus.INFO, "Below Are The Value For Month : "
						+ monthsname[i]);
				if (totalCSL.equals(dbValues.get(0))) {
					logger.log(LogStatus.PASS,
							"Total CSL Values Matched. Value From Application : "
									+ totalCSL + " Value From DataBase : "
									+ dbValues.get(0));
				} else {
					logger.log(LogStatus.FAIL,
							"Total CSL Values Did Not Matched. Value From Application : "
									+ totalCSL + " Value From DataBase : "
									+ dbValues.get(0));
				}

				// CSL MET
				String metCSL = (driver.findElement(By.xpath(conf
						.getElementCSLMet1()
						+ monthsname[i]
						+ conf.getElementCSLMet2())).getText());
				System.out.println(metCSL + "  " + dbValues.get(1));
				if (metCSL.equals(dbValues.get(1))) {
					logger.log(LogStatus.PASS,
							"CSL MET Values Matched. Value From Application : "
									+ metCSL + " Value From DataBase : "
									+ dbValues.get(1));
				} else {
					logger.log(LogStatus.FAIL,
							"CSL MET Values Did Not Matched. Value From Application : "
									+ metCSL + " Value From DataBase : "
									+ dbValues.get(1));
				}

				// CSL Compliance
				String cslCompliance = (driver.findElement(By.xpath(conf
						.getElementCSLCompliance1()
						+ monthsname[i]
						+ conf.getElementCSLCompliance2())).getText());

				cslCompPercentage = irisPage.getCSLCompliancePer(
						dbValues.get(1), dbValues.get(0));
				System.out.println(cslCompliance + "  " + cslCompPercentage);

				if (cslCompliance.trim().equals(cslCompPercentage.trim())) {
					logger.log(LogStatus.PASS,
							"CSL Complience Values Matched. Value From Application : "
									+ cslCompliance + " Value From DataBase : "
									+ cslCompPercentage);
				} else {
					logger.log(LogStatus.FAIL,
							"CSL Complience Values Did Not Matched. Value From Application : "
									+ cslCompliance + " Value From DataBase : "
									+ cslCompPercentage);
				}
			} catch (Exception e) {
				if (e.toString().contains("NoSuchElementException")) {
					// logger.log(LogStatus.INFO,
					// monthsname[i]+" Month Values Are Not Available In IRIS Cockpit!");
				}
			}

		}

		driver.close();

		driver.switchTo().window(parentHandle);
		
	}
	@Test(dependsOnMethods = "DashboardMenuValidation")
	public void VerifyMoreDetailsSection() {
	//More Details
		logger = rpt.startTest("VerifyMoreDetailsSection",
				"Verification More Details Option Of Critical SLA Graph").assignCategory(
				"Functional");
		try {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		robot.keyRelease(KeyEvent.VK_PAGE_UP);
		
		System.out.println(driver.findElements(By.tagName("iframe")).size());

		driver.switchTo().frame(0);
		
		//WebElement incidentBreakup = driver.findElement(By.xpath("//span[text()='Incident Breakup']"));
		WebElement criticalSLAMoreDetails = loginPage.getElement(conf.getElementCriticalSLAMoreDetails());
		
			
		Thread.sleep(2000);
		
		criticalSLAMoreDetails.click();
		//to do
		if(loginPage.getElement(conf.getElementSLAHeader()).isDisplayed()){
			logger.log(LogStatus.PASS,"Successfully Navigated To More Details!");
		}else{
			logger.log(LogStatus.FAIL,"Unable To Navigated To More Details!");
		}
		} catch (AWTException e) {
			//System.out.println(e.getMessage());
		}
		catch (InterruptedException e) {
			//System.out.println(e.getMessage());
		}
		

	}

	public void verifyData(String sheetName,String elements){
 		
        ArrayList<String> excelValues = new ArrayList<String>();
		int row = excel.getRow(sheetName);
		for(int j=0; j<=row; j++){
			excelValues.add(excel.getData(j, 0, sheetName));
	  }
		
		List<WebElement> appValues = loginPage.getElements(elements);
		
		for(String s : excelValues){
		for (int i = 0; i <appValues.size(); i++) {
			if (appValues.get(i).getText().trim().equals(s)) {

				logger.log(LogStatus.INFO, "The Menu '" + appValues.get(i).getText()+ "' Is Present");
				System.out.println("The Menu '" + appValues.get(i).getText()+ "' Is Present");
				
				count=1;
		       }else{
		    	temp = appValues.get(i).getText();
					count = 2;
		       }
			
			if(count==1){
				count = 0;
				break;
			}
			
	    	}
		if(count==2){
			logger.log(LogStatus.FAIL, "The Menu '" + temp + "' Is Not Present");
			System.out.println("The Menu '" + temp + "' Is Not Present");
			
			count = 0;	
		  }
		}		
}

	
}
