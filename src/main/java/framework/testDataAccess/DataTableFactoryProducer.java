package main.java.framework.testDataAccess;

public class DataTableFactoryProducer {
	
	public static DataTableAbstractFactory getDataTableFactory(){
		return new DataTableFactory();
	}

}
