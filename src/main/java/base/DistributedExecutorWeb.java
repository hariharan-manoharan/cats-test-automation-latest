package main.java.base;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.Map.Entry;

import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import com.codepine.api.testrail.TestRailException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import main.java.executionSetup.ExecutionType;
import main.java.executionSetup.TestParameters;
import main.java.testDataAccess.DataTable;
import main.java.utils.TestRailListener;
import main.java.utils.Utility;
import main.java.utils.WebDriverFactory;


public class DistributedExecutorWeb extends Utility implements Runnable {

	private ExtentReports report; 
	private ExtentTest test;
	private TestParameters testParameters;
	private ExecutionType executionType;
	private DataTable dataTable;
	private WebDriver webdriver;
	private TestRailListener testRailListenter;
	String testRailEnabled = testRailProperties.getProperty("testRail.enabled");
	int projectId = Integer.parseInt(testRailProperties.getProperty("testRail.projectId"));
	private Lock lock;
	private Connection connection;





	public DistributedExecutorWeb(TestParameters testParameters, ExtentReports report, ExecutionType executionType, DataTable dataTable, TestRailListener testRailListenter, Lock lock) {
		this.testParameters = testParameters;
		this.report = report;
		this.executionType = executionType;
		this.dataTable = dataTable;
		this.testRailListenter = testRailListenter;
		this.lock = lock;
	}

	public DistributedExecutorWeb(TestParameters testParameters, ExtentReports report, ExecutionType executionType, DataTable dataTable,Lock lock) {
		this.testParameters = testParameters;
		this.report = report;
		this.executionType = executionType;
		this.dataTable = dataTable;	
		this.lock = lock;

	}




	public void run() {



		LinkedHashMap<String, String> keywordMap=new LinkedHashMap<String,String>();
		try {
			intializeWebDriver();
			if(testParameters.getExecuteCurrentTestCase().equalsIgnoreCase("Yes")) {

				test = report.startTest(testParameters.getCurrentTestCase() + " : " + testParameters.getDescription());
				dataTable.setCurrentRow(testParameters.getCurrentTestCase());
				test.log(LogStatus.INFO, testParameters.getCurrentTestCase() + " execution started", "");
				test.log(LogStatus.INFO, "Current Thread - "+Thread.currentThread().getName().toString(), "");

				//To make connection with SQL
				connection = Getconnections();

				keywordMap = getKeywords();

				if(!keywordMap.isEmpty()) {
					executeKeywords(keywordMap);
				}

				//To disconnect the connection
				Closeconnections(connection);



				test.log(LogStatus.INFO, testParameters.getCurrentTestCase() + " execution completed", "");
				report.endTest(test);
				report.flush();		

				testRailReport();
			} 
		} catch (SessionNotCreatedException e) {
			test.log(LogStatus.FAIL, "Driver setup not done Successfully", "");
			test.log(LogStatus.FAIL, e);	
			report.flush();
			testRailReport();
			return;
		} catch (ExecuteException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			test.log(LogStatus.FAIL, e);	
			if(driver!=null){
				report(driver, test, "Exception occured", LogStatus.FAIL);
				//exceptionHandler();
			}
			report.flush();	
			testRailReport();
			return;
		} catch (IOException | InterruptedException | TimeoutException | NoSuchElementException e) {
			test.log(LogStatus.FAIL, e);
			if(driver!=null){
				report(driver, test, "Exception occured", LogStatus.FAIL);
				//	exceptionHandler();
			}
			report.flush();	
			testRailReport();
			return;
		} catch (SQLException  e) {
			test.log(LogStatus.FAIL, "DB Connection not established");
			test.log(LogStatus.FAIL, e);
			if(driver!=null){
				report(driver, test, "Exception occured", LogStatus.FAIL);
				//exceptionHandler();
			}
			report.flush();	
			testRailReport();
			return;
		}catch (Exception e) {
			test.log(LogStatus.FAIL, e);
			if(driver!=null){
				report(driver, test, "Exception occured", LogStatus.FAIL);
				//	exceptionHandler();
			}
			report.flush();	
			testRailReport();
			return;
		} 
	}
	
	
	private void executeKeywords(LinkedHashMap<String, String> keywords) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		
		Class<?> className = Class.forName("main.java." + executionType + "." + properties.getProperty("Project") + ".FunctionalComponents");
		Constructor<?> constructor = className.getDeclaredConstructors()[0];
		Object classInstance = constructor.newInstance(test, webdriver, dataTable, testParameters, lock, connection);
		
		for (Entry<String, String> map : keywords.entrySet()) {
			
			if(!map.getKey().equals("TC_ID")) {
				String currentKeyword = map.getValue().substring(0,1).toLowerCase() + map.getValue().substring(1);
				test.log(LogStatus.INFO, "<font size=2 face = Bedrock color=blue><b>"+currentKeyword.toUpperCase()+"</font></b>" );
				Method method = className.getMethod(currentKeyword);
				method.invoke(classInstance);
			}
			
		}

