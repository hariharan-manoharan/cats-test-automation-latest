/**
 * 
 */
package main.java.framework.base;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import main.java.framework.executionSetup.ExecutionType;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.reporting.HtmlReport;
import main.java.framework.testDataAccess.DataTable;
import main.java.framework.testDataAccess.DataTableAbstractFactory;
import main.java.framework.testDataAccess.DataTableFactoryProducer;
import main.java.framework.utils.AppiumServerHandler;
import main.java.framework.utils.CopyLatestResult;
import main.java.framework.utils.FrameworkProperties;
import main.java.framework.utils.TestRailListener;
import main.java.framework.utils.Utility;


/**
 * <h1>This class contains the main method and controls the flow of the framework.</h1>
 * 
 * @author HARISH
 * @version 1.0
 * @since 05/10/2016
 */
public class Main{	

	private static ExtentReports report;
	private static String absolutePath;
	private static Properties properties;
	private static Properties runtimeDataProperties;
	private static Properties testRailProperties;
	private static Properties desiredCapabilitiesProperties;
	private static DataTableAbstractFactory runManagerFactory;
	private static DataTable runManager;
	private static DataTableAbstractFactory dataTableFactory;
	private static DataTable dataTable;
	private static ExecutionType executionType;
	private static TestRailListener testRailListenter;
	private static ArrayList<TestParameters> testInstancesToRun;	
	private static ArrayList<Integer> setCategoryList = new ArrayList<>();
	private static ArrayList<TestParameters> groupedTestInstances = null;
	private static ArrayList<ArrayList<TestParameters>> groupedtestInstancesToRun;	
	private static int nThreads;
	private static FrameworkProperties globalRuntimeDataProperties;
	private static FrameworkProperties globalProperties;
	private static FrameworkProperties frameworkTestRailProperties;
	private static FrameworkProperties globalDesiredCapabilitiesProperties;
	private static Utility utility;
	private static ExtentTest setUpReport;
	private static Lock lock;
	private static ArrayList<AndroidDriver> androidDriverList = new ArrayList<>();
	private static ArrayList<AppiumServerHandler> appiumServerInstanceList = new ArrayList<>();
	static ArrayList<String> adbDevices = null;
	
	private static String reportFolderName;	
	private static boolean issueWithSetup = false;
	private static boolean appsdatavsmobility = true;
	private static final String globalPropertyFilePath = "./resources/PropertyFiles/GlobalProperties.properties";
	private static final String globalRuntimeDataPropertyFilePath = "./resources/PropertyFiles/GlobalRuntimeDataProperties.properties";
	private static final String testRailPropertyFilePath = "./resources/PropertyFiles/TestRail.properties";
	private static final String desiredCapabilityPropertyFilePath = "./resources/DesiredCapabilities/DesiredCapabilities.properties";
	private static final String FILENAME = "\\adbDevices-list.txt";

	

	/**
	 * This is the main method.
	 *  
	 * @param args
	 */
	
	public static void main(String[] args) {		
		
		prepare();	
		setReportFolderName();	
		initializeTestReport();
		initializeTestRailReporting();
		collectRunInfo();		
		setup();
		execute();
		tearDown();			
	}
	
	/**
	 * prepare method is used to get framework and execution related properties.
	 * 
	 * @param nil
	 * 
	 */

	private static void prepare() {
		
		setAbsolutePath();
		collectGlobalProperties();
		collectTestRailProperties();
		
		String executionType = properties.getProperty("ExecutionType");
		if(executionType.equalsIgnoreCase("MOBILE")) {
		identifyListofDevicesConnected();
		}
		collectDesiredCapabilitiesProperties();
		
	}	
	
	/**
	 * collectRunInfo method gathers the test instances to run from Run manager.xls.
	 * 
	 * @param nil
	 * 
	 */

