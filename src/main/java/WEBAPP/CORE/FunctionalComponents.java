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
import net.sf.ehcache.transaction.xa.commands.Command;

public class FunctionalComponents extends Utility{

	public FunctionalComponents(ExtentTest test, WebDriver webdriver, DataTable dataTable,
			TestParameters testParameters, Lock lock, Connection connection) {
		super(test,webdriver,dataTable,testParameters,lock,connection);
		
		

	}
	
	public void login() {
		LoginPage loginPage = new LoginPage(test,webdriver,dataTable,testParameters,lock,connection);
		loginPage.launchApp();
		loginPage.login();
	}	
	
	public void parts_dataform() {
		Parts parts = new Parts(test,webdriver,dataTable,testParameters,lock,connection);
		parts.selectDataForm("Parts", "Parts", "Parts");
		parts.openSearch();
	}
	
	public void assets_dataform() {
		Assets assets = new Assets(test,webdriver,dataTable,testParameters,lock,connection);
		assets.selectDataForm("Assets", "Assets", "Assets");
		assets.assetcodesearch();
	}
	
	
	
	

}
