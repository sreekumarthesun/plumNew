package com.plum.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plum.impl.MessagingPageImplementation;
import com.plum.impl.BasicPageImplementation;
import com.plum.impl.LoginPageImplementation;

public class Generic {
	public static WebDriver driver = null;
	public static ResourceBundle common = ResourceBundle.getBundle("common");;
	static LoginPageImplementation LoginPageImpl;
	static MessagingPageImplementation MESSAGINGPageImpl;
	static BasicPageImplementation BasicPageImpl;

	public Generic() {

	}

	public static WebDriver getWebDriver(String sysname, String browser, String url) throws MalformedURLException {

		if (browser.equalsIgnoreCase("ff")) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			driver = new RemoteWebDriver(new URL("http://" + sysname + ":4444/wd/hub"), DesiredCapabilities.firefox());
			driver.get(url);
		}

		if (browser.equalsIgnoreCase("ie")) {
			String IEDriverpath = common.getString("driverpath") + "IEDriverServer";
			System.setProperty("webdriver.ie.driver", IEDriverpath);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setVersion("8");
			driver = new RemoteWebDriver(new URL("http://" + sysname + ":4444/wd/hub"),
					DesiredCapabilities.internetExplorer());
			driver.get(url);
		}

		if (browser.equalsIgnoreCase("cr")) {
			String ChromeDriverpath = common.getString("driverpath") + "chromedriver";
			System.setProperty("webdriver.chrome.driver", ChromeDriverpath);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
			driver = new RemoteWebDriver(new URL("http://" + sysname + ":4444/wd/hub"), DesiredCapabilities.chrome());
			driver.get(url);
		}
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		return driver;
	}

	public static void quitWebDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public static WebDriver glogin(String browser, String sysname, String Url, String username, String password)
			throws Exception {
		try {
			driver = Generic.getWebDriver(sysname, browser, Url);
			LoginPageImpl = PageFactory.initElements(driver, LoginPageImplementation.class);
			MESSAGINGPageImpl = PageFactory.initElements(driver, MessagingPageImplementation.class);
			LoginPageImpl.CreateApp.click();
			LoginPageImpl.btnStarted.click();
			return driver;
		} catch (Exception e) {
			throw new Exception();
		}

	}

	

	public void glogout() throws Exception {
		driver.close();
		quitWebDriver();
	}

	
	public String gReportDetails(String computerName, String methodName,String browser, String stepNo) throws IOException{
		String returnVal=null;
		String DateVal = gDateFunction();				
		//Folder to save the screen shots 
		String path = common.getString("screenshot"); 
		String fileName = computerName+"_"+methodName+"_"+browser+"_"+stepNo+"_"+DateVal+".jpg";



		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE); 
		FileUtils.copyFile(screenshot, new File(path+fileName));


		returnVal = stepNo +" : Location of the Screen shot - "+"\\servername\\Screenshots\\"+computerName+"_"+methodName+"_"+stepNo+"_"+DateVal+".jpg";
		
		return returnVal;

	}
	
	public String gDateFunction(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateVal = dateFormat.format(date);
		dateVal = dateVal.replaceAll(" ", "_");
		dateVal = dateVal.replaceAll("/", "_");
		dateVal = dateVal.replaceAll(":", "_");
		return dateVal;

	}


	public boolean gClickLinkOrButton(By strLink, int index) throws Exception {
		try {
			List<WebElement> lstLinks = driver.findElements(strLink);
			lstLinks.get(index).click();
			return true;

		} catch (Exception e) {
			throw new Exception();
		}
	}

	public boolean gClickLinkOrButtonElement(WebElement wel) throws Exception {
		try {
			wel.click();
			return true;

		} catch (Exception e) {
			throw new Exception();
		}

	}

	public static void gIsDisplayed(WebElement welCreateOREditBranch) {
		try {
			welCreateOREditBranch.isDisplayed();
		} catch (Exception e) {
			throw new NoSuchElementException();
		}

	}

	public enum conditionalWait {
		VISIBILITY, INVISIBILITY, FRAME, PRESENCE, POPUPALERT;
	}

	public void gExplicitWait(WebElement welCreateOREditBranch, int maxTimeOut, String strConditionMode, String strName)
			throws InterruptedException {

		conditionalWait mode = conditionalWait.valueOf(strConditionMode.toUpperCase());

		switch (mode) {
		case VISIBILITY:
			(new WebDriverWait(driver, maxTimeOut))
					.until(ExpectedConditions.visibilityOfElementLocated((By) welCreateOREditBranch));
			break;

		case INVISIBILITY:
			(new WebDriverWait(driver, maxTimeOut))
					.until(ExpectedConditions.invisibilityOfElementLocated((By) welCreateOREditBranch));
			break;

		case FRAME:
			(new WebDriverWait(driver, maxTimeOut)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(strName));
			new WebDriverWait(driver, 10).until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Thomson Reuters/')]")));
			break;

		case PRESENCE:
			(new WebDriverWait(driver, maxTimeOut))
					.until(ExpectedConditions.presenceOfElementLocated((By) welCreateOREditBranch));
			break;

		case POPUPALERT:
			List<WebElement> popupElements = driver.findElements((By) welCreateOREditBranch);
			int count = popupElements.size();
			if (count > 0) {
				(new WebDriverWait(driver, maxTimeOut))
						.until(ExpectedConditions.visibilityOfElementLocated((By) welCreateOREditBranch));
				for (int j = 0; j < count; j++) {
					popupElements.get(j).click();
					Thread.sleep(1500);
				}
				break;
			}

		}

	}

	
	public boolean gEditTextbox(By txtKeywords, String strValue, int index) throws Exception {
		try {
			List<WebElement> ls = driver.findElements(txtKeywords);
			ls.get(index).clear();
			ls.get(index).sendKeys(strValue);
			return true;

		} catch (Exception e) {
			throw new Exception();
		}

	}

	public boolean gEditTextboxWebElement(WebElement txtKeywords, String strValue) throws Exception {
		try {
			txtKeywords.clear();
			txtKeywords.sendKeys(strValue);
			return true;

		} catch (Exception e) {
			throw new Exception();
		}

	}
	
	public String gListGetValue(WebElement lstBranch) {
		try {
			String strText = null;
			Select selectListBox = new Select(driver.findElement((By) lstBranch));
			strText = selectListBox.getFirstSelectedOption().getText();

			return strText;
		} catch (Exception e) {
			throw new NoSuchElementException();
		}
	}

	public boolean gListSelect(WebElement lstBranch, String newStaffnames) {
		try {
			new Select(lstBranch).selectByVisibleText(newStaffnames);
			Thread.sleep(3000);
			return true;

		} catch (Exception e) {
			throw new NoSuchElementException();
		}

	}

}