	@SuppressWarnings("unchecked")
	private static void collectRunInfo() {
		
		String runManagerType = properties.getProperty("RunManagerType");
		String runManagerName = properties.getProperty("RunManagerName");
		String testsuit = properties.getProperty("TestSuite");
		
		runManagerFactory = DataTableFactoryProducer.getDataTableFactory();
		runManager = runManagerFactory.getTestDataTableAccess(runManagerType, "./"+runManagerName);		
		testInstancesToRun = runManager.getRunManagerInfo(testsuit);
		
		if(testInstancesToRun.isEmpty()) {
			issueWithSetup = true;
			setUpReport = report.startTest("Run Manager status");
			setUpReport.log(LogStatus.FATAL, "No test cases are selected in Run Manager for execution.");
			report.flush();
		}
		
		Collections.sort(testInstancesToRun);
		
		for(int i=0;i<testInstancesToRun.size();i++) {
			setCategoryList.add(Integer.parseInt(testInstancesToRun.get(i).getSetCategory())) ;
		}
		
		setCategoryList = new ArrayList<Integer>(new LinkedHashSet<Integer>(setCategoryList));
		
		Utility utility = new Utility();
		utility.setEnvironmentVariables(runManager.getRowData("EnvironmentDetails", properties.getProperty("Environment")));
		
			
	}

	/**
	 * setup method is used to initialize the Test data access,Reporting etc...
	 * 
	 * @param nil
	 * 
	 */
	
	private static void setup() {
		
		setUpExecutionMode();
		initializeDataTable();
			
	}
		
	
	/**
	 * execute method is used to handle the execution of the test instances. 
	 * 
	 * @param nil
	 * 
	 */

	private static void execute() {		

		
		if(!testInstancesToRun.isEmpty()) {
		String executionMode = properties.getProperty("ExecutionMode");
		String executionType = properties.getProperty("ExecutionType");
		
		
		if(executionType.equals("MOBILE")) {
		
		if(executionMode.equalsIgnoreCase("DISTRIBUTED")) {
			distributedExecution();
		}else if(executionMode.equalsIgnoreCase("PARALLEL")){
			parallelExecution();
		}
		}else {
			//To launch web application
			distributedExecutionWeb();
			
		}
		}		
	    
	}
	
