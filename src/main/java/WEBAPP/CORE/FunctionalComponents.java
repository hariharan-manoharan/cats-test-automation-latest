package main.java.WEBAPP.CORE;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import main.java.WEBAPP.CORE.pages.LoginPage;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;
import main.java.framework.utils.Utility;

public class FunctionalComponents extends Utility{

	public FunctionalComponents(ExtentTest test, WebDriver webdriver, DataTable dataTable,
			TestParameters testParameters, Lock lock, Connection connection) {
		super(test,webdriver,dataTable,testParameters,lock,connection);
		
		

	}
	
	public void login() {
		LoginPage loginPage = new LoginPage(test,webdriver,dataTable,testParameters,lock,connection);
		loginPage.launchApp();
		loginPage.login();
		loginPage.selectDataForm("Assets", "Assets");
	}	
	
	public void openSearch(String dataForm) {
		
		
						
	}
	
	
	

}