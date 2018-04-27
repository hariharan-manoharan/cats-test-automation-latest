package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface PartsManufacturersInterface extends CommonObjectRepository {
	
	final By PARTS_MF_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "")); 
	final By PARTS_MF_TXT = By.xpath(String.format(XPATH_TEXTBOX, "")); 
	final By PARTS_MF_SELECT = By.xpath(String.format(XPATH_SELECT, ""));  
	
	final By PARTS_MF_NAME_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Name")); 
	final By PARTS_MF_DESCRIPTION_TXT = By.xpath(String.format(XPATH_TEXTBOX, "Description")); 
	final By PARTS_MF_CURRENTLY_ACTIVE_SELECT = By.xpath(String.format(XPATH_SELECT, "Currently Active"));
	final By PARTS_MF_INTERNAL_MFG_SELECT = By.xpath(String.format(XPATH_SELECT, "Internal Manufacturer"));
	final By PARTS_MF_ADDED_BY_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Added By"));  
	final By PARTS_MF_FROMADDEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Added On Date"));  
	final By PARTS_MF_TOADDEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Added On Date"));  
	final By PARTS_MF_LASTMODIFIEDBY_COMBO = By.xpath(String.format(XPATH_COMBOBOX, "Last Modified By"));  
	final By PARTS_MF_FROMLASTMODIFIEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "From Last Modified On Date"));  
	final By PARTS_MF_TOLASTMODIFIEDONDATE_TXT = By.xpath(String.format(XPATH_TEXTBOX, "To Last Modified On Date"));  
	
	
	final By PARTS_MF_CURRENTLY_ACTIVE_EDITTAB_CHECKBOX= By.xpath(String.format(XPATH_TEXTBOX, "Currently Active")); 
	final By PARTS_MF_INTERNAL_MFG_EDITTAB_CHECKBOX= By.xpath(String.format(XPATH_TEXTBOX, "Internal Manufacturer"));  
}
