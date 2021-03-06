package main.java.MOBILE.AIRTEL;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

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
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.reporting.HtmlReport;
import main.java.framework.testDataAccess.DataTable;
import main.java.framework.utils.Utility;

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
		
		Click(ID_ADD_CONNECTION, "Click - AddConnection");
		Click(ID_ADD_CONNECTIONS, "Click - AddConnection Symbol");
		EnterText(ID_TXT_CONNECTION_NAME, "Enter - Connection Name", environmentVariables.get("EnvironmentName"));
		EnterText(ID_TXT_CONNECTION_HOST, "Enter - Host", environmentVariables.get("MobilityHost"));
		EnterText(ID_TXT_CONNECTION_PORT, "Enter - Port", environmentVariables.get("MobilityPort"));
		if(environmentVariables.get("MobilitySSL").equalsIgnoreCase("Yes")){
		Click(ID_TOGGLE_BTN_SSL, "Click - Enable SSL");
		}
		Click(ID_ICON_SAVE, "Click - Save Connection");
		Click(ID_ACTION_BAR_BTN, "Click - Back button");

	}

	public void login() throws TimeoutException, NoSuchElementException {

		 EnterText(ID_TXT_USERNAME, "Enter - Username", "catsadm");
		 EnterText(ID_TXT_PASSWORD, "Enter - Password", "catscats11");
		 Click(ID_REMEMBER_ME, "Click - Remember me checkbox");
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
				element.click();
				takeScreenshot("Profile - <b>"+profile+"</b> is clicked");
				break;				
			}
		}		

	}

	@SuppressWarnings("unchecked")
	public void clickRoutineFolder(String folderName) throws TimeoutException, NoSuchElementException  {
		boolean isClicked = false;
		List<WebElement> elements = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\")");		
		waitUntilTextDisplayed(ID_ACTION_BAR_BTN, "Routines");
		TouchAction action = new TouchAction((MobileDriver)driver);
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(folderName)) {;
				element.click();
				takeScreenshot("Routine Folder - <b>"+folderName+"</b> is clicked");
				isClicked = true;
				break;				
			}
		}		
		
		if(!isClicked) {
			test.log(LogStatus.FAIL, "Routine Folder - <b>"+folderName+"</b> is not clicked");			
		}
	}

	@SuppressWarnings("unchecked")
	public void clickRoutine(String folderName, String routineName) throws TimeoutException, NoSuchElementException  {
		boolean isClicked = false;
		List<WebElement> elements = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\")");
		waitUntilTextDisplayed(ID_ACTION_BAR_SUBTITLE,folderName);
		TouchAction action = new TouchAction((MobileDriver)driver);
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(routineName)) {
				element.click();
				takeScreenshot("Routine - <b>"+routineName+"</b> is clicked");
				isClicked = true;
				break;				
			}
		}	
		
		if(!isClicked) {
			test.log(LogStatus.FAIL, "Routine - <b>"+routineName+"</b> is not clicked");			
		}
	}



	/**
	 * Function to enter text in WebElement
	 * 
	 * @param1 String fieldName
	 * @param2 String value	
	 * @return void
	 * @author Hari
	 * @throws InterruptedException 
	 * @since 06/27/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void enterText(String field, String data) throws TimeoutException, NoSuchElementException {

		By by = By.xpath(String.format(XPATH_TXT, field));
		waitCommand(by);
		
		MobileElement  element = (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");			
		
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
		test.log(LogStatus.INFO, "Clicked Next Button");
	}
	
	public void clickNextWaitTillFieldContains(String fieldLabel) throws TimeoutException, NoSuchElementException{
	 	
		waitCommand(By.xpath(String.format(XPATH_TXT_CONTAINS, fieldLabel)));		    
		driver.findElement(By.id("next")).click();	
		test.log(LogStatus.INFO, "Clicked Next Button");
	}


	public void clickNextMultiple(String times) throws TimeoutException, NoSuchElementException{

		for(int i=1;i<=Integer.parseInt(times);i++){ 	
			waitCommand(By.xpath(String.format(XPATH_TXT_CONTAINS, ":")));		    
			driver.findElement(By.id("next")).click();	
			test.log(LogStatus.INFO, "Clicked Next Button");
			HardDelay(3000L);
		}
	}

	public void multipleClickNext(String field,String times) throws TimeoutException, NoSuchElementException{

		for(int i=1;i<=Integer.parseInt(times);i++){ 	
			waitCommand(By.xpath(String.format(XPATH_TXT, field)));		    
			driver.findElement(By.id("next")).click();	
			test.log(LogStatus.INFO, "Clicked Next Button");
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
	 * Function to click spyglass
	 * 
	 * @param1 String reportName	 
	 * @return void
	 * @author Hari 
	 * @since 06/27/2017
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void clickSpyGlass(String pickListName) throws TimeoutException, NoSuchElementException {
		
		boolean isWebviewExist = false;
		
		waitCommand(By.xpath(String.format(XPATH_TXT, pickListName)));	
		driver.pressKeyCode(112); 

		
		//List<WebElement> element = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"\")");
		List<WebElement> element = driver.findElements(By.xpath(String.format(XPATH_TXT, "")));	
		isWebviewExist = isWebviewExist();
		
		int size = element.size();
		if (size >= 1) {			

			element.get(0).click();
				
			if (!isElementPresent(ID_PICKLIST_SEARCHFIELD, "Pick list search field")) {
				if (isElementPresent(ID_MESSAGE_OK, "Prompt")) {
					Click(ID_MESSAGE_OK,
							"Clicked 'Ok' for prompt - " + GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")));

					if (isWebviewExist) {
						test.log(LogStatus.INFO, "Re-Clicking in Web view - " + pickListName);
						clickWebView(
								By.xpath("//*[contains(text(),'" + pickListName + "')]/following-sibling::div/div"),
								pickListName);
					} else {
						test.log(LogStatus.INFO, "Re-Clicking in Native view - " + pickListName);
						int x = element.get(0).getLocation().getX();
						int y = element.get(0).getLocation().getY();				
						driver.tap(1, x, y, 3);
					}
				} else {
					if (isWebviewExist) {
						test.log(LogStatus.INFO, "Re-Clicking in Web view - " + pickListName);
						clickWebView(
								By.xpath("//*[contains(text(),'" + pickListName + "')]/following-sibling::div/div"),
								pickListName);
					} else {
						test.log(LogStatus.INFO, "Re-Clicking in Native view - " + pickListName);
						int x = element.get(0).getLocation().getX();
						int y = element.get(0).getLocation().getY();				
						driver.tap(1, x, y, 3);
					}
				}
			}
			
			
			takeScreenshot("Clicked - " + pickListName + " spyglass");
		} 		

	}
	
	
	public boolean isWebviewExist() {
		
		boolean isWebviewExist = false;
		Set<String> contextHandles = driver.getContextHandles();
		
		for (String s : contextHandles) {				
			if (s.contains("fulcrum")) {
				isWebviewExist = true;
				break;
			}
		}
		
		return isWebviewExist;
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
		
		
		WebElement element = driver.findElement(By.xpath(String.format(XPATH_TXT, "Tap to Choose Date")));	
		element.click();

			if(!isElementPresent(By.id("numberpicker_input"), "Number Picker")) {			
				test.log(LogStatus.INFO, "Re-Clicking in Web view - "+pickListName);			
				 clickWebView(By.xpath("//*[contains(text(),'"+pickListName+"')]/following-sibling::div"), pickListName);
			}
		
			takeScreenshot("Clicked - " + pickListName + " date picker icon");
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
	public void clickDatePickerSetCustomDate(String pickListName, String DDMONYY) throws TimeoutException, NoSuchElementException {
		
		
		String[] splittedDate = null;
		String[] changeFormat = new String[3];

		if(DDMONYY.contains("#")) {
		DDMONYY = getRuntimeTestdata(DDMONYY);
		}
		
		if(!DDMONYY.equals(null)) {
		splittedDate = DDMONYY.split("-");		
		changeFormat[1] =  splittedDate[0]; //date
		changeFormat[0] = splittedDate[1]; //month
		changeFormat[2] =  splittedDate[2]; //year
		}
		
		
		waitCommand(By.xpath(String.format(XPATH_TXT, pickListName)));		
		
		
		WebElement element = driver.findElement(By.xpath(String.format(XPATH_TXT, "Tap to Choose Date")));	
		element.click();

			if(!isElementPresent(By.id("numberpicker_input"), "Number Picker")) {			
				test.log(LogStatus.INFO, "Re-Clicking in Web view - "+pickListName);			
				 clickWebView(By.xpath("//*[contains(text(),'"+pickListName+"')]/following-sibling::div"), pickListName);
			}
		
			takeScreenshot("Clicked - " + pickListName + " date picker icon");
			
			
			List<WebElement> numberPickerInputs = driver.findElements(By.id("numberpicker_input"));
			
			if(numberPickerInputs.size()!=0) {
				for(int i=0;i<numberPickerInputs.size();i++) {
					if(numberPickerInputs.get(i).getText().equalsIgnoreCase(changeFormat[i])) {
						continue;
					}else {
						int x = numberPickerInputs.get(i).getLocation().getX();
						int y = numberPickerInputs.get(i).getLocation().getY();				
						driver.tap(1, x, y, 3);
						driver.pressKeyCode(19);
						if(numberPickerInputs.get(i).getText().equalsIgnoreCase(changeFormat[i])) {
							continue;
						}
					}
				}
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
	public void selectPickListValue(String columnIndex, String pickListValue) throws TimeoutException, NoSuchElementException{
		
		List<WebElement> elements = null;
		
		TouchAction action = new TouchAction((MobileDriver)driver);

		waitCommand(ID_PICKLIST_SEARCHFIELD);

		if(pickListValue.contains("#")){
			pickListValue = getRuntimeTestdata(pickListValue);
		}			
		
		if((columnIndex!=null) && !(columnIndex.equals(""))) {
		elements = driver.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout/android.widget.TextView[@index='"+(Integer.parseInt(columnIndex)-1)+"']");
		}else {
		elements = driver.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout/android.widget.TextView[@index='0']");	
		}	
		int size = elements.size();
		for(WebElement element: elements){			
			size--;
			if(element.getText().equalsIgnoreCase(pickListValue)){	
				element.click();
				takeScreenshot("Pick List Value - <b>"+pickListValue+"</b> is selected");
				break;
			}else if(size==0){
				takeScreenshot("Pick List Value - <b>"+pickListValue+"</b> is not selected");
			}
		}




	}
	
	/**
	 * Function to get Pick List value
	 * 	 
	 * @return void
	 * @author Saran 
	 * @since 09/19/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void verifyPickListValue(String pickListValue) throws TimeoutException, NoSuchElementException{
		
		waitCommand(ID_PICKLIST_SEARCHFIELD);

		if(pickListValue.contains("#")){
			pickListValue = getRuntimeTestdata(pickListValue);
		}			

		List<WebElement> elements = driver.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout/android.widget.TextView[@index='0']");
		int size = elements.size();
		for(WebElement element: elements){			
			size--;
			if(element.getText().equalsIgnoreCase(pickListValue)){
				
				test.log(LogStatus.PASS, "Pick list value <b>"+pickListValue+"</b> is displayed");	
				takeScreenshot("Pick list value <b>"+pickListValue+"</b> is displayed");
				break;
			}else if(size==0){
				test.log(LogStatus.FAIL, "Pick list value <b>"+pickListValue+"</b> is not displayed");	
				takeScreenshot("Pick list value <b>"+pickListValue+"</b> is not displayed");
			}
		}




	}
	/**
	 * Function to get Pick List value by index
	 * 	 
	 * @return void
	 * @author Saran
	 * @since 06/09/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void selectPickListValueByIndex(String Index , String pickListValue ) throws TimeoutException, NoSuchElementException{
		waitCommand(ID_PICKLIST_SEARCHFIELD);

		if(pickListValue.contains("#")){
			pickListValue = getRuntimeTestdata(pickListValue);
		}			

		List<WebElement> elements = driver.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout[@index='"+Index+"']/android.widget.TextView[@index='0']");
		int size = elements.size();
		for(WebElement element: elements){			
			size--;
			if(element.getText().equalsIgnoreCase(pickListValue)){	
				element.click();
				takeScreenshot("Pick List Value - <b>"+pickListValue+"</b> is selected");
				break;
			}else if(size==0){
				takeScreenshot("Pick List Value - <b>"+pickListValue+"</b> is not selected");
			}
		}

	}
	
	
	
	public void enterpicklistvalue(String pickListValue) {
		
		waitCommand(ID_PICKLIST_SEARCHFIELD);
		if(pickListValue.contains("#")){
			pickListValue = getRuntimeTestdata(pickListValue);
		}	
		MobileElement  element = (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");			
		driver.pressKeyCode(112); // DELETE Key event - https://developer.android.com/reference/android/view/KeyEvent.html#KEYCODE_FORWARD_DEL
		element.setValue(pickListValue);
		takeScreenshot(pickListValue);
		
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
				takeScreenshot(valueSplit[1]);
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
	
	public void isFieldDisplayed(String fieldName) {		
		if (isElementPresent(By.xpath(String.format(XPATH_TXT, fieldName)),"Field Name - "+fieldName)) {
			report(driver,test, "Field with label <b>'"+fieldName+"'</b> is displayed", LogStatus.PASS);			
		} else {
			report(driver,test, "Field with label <b>'"+fieldName+"'</b> is not displayed", LogStatus.FAIL);			
		}
	}
	
	
	public void isNotFieldDisplayed(String fieldName) {		
		if (!isElementPresent(By.xpath(String.format(XPATH_TXT, fieldName)),"Field Name - "+fieldName)) {
			report(driver,test, "Field with label <b>'"+fieldName+"'</b> is not displayed", LogStatus.PASS);			
		} else {
			report(driver,test, "Field with label <b>'"+fieldName+"'</b> is displayed", LogStatus.FAIL);			
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
		
	    String value = null;
		if (msg.contains("@")){
			String[] key =msg.split("@");
			String message1 =  key [0];
			String message2 =  key[1];
			String message3 =  key[2];

			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
				value = distributedRuntimeDataProperties.getProperty(message2);
			}else {
				value = parallelRuntimeDataProperties.getProperty(message2);	
			}

			String message = message1 +value+message3;
			msg=message;

		}		
		
		
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
	
	public void clickOkConfirmPromptContains(String msgContains) throws TimeoutException, NoSuchElementException{		
		if (GetText(ID_MESSAGE, GetText(ID_ALERT_TITLE, "Alert Title")).contains(msgContains)) {
			report(driver,test, msgContains + " is displayed", LogStatus.PASS);		
			Click(ID_MESSAGE_OK, "Clicked 'Ok' for prompt - " + msgContains);
		} else {
			report(driver,test, msgContains + " is not displayed", LogStatus.FAIL);			
		}
	}


	public void clickOkPrompt(String msg) throws TimeoutException, NoSuchElementException{

		String data1 = null;
		String data2 = null;
		String data3 = null;
		String errormessage1 = null;
		String errormessage2 = null;
		String errormessage3 = null;
		String errormessage4 = null;
		String errormessage5 = null;
		String errormessage = null;
		
		if (msg.contains("@")){
			String[] key =msg.split("@");
			
			if(key.length == 3 ) {
			errormessage1 =  key[0];
			errormessage2 =  key[1];
			errormessage3 =  key[2];
			
			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
				data1 = distributedRuntimeDataProperties.getProperty(errormessage2);
				}else {
				data1 = parallelRuntimeDataProperties.getProperty(errormessage2);	
				}

				errormessage = errormessage1 +data1+errormessage3;
				msg=errormessage;
			
			}else if(key.length == 2){
			errormessage1 =  key [0];
			errormessage2 =  key[1];
			

			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
				data1 = distributedRuntimeDataProperties.getProperty(errormessage2);
				}else {
				data1 = parallelRuntimeDataProperties.getProperty(errormessage2);	
				}
			
			errormessage = errormessage1 +data1;
			msg=errormessage;
			}else if(key.length == 5){
				errormessage1 =  key[0];
				errormessage2 =  key[1];
				errormessage3 =  key[2];
				errormessage4 =  key[3];
				errormessage5 =  key[4];
				
				if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {	
					data1 = distributedRuntimeDataProperties.getProperty(errormessage2);
					data2 = distributedRuntimeDataProperties.getProperty(errormessage4);
					}else {
					data1 = parallelRuntimeDataProperties.getProperty(errormessage2);
					data2 = parallelRuntimeDataProperties.getProperty(errormessage4);
					}

					errormessage = errormessage1 +data1+errormessage3+data2+errormessage5;
					msg=errormessage;
				
				}

			

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

			}else {

				test.log(LogStatus.FAIL,"Prompt <b>"+prompt+"</b> is not displayed");
			}
		}else {

			test.log(LogStatus.FAIL,"Alert is not displayed");

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
					test.log(LogStatus.PASS, testParameters.getCurrentTestCase() + "#" + columnName + " : "+data , "");
				}else {
					parallelRuntimeDataProperties.put(testParameters.getCurrentTestCase() + "#" + columnName, data);
					test.log(LogStatus.PASS, testParameters.getCurrentTestCase() + "#" + columnName + " : "+data , "");
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
		waitForSeconds("3");

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
				takeScreenshot(field + " is populated as expected");
			}else {
				test.log(LogStatus.FAIL, "<b>"+ field + "</b></br>Expected - <b>" + data + "</b></br>"
						+ "Actual - <font color=red><b>" + fieldValue +"</font></b>", "");				
				takeScreenshot(field + " is not populated as expected");				
			}
		}else if(field!=null && fieldValue.equals("")) {
			test.log(LogStatus.INFO, "<b>"+ field + "</b></br> - Value is not expected in this field</b></br>"	, "");
		}
	}
	
	/**
	 * Function to verify mandatory field
	 * 	 
	 * @return void
	 * @author Saran
	 * @since 11/09/2017
	 * 
	 */
	public void verifymandatoryfield(String field ,String prompt) {

		waitCommand(By.xpath(String.format(XPATH_TXT, field)+"/following-sibling::android.view.View"));
		waitForSeconds("3");

		clickNext();

		if(isElementPresent(ID_MESSAGE, "Mandatory field :"+field)) {

			if (GetText(ID_MESSAGE,"Alert").equalsIgnoreCase(prompt)) {	

				test.log(LogStatus.PASS, "<b>"+field +"</b> is a mandatory field and displays alert message <b> "+prompt+"</b>  when user left it blank."  , "");

			}
			else {
				test.log(LogStatus.FAIL, "<b>"+prompt+"</b> is not displayed for mandatory field <b> "+field+"</b>  when user left it blank." , "");
			}
		}

	}
	
	/**
	 * Function to verify non mandatory field
	 * 	 
	 * @return void
	 * @author Saran
	 * @since 12/09/2017
	 * 
	 */
	public void verifynonmandatoryfield(String field ,String field1) {

		if(isElementPresent(By.xpath(String.format(XPATH_TXT, field)),field)) {
			clickNext();
		}

		if(isElementPresent(ID_MESSAGE, "Alert message")) {

			test.log(LogStatus.FAIL, "<b>"+field +"</b> is a non mandatory  and user not allowed to proceed by leaving it blank."  , "");

		}
		else {
			test.log(LogStatus.PASS, "<b>"+field +"</b> is a non mandatory  and user able to proceed by leaving it blank."  , "");
		}

		waitCommand(By.xpath(String.format(XPATH_TXT, field1)+"/following-sibling::android.view.View"));
		waitForSeconds("3");

		WebElement element =  driver.findElement(By.xpath(String.format(XPATH_TXT, field1)+"/following-sibling::android.view.View"));
		String fieldValue = element.getAttribute("name");
		String fieldValue1 = element.getText();
		if(fieldValue.equals("")||fieldValue1.equals("")) {
			test.log(LogStatus.PASS, "<b>"+ field1 +"</b>  is displayed as blank and it is non mandatory field", "");
		}
		else {
			

			test.log(LogStatus.FAIL, "<b>"+ field1 +"</b>  is not displayed as blank", "");
		}

	}
	public void getSystemGenerateValue(String fieldName) {
		
		waitCommand(By.xpath(String.format(XPATH_TXT, fieldName)+"/following-sibling::android.view.View"));
		waitForSeconds("2");
		
		WebElement element =  driver.findElement(By.xpath(String.format(XPATH_TXT, fieldName)+"/following-sibling::android.view.View"));
		String fieldValue = element.getAttribute("name");
		addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), fieldValue);
		
		test.log(LogStatus.INFO, "<b>"+ fieldName + "</b></br>System Generated value - <b>" + fieldValue + "</b></br>" , "");
		
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
				element.click();
				takeScreenshot(buttonText+" button is Clicked");
				break;
			}
		}
		}
		
		
		if(!isClicked) {
			takeScreenshot(button+" button is not Clicked");
		}
	}


	/**
	 * Function to get Pick List value
	 * 	 
	 * @return void
	 * @author Saran 
	 * @throws InterruptedException 
	 * @since 09/19/2017
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void isPickListLineValueRepeated(String referenceHeaderName, String referenceColumnValue)
			throws TimeoutException, NoSuchElementException, InterruptedException {

		waitCommand(ID_PICKLIST_SEARCHFIELD);

		if (referenceColumnValue.contains("#")) {
			referenceColumnValue = getRuntimeTestdata(referenceColumnValue);
		}

		@SuppressWarnings("unchecked")
		List<WebElement> currentVisibleHeaders = driver.findElementsByXPath(
				".//android.widget.LinearLayout[@resource-id='net.fulcrum.mobility:id/lookup_headers']"
						+ "/android.widget.TextView");

		List<WebElement> pickListLines = driver
				.findElementsByXPath(".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout");
		int pickListSize = pickListLines.size();
		
		boolean isReferenceColumnVisible = false;
		ArrayList<Integer> matchingPickListNumbers = new ArrayList<Integer>();

		for (int columnIndex = 0; columnIndex < currentVisibleHeaders.size(); columnIndex++) {
				
			if (currentVisibleHeaders.get(columnIndex).getText().equalsIgnoreCase(referenceHeaderName)) {
				isReferenceColumnVisible = true;
				for (int pickListLine = 0; pickListLine < pickListSize; pickListLine++) {

					List<WebElement> pickListColumnJValues = driver.findElementsByXPath(
							".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout[@index='"
									+ pickListLine + "']/android.widget.TextView");
					
						if (pickListColumnJValues.get(columnIndex).getText().equalsIgnoreCase(referenceColumnValue)) {
							matchingPickListNumbers.add(pickListLine);
						}


				}

			}

		}
		
		if(!isReferenceColumnVisible) {
			swipingHorizontal("Right To Left");
			isPickListLineValueRepeated(referenceHeaderName,referenceColumnValue);
		}else {		
		
		if (matchingPickListNumbers.size() > 1) {
				test.log(LogStatus.INFO,"<b>"+ matchingPickListNumbers.size() + "</b> lines with same <b>" + referenceHeaderName
						+ "</b> - <b>" + referenceColumnValue + "</b> is identified");
				addRuntimeTestData("TOTAL_MATCHING_LINES", String.valueOf(matchingPickListNumbers.size()));
				for(int i = 0;i<matchingPickListNumbers.size();i++) {
					addRuntimeTestData("MATCHING_LINENUMBER_"+i, String.valueOf(matchingPickListNumbers.get(i)));
				}
				
			} else if (matchingPickListNumbers.size() == 1) {
				test.log(LogStatus.INFO,
						"Only one line with " + referenceHeaderName + " - " + referenceColumnValue + "is identified");
				addRuntimeTestData("TOTAL_MATCHING_LINES", String.valueOf(matchingPickListNumbers.size()));
			} else {
				test.log(LogStatus.INFO,
						"No line with " + referenceHeaderName + " - " + referenceColumnValue + "is identified");
			}
		}
	}
	
	
	public void compareLineValues(String verifyColumnNames) throws InterruptedException {

		String[] splittedVerifyColumnNames = null;		
		List<String> lineValuesList  = new ArrayList<String>();
		TreeSet<String> nonduplicateValuesSet = new TreeSet<String>(new StringComparator());
		boolean isReferenceColumnVisible = false;

		
		int totalMatchingLines = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase() + "#TOTAL_MATCHING_LINES"));
		
		if (verifyColumnNames.contains("@")) {
			splittedVerifyColumnNames = verifyColumnNames.split("@");
		} else {
			splittedVerifyColumnNames = new String[1];
			splittedVerifyColumnNames[0] = verifyColumnNames;
		}

		@SuppressWarnings("unchecked")
		List<WebElement> currentVisibleHeaders = driver.findElementsByXPath(
				".//android.widget.LinearLayout[@resource-id='net.fulcrum.mobility:id/lookup_headers']"
						+ "/android.widget.TextView");

		
		for(int i=0;i<splittedVerifyColumnNames.length;i++) {
			
		for (int columnIndex = 0; columnIndex < currentVisibleHeaders.size(); columnIndex++) {

			if (currentVisibleHeaders.get(columnIndex).getText().equalsIgnoreCase(splittedVerifyColumnNames[i])) {
				isReferenceColumnVisible = true;
				for (int j = 0; j < totalMatchingLines; j++) {

					@SuppressWarnings("unchecked")
					List<WebElement> values = driver.findElementsByXPath(
							".//android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout[@index='"
									+ getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MATCHING_LINENUMBER_" + j)
									+ "']/android.widget.TextView");
					
					
					lineValuesList.add(values.get(columnIndex).getText());
			

				}
				
				for(int j=0;j<lineValuesList.size();j++)
				{
				    if(!nonduplicateValuesSet.add(lineValuesList.get(j)))
				    {
				    	test.log(LogStatus.FAIL,"Duplicate value found for column '<b>"+splittedVerifyColumnNames[i]+"<b/>' in pick list line number  <b>"+getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MATCHING_LINENUMBER_" + j)+"</b>");
				    }else {
				    	test.log(LogStatus.PASS,"Line Number: <b>"+(Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MATCHING_LINENUMBER_" + j))+1)+"</b></br>"
				    						    +"<b>"+splittedVerifyColumnNames[i]+"<b/> - <b>"+lineValuesList.get(j)+"</b>");
				    }
				}
				
				if(nonduplicateValuesSet.size() == totalMatchingLines) {
					test.log(LogStatus.PASS,"<b>No duplicate value found for column '"+splittedVerifyColumnNames[i]+"'</b>");
				}
				
				
				
			}
		}
		}
			
			if(!isReferenceColumnVisible) {
				swipingHorizontal("Right To Left");
				compareLineValues(verifyColumnNames);
			}

		}
	

	
}

class StringComparator implements Comparator<String>
{	
	@Override
	public int compare(String arg0, String arg1) {		
		return arg0.compareTo(arg1);
		
	}
}
