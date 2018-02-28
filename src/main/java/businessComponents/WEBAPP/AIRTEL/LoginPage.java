package main.java.businessComponents.WEBAPP.AIRTEL;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.java.executionSetup.TestParameters;
import main.java.testDataAccess.DataTable;
import main.java.utils.Utility;

public class LoginPage extends Utility {


		public LoginPage(ExtentTest test, WebDriver webdriver, DataTable dataTable,
				TestParameters testParameters, Lock lock, Connection connection) {
			super(test,webdriver,dataTable,testParameters,lock,connection);
		

		}
		
		public void login() {
			
			String environmentUrl = environmentVariables.get("EnvironmentURL");	
			webdriver.get(environmentUrl);
			test.log(LogStatus.PASS, "Application launched");
	
		}

}