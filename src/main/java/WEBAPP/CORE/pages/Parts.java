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
	
	

	public void clickSearchBtn() {
		
		if(webdriver.findElement(XPATH_SEARCH_BTN).isEnabled()) {
		click(XPATH_SEARCH_BTN, "Click Search button");
		}
		
	}
	
	
	public void clickClearBtn() {
		
		if(webdriver.findElement(XPATH_CLEAR_BTN).isEnabled()) {
		click(XPATH_CLEAR_BTN, "Click Clear button");
		}
		
	}
	
	public void clickClearPopupBtn() {
		
		if(webdriver.findElement(XPATH_BTN_POPUP_CLEAR).isEnabled()) {
		click(XPATH_BTN_POPUP_CLEAR, "Click Clear Popup button");
		}
		
	}
	
	public void clickSearchTab() {
		
		if(!getText(XPATH_LINK_ACTIVE_TAB).equals("Search")) {
		click(XPATH_LINK_SEARCH_TAB, "Click Search button");
		}
		
	}
	
	public void clickResultTab() {
		
		if(!getText(XPATH_LINK_ACTIVE_TAB).equals("Result")) {
		click(XPATH_LINK_RESULTS_TAB, "Click Search button");
		}
		
	}
	
	public void clickEditTab() {
		
		if(!getText(XPATH_LINK_ACTIVE_TAB).equals("Edit")) {
		click(XPATH_LINK_EDIT_TAB, "Click Search button");
		}
		
	}
	
	
	public void clearText(By by) {
		
		webdriver.findElement(by).clear();
		
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
		selectValueByVisibleText(PARTS_MANUFACTURER_SELECT, "ALPHA TECHNOLOGIES", "Select Manufacturer - ALPHA TECHNOLOGIES");
		click(XPATH_SAVE_BTN, "Click Save button");	
		click(XPATH_BTN_POPUP_SAVE, "Click Pop-up Save button");
		click(XPATH_BTN_POPUP_OK, "Click Pop-up Ok button");
		click(XPATH_LINK_SEARCH_TAB, "Click Search tab");
		clearText(PARTS_PARTCODE_COMBO);
		enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", partcode);
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(1);
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));	
		
		if(webdriver.findElement(PARTS_PARTCODE_COMBO_EDIT).getAttribute("value").equals(partcode)) {
			test.log(LogStatus.PASS, "New Part code is created successfully - Partcode: "+partcode);	
		}else {
			test.log(LogStatus.FAIL, "New Part code is not created successfully");
		}
				
	}
	
	
	public void partcodeSearch(String partCode) {
		
		enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", partCode);
		click(By.xpath(String.format(XPATH_COMBOBOX_1, "Part Code")), "Click Part Code dropdown");
		waitCommand(By.xpath("//div[@class='combo_results_controls']//div[@class='drag-handle']"));
		click(By.xpath("//td[@style='width: 200px;'][contains(text(),'10124')]"), "Click Part code "+partCode);
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		
	}
	
	public void selectRecordResultTab(int i) {
		
		List<WebElement> records = webdriver.findElements(XPATH_RESULTTAB_EDITICON);
		
		if(records.size()>0) {
			
			records.get(i-1).click();
			report(LogStatus.PASS, "selectRecordResultTab - Record - "+i+" is selected.");
			
		}else {
			test.log(LogStatus.WARNING, "selectRecordResultTab - No records found in Resukt tab.");
		}
		
	}
	
	

	

}
