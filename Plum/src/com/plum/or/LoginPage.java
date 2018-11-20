/*
 ***************************************************************************************************************
'File Name			:	LoginPage 
'File Description	:	Contains all the Objects of "Login" Page.  
'Prerequisites		:	N/A
'Author				:	Sathya
'Reviewed By		:
'Date Created		:	04 August 2014
'Notes				:	
'****************************************************************************************************************
'****************************************************************************************************************
'                               C H A N G E                         H I S T O R Y
'****************************************************************************************************************
' Date    Change made by              Purpose of change
'-------- ------------------- --------------------------------------------------------------- -------------------
'
'****************************************************************************************************************
 */

package com.plum.or;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
		
	@FindBy(name = "Login")
	public  WebElement lnkLogin;
	
	@FindBy(id = "login-username")
	public  WebElement txtUsername;
	

	@FindBy(id = "password")
	public  WebElement txtPwd;
	

	@FindBy(linkText = "Create an App")
	public  WebElement CreateApp;
	
	@FindBy(xpath = "//button[contains(text(),'started')]")
	public  WebElement btnStarted;
	
	
}
