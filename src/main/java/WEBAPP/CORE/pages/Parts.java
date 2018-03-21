package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
	
	
	public void partcodeSearch() {
		
		//enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", "10124%");
		click(By.xpath(String.format(XPATH_COMBOBOX_1, "Part Code")), "Click Part Code dropdown");
		waitCommand(By.xpath("//div[@class='combo_results_controls']//div[@class='drag-handle']"));
		click(By.xpath("//td[@style='width: 200px;'][contains(text(),'10124')]"), "Click Part code 10124");
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(1);
		
	}
	
	public void openSearch() {
		
		click(XPATH_SEARCH_BTN, "Click Search button");
		
	}
	
	
	public void createNewPartSerialized() {
		
		String partcode = "ASPARTCODE_"+generateRandomNum(1000);
		
		click(XPATH_LINK_EDIT_TAB, "Click Edit tab");
		enterText(PARTS_PARTCODE_COMBO_EDIT, "Enter Part code in part code field", partcode);
		enterText(PARTS_DESCRIPTION_TXT, "Enter Part code in part code field", "DESCRIPTION "+ partcode);
		selectValueByVisibleText(PARTS_MANUFACTURER_SELECT, "ALPHA TECHNOLOGIES", "Select Manufacturer - ALPHA TECHNOLOGIES");
		click(XPATH_SAVE_BTN, "Click Save button");	
		click(XPATH_BTN_POPUP_SAVE, "Click Pop-up Save button");
		click(XPATH_BTN_POPUP_OK, "Click Pop-up Ok button");
		click(XPATH_LINK_SEARCH_TAB, "Click Search tab");
		enterText(PARTS_PARTCODE_COMBO, "Enter Part code in part code field", partcode);
		click(XPATH_SEARCH_BTN, "Click Search button");
		waitUntilNotDisplayed(By.xpath("//div[@class='blocking-screen']"));		
		clickEditIcon(1);

	}
	

	public void createNewPartNonSerialized() {
		
	}
	
	
	
	public void selectValueByVisibleText(By by, String text, String reportName) {
		
		try {
		waitCommand(by);		
		Select select = new Select(webdriver.findElement(by));		
		select.selectByVisibleText(text);
		takeScreenshot(reportName);
		}catch(Exception e) {
			test.log(LogStatus.FAIL, reportName + " is not successfull");
		}
		
	}
	

}
