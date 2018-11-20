/*
 *************************************************************************************************************
'Script Name		:	Cls
'Script Description	:	To create class tag and its attributes in TestNG XML file
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	15 July 2012
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

import com.plum.utility.Method;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "class")
public class Cls {

	@XmlAttribute(name = "name")
	private String className;
	
	@XmlElement(name = "methods", required = true)
	List<Method> methods = new ArrayList<Method>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	
	
	

}
//