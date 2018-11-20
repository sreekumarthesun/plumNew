/*
 *************************************************************************************************************
'Script Name		:	Method
'Script Description	:	To create method and its attributes in TestNG XML file.
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	17 July 2012
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.plum.utility.Include;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "methods")
public class Method {

//	@XmlAttribute(name = "name")
//	private String name;
	
	@XmlElement(name = "include", required = true)
	private Include include;

	public Include getInclude() {
		return include;
	}

	public void setInclude(Include include) {
		this.include = include;
	}

	/*public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}*/
}
//