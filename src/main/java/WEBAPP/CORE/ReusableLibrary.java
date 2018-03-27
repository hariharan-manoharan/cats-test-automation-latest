package main.java.WEBAPP.CORE;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.commons.io.FileUtils;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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


	public void report(LogStatus status, String reportName) {

		if (properties.getProperty("take.screenshot.on.pass").equalsIgnoreCase("True")) {

			if (status.equals(LogStatus.PASS)) {
				test.log(LogStatus.PASS, reportName,
						"<b>Screenshot: <b>" + test.addScreenCapture("./" + screenShot() + ".png"));
			} else if (status.equals(LogStatus.FAIL)) {
				test.log(LogStatus.FAIL, reportName,
						"<b>Screenshot: <b>" + test.addScreenCapture("./" + screenShot() + ".png"));
			}
		} else {

			if (status.equals(LogStatus.PASS)) {
				test.log(LogStatus.PASS, reportName);
			} else if (status.equals(LogStatus.FAIL)) {
				test.log(LogStatus.FAIL, reportName);
			}
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
	
	
	public void waitUntilNotDisplayed(final By by) throws TimeoutException, NoSuchElementException {

		WebDriverWait wait = new WebDriverWait(webdriver,20 ); 
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));	

	}
	
	
	public void selectDataForm( String dataforFolder, String dataform, String dataFormLabel) {
		
		click(XPATH_CLIENT_FOLDER, "Click Client folder");
		click(By.xpath(String.format(XPATH_DATAFORM_FOLDER,dataforFolder)), "Click Data form folder - "+dataforFolder);
		click(By.xpath(String.format(XPATH_DATAFORM,dataform)), "Click Data form - " +dataform);	
		
		
		if(isDisplayed(By.xpath("//div[contains(text(),\'"+dataFormLabel+"\') and @class='dataform_title']"))) {
			test.log(LogStatus.PASS, dataform + " is selected successfully");
		}else {
			test.log(LogStatus.FAIL, dataform + " is not selected successfully");
		}
	}
	
	
	public void enterText(By by, String reportName, String text) throws TimeoutException, NoSuchElementException {

		waitCommand(by);
		WebElement element = webdriver.findElement(by);
		element.sendKeys(text);
		takeScreenshot(reportName);

	}

	public void click(By by, String reportName) throws TimeoutException, NoSuchElementException {

		waitCommand(by);
		webdriver.findElement(by).click();
		takeScreenshot(reportName);

	}
	
	public boolean isDisplayed(By by) {

		HardDelay(3000);
		try {
			if(webdriver.findElement(by).isDisplayed()) {
				report(LogStatus.PASS, by+" is displayed");
				return true;
			}
		}
		catch(Exception e){
			report(LogStatus.INFO,by+" is not displayed");
		}
		return false;


	}
	
/*	
	public String getText(By by) {		
		
		waitCommand(by);			
		return webdriver.findElement(by).getText();		
		
	}
	
	*/
	public void clickEditIcon(int rowNumber) {
		
		List<WebElement> rows = webdriver.findElements(XPATH_RESULTTAB_EDITICON);
		
		if(rows.size()>0) {
		rows.get(rowNumber - 1).click();
		
		waitCommand(XPATH_EDITTAB_PAGINATION);
				
		String paginationDetails = webdriver.findElement(XPATH_EDITTAB_PAGINATION).getText();
		
		String[] splitpaginationDetails = paginationDetails.split("of");
		
		if(rowNumber == Integer.parseInt(splitpaginationDetails[0].trim())) {
			report(LogStatus.PASS,"Edit icon of row "+ rowNumber+" is clicked successfully");
		}else {		 
			report(LogStatus.FAIL,"Edit icon of row "+ rowNumber+" is not clicked successfully");
		}
		}else {
			report(LogStatus.WARNING,"Zero records retreived. Cannot perform Click Edit icon");
		}
		
	}
	
	
	
	public void selectValueByVisibleText(By by, String text, String reportName) {
		
		try {
		waitCommand(by);		
		Select select = new Select(webdriver.findElement(by));		
		select.selectByVisibleText(text);
		takeScreenshot(reportName + " is successfull");
		}catch(Exception e) {
			test.log(LogStatus.FAIL, reportName + " is not successfull"); 
		}
		
	}
	
	public void sendTab(By by) {
		try {
			webdriver.findElement(by).sendKeys(Keys.TAB);
			test.log(LogStatus.PASS, "TAB is successfull");
		}catch(Exception e){
			
			test.log(LogStatus.FAIL,  "TAB is not successfull");
		}				
	}
	
	public void moveToElement(By by, String field) {		
		try {
		Actions builder = new Actions(webdriver);
		WebElement ele = webdriver.findElement(by);
        builder.moveToElement(ele).build().perform();
        test.log(LogStatus.PASS, "Moved to "+field);
        
		}catch(Exception e) {
		test.log(LogStatus.FAIL, "Moved to "+field);
		}
	} 
	
	
	public String getText(By by) {
		String text = null;
		try {		
			WebElement element = webdriver.findElement(by);
			text = element.getText();
			test.log(LogStatus.INFO, "Returned-Text:<b>" + text+"</b>");
			return text.trim();
		} catch (Exception ex) {
			test.log(LogStatus.FAIL, ex);
			test.log(LogStatus.INFO, "Not Returned-Text:<b>" + text+"</b>");
			return "NULL";
		}
	}
	

	public void clickSearchBtn() {
		
		if(webdriver.findElement(XPATH_SEARCH_BTN).isEnabled()) {
		click(XPATH_SEARCH_BTN, "Click Search button");
		}
		
	}
	
	
	public void clickClearBtn() {
		
		if(webdriver.findElement(XPATH_CLEAR_BTN).isEnabled()) {
		click(XPATH_CLEAR_BTN, "Click Clear button");
		}
		
	}
	
	public void clickClearPopupBtn() {
		
		if(webdriver.findElement(XPATH_BTN_POPUP_CLEAR).isEnabled()) {
		click(XPATH_BTN_POPUP_CLEAR, "Click Clear Popup button");
		}
		
	}
	
	public void clickSearchTab() {
		
		if(!getText(XPATH_LINK_ACTIVE_TAB).equals("Search")) {
		click(XPATH_LINK_SEARCH_TAB, "Click Search button");
		}
		
	}
	
	public void clickResultTab() {
		
		if(!getText(XPATH_LINK_ACTIVE_TAB).equals("Result")) {
		click(XPATH_LINK_RESULTS_TAB, "Click Search button");
		}
		
	}
	
	public void clickEditTab() {
		
		if(!getText(XPATH_LINK_ACTIVE_TAB).equals("Edit")) {
		click(XPATH_LINK_EDIT_TAB, "Click Search button");
		}
		
	}
	
	
	public void clearText(By by) {
		
		webdriver.findElement(by).clear();
		
	}
	
	public void selectRecordResultTab(int i) {
		
		List<WebElement> records = webdriver.findElements(XPATH_RESULTTAB_EDITICON);
		
		if(records.size()>0) {
			
			records.get(i-1).click();
			report(LogStatus.PASS, "selectRecordResultTab - Record - "+i+" is selected.");
			
		}else {
			test.log(LogStatus.WARNING, "selectRecordResultTab - No records found in Resukt tab.");
		}
		
	}

}
