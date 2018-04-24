package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.java.WEBAPP.CORE.ReusableLibrary;
import main.java.WEBAPP.CORE.pageObjectRepositories.PartsInterface;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;

public class Parts extends ReusableLibrary implements PartsInterface{
	
	public Parts(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);		
	}
	
	


	
	
	public void createNewPart(String isSerialized) {

		String partcode = null;
		
		if(isSerialized.equalsIgnoreCase("Y")) {
		partcode = generateTestData(testParameters.getCurrentKeywordColumnName(), "AS");
		}else {
		partcode = generateTestData(testParameters.getCurrentKeywordColumnName(), "ANS");	
		}		
		
		enterText(PARTS_PARTCODE_COMBO_EDIT, "Enter Part code in part code field", partcode);
		enterText(PARTS_DESCRIPTION_TXT, "Enter Part code in part code field", "DESCRIPTION "+ partcode);
		if(isSerialized.equalsIgnoreCase("N")) {
		click(PARTS_SERIALIZEDINVENTORY_EDIT_CHECKBOX, "Un check ");	
		}
		selectValueByVisibleText(PARTS_MANUFACTURER_SELECT, "TEST", "Select Manufacturer - TEST");
		click(XPATH_SAVE_BTN, "Click Save button");	
		click(XPATH_BTN_POPUP_SAVE, "Click Pop-up Save button");
		//click(XPATH_BTN_POPUP_OK, "Click Pop-up Ok button");
		waitUntilNotDisplayed(By.xpath("//div[@class='snackbar_item_message']"));	
		clickAction(XPATH_LINK_SEARCH_TAB, "Click Search tab");
		clearText(PARTS_PARTCODE_COMBO);
		enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", partcode);
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);		
		clickEditIcon(1);
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);	
		
		if(webdriver.findElement(PARTS_PARTCODE_COMBO_EDIT).getAttribute("value").equals(partcode)) {
			test.log(LogStatus.PASS, "New Part code is created successfully - Partcode: "+partcode);	
		}else {
			test.log(LogStatus.FAIL, "New Part code is not created successfully");
		}
				
	}
	
	
	public void partcodeSearch(String partCode) {
		
		enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", partCode);		
		waitCommand(By.xpath("//div[@class='combo_results_controls']//div[@class='drag-handle']"));
		click(By.xpath("//td[@style='width: 200px;'][contains(text(),'"+partCode+"')]"), "Click Part code "+partCode);
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);		
		
	}
	

	

	

}
