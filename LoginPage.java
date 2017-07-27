package com.pages;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.utility.ConfigReader;

/**
 * 
 * @author ajay.sopan.raut
 *
 */

public class LoginPage {
	
	WebDriver driver;
	ConfigReader conf = new ConfigReader();

	public LoginPage(WebDriver driver) // constructor
	{
		this.driver = driver;
	}

	public void openBrowser() {
		driver.get(conf.getApplicationURL());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public void setUserName() {
		driver.findElement(By.xpath(conf.getElementUserName())).sendKeys(
				conf.getUserName());
	}

	public void setPassWord() {
		driver.findElement(By.xpath(conf.getElementPassword())).sendKeys(
				conf.getPassword());
	}
	
	public String getPassword(){
		return JOptionPane.showInputDialog(null,"What is your Password?");
	}
	

	public void clickSignInButton() {
		driver.findElement(By.xpath(conf.getElementSubmitButton())).click();
	}
	public WebElement getElement(String str) {
		WebElement element = driver.findElement(By.xpath(str));
		return element;
	}
   public String splitValues(String str, String pattern) throws ParseException {
	   
		String[] s1 = str.split(pattern);
		return s1[0].trim();
	}
   public List<WebElement> getElements(String str) {
		List<WebElement> elements = driver.findElements(By.xpath(str));
		return elements;
	}
   

}
