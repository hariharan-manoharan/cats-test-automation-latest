package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface PartsInterface extends CommonObjectRepository {


final By PARTS_PARTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Part Code")); 
final By PARTS_TRANSLATIONCODE_TXT = By.xpath(String.format(xpath_textbox, "Translation Code"));  
final By PARTS_BUSINESSUNIT_COMBO = By.xpath(String.format(xpath_combobox, "Business Unit"));  
final By PARTS_TRACKEDFORENVIRONMENT_SELECT = By.xpath(String.format(xpath_select, "Tracked for Environment"));  
final By PARTS_DSECRIPTION_TXT = By.xpath(String.format(xpath_textbox, "Description"));  
final By PARTS_TRACKEDFORSAFTEY_SELECT = By.xpath(String.format(xpath_select, "Tracked for Safety"));  
final By PARTS_OLDPARTCODE_COMBO = By.xpath(String.format(xpath_textbox, "Old Part Code"));  
final By PARTS_TRACKEDBYACCOUNTING_SELECT = By.xpath(String.format(xpath_select, "Tracked by Accounting"));  
final By PARTS_CURRENTLYACTIVE_SELECT = By.xpath(String.format(xpath_select, "Currently Active"));  
final By PARTS_KIT_SELECT = By.xpath(String.format(xpath_select, "Kit"));  
final By PARTS_SERIALIZEDINVENTORY_SELECT = By.xpath(String.format(xpath_select, "Serialized Inventory"));  
final By PARTS_ASSEMBLY_SELECT = By.xpath(String.format(xpath_select, "Assembly"));  
final By PARTS_LOCKED_SELECT = By.xpath(String.format(xpath_select, "Locked"));  
final By PARTS_LOT_SELECT = By.xpath(String.format(xpath_select, "Lot"));  
final By PARTS_STOCKPART_SELECT = By.xpath(String.format(xpath_select, "Stock Part"));  
final By PARTS_INSPECT_SELECT = By.xpath(String.format(xpath_select, "Inspect"));  
final By PARTS_CONTAINER_SELECT = By.xpath(String.format(xpath_select, "Container"));  
final By PARTS_TRACKABLE_SELECT = By.xpath(String.format(xpath_select, "Trackable"));  
final By PARTS_ORDERABLE_SELECT = By.xpath(String.format(xpath_select, "Orderable"));  
final By PARTS_PURCHASABLE_SELECT = By.xpath(String.format(xpath_select, "Purchasable"));  
final By PARTS_NMSENABLED_SELECT = By.xpath(String.format(xpath_select, "NMS Enabled")); 
final By PARTS_NUMSSERIALIZED__SELECT = By.xpath(String.format(xpath_select, "NMS Serialized"));  
final By PARTS_INSTALLABLE_SELECT = By.xpath(String.format(xpath_select, "Installable")); 
final By PARTS_SERIALPREFIX_SELECT = By.xpath(String.format(xpath_select, "Serial Prefix"));  
final By PARTS_COST_TXT  = By.xpath(String.format(xpath_textbox, "Cost"));
final By PARTS_COSTCURRENCYCODE_COMBO = By.xpath(String.format(xpath_combobox, "Cost Currency Code"));  
final By PARTS_SALVAGEVALUE_TXT  = By.xpath(String.format(xpath_textbox, "Salvage Value"));
final By PARTS_SALVAGEVALUECURRENCYCODE_COMBO = By.xpath(String.format(xpath_combobox, "Salvage Value Currency Code"));  
final By PARTS_MANUFACTURER_SELECT = By.xpath(String.format(xpath_select, "Manufacturer"));  
final By PARTS_ACCOUNTCODE_TXT = By.xpath(String.format(xpath_textbox, "Account Code"));  
final By PARTS_MFGPARTNUMBER_TXT = By.xpath(String.format(xpath_textbox, "Mfg Part Number"));  
final By PARTS_CLEI_TXT = By.xpath(String.format(xpath_textbox, "CLEI™"));  
final By PARTS_MFGMODEL_TXT = By.xpath(String.format(xpath_textbox, "Mfg Model")); 
final By PARTS_MFGREVISION_TXT = By.xpath(String.format(xpath_textbox, "Mfg Revision"));  
final By PARTS_UOMDESCRIPTION_SELECT = By.xpath(String.format(xpath_select, "UOM Description"));  
final By PARTS_ITEMTYPE_SELECT = By.xpath(String.format(xpath_select, "Item Type"));  
final By PARTS_INVENTORYCLASSIFICATION_TXT = By.xpath(String.format(xpath_textbox, "Inventory Classification" )); 
final By PARTS_ADDEDBY_COMBO = By.xpath(String.format(xpath_combobox, "Added By"));  
final By PARTS_OEMWARRANTY_TXT = By.xpath(String.format(xpath_textbox, "OEM Warranty"));  
final By PARTS_FROMADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "From Added On Date"));  
final By PARTS_WARRANTYDURATION_TXT = By.xpath(String.format(xpath_textbox, "Warranty Duration (Months)"));  
final By PARTS_TOADDEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "To Added On Date"));  
final By PARTS_STARTWARRANTYSTATUS_SELECT = By.xpath(String.format(xpath_select, "Start Warranty Status"));  
final By PARTS_LASTMODIFIEDBY_COMBO = By.xpath(String.format(xpath_combobox, "Last Modified By"));  
final By PARTS_LENGTH_TXT = By.xpath(String.format(xpath_textbox, "Length"));  
final By PARTS_FROMLASTMODIFIEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "From Last Modified On Date"));  
final By PARTS_WIDTH_TXT = By.xpath(String.format(xpath_textbox, "Width"));  
final By PARTS_TOLASTMODIFIEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "To Last Modified On Date"));  
final By PARTS_HEIGHT_TXT = By.xpath(String.format(xpath_textbox, "Height"));  
final By PARTS_LASTSCANNEDBY_COMBO = By.xpath(String.format(xpath_combobox, "Last Scanned By"));  
final By PARTS_WEIGHT_TXT = By.xpath(String.format(xpath_textbox, "Weight"));  
final By PARTS_FROMLASTSCANNEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "From Last Scanned On Date"));  
final By PARTS_EXPECTEDLIFE_TXT = By.xpath(String.format(xpath_textbox, "Expected Life (months)"));  
final By PARTS_TOLASTSCANNEDONDATE_TXT = By.xpath(String.format(xpath_textbox, "To Last Scanned On Date"));

}
