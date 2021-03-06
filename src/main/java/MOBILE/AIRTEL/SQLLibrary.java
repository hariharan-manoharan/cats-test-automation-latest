package main.java.MOBILE.AIRTEL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import main.java.framework.executionSetup.TestParameters;
import main.java.framework.testDataAccess.DataTable;
import main.java.framework.utils.Utility;


public class SQLLibrary extends Utility {
	
	public Lock lock;
	public Connection connection;

	
	@SuppressWarnings("rawtypes")
	public SQLLibrary(ExtentTest test, AndroidDriver driver, DataTable dataTable, TestParameters testParameters, Lock lock, Connection connection) {
		super(test, driver, dataTable, testParameters, lock, connection);
		this.lock = lock;
		this.connection = connection;
	}
	
	@SuppressWarnings("rawtypes")
	public SQLLibrary(ExtentTest test, AndroidDriver driver, DataTable dataTable, TestParameters testParameters, Lock lock, Connection connection, Properties runtimeDataProperties) {
		super(test, driver, dataTable, testParameters, lock, connection, runtimeDataProperties);
		this.lock = lock;
		this.connection = connection;		
	}

	
	//SQL FUNCTIONS
	
	
	
	public void createNewPart(){
		String validateItem = "SELECT * FROM CATSCON_PART_STG WHERE ITEM='%s' AND RECORD_ID=%d";
		LinkedHashMap<String, String> dataMap = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase());			
		int recordId = createNewPart(dataMap);
		validateInboundTransaction("Item", "PROCESS_FLAG", "ERROR_MESSAGE", validateItem, getRuntimeTestdata(testParameters.getCurrentTestCase()+"#ITEMCODE"),recordId);	
	}
	
	public void addManufacturer(){
		String validateMFG = "SELECT * FROM CATSCON_MFG_STG WHERE MANUFACTURER_NAME='%s' AND RECORD_ID=%d";
		LinkedHashMap<String, String> dataMap = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase());		
		int recordId = addMfgForItem(dataMap);
		validateInboundTransaction("MFG", "PROCESS_FLAG", "ERROR_MESSAGE", validateMFG, getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MFG"),recordId);
	}
	

	
	public void createBillOfMaterial(){
		LinkedHashMap<String, String> dataMap = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase());
		createBillOfMaterialQuery(dataMap);
	}


	public void createBulkTransferRequest(){
		

		String validateBulkTransferRequest = "SELECT * FROM CATSCON_TRANSFERREQ_STG WHERE REFERENCENUMBER='%s' AND STAGEID=%d";	

		LinkedHashMap<String, String> dataMap = dataTable.getRowData("Bulk_Transfer_Request", testParameters.getCurrentTestCase());		
		int stageId = createBulkTransferRequest(dataMap);
		boolean successFlag = validateInboundTransaction("Bulk Transfer Request", "PROCESSED", "ERRORMESSAGE", validateBulkTransferRequest, dataMap.get("REFERENCENUMBER"),stageId);	
		
		if(successFlag){
			String RequestNumber = selectQuerySingleValue(String.format(validateBulkTransferRequest, dataMap.get("REFERENCENUMBER"), stageId), "GENERATEDREQNUM");		
			String query = "SELECT * FROM CATS_TRANSFER WHERE REFERENCENUMBER ="+"'"+RequestNumber+"'";
			String TRANSFERNO = selectQuerySingleValue(query, "TRANSFERNUMBER");
			addRuntimeTestData("REQUESTNUMBER", RequestNumber);
			addRuntimeTestData("TRANSFERNUMBER", TRANSFERNO);
		}
		
	}	
	
	
	public void deliveryConfirmation(String iteration) {
		
		LinkedHashMap<String, String> dataMap = null;

		try {

			lock.lock();
			String validateDC = "SELECT * FROM CATSCON_POREC_STG WHERE ITEM_CODE='%s' AND RECORD_ID=%d";
			
			if(iteration!=null) {
				dataMap = dataTable.getRowData("Data_Staging",
					testParameters.getCurrentTestCase() + "_DC"+iteration);			
			}else {
				dataMap = dataTable.getRowData("Data_Staging",
						testParameters.getCurrentTestCase() + "_DC");
				
			}
			
			int recordId = deliveryConfirmationQuery(dataMap);
			validateInboundTransaction("Delivery Confirmation :", "PROCESS_FLAG", "ERROR_MESSAGE", validateDC,
					getRuntimeTestdata(dataMap.get("VALUE7")), recordId);
			
			
		} finally {
			lock.unlock();
		}
	}

	public void deliveryConfirmation1(String iteration) {
		
		LinkedHashMap<String, String> dataMap = null;

		try {

			lock.lock();
			String validateDC = "SELECT * FROM CATSCON_POREC_STG WHERE ITEM_CODE='%s' AND RECORD_ID=%d";
			
			if(iteration!=null) {
				dataMap = dataTable.getRowData("Data_Staging",
					testParameters.getCurrentTestCase() + "_DC"+iteration);
				int recordId = deliveryConfirmationQuery1(dataMap,Integer.parseInt(iteration));
				validateInboundTransaction("Delivery Confirmation :", "PROCESS_FLAG", "ERROR_MESSAGE", validateDC,
						getRuntimeTestdata(dataMap.get("VALUE7")), recordId);
			}else {
				dataMap = dataTable.getRowData("Data_Staging",
						testParameters.getCurrentTestCase() + "_DC");
				int recordId = deliveryConfirmationQuery1(dataMap, 0);
				validateInboundTransaction("Delivery Confirmation :", "PROCESS_FLAG", "ERROR_MESSAGE", validateDC,
						getRuntimeTestdata(dataMap.get("VALUE7")), recordId);
			}
			
			
		} finally {
			lock.unlock();
		}
	}
	

	public void createPurchaseOrder(String iteration) {
		LinkedHashMap<String, String> dataMap = null;
		try {

			lock.lock();
			String validatePO = "SELECT * FROM CATSCON_PO_STG WHERE PHA_PO_NUMBER='%s' AND RECORD_ID=%d";
			
			if(iteration!=null) {
			 dataMap = dataTable.getRowData("Data_Staging",
					testParameters.getCurrentTestCase() + "_PO"+iteration);
			}else {
				dataMap = dataTable.getRowData("Data_Staging",
						testParameters.getCurrentTestCase() + "_PO");
			}
			int recordId = createPurchaseOrderQuery(dataMap);
			validateInboundTransaction("PO", "PROCESS_FLAG", "ERROR_MESSAGE", validatePO,
					getRuntimeTestdata(testParameters.getCurrentTestCase() + "#PONUMBER"), recordId);

		} finally {
			lock.unlock();
		}
	}
	
	


	public void createMaterialReceiveReceipt(String iteration) {
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
			
			int recordId = createMaterialReceiveReceiptQuery(dataMap);
			validateInboundTransaction("MRR", "PROCESS_FLAG", "ERROR_MESSAGE", validateMRR,
					getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MRRNUMBER"), recordId);
		
			poTaxUpdateQuery(dataMap);

		} finally {
			lock.unlock();
		}
	}
	public void createMRRwithSinglePO(String iteration) {
		LinkedHashMap<String, String> dataMap = null;
		try {

			lock.lock();
			String validateMRR = "SELECT * FROM CATSCON_MRR_STG WHERE RECEIPT_NUM='%s' AND RECORD_ID=%d";

			if(iteration!=null) {
			 dataMap = dataTable.getRowData("Data_Staging",
					testParameters.getCurrentTestCase() + "_MRR"+iteration);
			 int recordId = createMRRwithsinglePO(dataMap,Integer.parseInt(iteration));
				validateInboundTransaction("MRR", "PROCESS_FLAG", "ERROR_MESSAGE", validateMRR,
						getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MRRNUMBER"+iteration), recordId);
			}else {
				dataMap = dataTable.getRowData("Data_Staging",
						testParameters.getCurrentTestCase() + "_MRR");
				int recordId = createMRRwithsinglePO(dataMap,0);
				validateInboundTransaction("MRR", "PROCESS_FLAG", "ERROR_MESSAGE", validateMRR,
						getRuntimeTestdata(testParameters.getCurrentTestCase() + "#MRRNUMBER"), recordId);
			}
		
			poTaxUpdateQuery(dataMap);

		} finally {
			lock.unlock();
		}
	}

	public void createBillOfMaterialQuery(LinkedHashMap<String, String> inputValueMap){
		String query = null;	
		CallableStatement stproc_stmt; 
		try {
			
			query = "INSERT "
						+"INTO CATS.CATSCON_BOM_STG"
						  +"("
						    +"ORGANIZATION_CODE,"
						    +"ORGANIZATION_ID,"
						    +"BILL_ITEM_NAME,"
						    +"ITEM_SEQUENCE_NUMBER,"
						    +"COMPONENT_ITEM_NAME,"
						    +"QUANTITY_PER_ASSEMBLY,"
						    +"START_EFFECTIVE_DATE,"
						    +"UNIQUE_ID,"
						    +"RECORD_ID,"
						    +"CREATION_DATE,"
						    +"PROCESS_FLAG,"
						    + "YIELD"
						  +")"
						  +"VALUES"
						  +"("
						  	+"'"+inputValueMap.get("VALUE1")+"',"
						  	+ Integer.parseInt(inputValueMap.get("VALUE2"))+","
						    +"'"+getRuntimeTestdata(inputValueMap.get("VALUE3"))+"',"
						    + Integer.parseInt(inputValueMap.get("VALUE4"))+","
						    +"'"+getRuntimeTestdata(inputValueMap.get("VALUE5"))+"',"
						    + Integer.parseInt(inputValueMap.get("VALUE6"))+","
						    +inputValueMap.get("VALUE7")+","
						    +"'"+inputValueMap.get("VALUE8")+"',"
						    +generateRandomNum(10000000)+","
						    +inputValueMap.get("VALUE10")+","
						    +"'"+inputValueMap.get("VALUE11")+"',"
						    +"'"+inputValueMap.get("VALUE12")+"')";
						 
			//System.out.println(query);
			executeUpdateQuery(query, "BOM is created for Item code - <b>"+getRuntimeTestdata(inputValueMap.get("VALUE3"))+"</b> where, Item Sequence # - <b>"+inputValueMap.get("VALUE4")+
																										"</b>, Component Item Code - <b>"+getRuntimeTestdata(inputValueMap.get("VALUE5"))+
																										"</b>, Qty Per Assembly - <b>"+ inputValueMap.get("VALUE6")+"</b>");
			connection.commit();
			stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_BOM}");	
			stproc_stmt.executeUpdate();		
			stproc_stmt = connection.prepareCall ("{call CATSCON_P_PARTINTERFACE.SP_INITPARTINTERFACE(?)}");
			stproc_stmt.setString(1, "");
			stproc_stmt.executeUpdate();
			stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_BOM_ERPACK}");	
			stproc_stmt.executeUpdate();
			
			stproc_stmt.close();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	
	public int createPurchaseOrderQuery(LinkedHashMap<String, String> inputValueMap){
		String query = null;		
		int RECORD_ID = 0;
		CallableStatement stproc_stmt;  
		try {

			RECORD_ID = generateRandomNum(10000000);

			String purchaseOrder = (inputValueMap.get("VALUE2").contains("#")) ?  getRuntimeTestdata(inputValueMap.get("VALUE2")) : generateTestData("PONUMBER", inputValueMap.get("VALUE2"));
			String itemcode = getRuntimeTestdata(inputValueMap.get("VALUE18"));

			addRuntimeTestData("PONUMBER", purchaseOrder);
			
			query="INSERT "
					+"INTO CATS.CATSCON_PO_STG"
					+"("
					+"PHA_OPERATING_UNIT_ID,"
					+"PHA_PO_NUMBER,"
					+"PHA_REVISION_NUM,"
					+"PHA_VENDOR_NUMBER,"
					+"PHA_VENDOR_NAME,"
					+"PHA_VENDOR_SITE_CODE,"
					+"PHA_SHIP_TO_LOCATION_ID,"
					+"PHA_COMMENTS,"
					+"PHA_AUTHORIZATION_STATUS,"
					+"PHA_CANCEL_FLAG,"
					+"PHA_CLOSED_CODE,"
					+"PHA_CURRENCY_CODE,"
					+"PHA_CREATION_DATE,"
					+"PHA_APPROVED_DATE,"
					+"PHA_LAST_UPDATE_DATE,"
					+"PHA_CREATED_BY,"
					+"PLA_LINE_NUM,"
					+"PLA_ITEM_CODE,"
					+"PLA_ITEM_DESCRIPTION,"
					+"PLA_ITEM_CATEGORY,"
					+"PLA_UNIT_MEASURE_CODE,"
					+"PLA_UNIT_PRICE,"
					+"PLA_WARRANTY_START_DATE,"
					+"PLA_WARRANTY_PERIOD,"
					+"PLA_CANCEL_FLAG,"
					+"PLA_CLOSED_CODE,"
					+"PLA_CREATION_DATE,"
					+"PLA_LAST_UPDATE_DATE,"
					+"PLLA_SHIPMENT_NUM,"
					+"PLLA_SHIP_TO_ORGANIZATION_ID,"
					+"PLLA_SHIP_TO_LOCATION_ID,"
					+"PLLA_QUANTITY_ORDERED,"
					+"PLLA_NEED_BY_DATE,"
					+"PLLA_QUANTITY_RECEIVED,"
					+"PLLA_QUANTITY_CANCELLED,"
					+"PLLA_CLOSED_CODE,"
					+"PDA_PO_DISTRIBUTION_ID,"
					+"PDA_DISTRIBUTION_NUM,"
					+"PDA_DESTINATION_TYPE_CODE,"
					+"PDA_CHARGE_ACCOUNT,"
					+"PDA_EX_RATE_DATE,"
					+"RECORD_ID,"
					+"CREATION_DATE,"
					+"PROCESS_FLAG"
					+")"
					+"VALUES"
					+"("
					+"'"+inputValueMap.get("VALUE1")+"',"
					+"'"+purchaseOrder+"',"
					+"'"+inputValueMap.get("VALUE3")+"',"
					+"'"+inputValueMap.get("VALUE4")+"',"
					+"'"+inputValueMap.get("VALUE5")+"',"
					+"'"+inputValueMap.get("VALUE6")+"',"
					+"'"+inputValueMap.get("VALUE7")+"',"
					+"'"+inputValueMap.get("VALUE8")+"',"
					+"'"+inputValueMap.get("VALUE9")+"',"
					+"'"+inputValueMap.get("VALUE10")+"',"
					+"'"+inputValueMap.get("VALUE11")+"',"
					+"'"+inputValueMap.get("VALUE12")+"',"
					+inputValueMap.get("VALUE13")+","
					+inputValueMap.get("VALUE14")+","
					+inputValueMap.get("VALUE15")+","
					+"'"+inputValueMap.get("VALUE16")+"',"
					+"'"+inputValueMap.get("VALUE17")+"',"
					+"'"+itemcode+"',"
					+"'"+inputValueMap.get("VALUE19")+"',"
					+"'"+inputValueMap.get("VALUE20")+"',"
					+"'"+inputValueMap.get("VALUE21")+"',"
					+"'"+inputValueMap.get("VALUE22")+"',"
					+"'"+inputValueMap.get("VALUE23")+"',"
					+"'"+inputValueMap.get("VALUE24")+"',"
					+"'"+inputValueMap.get("VALUE25")+"',"
					+"'"+inputValueMap.get("VALUE26")+"',"
					+inputValueMap.get("VALUE27")+","
					+inputValueMap.get("VALUE28")+","
					+"'"+inputValueMap.get("VALUE29")+"',"
					+"'"+inputValueMap.get("VALUE30")+"',"
					+"'"+inputValueMap.get("VALUE31")+"',"
					+"'"+inputValueMap.get("VALUE32")+"',"
					+inputValueMap.get("VALUE33")+","
					+"'"+inputValueMap.get("VALUE34")+"',"
					+"'"+inputValueMap.get("VALUE35")+"',"
					+"'"+inputValueMap.get("VALUE36")+"',"
					+"'"+inputValueMap.get("VALUE37")+"',"
					+"'"+inputValueMap.get("VALUE38")+"',"
					+"'"+inputValueMap.get("VALUE39")+"',"
					+"'"+inputValueMap.get("VALUE40")+"',"
					+inputValueMap.get("VALUE41")+","
					+RECORD_ID+","
					+inputValueMap.get("VALUE43")+","
					+"'"+inputValueMap.get("VALUE44")+"')";
			
			//test.log(LogStatus.INFO, "<textarea rows=10 cols=50 class=scrollabletextbox name=note><code>"+query+"<textarea></code>");
			//System.out.println(query);
			executeUpdateQuery(query, "PO - <b>"+purchaseOrder+"</b> for Item <b>"+itemcode+"</b> is inserted in to CATSCON_PO_STG table");
			connection.commit();
			stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_PO}");	
			stproc_stmt.executeUpdate();		
			stproc_stmt = connection.prepareCall ("{call CATSCON_P_POINTERFACE.SP_INITPOINTERFACE(?)}");
			stproc_stmt.setString(1, "");
			stproc_stmt.executeUpdate();
			stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_PO_ERPACK}");	
			stproc_stmt.executeUpdate();

			stproc_stmt.close();

		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return RECORD_ID;
	}

	public int createMaterialReceiveReceiptQuery(LinkedHashMap<String, String> inputValueMap){
		String query = null;
		int RECORD_ID = 0;
		CallableStatement stproc_stmt; 
		try {

			RECORD_ID = generateRandomNum(10000000);

			String mrrNumber = (inputValueMap.get("VALUE9").contains("#")) ?  getRuntimeTestdata(inputValueMap.get("VALUE9")) : generateTestData("MRRNUMBER", inputValueMap.get("VALUE9"));

			addRuntimeTestData("MRRNUMBER", mrrNumber);	
			
			int SHIPMENT_LINE_ID = generateRandomNum(10000000);
			
			addRuntimeTestData("SHIPMENT_LINE_ID_"+inputValueMap.get("VALUE7"), inputValueMap.get("VALUE7")+"."+SHIPMENT_LINE_ID);	
			
		
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
					+"'"+inputValueMap.get("VALUE10")+"',"
					+inputValueMap.get("VALUE11")+","
					+inputValueMap.get("VALUE12")+","
					+generateRandomNum(10000000)+","
					+generateRandomNum(10000000)+","
					+SHIPMENT_LINE_ID+","
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


	public int createMRRwithsinglePO(LinkedHashMap<String, String> inputValueMap, int iteration){
		String query = null;
		int RECORD_ID = 0;
		CallableStatement stproc_stmt; 
		try {

			RECORD_ID = generateRandomNum(10000000);

			String mrrNumber = (inputValueMap.get("VALUE9").contains("#")) ?  getRuntimeTestdata(inputValueMap.get("VALUE9")) : generateTestData("MRRNUMBER", inputValueMap.get("VALUE9"));
			
			if(iteration!=0) {
			addRuntimeTestData("MRRNUMBER"+iteration, mrrNumber);
			}else {
			addRuntimeTestData("MRRNUMBER", mrrNumber);	
			}

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
					+"'"+inputValueMap.get("VALUE10")+"',"
					+inputValueMap.get("VALUE11")+","
					+inputValueMap.get("VALUE12")+","
					+generateRandomNum(10000000)+","
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



	@SuppressWarnings("resource")
	public int deliveryConfirmationQuery(LinkedHashMap<String, String> inputValueMap) {
		
		String query = null;
		String SERIALIZED = null;
		String ASSETCODE = null;
		String LOTNUMBER;
		ResultSet rs;
		Statement stmt;
		CallableStatement stproc_stmt;

 
		int RECORD_ID = generateRandomNum(10000000);
		
		


		 LOTNUMBER = getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MRRNUMBER");	

		 try {
			 
			 stproc_stmt = connection.prepareCall ("{call CATSCON_P_SUMMARYINTERFACE.SP_SUMM_INT_STG}");
			 stproc_stmt.executeUpdate();
			 
				String	query1 =  "SELECT * FROM CATS_PART WHERE PARTCODE =" +"'"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"'";
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query1);
				while (rs.next()) {
					SERIALIZED = rs.getString("SERIALIZED");
				}

					if (SERIALIZED.equalsIgnoreCase("N")){

						query = "INSERT INTO "
								+"CATS.CATSCON_POREC_STG"
								+"("
								+"CATS_RCPT_LINE_DLVR_TRX_ID,"
								+"PO_RCPT_LINE_DLVR_ID,"
								+"LOT_NUMBER,"
								+"RECORD_ID,"
								+"CREATION_DATE,"
								+"PROCESS_FLAG,"
								+"ITEM_CODE,"
								+"CREATED_BY"
								+")"
								+"SELECT "
								+"CATS_TRXID,"
								+"SHIPMENT_LINE_ID,"
								+"RECEIPT_NUM,"
								+RECORD_ID+","
								+"SYSDATE,"
								+"'N',"								
								+"ITEM_CODE,"
								+selectQuerySingleValue("SELECT * FROM CATS_CONTACT_UDFDATA WHERE CONTACTID=1", "NUMBER3") 
								+" FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
								+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"'"
								+" AND ITEM_CODE = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"'";		
						
						executeUpdateQuery(query, "Delivery Confirmation  - <b>"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"</b>");
						stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_POREC}");	
						stproc_stmt.executeUpdate();		
						stproc_stmt = connection.prepareCall ("{call CATSCON_P_POCONFINTERFACE.SP_INITPOCONFINTERFACE(?)}");
						stproc_stmt.setString(1, "");
						stproc_stmt.executeUpdate();
						stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_POREC_ERPACK}");	
						stproc_stmt.executeUpdate();

						stproc_stmt.close();		

					
					}else{
						String query3 = "SELECT ASSETCODE FROM CATSCON_SUMMARYTRX_STG WHERE RECORD_ID IN (SELECT MAX(RECORD_ID) FROM CATSCON_SUMMARYTRX_STG WHERE CUSTOMTRXTYPE = 'RECEIPT DELIVER' AND RECORD_ID IN (SELECT "
										+"RECORD_ID "
										+"FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
										+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"' "
										+"AND ITEM_CODE     = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"'))";
											
						stmt = connection.createStatement();
						rs = stmt.executeQuery(query3);	
						while (rs.next()) {						
							ASSETCODE = rs.getString("ASSETCODE");				
							addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), ASSETCODE);
						}

							query = "INSERT INTO "
									+"CATS.CATSCON_POREC_STG"
									+"("
									+"CATS_RCPT_LINE_DLVR_TRX_ID,"
									+"PO_RCPT_LINE_DLVR_ID,"
									+"LOT_NUMBER,"
									+"RECORD_ID,"
									+"CREATION_DATE,"
									+"PROCESS_FLAG,"
									+"ITEM_CODE,"
									+"CREATED_BY"
									+")"
									+"SELECT "
									+"CATS_TRXID,"
									+"SHIPMENT_LINE_ID,"
									+"RECEIPT_NUM,"
									+RECORD_ID+","
									+"SYSDATE,"
									+"'N',"								
									+"ITEM_CODE,"
									+selectQuerySingleValue("SELECT * FROM CATS_CONTACT_UDFDATA WHERE CONTACTID=1", "NUMBER3") 
									+" FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
									+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"' "
									+"AND ITEM_CODE = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"' "
									+"AND CATS_TRXID IN (SELECT MAX(CATS_TRXID) FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
									+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"' "
									+"AND ITEM_CODE = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"')";
							
							connection.commit();
							executeUpdateQuery(query, "Delivery Confirmation ITEMCODE : - <b>"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"</b> with Assetcode : <b>" + ASSETCODE +"</b>");
							stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_POREC}");	
							stproc_stmt.executeUpdate();		
							stproc_stmt = connection.prepareCall ("{call CATSCON_P_POCONFINTERFACE.SP_INITPOCONFINTERFACE(?)}");
							stproc_stmt.setString(1, "");
							stproc_stmt.executeUpdate();
							stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_POREC_ERPACK}");	
							stproc_stmt.executeUpdate();

							stproc_stmt.close();		
						}


			}catch (SQLException e) {	
				test.log(LogStatus.FAIL, "Delivery Confirmation   - "+getRuntimeTestdata(inputValueMap.get("VALUE7"))+" is not done successfully");
				e.printStackTrace();			
			}
			return RECORD_ID;
		}

	@SuppressWarnings("resource")
	public int deliveryConfirmationQuery1(LinkedHashMap<String, String> inputValueMap , int Iteration) {

		String query = null;
		String SERIALIZED = null;
		String ASSETCODE = null;
		String LOTNUMBER; 
		int RECORD_ID = 0;
		ResultSet rs;
		Statement stmt;
		CallableStatement stproc_stmt;
		
		RECORD_ID = generateRandomNum(10000000);
		
		if(Iteration!=0) {
		 LOTNUMBER = getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MRRNUMBER"+Iteration);
		}else {
		 LOTNUMBER = getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MRRNUMBER");	
		}


		try {
			 
			 stproc_stmt = connection.prepareCall ("{call CATSCON_P_SUMMARYINTERFACE.SP_SUMM_INT_STG}");
			 stproc_stmt.executeUpdate();
			 
				String	query1 =  "SELECT * FROM CATS_PART WHERE PARTCODE =" +"'"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"'";
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query1);
				while (rs.next()) {
					SERIALIZED = rs.getString("SERIALIZED");
				}

					if (SERIALIZED.equalsIgnoreCase("N")){

						query = "INSERT INTO "
								+"CATS.CATSCON_POREC_STG"
								+"("
								+"CATS_RCPT_LINE_DLVR_TRX_ID,"
								+"PO_RCPT_LINE_DLVR_ID,"
								+"LOT_NUMBER,"
								+"RECORD_ID,"
								+"CREATION_DATE,"
								+"PROCESS_FLAG,"
								+"ITEM_CODE,"
								+"CREATED_BY"
								+")"
								+"SELECT "
								+"CATS_TRXID,"
								+"SHIPMENT_LINE_ID,"
								+"RECEIPT_NUM,"
								+RECORD_ID+","
								+"SYSDATE,"
								+"'N',"								
								+"ITEM_CODE,"
								+selectQuerySingleValue("SELECT * FROM CATS_CONTACT_UDFDATA WHERE CONTACTID=1", "NUMBER3") 
								+" FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
								+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"'"
								+" AND ITEM_CODE = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"'";		
						
						executeUpdateQuery(query, "Delivery Confirmation  - <b>"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"</b>");
						stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_POREC}");	
						stproc_stmt.executeUpdate();		
						stproc_stmt = connection.prepareCall ("{call CATSCON_P_POCONFINTERFACE.SP_INITPOCONFINTERFACE(?)}");
						stproc_stmt.setString(1, "");
						stproc_stmt.executeUpdate();
						stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_POREC_ERPACK}");	
						stproc_stmt.executeUpdate();

						stproc_stmt.close();		

					
					}else{
						String query3 = "SELECT ASSETCODE FROM CATSCON_SUMMARYTRX_STG WHERE RECORD_ID IN (SELECT MAX(RECORD_ID) FROM CATSCON_SUMMARYTRX_STG WHERE CUSTOMTRXTYPE = 'RECEIPT DELIVER' AND RECORD_ID IN (SELECT "
										+"RECORD_ID "
										+"FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
										+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"' "
										+"AND ITEM_CODE     = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"'))";
											
						stmt = connection.createStatement();
						rs = stmt.executeQuery(query3);	
						while (rs.next()) {						
							ASSETCODE = rs.getString("ASSETCODE");				
							addRuntimeTestData(testParameters.getCurrentKeywordColumnName(), ASSETCODE);
						}

							query = "INSERT INTO "
									+"CATS.CATSCON_POREC_STG"
									+"("
									+"CATS_RCPT_LINE_DLVR_TRX_ID,"
									+"PO_RCPT_LINE_DLVR_ID,"
									+"LOT_NUMBER,"
									+"RECORD_ID,"
									+"CREATION_DATE,"
									+"PROCESS_FLAG,"
									+"ITEM_CODE,"
									+"CREATED_BY"
									+")"
									+"SELECT "
									+"CATS_TRXID,"
									+"SHIPMENT_LINE_ID,"
									+"RECEIPT_NUM,"
									+RECORD_ID+","
									+"SYSDATE,"
									+"'N',"								
									+"ITEM_CODE,"
									+selectQuerySingleValue("SELECT * FROM CATS_CONTACT_UDFDATA WHERE CONTACTID=1", "NUMBER3") 
									+" FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
									+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"' "
									+"AND ITEM_CODE = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"' "
									+"AND CATS_TRXID IN (SELECT MAX(CATS_TRXID) FROM BTVL_CATS_DELIVER_INB_STAG_TAB "
									+"WHERE RECEIPT_NUM = '"+LOTNUMBER+"' "
									+"AND ITEM_CODE = '"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"')";
							
							connection.commit();
							executeUpdateQuery(query, "Delivery Confirmation ITEMCODE : - <b>"+getRuntimeTestdata(inputValueMap.get("VALUE7"))+"</b> with Assetcode : <b>" + ASSETCODE +"</b>");
							stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_STG_INT_POREC}");	
							stproc_stmt.executeUpdate();		
							stproc_stmt = connection.prepareCall ("{call CATSCON_P_POCONFINTERFACE.SP_INITPOCONFINTERFACE(?)}");
							stproc_stmt.setString(1, "");
							stproc_stmt.executeUpdate();
							stproc_stmt = connection.prepareCall ("{call CATSCON_P_ERPINBOUND.SP_POREC_ERPACK}");	
							stproc_stmt.executeUpdate();

							stproc_stmt.close();		
						}


			}catch (SQLException e) {	
			test.log(LogStatus.FAIL, "Delivery Confirmation   - "+getRuntimeTestdata(inputValueMap.get("VALUE7"))+" is not done successfully");
			e.printStackTrace();			
		}
		return RECORD_ID;
	}
	public void poTaxUpdateQuery(LinkedHashMap<String, String> inputValueMap){
		String query = null;
		int NON_RECOVERABLE_TAX_AMOUNT = 100;
		int TOTAL_AMOUNT = (Integer.parseInt((inputValueMap.get("VALUE17")))*Integer.parseInt((inputValueMap.get("VALUE19"))))+NON_RECOVERABLE_TAX_AMOUNT;
		try {			
			query = "UPDATE CATSCUST_MRR "
					+"SET TAX_UPDATE = 'Y', NON_RECOVERABLE_TAX_AMOUNT="+NON_RECOVERABLE_TAX_AMOUNT+", TOTAL_AMOUNT="+TOTAL_AMOUNT
					+" WHERE PARTCODE='"+getRuntimeTestdata(inputValueMap.get("VALUE16"))+"' AND POCODE IN ('"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+".OU."+inputValueMap.get("VALUE1")+"')";
			test.log(LogStatus.INFO,query);
			executeUpdateQuery(query, "Following details are updated in CATSCUST_MRR for PO - <b>"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+"</b></br></br>"+
										"<b>TAX_UPDATE</b> = <b>'Y'</b></br>"+
										"<b>NON_RECOVERABLE_TAX_AMOUNT</b> = <b>"+NON_RECOVERABLE_TAX_AMOUNT+"</b></br>"+
										"<b>TOTAL_AMOUNT</b> = <b>"+TOTAL_AMOUNT+"</b></br></br>");
			test.log(LogStatus.INFO, "Below calculation is performed </br></br>"
									+"<b>NON_RECOVERABLE_TAX_AMOUNT</b> = <b>"+NON_RECOVERABLE_TAX_AMOUNT+"</b> (value defaulted inside method 'poTaxUpdateQuery' for testing purpose) </br>"
									+"<b>QUANTITY</b> = <b>"+inputValueMap.get("VALUE17")+"</b></br>"
									+"<b>PO_UNIT_PRICE</b> = <b>"+inputValueMap.get("VALUE19")+"</b></br>"
									+ "<b>TOTAL_AMOUNT = (QUANTITY * PO_UNIT_PRICE) + NON_RECOVERABLE_TAX_AMOUNT<b></br>"
									+"<b>TOTAL_AMOUNT</b> = <b>"+TOTAL_AMOUNT+"</b></br>");
			connection.commit();			

		} catch (SQLException e) {	
			test.log(LogStatus.FAIL, "Tax Update for PO - <b>"+getRuntimeTestdata(testParameters.getCurrentTestCase()+"#PONUMBER")+"</b>");
			e.printStackTrace();			
		}
	}
	
	
	

	public void deliveryinfocomplete( String Complete, String TCID){
		
		if(Complete.equalsIgnoreCase("Yes")){			
		test.log(LogStatus.PASS, "Delivery info complete - <b>Yes</b>");
			deliveryinfocomplete(TCID);
		}
		else{
		test.log(LogStatus.PASS, "Delivery info complete -<b>No</b>");
		}
		
	}
	public void updateMfgPartnoActive(String Mfgpartno , String Status) {

		String query = null;

		Mfgpartno = getRuntimeTestdata(Mfgpartno) ;

		try {

			query = "UPDATE CATSCUST_PARTMANUFACTURER SET ACTIVE ='"+ Status+"' WHERE MFGPARTNUMBER  ='"+Mfgpartno+"'";
			test.log(LogStatus.INFO, "<div style= border:1px solid black;width:200px;height:150px;overflow:scroll;><code>"+query+"</code></div>");

			executeUpdateQuery(query, "Manfacturing Part Number is updated with <b> Active ="+ Status +"</b>");

			connection.commit();			

		} catch (SQLException e) {	
			test.log(LogStatus.FAIL, "Manfacturing Part Number is not updated with <b> Active ="+ Status +"</b>");
			e.printStackTrace();			
		}

}
	
	public void updateSerializedPartStatus(String Assetcode , String Status) {

		String query = null;

		Assetcode = getRuntimeTestdata(Assetcode) ;

		try {
		
			query = "UPDATE CATS_ASSET SET LOCATIONSTATUSID = "
					+ "(SELECT LOCATIONSTATUSID FROM CATS_LOCATIONSTATUS WHERE DESCRIPTION ='"+ Status+"') "
					+ "WHERE ASSETCODE ='"+Assetcode+"'";
			
			test.log(LogStatus.INFO, "<div ><code>"+query+"</code></div>");

			executeUpdateQuery(query, "AssetCode  is updated with <b> Status ="+ Status +"</b>");

			connection.commit();			

		} catch (SQLException e) {	
			test.log(LogStatus.FAIL, "AssetCode  is not updated with <b> Status"+ Status +"</b>");
			e.printStackTrace();			
		}

}
	public void deliveryinfocomplete(String TCID){

		String SHIPMENTID;
		String query;
		String query1;

		String Shipmentnumber= null;
		
		if(properties.getProperty("ExecutionMode").equalsIgnoreCase("DISTRIBUTED")) {
		Shipmentnumber =distributedRuntimeDataProperties.getProperty(TCID);
		}else {
		Shipmentnumber =parallelRuntimeDataProperties.getProperty(TCID);	
		}

		try{
			query = "select * FROM CATS_SHIPMENT where SHIPMENTNUMBER ="+"'"+Shipmentnumber+"'";

			SHIPMENTID = selectQuerySingleValue(query, "SHIPMENTID");

			query1 = "UPDATE  CATSCUST_V_SHIPMENT   SET  VEHICLENUMBER = 'TN47AW4752', COMPLETE = 'Y' WHERE SHIPMENTID ="+"'"+SHIPMENTID+"'";

			executeUpdateQuery(query1, "Shipment  # <b>"+Shipmentnumber+"</b> with Delivery info completed is updated in CATSCUST_V_SHIPMENT");	

			connection.commit();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.FAIL, "SHIPMENT # <b>"+Shipmentnumber+"</b> is not delivery info completed");
		}


	}
	
	public void createtransferreason(LinkedHashMap<String, String> inputValueMap) {

		String query = null;
		int TRANSFERREASONID=0;
		TRANSFERREASONID = generateRandomNum(100);
		String REASONCODE =  inputValueMap.get("REASON");
		String DESCRIPTION = inputValueMap.get("REASON_DESCRIPTION");
		String TRANSFERTYPE =  inputValueMap.get("REASON_TRANSFERTYPE");
		String APPROVAL_REQUIRED =  inputValueMap.get("REASON_APPROVAL_REQUIRED");
		String DEACTIVATE =  inputValueMap.get("REASON_DEACTIVATE");
		String SPARE =  inputValueMap.get("REASON_SPARE");

		try {
			query ="INSERT INTO "
					+"CATSCUST_TRANSFERREASON"+"("

				+"TRANSFERREASONID,"
				+"REASONCODE,"
				+"DESCRIPTION,"
				+"TRANSFERTYPE,"
				+"APPROVAL_REQUIRED,"
				+"DEACTIVATE,"
				+"SPARE,"
				+"ADDCONTACTID,"
				+"ADDDTTM,"
				+"MODIFIEDCONTACTID,"
				+"MODIFIEDDTTM)"
				+"VALUES"
				+"("
				+TRANSFERREASONID+","
				+"'"+REASONCODE+"',"
				+"'"+DESCRIPTION+"',"
				+"'"+TRANSFERTYPE+"',"
				+"'"+APPROVAL_REQUIRED+"',"
				+"'"+DEACTIVATE+"',"
				+"'"+SPARE+"',"
				+"'"+1+"',"
				+"SYSDATE,"
				+"'"+1+"',"
				+"SYSDATE"+")";

			executeUpdateQuery(query, "TRANSFER REASON: <b>"+REASONCODE+"</b>  is added  into CATSCUST_TRANSFERREASON table");

			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.FAIL, "TRANSFER REASON: <b>"+REASONCODE+"</b>  is not added  into CATSCUST_TRANSFERREASON table");
		}	

	}
	public int createBulkTransferRequest(LinkedHashMap<String, String> inputValueMap){

		ResultSet rs;
		Statement stmt;
		CallableStatement stproc_stmt;  
		String REASONCODE = inputValueMap.get("REASON");

		try {
			
			stmt = connection.createStatement();
			String query = "SELECT * from CATSCUST_TRANSFERREASON WHERE REASONCODE ="+"'"+REASONCODE+"'";
			rs = stmt.executeQuery(query);
			//if Transfer Reason is present then below code will not run

			while (!rs.isBeforeFirst()) {

				createtransferreason(inputValueMap);
				
			}
			
		}
		catch (SQLException e) {
			test.log(LogStatus.FAIL, e);
		}


		String insertquery = null;	
		String updatequery = null;
		int lastStageId = 0;
		int currentStageId = 0;

		String FROMSUBINVENTORY = ((inputValueMap.get("FROMSUBINVENTORY") == null) ? "NULL" : "'"+inputValueMap.get("FROMSUBINVENTORY") +"'");
		String TOSTATUS = ((inputValueMap.get("TOSTATUS") == null) ? "NULL" : "'"+inputValueMap.get("TOSTATUS") +"'");
		String TOSUBINVENTORY = ((inputValueMap.get("TOSUBINVENTORY") == null) ? "NULL" : "'"+inputValueMap.get("TOSUBINVENTORY") +"'");
		String DESTINATIONLOCATION = ((inputValueMap.get("DESTINATIONLOCATION") == null) ? "NULL" : "'"+inputValueMap.get("DESTINATIONLOCATION") +"'");
		String SERVICEORDER = ((inputValueMap.get("SERVICEORDER") == null) ? "NULL" : "'"+inputValueMap.get("SERVICEORDER") +"'");
		String RECEIVERNAME = ((inputValueMap.get("RECEIVERNAME") == null) ? "NULL" : "'"+inputValueMap.get("RECEIVERNAME") +"'");
		String RECEIVERPHONE = ((inputValueMap.get("RECEIVERPHONE") == null) ? "NULL" : "'"+inputValueMap.get("RECEIVERPHONE") +"'");
		String BEHALFOF = ((inputValueMap.get("BEHALFOF") == null) ? "NULL" : "'"+inputValueMap.get("BEHALFOF") +"'");
		String NEEDBYDATE = getfutureFormattedDate(15); test.log(LogStatus.INFO, "NEED BY DATE: <b>" + NEEDBYDATE +"<b>");
		String PARTCODE = ((inputValueMap.get("PARTCODE") == null) ? "NULL" : inputValueMap.get("PARTCODE") );
		String MFGPARTNUMBER = ((inputValueMap.get("MFGPARTNUMBER") == null) ? "NULL" : "'"+inputValueMap.get("MFGPARTNUMBER") +"'");
		String ASSETCODE = ((inputValueMap.get("ASSETCODE") == null) ? "NULL" : "'"+inputValueMap.get("ASSETCODE") +"'");
		String RMANUMBER = ((inputValueMap.get("RMANUMBER") == null) ? "NULL" : "'"+inputValueMap.get("RMANUMBER") +"'");
		String FAULT_CODE = ((inputValueMap.get("FAULT_CODE") == null) ? "NULL" : "'"+inputValueMap.get("FAULT_CODE") +"'");
		String NOTES = ((inputValueMap.get("NOTES") == null) ? "NULL" : "'"+inputValueMap.get("NOTES") +"'");
		
		String WO_NUMBER = ((inputValueMap.get("NOTES") == null) ? "NULL" : "'"+inputValueMap.get("WO_NUMBER") +"'");
		
		
		PARTCODE =  "'"+getRuntimeTestdata(PARTCODE)+"'";


		try {
			lock.lock();
			if(inputValueMap.get("TRANSACTION_TYPE").equalsIgnoreCase("INSERT")){			
				lastStageId = getLastTransactionId("SELECT MAX(STAGEID) AS STAGEID FROM CATSCON_TRANSFERREQ_STG","STAGEID" );
				currentStageId = lastStageId+1;

				insertquery = "INSERT "
						+"INTO CATSCON_TRANSFERREQ_STG"
						+"("
						+"STAGEID,"
						+"PROCESSED,"
						+"GENERATEDREQNUM,"
						+"REFERENCENUMBER,"
						+"FROMLOCATION,"
						+"FROMSTATUS,"
						+"FROMSUBINVENTORY,"
						+"TOLOCATION,"
						+"TOSTATUS,"
						+"TOSUBINVENTORY,"
						+"DESTINATIONLOCATION,"
						+"REASON,"
						+"WO_NUMBER,"
						+"SERVICEORDER,"
						+"RECEIVERNAME,"
						+"RECEIVERPHONE,"
						+"BEHALFOF,"
						+"NEEDBYDATE,"
						+"PARTCODE,"
						+"MFGPARTNUMBER,"
						+"ASSETCODE,"
						+"RMANUMBER,"
						+"FAULT_CODE,"						    
						+"ADDDTTM,"
						+"ADDCONTACTCODE,"
						+"QTYNEEDED,"
						+"NOTES"
						+")"
						+"VALUES"
						+"("
						+currentStageId+","
						+"'"+inputValueMap.get("PROCESSED")+"',"
						+"'"+inputValueMap.get("GENERATEDREQNUM")+"',"
						+"'"+inputValueMap.get("REFERENCENUMBER")+"',"
						+"'"+inputValueMap.get("FROMLOCATION")+"',"
						+"'"+inputValueMap.get("FROMSTATUS")+"',"
						+FROMSUBINVENTORY+","
						+"'"+inputValueMap.get("TOLOCATION")+"',"
						+TOSTATUS+","
						+TOSUBINVENTORY+","
						+DESTINATIONLOCATION+","
						+"'"+inputValueMap.get("REASON")+"',"
						+WO_NUMBER+","
						+SERVICEORDER+","
						+RECEIVERNAME+","
						+RECEIVERPHONE+","
						+BEHALFOF+","
						+"'"+NEEDBYDATE+"',"
						//+NEEDBYDATE+","
						+PARTCODE+","
						+MFGPARTNUMBER+","
						+ASSETCODE+","
						+RMANUMBER+","
						+FAULT_CODE+","						 
						+inputValueMap.get("ADDDTTM")+","
						+"'"+inputValueMap.get("ADDCONTACTCODE")+"',"
						+inputValueMap.get("QTYNEEDED")+","
						+NOTES+")";

				executeUpdateQuery(insertquery, "Bulk Transfer request with REFERENCENUMBER - <b>"+inputValueMap.get("REFERENCENUMBER")+"</b> is <b>"+inputValueMap.get("TRANSACTION_TYPE")+"ED</b> in CATSCON_TRANSFERREQ_STG table (STAGEID - <b>"+currentStageId+"</b>)");
				stproc_stmt = connection.prepareCall ("{call CATSCON_P_TRANSFERIMPORT.SP_PROCESS_TRANSFERS}");	
				stproc_stmt.executeUpdate();	
				
			}else {
				lastStageId = Integer.parseInt(inputValueMap.get("STAGEID"));
				currentStageId = lastStageId;

				updatequery = "";

				executeUpdateQuery(updatequery, "Bulk Transfer request with REFERENCENUMBER - <b>"+inputValueMap.get("REFERENCENUMBER")+"</b> is <b>"+inputValueMap.get("TRANSACTION_TYPE")+"ED</b> in CATSCON_TRANSFERREQ_STG table (STAGEID - <b>"+currentStageId+"</b>)");

			}				


			connection.commit();			

		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return currentStageId;
	} 
	
	
	public void createLocatorRuleset() {
		
	LinkedHashMap<String, String> inputValueMap = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase()+"_RULESET");		
		
	String query = null;	

	try {
		
		int lastRuleSetId = getLastTransactionId("SELECT MAX(LOCATORRULESETID) AS LOCATORRULESETID FROM CATS_LOCATORRULESET","LOCATORRULESETID" );
		int currentRuleSetId = lastRuleSetId+1;	
		
		String locatorRuleSetName = inputValueMap.get("VALUE1")+getCurrentFormattedTime("ddMMhhmmssSSS");
	
		query = "INSERT "
				+"INTO CATS_LOCATORRULESET"
				  +"("
				   +" LOCATORRULESETID,"
				    +"NAME,"
				    +"DESCRIPTION,"
				    +"SEGMENT1_NAME,"
				    +"SEGMENT1_DISPLAY,"
				    +"SEGMENT2_NAME,"
				    +"SEGMENT2_DISPLAY,"
				    +"SEGMENT3_NAME,"
				    +"SEGMENT3_DISPLAY,"    
				    +"ADDCONTACTID,"
				    +"ADDDTTM,"
				    +"MODIFIEDCONTACTID,"
				    +"MODIFIEDDTTM"
				  +")"
				  +"VALUES"
				  +"("
				    +currentRuleSetId+","
				    +"'"+locatorRuleSetName+"',"
				    +"'"+inputValueMap.get("VALUE2")+"',"
				    +"'"+inputValueMap.get("VALUE3")+"',"
				    +"'"+inputValueMap.get("VALUE4")+"',"
				    +"'"+inputValueMap.get("VALUE5")+"',"
				    +"'"+inputValueMap.get("VALUE6")+"',"
				    +"'"+inputValueMap.get("VALUE7")+"',"
				    +"'"+inputValueMap.get("VALUE8")+"',"
				    +inputValueMap.get("VALUE9")+","
				    +inputValueMap.get("VALUE10")+","
				    +inputValueMap.get("VALUE11")+","
				    +inputValueMap.get("VALUE12")+")";		
			
		executeUpdateQuery(query, "Locator Ruleset with Name <b>"+locatorRuleSetName+ "</b> is inserted into table CATS_LOCATORRULESET (<b>LOCATORRULESETID - "+currentRuleSetId+"</b>).");		
		connection.commit();	
		
		addRuntimeTestData("LOCATORRULESETID", String.valueOf(currentRuleSetId));
		addRuntimeTestData("LOCATORRULESET_NAME", String.valueOf(locatorRuleSetName));	
		
		createLocatorRule(currentRuleSetId);
		
	} catch (SQLException e) {		
		e.printStackTrace();
	}

}
	
	
	public void createLocatorRule(int locatorRulsetID) {
		
	LinkedHashMap<String, String> inputValueMap	  = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase()+"_RULE");	
		
	String query = null;	
	
	try {
		lock.lock();
		
		
		int lastRuleId = getLastTransactionId("SELECT MAX(LOCATORRULEID) AS LOCATORRULEID FROM CATS_LOCATORRULE","LOCATORRULEID" );
		int currentRuleId = lastRuleId+1;		
		
		
		String locatorRuleName = inputValueMap.get("VALUE1")+getCurrentFormattedTime("ddMMhhmmssSSS");
	
		query = "INSERT "
				+"INTO CATS_LOCATORRULE"
				+"("
				+"LOCATORRULEID,"
				+"LOCATORRULESETID,"
				+"NAME,"
				+"DESCRIPTION,"
				+"SEGMENT1_CODECOMPONENT,"
				+"SEGMENT1_LOWER,"
				+"SEGMENT1_UPPER,"
				+"SEGMENT2_CODECOMPONENT,"
				+"SEGMENT2_LOWER,"
				+"SEGMENT2_UPPER,"
				+"SEGMENT3_CODECOMPONENT,"
				+"SEGMENT3_LOWER,"
				+"SEGMENT3_UPPER,"
				+"ADDCONTACTID,"
				+"ADDDTTM,"
				+"MODIFIEDCONTACTID,"
				+"MODIFIEDDTTM,"
				+"REVISION"
				+")"
				+"VALUES"
				+"("
				+currentRuleId+","
				+locatorRulsetID+","
				+"'"+locatorRuleName+"',"
				+"'"+inputValueMap.get("VALUE2")+"',"
    			+"'"+inputValueMap.get("VALUE3")+"',"
    			+"'"+inputValueMap.get("VALUE4")+"',"
    			+"'"+inputValueMap.get("VALUE5")+"',"
    			+"'"+inputValueMap.get("VALUE6")+"',"
    			+"'"+inputValueMap.get("VALUE7")+"',"
    			+"'"+inputValueMap.get("VALUE8")+"',"
    			+"'"+inputValueMap.get("VALUE9")+"',"
    			+"'"+inputValueMap.get("VALUE10")+"',"
    			+"'"+inputValueMap.get("VALUE11")+"',"
    			+inputValueMap.get("VALUE12")+","
    			+inputValueMap.get("VALUE13")+","
    			+inputValueMap.get("VALUE14")+","
    			+inputValueMap.get("VALUE15")+","
    			+inputValueMap.get("VALUE16")+")";			
			
		executeUpdateQuery(query, "Locator Rule with Name <b>"+locatorRuleName+ "</b> is inserted into table CATS_LOCATORRULE (<b>LOCATORRULEID - "+currentRuleId+"</b>).");		
		connection.commit();	
		
		addRuntimeTestData("LOCATORRULEID", String.valueOf(currentRuleId));	
		addRuntimeTestData("LOCATORRULE_NAME", String.valueOf(locatorRuleName));	
			
		
		
	} catch (SQLException e) {		
		e.printStackTrace();
	}finally{
		lock.unlock();
	}

}
	
	public void updateLocatorRuleSet(String locationName, String locatorRuleSetID){
		
		String query = null;	
		
		try {
			lock.lock();
			
			if(locatorRuleSetID.contains("#")){
				locatorRuleSetID = getRuntimeTestdata(locatorRuleSetID);
			}
			
			query = "UPDATE CATS_LOCATION SET LOCATORRULESETID="+locatorRuleSetID+" WHERE NAME='"+locationName+"'";
			
			executeUpdateQuery(query, "Locator Ruleset with LOCATORRULESETID <b>"+locatorRuleSetID+ "</b> is linked to Location <b>NAME - "+locationName+"</b>.");		
			connection.commit();	
			
		}catch (SQLException e) {		
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	
	
	/**
	 * Function to get single column data from Database
	 * 
	 * @param1 String query
	 * @param2 String dataRequired
	 * @return NULL
	 * @author Hari
	 * @since 10/10/2017
	 * 
	 */

	public void selectQueryReturnSingleValue(String query, String dataRequired) {
		String data = null;
		Statement stmt;
		ResultSet rs;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				rs.getObject(1);
				data = rs.getString(dataRequired);
				if (!data.equals(null)) {
					break;
				}
				

			}
		} catch (SQLException e) {
			test.log(LogStatus.FAIL, dataRequired + " - <b>" + data +"</b>");
			test.log(LogStatus.FAIL, e);
		}
		
		
		test.log(LogStatus.INFO, dataRequired + " - <b>" + data +"</b>");
		addRuntimeTestData(dataRequired, data);
		

	}
	 
	
	public void createMoveOrder(String iteration){



		LinkedHashMap<String, String> dataMap = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase());

		if(iteration!=null) {

			createMoveOrder(dataMap , Integer.parseInt(iteration));
		}
		else {
			createMoveOrder(dataMap , 0);	
		}


}	
	
	public void createSameMoveOrderMultiLine(String iteration) {

		LinkedHashMap<String, String> dataMap = null;

		
		if(iteration!=null) {
			 dataMap = dataTable.getRowData("Data_Staging",
					testParameters.getCurrentTestCase() + "_MO"+iteration);
			}else {
				dataMap = dataTable.getRowData("Data_Staging",
						testParameters.getCurrentTestCase() + "_MO");
			}
		if (iteration != null) {
			createSameMoveOrderMultiLine(dataMap, Integer.parseInt(iteration));
		} else {
			createSameMoveOrderMultiLine(dataMap, 0);
		}

	}	
	
	public void createSameMoveOrderMultiLine(LinkedHashMap<String, String> inputValueMap , int iteration){		


		String insertquery1 = null;	
		String insertquery2 = null;
		String sP1LOTNumber = null;
		String sPart1Code =null;

		int sMOVEORDERNUMBER = 0;
		int sMOVEORDERHEADERID;
		int sMOVEORDERLINEID;
		int sMOVEORDERLINENO = 0;
		String nInvOrgName = inputValueMap.get("VALUE1");
		String nOppUnitName = inputValueMap.get("VALUE2");
		String sFROMLocationCode =inputValueMap.get("VALUE3");
		String sTOLocationCode = inputValueMap.get("VALUE4");
		String sP1QTY =inputValueMap.get("VALUE6");
		String moveOrderStatus = inputValueMap.get("VALUE8");

		sPart1Code = inputValueMap.get("VALUE5");
		sP1LOTNumber =inputValueMap.get("VALUE7");
		sMOVEORDERLINENO = Integer.parseInt(inputValueMap.get("VALUE9"));
		
		if(iteration>1) {
			sMOVEORDERNUMBER = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MOVEORDER"));
			sMOVEORDERHEADERID = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MOVEORDER_HEADERID"));
			sMOVEORDERLINEID = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MOVEORDER_LINE_ID"))+30;
			addRuntimeTestData("MOVEORDER_LINE_ID",Integer.toString(sMOVEORDERLINEID));	 
		}else {
			sMOVEORDERNUMBER = generateRandomNum(10000000); 
			addRuntimeTestData("MOVEORDER",Integer.toString(sMOVEORDERNUMBER));			
			sMOVEORDERHEADERID = sMOVEORDERNUMBER+10;
			addRuntimeTestData("MOVEORDER_HEADERID",Long.toString(sMOVEORDERHEADERID));
			sMOVEORDERLINEID = sMOVEORDERNUMBER+11;
			addRuntimeTestData("MOVEORDER_LINE_ID",Integer.toString(sMOVEORDERNUMBER));	
		}


		int TRANSACTIONID = generateRandomNum(100000000);
		sPart1Code =  getRuntimeTestdata(sPart1Code);
		sP1LOTNumber= getRuntimeTestdata(sP1LOTNumber);
		String sP1DeliveryChallan ="DC"+sMOVEORDERNUMBER;
		addRuntimeTestData("DCNUMBER",sP1DeliveryChallan);	


		String selectquery = "SELECT * FROM CATS_LOCATION_UDFDATA WHERE LOCATIONID IN (SELECT LOCATIONID FROM CATS_LOCATION WHERE NAME = "+"'"+sFROMLocationCode+"')";

		String FROMLOCATIONID= selectQuerySingleValue(selectquery, "NUMBER1");
		String SYSDATE="'"+ getCurrentFormattedTime("dd-MMM-yy")+"'";
		try {
			lock.lock();
			insertquery1 ="INSERT "
					+"INTO BTVL_CATS_MO_REQUEST_INT_V"
					+" ("
					+" OPERATING_UNIT_ID,"
					+" INV_ORGANIZATION_ID,"
					+" FROM_LOCATION,"
					+" MOVE_ORDER_NUMBER,"
					+" MOVE_ORDER_HEADER_ID,"
					+" MOVE_ORDER_STATUS,"
					+" MOVE_ORDER_LINE_NO,"
					+" MOVE_ORDER_LINE_ID,"
					+" MOVE_ORDER_LINE_STATUS,"
					+" ITEM,"
					+" SOURCE_SUBINVENTORY,"
					+" DESTINATION_SUBINVENTORY,"
					+" SOURCE_LOCATOR,"
					+" DESTINATION_LOCATOR,"
					+" QUANTITY,"
					+" UOM,"
					+" DATE_REQUIRED,"
					+" REASON_ID,"
					+" TO_LOCATION,"
					+" RECEIVER_NAME,"
					+" RECEIVER_CONTACT_NUMBER,"
					+" TOCO_RECEIVER_NAME,"
					+" TOCO_RECEIVER_CONTACT,"
					+" MOVE_ORDER_CREATED_BY,"
					+" MOVE_ORDER_CREATION_DATE,"
					+" MOVE_ORDER_LAST_UPDATE_DATE,"
					+" MO_LINE_CREATION_DATE,"
					+" MO_LINE_LAST_UPDATE_DATE"
					+")"
					+" VALUES"
					+"("
					+  nOppUnitName+","
					+  nInvOrgName+","
					+  FROMLOCATIONID+","
					+  sMOVEORDERNUMBER+","
					+  sMOVEORDERHEADERID+","
					+ "'"+moveOrderStatus+"',"
					+ sMOVEORDERLINENO+","
					+  sMOVEORDERLINEID+","
					+ "'"+"APPROVED"+"',"
					+ "'"+sPart1Code+"',"
					+ "'"+"STORES"+"',"
					+ "'"+"CWIP-CL"+"',"
					+ "'"+sFROMLocationCode+"',"
					+ "'"+sTOLocationCode+"',"
					+ sP1QTY+","
					+ "'"+"NOS"+"',"
					+ SYSDATE+","
					+ 1+","
					+ "'"+sTOLocationCode+"',"
					+ "'"+"SARAN"+"',"
					+ "'"+"9585989008"+"',"
					+ "'"+"HARI"+"',"
					+ "'"+"9789391639"+"',"
					+ 1+","
					+ SYSDATE+","
					+ SYSDATE+","
					+ SYSDATE+","
					+ SYSDATE+
					")";



			insertquery2= "INSERT "
					+"INTO BTVL_CATS_MO_TRANSACT_INT_V"
					+"("
					+"INV_ORGANIZATION_ID,"
					+"TRANSACTION_ID,"
					+"MOVE_ORDER_HEADER_ID,"
					+"MOVE_ORDER_LINE_ID,"
					+"ITEM,"
					+"SOURCE_SUBINVENTORY,"
					+"SOURCE_LOCATOR,"
					+"DESTINATION_LOCATOR,"
					+"DESTINATION_SUBINVENTORY,"
					+"QTY_TRANSACTED,"
					+"UOM,"
					+"LOT_NUMBER,"
					+"CREATED_BY,"
					+"CREATION_DATE,"
					+"TRANSACTION_DATE,"
					+"DELIVERY_CHALLAN_NUMBER,"
					+"DC_CREATION_DATE,"
					+"EBN_NUMBER,"
					+"EBN_ENTRY_CREATED_BY,"
					+"EBN_ENTRY_CREATION_DATE,"
					+"EBN_ENTRY_LAST_UPDATED_BY,"
					+"EBN_ENTRY_LAST_UPDATED_DATE,"
					+"VEHICLE_NUMBER,"
					+"DISABLED_FLAG,"
					+"EXPIRED_FLAG,"
					+"FREEZED_FLAG"
					+")"
					+"VALUES"
					+"("
					+ nInvOrgName+","
					+ TRANSACTIONID+","
					+ sMOVEORDERHEADERID+","
					+ sMOVEORDERLINEID+","
					+ "'"+sPart1Code+"',"
					+ "'"+"STORES"+"',"
					+ "'"+sFROMLocationCode+"',"
					+ "'"+sTOLocationCode+"',"
					+ "'"+"CWIP-CL"+"',"
					+ "-"+sP1QTY+","
					+ "'"+"NOS"+"',"
					+ "'"+sP1LOTNumber+"',"
					+ 1+","
					+ SYSDATE+","
					+ SYSDATE+","
					+ "'"+sP1DeliveryChallan+"',"
					+ SYSDATE+","
					+ "NULL,"
					+ "'',"
					+ "NULL,"
					+ "'',"
					+ "'',"
					+ "NULL,"
					+"'"+"N"+"',"
					+"'"+"N"+"',"
					+"'"+"N"+"'"
					+")";

			executeUpdateQuery(insertquery1, "Inserted Records to BTVL_CATS_MO_REQUEST_INT_V where Move Order Number is "+sMOVEORDERNUMBER);			
			executeUpdateQuery(insertquery2, "Inserted Records to BTVL_CATS_MO_TRANSACT_INT_V where Move Order Header ID is "+sMOVEORDERHEADERID );		
			connection.commit();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}


}

	public void createMoveOrder(LinkedHashMap<String, String> inputValueMap , int iteration){		


		String insertquery1 = null;	
		String insertquery2 = null;
		String sP1LOTNumber = null;
		String sPart1Code =null;

		int sMOVEORDERNUMBER = 0;
		int sMOVEORDERHEADERID;
		int sMOVEORDERLINEID;
		int sMOVEORDERLINENO = 0;
		String nInvOrgName = inputValueMap.get("VALUE1");
		String nOppUnitName = inputValueMap.get("VALUE2");
		String sFROMLocationCode =inputValueMap.get("VALUE3");
		String sTOLocationCode = inputValueMap.get("VALUE4");
		String sP1QTY =inputValueMap.get("VALUE6");
		String moveOrderStatus = inputValueMap.get("VALUE8");

		sPart1Code = inputValueMap.get("VALUE5");
		sP1LOTNumber =inputValueMap.get("VALUE7");
		
		if(iteration>1) {
			sMOVEORDERNUMBER = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MOVEORDER"));
			sMOVEORDERHEADERID = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MOVEORDER_HEADERID"));
			sMOVEORDERLINEID = sMOVEORDERNUMBER+30;
			sMOVEORDERLINENO = iteration;

			if(sPart1Code.contains("@")) {
				String [] Part1Code = sPart1Code.split("@");
				sPart1Code =Part1Code[iteration-1] ;
				
			}

			if(sP1LOTNumber.contains("@")) {
				String [] lotNumber = sP1LOTNumber.split("@");
				sP1LOTNumber =lotNumber [iteration-1] ;			
			}

		}else {
			//String moveOrderquery ="SELECT DECODE(max (cast(MOVE_ORDER_NUMBER as INT)), NULL,1, max(cast(MOVE_ORDER_NUMBER as INT))+1) MOVE_ORDER_NUMBER FROM BTVL_CATS_MO_REQUEST_INT_V";
			//String moveOrdernum= selectQuerySingleValue(moveOrderquery, "MOVE_ORDER_NUMBER");
			// sMOVEORDERNUMBER = Long.parseLong(moveOrdernum);
			sMOVEORDERNUMBER = generateRandomNum(10000000); 
			addRuntimeTestData("MOVEORDER",Integer.toString(sMOVEORDERNUMBER));
			if(iteration==1) {

				if(sPart1Code.contains("@")) {
					String [] Part1Code = sPart1Code.split("@");
					sPart1Code =Part1Code[iteration-1] ;			
				}

				if(sP1LOTNumber.contains("@")) {
					String [] lotNumber = sP1LOTNumber.split("@");
					sP1LOTNumber =lotNumber [iteration-1] ;			
				}
			}

			sMOVEORDERLINENO = 1;
			sMOVEORDERHEADERID = sMOVEORDERNUMBER+10;
			addRuntimeTestData("MOVEORDER_HEADERID",Long.toString(sMOVEORDERHEADERID));
			sMOVEORDERLINEID = sMOVEORDERNUMBER+11;
		}

		//String transactionQuery= "SELECT DECODE(max(TRANSACTION_ID), NULL,1, max(TRANSACTION_ID)+1) TRANSACTION_ID FROM BTVL_CATS_MO_TRANSACT_INT_V";
		//String transactionId= selectQuerySingleValue(transactionQuery, "TRANSACTION_ID");
		//long TRANSACTIONID = Long.parseLong(transactionId);
		
		int TRANSACTIONID = generateRandomNum(100000000);
		sPart1Code =  getRuntimeTestdata(sPart1Code);
		sP1LOTNumber= getRuntimeTestdata(sP1LOTNumber);
		String sP1DeliveryChallan ="DC"+sMOVEORDERNUMBER;
		addRuntimeTestData("DCNUMBER",sP1DeliveryChallan);
		
		//String sEBINNUMBER = inputValueMap.get("EBINNUMBER");
		//String sEBINCreationDate =inputValueMap.get("EBINCREATIONDATE");
		//String sP1VehicleNo = inputValueMap.get("VEHICLENO");

		String selectquery = "SELECT * FROM CATS_LOCATION_UDFDATA WHERE LOCATIONID IN (SELECT LOCATIONID FROM CATS_LOCATION WHERE NAME = "+"'"+sFROMLocationCode+"')";

		String FROMLOCATIONID= selectQuerySingleValue(selectquery, "NUMBER1");
		String SYSDATE="'"+ getCurrentFormattedTime("dd-MMM-yy")+"'";
		try {
			lock.lock();
			insertquery1 ="INSERT "
					+"INTO BTVL_CATS_MO_REQUEST_INT_V"
					+" ("
					+" OPERATING_UNIT_ID,"
					+" INV_ORGANIZATION_ID,"
					+" FROM_LOCATION,"
					+" MOVE_ORDER_NUMBER,"
					+" MOVE_ORDER_HEADER_ID,"
					+" MOVE_ORDER_STATUS,"
					+" MOVE_ORDER_LINE_NO,"
					+" MOVE_ORDER_LINE_ID,"
					+" MOVE_ORDER_LINE_STATUS,"
					+" ITEM,"
					+" SOURCE_SUBINVENTORY,"
					+" DESTINATION_SUBINVENTORY,"
					+" SOURCE_LOCATOR,"
					+" DESTINATION_LOCATOR,"
					+" QUANTITY,"
					+" UOM,"
					+" DATE_REQUIRED,"
					+" REASON_ID,"
					+" TO_LOCATION,"
					+" RECEIVER_NAME,"
					+" RECEIVER_CONTACT_NUMBER,"
					+" TOCO_RECEIVER_NAME,"
					+" TOCO_RECEIVER_CONTACT,"
					+" MOVE_ORDER_CREATED_BY,"
					+" MOVE_ORDER_CREATION_DATE,"
					+" MOVE_ORDER_LAST_UPDATE_DATE,"
					+" MO_LINE_CREATION_DATE,"
					+" MO_LINE_LAST_UPDATE_DATE"
					+")"
					+" VALUES"
					+"("
					+  nOppUnitName+","
					+  nInvOrgName+","
					+  FROMLOCATIONID+","
					+  sMOVEORDERNUMBER+","
					+  sMOVEORDERHEADERID+","
					+ "'"+moveOrderStatus+"',"
					+ sMOVEORDERLINENO+","
					+  sMOVEORDERLINEID+","
					+ "'"+"APPROVED"+"',"
					+ "'"+sPart1Code+"',"
					+ "'"+"STORES"+"',"
					+ "'"+"CWIP-CL"+"',"
					+ "'"+sFROMLocationCode+"',"
					+ "'"+sTOLocationCode+"',"
					+ sP1QTY+","
					+ "'"+"NOS"+"',"
					+ SYSDATE+","
					+ 1+","
					+ "'"+sTOLocationCode+"',"
					+ "'"+"SARAN"+"',"
					+ "'"+"9585989008"+"',"
					+ "'"+"HARI"+"',"
					+ "'"+"9789391639"+"',"
					+ 1+","
					+ SYSDATE+","
					+ SYSDATE+","
					+ SYSDATE+","
					+ SYSDATE+
					")";



			insertquery2= "INSERT "
					+"INTO BTVL_CATS_MO_TRANSACT_INT_V"
					+"("
					+"INV_ORGANIZATION_ID,"
					+"TRANSACTION_ID,"
					+"MOVE_ORDER_HEADER_ID,"
					+"MOVE_ORDER_LINE_ID,"
					+"ITEM,"
					+"SOURCE_SUBINVENTORY,"
					+"SOURCE_LOCATOR,"
					+"DESTINATION_LOCATOR,"
					+"DESTINATION_SUBINVENTORY,"
					+"QTY_TRANSACTED,"
					+"UOM,"
					+"LOT_NUMBER,"
					+"CREATED_BY,"
					+"CREATION_DATE,"
					+"TRANSACTION_DATE,"
					+"DELIVERY_CHALLAN_NUMBER,"
					+"DC_CREATION_DATE,"
					+"EBN_NUMBER,"
					+"EBN_ENTRY_CREATED_BY,"
					+"EBN_ENTRY_CREATION_DATE,"
					+"EBN_ENTRY_LAST_UPDATED_BY,"
					+"EBN_ENTRY_LAST_UPDATED_DATE,"
					+"VEHICLE_NUMBER,"
					+"DISABLED_FLAG,"
					+"EXPIRED_FLAG,"
					+"FREEZED_FLAG"
					+")"
					+"VALUES"
					+"("
					+ nInvOrgName+","
					+ TRANSACTIONID+","
					+ sMOVEORDERHEADERID+","
					+ sMOVEORDERLINEID+","
					+ "'"+sPart1Code+"',"
					+ "'"+"STORES"+"',"
					+ "'"+sFROMLocationCode+"',"
					+ "'"+sTOLocationCode+"',"
					+ "'"+"CWIP-CL"+"',"
					+ "-"+sP1QTY+","
					+ "'"+"NOS"+"',"
					+ "'"+sP1LOTNumber+"',"
					+ 1+","
					+ SYSDATE+","
					+ SYSDATE+","
					+ "'"+sP1DeliveryChallan+"',"
					+ SYSDATE+","
					+ "NULL,"
					+ "'',"
					+ "NULL,"
					+ "'',"
					+ "'',"
					+ "NULL,"
					+"'"+"N"+"',"
					+"'"+"N"+"',"
					+"'"+"N"+"'"
					+")";

			executeUpdateQuery(insertquery1, "Inserted Records to BTVL_CATS_MO_REQUEST_INT_V where Move Order Number is "+sMOVEORDERNUMBER);
			test.log(LogStatus.INFO, "<div style= border:1px solid black;width:200px;height:500px;overflow:scroll;><code>"+insertquery1+"</code></div>");
			executeUpdateQuery(insertquery2, "Inserted Records to BTVL_CATS_MO_TRANSACT_INT_V where Move Order Header ID is "+sMOVEORDERHEADERID );
			test.log(LogStatus.INFO, "<div style= border:1px solid black;width:200px;height:500px;overflow:scroll;><code>"+insertquery2+"</code></div>");
			connection.commit();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}


}

	public void runMOservices() {
	int moveordernum;
	CallableStatement stproc_stmt;

	try{
		stproc_stmt = connection.prepareCall ("{call CATSCON_P_ORACLE_ORDERS_IN.SP_PULL_MO()}");	

		stproc_stmt.executeUpdate();		
		stproc_stmt = connection.prepareCall ("{call CATSCON_P_ORACLE_ORDERS_IN.SP_PULL_MOTRX()}");
		stproc_stmt.executeUpdate();
		stproc_stmt = connection.prepareCall ("{call CATSCON_P_ORDERPROCESSING.SP_PROCESS_MO_REQUISITIONS()}");	
		stproc_stmt.executeUpdate();
		stproc_stmt = connection.prepareCall ("{call CATSCON_P_TRANSFERIMPORT.SP_PROCESS_TRANSFERS()}");
		stproc_stmt.executeUpdate();
		stproc_stmt = connection.prepareCall ("{call CATSCON_P_ORDERPROCESSING.SP_PROCESS_MO_UPDATES()}");	
		stproc_stmt.executeUpdate();
	}
	catch (SQLException e){
		e.printStackTrace();
		test.log(LogStatus.FAIL, e);
		test.log(LogStatus.FAIL, "MO Services Failed");
	}

	moveordernum = Integer.parseInt(getRuntimeTestdata(testParameters.getCurrentTestCase()+"#MOVEORDER"));
	String validateBulkTransferRequest = "SELECT * FROM CATSCON_TRANSFERREQ_STG WHERE REFERENCENUMBER='%s' AND STAGEID='%d'";	
	String selectquery =  "SELECT * FROM CATSCON_TRANSFERREQ_STG WHERE REFERENCENUMBER ="+"'"+moveordernum+"'";
	
	String stageId=selectQuerySingleValue(selectquery, "STAGEID");
	
	boolean successFlag = validateInboundTransaction("Move Order", "PROCESSED", "ERRORMESSAGE", validateBulkTransferRequest,String.valueOf(moveordernum) ,Integer.parseInt(stageId));	

	if(successFlag){
		String requestNumber = selectQuerySingleValue(String.format(validateBulkTransferRequest, String.valueOf(moveordernum) ,Integer.parseInt(stageId)), "GENERATEDREQNUM");		
		String query = "SELECT * FROM CATS_TRANSFER WHERE REFERENCENUMBER ="+"'"+requestNumber+"'";
		String transferno = selectQuerySingleValue(query, "TRANSFERNUMBER");
		addRuntimeTestData("REQUESTNUMBER", requestNumber);
		addRuntimeTestData("TRANSFERNUMBER", transferno);

	}

}	
	
