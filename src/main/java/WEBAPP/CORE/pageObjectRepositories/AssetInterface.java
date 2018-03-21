package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface AssetInterface extends CommonObjectRepository {
	//XPATH_COMBOBOX
	//XPATH_SELECT
	//xpath_textarea ,XPATH_TEXTBOX
	
	final By ASSET_ASSETCODE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Asset Code"));
	final By ASSET_PARTCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Part Code"));
	final By ASSET_CLEI_TXT = By.xpath(String.format(XPATH_TEXTBOX, "CLEI™"));
	final By ASSET_BUSINESSUNIT_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Business Unit Name"));
	final By ASSET_LOCATIONNAME_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Location Name"));
	final By ASSET_LOCATIONSTATUS_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Location Status"));
	final By ASSET_LOCATORCODE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Locator Code"));
	final By ASSET_LOTNUMBER_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Lot Number"));
	final By ASSET_SERIALNO_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Serial Number"));
	final By ASSET_SOFTWAREREVISION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Software Revision"));
	final By ASSET_FIRMWAREREVISION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Firmware Revision"));
	final By ASSET_HARDWAREREVISION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Hardware Revision"));
	final By ASSET_JOB_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Job"));
	final By ASSET_PROJECTCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Project Code"));
	final By ASSET_TASKCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Task Code"));
	final By ASSET_USAGECODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Usage Code"));
	final By ASSET_CONDITIONCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Condition Code"));
	final By ASSET_OWNERCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Owner Code"));
	final By ASSET_OWNERSTARTDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Owner Start Date"));
	final By ASSET_OWNERENDDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Owner End Date"));
	final By ASSET_FUTOWNERCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Future Owner Code"));
	final By ASSET_DISPOSITONCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Disposition Code"));
	final By ASSET_DISPOSALCREDIT_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Disposal Credit"));
	final By ASSET_POCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "PO Code"));
	final By ASSET_POLINENO_TXT = By.xpath(String.format(XPATH_TEXTBOX, "PO Line Number"));
	final By ASSET_TRANSFERREQUESTNO_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Transfer Request Number"));
	final By ASSET_SHIPMENTNO_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Shipment Number"));
	final By ASSET_CURRENTLYACTIVE_SELECT = By.xpath(String.format(XPATH_SELECT, "Currently Active"));
	final By ASSET_VIRTUALl_SELECT = By.xpath(String.format(XPATH_SELECT, "Virtual"));
	final By ASSET_USED_SELECT = By.xpath(String.format(XPATH_SELECT, "Used"));
	final By ASSET_POSITION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Position"));
	final By ASSET_XCOORDINATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "X Coordinate"));
	final By ASSET_YCOORDINATE_TXT  = By.xpath(String.format(XPATH_TEXTBOX, "Y Coordinate"));
	final By ASSET_ZCOORDINATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Z Coordinate"));
	final By ASSET_ACCOUNTCODE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Account Code"));
	final By ASSET_TOTALCOST_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Total Cost"));
	final By ASSET_PURCHASECOST_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Purchase Cost"));
	final By ASSET_COST2_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Cost 2"));
	final By ASSET_FIXEDASSETID_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Fixed Asset ID"));
	final By ASSET_OPERATIONVALUE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Operation Value"));
	final By ASSET_BOOKVALUE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Book Value"));
	final By ASSET_TAXVALUE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Tax Value"));
	final By ASSET_INSTALLDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Install Date"));
	final By ASSET_INSERVICE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "In Service Date"));
	final By ASSET_OUTOFSERVICEDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Out of Service Date")); 
	final By ASSET_UNINSTALLDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Un-Install Date"));
	final By ASSET_ADDEDBY_TXT = By.xpath(String.format(XPATH_COMBOBOX, "Added By"));
	final By ASSET_FROMADDEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Added On Date"));
	final By ASSET_TOADDEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Added On Date"));
	final By ASSET_LASTMODIFIEDBY_TXT = By.xpath(String.format(XPATH_COMBOBOX, "Last Modified By"));
	final By ASSET_FRLMODIFIED_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Last Modified On Date"));
	final By ASSET_TLMODIFED_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Last Modified On Date"));
	final By ASSET_LASTSCANNED_TXT = By.xpath(String.format(XPATH_COMBOBOX, "Last Scanned By"));
	final By ASSET_FRLSCANNED_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Last Scanned On Date"));
	final By ASSET_TLSCANNED_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Last Scanned On Date"));
	final By ASSET_NOTES_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Notes"));
	
	
}
