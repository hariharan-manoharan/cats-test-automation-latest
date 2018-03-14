package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import main.java.WEBAPP.CORE.ReusableLibrary;
import main.java.WEBAPP.CORE.pageObjectRepositories.AssetInterface;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;

public class Assets extends ReusableLibrary implements AssetInterface {

	public Assets(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters, Lock lock,
			Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);
	}

	
	public void assetcodesearch() {
		
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", "43643616");
		click(XPATH_SEARCH_BTN, "Click Search button");

		
	}
	
	public void editassetcode() {
		
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", "43643616");
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(2);
		
		
	}
}