public void createIRISO() {
		
		
		LinkedHashMap<String, String> inputValueMap	  = dataTable.getRowData("Data_Staging", testParameters.getCurrentTestCase()+"_RULE");	
		
		String nFromInvOrgName ="133";// inputValueMap.get("VALUE1");
		String nFromOppUnitName = "112";//inputValueMap.get("VALUE1");
		String nToInvOrgName = "2617";//inputValueMap.get("VALUE1");
		String nToOppUnitName ="2616";// inputValueMap.get("VALUE1");
		String sFROMLocationCode ="BAL-MUNDKA-MDEL";// inputValueMap.get("VALUE1");
		String sTOLocationCode = "204-B023-303476";//inputValueMap.get("VALUE1");

		String NumberofParts = "1";inputValueMap.get("VALUE1");		
		String sPart1Code = "SER_112205";//inputValueMap.get("VALUE1");
		String sP1QTY ="1";// inputValueMap.get("VALUE1");
		String sP1LOTNumber ="MRR_112205";// inputValueMap.get("VALUE1");
		String sP1InvoiceNumber ="INV11220501";// inputValueMap.get("VALUE1");
		String sPart2Code ="NONSER_112205";// inputValueMap.get("VALUE1");
		String sP2QTY = "5";//inputValueMap.get("VALUE1");		
		String sP2LOTNumber ="MRR_112205";// inputValueMap.get("VALUE1");
		String sP2InvoiceNumber ="INV11220501";// inputValueMap.get("VALUE1");
		
		String destinationlocatorID;
		String invOrgLocationID;
		
		destinationlocatorID =  "select NUMBER1 FROM CATS_LOCATION_UDFDATA WHERE LOCATIONID IN (SELECT LOCATIONID FROM CATS_LOCATION WHERE NAME = "+"'"+sTOLocationCode+"')";
		destinationlocatorID=selectQuerySingleValue(destinationlocatorID,"NUMBER1");
		
		invOrgLocationID =  "select NUMBER1 FROM CATS_LOCATION_UDFDATA WHERE LOCATIONID IN (SELECT LOCATIONID FROM CATS_LOCATION WHERE NAME = "+"'"+sFROMLocationCode+"')";
		invOrgLocationID =selectQuerySingleValue(sFROMLocationCode,"NUMBER1");
		

		String insertquery1 = null;	
		String insertquery2 = null;
		String insertquery3 = null;
		
	/*	try {
			lock.lock();
		*/
			insertquery1 ="INSERT INTO "								
					+"CATS"
					+"."
					+"BTVL_CATS_IRISO_DETAIL_INT_V"
					+" ("
					+" SOURCE_INV_ORGANIZATION_ID,"
					+" SOURCE_OPERATING_UNIT_ID,"
					+" DESTINATION_INV_ORG_ID,"
					+" DESTINATION_OPERATING_UNIT_ID,"
					+" SALES_ORDER_NUMBER,"
					+" SO_HEADER_ID,"
					+" SO_CREATION_DATE,"
					+" SO_STATUS,"
					+" SO_LINE_NUMBER,"
					+" SO_LINE_ID,"
					+" SO_LINE_SCHEDULE_SHIP_DATE,"
					+" SO_LINE_SUB_INVENTORY,"
					+" ITEM_CODE,"
					+" SO_LINE_QUANTITY,"
					+" DESTINATION_SUB_INVENTORY,"
					+" DESTINATION_LOCATOR_ID,"
					+" SO_LINE_CREATED_BY,"
					+" SO_LINE_CREATION_DATE,"
					+" SO_LINE_STATUS,"
					+" SO_LINE_LAST_UPDATED_BY,"
					+" SO_LINE_LAST_UPDATE_DATE,"
					+" SOURCE_INV_ORG_LOCATION_ID,"
					+" RECEIVER_NAME,"
					+" RECEIVER_CONTACT_NO,"
					+" UOM"
					+")"
					+"VALUES" 
					+"(" 
					+ nFromInvOrgName+"," 
					+ nFromOppUnitName+","
					+ nToInvOrgName+","
					+ nToOppUnitName+","
					+generateRandomNum(10000000)+","
					+generateRandomNum(10000000)+","
					+ "SYSDATE"+","
					+ "'"+"BOOKED"+"',"
					+ '1'+","
					+generateRandomNum(10000000)+","
					+ "SYSDATE"+","
					+ "'"+"STORES"+"',"
					+ sPart1Code+"," 
					+ sP1QTY +"," 
					+ "'"+"CWIP-CL"+"',"
					+destinationlocatorID+"," 
					+ "'"+"37532"+"',"
					+ "SYSDATE"+","
					+ "'"+"APPROVED"+"',"
					+ "'"+"2685618"+"',"		
					+ "SYSDATE"+","
					+ invOrgLocationID+"," 
					+ "'"+"TNS"+"',"	
					+ "'"+"9810291815"+"',"	
					+ "'"+"NOS"+"'"	
					+")";
			
			
			insertquery2 ="INSERT INTO "
					+"CATS"
					+"."
					+"BTVL_CATS_IRISO_SHIP_TXN_INT_V"
					+" ("
					+" DELIVERY_NAME,"
					+" DELIVERY_DETAIL_NUMBER,"
					+" SHIPPING_FROM_SUB_INVENTORY,"
					+" LOT_NUMBER,"
					+" CURRENT_STATUS,"
					+" TRANSACTION_QUANTITY,"
					+" DELIVERY_LINE_CREATION_DATE,"
					+" DELIVERY_LINE_CREATED_BY,"
					+" DELIVERY_LINE_LAST_UPDATE_DATE,"
					+" DELIVERY_LINE_LAST_UPDATED_BY,"
					+" INVOICE_NUMBER,"
					+" INVOICE_DATE,"
					+" INVOICE_CREATION_DATE,"
					+" INVOICE_LAST_UPDATE_DATE,"
					+" EBN_ENTRY_CREATED_BY,"
					+" EBN_ENTRY_CREATION_DATE,"
					+" EBN_ENTRY_LAST_UPDATE_DATE,"
					+" EBN_NUMBER,"
					+" VEHICLE_NUMBER,"
					+" DISABLED_FLAG,"
					+" EXPIRED_FLAG,"
					+" FREEZED_FLAG,"
					+" SO_HEADER_ID,"
					+" SO_LINE_ID"
					+")"
					+"VALUES" 
					+"(" 
					+ "'"+"9213"+"',"
					+ "'"+"9213"+"',"
					+ "'"+"STORES"+"',"
					+ sP1LOTNumber+","
					+ "'"+"Shipped"+"',"
					+ sP1QTY+","
					+ "SYSDATE"+","
					+ "'"+"2732295"+"',"
					+ "SYSDATE"+","
					+ "'"+"2732295"+"',"
					+ sP1InvoiceNumber+","
					+ "SYSDATE"+","
					+ "SYSDATE"+","
					+ "SYSDATE"+","
					+ "'"+"2732295"+"',"
					+ "'',"
					+ "SYSDATE"+","
					+ "'',"
					+ "'',"
					+ "'"+"N"+"',"
					+ "'"+"N"+"',"
					+ "'"+"Y"+"',"
					+generateRandomNum(10000000)+","
					+generateRandomNum(10000000)
					+")";
			
			insertquery3 ="INSERT INTO "
					+"CATS"
					+"."
					+"BTVL_CATS_IRISO_SHIP_TXN_INT_V"
					+" ("
					+" DELIVERY_NAME,"
					+" DELIVERY_DETAIL_NUMBER,"
					+" SHIPPING_FROM_SUB_INVENTORY,"
					+" LOT_NUMBER,"
					+" CURRENT_STATUS,"
					+" TRANSACTION_QUANTITY,"
					+" DELIVERY_LINE_CREATION_DATE,"
					+" DELIVERY_LINE_CREATED_BY,"
					+" DELIVERY_LINE_LAST_UPDATE_DATE,"
					+" DELIVERY_LINE_LAST_UPDATED_BY,"
					+" INVOICE_NUMBER,"
					+" INVOICE_DATE,"
					+" INVOICE_CREATION_DATE,"
					+" INVOICE_LAST_UPDATE_DATE,"
					+" EBN_ENTRY_CREATED_BY,"
					+" EBN_ENTRY_CREATION_DATE,"
					+" EBN_ENTRY_LAST_UPDATE_DATE,"
					+" EBN_NUMBER,"
					+" VEHICLE_NUMBER,"
					+" DISABLED_FLAG,"
					+" EXPIRED_FLAG,"
					+" FREEZED_FLAG,"
					+" SO_HEADER_ID,"
					+" SO_LINE_ID"
					+")"
					+"VALUES" 
					+"(" 
					+ "'"+"9213"+"',"
					+ "'"+"9213"+"',"
					+ "'"+"STORES"+"',"
					+ sP2LOTNumber+","
					+ "'"+"Shipped"+"',"
					+ sP2QTY+","
					+ "SYSDATE"+","
					+ "'"+"2732295"+"',"
					+ "SYSDATE"+","
					+ "'"+"2732295"+"',"
					+ sP2InvoiceNumber+","
					+ "SYSDATE"+","
					+ "SYSDATE"+","
					+ "SYSDATE"+","
					+ "'"+"2732295"+"',"
					+ "''," 
					+ "SYSDATE"+","
					+ "'',"
					+ "'',"
					+ "'"+"N"+"',"
					+ "'"+"N"+"',"
					+ "'"+"Y"+"',"
					+generateRandomNum(10000000)+","
					+generateRandomNum(10000000)
					+")";
			
			
			test.log(LogStatus.INFO, insertquery1);
			test.log(LogStatus.INFO, insertquery2);
			test.log(LogStatus.INFO, insertquery3);
/*			executeUpdateQuery(insertquery1, "");
			executeUpdateQuery(insertquery2, "");	
			executeUpdateQuery(insertquery3, "");
			connection.commit();	
		} catch (SQLException e) {		
			e.printStackTrace();
		}finally{
			lock.unlock();
		}*/

	}
}