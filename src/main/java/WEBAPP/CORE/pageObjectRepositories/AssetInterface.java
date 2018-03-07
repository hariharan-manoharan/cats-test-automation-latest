package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface AssetInterface extends CommonObjectRepository {
	//xpath_combobox
	//xpath_select
	//xpath_textarea ,xpath_textbox
	
	By ASSETCODE_TXT = By.xpath(String.format(xpath_textbox, "Asset Code"));
	By PARTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Part Code"));
	By CLEI_TXT = By.xpath(String.format(xpath_textbox, "CLEI™"));
	By BUSINESSUNIT_COMBO = By.xpath(String.format(xpath_combobox, "Business Unit Name"));
	By LOCATIONNAME_COMBO = By.xpath(String.format(xpath_combobox, "Location Name"));
	By LOCATIONSTATUS_COMBO = By.xpath(String.format(xpath_combobox, "Location Status"));
	By LOCATORCODE_TXT = By.xpath(String.format(xpath_textbox, "Locator Code"));
	By LOTNUMBER_TXT = By.xpath(String.format(xpath_textbox, "Lot Number"));
	By SERIALNO_TXT = By.xpath(String.format(xpath_textbox, "Serial Number"));
	By SOFTWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Software Revision"));
	By FIRMWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Firmware Revision"));
	By HARDWAREREVISION_TXT = By.xpath(String.format(xpath_textbox, "Hardware Revision"));
	By JOB_COMBO = By.xpath(String.format(xpath_combobox, "Job"));
	By PROJECTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Project Code"));
	By TASKCODE_COMBO = By.xpath(String.format(xpath_combobox, "Task Code"));
	By USAGECODE_COMBO = By.xpath(String.format(xpath_combobox, "Usage Code"));
	By CONDITIONCODE_COMBO = By.xpath(String.format(xpath_combobox, "Condition Code"));
	By OWNERCODE_COMBO = By.xpath(String.format(xpath_combobox, "Owner Code"));
	By OWNERSTARTDATE_TXT = By.xpath(String.format(xpath_textbox, "Owner Start Date"));
	By OWNERENDDATE_TXT = By.xpath(String.format(xpath_textbox, "Owner End Date"));
	By FUTOWNERCODE_COMBO = By.xpath(String.format(xpath_combobox, "Future Owner Code"));
	By DISPOSITONCODE_COMBO = By.xpath(String.format(xpath_combobox, "Disposition Code"));
	By DISPOSALCREDIT_TXT = By.xpath(String.format(xpath_textbox, "Disposal Credit"));
	By POCODE_COMBO = By.xpath(String.format(xpath_combobox, "PO Code"));
	By POLINENO_TXT = By.xpath(String.format(xpath_textbox, "PO Line Number"));
	By TRANSFERREQUESTNO_COMBO = By.xpath(String.format(xpath_combobox, "Transfer Request Number"));
	By SHIPMENTNO_COMBO = By.xpath(String.format(xpath_combobox, "Shipment Number"));
	By CURRENTLYACTIVE_SELECT = By.xpath(String.format(xpath_select, "Currently Active"));
	By VIRTUALl_SELECT = By.xpath(String.format(xpath_select, "Virtual"));
	By USED_SELECT = By.xpath(String.format(xpath_select, "Used"));
	By POSITION_TXT = By.xpath(String.format(xpath_textbox, "Position"));
	By XCOORDINATE_TXT = By.xpath(String.format(xpath_textbox, "X Coordinate"));
	By YCOORDINATE_TXT  = By.xpath(String.format(xpath_textbox, "Y Coordinate"));
	By ZCOORDINATE_TXT = By.xpath(String.format(xpath_textbox, "Z Coordinate"));
	By ACCOUNTCODE_TXT = By.xpath(String.format(xpath_textbox, "Account Code"));
	By TOTALCOST_TXT = By.xpath(String.format(xpath_textbox, "Total Cost"));
	By PURCHASECOST_TXT = By.xpath(String.format(xpath_textbox, "Purchase Cost"));
	By COST2_TXT = By.xpath(String.format(xpath_textbox, "Cost 2"));
	By FIXEDASSETID_TXT = By.xpath(String.format(xpath_textbox, "Fixed Asset ID"));
	By OPERATIONVALUE_TXT = By.xpath(String.format(xpath_textbox, "Operation Value"));
	By BOOKVALUE_TXT = By.xpath(String.format(xpath_textbox, "Book Value"));
	By TAXVALUE_TXT = By.xpath(String.format(xpath_textbox, "Tax Value"));
	By INSTALLDATE_TXT = By.xpath(String.format(xpath_textbox, "Install Date"));
	By INSERVICE_TXT = By.xpath(String.format(xpath_textbox, "In Service Date"));
	By OUTOFSERVICEDATE_TXT = By.xpath(String.format(xpath_textbox, "Out of Service Date"));
	By UNINSTALLDATE_TXT = By.xpath(String.format(xpath_textbox, "Un-Install Date"));
	By ADDEDBY_TXT = By.xpath(String.format(xpath_combobox, "Added By"));
	By FROMADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "From Added On Date"));
	By TOADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "To Added On Date"));
	By LASTMODIFIEDBY_TXT = By.xpath(String.format(xpath_combobox, "Last Modified By"));
	By FRLMODIFIED_TXT = By.xpath(String.format(xpath_textbox, "From Last Modified On Date"));
	By TLMODIFED_TXT = By.xpath(String.format(xpath_textbox, "To Last Modified On Date"));
	By LASTSCANNED_TXT = By.xpath(String.format(xpath_combobox, "Last Scanned By"));
	By FRLSCANNED_TXT = By.xpath(String.format(xpath_textbox, "From Last Scanned On Date"));
	By TLSCANNED_TXT = By.xpath(String.format(xpath_textbox, "To Last Scanned On Date"));

	
	
	
}
