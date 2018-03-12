package main.java.WEBAPP.CORE.pages;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import main.java.WEBAPP.CORE.ReusableLibrary;
import main.java.WEBAPP.CORE.pageObjectRepositories.CommonObjectRepository;
import main.java.WEBAPP.CORE.pageObjectRepositories.PartsInterface;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;

public class LoginPage extends ReusableLibrary implements CommonObjectRepository{

	public LoginPage(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);

	}

	public void launchApp() {
		
		webdriver.get(environmentVariables.get("EnvironmentURL"));
		
		if (webdriver.getTitle().equals("CATS CenterPoint:")) {
			test.log(LogStatus.PASS, "App with URL - <b>"+ environmentVariables.get("EnvironmentURL")+"</b> is launched successfully.");
		}else{
			test.log(LogStatus.FAIL, "App with URL - <b>"+ environmentVariables.get("EnvironmentURL")+"</b> is not launched successfully.");
		}
	}
	
	
	public void login() {		

		EnterText(ID_TEXT_USERNAME, "Enter Username", "catsadm");
		EnterText(ID_TEXT_PASSWORD, "Enter Password", "catscats11");
		waitCommand(ID_BTN_LOGIN);
		Click(ID_BTN_LOGIN, "Login button");

		if (webdriver.getTitle().equals("CATS CenterPoint: CenterPoint")) {
			test.log(LogStatus.PASS, "Login to Center Point is success");
		}else {
			test.log(LogStatus.FAIL, "Login to Center Point is not success");
		}

	}
	
}