		end();
	}

	public LinkedHashMap<String, String> getKeywords() {
		LinkedHashMap<String, String> keywordMap = new LinkedHashMap<String, String>();

		keywordMap = dataTable.getRowData("BusinessFlow", testParameters.getCurrentTestCase());

		if(keywordMap.isEmpty()) {
			test.log(LogStatus.FATAL, "No Business flow available for current test case in worksheet - <b>BusinessFlow</b>");
		}



		return keywordMap;

	}

	private void testRailReport() {



		String groupedTestRailTestcaseIDs[] = null;

		try {

			if (testRailProperties.getProperty("testRail.enabled").equalsIgnoreCase("True")) {	

				if(!testParameters.getCurrentTestCase().contains("STAGE_DATA") && !testParameters.getTestRailTestcaseID().equalsIgnoreCase("NA")) {	

					if(testParameters.getTestRailTestcaseID().contains(",")) {
						groupedTestRailTestcaseIDs = testParameters.getTestRailTestcaseID().split(",");

						for(int i=0;i<groupedTestRailTestcaseIDs.length;i++) {
							updateTestRail(groupedTestRailTestcaseIDs[i]);					
						}

					}else {					
						updateTestRail(testParameters.getTestRailTestcaseID());
					}


				}

			}
		}catch(RuntimeException e) {
			test.log(LogStatus.WARNING, "Exception occured while updating test result in test rail.");
			test.log(LogStatus.WARNING, e);
		}


	}	



	private void updateTestRail(String testRailTCID) {

		String[] testRailRunID = null;

		if(testRailProperties.getProperty("testRail.runId").contains(",")) {		
			testRailRunID = testRailProperties.getProperty("testRail.runId").split(",");
		}else {
			testRailRunID= new String[1];
			testRailRunID[0] =  testRailProperties.getProperty("testRail.runId");			
		}

		try {

			if (test.getRunStatus() == LogStatus.PASS && testRailEnabled.equalsIgnoreCase("True")) {
				testRailListenter.addTestResult(Integer.parseInt(testRailRunID[0]),Integer.parseInt(testRailTCID), 1);
				test.log(LogStatus.INFO, "Result for Test case with ID <b>" + testRailTCID + "</b> is <b>PASSED</b> in TestRail (Test Run ID - <b>"+testRailRunID[0]+"</b>)");
			} else if (testRailEnabled.equalsIgnoreCase("True")) {
				testRailListenter.addTestResult(Integer.parseInt(testRailRunID[0]),Integer.parseInt(testRailTCID), 5);
				test.log(LogStatus.INFO, "Result for Test case with ID <b>" + testRailTCID + "</b> is <b>FAILED</b> in TestRail (Test Run ID - <b>"+testRailRunID[0]+"</b>)");
			}
		} catch (TestRailException e) {
			if(e.getResponseCode()==400) {
				test.log(LogStatus.INFO, "Result for Test case with ID <b>" + testRailTCID + "</b> is not updated in Test Run with ID <b>"+testRailRunID[0]+"</b>. Response code '400' is returned (400 - No (active) test found for the run/case combination.)");
				test.log(LogStatus.INFO,"Checking if more than one Test Run ID's are provided.");

				int runIndex = 0;

				try {
					if(testRailRunID.length>1) {					
						for(runIndex =1;runIndex<testRailRunID.length;runIndex++) {
							if (test.getRunStatus() == LogStatus.PASS && testRailEnabled.equalsIgnoreCase("True")) {
								testRailListenter.addTestResult(Integer.parseInt(testRailRunID[runIndex]),Integer.parseInt(testRailTCID), 1);
								test.log(LogStatus.INFO, "Result for Test case with ID <b>" + testRailTCID + "</b> is <b>PASSED</b> in TestRail (Test Run ID - <b>"+testRailRunID[runIndex]+"</b>)");
							} else if (testRailEnabled.equalsIgnoreCase("True")) {
								testRailListenter.addTestResult(Integer.parseInt(testRailRunID[runIndex]),Integer.parseInt(testRailTCID), 5);
								test.log(LogStatus.INFO, "Result for Test case with ID <b>" + testRailTCID + "</b> is <b>FAILED</b> in TestRail (Test Run ID - <b>"+testRailRunID[runIndex]+"</b>)");
							}
						}
					}else {
						test.log(LogStatus.INFO,"Only one Test Run is provided.Not updating results in Test Rail.");				

					}
				} catch (TestRailException ex) {
					test.log(LogStatus.INFO, "Result for Test case with ID <b>" + testRailTCID + "</b> is not updated in Test Run with ID <b>"+testRailRunID[runIndex]+"</b>. Response code '400' is returned (400 - No (active) test found for the run/case combination.)");

				}
			}
		}

	}

	public void end() {

		if(webdriver!=null) {
			webdriver.quit();	
		}

	}

	private  void intializeWebDriver() {

		webdriver = WebDriverFactory.getDriver(testParameters.getBROWSER_NAME());

	}
}
