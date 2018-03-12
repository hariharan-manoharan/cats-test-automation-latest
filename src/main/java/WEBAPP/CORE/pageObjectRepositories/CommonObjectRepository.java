package main.java.WEBAPP.CORE.pageObjectRepositories;

import org.openqa.selenium.By;

public interface CommonObjectRepository {
	
	
	//************************************************************** Text fields **********************************************************
	
	// Locator Type :: id
	
	By id_txt_user_name = By.id("j_username");
	By id_txt_password = By.id("j_password");
	
	
	//************************************************************** Buttons **************************************************************
	
	// Locator Type :: id
	
	By id_btn_login = By.id("loginBtn");	
	
	// Locator Type :: xpath
	
	By xpath_btn_popup_yes = By.xpath("//button[contains(text(),\'Yes\')]");
	By xpath_btn_popup_no = By.xpath("//button[contains(text(),\'No\')]");
	By xpath_btn_popup_save = By.xpath("//button[contains(text(),\'Save\')]");
	By xpath_btn_popup_back = By.xpath("//button[contains(text(),\'Back\')]");
	By xpath_btn_popup_close = By.xpath("//form[@class = \'dataform-form form\']//a[contains(text(),\'Close\')]");
	By xpath_btn_popup_clear = By.xpath("//button[contains(text(),\'Clear\')]");
	By xpath_btn_popup_delete = By.xpath("//button[contains(text(),\'Delete\')]");
	String xpath_btn_format = "//div[not(contains(@style,\'display: none;\')) and @class = \'dataform-tab tab-content has-action-bar\']//div[normalize-space(text())=\'%s\']";

	
	
	//************************************************************** Links ****************************************************************
	
	
	// Locator Type :: xpath
	
	By xpath_link_search_tab = By.xpath("//li[@class=\'tab\']/a[contains(text(),\'Search\')]");
	By xpath_link_results_tab = By.xpath("//li[@class=\'tab\']/a[contains(text(),\'Results\')]");
	By xpath_link_edit_tab = By.xpath("//li[@class=\'tab\']/a[contains(text(),\'Edit\')]");

	
	
	//************************************************************** Others ****************************************************************
	
	// Locator Type :: xpath
	
	By xpath_client_folder = By.xpath("//div[@class = \'nav-tree-item-label\' and contains(text(),\'Client\')]");
	String xpath_dataform_folder = "//div[@class = \'nav-tree-item-label\' and @style = \'padding-left: 20px;\' and contains(text(),\'%s\')]";
	String xpath_data_form = "//div[@class = \'nav-tree-item unselectable nav-tree-item-dataform\']/div[contains(text(),\'%s\')]";
	
	
	
	String xpath_textbox = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]/following-sibling::input";
	String xpath_combobox = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]//following-sibling::div//input";
	String xpath_select = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]//following-sibling::select";
	String xpath_textarea = "//div[not(contains(@style,'display: none;')) and @class = 'dataform-tab tab-content has-action-bar']//label[contains(text(),'%s')]//following-sibling::textarea";
	String xpath_dataform_title = "//div[@class='dataform_title']";

}
