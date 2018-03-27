package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.java.WEBAPP.CORE.ReusableLibrary;
import main.java.WEBAPP.CORE.pageObjectRepositories.AssetInterface;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;

public class Assets extends ReusableLibrary implements AssetInterface {

	public Assets(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters, Lock lock,
			Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);
	}

	
	public void searchAssetCode() {
		
		String  assetcode=dataTable.getData("Assets", "ASSETCODE");
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", assetcode);
		click(XPATH_SEARCH_BTN, "Click Search button");

		
	}
	
	public void editAssetCode() {
		
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", "43643616");
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(2);
		
		
	}
	
	public void createAssetCode() {
		
		
		String	assetcode = generateTestData(testParameters.getCurrentKeywordColumnName(), "ASSET");
		String  getpartcode=dataTable.getData("Assets", "PARTCODE");
		String partcode = getRuntimeTestdata(getpartcode);
		String  businessUnit=dataTable.getData("Assets", "BUSINESS_UNIT");
		String  locationName=dataTable.getData("Assets", "LOCATION_NAME");
		String  locationStatus=dataTable.getData("Assets", "LOCATION_STATUS");
		
		click(XPATH_LINK_EDIT_TAB, "Click Edit button");
		enterText(ASSET_ASSETCODE_TXT, "Asset Code", assetcode);
		
		enterText(ASSET_PARTCODE_COMBO, "Part Code", partcode);
		sendTab(ASSET_PARTCODE_COMBO);
		sendTab(ASSET_CLEI_TXT);
		
		
		enterText(ASSET_BUSINESSUNIT_COMBO, "Business Unit", businessUnit);
		sendTab(ASSET_BUSINESSUNIT_COMBO);
		
		enterText(ASSET_LOCATIONNAME_COMBO, "Location Name", locationName);
		sendTab(ASSET_LOCATIONNAME_COMBO);
		
		enterText(ASSET_LOCATIONSTATUS_COMBO, "Location Status", locationStatus);
		sendTab(ASSET_LOCATIONSTATUS_COMBO);
		
		moveToElement(ASSET_NOTES_TXTAREA,"Notes");
		
		enterText(ASSET_NOTES_TXTAREA, "Notes", "Automation Asset Creation");		
		click(XPATH_SAVE_BTN, "Clicked Save button");
		
		click(XPATH_BTN_POPUP_SAVE, "Confirm Save button");
		
		if(isDisplayed( XPATH_ERROR_SAVING)) {
					
			getText(XPATH_BLOCKINGMESSAGE_CONTENT);	
			report(LogStatus.FAIL,"Asset "+assetcode+" is not created successfully");
		
		}else {
			click(XPATH_BTN_POPUP_OK,"Clicked Ok button");
			report(LogStatus.PASS,"Asset "+assetcode+" is created successfully");
			
		}					
		
}
}
