package main.java.WEBAPP.CORE;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.java.WEBAPP.CORE.pageObjectRepositories.CommonObjectRepository;
import main.java.WEBAPP.CORE.pages.Assets;
import main.java.WEBAPP.CORE.pages.LoginPage;
import main.java.WEBAPP.CORE.pages.PartManufacturers;
import main.java.WEBAPP.CORE.pages.Parts;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;
import main.java.framework.utils.Utility;


public class FunctionalComponents extends ReusableLibrary{

	public FunctionalComponents(ExtentTest test, WebDriver webdriver, DataTable dataTable,
			TestParameters testParameters, Lock lock, Connection connection) {
		super(test,webdriver,dataTable,testParameters,lock,connection);
	}
	
	public void login() {
		LoginPage loginPage = new LoginPage(test,webdriver,dataTable,testParameters,lock,connection);
		loginPage.launchApp();
		loginPage.login();
	}		
	
	public void selectDataForm() {
		
		String dataformFolder = dataTable.getData("Data", "DATAFORM_FOLDER");
		String dataform = dataTable.getData("Data", "DATAFORM");
		String dataFormLabel = dataTable.getData("Data", "DATAFORMLABEL");
		
		selectDataForm(dataformFolder, dataform, dataFormLabel);
	}
	
/*	public void createSandNSPartcode() {
		Parts parts = new Parts(test,webdriver,dataTable,testParameters,lock,connection);	
		parts.clickEditTab();
		parts.createNewPart("N");
		parts.clickClearBtn();
		parts.clickClearPopupBtn();
		parts.createNewPart("Y");
	}*/
	
	public void createNSPartcode() {
		
		Parts parts = new Parts(test,webdriver,dataTable,testParameters,lock,connection);		
		parts.clickEditTab();
		parts.createNewPart("N");
		parts.clickClearBtn();
		parts.clickClearPopupBtn();		
	}	
		
	public void createSPartcode() {		
		
		Parts parts = new Parts(test,webdriver,dataTable,testParameters,lock,connection);		
		parts.clickEditTab();
		parts.createNewPart("Y");
		parts.clickClearBtn();
		parts.clickClearPopupBtn();	
	}
	
	

	public void deleteRecord() {
		
		int rowNumber = Integer.parseInt(dataTable.getData("Data", testParameters.getCurrentKeywordColumnName()));
		
		Parts parts = new Parts(test,webdriver,dataTable,testParameters,lock,connection);	
		parts.selectRecordResultTab(rowNumber);
		parts.clickDeleteBtn();
		parts.clickDeletePopupBtn();	
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);
		
		if(getText(XPATH_RESULTTAB_PAGINATION).equals("No Results")) {
			test.log(LogStatus.PASS, "Record deleted successfully.");	
		}else {
			test.log(LogStatus.FAIL, "Record not deleted.");
		}
				
	}
	
	public void searchPartcode() {
		
		String partCode = getRuntimeTestdata(dataTable.getData("Data", testParameters.getCurrentKeywordColumnName()));
		
		Parts parts = new Parts(test,webdriver,dataTable,testParameters,lock,connection);	
		parts.partcodeSearch(partCode);
		
	}
	
	
	public void createPartMFG() {
		
		PartManufacturers partManufacturers = new PartManufacturers(test,webdriver,dataTable,testParameters,lock,connection);
		partManufacturers.clickEditTab();
		partManufacturers.createNewPartManufacturer();
				
	}
	
	
	public void searchPartMFG() {
		
		String partMFG = getRuntimeTestdata(dataTable.getData("Data", testParameters.getCurrentKeywordColumnName()));
		
		PartManufacturers partManufacturers = new PartManufacturers(test,webdriver,dataTable,testParameters,lock,connection);
		partManufacturers.searchPartManufacturer(partMFG);
				
	}
	
	public void createAssetcode() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.createOReditAssetCode();
	}
	
	public void searchAssetCode() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.searchAsset();
		waitUntilNotDisplayed(XPATH_BLOCKINGMESSAGE);
		assets.selectRecordResultTab(1);		
	}
	
	public void clearAssetCode() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.clickClearBtn();
		assets.clickClearPopupBtn();		
	}
	
	public void editAssetCode() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.createOReditAssetCode();

	}
	


}
