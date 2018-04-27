package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.java.WEBAPP.CORE.ReusableLibrary;
import main.java.WEBAPP.CORE.pageObjectRepositories.PartsManufacturersInterface;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;

public class PartManufacturers extends ReusableLibrary implements PartsManufacturersInterface{
	
	
	public PartManufacturers(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);		
	}
	
	public void createNewPartManufacturer() {
		
		String partManufacturerName = generateTestData(testParameters.getCurrentKeywordColumnName(), "MFG");
		String partManufacturerDescprition = "MFG DESC - "+partManufacturerName;
		
		enterText(PARTS_MF_NAME_TXT, "Enter Manufacturer name in Name* field", partManufacturerName);
		enterText(PARTS_MF_DESCRIPTION_TXT, "Enter Manufacturer description in Description field", partManufacturerDescprition);
		click(XPATH_SAVE_BTN, "Click Save button");	
		click(XPATH_BTN_POPUP_SAVE, "Click Pop-up Save button");		
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);
		waitUntilNotDisplayed(XPATH_SNACKBAR_ITEM_MSG);	
		clickAction(XPATH_LINK_SEARCH_TAB, "Click Search tab");

	}
	
	public void searchPartManufacturer(String partManufacturerName) {
		String name = "";
		enterText(PARTS_MF_NAME_TXT, "Enter Manufacturer name in Name field", partManufacturerName);
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);		
		
		if(!getElementList(XPATH_RESULTTAB_RECORDSLIST).isEmpty()) {
		
		name = webdriver.findElement(By.xpath(String.format(XPATH_RESULTTAB_RECORD_FIELD, 1, "NAME"))).getAttribute("_value");		
		
		if(name.equals(partManufacturerName)) {
			test.log(LogStatus.PASS, "Part Manufacturer ("+name+") is retreived successfully.");	
		}else {
			test.log(LogStatus.FAIL, "Part Manufacturer ("+name+") is not retreived successfully.");
		}
		}

	}



}
