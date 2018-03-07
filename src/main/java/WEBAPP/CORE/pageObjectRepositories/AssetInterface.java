package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface AssetInterface extends CommonObjectRepository {
	//xpath_combobox
	//xpath_select
	//xpath_textarea ,xpath_textbox
	
	By ASSET_ASSETCODE_TXT = By.xpath(String.format(xpath_textbox, "Asset Code"));
	By ASSET_PARTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Part Code"));
	By ASSET_CLEI_TXT = By.xpath(String.format(xpath_textbox, "CLEI™"));
	By ASSET_BUSINESSUNIT_COMBO = By.xpath(String.format(xpath_combobox, "Business Unit Name"));
	By ASSET_LOCATIONNAME_COMBO = By.xpath(String.format(xpath_combobox, "Location Name"));
	By ASSET_LOCATIONSTATUS_COMBO = By.xpath(String.format(xpath_combobox, "Location Status"));
	By ASSET_LOCATORCODE_TXT = By.xpath(String.format(xpath_textbox, "Locator Code"));
	By ASSET_LOTNUMBER_TXT = By.xpath(String.format(xpath_textbox, "Lot Number"));
	By ASSET_SERIALNO_TXT = By.xpath(String.format(xpath_textbox, "Serial Number"));
	By ASSET_SOFTWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Software Revision"));
	By ASSET_FIRMWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Firmware Revision"));
	By ASSET_HARDWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Hardware Revision"));
	By ASSET_JOB_COMBO = By.xpath(String.format(xpath_combobox, "Job"));
	By ASSET_PROJECTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Project Code"));
	By ASSET_TASKCODE_COMBO = By.xpath(String.format(xpath_combobox, "Task Code"));
	By ASSET_USAGECODE_COMBO = By.xpath(String.format(xpath_combobox, "Usage Code"));
	By ASSET_CONDITIONCODE_COMBO = By.xpath(String.format(xpath_combobox, "Condition Code"));
	By ASSET_OWNERCODE_COMBO = By.xpath(String.format(xpath_combobox, "Owner Code"));
	By ASSET_OWNERSTARTDATE_TXT = By.xpath(String.format(xpath_textbox, "Owner Start Date"));
	By ASSET_OWNERENDDATE_TXT = By.xpath(String.format(xpath_textbox, "Owner End Date"));
	By ASSET_FUTOWNERCODE_COMBO = By.xpath(String.format(xpath_combobox, "Future Owner Code"));
	By ASSET_DISPOSITONCODE_COMBO = By.xpath(String.format(xpath_combobox, "Disposition Code"));
	By ASSET_DISPOSALCREDIT_TXT = By.xpath(String.format(xpath_textbox, "Disposal Credit"));
	By ASSET_POCODE_COMBO = By.xpath(String.format(xpath_combobox, "PO Code"));
	By ASSET_POLINENO_TXT = By.xpath(String.format(xpath_textbox, "PO Line Number"));
	By ASSET_TRANSFERREQUESTNO_COMBO = By.xpath(String.format(xpath_combobox, "Transfer Request Number"));
	By ASSET_SHIPMENTNO_COMBO = By.xpath(String.format(xpath_combobox, "Shipment Number"));
	By ASSET_CURRENTLYACTIVE_SELECT = By.xpath(String.format(xpath_select, "Currently Active"));
	By ASSET_VIRTUALl_SELECT = By.xpath(String.format(xpath_select, "Virtual"));
	By ASSET_USED_SELECT = By.xpath(String.format(xpath_select, "Used"));
	By ASSET_POSITION_TXT = By.xpath(String.format(xpath_textbox, "Position"));
	By ASSET_XCOORDINATE_TXT = By.xpath(String.format(xpath_textbox, "X Coordinate"));
	By ASSET_YCOORDINATE_TXT  = By.xpath(String.format(xpath_textbox, "Y Coordinate"));
	By ASSET_ZCOORDINATE_TXT = By.xpath(String.format(xpath_textbox, "Z Coordinate"));
	By ASSET_ACCOUNTCODE_TXT = By.xpath(String.format(xpath_textbox, "Account Code"));
	By ASSET_TOTALCOST_TXT = By.xpath(String.format(xpath_textbox, "Total Cost"));
	By ASSET_PURCHASECOST_TXT = By.xpath(String.format(xpath_textbox, "Purchase Cost"));
	By ASSET_COST2_TXT = By.xpath(String.format(xpath_textbox, "Cost 2"));
	By ASSET_FIXEDASSETID_TXT = By.xpath(String.format(xpath_textbox, "Fixed Asset ID"));
	By ASSET_OPERATIONVALUE_TXT = By.xpath(String.format(xpath_textbox, "Operation Value"));
	By ASSET_BOOKVALUE_TXT = By.xpath(String.format(xpath_textbox, "Book Value"));
	By ASSET_TAXVALUE_TXT = By.xpath(String.format(xpath_textbox, "Tax Value"));
	By ASSET_INSTALLDATE_TXT = By.xpath(String.format(xpath_textbox, "Install Date"));
	By ASSET_INSERVICE_TXT = By.xpath(String.format(xpath_textbox, "In Service Date"));
	By ASSET_OUTOFSERVICEDATE_TXT = By.xpath(String.format(xpath_textbox, "Out of Service Date")); 
	By ASSET_UNINSTALLDATE_TXT = By.xpath(String.format(xpath_textbox, "Un-Install Date"));
	By ASSET_ADDEDBY_TXT = By.xpath(String.format(xpath_combobox, "Added By"));
	By ASSET_FROMADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "From Added On Date"));
	By ASSET_TOADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "To Added On Date"));
	By ASSET_LASTMODIFIEDBY_TXT = By.xpath(String.format(xpath_combobox, "Last Modified By"));
	By ASSET_FRLMODIFIED_TXT = By.xpath(String.format(xpath_textbox, "From Last Modified On Date"));
	By ASSET_TLMODIFED_TXT = By.xpath(String.format(xpath_textbox, "To Last Modified On Date"));
	By ASSET_LASTSCANNED_TXT = By.xpath(String.format(xpath_combobox, "Last Scanned By"));
	By ASSET_FRLSCANNED_TXT = By.xpath(String.format(xpath_textbox, "From Last Scanned On Date"));
	By ASSET_TLSCANNED_TXT = By.xpath(String.format(xpath_textbox, "To Last Scanned On Date"));
	
	
}