	public static void distributedExecutionWeb() {
		
		groupedtestInstancesToRun = new ArrayList<ArrayList<TestParameters>>();
		initializeGlobalRuntimeDataProperties();
		
		ExtentReports report = initializeTestReport("TestSummary");
		
		Runnable testRunner = null;
		lock = new ReentrantLock();
		
		for (int i = 0; i < setCategoryList.size(); i++) {
			groupedTestInstances = new ArrayList<TestParameters>();
			for (int j = 0; j < testInstancesToRun.size(); j++) {
				if (Integer.parseInt(testInstancesToRun.get(j).getSetCategory()) == setCategoryList.get(i)) {
					groupedTestInstances.add(testInstancesToRun.get(j));
				}
			}
			groupedtestInstancesToRun.add(groupedTestInstances);					
		}
		
		for (int k = 0; k < groupedtestInstancesToRun.size(); k++) {

			ExecutorService distributedExecutionWeb = Executors.newFixedThreadPool(nThreads);


			int groupedTestInstanceSize = groupedtestInstancesToRun.get(k).size();			

			for (int currentTestInstance = 0; currentTestInstance < groupedTestInstanceSize; currentTestInstance++) {
				if (testRailProperties.getProperty("testRail.enabled").equalsIgnoreCase("True")) {
					testRunner = new DistributedExecutorWeb(groupedtestInstancesToRun.get(k).get(currentTestInstance), report,
							executionType, dataTable, testRailListenter, lock);
				} else {
					testRunner = new DistributedExecutorWeb(groupedtestInstancesToRun.get(k).get(currentTestInstance), report,
							executionType, dataTable, lock);
				}
				distributedExecutionWeb.execute(testRunner);
			}
			

			distributedExecutionWeb.shutdown();
			while (!distributedExecutionWeb.isTerminated()) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
		globalRuntimeDataProperties.writeGlobalRuntimeDataProperties(globalRuntimeDataPropertyFilePath, utility.getRuntimeDataProperties());	
		report.flush();
	}
	
	/**
	 * distributedExecution method is used to handle the execution of the test instances in distributed mode across available devices. 
	 * 
	 * @param nil
	 * 
	 */
	
	public static void distributedExecution() {
		
		groupedtestInstancesToRun = new ArrayList<ArrayList<TestParameters>>();
		String appSetup = properties.getProperty("appSetup");
		
		initializeGlobalRuntimeDataProperties();
		
		ExtentReports report = initializeTestReport("TestSummary");
		
		Runnable testRunner = null;
		lock = new ReentrantLock();
		
		if(adbDevices.size()>=nThreads) {

			for (int t = 0; t < nThreads; t++) {

				appiumServerSetup(t + 1);
				androidDriverSetUp(t + 1);
				if(appSetup.equalsIgnoreCase("True")) { 
					setupAppForTesting(t);
				}

			}
			for (int i = 0; i < setCategoryList.size(); i++) {
				if(appsdatavsmobility) {
					groupedTestInstances = new ArrayList<TestParameters>();
					for (int j = 0; j < testInstancesToRun.size(); j++) {
						if (Integer.parseInt(testInstancesToRun.get(j).getSetCategory()) == setCategoryList.get(i)) {
							groupedTestInstances.add(testInstancesToRun.get(j));
						}

					}
					groupedtestInstancesToRun.add(groupedTestInstances);
				}
			}
			
			Map<String, Integer> idleThreadPositionMap= decideWhenToKillIdleSession(groupedtestInstancesToRun);

			for (int k = 0; k < groupedtestInstancesToRun.size(); k++) {
				if(appsdatavsmobility) {
					
					if(!idleThreadPositionMap.isEmpty() && idleThreadPositionMap.get("POSITION")==k) {
						shutDownAppiumAndAndroidDriver(idleThreadPositionMap.get("IDLE_THREAD_COUNT"));
					}
					
					ExecutorService distributedExecutor = Executors.newFixedThreadPool(nThreads);

					int groupedTestInstanceSize = groupedtestInstancesToRun.get(k).size();			

					for (int currentTestInstance = 0; currentTestInstance < groupedTestInstanceSize; currentTestInstance++) {
						if (testRailProperties.getProperty("testRail.enabled").equalsIgnoreCase("True")) {
							testRunner = new DistributedExecutor(groupedtestInstancesToRun.get(k).get(currentTestInstance), report,
									executionType, dataTable, testRailListenter, lock,
									androidDriverList);
						} else {
							testRunner = new DistributedExecutor(groupedtestInstancesToRun.get(k).get(currentTestInstance), report,
									executionType, dataTable, lock,
									androidDriverList);
						}

						distributedExecutor.execute(testRunner);

					}

					distributedExecutor.shutdown();
					while (!distributedExecutor.isTerminated()) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}}

			globalRuntimeDataProperties.writeGlobalRuntimeDataProperties(globalRuntimeDataPropertyFilePath, utility.getRuntimeDataProperties());	
			report.flush();

		}else {
			issueWithSetup = true;
			setUpReport = report.startTest("Execution setup status");
			setUpReport.log(LogStatus.FATAL, "Number of adb devices connected <b>("+adbDevices.size()+")</b> is not equal to NumberOfNodes property <b>("+nThreads+").</b>");
			setUpReport.log(LogStatus.INFO, "<b>Number of adb devices connected should be greater than or equal to NumberOfNodes. Please check whether all the devices are connected properly.</b>");
			report.flush();
		}
	}
	
	
	private static Map<String, Integer> decideWhenToKillIdleSession(ArrayList<ArrayList<TestParameters>> groupedtestInstancesToRun) {

		int totalGroups = groupedtestInstancesToRun.size();
		ArrayList<Integer> noOfIdleSessions = new ArrayList<Integer>();
		Map<String, Integer> returnMap = new HashMap<String, Integer>();

		for (int i = 0; i < totalGroups; i++) {
			int groupSize = groupedtestInstancesToRun.get(i).size();
			noOfIdleSessions.add(nThreads - groupSize);
		}


		Stack<Integer> positionStack = new Stack<Integer>();
		Stack<Integer> idleThreadStack = new Stack<Integer>();	

			for (int j = 0; j < noOfIdleSessions.size() - 1; j++) {
				
				if (noOfIdleSessions.get(j) > 0) {
					int currentval =noOfIdleSessions.get(j);
					int nextval = noOfIdleSessions.get(j + 1);
					if ( currentval == nextval) {
						positionStack.push(j);
						idleThreadStack.push(currentval);
					}else if (currentval > nextval) {
						if(!positionStack.isEmpty()) {
							int positionStackSize = positionStack.size();
							for(int i=0;i<positionStackSize;i++) {
								positionStack.pop();
								idleThreadStack.pop();
							}
					}

				}
			}
			}
		
			if(!positionStack.isEmpty()) {
				returnMap.put("POSITION", positionStack.get(0));
				returnMap.put("IDLE_THREAD_COUNT", idleThreadStack.get(0));
				return returnMap;
			}else {
				return returnMap;
			}

	}

	/**
	 * parallelExecution method is used to handle the execution of the test instances in parallel mode across available devices. 
	 * 
	 * @param nil
	 * 
	 */

	public static void parallelExecution() {

		int numberOfNodes = Integer.parseInt(properties.getProperty("NumberOfNodes"));
		ExecutorService[] parallelExecutor = new ExecutorService[numberOfNodes] ;	
		String appSetup = properties.getProperty("appSetup");

		Runnable testRunner = null;
		lock = new ReentrantLock();

		if(adbDevices.size()>=nThreads) {

			for (int t = 0; t < numberOfNodes; t++) {

				appiumServerSetup(t + 1);
				androidDriverSetUp(t + 1);
				if(appSetup.equalsIgnoreCase("True")) {
					setupAppForTesting(t);
				}

			}

			for(int run = 0; run < numberOfNodes; run ++ ) {

				if(appsdatavsmobility) {
					parallelExecutor[run] = Executors.newFixedThreadPool(1);

					FrameworkProperties globalRuntimeDataProperties = FrameworkProperties.getInstance();
					Properties runtimeDataProperties = globalRuntimeDataProperties.loadPropertyFile(globalRuntimeDataPropertyFilePath);
					ExtentReports report = initializeTestReport("TestSummary_"+(run+1));

					for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun.size(); currentTestInstance++) {

						if (testRailProperties.getProperty("testRail.enabled").equalsIgnoreCase("True")) {
							testRunner = new ParallelExecutor(testInstancesToRun.get(currentTestInstance), report,
									executionType, dataTable, testRailListenter, lock,
									androidDriverList.get(run), runtimeDataProperties);
						} else {
							testRunner = new ParallelExecutor(testInstancesToRun.get(currentTestInstance), report,
									executionType, dataTable, lock,
									androidDriverList.get(run), runtimeDataProperties);					
						}				

						parallelExecutor[run].execute(testRunner);	

					}



				}
			}

			for(int run = 0; run < numberOfNodes; run ++ ) {
				if(appsdatavsmobility) {	
					parallelExecutor[run].shutdown();
					while (!parallelExecutor[run].isTerminated()) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}else {
			issueWithSetup = true;
			setUpReport = report.startTest("Execution setup status");
			setUpReport.log(LogStatus.FATAL, "Number of adb devices connected <b>("+adbDevices.size()+")</b> is not equal to NumberOfNodes property <b>("+nThreads+").</b>");
			setUpReport.log(LogStatus.INFO, "<b>Number of adb devices connected should be greater than or equal to NumberOfNodes. Please check whether all the devices are connected properly.</b>");
			report.flush();
		}

	}
	
	
	public static void appiumServerSetup(int selectDevice) {		
		AppiumServerHandler appiumServerHandler = new AppiumServerHandler(Integer.parseInt(desiredCapabilitiesProperties.getProperty("device"+selectDevice+".appium.port").trim())
														, desiredCapabilitiesProperties.getProperty("device"+selectDevice+".appium.bootstrap.port").trim(), reportFolderName);
		appiumServerHandler.appiumServerStart();	
		appiumServerInstanceList.add(appiumServerHandler);		
	}
	

	@SuppressWarnings("rawtypes")
	public static void androidDriverSetUp(int selectDevice) {
		
		try {
			
		AndroidDriver driver;	

		String absolutePath = new File(System.getProperty("user.dir")).getAbsolutePath();

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", adbDevices.get(selectDevice-1));
		capabilities.setCapability("udid", adbDevices.get(selectDevice-1));
		capabilities.setCapability("platformName", desiredCapabilitiesProperties.getProperty("platformName"));
		capabilities.setCapability("platformVersion", desiredCapabilitiesProperties.getProperty("platformVersion"));
		capabilities.setCapability("app", absolutePath + "\\resources\\Libs\\" + desiredCapabilitiesProperties.getProperty("app"));	
		capabilities.setCapability("appPackage", desiredCapabilitiesProperties.getProperty("appPackage"));
		capabilities.setCapability("appActivity", desiredCapabilitiesProperties.getProperty("appActivity"));
		capabilities.setCapability("unicodeKeyboard", desiredCapabilitiesProperties.getProperty("unicodeKeyboard"));
		capabilities.setCapability("resetKeyboard", desiredCapabilitiesProperties.getProperty("resetKeyboard"));
		capabilities.setCapability("newCommandTimeout", desiredCapabilitiesProperties.getProperty("newCommandTimeout"));
		capabilities.setCapability("noReset", desiredCapabilitiesProperties.getProperty("noReset"));

		
		driver = new AndroidDriver(new URL(	"http://" + properties.getProperty("RemoteAddress") + ":" + desiredCapabilitiesProperties.getProperty("device"+selectDevice+".appium.port") + "/wd/hub"),capabilities);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		androidDriverList.add(driver);
		
		} catch (MalformedURLException e) {		
			e.printStackTrace();
		}
		
	}
	
	
	
	

	public static void shutDownAppiumAndAndroidDriver() {

		if (!androidDriverList.isEmpty() && !appiumServerInstanceList.isEmpty())

			for (int i = 0; i < nThreads; i++) {

				if (androidDriverList.get(i) != null) {
					androidDriverList.get(i).quit();
				}

				if (appiumServerInstanceList.get(i) != null) {
					appiumServerInstanceList.get(i).appiumServerStop();
				}

			}
	}
	
	public static void shutDownAppiumAndAndroidDriver(int idleThreadCount) {

		if (!androidDriverList.isEmpty() && !appiumServerInstanceList.isEmpty())
			
			for(int i=idleThreadCount; idleThreadCount>0;idleThreadCount--) {

				if (androidDriverList.get(i) != null) {
					androidDriverList.get(i).quit();
				}

				if (appiumServerInstanceList.get(i) != null) {
					appiumServerInstanceList.get(i).appiumServerStop();
				}
				
			}
			
	}
	
	
	
	/**
	 * tearDown method is used to wrap up the execution. 
	 * 
	 * @param nil
	 * 
	 */

	private static void tearDown() {		
		
		frameworkTestRailProperties.writeGlobalRuntimeDataProperties(testRailPropertyFilePath, utility.getTestRailProperties());
		shutDownAppiumAndAndroidDriver();	
		
		if(properties.getProperty("RestartAdbServer").equalsIgnoreCase("True")) {
			executeShellScript(absolutePath+"\\adbKillServer.sh");	
		}
		
		try {
			
			if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {
			Desktop.getDesktop().open(new File(HtmlReport.staticReportPath.get(0)));
			}else {
				if (!issueWithSetup) {
				for(int i=1;i<=Integer.parseInt(properties.getProperty("NumberOfNodes"));i++) {
					Desktop.getDesktop().open(new File(HtmlReport.staticReportPath.get(i)));
				}
				}else {
					Desktop.getDesktop().open(new File(HtmlReport.staticReportPath.get(0)));
				}
			}
			CopyLatestResult copyLatestResult = new CopyLatestResult();
			copyLatestResult.copyLatestResultFolder();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * setAbsolutePath method is used to set the absolute path of the framework
	 * which is in turn used for saving the report files, accessing test data
	 * files, Run manager etc...    
	 * 
	 * @param nil
	 * 
	 */

	private static void setAbsolutePath() {
		
		absolutePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		
	}
		
	/**
	 * collectGlobalProperties method is used to gather Global properties from user  
	 * stored in a property file called GlobalProperties.properties.  
	 * 
	 * @param nil
	 * 
	 */
	
	private static void collectGlobalProperties() {

		globalProperties = FrameworkProperties.getInstance();
		properties = globalProperties.loadPropertyFile(globalPropertyFilePath);
		
		utility = new Utility();
		utility.setProperties(properties);

		
	}
	
	private static void collectTestRailProperties() {
	
	frameworkTestRailProperties = FrameworkProperties.getInstance();			
	testRailProperties = frameworkTestRailProperties.loadPropertyFile(testRailPropertyFilePath);			
	utility.setTestRailProperties(testRailProperties);
	
	}
	
	
	private static void collectDesiredCapabilitiesProperties() {
		
		globalDesiredCapabilitiesProperties = FrameworkProperties.getInstance();			
		desiredCapabilitiesProperties = globalDesiredCapabilitiesProperties.loadPropertyFile(desiredCapabilityPropertyFilePath);	
			
		}
	
	/**
	 * collectGlobalProperties method is used to gather Global properties from user  
	 * stored in a property file called GlobalProperties.properties.  
	 * 
	 * @param nil
	 * 
	 */
	
	private static void initializeGlobalRuntimeDataProperties() {

		globalRuntimeDataProperties = FrameworkProperties.getInstance();
		runtimeDataProperties = globalRuntimeDataProperties.loadPropertyFile(globalRuntimeDataPropertyFilePath);
		
		utility.setRuntimeDataProperties(runtimeDataProperties);

	}
	
	/**	 
	 * 
	 * 
	 * @param nil
	 * 
	 */
	
	private static void setUpExecutionMode() {
		
		executionType = ExecutionType.valueOf(properties.getProperty("ExecutionType"));
		nThreads = Integer.parseInt(properties.getProperty("NumberOfNodes"));
		
	}
	
	/**
	 * initializeDataTable method is used to create a DataTable object based on the input
	 * of TestDataTableType property. 
	 * 
	 * @param nil
	 * 
	 */

	private static void initializeDataTable() {
		
		String dataTablePath = null;
		String dataTableType = properties.getProperty("TestDataTableType");
		String dataTableName = properties.getProperty("TestDataTableName");
		
		if(dataTableType.equalsIgnoreCase("MSExcel")){
			dataTablePath = "./resources/TestData/MSExcel/"+dataTableName;
		}else if(dataTableType.equalsIgnoreCase("MSAccess")){
			dataTablePath = "./resources/TestData/MSAccess/"+dataTableName;
		}
		
		dataTableFactory = DataTableFactoryProducer.getDataTableFactory();		
		dataTable = dataTableFactory.getTestDataTableAccess(dataTableType, dataTablePath);			
	
	}
	
	/**	 
	 * 
	 * 
	 * @param nil
	 * 
	 */
	
	private static void setReportFolderName() {
		
		reportFolderName = "Run_" + getCurrentFormattedTime("dd_MMM_yyyy_hh_mm_ss");
		
	}
	
	private static ExtentReports initializeTestReport() {
		
		HtmlReport htmlReportParallel = new HtmlReport("TestSummary", reportFolderName);
		report = htmlReportParallel.initialize();
		report.loadConfig((new File("./resources/PropertyFiles/extent-report-config.xml")));
		report.addSystemInfo("Project", properties.getProperty("Project"));
		report.addSystemInfo("Environment", properties.getProperty("Environment"));
		report.addSystemInfo("Project ID", testRailProperties.getProperty("testRail.projectId"));
		report.addSystemInfo("Suite ID", testRailProperties.getProperty("testRail.suiteId"));
		report.addSystemInfo("Test Run name", testRailProperties.getProperty("testRail.testRunName"));		
		
		return report;
		
	}
	
	
	private static ExtentReports initializeTestReport(String reportName) {
		
		HtmlReport htmlReportParallel = new HtmlReport(reportName, reportFolderName);
		ExtentReports report = htmlReportParallel.initialize();
		report.loadConfig((new File("./resources/PropertyFiles/extent-report-config.xml")));
		report.addSystemInfo("Project", properties.getProperty("Project"));
		report.addSystemInfo("Environment", properties.getProperty("Environment"));
		report.addSystemInfo("Project ID", testRailProperties.getProperty("testRail.projectId"));
		report.addSystemInfo("Suite ID", testRailProperties.getProperty("testRail.suiteId"));
		report.addSystemInfo("Test Run name", testRailProperties.getProperty("testRail.testRunName"));		
		
		return report;
		
	}
	
	
	private static void initializeTestRailReporting(){			
		
		if (testRailProperties.getProperty("testRail.enabled").equalsIgnoreCase("True")) {			
		int projectId = Integer.parseInt(testRailProperties.getProperty("testRail.projectId"));
		testRailListenter = new TestRailListener(projectId);
		testRailListenter.initialize();
		if (testRailProperties.getProperty("testRail.addNewRun").equalsIgnoreCase("True")) {	
		testRailListenter.addTestRun();
		}
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	private static void setupAppForTesting(int driverIndex) {
		
		try {

		String Envapp = androidDriverList.get(driverIndex).findElement(By.id("my_connections")).getText();
		String Envdata = properties.getProperty("Environment");
		issueWithSetup = true;
		setUpReport = report.startTest("Login");

		if(Envapp.equalsIgnoreCase(Envdata)) {

			androidDriverList.get(driverIndex).findElement(By.id("username")).sendKeys("catsadm");;
			androidDriverList.get(driverIndex).findElement(By.id("password")).sendKeys("catscats11");;
		
			androidDriverList.get(driverIndex).findElement(By.id("btn_connect")).click();	
			waitCommand(By.id("action_bar_title"), androidDriverList.get(driverIndex));

			List<WebElement> elements = androidDriverList.get(driverIndex).findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\")");

			for(WebElement element: elements) {				
				if(element.getText().equalsIgnoreCase(properties.getProperty("userProfile"))) {
					element.click();
					break;
				}
			}

		}else {
			setUpReport.log(LogStatus.FATAL, "<b>"+Envdata +"</b> is not connected to <b>"+ Envapp + "</b> in CATS MOBILITY");
			appsdatavsmobility = false;
			report.flush();

		}
		}catch(Exception e){
			setUpReport.log(LogStatus.FAIL, "Exception occured while logging into application.");
			setUpReport.log(LogStatus.FAIL, e);
		}
	}
	
	
	/**
	 * FulentWait Function - Waits until the object is available with timeout of 100 seconds polling every 5 seconds
	 * 
	 * @param1 By by	
	 * @return void
	 * @author Hari
	 * @since 12/27/2016
	 * 
	 */
	
	
	public static void waitCommand(final By by, AndroidDriver driver) throws TimeoutException, NoSuchElementException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
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
	
	private static void executeShellScript(String shellScriptPath) {

		String[] cmdScript = null;
		
		try {

			cmdScript = new String[]{"bash", shellScriptPath}; 	
			
			Process procScript = Runtime.getRuntime().exec(cmdScript);

			procScript.waitFor();
			/*StringBuffer output = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(procScript.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				
			}
			
			System.out.println(output);*/
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	
	
	private static void identifyListofDevicesConnected() {
		
		String exeuteOverWifi = properties.getProperty("ExecuteOverWifi");
		String restartAdbServer = properties.getProperty("RestartAdbServer");
		
		if(restartAdbServer.equalsIgnoreCase("True")) {
			executeShellScript(absolutePath+"\\adbKillServer.sh");	
			executeShellScript(absolutePath+"\\adbStartServer.sh");	
		}
		
		if(exeuteOverWifi.equalsIgnoreCase("True")) {
			executeShellScript(absolutePath+"\\adbDevicesWifi.sh");
		}else {
			executeShellScript(absolutePath+"\\adbDevicesUsb.sh");
		}

		BufferedReader br = null;
		FileReader fr = null;
		adbDevices = new ArrayList<String>();	
		

		try {
			
			fr = new FileReader(absolutePath+FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			
			if(exeuteOverWifi.equalsIgnoreCase("True")) {
			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains(":")) {
					String[] splitCurentLine = sCurrentLine.split("\\s");
					if(splitCurentLine[1].equals("device")) {					
					adbDevices.add(splitCurentLine[0]);
					}
				}
			}
			}else {
				while ((sCurrentLine = br.readLine()) != null) {
					if(!sCurrentLine.equals("List of devices attached") && !sCurrentLine.equals("")) {
						String[] splitCurentLine = sCurrentLine.split("\\s");
						if(splitCurentLine[1].equals("device")) {					
						adbDevices.add(splitCurentLine[0]);
						}
					}
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
	
	
	/**
	 * Function to format the current time instance
	 * 
	 * @return String (formatted date)
	 * @author Hari
	 * @since 12/27/2016
	 * 
	 */

	public static String getCurrentFormattedTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
		
}
