/*
 ***************************************************************************************************************
'File Name			:	LoginPageImplementation 
'File Description	:	Contains all the Methods on LoginPage file Objects.  
'Prerequisites		:	extend  - LoginPage
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	01 JUlY 2016
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


package com.plum.impl;

import org.openqa.selenium.WebDriver;
import com.plum.or.LoginPage;

public class LoginPageImplementation extends LoginPage{
	

	protected WebDriver driver;
	public LoginPageImplementation(WebDriver driver)
	{
		this.driver = driver;
	}	
}
