package main.java.businessComponents.MOBILE.AIRTEL;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;
import main.java.executionSetup.TestParameters;
import main.java.reporting.HtmlReport;
import main.java.reporting.HtmlReport;
import main.java.testDataAccess.DataTable;
import main.java.utils.Utility;

public class ReusableLibrary extends Utility implements RoutineObjectRepository {


	public Lock lock;
	public Connection connection;


	@SuppressWarnings("rawtypes")
	public ReusableLibrary(ExtentTest test, AndroidDriver driver, DataTable dataTable, TestParameters testParameters, Lock lock, Connection connection) {
		super(test, driver, dataTable, testParameters, lock, connection);
		this.lock = lock;
		this.connection = connection;
	}
	
	public ReusableLibrary(ExtentTest test, AndroidDriver driver, DataTable dataTable, TestParameters testParameters, Lock lock, Connection connection, Properties runtimeDataProperties) {
		super(test, driver, dataTable, testParameters, lock, connection, runtimeDataProperties);
		this.lock = lock;
		this.connection = connection;
	}


	public void createNewConnection() throws TimeoutException, NoSuchElementException  {
		
		Click(NAME_ADD_CONNECTION, "Click - AddConnection");
		Click(ID_ADD_CONNECTIONS, "Click - AddConnection Symbol");
		EnterText(NAME_TXT_CONNECTION_NAME, "Enter - Connection Name", environmentVariables.get("EnvironmentName"));
		EnterText(ID_TXT_CONNECTION_HOST, "Enter - Host", environmentVariables.get("MobilityHost"));
		EnterText(ID_TXT_CONNECTION_PORT, "Enter - Port", environmentVariables.get("MobilityPort"));
		if(environmentVariables.get("MobilitySSL").equalsIgnoreCase("Yes")){
		Click(ID_TOGGLE_BTN_SSL, "Click - Enable SSL");
		}
		Click(ID_ICON_SAVE, "Click - Save Connection");
		Click(ID_ACTION_BAR_BTN, "Click - Back button");

	}

	public void login() throws TimeoutException, NoSuchElementException {

		// EnterText(ID_TXT_USERNAME, "Enter - Username", "catsadm");
		// EnterText(ID_TXT_PASSWORD, "Enter - Password", "catscats11");
		// HideKeyboard();
		Click(ID_BTN_CONNECT, "Click - Connect button");
	}

