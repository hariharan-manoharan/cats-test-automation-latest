package main.java.businessComponents.WEBAPP.AIRTEL;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;


import main.java.executionSetup.TestParameters;
import main.java.testDataAccess.DataTable;
import main.java.utils.Utility;

public class FunctionalComponents extends Utility{

	public FunctionalComponents(ExtentTest test, WebDriver webdriver, DataTable dataTable,
			TestParameters testParameters, Lock lock, Connection connection) {
		super(test,webdriver,dataTable,testParameters,lock,connection);
		
		

	}
	
	public void login() {
		LoginPage log = new LoginPage(test,webdriver,dataTable,testParameters,lock,connection);
		log.login();
	

	}	

}
