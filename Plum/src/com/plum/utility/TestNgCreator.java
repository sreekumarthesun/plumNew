package com.plum.utility;

import java.io.File;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.plum.utility.AddDocType;
import com.plum.utility.Classes;
import com.plum.utility.Cls;
import com.plum.utility.Include;
import com.plum.utility.Method;
import com.plum.utility.ObjectFactory;
import com.plum.utility.Parameter;
import com.plum.utility.Suite;
import com.plum.utility.Test;
import com.plum.utility.TestNgCreator;
import com.plum.utility.XMLMarshaller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestNgCreator {
	public static ResourceBundle common=ResourceBundle.getBundle("common");;
	final static String inputFileName = common.getString("excelpath");
	List<String> sysnames;
	
	private void writeTestNgXML() throws FileNotFoundException, IOException,BiffException {
		try {

			Workbook workbook = Workbook.getWorkbook(new File(common.getString("excelpath")));
			Sheet sheet = workbook.getSheet(0);
			Sheet sheet2 = workbook.getSheet(1);

			int Lstrow = sheet2.getRows();
			for (int l = 0; l < Lstrow; l++) {
				Cell cell1 = sheet2.getCell(0, l);
				if (cell1.getContents().trim().equalsIgnoreCase("Machine Names")) {
					sysnames = new ArrayList<String>();
					for (int j = 1; j < Lstrow - l; j++) {
						if (sheet2.getCell(0, l + j).getContents() != "") {							
							Cell cell = sheet2.getCell(0, j + l);								
								sysnames.add(cell.getContents());						
						}
					}
					break;
				}
			}
			
			ObjectFactory factory = new ObjectFactory();
			Suite suite = factory.createSuite();
			int increment = 1;
			for (String compname : sysnames) {
				List<Test> tests = new ArrayList<Test>();
				final String path = common.getString("xmlpath")+ compname +".xml";
				final URI uri = new URI(path);
				suite.setSuiteName("Suite" + increment);
				increment++;
				for (int i = 1; i < sheet.getRows(); i++) {

					Cell a7 = sheet.getCell(7, i);
					String className = a7.getContents().trim();
					Cell a9 = sheet.getCell(6, i);
					String sysname = a9.getContents().trim();

					if (compname.equalsIgnoreCase(sysname)) {

						// Parameter parameter6 = new Parameter();
						Cell a10 = sheet.getCell(5, i);
						String s10 = a10.getContents();

						String[] brow;
						brow = s10.split(";");
						for (int k = 0; k < brow.length; k++) {
							Test test = factory.createTest();
							test.setTestName(brow[k] + i);
							Parameter browser = new Parameter();
							browser.setParameterName("browser");
							Cell a1 = sheet.getCell(5, i);
							String s1 = a1.getContents();
							browser.setParameterValue(brow[k]);

							Parameter machinename = new Parameter();
							machinename.setParameterName("sysname");
							Cell a2 = sheet.getCell(6, i);
							String s2 = a2.getContents();
							machinename.setParameterValue(s2);

							Parameter url = new Parameter();
							url.setParameterName("url");
							Cell a3 = sheet.getCell(12, i);
							String s3 = a3.getContents();
							url.setParameterValue(s3);

							Parameter uname = new Parameter();
							uname.setParameterName("username");
							Cell a4 = sheet.getCell(10, i);
							String s4 = a4.getContents();
							uname.setParameterValue(s4);

							Parameter pwd = new Parameter();
							pwd.setParameterName("password");
							Cell a5 = sheet.getCell(11, i);
							String s5 = a5.getContents();
							pwd.setParameterValue(s5);

							Parameter feature = new Parameter();
							feature.setParameterName("feature");
							feature.setParameterValue(className);

							List<Parameter> params = new ArrayList<Parameter>();
							params.add(browser);
							params.add(machinename);
							params.add(url);
							params.add(uname);
							params.add(pwd);
							params.add(feature);

							Cls c = factory.createClass();
							Method m = new Method();
							Cell a6 = sheet.getCell(2, i);
							String s6 = a6.getContents();

							Include in = new Include();
							Cell a61 = sheet.getCell(2, i);
							String s61 = a61.getContents().trim();
							in.setName(s61);
							m.setInclude(in);

							List<Method> methods = new ArrayList<Method>();
							methods.add(m);
							c.setMethods(methods);
							c.setClassName("com.gurukulam.test."+ className);

							Classes clsses = factory.createClasses();
							List<Cls> clss = new ArrayList<Cls>();
							clss.add(c);
							clsses.setClasses(clss);
							test.setClasses(clsses);
							test.setParameters(params);

							tests.add(test);

						}
					}

					suite.setTests(tests);

				}
				final XMLMarshaller xmlBookmarks = new XMLMarshaller(uri);
				xmlBookmarks.write(factory.createSuite(suite), Suite.class);				
				AddDocType.unescapeHTML(common.getString("xmlpath") + compname + ".xml","http://testng.org/testng-1.0.dtd");
				 System.out.println("Info: Driver data is written successfuly.");

			}


		} catch (Exception ex) {
			System.out.println("Error: Writing bookmarks XML file failed");
		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		TestNgCreator loader = new TestNgCreator();
		try {
			loader.writeTestNgXML();
		} catch (BiffException e) {
			
			e.printStackTrace();
		}
	}

}