	@SuppressWarnings("unchecked")
	public void selectUserProfile(String profile) throws TimeoutException, NoSuchElementException {

		List<WebElement> elements = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\")");
		waitCommand(ID_ACTION_BAR_BTN);
		TouchAction action = new TouchAction((MobileDriver)driver);
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(profile)) {
				//int x = element.getLocation().getX();
				//int y = element.getLocation().getY();				
				//action.press(element).release().perform();	
				element.click();
				takeScreenshot("Profile - <b>"+profile+"</b> is clicked");
				break;				
			}
		}		

	}

	@SuppressWarnings("unchecked")
	public void clickRoutineFolder(String folderName) throws TimeoutException, NoSuchElementException  {
		
		List<WebElement> elements = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\")");		
		waitUntilTextDisplayed(ID_ACTION_BAR_BTN, "Routines");
		TouchAction action = new TouchAction((MobileDriver)driver);
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(folderName)) {
				//int x = element.getLocation().getX();
				//int y = element.getLocation().getY();				
				//action.press(element).release().perform();
				element.click();
				takeScreenshot("Routine Folder - <b>"+folderName+"</b> is clicked");
				break;				
			}
		}		
	}

	@SuppressWarnings("unchecked")
	public void clickRoutine(String folderName, String routineName) throws TimeoutException, NoSuchElementException  {
		List<WebElement> elements = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\")");
		waitUntilTextDisplayed(ID_ACTION_BAR_SUBTITLE,folderName);
		TouchAction action = new TouchAction((MobileDriver)driver);
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(routineName)) {
				//int x = element.getLocation().getX();
				//int y = element.getLocation().getY();				
				//action.press(element).release().perform();
				element.click();
				takeScreenshot("Routine - <b>"+routineName+"</b> is clicked");
				break;				
			}
		}	
	}



	/**
	 * Function to enter text in WebElement
	 * 
	 * @param1 String fieldName
	 * @param2 String value	
	 * @return void
	 * @author Hari
	 * @since 06/27/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void enterText(String field, String data) throws TimeoutException, NoSuchElementException {

		By by = By.xpath(String.format(XPATH_TXT, field));
		
		MobileElement  element = (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");		
		

		waitCommand(by);
		
		driver.pressKeyCode(112); // DELETE Key event - https://developer.android.com/reference/android/view/KeyEvent.html#KEYCODE_FORWARD_DEL
		if(data.contains("&")) { // setValue function not working if data contains '&', so included this check. Need to research if there is any alternative approach to handle this issue.
		element.sendKeys(data);	
		}else{
		element.setValue(data);
		}
		takeScreenshot(field, data);

	}

	/**
	 * Function to click "Next" button in CATS Mobility - Application Specific
	 * 
	 * @param no parameters
	 * @return void
	 * @author Hari
	 * @since 06/27/2017
	 * 
	 */

	public void clickNext() throws TimeoutException, NoSuchElementException{
		 	
		waitCommand(By.xpath(String.format(XPATH_TXT_CONTAINS, ":")));		    
		driver.findElement(By.id("next")).click();				
	}
	
	public void clickNextWaitTillFieldContains(String fieldLabel) throws TimeoutException, NoSuchElementException{
	 	
		waitCommand(By.xpath(String.format(XPATH_TXT_CONTAINS, fieldLabel)));		    
		driver.findElement(By.id("next")).click();				
	}


	public void clickNextMultiple(String times) throws TimeoutException, NoSuchElementException{

		for(int i=1;i<=Integer.parseInt(times);i++){ 	
			waitCommand(By.xpath(String.format(XPATH_TXT_CONTAINS, ":")));		    
			driver.findElement(By.id("next")).click();	
			HardDelay(3000L);
		}
	}

	public void multipleClickNext(String field,String times) throws TimeoutException, NoSuchElementException{

		for(int i=1;i<=Integer.parseInt(times);i++){ 	
			waitCommand(By.xpath(String.format(XPATH_TXT, field)));		    
			driver.findElement(By.id("next")).click();	
			HardDelay(3000L);
		}
	}


	/**
	 * Function to click "Previous" button in CATS Mobility - Application Specific
	 * 
	 * @param no parameters
	 * @return void
	 * @author Hari 
	 * @since 06/27/2017
	 * 
	 */

	public void clickPrevious() throws TimeoutException, NoSuchElementException{

		waitCommand(By.id("previous"));
		driver.findElement(By.id("previous")).click();
		takeScreenshot("Click Previous Button");

	}
	
	public void clickDeviceBackButton(){
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}


	public void clickRoutineBackButton(){	
		
		try {
		
		WebElement element = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").clickable(true)");
		element.click();
		takeScreenshot("Click Routine back Button");
		
		}catch(Exception e) {
			WebElement element = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").clickable(true)");
			element.click();
			takeScreenshot("Click Routine back Button");
		}

	}


	/**
	 * Function to click spyglass  - Need to Fix this one - Avoid getting the fieldIndex from user
	 * 
	 * @param1 String reportName	 
	 * @return void
	 * @author Hari 
	 * @since 06/27/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void clickSpyGlass(String pickListName) throws TimeoutException, NoSuchElementException {
		
		waitCommand(By.xpath(String.format(XPATH_TXT, pickListName)));		
		
		List<WebElement> element = driver.findElementsByAndroidUIAutomator(
				"new UiSelector().className(\"android.view.View\").index(0).clickable(true)");
		
		TouchAction action = new TouchAction((MobileDriver)driver);
		
		
		int size = element.size();
		if (size > 1) {
						
			int x = element.get(size - 1).getLocation().getX();
			int y = element.get(size - 1).getLocation().getY();
			
			driver.tap(1, x, y, 2);		
			
			//action.press(element.get(size - 1)).release().perform();
			//element.get(size - 1).click();
			
			takeScreenshot("Clicked - " + pickListName + " spyglass");
		} else {
		    			
			int x = element.get(0).getLocation().getX();
			int y = element.get(0).getLocation().getY();
			
			driver.tap(1, x, y, 2);
			
			//action.press(element.get(0)).release().perform();
			//element.get(0).click();
			
			takeScreenshot("Clicked - " + pickListName + " spyglass");
		}			

	}
	
	
	/**
	 * Function to click spyglass  - Need to Fix this one - Avoid getting the fieldIndex from user
	 * 
	 * @param1 String reportName	 
	 * @return void
	 * @author Hari 
	 * @since 06/27/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void clickDatePicker(String pickListName) throws TimeoutException, NoSuchElementException {
		
		waitCommand(By.xpath(String.format(XPATH_TXT, pickListName)));		
		
		List<WebElement> element = driver.findElementsByAndroidUIAutomator(
				"new UiSelector().className(\"android.view.View\").index(0).clickable(true)");
		
		TouchAction action = new TouchAction((MobileDriver)driver);
		
		
		int size = element.size();
		if (size > 1) {
						
			int x = element.get(size - 1).getLocation().getX();
			int y = element.get(size - 1).getLocation().getY();
			
			driver.tap(1, x, y, 2);		
			
			//action.press(element.get(size - 1)).release().perform();
			//element.get(size - 1).click();
			
			takeScreenshot("Clicked - " + pickListName + " date picker icon");
		} else {
		    			
			int x = element.get(0).getLocation().getX();
			int y = element.get(0).getLocation().getY();
			
			driver.tap(1, x, y, 2);
			
			//action.press(element.get(0)).release().perform();
			//element.get(0).click();
			
			takeScreenshot("Clicked - " + pickListName + " date picker icon");
		}			

	}


	/**
	 * Function to get Pick List value
	 * 	 
	 * @return void
	 * @author Hari 
	 * @since 06/27/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void selectPickListValue(String pickListValue) throws TimeoutException, NoSuchElementException{
		
		TouchAction action = new TouchAction((MobileDriver)driver);

		waitCommand(ID_PICKLIST_SEARCHFIELD);

		if(pickListValue.contains("#")){
			pickListValue = getRuntimeTestdata(pickListValue);
		}			

		List<WebElement> elements = driver.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout/android.widget.TextView[@index='0']");
		int size = elements.size();
		for(WebElement element: elements){			
			size--;
			if(element.getText().equalsIgnoreCase(pickListValue)){
				//int x = element.getLocation().getX();
				//int y = element.getLocation().getY();				
				//action.press(element).release().perform();	
				element.click();
				takeScreenshot("Pick List Value - <b>"+pickListValue+"</b> is selected");
				break;
			}else if(size==0){
				takeScreenshot("Pick List Value - <b>"+pickListValue+"</b> is not selected");
			}
		}




	}
	
	
	public void validatePicklistValue(String columnName, String value) throws InterruptedException {
		
		int requiredValueIndex = 0;
		int picklistNumber = 0;
		String[] valueSplit = new String[2];
		String header = null;
		
		waitCommand(ID_PICKLIST_SEARCHFIELD);
		
		
		if(value!=null && value.contains("@")) {
			
			valueSplit = value.split("@");			
			picklistNumber = Integer.parseInt(valueSplit[0])-1;
			
			if(valueSplit[1].contains("#")){
				valueSplit[1] = getRuntimeTestdata(valueSplit[1]);
			}else if(valueSplit[1].equalsIgnoreCase("EMPTY")) {
				valueSplit[1] = "";
			}
			
		}
		
	
		@SuppressWarnings("unchecked")
		List<WebElement> elements1 = driver.findElementsByXPath(".//android.widget.LinearLayout[@resource-id='net.fulcrum.mobility:id/lookup_headers']"
				+ "/android.widget.TextView");
		
		
		for(int i=0;i<elements1.size();i++) {			
			
			header = elements1.get(i).getText();	
			
			if(header.equals(columnName)) {
				requiredValueIndex = i;	
				
				 
				
				@SuppressWarnings("unchecked")
				List<WebElement> elements2 = driver.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']"
						+ "/android.widget.LinearLayout[@index='"+picklistNumber+"']/android.widget.TextView");
				
				String actualValue = elements2.get(requiredValueIndex).getText();

				if (actualValue.equals(valueSplit[1])) {
					test.log(LogStatus.PASS, "<b> Line Number - " + valueSplit[0] + ":" + columnName + "</b></br>Expected - <b>" + valueSplit[1] + "</b></br>"
																+ "Actual - <b>" + actualValue +"</b>", "");
				}else {
					test.log(LogStatus.FAIL, "<b> Line Number - " + valueSplit[0] + ":" + columnName + "</b></br>Expected - <b>" + valueSplit[1] + "</b></br>"
							   										+ "Actual - <font color=red><b>" + actualValue +"</font></b>", "");
				}
				
				break;
			}else if ((elements1.size()-1)==i){
				swipingHorizontal("Right To Left");
				validatePicklistValue(columnName,value);
				break;
			}
		}
		
		
		
	}
	
	

	public void swipingHorizontal(String direction) throws InterruptedException {
		  //Get the size of screen.
		  Dimension size = driver.manage().window().getSize();
		  System.out.println(size);
		  
		  //Find swipe start and end point from screen's with and height.
		  //Find startx point which is at right side of screen.
		  int startx = (int) (size.width * 0.70);
		  //Find endx point which is at left side of screen.
		  int endx = (int) (size.width * 0.1);
		  //Find vertical point where you wants to swipe. It is in middle of screen height.
		  int starty = size.height / 2;
		  //System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);

		  
		  if(direction.equalsIgnoreCase("Right To Left")) {
		  //Swipe from Right to Left.
		  driver.swipe(startx, starty, endx, starty, 3000);
		  Thread.sleep(2000);
		  takeScreenshot("Swiping Horizontally...Right to Left");
		  }else if (direction.equalsIgnoreCase("Left to Right")){
		  //Swipe from Left to Right.
		  driver.swipe(endx, starty, startx, starty, 3000);
		  Thread.sleep(2000); 
		  takeScreenshot("Swiping Horizontally...Left to Right");
		  }	
	}
		  


	/**
	 * Function to enter text in WebElement
	 * 
	 * @param1 String fieldName
	 * @param2 String value	
	 * @return void
	 * @author Hari
	 * @since 06/27/2017
	 * 
	 */

	public void enterTextFormattedData(String field, String data, String columnName) throws TimeoutException, NoSuchElementException {

		data = (data.contains("#")) ?  getRuntimeTestdata(data) : generateTestData(columnName, data);

		By by = By.xpath(String.format(XPATH_TXT, field));	

		waitCommand(by);
		
		MobileElement  element = (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");	
		
		driver.pressKeyCode(112); // DELETE Key event - https://developer.android.com/reference/android/view/KeyEvent.html#KEYCODE_FORWARD_DEL
		element.setValue(data);
		takeScreenshot(field, data);

	}

	public static long generateRandom(int length) {
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}



	// Verification Components

	public void validateLoopField(String loopField) {		
		if (isElementPresent(By.xpath(String.format(XPATH_TXT, loopField)),"Loop field - "+loopField)) {
			report(driver,test, " Transaction is successfull", LogStatus.PASS);			
		} else {
			report(driver,test, " Transaction is not successfull", LogStatus.FAIL);			
		}
	}



	/**
	 * Function to log test report with screenshot and Status PASS
	 * 
	 * @param1 String reportName
	 * @return void
	 * @author Hari
	 * @since 12/27/2016
	 * 
	 */

	public void takeScreenshot(String reportName, String data) {

		if(properties.getProperty("take.screenshot.on.pass").equalsIgnoreCase("True")){

			String screenshotName = getCurrentFormattedTime("dd_MMM_yyyy_hh_mm_ss_SSS");

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {			
				FileUtils.copyFile(scrFile,
						new File("./Results/" + HtmlReport.reportFolderName + "/" + screenshotName + ".png"));				
				
			} catch (IOException e) {
				e.printStackTrace();
			}



			test.log(LogStatus.PASS, reportName + "<b>"+data+"</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./" + screenshotName + ".png"));
		}else{
			test.log(LogStatus.PASS, reportName + "<b>"+data+"</b>");	
		}

	}

	public void clickConfirmPrompt(String msg , String data) throws TimeoutException, NoSuchElementException{		
		if (GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")).equalsIgnoreCase(msg)) {
			report(driver,test, msg + " is displayed", LogStatus.PASS);	

			if(data.equalsIgnoreCase("Yes")){
				Click(ID_MESSAGE_CONFIRM_YES, "Clicked 'Yes' for prompt - " + msg);
			}
			else{
				Click(ID_MESSAGE_CONFIRM_NO, "Clicked 'No' for prompt - " + msg);	
			}
		} else {
			report(driver,test, msg + " is not displayed", LogStatus.FAIL);			
		}
	}


	public void clickYesConfirmPromptContains(String msgContains) throws TimeoutException, NoSuchElementException{		
		if (GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")).contains(msgContains)) {
			report(driver,test, msgContains + " is displayed", LogStatus.PASS);		
			Click(ID_MESSAGE_CONFIRM_YES, "Clicked 'Yes' for prompt - " + msgContains);
		} else {
			report(driver,test, msgContains + " is not displayed", LogStatus.FAIL);			
		}
	}

	public void clickNoConfirmPromptContains(String msgContains) throws TimeoutException, NoSuchElementException{		
		if (GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")).contains(msgContains)) {
			report(driver,test, msgContains + " is displayed", LogStatus.PASS);		
			Click(ID_MESSAGE_CONFIRM_NO, "Clicked 'No' for prompt - " + msgContains);
		} else {
			report(driver,test, msgContains + " is not displayed", LogStatus.FAIL);			
		}
	}


	public void clickOkPrompt(String msg) throws TimeoutException, NoSuchElementException{

		String data = null;
		if (msg.contains("@")){
			String[] key =msg.split("@");
			String errormessage1 =  key [0];
			String errormessage2 =  key[1];
			String errormessage3 =  key[2];

			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
			data = distributedRuntimeDataProperties.getProperty(errormessage2);
			}else {
			data = parallelRuntimeDataProperties.getProperty(errormessage2);	
			}

			String errormessage = errormessage1 +data+errormessage3;
			msg=errormessage;

		}

		if (GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")).equalsIgnoreCase(msg)) {
			report(driver,test, msg + " is displayed", LogStatus.PASS);		
			Click(ID_MESSAGE_OK, "Clicked 'Ok' for prompt - " + msg);
		} else {
			report(driver,test, msg + " is not displayed", LogStatus.FAIL);			
		}
	}
	
	
	public void verifyPrompt(String prompt, String action) throws TimeoutException, NoSuchElementException{
		
		if(isElementPresent(ID_MESSAGE, "Alert")) {
		
		if (GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")).equalsIgnoreCase(prompt)) {			
			
			switch(action) {
			case "ClickYes":
				Click(ID_MESSAGE_CONFIRM_YES, "Clicked 'Yes' for prompt - " + prompt);
				break;
			case "ClickNo":
				Click(ID_MESSAGE_CONFIRM_NO, "Clicked 'No' for prompt - " + prompt);
				break;
			case "ClickOk":
				Click(ID_MESSAGE_OK, "Clicked 'Ok' for prompt - " + prompt);
				break;
			default:
				test.log(LogStatus.FAIL, "Action <b><"+action+"></b> cannot be performed for the prompt <b><"+prompt+"></b>. Provide valid action like <ClickYes>/<ClickNo>/<ClickOk>");
				break;
			}
			
		}
		}
		
	}



	public void clearField(){
		driver.pressKeyCode(112);
	}


