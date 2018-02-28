package main.java.framework.testDataAccess;

import main.java.framework.executionSetup.TestParameters;

public abstract class DataTableAbstractFactory {
	
	public abstract DataTable getTestDataTableAccess(String testDataTableType, String testDataTablePath, TestParameters testParameters);

	public abstract DataTable getTestDataTableAccess(String testDataTableType, String testDataTablePath) ;

}
