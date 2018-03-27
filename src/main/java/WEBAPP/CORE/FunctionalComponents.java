package main.java.WEBAPP.CORE;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import main.java.WEBAPP.CORE.pageObjectRepositories.CommonObjectRepository;
import main.java.WEBAPP.CORE.pages.Assets;
import main.java.WEBAPP.CORE.pages.LoginPage;
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
	
	
	public void assets_dataform() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.createAssetCode();
		assets.assetCodeSearch();
		assets.editAssetCode();
	}
	
	public void createAssetcode() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.createAssetCode();
		assets.clickClearBtn();
		assets.clickClearPopupBtn();

	}
	
	public void editAssetcode() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.searchAssetCode();
		assets.createAssetCode();

	}

}
