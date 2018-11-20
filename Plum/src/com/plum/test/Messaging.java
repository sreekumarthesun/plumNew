package com.plum.test;



import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Messaging extends Generic {
	public static ResourceBundle common=ResourceBundle.getBundle("common");;
	public static ResourceBundle MESSAGING=ResourceBundle.getBundle("messaging");;
	private static WebDriver driver=null;
	Actions act=null;
	
	//Crate a MESSAGING	
	@Parameters({ "browser", "sysname", "url", "username", "password" })
	@Test
	public void MESSAGING01(String browser, String sysname, String url,String username, String password) throws Exception{
		String stepInfo = null;
			try{
				String testString="MESSAGING01";			
				driver=glogin(browser,sysname,url,username,password);
				act=new Actions(driver);
				stepInfo = "Step 1 : failed to click  - gClickLinkOrButton New page ";				
				gClickLinkOrButton(MESSAGINGPageImpl.welNewPage,0);
				
				stepInfo = "Step 2 : failed to click button - gClickLinkOrButtonElement ";
				gEditTextbox(MESSAGINGPageImpl.txtPagename, MESSAGING.getString("pageName"),2);
				
				stepInfo = "Step 3 : failed to create MESSAGING - gcreateMESSAGING ";
				gClickLinkOrButton(MESSAGINGPageImpl.btnCreatePage,1);
				sendsms();
				sendemail();
				exitapp();		
			}catch(NoSuchElementException e){				
				String stepNo[] = stepInfo.split(":"); 
				gReportDetails(sysname,"MESSAGING01",browser,stepNo[0]);					
				throw new NoSuchElementException(stepInfo);
			}finally{
				stepInfo = null;
				glogout();				
			}
	}


	private void exitapp() {	
		act.dragAndDrop(BasicPageImpl.welHangUp, MESSAGINGPageImpl.welTab).build().perform();
		act.dragAndDrop(BasicPageImpl.welHangSyncreceptor, BasicPageImpl.welHangSyncnode).build().perform();
	}


	private void sendemail() throws Exception {
		act.dragAndDrop(MESSAGINGPageImpl.welSendEmail, MESSAGINGPageImpl.welTab).build().perform();
		act.dragAndDrop(MESSAGINGPageImpl.welEmailSyncreceptor, MESSAGINGPageImpl.welEmailSyncnode).build().perform();
		gEditTextboxWebElement(MESSAGINGPageImpl.txtSMTPHost, MESSAGING.getString("smtphost"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtPort, MESSAGING.getString("port"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtUserName, MESSAGING.getString("username"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtPassword, MESSAGING.getString("password"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtFrom, MESSAGING.getString("from"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtTo, MESSAGING.getString("to"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtSubject, MESSAGING.getString("subject"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtCc,MESSAGING.getString("cc"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtMsg,MESSAGING.getString("msg") );	
	}


	private void sendsms() throws Exception {
		gClickLinkOrButtonElement(MESSAGINGPageImpl.lnkMessaging);
		act.dragAndDrop(MESSAGINGPageImpl.welSendSMS, MESSAGINGPageImpl.welTab).build().perform();
		act.dragAndDrop(MESSAGINGPageImpl.welMsgSyncreceptor, MESSAGINGPageImpl.welMsgSyncnode).build().perform();
		gEditTextboxWebElement(MESSAGINGPageImpl.txtPhoneNumber, MESSAGING.getString("phonenumber"));
		gEditTextboxWebElement(MESSAGINGPageImpl.txtMsg, MESSAGING.getString("msg"));	
	}				
}
