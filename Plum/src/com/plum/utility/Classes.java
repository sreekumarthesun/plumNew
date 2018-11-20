/*
 *************************************************************************************************************
'Script Name		:	Classes
'Script Description	:	To create Classes tag and its attributes in TestNG XML file
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	14 July 2012
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

import com.plum.utility.Cls;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "classes")
public class Classes {

	@XmlElement(name = "class", required = true)
	List<Cls> classes = new ArrayList<Cls>();

	public List<Cls> getClasses() {
		return classes;
	}

	public void setClasses(List<Cls> classes) {
		this.classes = classes;
	}


}
