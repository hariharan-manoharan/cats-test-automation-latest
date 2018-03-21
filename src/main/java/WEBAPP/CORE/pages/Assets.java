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

	
	public void assetCodeSearch() {
		
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", "43643616");
		click(XPATH_SEARCH_BTN, "Click Search button");

		
	}
	
	public void editAssetCode() {
		
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", "43643616");
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(2);
		
		
	}
	
	public void createAssetCode() {
		
		click(XPATH_LINK_EDIT_TAB, "Click Edit button");
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", "AASSETCODE_001");
		enterText(ASSET_PARTCODE_COMBO, "Part Code", "10753");
		enterText(ASSET_BUSINESSUNIT_COMBO, "Business Unit", "ALPHA BUTYPE1");
		enterText(ASSET_LOCATIONNAME_COMBO, "Location Name", "1BL1184");
		enterText(ASSET_LOCATIONSTATUS_COMBO, "Location Status", "AVAILABLE");
		enterText(ASSET_NOTES_TXT, "Notes", "Automation Asset Creation");
		
		click(XPATH_LINK_EDIT_TAB, "Click Edit button");
		
		
		
		
		
		
	}
}
