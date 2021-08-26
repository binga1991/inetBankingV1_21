package com.inetbanking.testCases;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;


public class TC_LoginDDT_002 extends BaseClass
{
    // we have all methods available in BaseClass like setup and tear down. we will write Test method and DataProvider method Only
	
	@Test(dataProvider="LoginData")
	public void loginDDT(String user, String pwd) throws InterruptedException
	{
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(user);
		logger.info("user name provided");
		lp.setPassword(pwd);
		logger.info("password provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		if(isAlertPresent()==true)
		{
			driver.switchTo().alert().accept(); //close alert
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warn("Login Failed");
		}
		else
		{
			Assert.assertTrue(true);
			logger.info("Login Passed");
			lp.clickLogout();
			Thread.sleep(3000);
			driver.switchTo().alert().accept(); //close logout alert
			driver.switchTo().defaultContent(); //this used to land in default page content
			
		}
	}
	
	public boolean isAlertPresent() // user defined method created to check alert present or not
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
	    catch(NoAlertPresentException e)
	    {
		return false;
	    }
	}
	
	@DataProvider(name="LoginData")
	String[][] getData() throws IOException
	{
		String path= System.getProperty("user.dir")+"\\src\\test\\java\\com\\inetbanking\\testData\\LoginData.xlsx";
		
		int rownum=XLUtils.getRowCount(path, "Sheet1");
		int colnum=XLUtils.getCellCount(path, "Sheet1", 1);
		
		String logindata[][]=new String[rownum][colnum];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colnum;j++)
			{
				logindata[i-1][j]=XLUtils.getCellData(path, "Sheet1", i, j); //1 and 0 to row and column
			}
		}
		
		return logindata;
	}
}