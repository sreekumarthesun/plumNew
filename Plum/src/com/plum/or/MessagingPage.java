

package com.plum.or;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MessagingPage {
	
	public By welNewPage = By.cssSelector("#add-page");
	public By btnCreatePage = By.xpath("//button[text()='Create']");
	public By txtPagename = By.name("name");
	
	
	@FindBy(linkText = "Messaging")
	public WebElement lnkMessaging;

	@FindBy(xpath = "//li[text()='Send an SMS']")
	public WebElement welSendSMS;

	@FindBy(css = ".ui-page-panel ui-tabs-panel ui-widget-content ui-droppable")
	public WebElement welTab;
	
	@FindBy(xpath = "//li[text()='Send an Email']")
	public WebElement welSendEmail;

	@FindBy(xpath = "//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']/parent::*//div[@id='module-1']")
	public WebElement welMsgSyncreceptor;
	
	@FindBy(xpath = "//div[@class='syn-node ui-draggable syn-node-active'/parent::*//div[@id='module-1']")
	public WebElement welMsgSyncnode;
	
	@FindBy(name="phone_constant")
	public WebElement txtPhoneNumber;
	
	@FindBy(name="message_phrase[]")
	public WebElement txtMsg;

	@FindBy(xpath = "//div[@class='syn-receptor ui-droppable syn-receptor-north ui-draggable syn-receptor-draggable']/parent::*//div[@id='module-2']")
	public WebElement welEmailSyncreceptor;
	
	@FindBy(xpath = "//div[@class='syn-node ui-draggable syn-node-active']/parent::*//div[@id='module-2'")
	public WebElement welEmailSyncnode;
	
	@FindBy(name="smtp_url")
	public WebElement txtSMTPHost;
	
	@FindBy(name="port[]")
	public WebElement txtPort;

	@FindBy(name="username")
	public WebElement txtUserName;
	
	@FindBy(name="password")
	public WebElement txtPassword;
	
	@FindBy(name="from_constant")
	public WebElement txtFrom;
	
	@FindBy(name="to_constant")
	public WebElement txtTo;
	
	@FindBy(name="subject_constant")
	public WebElement txtSubject;
	
	@FindBy(name="cc_constant")
	public WebElement txtCc;
	
}