public void addRuntimeTestData(String columnName, String columnValue) {
		
		try {
			lock.lock();
		try {

			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {			
				distributedRuntimeDataProperties.put(testParameters.getCurrentTestCase() + "#" + columnName, columnValue);
				test.log(LogStatus.PASS, testParameters.getCurrentTestCase() + "#" + columnName + " : "+columnValue , "");
				
			}else {
				parallelRuntimeDataProperties.put(testParameters.getCurrentTestCase() + "#" + columnName, columnValue);
				test.log(LogStatus.PASS, testParameters.getCurrentTestCase() + "#" + columnName + " : "+columnValue , "");
			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL, e);
			test.log(LogStatus.FAIL, testParameters.getCurrentTestCase() + "#" + columnName + " : "+columnValue , "");
		}
		}finally {
			lock.unlock();
		}

	}
	
	
	public String getRuntimeTestdata(String tescase_ColumnName) {

		String data = null;

		try {

			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {		
			data = distributedRuntimeDataProperties.getProperty(tescase_ColumnName);
			}else {
			data = 	parallelRuntimeDataProperties.getProperty(tescase_ColumnName);
			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL, e);
		}

		return data;

	}
	
	
	public String generateTestData(String columnName, String columnValue) {
		
		String data = null;

		try {
			
			lock.lock();			

			try {

				data = columnValue + getCurrentFormattedTime("ddMMhhmmssSSS");

				if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
					distributedRuntimeDataProperties.put(testParameters.getCurrentTestCase() + "#" + columnName, data);
				}else {
					parallelRuntimeDataProperties.put(testParameters.getCurrentTestCase() + "#" + columnName, data);
				}

			} catch (Exception e) {
				test.log(LogStatus.FAIL, e);
			}

		} finally {
			lock.unlock();
		}

		return data;

	}

	public void getPutTestdata(String field , String name) throws TimeoutException, NoSuchElementException{


		waitCommand(By.xpath(String.format(XPATH_TXT, field)+"/following-sibling::android.view.View"));

		String Fieldvalue = driver.findElement(By.xpath(String.format(XPATH_TXT, field)+"/following-sibling::android.view.View")).getAttribute("name");	

		addRuntimeTestData(name, Fieldvalue);

	}


	public void verifyAutopopulatefieldvalues(String field, String data)  throws TimeoutException, NoSuchElementException {

		waitCommand(By.xpath(String.format(XPATH_TXT, field)+"/following-sibling::android.view.View"));
		waitForSeconds("2");
		
		WebElement element =  driver.findElement(By.xpath(String.format(XPATH_TXT, field)+"/following-sibling::android.view.View"));
		String fieldValue = element.getAttribute("name");		
		
		if(data!=null){
			if(data.contains("#")){
				if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
				data = distributedRuntimeDataProperties.getProperty(data);
				}else {
				data = parallelRuntimeDataProperties.getProperty(data);	
				}

			}
		}

		if (data!=null){
			if (data.equalsIgnoreCase(fieldValue)) {
				test.log(LogStatus.PASS, "<b>"+ field + "</b></br>Expected - <b>" + data + "</b></br>"
													   + "Actual - <b>" + fieldValue +"</b>", "");
			}else {
				test.log(LogStatus.FAIL, "<b>"+ field + "</b></br>Expected - <b>" + data + "</b></br>"
						   + "Actual - <font color=red><b>" + fieldValue +"</font></b>", "");				
				takeScreenshot(field + " is not populated as expected");				
			}
		}else if(field!=null && fieldValue.equals("")) {
			test.log(LogStatus.INFO, "<b>"+ field + "</b></br> - Value is not expected in this field</b></br>"	, "");
		}
	}
	
	
	
	// Won't Work - Yet to Fix
	public void touchAndScrollToElement(WebElement element) {
		try {

	    int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		
		@SuppressWarnings("unchecked")
		List<WebElement> elements = driver.findElements(By.xpath(".//android.view.View"));
		
		WebElement startElement = elements.get(elements.size()-1);
		
		startX = startElement.getLocation().getX();
		startY = startElement.getLocation().getY();
		
		endX = element.getLocation().getX();
		endY = element.getLocation().getX();
		
		
		driver.swipe(startX, startY, endX, endY, 3000);
		
		}catch(Exception e) {
			test.log(LogStatus.INFO, e);
		}
	}

	/**
	 * Function implements thread.sleep function 8
	 * 
	 * @param1 long delayTime	
	 * @return void
	 * @author Hari
	 * @since 12/27/2016
	 * 
	 */

	public static void waitForSeconds(String delaySeconds) {

		delaySeconds = delaySeconds +"000";

		try {
			Thread.sleep(Long.parseLong(delaySeconds));
		} catch (Exception e) {

		}

	}




	public void verifyRoutine(String routinename) throws TimeoutException, NoSuchElementException{
		
		waitUntilTextDisplayed(ID_ACTION_BAR_SUBTITLE, routinename);

		if (GetText(ID_ACTION_BAR_SUBTITLE, "Routine name").equals(routinename)) {

			test.log(LogStatus.PASS, routinename + " - Routine is displayed");
		}
		else{
			test.log(LogStatus.FAIL, routinename + " - Routine is not displayed");

		}

	}
	
	
	@SuppressWarnings("unchecked")
	public void clickButtonWithText(String button) {
		
		List<WebElement> elements = driver.findElementsByXPath(".//android.widget.Button");
		boolean isClicked = false;

		String[] buttonTexts = null;
		String buttonTextClicked = null;
		
		
		if(button.contains("/")) {
			buttonTexts  = button.split("/");
		}
		
		for(String buttonText : buttonTexts) {
		
		for(WebElement element:elements) {
			
			if(isClicked) {
				break;
			}
			
			if(element.getText().equalsIgnoreCase(buttonText)) {
				isClicked = true;
				buttonTextClicked = buttonText; 
				element.click();
				takeScreenshot(buttonText+" button is Clicked");
				break;
			}
		}
		}
		
		
		if(!isClicked) {
			takeScreenshot(buttonTextClicked+" button is not Clicked");
		}
	}



}
