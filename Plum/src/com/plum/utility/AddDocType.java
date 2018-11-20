/*
 *************************************************************************************************************
'Script Name		:	AdDocType 
'Script Description	:	To create DocType tag in TestNG XML file
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	Driver-fullData.xls should be present at specified location.
'Author				:	Sreekumar
'Reviewed By		:
'Date Created		:	04 June 2012
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.testng.Reporter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AddDocType {
	
	

	/**
	 * Utility method which adds the DOCTYPE and replaces & with &amp; in the
	 * passed xml file and returns the resulted InputStream.
	 * 
	 * @param fileName
	 */
	
	
	public static InputSource unescapeHTML(String fileName, String dtdFileName)
			throws FileNotFoundException, IOException {
		Boolean isParsingError = checkForParsingErrors(fileName);
		InputSource inputSource = null;
		String docType = "<!DOCTYPE suite SYSTEM ";
		docType = docType + " \"" + dtdFileName + "\">\n";
		Reporter.log("Resulting DOCTYPE to be added to input file ->" + docType);

		FileInputStream fileInputStream = null;
		FileChannel readWriteChannel = null;
		fileInputStream = new FileInputStream(new File(fileName));
		FileChannel fc = fileInputStream.getChannel();
		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc
				.size());
		/* Instead of using default, pass in a decoder. */
		String result = Charset.forName("UTF-8").decode(bb).toString();
		if (isParsingError) {
			result = addDocType(result, docType);
			result = result.replace("& ", "&amp;");
			// result = result.replaceAll("\\<.*?>","");
			// result = StringEscapeUtils.escapeHtml(result);
			// result = result.trim().replaceFirst("^([\\W]+)<","<");
			// String appendedFileName = appendTMS(fileName);
		}

		readWriteChannel = new RandomAccessFile(fileName, "rw").getChannel();
		ByteBuffer wrBuf = readWriteChannel.map(FileChannel.MapMode.READ_WRITE,
				0, result.length());
		wrBuf.put(result.getBytes());
		fileInputStream.close();
		readWriteChannel.close();
		inputSource = parseXML(fileName);
		return inputSource;
	}

	/**
	 * Helper method return the inputStream enforcing the encodingType.
	 * 
	 * @param fileName
	 * @return inputStream
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static InputSource parseXML(String fileName)
			throws FileNotFoundException, UnsupportedEncodingException {
		InputStream inputStream = null;
		Reader reader = null;
		InputSource inputSource = null;
		// try {
		inputStream = new FileInputStream(new File(fileName));
		reader = new InputStreamReader(inputStream, "UTF-8");
		inputSource = new InputSource(reader);
		inputSource.setEncoding("UTF-8");

		return inputSource;
	}

	/**
	 * This method is used to identify whether the given xml string contains the
	 * DOCTYPE and if yes it will compare both the DOCTYPE's and if both are
	 * equal ignores it else it will append the new DOCTYPE to the existing one.
	 * 
	 */
	private static String addDocType(String xmlContent, String newDocType) {
		String[] fileInLines = xmlContent.split("[\\r\\n]+");
		// there is no existing DOCTYPE
		if (!xmlContent.contains("<!DOCTYPE")) {
			// checking for existing xml version tag so that new
			// doctype should be added after this.
			if (xmlContent.contains("<?xml")) {
				for (String line : fileInLines) {
					String oldContent = line;
					if (line.startsWith("<?xml")) {
						// append the newDoctype below this line/after this line
						line = line + "\n" + newDocType;
						xmlContent = xmlContent.replace(oldContent, line);
						break;
					}

				}
			}
			else // No xml version so adding newdoctype @ firstline
			{
				for (String line : fileInLines) {
					String oldContent = line;
					line = newDocType + "\n" + line;
					xmlContent = xmlContent.replace(oldContent, line);
					break;
				}
			}
		}
		// No need to worry about xml version because this is already existing
		// DOCTYPE case.
		else {
			for (String line : fileInLines) {
				if (line.startsWith("<!DOCTYPE")) {
					// this means Doctype already exists but with different DTD
					if (!newDocType.trim().equalsIgnoreCase(line)) {
						String oldContent = line;
						// append the newDoctype below this line/after this line
						line = line + "\n" + newDocType;
						xmlContent = xmlContent.replace(oldContent, line);
						break;
					}

				}
			}
		}
		return xmlContent;
	}

	/**
	 * 
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	
	private static Boolean checkForParsingErrors(String fileName)
			throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(new File(fileName));
		InputSource inputSource = null;
		Boolean isParsingError = Boolean.TRUE;
		Reader reader = null;
		try {
			reader = new InputStreamReader(inputStream, getUTF8Decoder());
			inputSource = new InputSource(reader);
			inputSource.setEncoding("UTF-8");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler();
			saxParser.parse(inputSource, handler);
		}
		catch (SAXException e) {
			isParsingError = Boolean.FALSE;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return isParsingError;
	}

	private static CharsetDecoder getUTF8Decoder() {
		CharsetDecoder dec = Charset.forName("UTF-8").newDecoder();
		dec.onMalformedInput(CodingErrorAction.REPLACE);
		dec.onUnmappableCharacter(CodingErrorAction.REPLACE);
		return dec;
	}
	


}