package main.java.businessComponents.WEBAPP.AIRTEL;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import main.java.executionSetup.TestParameters;
import main.java.testDataAccess.DataTable;

public class LoginPage extends ReusableLibrary implements WebObjectRepository{

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

		EnterText(id_txt_user_name, "Enter Username", "catsadm");
		EnterText(id_txt_password, "Enter Password", "catscats11");
		waitCommand(id_btn_login);
		Click(id_btn_login, "Login button");

		if (webdriver.getTitle().equals("CATS CenterPoint: CenterPoint")) {
			test.log(LogStatus.PASS, "Login to Center Point is success");
		}else {
			test.log(LogStatus.FAIL, "Login to Center Point is not success");
		}

	}
	
	public void selectDataForm(String dataform, String dataforFolder) {
		
		Click(xpath_client_folder, "Click Client folder");
		Click(By.xpath(String.format(xpath_dataform_folder,dataforFolder)), "Click Data form folder - "+dataforFolder);
		Click(By.xpath(String.format(xpath_data_form,dataform)), "Click Data form - " +dataform);
		
		
	}
	
	public void clickSearchButton() {
		
	}

}