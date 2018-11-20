/*
 *************************************************************************************************************
'Script Name		:	ObjectFactory 
'Script Description	:	To Create a new ObjectFactory that can be used to create new instances	 
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	Driver-fullData.xls should be present at specified location.
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	12 August 2012
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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.plum.utility.Classes;
import com.plum.utility.Cls;
import com.plum.utility.Suite;
import com.plum.utility.Test;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the jaxbgen package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _CPDevQA_QNAME = new QName("", "suite");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: jaxbgen
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Meta }
	 * 
	 */
	public Suite createSuite() {
		return new Suite();
	}

	
	public Test createTest() {
		return new Test();
	}
	
	public Cls createClass() {
		return new Cls();
	}
	
	public Classes createClasses() {
		return new Classes();
	}

	
	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Bookmarks }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "suite")
	public JAXBElement<Suite> createSuite(Suite value) {
		return new JAXBElement<Suite>(_CPDevQA_QNAME,
				Suite.class, null, value);
	}

}
//