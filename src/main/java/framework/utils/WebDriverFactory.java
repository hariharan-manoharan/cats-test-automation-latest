package main.java.framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {
	


	public static RemoteWebDriver getDriver(String browser) {
		WebDriver driver = null;
		
		switch(browser) {
		
		case "Chrome":
			System.setProperty("webdriver.chrome.driver","./resources/Drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
			
		case "Firefox":
			FirefoxProfile fprofile = new FirefoxProfile();
			fprofile.setAcceptUntrustedCertificates(true);
			System.setProperty("webdriver.gecko.driver","./resources/Drivers/geckodriver.exe");
			driver = new FirefoxDriver(fprofile);
			break;
			
		case "Htmlunit":
			driver = new HtmlUnitDriver();
			break;
			
		case "InternetExplore":
			System.setProperty("webdriver.ie.driver","./resources/Drivers/IEdriver.exe");
			break;
		
		}
		return (RemoteWebDriver)driver;
		
	}


}
