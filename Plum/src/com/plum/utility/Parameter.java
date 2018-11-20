/*
 *************************************************************************************************************
'Script Name		:	Parameter 
'Script Description	:	To create parameter tag and its attributes in TestNG XML file.
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	Driver-fullData.xls should be present at specified location.
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	18 July 2012
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {

	@XmlAttribute(name = "name")
	private String parameterName;

	@XmlAttribute(name = "value")
	private String parameterValue;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
//