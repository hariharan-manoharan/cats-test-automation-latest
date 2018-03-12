package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import main.java.WEBAPP.CORE.ReusableLibrary;
import main.java.WEBAPP.CORE.pageObjectRepositories.PartsInterface;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;

public class Parts extends ReusableLibrary implements PartsInterface{
	
	public Parts(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);		
	}
	
	
	public void partcodeSearch() {
		
		//enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", "10124%");
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(2);
		
	}
	
	public void openSearch() {
		
		click(XPATH_SEARCH_BTN, "Click Search button");
		
	}

}
