package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface PartsInterface extends CommonObjectRepository {


final By PARTS_PARTCODE_COMBO = By.xpath(String.format(xpath_combobox, "Part Code")); 
final By PARTS_TRANSLATIONCODE_TXT = By.xpath(String.format(xpath_textbox, "Translation Code"));  
final By PARTS_BUSINESSUNIT_COMBO = By.xpath(String.format(xpath_combobox, "Business Unit"));  
final By PARTS_TRACKEDFORENVIRONMENT_SELECT = By.xpath(String.format(xpath_select, "Tracked for Environment"));  
final By PARTS_DSECRIPTION_TXT = By.xpath(String.format(xpath_textbox, "Description"));  
final By PARTS_TRACKEDFORSAFTEY_SELECT = By.xpath(String.format(xpath_select, "Tracked for Safety"));  
final By PARTS_OLDPARTCODE_ = By.xpath(String.format(xpath_textbox, "Old Part Code"));  
final By PARTS_TRACKEDBYACCOUNTING_ = By.xpath(String.format(xpath_textbox, "Tracked by Accounting"));  
final By PARTS_CURRENTLYACTIVE_ = By.xpath(String.format(xpath_textbox, "Currently Active"));  
final By PARTS_KIT_ = By.xpath(String.format(xpath_textbox, "Kit"));  
final By PARTS_SERIALIZEDINVENTORY_ = By.xpath(String.format(xpath_textbox, "Serialized Inventory"));  
final By PARTS_ASSEMBLY_ = By.xpath(String.format(xpath_textbox, "Assembly"));  
final By PARTS_LOCKED_ = By.xpath(String.format(xpath_textbox, "Locked"));  
final By PARTS_LOT_ = By.xpath(String.format(xpath_textbox, "Lot"));  
final By PARTS_STOCKPART_ = By.xpath(String.format(xpath_textbox, "Stock Part"));  
final By PARTS_INSPECT_ = By.xpath(String.format(xpath_textbox, "Inspect"));  
final By PARTS_CONTAINER_ = By.xpath(String.format(xpath_textbox, "Container"));  
final By PARTS_TRACKABLE_ = By.xpath(String.format(xpath_textbox, "Trackable"));  
final By PARTS_ORDERABLE_ = By.xpath(String.format(xpath_textbox, "Orderable"));  
final By PARTS_PURCHASABLE_ = By.xpath(String.format(xpath_textbox, "Purchasable"));  
final By PARTS_NMSENABLED_ = By.xpath(String.format(xpath_textbox, "NMS Enabled")); 
final By PARTS_NUMSSERIALIZED__ = By.xpath(String.format(xpath_textbox, "NMS Serialized"));  
final By PARTS_INSTALLABLE_ = By.xpath(String.format(xpath_textbox, "Installable")); 
final By PARTS_SERIALPREFIX_ = By.xpath(String.format(xpath_textbox, "Serial Prefix"));  
final By PARTS_COST_ = By.xpath(String.format(xpath_textbox, "Cost"));
final By PARTS_COSTCURRENCYCODE_ = By.xpath(String.format(xpath_textbox, "Cost Currency Code"));  
final By PARTS_SALVAGEVALUE_ = By.xpath(String.format(xpath_textbox, "Salvage Value"));
final By PARTS_SALVAGEVALUECURRENCYCODE_ = By.xpath(String.format(xpath_textbox, "Salvage Value Currency Code"));  
final By PARTS_MANUFACTURER_ = By.xpath(String.format(xpath_textbox, "Manufacturer"));  
final By PARTS_ACCOUNTCODE_ = By.xpath(String.format(xpath_textbox, "Account Code"));  
final By PARTS_MFGPARTNUMBER_ = By.xpath(String.format(xpath_textbox, "Mfg Part Number"));  
final By PARTS_CLEI_ = By.xpath(String.format(xpath_textbox, "CLEI™"));  
final By PARTS_MFGMODEL_ = By.xpath(String.format(xpath_textbox, "Mfg Model")); 
final By PARTS_MFGREVISION = By.xpath(String.format(xpath_textbox, "Mfg Revision"));  
final By PARTS_UOMDESCRIPTION_ = By.xpath(String.format(xpath_textbox, "UOM Description"));  
final By PARTS_ITEMTYPE_ = By.xpath(String.format(xpath_textbox, "Item Type"));  
final By PARTS_INVENTORYCLASSIFICATION_ = By.xpath(String.format(xpath_textbox, "Inventory Classification" )); 
final By PARTS_ADDEDBY_ = By.xpath(String.format(xpath_textbox, "Added By"));  
final By PARTS_OEMWARRANTY_ = By.xpath(String.format(xpath_textbox, "OEM Warranty"));  
final By PARTS_FROMADDEDONDATE_ = By.xpath(String.format(xpath_textbox, "From Added On Date"));  
final By PARTS_WARRANTYDURATION_ = By.xpath(String.format(xpath_textbox, "Warranty Duration (Months)"));  
final By PARTS_TOADDEDONDATE_ = By.xpath(String.format(xpath_textbox, "To Added On Date"));  
final By PARTS_STARTWARRANTYSTATUS_ = By.xpath(String.format(xpath_textbox, "Start Warranty Status"));  
final By PARTS_LASTMODIFIEDBY_ = By.xpath(String.format(xpath_textbox, "Last Modified By"));  
final By PARTS_LENGTH_ = By.xpath(String.format(xpath_textbox, "Length"));  
final By PARTS_FROMLASTMODIFIEDONDATE_ = By.xpath(String.format(xpath_textbox, "From Last Modified On Date"));  
final By PARTS_WIDTH_ = By.xpath(String.format(xpath_textbox, "Width"));  
final By PARTS_TOLASTMODIFIEDONDATE_ = By.xpath(String.format(xpath_textbox, "To Last Modified On Date"));  
final By PARTS_HEIGHT_ = By.xpath(String.format(xpath_textbox, "Height"));  
final By PARTS_LASTSCANNEDBY_ = By.xpath(String.format(xpath_textbox, "Last Scanned By"));  
final By PARTS_WEIGHT_ = By.xpath(String.format(xpath_textbox, "Weight"));  
final By PARTS_FROMLASTSCANNEDONDATE_ = By.xpath(String.format(xpath_textbox, "From Last Scanned On Date"));  
final By PARTS_EXPECTEDLIFE_ = By.xpath(String.format(xpath_textbox, "Expected Life (months)"));  
final By PARTS_TOLASTSCANNEDONDATE_ = By.xpath(String.format(xpath_textbox, "To Last Scanned On Date"));

}
