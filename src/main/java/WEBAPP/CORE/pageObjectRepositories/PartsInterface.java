package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface PartsInterface extends CommonObjectRepository {


final By PARTS_PARTCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Part Code")); 
final By PARTS_PARTCODE_COMBO_EDIT = By.xpath(String.format(XPATH_TEXTBOX, "Part Code")); 
final By PARTS_TRANSLATIONCODE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Translation Code"));  
final By PARTS_BUSINESSUNIT_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Business Unit"));  
final By PARTS_TRACKEDFORENVIRONMENT_SELECT = By.xpath(String.format(XPATH_SELECT, "Tracked for Environment"));  
final By PARTS_DESCRIPTION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Description"));  
final By PARTS_TRACKEDFORSAFTEY_SELECT = By.xpath(String.format(XPATH_SELECT, "Tracked for Safety"));  
final By PARTS_OLDPARTCODE_COMBO = By.xpath(String.format(XPATH_TEXTBOX, "Old Part Code"));  
final By PARTS_TRACKEDBYACCOUNTING_SELECT = By.xpath(String.format(XPATH_SELECT, "Tracked by Accounting"));  
final By PARTS_CURRENTLYACTIVE_SELECT = By.xpath(String.format(XPATH_SELECT, "Currently Active"));  
final By PARTS_KIT_SELECT = By.xpath(String.format(XPATH_SELECT, "Kit"));  
final By PARTS_SERIALIZEDINVENTORY_SELECT = By.xpath(String.format(XPATH_SELECT, "Serialized Inventory"));  
final By PARTS_ASSEMBLY_SELECT = By.xpath(String.format(XPATH_SELECT, "Assembly"));  
final By PARTS_LOCKED_SELECT = By.xpath(String.format(XPATH_SELECT, "Locked"));  
final By PARTS_LOT_SELECT = By.xpath(String.format(XPATH_SELECT, "Lot"));  
final By PARTS_STOCKPART_SELECT = By.xpath(String.format(XPATH_SELECT, "Stock Part"));  
final By PARTS_INSPECT_SELECT = By.xpath(String.format(XPATH_SELECT, "Inspect"));  
final By PARTS_CONTAINER_SELECT = By.xpath(String.format(XPATH_SELECT, "Container"));  
final By PARTS_TRACKABLE_SELECT = By.xpath(String.format(XPATH_SELECT, "Trackable"));  
final By PARTS_ORDERABLE_SELECT = By.xpath(String.format(XPATH_SELECT, "Orderable"));  
final By PARTS_PURCHASABLE_SELECT = By.xpath(String.format(XPATH_SELECT, "Purchasable"));  
final By PARTS_NMSENABLED_SELECT = By.xpath(String.format(XPATH_SELECT, "NMS Enabled")); 
final By PARTS_NUMSSERIALIZED__SELECT = By.xpath(String.format(XPATH_SELECT, "NMS Serialized"));  
final By PARTS_INSTALLABLE_SELECT = By.xpath(String.format(XPATH_SELECT, "Installable")); 
final By PARTS_SERIALPREFIX_SELECT = By.xpath(String.format(XPATH_SELECT, "Serial Prefix"));  
final By PARTS_COST_TXT  = By.xpath(String.format(XPATH_TEXTBOX, "Cost"));
final By PARTS_COSTCURRENCYCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Cost Currency Code"));  
final By PARTS_SALVAGEVALUE_TXT  = By.xpath(String.format(XPATH_TEXTBOX, "Salvage Value"));
final By PARTS_SALVAGEVALUECURRENCYCODE_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Salvage Value Currency Code"));  
final By PARTS_MANUFACTURER_SELECT = By.xpath(String.format(XPATH_SELECT, "Manufacturer"));  
final By PARTS_ACCOUNTCODE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Account Code"));  
final By PARTS_MFGPARTNUMBER_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Mfg Part Number"));  
final By PARTS_CLEI_TXT = By.xpath(String.format(XPATH_TEXTBOX, "CLEI™"));  
final By PARTS_MFGMODEL_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Mfg Model")); 
final By PARTS_MFGREVISION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Mfg Revision"));  
final By PARTS_UOMDESCRIPTION_SELECT = By.xpath(String.format(XPATH_SELECT, "UOM Description"));  
final By PARTS_ITEMTYPE_SELECT = By.xpath(String.format(XPATH_SELECT, "Item Type"));  
final By PARTS_INVENTORYCLASSIFICATION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Inventory Classification" )); 
final By PARTS_ADDEDBY_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Added By"));  
final By PARTS_OEMWARRANTY_TXT = By.xpath(String.format(XPATH_TEXTBOX, "OEM Warranty"));  
final By PARTS_FROMADDEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Added On Date"));  
final By PARTS_WARRANTYDURATION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Warranty Duration (Months)"));  
final By PARTS_TOADDEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Added On Date"));  
final By PARTS_STARTWARRANTYSTATUS_SELECT = By.xpath(String.format(XPATH_SELECT, "Start Warranty Status"));  
final By PARTS_LASTMODIFIEDBY_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Last Modified By"));  
final By PARTS_LENGTH_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Length"));  
final By PARTS_FROMLASTMODIFIEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Last Modified On Date"));  
final By PARTS_WIDTH_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Width"));  
final By PARTS_TOLASTMODIFIEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Last Modified On Date"));  
final By PARTS_HEIGHT_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Height"));  
final By PARTS_LASTSCANNEDBY_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Last Scanned By"));  
final By PARTS_WEIGHT_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Weight"));  
final By PARTS_FROMLASTSCANNEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Last Scanned On Date"));  
final By PARTS_EXPECTEDLIFE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Expected Life (months)"));  
final By PARTS_TOLASTSCANNEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Last Scanned On Date"));

}
