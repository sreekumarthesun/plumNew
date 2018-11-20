/*
 *************************************************************************************************************
'Script Name		:	DriverScript 
'Script Description	:	To create include tag and its attributes in TestNG XML file
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	16 July 2012
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

//import nestedxmlmappings.Folder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "include")
public class Include {

	@XmlAttribute(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
//