package main.java.WEBAPP.CORE;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import main.java.WEBAPP.CORE.pageObjectRepositories.CommonObjectRepository;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.reporting.HtmlReport;
import main.java.framework.testDataAccess.DataTable;
import main.java.framework.utils.Utility;

public class ReusableLibrary extends Utility implements CommonObjectRepository{

	public WebDriver webdriver;


	@SuppressWarnings("rawtypes")
	public ReusableLibrary(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection) {
		super(test, webdriver, dataTable, testParameters, lock, connection);
		this.webdriver = webdriver;
	}

	@SuppressWarnings("rawtypes")
	public ReusableLibrary(ExtentTest test, WebDriver webdriver, DataTable dataTable, TestParameters testParameters,
			Lock lock, Connection connection, Properties runtimeDataProperties) {
		super(test, webdriver, dataTable, testParameters, lock, connection);
		this.webdriver = webdriver;
	}

	public ReusableLibrary() {

	}


	public void takeScreenshot(String reportName) {

		if (properties.getProperty("take.screenshot.on.pass").equalsIgnoreCase("True")) {
			test.log(LogStatus.PASS, reportName,
					"<b>Screenshot: <b>" + test.addScreenCapture("./" + screenShot() + ".png"));
		} else {
			test.log(LogStatus.PASS, reportName);
		}

	}

	public String screenShot() {

		String screenshotName = null;

		screenshotName = getCurrentFormattedTime("dd_MMM_yyyy_hh_mm_ss_SSS");

		File scrFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
		try {

			FileUtils.copyFile(scrFile,
					new File("./Results/" + HtmlReport.reportFolderName + "/" + screenshotName + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotName;

	}

	public void waitCommand(final By by) throws TimeoutException, NoSuchElementException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webdriver);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.withTimeout(20, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);

		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				boolean displayed = arg0.findElement(by).isEnabled();
				if (displayed) {
					return true;
				}
				return false;
			}
		};
		wait.until(function);

	}
	
	
	public void selectDataForm( String dataforFolder, String dataform, String dataFormLabel) {
		
		Click(XPATH_CLIENT_FOLDER, "Click Client folder");
		Click(By.xpath(String.format(XPATH_DATAFORM_FOLDER,dataforFolder)), "Click Data form folder - "+dataforFolder);
		Click(By.xpath(String.format(XPATH_DATAFORM,dataform)), "Click Data form - " +dataform);	
		
		
		if(isDisplayed(By.xpath("//div[contains(text(),\'"+dataFormLabel+"\') and @class='dataform_title']"))) {
			test.log(LogStatus.PASS, dataform + " is selected successfully");
		}else {
			test.log(LogStatus.FAIL, dataform + " is not selected successfully");
		}
	}
	
	
	public void EnterText(By by, String reportName, String text) throws TimeoutException, NoSuchElementException {

		waitCommand(by);
		WebElement element = webdriver.findElement(by);
		element.sendKeys(text);
		takeScreenshot(reportName);

	}

	public void Click(By by, String reportName) throws TimeoutException, NoSuchElementException {

		waitCommand(by);
		webdriver.findElement(by).click();
		takeScreenshot(reportName);

	}
	
	public boolean isDisplayed(By by) {
		
		waitCommand(by);
		
		if(webdriver.findElement(by).isDisplayed()) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public String getText(By by) {		
		
		waitCommand(by);			
		return webdriver.findElement(by).getText();		
		
	}

}
