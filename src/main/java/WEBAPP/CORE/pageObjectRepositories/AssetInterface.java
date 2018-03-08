package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface AssetInterface extends CommonObjectRepository {
	//xpath_combobox
	//xpath_select
	//xpath_textarea ,xpath_textbox
	
	final By ASSET_ASSETCODE_TXT = By.xpath(String.format(xpath_textbox, "Asset Code"));
	final By ASSET_PARTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Part Code"));
	final By ASSET_CLEI_TXT = By.xpath(String.format(xpath_textbox, "CLEI™"));
	final By ASSET_BUSINESSUNIT_COMBO = By.xpath(String.format(xpath_combobox, "Business Unit Name"));
	final By ASSET_LOCATIONNAME_COMBO = By.xpath(String.format(xpath_combobox, "Location Name"));
	final By ASSET_LOCATIONSTATUS_COMBO = By.xpath(String.format(xpath_combobox, "Location Status"));
	final By ASSET_LOCATORCODE_TXT = By.xpath(String.format(xpath_textbox, "Locator Code"));
	final By ASSET_LOTNUMBER_TXT = By.xpath(String.format(xpath_textbox, "Lot Number"));
	final By ASSET_SERIALNO_TXT = By.xpath(String.format(xpath_textbox, "Serial Number"));
	final By ASSET_SOFTWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Software Revision"));
	final By ASSET_FIRMWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Firmware Revision"));
	final By ASSET_HARDWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Hardware Revision"));
	final By ASSET_JOB_COMBO = By.xpath(String.format(xpath_combobox, "Job"));
	final By ASSET_PROJECTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Project Code"));
	final By ASSET_TASKCODE_COMBO = By.xpath(String.format(xpath_combobox, "Task Code"));
	final By ASSET_USAGECODE_COMBO = By.xpath(String.format(xpath_combobox, "Usage Code"));
	final By ASSET_CONDITIONCODE_COMBO = By.xpath(String.format(xpath_combobox, "Condition Code"));
	final By ASSET_OWNERCODE_COMBO = By.xpath(String.format(xpath_combobox, "Owner Code"));
	final By ASSET_OWNERSTARTDATE_TXT = By.xpath(String.format(xpath_textbox, "Owner Start Date"));
	final By ASSET_OWNERENDDATE_TXT = By.xpath(String.format(xpath_textbox, "Owner End Date"));
	final By ASSET_FUTOWNERCODE_COMBO = By.xpath(String.format(xpath_combobox, "Future Owner Code"));
	final By ASSET_DISPOSITONCODE_COMBO = By.xpath(String.format(xpath_combobox, "Disposition Code"));
	final By ASSET_DISPOSALCREDIT_TXT = By.xpath(String.format(xpath_textbox, "Disposal Credit"));
	final By ASSET_POCODE_COMBO = By.xpath(String.format(xpath_combobox, "PO Code"));
	final By ASSET_POLINENO_TXT = By.xpath(String.format(xpath_textbox, "PO Line Number"));
	final By ASSET_TRANSFERREQUESTNO_COMBO = By.xpath(String.format(xpath_combobox, "Transfer Request Number"));
	final By ASSET_SHIPMENTNO_COMBO = By.xpath(String.format(xpath_combobox, "Shipment Number"));
	final By ASSET_CURRENTLYACTIVE_SELECT = By.xpath(String.format(xpath_select, "Currently Active"));
	final By ASSET_VIRTUALl_SELECT = By.xpath(String.format(xpath_select, "Virtual"));
	final By ASSET_USED_SELECT = By.xpath(String.format(xpath_select, "Used"));
	final By ASSET_POSITION_TXT = By.xpath(String.format(xpath_textbox, "Position"));
	final By ASSET_XCOORDINATE_TXT = By.xpath(String.format(xpath_textbox, "X Coordinate"));
	final By ASSET_YCOORDINATE_TXT  = By.xpath(String.format(xpath_textbox, "Y Coordinate"));
	final By ASSET_ZCOORDINATE_TXT = By.xpath(String.format(xpath_textbox, "Z Coordinate"));
	final By ASSET_ACCOUNTCODE_TXT = By.xpath(String.format(xpath_textbox, "Account Code"));
	final By ASSET_TOTALCOST_TXT = By.xpath(String.format(xpath_textbox, "Total Cost"));
	final By ASSET_PURCHASECOST_TXT = By.xpath(String.format(xpath_textbox, "Purchase Cost"));
	final By ASSET_COST2_TXT = By.xpath(String.format(xpath_textbox, "Cost 2"));
	final By ASSET_FIXEDASSETID_TXT = By.xpath(String.format(xpath_textbox, "Fixed Asset ID"));
	final By ASSET_OPERATIONVALUE_TXT = By.xpath(String.format(xpath_textbox, "Operation Value"));
	final By ASSET_BOOKVALUE_TXT = By.xpath(String.format(xpath_textbox, "Book Value"));
	final By ASSET_TAXVALUE_TXT = By.xpath(String.format(xpath_textbox, "Tax Value"));
	final By ASSET_INSTALLDATE_TXT = By.xpath(String.format(xpath_textbox, "Install Date"));
	final By ASSET_INSERVICE_TXT = By.xpath(String.format(xpath_textbox, "In Service Date"));
	final By ASSET_OUTOFSERVICEDATE_TXT = By.xpath(String.format(xpath_textbox, "Out of Service Date")); 
	final By ASSET_UNINSTALLDATE_TXT = By.xpath(String.format(xpath_textbox, "Un-Install Date"));
	final By ASSET_ADDEDBY_TXT = By.xpath(String.format(xpath_combobox, "Added By"));
	final By ASSET_FROMADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "From Added On Date"));
	final By ASSET_TOADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "To Added On Date"));
	final By ASSET_LASTMODIFIEDBY_TXT = By.xpath(String.format(xpath_combobox, "Last Modified By"));
	final By ASSET_FRLMODIFIED_TXT = By.xpath(String.format(xpath_textbox, "From Last Modified On Date"));
	final By ASSET_TLMODIFED_TXT = By.xpath(String.format(xpath_textbox, "To Last Modified On Date"));
	final By ASSET_LASTSCANNED_TXT = By.xpath(String.format(xpath_combobox, "Last Scanned By"));
	final By ASSET_FRLSCANNED_TXT = By.xpath(String.format(xpath_textbox, "From Last Scanned On Date"));
	final By ASSET_TLSCANNED_TXT = By.xpath(String.format(xpath_textbox, "To Last Scanned On Date"));
	
	
}
