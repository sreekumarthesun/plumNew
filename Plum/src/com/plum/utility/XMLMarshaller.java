/*
 *************************************************************************************************************
'Script Name		:	XMLMarshaller 
'Script Description	:	To marshal java objects to XML
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	Driver-fullData.xls should be present at specified location.
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	21 July 2012
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

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.testng.Reporter;



public class XMLMarshaller {

	

	/**
	 * The output XML file.
	 */
	protected URI uri;

	/**
	 * Create an xml based writer for specified jaxbObject.
	 * @param uri - uri for output XML file
	 */
	public XMLMarshaller(final URI uri) {
		this.uri = uri;
	}

	//
	// XMLMarshaller
	//
	/**
	 * Write JAXBElement representation of object to XML file.
	 * 
	 * @param jaxbObject - object for marshalling to xml, converted to
	 * JAXBElement. Conversion is done using function inside ObjectFactory.java
	 * which is created with xjc utility
	 * @param docClass - class for object that is going to be marshalled to XML
	 * 
	 * @throws IOException If an I/O error occurred.
	 * @throws FileNotFoundException If the resource was not found.
	 */
	public void write(final JAXBElement jaxbObject, Class docClass)
			throws IOException {
		final OutputStream os = new FileOutputStream(uri.getPath());

		if (os == null) {
			throw new FileNotFoundException("Cannot create resource: " + uri);
		}

		try {
			write(jaxbObject, docClass, os);
		}
		finally {
			os.close();
		}
	}

	protected void write(final JAXBElement jaxbObject, Class docClass,
			final OutputStream os) {
		try {
			marshall(jaxbObject, docClass, os);
		}
		catch (JAXBException ex) {
			Reporter.log("Error in marshalling to XML.");
		}
	}

	private void marshall(final JAXBElement jaxbObject, Class docClass,
			final OutputStream os) throws JAXBException {
		String packageName = docClass.getPackage().getName();
		JAXBContext context = JAXBContext.newInstance(packageName);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(jaxbObject, os);
	}
}
//