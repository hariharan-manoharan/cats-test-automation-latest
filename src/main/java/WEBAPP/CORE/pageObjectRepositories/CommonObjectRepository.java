package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface CommonObjectRepository {
	
	
	//************************************************************** Text fields **********************************************************
	
	// Locator Type :: id
	
	By ID_TEXT_USERNAME = By.id("j_username");	
	By ID_TEXT_PASSWORD = By.id("j_password");
	
	
	//************************************************************** Buttons **************************************************************
	
	// Locator Type :: id
	
	By ID_BTN_LOGIN = By.id("loginBtn");	
	
	// Locator Type :: xpath
	
	By XPATH_BTN_POPUP_YES = By.xpath("//button[contains(text(),\'Yes\')]");
	By XPATH_BTN_POPUP_NO = By.xpath("//button[contains(text(),\'No\')]");
	By XPATH_BTN_POPUP_SAVE = By.xpath("//button[contains(text(),\'Save\')]");
	By XPATH_BTN_POPUP_BACK = By.xpath("//button[contains(text(),\'Back\')]");
	By XPATH_BTN_POPUP_CLOSE = By.xpath("//form[@class = \'dataform-form form\']//a[contains(text(),\'Close\')]");
	By XPATH_BTN_POPUP_CLEAR = By.xpath("//button[contains(text(),\'Clear\')]");
	By XPATH_BTN_POPUP_DELETE = By.xpath("//button[contains(text(),\'Delete\')]");
	By XPATH_BTN_POPUP_OK = By.xpath("//button[contains(text(),\'Ok\')]");
	String XPATH_BTN_FORMAT = "//div[not(contains(@style,\'display: none;\')) and @class = \'dataform-tab tab-content has-action-bar\']//div[normalize-space(text())=\'%s\']";
	
	By XPATH_SEARCH_BTN = By.xpath(String.format(XPATH_BTN_FORMAT, "Search"));
	By XPATH_CLEAR_BTN = By.xpath(String.format(XPATH_BTN_FORMAT, "Clear"));
	By XPATH_SAVE_BTN = By.xpath(String.format(XPATH_BTN_FORMAT, "Save"));
	By XPATH_REFRESH_BTN = By.xpath(String.format(XPATH_BTN_FORMAT, "Refresh"));
	By XPATH_DELETE_BTN = By.xpath(String.format(XPATH_BTN_FORMAT, "Delete"));
	
	
	//************************************************************** Links ****************************************************************
	
	
	// Locator Type :: xpath
	
	By XPATH_LINK_SEARCH_TAB = By.xpath("//li[@class=\'tab\']/a[contains(text(),\'Search\')]");
	By XPATH_LINK_RESULTS_TAB = By.xpath("//li[@class=\'tab\']/a[contains(text(),\'Results\')]");
	By XPATH_LINK_EDIT_TAB = By.xpath("//li[@class=\'tab\']/a[contains(text(),\'Edit\')]");
	
	By XPATH_LINK_ACTIVE_TAB= By.xpath("//li[@class=\'tab active\']/a");

	By XPATH_POPUP_TRANSACTION_SAVED = By.xpath("//div[contains(text(),'Transaction saved.')]");
	
	//************************************************************** Others ****************************************************************
	
	// Locator Type :: xpath
	
	By XPATH_CLIENT_FOLDER = By.xpath("//div[@class = \'nav-tree-item-label\' and contains(text(),\'Client\')]");
	String XPATH_DATAFORM_FOLDER = "//div[@class = \'nav-tree-item-label\' and @style = \'padding-left: 20px;\' and contains(text(),\'%s\')]";
	String XPATH_DATAFORM = "//div[@class = \'nav-tree-item unselectable nav-tree-item-dataform\']/div[contains(text(),\'%s\')]";
	
	String XPATH_COMBOBOX_1 = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[normalize-space(text())= '%s']//following-sibling::div//a";
	
	String XPATH_TEXTBOX = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]/following-sibling::input";
	String XPATH_COMBOBOX = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[normalize-space(text())= '%s']//following-sibling::div//input";
	String XPATH_SELECT = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]//following-sibling::select";
	String XPATH_TEXTAREA = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]//following-sibling::textarea";
	String XPATH_DATAFORM_TITLE = "//div[@class='dataform_title']";

	
	By XPATH_RESULTTAB_EDITICON = By.xpath("//a[@class = 'btn btn-xs btn-default']");
	By XPATH_EDITTAB_PAGINATION = By.xpath("//div[@class='fcm-pagination small']//div[@class='fcm-pagination_details']");
	
	String XPATH_BLOCKINGMESSAGE_HEADER="//div[@class='modal-content']//div//h4[contains(text(),'%s')]";
	
	By XPATH_ERROR_SAVING = By.xpath(String.format(XPATH_BLOCKINGMESSAGE_HEADER, "Error saving record"));
	
	By XPATH_BLOCKINGMESSAGE_CONTENT=By.xpath("//div[@style='text-align: center;']");
	
	
	String XPATH_ROW_CHECKBOX = "(//div[@class='grid-row grid__row'])[%d]/child::*[1]";
	
	By XPATH_RESULTTAB_PAGINATION = By.xpath("//div[@class='fcm-pagination_details']");
	By XPATH_BLOCKINGMESSAGE=By.xpath("//div[@class='blocking-screen']");
	String XPATH_RESULTTAB_RECORDSELECT_CHECKBOX = "(//form[@class='grid-row grid__row'])[%d]//div[@class='grid-row__cell grid-row__cell-CHECKBOX'][1]";
	By XPATH_RESULTTAB_RECORDSLIST = By.xpath("//form[@class='grid-row grid__row']");
}
