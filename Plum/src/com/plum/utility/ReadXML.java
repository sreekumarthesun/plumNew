package com.plum.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.entity.mime.Header;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import com.plum.test.Generic;
import com.plum.utility.CellInfo;
import com.plum.utility.ReadXML;
import com.plum.utility.WriteExcel;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Here is sample of reading attributes of a given XML element.
 */

public class ReadXML {
	public static int rowIndexForExcel = 1;
	public WriteExcel efile = null;
	private static WritableWorkbook workbook;
	private static WritableSheet sheet;
	private static List<CellInfo> cellInfos;
	private static String className;
	private static String browsername;

	/**
	 * Application entry point
	 * 
	 * @param args
	 *            command-line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ReadXML readXML = new ReadXML();
		Map readEnvProp = getEnvironmentProperties();
		
		


		workbook = Workbook.createWorkbook(new File(readEnvProp.get(
				"TestResulstExcel_path").toString()
				+ "TestResults.xls"));

		sheet = workbook.createSheet("Test Results", 0); // sheet name

		try {

			// creates and returns new instance of SAX-implementation:
			SAXParserFactory factory = SAXParserFactory.newInstance();

			// create SAX-parser...
			SAXParser parser = factory.newSAXParser();
			// .. define our handler:
			SaxHandler handler = new SaxHandler(readXML.efile);

			// and parse:
			parser.parse(readEnvProp.get("TestNGXML_path").toString()
					+ "testng-results.xml", handler);
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}

	}

	private static Map getEnvironmentProperties() {
		ResourceBundle resource = ResourceBundle.getBundle("env");
		Map<String, String> map = convertResourceBundleToMap(resource);
		return map;

	}

	private static Map<String, String> convertResourceBundleToMap(
			ResourceBundle resource) {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, resource.getString(key));
		}

		return map;
	}

	/**
	 * Our own implementation of SAX handler reading a purchase-order data.
	 */
	private static final class SaxHandler extends DefaultHandler {

		public SaxHandler(WriteExcel efile) {
			// TODO Auto-generated constructor stub
		}

		public void startDocument() {
			cellInfos = new ArrayList<CellInfo>();
		}

		public synchronized void characters(char[] cbuf, int start, int len) {
			System.out.println(new String(cbuf, start, len));	
			
		}
		
		
		

		// we enter to element 'qName':
		public void startElement(String uri, String localName, String qName,
				Attributes attrs) throws SAXException {
			
			if (qName.equals("class")) {
				className = attrs.getValue("name");
				System.out.println("name from Class-->" + className);
			}
			
			if(qName.equals("value")){
				String text=attrs.getValue("cdata");		
				System.out.print(text);
				
			}

		
			if (qName.equals("test-method")) {

				
				String name = attrs.getValue("name");				
				String status = attrs.getValue("status");
				
			
				
				int size = cellInfos.size();
				CellInfo cellInfo = new CellInfo();
				cellInfo.setName(name);
				cellInfo.setStatus(status);
				cellInfo.setRowNumber(size);
				cellInfo.setColumnNumber(0);
				
				
				cellInfo.setRowNumber(size + 1);
				cellInfo.setColumnNumber(0);
				cellInfo.setClassName(className);
				cellInfo.setBrowsername(browsername);				
				cellInfos.add(cellInfo);

			}
		}

		public void endDocument() {
			writeExcel();

		}

		public static void writeExcel() {

			try {
				for (CellInfo info : cellInfos) {				
					

					// Writing data on to first(zeroth) column.
					Label label = new Label(info.getColumnNumber(),
							info.getRowNumber(), info.getName());
					sheet.addCell(label);
					// Writing data on to second column.
					Label label1 = new Label(info.getColumnNumber() + 1,
							info.getRowNumber(), info.getStatus());				

					
					// Writing data on to third column.
					Label label2 = new Label(info.getColumnNumber() + 2,
							info.getRowNumber(), info.getClassName());
					

					sheet.addCell(label2);
					// Writing data on to fourth column.
					Label label3 = new Label(info.getColumnNumber() + 3,
							info.getRowNumber(), info.getBrowsername());

					sheet.addCell(label3);
				}

			} catch (RowsExceededException e) {

				e.printStackTrace();
			} catch (WriteException e) {

				e.printStackTrace();
			}
			try {
				workbook.write();
				workbook.close();
			} catch (WriteException e) {

				e.printStackTrace();
			} catch (IOException e) {

			}

		}

	}
}
// }
