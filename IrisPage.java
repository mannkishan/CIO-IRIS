package com.pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.utility.ConfigReader;

/**
 * 
 * @author ajay.sopan.raut
 *
 */

public class IrisPage {

	WebDriver driver;
	ConfigReader conf = new ConfigReader();
	Calendar cal = Calendar.getInstance();

	public IrisPage(WebDriver driver) // constructor
	{
		this.driver = driver;
	}

	public String getCurrentMonth() {

		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date dt = new Date();
		String month;

		String todaydate = dateFormat.format(dt);

		int currentdate = Integer.parseInt(todaydate);
		
		//System.out.println(currentdate);

		if (currentdate <= 8) {
			cal.add(Calendar.MONTH, -(2));
			month = new SimpleDateFormat("MMMMMMMM").format(cal.getTime());
			//System.out.println(month + " from IF");

		} else {
			cal.add(Calendar.MONTH, -(1));
			month = new SimpleDateFormat("MMMMMMMM").format(cal.getTime());
			//System.out.println(month + " from Else");
		}
		return month;
	}

	public String[] getmonthsname(List<WebElement> mnth) {
		
		int i=0;
		
		String[] a =new String[mnth.size()];
		
		for(WebElement e:mnth){
			
			String[] s1 = e.getText().split(" ");
			
			a[i]=s1[0].trim();
			i++;
		}
		return a;
	}
	
	public String formatMonth(String month) throws ParseException{
		
        DateFormat formatDate = new SimpleDateFormat("MMM");
		
		Date d= formatDate.parse(month);
		
		String mon = formatDate.format(d).toString();
	
		return mon;
	}

	public String getCSLCompliancePer(String cslMet,String totalCsl){
		
		return (String.format("%.11g%n", (Double.parseDouble(cslMet)/Double.parseDouble(totalCsl))*100)).toString();	
	}
	
	
	
}
