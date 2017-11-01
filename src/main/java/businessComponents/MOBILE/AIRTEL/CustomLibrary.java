package main.java.businessComponents.MOBILE.AIRTEL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
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
		
		String[] key =columnName.split("@");
		
		String data = key [0];
		String generateshipmentno = key [1];
		String transferorder = getRuntimeTestdata(data);

		if (count>1){
			enterText("Enter Transfer Order (*) :", transferorder);
		
			clickNext();
			
			clickConfirmPrompt("Generate new shipment?", generateshipmentno);
		}
		else
		{
			clickConfirmPrompt("Generate new shipment?", generateshipmentno);
			verifyAutopopulatefieldvalues("Transfer Order", transferorder);
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
	  
	  
	  public void clickOkPromptmovefinish(String locationName) {
		  String COUNT= String.format(MOVESTARTLOC_COUNT, locationName);
		  String MovestartLoccount = selectQuerySingleValue(COUNT, "MOVESTARTLOCCOUNT");

		  int count = 	Integer.parseInt(MovestartLoccount);

		  if(count<1){
			  clickOkPrompt("There are no items to be received at this location.");
			  test.log(LogStatus.INFO, "There are no items to be received at this location.");
			  validateLoopField("Enter Location (*) :");
		  }
		  else {
			  test.log(LogStatus.INFO, "Items to be received at this location");
			  validateLoopField("Enter Barcode (*) :");
		  }
		  
		  
	  }
	  
	  

		/**
		 * Function to get single column data from Database
		 * 
		 * @param1 String query
		 * @param2 String dataRequired
		 * @return String data
		 * @author Hari
		 * @since 12/23/2016
		 * 
		 */

		public void getAssetCodeFromAssetTrx(String originatorTypeTrx, String lotNumber) {
			String data = null;
			Statement stmt;
			ResultSet rs;
			
			String query = "SELECT ASSETCODE FROM CATS_ASSETTRANSACTION WHERE ORIGINATORTYPETRX='"+originatorTypeTrx+"' AND LOTNUMBER='"+getRuntimeTestdata(lotNumber)+"' ORDER BY ASSETTRANSACTIONID DESC";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					data = rs.getString("ASSETCODE");
					if (!data.equals(null)) {
						break;
					}
					
				}
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "ASSETCODE" + " - <b>" + data +"</b>");
				test.log(LogStatus.FAIL, e);
			}
			
			if (!data.equals(null)) {
			test.log(LogStatus.INFO, "ASSETCODE" + " - <b>" + data +"</b>");
			addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), data);
			}
			

		}
		
		
		public void validateAssetStatus(String expectedStatus, String assetcode) {
			
			String actualStatus = null;
			Statement stmt;
			ResultSet rs;
			
						
			String query = "SELECT DESCRIPTION FROM CATS_LOCATIONSTATUS WHERE LOCATIONSTATUSID IN (SELECT LOCATIONSTATUSID FROM CATS_ASSET WHERE ASSETCODE='"+getRuntimeTestdata(assetcode)+"')";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					actualStatus = rs.getString("DESCRIPTION");
					if (!actualStatus.equals(null)) {
						break;
					}
					
				}
				
				if (!actualStatus.equals(null)) {
				if(expectedStatus.equalsIgnoreCase(actualStatus)) {
					test.log(LogStatus.PASS, "<b>Asset Status </b></br>"
												+ "<b>Expected </b> - "+expectedStatus+ "</br>"
												+ "<b>Actual </b> - "+actualStatus+ "</br>"
												);
					}else {
						test.log(LogStatus.FAIL, "<b>Asset Status </b></br>"
								+ "<b>Expected </b> - "+expectedStatus+ "</br>"
								+ "<b>Actual </b> - "+actualStatus+ "</br>"
								);
					}
				}
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while validating Asset Status");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		public void verifyAssetActiveState(String assetCode, String expectedValue) {			
		
			String isActive = null;
			Statement stmt;
			ResultSet rs;
			
						
			String query = "SELECT ACTIVE FROM CATS_ASSET WHERE ASSETCODE='"+getRuntimeTestdata(assetCode)+"'";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					isActive = rs.getString("ACTIVE");
					if (!isActive.equals(null)) {
						break;
					}
					
				}
				
				if (!isActive.equals(null)) {
				if(expectedValue.equalsIgnoreCase(isActive)) {
					test.log(LogStatus.PASS, "<b>Asset ("+getRuntimeTestdata(assetCode)+") Active State </b></br>"
												+ "<b>Expected </b> - "+expectedValue+ "</br>"
												+ "<b>Actual </b> - "+isActive+ "</br>"
												);
					}else {
						test.log(LogStatus.FAIL, "<b>Asset ("+getRuntimeTestdata(assetCode)+") Active State </b></br>"
								+ "<b>Expected </b> - "+expectedValue+ "</br>"
								+ "<b>Actual </b> - "+isActive+ "</br>"
								);
					}
				
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while getting Asset's Active State");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		public void verifyPartActiveState(String lotNumber, String expectedValue) {
			
			String isActive = null;
			Statement stmt;
			ResultSet rs;
			
						
			String query = "SELECT ACTIVE FROM CATS_PART WHERE PARTID IN (SELECT PARTID FROM CATS_PARTDETAIL WHERE LOTNUMBER='"+getRuntimeTestdata(lotNumber)+"')";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					isActive = rs.getString("ACTIVE");
					if (!isActive.equals(null)) {
						break;
					}
					
				}
				
				if(!isActive.equals(null)) {
					if(expectedValue.equalsIgnoreCase(isActive)) {
						test.log(LogStatus.PASS, "<b>Part with Lot number "+getRuntimeTestdata(lotNumber)+" Active State </b></br>"
													+ "<b>Expected </b> - "+expectedValue+ "</br>"
													+ "<b>Actual </b> - "+isActive+ "</br>"
													);
						}else {
							test.log(LogStatus.FAIL, "<b>Part Active State </b></br>"
									+ "<b>Expected </b> - "+expectedValue+ "</br>"
									+ "<b>Actual </b> - "+isActive+ "</br>"
									);
						}
					
				}else{				
				test.log(LogStatus.FAIL, "Part Detail record not found");
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while getting Part's Active State");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		
		public void getPartID(String partcode) {
			String data = null;
			Statement stmt;
			ResultSet rs;
			
			String query = "SELECT PARTID FROM CATS_PART WHERE PARTCODE='"+getRuntimeTestdata(partcode)+"'";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					data = rs.getString("PARTID");
					if (!data.equals(null)) {
						break;
					}
					
				}
				if (!data.equals(null)) {
				test.log(LogStatus.PASS, "PARTID for Part Code " +getRuntimeTestdata(partcode)+ " - <b>" + data +"</b>");
				addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), data);
				}
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while getting PARTID using Part Code");
				test.log(LogStatus.FAIL, e);
			}
			
			
			

		}
		

		public void getUpdatedPOCode(String poCodeLike) {
			String data = null;
			Statement stmt;
			ResultSet rs;
			
			String query = "SELECT POCODE FROM CATS_PO WHERE POCODE LIKE ('"+getRuntimeTestdata(poCodeLike)+"%')";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					data = rs.getString("POCODE");
					if (!data.equals(null)) {
						break;
					}
					
				}
				if (!data.equals(null)) {
				test.log(LogStatus.PASS, "Updated POCODE - <b>" + data +"</b>");
				addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), data);
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while getting updated POCODE");
				test.log(LogStatus.FAIL, e);
			}
			
			

		}
		public void activateBOM(String partcode) {
			String query = "UPDATE CATS_BOM SET ACTIVE='Y' WHERE PARTID IN (SELECT PARTID FROM CATS_PART WHERE PARTCODE='"+getRuntimeTestdata(partcode)+"')";
			 executeUpdateQuery(query, "Update BOM Item "+getRuntimeTestdata(partcode)+" ACTIVE='Y' ");
		}
		
		public void inactivateBOM(String partcode) {
			String query = "UPDATE CATS_BOM SET ACTIVE='N' WHERE PARTID IN (SELECT PARTID FROM CATS_PART WHERE PARTCODE='"+getRuntimeTestdata(partcode)+"')";
		    executeUpdateQuery(query, "Update BOM Item "+getRuntimeTestdata(partcode)+" ACTIVE='N' ");
			
		}
		
		
		
		public void verifyContainerContentsLocatorCodeAsset(String containerCode, String expectedValue) {
			
			
			String locatorcode = null;
			String assetcode = null;
			Statement stmt;
			ResultSet rs;
			
						
			String query = "SELECT LOCATORCODE, ASSETCODE FROM CATS_ASSET WHERE ASSETID IN "
							+ "(SELECT ASSETID FROM CATS_CONTAINERDETAIL WHERE CONTAINERID IN "
							+ "(SELECT CONTAINERID FROM CATS_CONTAINER WHERE CONTAINERCODE='"+getRuntimeTestdata(containerCode)+"'))";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					locatorcode = rs.getString("LOCATORCODE");
					assetcode =  rs.getString("ASSETCODE");
					if (!locatorcode.equals(null)) {
						break;
					}
					
				}
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying locatorcode of Container Contents - Asset");
				test.log(LogStatus.FAIL, e);
			}
			
			if(!locatorcode.equals(null)) {
				if(expectedValue.equalsIgnoreCase(locatorcode)) {
					test.log(LogStatus.PASS, "<b>Locatorcode of Asset - "+assetcode+"</b></br>"
												+ "<b>Expected </b> - "+expectedValue+ "</br>"
												+ "<b>Actual </b> - "+locatorcode+ "</br>"
												);
					}else {
						test.log(LogStatus.FAIL, "<b>Locatorcode of Asset - "+assetcode+"</b></br>"
								+ "<b>Expected </b> - "+expectedValue+ "</br>"
								+ "<b>Actual </b> - "+locatorcode+ "</br>"
								);
					}
				
			}else{				
			test.log(LogStatus.FAIL, "");
			}
		}
		
		public void verifyContainerContentsLocatorCodePart(String containerCode, String expectedValue) {
			
			
			String locatorcode = null;
			String partcode = null;
			Statement stmt;
			ResultSet rs;
			
						
			String query1 = "SELECT LOCATORCODE FROM CATS_PARTDETAIL WHERE LOCATORCODE IS NOT NULL AND CONTAINERID IN "
							+ "(SELECT CONTAINERID FROM CATS_CONTAINER WHERE CONTAINERCODE='"+getRuntimeTestdata(containerCode)+"')";
			
			String query2 = "SELECT PARTCODE FROM CATS_PART WHERE PARTID IN "
					+ "(SELECT PARTID FROM CATS_PARTDETAIL WHERE LOCATORCODE IS NOT NULL AND CONTAINERID  IN "
					+ "(SELECT CONTAINERID FROM CATS_CONTAINER WHERE CONTAINERCODE='"+getRuntimeTestdata(containerCode)+"'))";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query1);
				while (rs.next()) {
					rs.getObject(1);
					locatorcode = rs.getString("LOCATORCODE");
					if (!locatorcode.equals(null)) {
						break;
					}
					
				}
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying locatorcode of Container Contents - Part");
				test.log(LogStatus.FAIL, e);
			}
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query2);
				while (rs.next()) {
					rs.getObject(1);
					partcode = rs.getString("PARTCODE");
					if (!partcode.equals(null)) {
						break;
					}
					
				}
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while getting Partcode of Container Contents");
				test.log(LogStatus.FAIL, e);
			}
			
			if(!locatorcode.equals(null)) {
				if(expectedValue.equalsIgnoreCase(locatorcode)) {
					test.log(LogStatus.PASS, "<b>Locatorcode of Part - "+partcode+"</b></br>"
												+ "<b>Expected </b> - "+expectedValue+ "</br>"
												+ "<b>Actual </b> - "+locatorcode+ "</br>"
												);
					}else {
						test.log(LogStatus.FAIL, "<b>Locatorcode of Part - "+partcode+"</b></br>"
								+ "<b>Expected </b> - "+expectedValue+ "</br>"
								+ "<b>Actual </b> - "+locatorcode+ "</br>"
								);
					}
				
			}else{				
			test.log(LogStatus.FAIL, "");
			}
		}
		
		
		public void createMRRPOReceiptQuarantine(String iteration) {
			LinkedHashMap<String, String> dataMap = null;
			try {

				lock.lock();
				String validateMRR = "SELECT * FROM CATSCON_MRR_STG WHERE RECEIPT_NUM='%s' AND RECORD_ID=%d";
				
				if(iteration!=null) {
				 dataMap = dataTable.getRowData("Data_Staging",
						testParameters.getCurrentTestCase() + "_MRR"+iteration);
				}else {
					dataMap = dataTable.getRowData("Data_Staging",
							testParameters.getCurrentTestCase() + "_MRR");	
				}
				int recordId = createMRRPOReceiptQuarantineQuery(dataMap);
				validateInboundTransaction("MRR", "PROCESS_FLAG", "ERROR_MESSAGE", validateMRR,
						getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MRRNUMBER"), recordId);
				poTaxUpdateQuery(dataMap);

			} finally {
				lock.unlock();
			}
		}
		
		
		public int createMRRPOReceiptQuarantineQuery(LinkedHashMap<String, String> inputValueMap){
			String query = null;
			int RECORD_ID = 0;
			int TRANSACTION_ID = 0;
			CallableStatement stproc_stmt; 
			try {

				RECORD_ID = generateRandomNum(10000000);

				String mrrNumber = (inputValueMap.get("VALUE9").contains("#")) ?  getRuntimeTestdata(inputValueMap.get("VALUE9")) : generateTestData("MRRNUMBER", inputValueMap.get("VALUE9"));
				String asnNumber = (inputValueMap.get("VALUE10").contains("#")) ?  getRuntimeTestdata(inputValueMap.get("VALUE10")) : generateTestData("ASNNUMBER", inputValueMap.get("VALUE10"));
					
				
				TRANSACTION_ID = generateRandomNum(10000000);

				addRuntimeTestData(testParameters.getCurrentKeywordColumnName(),String.valueOf(TRANSACTION_ID));
				
				query = "INSERT "
						+"INTO CATSCON_MRR_STG"
						+"("
						+"OPERATING_UNIT,"
						+"TO_ORGANIZATION_ID,"
						+"VENDOR_NUMBER,"
						+"VENDOR_NAME,"
						+"VENDOR_SITE_CODE,"
						+"PO_NUMBER,"
						+"PO_LINE_NUM ,"
						+"PO_SHIPMENT_NUM,"
						+"RECEIPT_NUM,"
						+"SHIPMENT_NUM,"
						+"SHIPPED_DATE,"
						+"TRANSACTION_DATE,"
						+"TRANSACTION_ID,"
						+"SHIPMENT_HEADER_ID,"
						+"SHIPMENT_LINE_ID,"
						+"ITEM_CODE ,"
						+"QUANTITY,"
						+"LOCATION_ID,"
						+"PO_UNIT_PRICE,"
						+"H_ATTRIBUTE10,"
						+"H_ATTRIBUTE11,"
						+"H_ATTRIBUTE14,"
						+"H_ATTRIBUTE6,"
						+"H_ATTRIBUTE7,"
						+"H_ATTRIBUTE8,"
						+"H_ATTRIBUTE9 ,"
						+"L_ATTRIBUTE10,"
						+"L_ATTRIBUTE12,"
						+"L_ATTRIBUTE13,"
						+"DESTINATION_TYPE_CODE,"
						+"MRR_CREATED_BY,"
						+"INTERFACE_NAME,"
						+"RECORD_ID,"
						+"PROCESS_FLAG,"
						+"CREATION_DATE,"
						+"CREATED_BY"
						+")"
						+"VALUES"
						+"("
						+ Integer.parseInt(inputValueMap.get("VALUE1"))+","
						+ Integer.parseInt(inputValueMap.get("VALUE2"))+","
						+"'"+inputValueMap.get("VALUE3")+"',"
						+"'"+inputValueMap.get("VALUE4")+"',"
						+"'"+inputValueMap.get("VALUE5")+"',"
						+"'"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+"',"
						+ Integer.parseInt(inputValueMap.get("VALUE7"))+","
						+ Integer.parseInt(inputValueMap.get("VALUE8"))+","
						+"'"+mrrNumber+"',"
						+"'"+asnNumber+"',"
						+"TO_DATE('"+getRuntimeTestdata(inputValueMap.get("VALUE11"))+"','DD-MON-YYYY HH:MI:SS AM')"+","
						+"TO_DATE('"+getRuntimeTestdata(inputValueMap.get("VALUE12"))+"','DD-MON-YYYY HH:MI:SS AM')"+","
						+TRANSACTION_ID+","
						+generateRandomNum(10000000)+","
						+ Integer.parseInt(inputValueMap.get("VALUE15"))+","
						+"'"+getRuntimeTestdata(inputValueMap.get("VALUE16"))+"',"
						+ Integer.parseInt(inputValueMap.get("VALUE17"))+","
						+ Integer.parseInt(inputValueMap.get("VALUE18"))+","
						+ Integer.parseInt(inputValueMap.get("VALUE19"))+","
						+"'"+inputValueMap.get("VALUE20")+"',"
						+"'"+inputValueMap.get("VALUE21")+"',"
						+"'"+inputValueMap.get("VALUE22")+"',"
						+"'"+inputValueMap.get("VALUE23")+"',"
						+inputValueMap.get("VALUE24")+","
						+inputValueMap.get("VALUE25")+","
						+inputValueMap.get("VALUE26")+","
						+"'"+inputValueMap.get("VALUE27")+"',"
						+"'"+inputValueMap.get("VALUE28")+"',"
						+"'"+inputValueMap.get("VALUE29")+"',"
						+"'"+inputValueMap.get("VALUE30")+"',"
						+inputValueMap.get("VALUE31")+","
						+"'"+inputValueMap.get("VALUE32")+"',"
						+RECORD_ID+","
						+"'"+inputValueMap.get("VALUE34")+"',"
						+inputValueMap.get("VALUE35")+","
						+ Integer.parseInt(inputValueMap.get("VALUE36"))+")";

				//System.out.println(query);

				executeUpdateQuery(query, "MRR - <b>"+mrrNumber+"</b> is created for PO - <b>"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+"</b>");
				connection.commit();
				stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_MRR}");	
				stproc_stmt.executeUpdate();		
				stproc_stmt = connection.prepareCall ("{call CATSCON_P_MRRINTERFACE.SP_INITMRRINTERFACE(?)}");
				stproc_stmt.setString(1, "");
				stproc_stmt.executeUpdate();
				stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_MRR_ERPACK}");	
				stproc_stmt.executeUpdate();

				stproc_stmt.close();		


			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return RECORD_ID;
		}
		
		
		public void generateDate(String dateName) {
			

			String data = null;
			Statement stmt;
			ResultSet rs;
			
			String query = "SELECT TO_CHAR(SYSDATE, 'DD-Mon-YYYY') AS "+dateName+" FROM DUAL";

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					data = rs.getString(dateName);
					if (!data.equals(null)) {
						break;
					}
					
				}
				
							
				if (!data.equals(null)) {
				test.log(LogStatus.PASS, "Generated "+dateName+" Date for MRR - <b>" + data +"</b>");
				addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), data);
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while generating "+dateName+" date for MRR");
				test.log(LogStatus.FAIL, e);
			}
			
			

		
			
		}

		public void poTaxUpdateQuery(LinkedHashMap<String, String> inputValueMap){
			String query = null;		
			try {			
				query = "UPDATE CATSCUST_MRR "
						+"SET TAX_UPDATE = 'Y'"
						+"WHERE MRRID IN (SELECT MRRID FROM CATSCUST_MRR WHERE POCODE='"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+".OU."+inputValueMap.get("VALUE1")+"')";
				executeUpdateQuery(query, "Tax Update for PO - <b>"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+"</b>");
				connection.commit();			

			} catch (SQLException e) {	
				test.log(LogStatus.FAIL, "Tax Update for PO - <b>"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+"</b>");
				e.printStackTrace();			
			}
		}
		
		
		public void verifyMrrTrxIdPOQuarantine(String partcodeAndShipmentNumberAndReceiptType, String trxidExpected) {
			
			String[] arguments = null;			
			String trxidActual = null;
			Statement stmt;
			ResultSet rs;
			
			if(trxidExpected.contains("#")) {
				trxidExpected = 	getRuntimeTestdata(trxidExpected);
			}
			
			if(partcodeAndShipmentNumberAndReceiptType.contains("@")) {
				arguments = partcodeAndShipmentNumberAndReceiptType.split("@");
			}
			
			String query = "SELECT TRANSACTION_ID  FROM CATSCUST_POQUARANTINE a WHERE "
							+ "PARTCODE='"+getRuntimeTestdata(arguments[0])+"' "
							+ "AND SHIPMENTNUMBER='"+getRuntimeTestdata(arguments[1])+"' "
							+ "AND RECEIPTTYPE='"+arguments[2]+"'";
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					trxidActual = rs.getString("TRANSACTION_ID");
					if (!trxidActual.equals(null)) {
						break;
					}
					
				}
				
				if(!trxidActual.equals(null)) {
					if(trxidExpected.equalsIgnoreCase(trxidActual)) {
						test.log(LogStatus.PASS, "<b>"+arguments[2]+":</b>TRANSACTION_ID in CATSCUST_POQUARANTINE and CATSCUST_MRR  matched for line with Partcode ("+getRuntimeTestdata(arguments[0])+").</br>"
													+ "<b>Expected </b> - "+trxidExpected+ "</br>"
													+ "<b>Actual </b> - "+trxidActual+ "</br>"
													);
						}else {
						test.log(LogStatus.FAIL, "<b>"+arguments[2]+":</b>TRANSACTION_ID in CATSCUST_POQUARANTINE and CATSCUST_MRR  not matched for line with Partcode ("+getRuntimeTestdata(arguments[0])+").</br>"
									+ "<b>Expected </b> - "+trxidExpected+ "</br>"
									+ "<b>Actual </b> - "+trxidActual+ "</br>"
									);
						}
					
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying MRR TRXID in CATSCUST_POQUARANTINE");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}

		
		public void verifyLotNumberPOQuarantine(String partcodeAndShipmentNumberAndReceiptType, String lotNumberExpected) {
			
			String[] arguments = null;			
			String lotNumberActual = null;
			Statement stmt;
			ResultSet rs;
			
			if(lotNumberExpected.contains("#")) {
				lotNumberExpected = 	getRuntimeTestdata(lotNumberExpected);
			}
			
			if(partcodeAndShipmentNumberAndReceiptType.contains("@")) {
				arguments = partcodeAndShipmentNumberAndReceiptType.split("@");
			}
			
			String query = "SELECT LOTNUMBER  FROM CATSCUST_POQUARANTINE a WHERE "
							+ "PARTCODE='"+getRuntimeTestdata(arguments[0])+"' "
							+ "AND SHIPMENTNUMBER='"+getRuntimeTestdata(arguments[1])+"' "
							+ "AND RECEIPTTYPE='"+arguments[2]+"'";
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					lotNumberActual = rs.getString("LOTNUMBER");
					if (!lotNumberActual.equals(null)) {
						break;
					}
					
				}
				
				if(!lotNumberActual.equals(null)) {
					if(lotNumberExpected.equalsIgnoreCase(lotNumberActual)) {
						test.log(LogStatus.PASS, "<b>"+arguments[2]+":</b>LOTNUMBER in CATSCUST_POQUARANTINE and CATSCUST_MRR  matched for line with Partcode ("+getRuntimeTestdata(arguments[0])+").</br>"
													+ "<b>Expected </b> - "+lotNumberExpected+ "</br>"
													+ "<b>Actual </b> - "+lotNumberActual+ "</br>"
													);
						}else {
						test.log(LogStatus.FAIL, "<b>"+arguments[2]+":</b>LOTNUMBER in CATSCUST_POQUARANTINE and CATSCUST_MRR  not matched for line with Partcode ("+getRuntimeTestdata(arguments[0])+").</br>"
									+ "<b>Expected </b> - "+lotNumberExpected+ "</br>"
									+ "<b>Actual </b> - "+lotNumberActual+ "</br>"
									);
						}
					
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying MRR TRXID in CATSCUST_POQUARANTINE");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		/*public void createNewReasonCategory(String segment1){
			String query = null;	
			String isSegment1Exists = null;
			int lastCategoryID = getLastTransactionId("SELECT MAX(CATEGORYID) AS CATEGORYID FROM CATS_CATEGORY","CATEGORYID" );
			
			try {
				
				//check if category already exists
				
				isSegment1Exists = selectQuerySingleValue("SELECT SEGMENT1 AS SEGMENT1 FROM CATS_CATEGORY WHERE SEGMENT1='"+segment1+"'", "SEGMENT1");
				
				if(isSegment1Exists.equalsIgnoreCase(segment1)){
					test.log(LogStatus.INFO, "CATEGORY with SEGMENT1 = '"+segment1+"' already exists.");
				}else {
					
					query = "INSERT "
							+"INTO CATS_CATEGORY("
							+"CATEGORYID,"
							+"SEGMENT1,"
							+"SEGMENT2,"
							+"TABLENAME,"
							+"USAGE,"
							+"SEGMENT3,"
							+"ACTIVE,"
							+"ADDCONTACTID,"
							+"ADDDTTM,"
							+"MODIFIEDCONTACTID,"
							+"MODIFIEDDTTM,"
							+"DISPLAY)"
							+"VALUES("
							+(lastCategoryID+1)+","
							+"'"+segment1+"',"
							+"NULL,"
							+"'CATS_REASON',"
							+"NULL,"
							+"NULL,"
							+"'Y',"
							+1+","
							+"SYSDATE,"
							+1+","
							+"SYSDATE,"
							+"'"+segment1+"')"
							;					
					executeUpdateQuery(query, "Category with SEGMENT1 = '"+segment1+"' is inserted into CATS_CATEGORY");
					connection.commit();
					
					addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), String.valueOf(lastCategoryID));
					
				}
				
				
								
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		
		
		public void createNewReason(LinkedHashMap<String, String> inputValueMap){
			String query = null;	
			
			try {
				
				query = "";
							 
				//System.out.println(query);
				executeUpdateQuery(query, "");
				connection.commit();				
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		*/
		
		
		public void stockReviewSerialized(String assetCode, String subInventory) {
			
			String query1 = null;
			
			if(assetCode.contains("#")) {
				assetCode = getRuntimeTestdata(assetCode);
			}
			
			try {
			
			String CUSTOMTRANSACTIONID = selectQuerySingleValue("SELECT ORIGINATORTRXID FROM CATS_ASSETTRANSACTION WHERE ASSETCODE='"+assetCode+"' AND ORIGINATORTYPETRX='STOCK'", "ORIGINATORTRXID");
			
			LinkedHashMap<String, String> customStockTrxData = selectQueryMultipleValues("SELECT CUSTOMTRANSACTIONID,VALUE4, VALUE10,VALUE17,VALUE24,VALUE25,VALUE50,NOTES "
					+ "FROM CATS_CUSTOMTRANSACTION WHERE CUSTOMTRANSACTIONID="+CUSTOMTRANSACTIONID, "CUSTOMTRANSACTIONID#VALUE4#VALUE10#VALUE17#VALUE24#VALUE25#VALUE50#NOTES");
			String BUSINESSUNITID = selectQuerySingleValue("SELECT BUSINESSUNITID FROM CATS_LOCATIONBU WHERE LOCATIONID IN (SELECT LOCATIONID FROM CATS_LOCATION WHERE NAME='"+customStockTrxData.get("VALUE24_1")+"')", "BUSINESSUNITID");
			String MANUFACTURERID = selectQuerySingleValue("SELECT MANUFACTURERID FROM CATSCUST_PARTMANUFACTURER WHERE MFGPARTNUMBER='"+customStockTrxData.get("VALUE10_1")+"'", "MANUFACTURERID");
			String ASSETID = selectQuerySingleValue("SELECT ASSETID FROM CATS_ASSET WHERE SERIALNUMBER='"+assetCode+"'", "ASSETID");
			String ASSETACTIVE = selectQuerySingleValue("SELECT ACTIVE FROM CATS_ASSET WHERE SERIALNUMBER='"+assetCode+"'", "ACTIVE");			
		
			int STOCKREVIEWID = getLastTransactionId("SELECT STOCKREVIEWID FROM CATSCUST_V_ASSET_UDFDATA WHERE ASSETID IN (SELECT ASSETID FROM CATS_ASSET WHERE ASSETCODE='"+assetCode+"')","STOCKREVIEWID" );
			
			query1 = "INSERT "
					+"INTO CATS_CUSTOMTRANSACTION("
					+"CUSTOMTRANSACTIONID,"
					+"ORIGINATOR,"
					+"ORIGINATORTYPETRX,"
					+"TYPETRX,"					
					+"VALIDATIONOVERRIDE,"
					+"QTYOVERRIDE,"
					+"VALUE1,"
					+"VALUE4,"
					+"VALUE5,"
					+"VALUE6,"
					+"VALUE10,"
					+"VALUE17,"
					+"VALUE24,"
					+"VALUE27,"
					+"VALUE29,"
					+"VALUE31,"
					+"VALUE50,"
					+"NOTES,"
					+"NUMBER4,"
					+"PROCESSED,"
					+"ADDCONTACTCODE,"
					+"ADDDTTM,"
					+"NUMBER8,"
					+"NUMBER10,"
					+"VALUE90,"
					+"VALUE91,"
					+"VALUE92,"
					+"VALUE93,"
					+"ASSETID"
					+ ")"
					+"VALUES("
					+"(SELECT DECODE(max(CUSTOMTRANSACTIONID), NULL,1, max(CUSTOMTRANSACTIONID)+1) CUSTOMTRANSACTIONID FROM CATS_CUSTOMTRANSACTION),"
					+"'CATS_MANAGED_MANAGER',"
					+"'CUSTOM',"
					+"'CUSTOM',"					
					+"NULL,"
					+"NULL,"
					+"'STOCK_REVIEW',"
					+"'"+customStockTrxData.get("VALUE4_1")+"',"
					+"'"+assetCode+"',"
					+"'"+ASSETACTIVE+"',"
					+"'"+customStockTrxData.get("VALUE10_1")+"',"
					+"'"+customStockTrxData.get("VALUE17_1")+"',"
					+"'"+customStockTrxData.get("VALUE24_1")+"',"
					+"'"+subInventory+"',"
					+"'INVENTORY',"
					+"'"+customStockTrxData.get("VALUE25_1")+"',"
					+"'"+customStockTrxData.get("VALUE50_1")+"',"
					+"'"+customStockTrxData.get("NOTES_1")+"',"
					+(BUSINESSUNITID)+","
					+"NULL,"
					+"'CATSADM',"
					+"SYSDATE,"
					+(STOCKREVIEWID)+","
					+(MANUFACTURERID)+","
					+"'"+assetCode+"',"
					+"'APPROVED',"
					+"'INVENTORY',"
					+"'STOCK',"
					+(ASSETID)
					+ ")"
					;								
			executeUpdateQuery(query1, "Stock review is done for Assetcode - "+assetCode);
			connection.commit();
			
			}catch (SQLException e) {			
				e.printStackTrace();
			}
			
		}
		
		public void stockReviewNonSerialized() {
			
		}
		
		
		public void getLotNumberSerializedStockTrx(String assetcode) {
			
			Statement stmt;
			ResultSet rs;
			String lotnumber = null;
			
			if(assetcode.contains("#")) {
				assetcode = 	getRuntimeTestdata(assetcode);
			}
			
				
			String query = "SELECT LOTNUMBER FROM CATS_ASSETTRANSACTION WHERE ASSETCODE='"+assetcode+"' AND ORIGINATORTYPETRX='STOCK'";
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					lotnumber = rs.getString("LOTNUMBER");
					if (lotnumber!=null) {
						break;
					}
					
				}
				
			if (lotnumber!=null) {
			test.log(LogStatus.PASS, "LOTNUMBER for Stock Transaction with ASSETCODE - <b>"+assetcode+"</b> is <b>"+lotnumber+"</b>");
			if(lotnumber.contains("S-")) {
				test.log(LogStatus.PASS, "LOTNUMBER for Stock Transaction with ASSETCODE - <b>"+assetcode+"</b> is defaulted with format '<b>S-</b>' ");	
			}
			}
			
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while getting LOTNUMBER for Stock Transaction from CATS_ASSETTRANSACTION table with ASSETCODE - "+assetcode);
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		
		public void verifyPartTotalCost(String partcodeAndLotnumber, String expectedTotalCost){
			String[] arguments = null;			
			String actualTotalCost = null;
			Statement stmt;
			ResultSet rs;
			
			if(partcodeAndLotnumber.contains("@")) {
				arguments = partcodeAndLotnumber.split("@");
			}
			
			String query = "SELECT TOTALVALUE FROM CATS_PARTDETAIL WHERE  LOTNUMBER='"+getRuntimeTestdata(arguments[1])+"' AND PARTID IN " + 
						   "(SELECT PARTID FROM CATS_PART WHERE PARTCODE='"+getRuntimeTestdata(arguments[0])+"')";
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					actualTotalCost = rs.getString("TOTALVALUE");
					if (actualTotalCost!=null) {
						break;
					}
					
				}
				
				if (actualTotalCost!=null) {
					if(expectedTotalCost.equalsIgnoreCase(actualTotalCost)) {
						test.log(LogStatus.PASS, "<b>TOTALVALUE</b> matched for Part Detail record with Partcode (<b>"+getRuntimeTestdata(arguments[0])+"</b>) and Lot Number (<b>"+getRuntimeTestdata(arguments[1])+"</b>).</br>"
													+ "<b>Expected </b> - "+expectedTotalCost+ "</br>"
													+ "<b>Actual </b> - "+actualTotalCost+ "</br>"
													);
						}else {
						test.log(LogStatus.FAIL, "<b>TOTALVALUE<b></b> not matched for Part Detail record line with Partcode (<b>"+getRuntimeTestdata(arguments[0])+"</b>) and Lot Number (<b>"+getRuntimeTestdata(arguments[1])+"</b>).</br>"
									+ "<b>Expected </b> - "+expectedTotalCost+ "</br>"
									+ "<b>Actual </b> - "+actualTotalCost+ "</br>"
									);
						}
					
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying Part TOTALVALUE");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		public void verifyAssetTotalCost(String assetcode, String expectedTotalCost){
			
			String actualTotalCost = null;
			Statement stmt;
			ResultSet rs;		
			
			if(assetcode.contains("#")) {				
				assetcode = getRuntimeTestdata(assetcode);
			}
			
			String query = "SELECT COST FROM CATS_ASSET WHERE ASSETCODE='"+assetcode+"'";
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					rs.getObject(1);
					actualTotalCost = rs.getString("COST");
					if (actualTotalCost!=null) {
						break;
					}
					
				}
				
				if (actualTotalCost!=null) {
					if(expectedTotalCost.equalsIgnoreCase(actualTotalCost)) {
						test.log(LogStatus.PASS, "<b>COST</b> matched for ASSET (<b>"+assetcode+"</b>).</br>"
													+ "<b>Expected </b> - "+expectedTotalCost+ "</br>"
													+ "<b>Actual </b> - "+actualTotalCost+ "</br>"
													);
						}else {
						test.log(LogStatus.FAIL, "<b>COST</b> not matched for ASSET (<b>"+assetcode+"</b>).</br>"
									+ "<b>Expected </b> - "+expectedTotalCost+ "</br>"
									+ "<b>Actual </b> - "+actualTotalCost+ "</br>"
									);
						}
					
				}
				
			} catch (SQLException e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying Asset's Total cost");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		
		public void verifyChildAssetWarranty(String parentAssetCode, String childAssetCode){			
		

			LinkedHashMap<String, String> parentWarrantyDetailsMap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> childWarrantyDetailsMap = new LinkedHashMap<String, String>();
			
			if(parentAssetCode.contains("#")) {				
				parentAssetCode = getRuntimeTestdata(parentAssetCode);
			}
			
			if(childAssetCode.contains("#")) {				
				childAssetCode = getRuntimeTestdata(childAssetCode);
			}
			
			String query = "SELECT AW.ASSETWARRANTYID,\r\n" + 
					"							AW.ASSETID,\r\n" + 
					"							AW.VENDOR,\r\n" + 
					"							AU.VENDORLOCATION,\r\n" + 
					"							M.NAME MANUFACTURER,\r\n" + 
					"							AW.ACTIVE,\r\n" + 
					"							AW.TYPE,\r\n" + 
					"							AW.WARRANTYCODE,\r\n" + 
					"							AW.REFERENCENUMBER,\r\n" + 
					"							AW.LEADTIME,\r\n" + 
					"							AW.DURATION,\r\n" + 
					"							LS.DESCRIPTION STARTSTATUS,\r\n" + 
					"							AW.STARTDATE\r\n" + 
					"					FROM 	CATS_ASSET A,\r\n" + 
					"							CATS_V_ASSETWARRANTY AW,\r\n" + 
					"							CATSCUST_V_ASSET_UDFDATA AU,\r\n" + 
					"							CATS_PART P,\r\n" + 
					"							CATS_MANUFACTURER M,\r\n" + 
					"							CATS_LOCATIONSTATUS LS\r\n" + 
					"					WHERE AW.ASSETID = A.ASSETID\r\n" + 
					"					AND   A.ASSETID = AU.ASSETID(+)\r\n" + 
					"					AND   A.PARTID = P.PARTID\r\n" + 
					"					AND   AU.MANUFACTURERID = M.MANUFACTURERID(+)\r\n" + 
					"					AND   AW.STARTSTATUSID = LS.LOCATIONSTATUSID(+)\r\n" + 
					"					AND   A.ASSETID IN (SELECT ASSETID FROM CATS_ASSET WHERE ASSETCODE='%s')\r\n" + 
					"				ORDER BY AW.ASSETWARRANTYID";
			
			
			
			String getParentWarrantyDetailsQuery = String.format(query,parentAssetCode );
			String getChildWarrantyDetailsQuery= String.format(query, childAssetCode);	

			
			try {
				
				parentWarrantyDetailsMap = selectQueryMultipleValues(getParentWarrantyDetailsQuery, "VENDOR#VENDORLOCATION#ACTIVE#TYPE#WARRANTYCODE#REFERENCENUMBER#LEADTIME#DURATION#STARTSTATUS#STARTDATE");
				childWarrantyDetailsMap = selectQueryMultipleValues(getChildWarrantyDetailsQuery, "VENDOR#VENDORLOCATION#ACTIVE#TYPE#WARRANTYCODE#REFERENCENUMBER#LEADTIME#DURATION#STARTSTATUS#STARTDATE");
				
				test.log(LogStatus.PASS, "<b> Verifying Child Asset("+childAssetCode+") Warranty Details with Parent Asset ("+parentAssetCode+")...</b>");
				for (Entry<String, String> entryMap : parentWarrantyDetailsMap.entrySet()) {
					
					String parentKey = entryMap.getKey();
					String parentValue = entryMap.getValue();
					
					String childValue = childWarrantyDetailsMap.get(parentKey);
					
					if(childValue.equals(parentValue)) {
						test.log(LogStatus.PASS, "<b>"+parentKey+"</b></br>"
								+ "<b>Expected </b> - "+parentValue+ "</br>"
								+ "<b>Actual </b> - "+childValue+ "</br>"
								);	
					}else {
						test.log(LogStatus.FAIL, "<b>"+parentKey+"</b></br>"
								+ "<b>Expected </b> - "+parentValue+ "</br>"
								+ "<b>Actual </b> - "+childValue+ "</br>"
								);
					}
					
				}
				
				
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "Exception occured while verifying Child Asset's Warranty details.");
				test.log(LogStatus.FAIL, e);
			}
			
			
		}
		
		

}
