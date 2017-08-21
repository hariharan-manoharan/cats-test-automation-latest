package main.java.businessComponents.MOBILE.AIRTEL;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import main.java.executionSetup.TestParameters;
import main.java.testDataAccess.DataTable;


public class CustomLibrary extends ReusableLibrary implements RoutineObjectRepository {
	
	public Lock lock;
	public Connection connection;

	
	@SuppressWarnings("rawtypes")
	public CustomLibrary(ExtentTest test, AndroidDriver driver, DataTable dataTable, TestParameters testParameters, Lock lock, Connection connection) {
		super(test, driver, dataTable, testParameters, lock, connection);
		this.lock = lock;
		this.connection = connection;
	}

	public CustomLibrary(ExtentTest test, AndroidDriver driver, DataTable dataTable, TestParameters testParameters, Lock lock, Connection connection, Properties runtimeDataProperties) {
		super(test, driver, dataTable, testParameters, lock, connection, runtimeDataProperties);
		this.lock = lock;
		this.connection = connection;
	}
	
	/************************************************************************************************
	 * Function :enterTransferOrder 
	 * Decsription:Function to used enter TransferOrder in Pack Routine
	 * Date :24-07-2017 
	 * Author :Saran
	 *************************************************************************************************/
	public void enterTransferOrder(String locationName, String columnName){

		String TRANSFERCOUNT = String.format(TRANSFERCOUNT_PACK, locationName,locationName,locationName);

		String NoofTransfer = selectQuerySingleValue(TRANSFERCOUNT, "TRANSFERCOUNT");

		int count = 	Integer.parseInt(NoofTransfer);

		String data = getRuntimeTestdata(columnName);

		if (count>1){
			enterText("Enter Transfer Order (*) :", data);
			clickNext();
			clickConfirmPrompt("Generate new shipment?", "Yes");
		}
		else
		{
			clickConfirmPrompt("Generate new shipment?", "Yes");
			verifyAutopopulatefieldvalues("Transfer Order", data);
		}
	}
	
	/************************************************************************************************
	 * Function :enterShipmentNumber 
	 * Decsription:Function to used enter ShipmentNumber in InternalReceive Routine
	 * Date :24-07-2017 
	 * Author :Saran
	 *************************************************************************************************/
	public void enterShipmentNumber(String locationName, String columnName){

		String SHIPMENTCOUNT = String.format(SHIPMENTCOUNT_IR, locationName,locationName,locationName);

		String Noofshipments = selectQuerySingleValue(SHIPMENTCOUNT, "SHIPMENTCOUNT");

		int count = 	Integer.parseInt(Noofshipments);

		String data = getRuntimeTestdata(columnName);

		if (count>1){
			enterText("Enter Shipment # (*) :", data);
			clickNext();
		}
		else
		{
			verifyAutopopulatefieldvalues("Shipment #", data);
		}
	}
	
	/************************************************************************************************
	 * Function :enterSiteID 
	 * Decsription:Function to used enter SiteID in MRRSITEReceive Routine
	 * Date :07-08-2017 
	 * Author :Saran
	 *************************************************************************************************/
	public void enterSiteID(String locationName){
		
		String TRANSLATION= String.format(TRANSLATION_COUNT, locationName);
		String Translationcount = selectQuerySingleValue(TRANSLATION, "TRANSLATIONCOUNT");

		int count = 	Integer.parseInt(Translationcount);
		
		if(count>1){
			
			clickNextMultiple(Integer.toString(2));
		}
		else{
		    clickNext();
		}
	}
	
	
	  /************************************************************************************************
	   * Function :getappinfo 
	   * Decsription:Function to used get application info
	   * Date :21-08-2017 
	   * Author :Saran
	   *************************************************************************************************/
	  public void getappinfo(){
	    
	    driver.findElement(By.id("home")).click();
	    takeScreenshot("Environmet/User/Profile");

	    WebElement element = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").text(\"Info\").clickable(true)");
	    element.click();
	    takeScreenshot("App Info");
	    
	    waitCommand(By.id("info_client_version"));
	    
	    String clientversion = driver.findElement(By.id("info_client_version")).getText();
	    String serverversion = driver.findElement(By.id("info_server_version")).getText();
	    String catsversion = driver.findElement(By.id("info_cats_version")).getText();
	    
	    test.log(LogStatus.INFO, "Client Version : <b>" + clientversion +"</b>");
	    test.log(LogStatus.INFO, "Server Version : <b>"+ serverversion+"</b>");
	    test.log(LogStatus.INFO, "CATS Version : <b>"+catsversion+"</b>");
	    
	    driver.findElement(By.id("info_done_button")).click();

	  }
}
