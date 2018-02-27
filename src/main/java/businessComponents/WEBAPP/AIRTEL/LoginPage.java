package main.java.businessComponents.WEBAPP.AIRTEL;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import main.java.executionSetup.TestParameters;
import main.java.testDataAccess.DataTable;

public class LoginPage extends ReusableLibrary {

	By id_txt_user_name = By.id("j_username");
	By id_txt_password = By.id("j_password");
	By id_btn_login = By.id("loginBtn");

	public LoginPage(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);

	}

	public void login() {

		webdriver.get("https://172.16.32.38:8443/cats/login");

		EnterText(id_txt_user_name, "Enter Username", "catsadm");
		EnterText(id_txt_password, "Enter Password", "catscats11");
		waitCommand(id_btn_login);
		Click(id_btn_login, "Login button");

		if (webdriver.getTitle().equals("CATS CenterPoint: CenterPoint")) {
			test.log(LogStatus.PASS, "Logged into Center Point");
		}

	}

}