/*
 *************************************************************************************************************
'Script Name		:	Suite 
'Script Description	:	To create suite tag and its attributes in TestNG XML file.
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	Driver-fullData.xls should be present at specified location.
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	20 July 2012
'Notes				:	
'****************************************************************************************************************
'****************************************************************************************************************
'                               C H A N G E                         H I S T O R Y
'****************************************************************************************************************
' Date    Change made by              Purpose of change
'-------- ------------------- --------------------------------------------------------------- ------------------------------------------------
'
'***********************************************************************************************
 */
package com.plum.utility;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.plum.utility.Test;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "suite")
public class Suite {

	@XmlAttribute(name = "name")
	private String suiteName;
	
	@XmlAttribute(name = "parallel")
	private String suiteType;
	
	@XmlElement(name = "test", required = true)
	private List<Test> tests = new ArrayList<Test>();
	
	

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public String getSuiteType() {
		return suiteType;
	}

	public void setSuiteType(String suiteType) {
		this.suiteType = suiteType;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}
	
}
//