
package com.plum.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;



import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Row;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class CreateExcelFile {

	static FileInputStream io=null;
	static FileOutputStream out =null;
	static FileOutputStream fileOut =null;	
	static String inputXMLFile = "C:\\workspace\\Gurukula\\test-output\\testng-results.xml";
	static String fileToScreenShot = "C:\\workspace\\Gurukula\\resources\\output\\final_results.xls";
	static String outputExcelFile = "C:\\workspace\\Gurukula\\resources\\output\\FinalResults.xls";
	static String inputExcelFile = "C:\\workspace\\Gurukula\\resources\\input\\Driver.xls";
	static String screenShotResults = "C:\\Selenium\\Screenshots\\";
	static HSSFSheet sheet1;
	static HSSFWorkbook wb;
	static HSSFWorkbook hwb;
	static HSSFSheet sheet;




	public static void main(String[] args) throws Exception {
		try {

			//			FileInputStream io= new FileInputStream(filename);
			//			FileOutputStream out = new FileOutputStream (outputFileName);
			System.out.println("Your excel file creation started at: "+DateFunction() );

			createExcelFileData();

			//System.out.println("Your excel file has been generated - createExcelFileData");

			Thread.sleep(1000);		

			CreateExcelFileiwthScreenshot();

			File f =new File(fileToScreenShot);
			f.delete();

			System.out.println("Your excel file has been generated - CreateExcelFileiwthScreenshot : "+DateFunction() );

		}catch(FileNotFoundException fo) 
		{ 
			System.out.println("FileNotFoundException"+ fo.getMessage()); 
		}


	}
	
	public static String DateFunction(){
		Date d = new Date();          
		String date = d.toString().replaceAll(" ", "_");
		date = date.replaceAll(":", "_");
		date = date.replaceAll("\\+", "_");
		return date;

	}


	private static void CreateExcelFileiwthScreenshot() throws Exception {
		try{
			FileInputStream io= new FileInputStream(fileToScreenShot);
			POIFSFileSystem poiFS = new POIFSFileSystem(io);
			FileOutputStream out = new FileOutputStream (outputExcelFile);
			HSSFWorkbook wb=new HSSFWorkbook(poiFS);
			HSSFSheet sheet1=wb.getSheetAt(0); 

			int numberOfRowsSheet1 = sheet1.getPhysicalNumberOfRows();
			for (int rowInd=1; rowInd<numberOfRowsSheet1; rowInd++){
				HSSFRow rowPointer = sheet1.getRow(rowInd);
				String testCaseStatus = rowPointer.getCell(1).getStringCellValue();			
				if (testCaseStatus.trim().equalsIgnoreCase("FAILED")) {
					String testCaseName = rowPointer.getCell(2).getStringCellValue();
					String testCaseBrowser = rowPointer.getCell(4).getStringCellValue();
					//String scrtResults = "C:\\Selenium\\Screenshots\\";
					File folder = new File(screenShotResults); 
					File[] listOfFiles = folder.listFiles();
					{
						for (int i = 0; i < listOfFiles.length; i++) { 
							if (listOfFiles[i].isFile()) {   
								String indImgName = listOfFiles[i].getName();
								String imgArray[] = indImgName.split("_");
								String methodName = imgArray[1];
								String browser = imgArray[2];							
								if ((testCaseName.trim().equalsIgnoreCase(methodName))&& (testCaseBrowser.trim().equalsIgnoreCase(browser))) {								
									HSSFCell cell1= null;
									if(null!=rowPointer.getCell(9)){
										cell1 = rowPointer.getCell(9);
									}else{
										cell1 = rowPointer.createCell(9);
									}
									cell1.setCellValue(screenShotResults+indImgName);
								}
							} 
						}
					}

				}
			}

			wb.write(out);
			out.close();

			//System.out.println("FinalResults.xls file generated sucessfully");
		}catch (Exception e){
			System.out.println(e);

		}

	}


	private static void createExcelFileData() throws Exception {
		// TODO Auto-generated method stub
		try {

			hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("Final Results");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("TestcaseID");
			rowhead.createCell(1).setCellValue("Status");
			rowhead.createCell(2).setCellValue("TestcaseName");
			rowhead.createCell(3).setCellValue("Feature");
			rowhead.createCell(4).setCellValue("BrowserName");
			rowhead.createCell(5).setCellValue("MachineName");
			rowhead.createCell(6).setCellValue("UserName");
			rowhead.createCell(7).setCellValue("Password");
			rowhead.createCell(8).setCellValue("FailedstepDetails");
			rowhead.createCell(9).setCellValue("ScreenshotPath");
			rowhead.createCell(10).setCellValue("StartTime");
			rowhead.createCell(11).setCellValue("EndTime");

			// create a DOMParser
			DOMParser parser = new DOMParser();
			//parser.parse("C:\\workspace\\cpcatalyst_selenium\\resources\\output\\testng-results-final.xml");
			parser.parse(inputXMLFile);

			// get the DOM Document object
			Document doc = parser.getDocument();

			// getting "Test Method" tag
			NodeList nodelist = doc.getElementsByTagName("test-method");
			//System.out.println("no. of test methods are in xml file is : "+ nodelist.getLength());
			for (int len = 0; len < nodelist.getLength(); len++) {
				//getting the nodelist for each "Test Method"
				//System.out.println("*****Running Test Method*****: "+ len);

				HSSFRow row = sheet.createRow((short) len+1);
				Node node = nodelist.item(len);

				NamedNodeMap attrs = node.getAttributes();

				String name = (String) attrs.getNamedItem("name").getNodeValue();
				//System.out.println("Name of the test case :"+ name +" for the row  :"+ len);
				row.createCell(2).setCellValue(name);
				String startedTime = (String) attrs.getNamedItem("started-at").getNodeValue();
				row.createCell(10).setCellValue(startedTime);

				String FinishedTime = (String) attrs.getNamedItem("finished-at").getNodeValue();
				row.createCell(11).setCellValue(FinishedTime);

//				if (name.equalsIgnoreCase("loginTest")) { break; }
//				if (name.equalsIgnoreCase("tearDown")) { break; }
				String status = (String) attrs.getNamedItem("status").getNodeValue();
				row.createCell(1).setCellValue(status + "ED");
				if (status.equalsIgnoreCase("fail")) {
					//NodeList fstacktrace = doc.getElementsByTagName("message");
					NodeList exceptionNodeList = node.getChildNodes();
					String enNodeName = exceptionNodeList.item(3).getNodeName();
					if (enNodeName.equalsIgnoreCase("exception")){
						NodeList messageNodeList = exceptionNodeList.item(3).getChildNodes();
						String excepMessage = messageNodeList.item(1).getTextContent();
						row.createCell(8).setCellValue(excepMessage.trim());
					}
														
				}

				NodeList featurename = doc.getElementsByTagName("class");		
				Node node1 = featurename.item(len);
				NamedNodeMap attrs1 = node1.getAttributes();
				String feature = (String) attrs1.getNamedItem("name").getNodeValue();

				String[] fea;
				fea = feature.split("com.gurukulam.test.");
				String s = fea[1];
				row.createCell(3).setCellValue(s);

				NodeList testMethodChildNodes = nodelist.item(len).getChildNodes();
				NamedNodeMap attr = nodelist.item(len).getAttributes();
				//System.out.println("attribtes are in Test Method :"+attr.getLength());
				//String testCaseName =  attr.getNamedItem("name").getTextContent();
				//System.out.println("&&&Name of the test case&&& :"+testCaseName);

				for (int cn=0; cn<testMethodChildNodes.getLength(); cn++){							
					NodeList paramList = testMethodChildNodes.item(cn).getChildNodes();				
					String cnNodename = testMethodChildNodes.item(cn).getNodeName();
					//nodename is equal to params	
					if (cnNodename.equalsIgnoreCase("params")){
						for (int cnparam=0; cnparam<paramList.getLength(); cnparam++){													
							String pramName = paramList.item(cnparam).getNodeName();	
							//Nodename equal to Param
							if (pramName.equalsIgnoreCase("param")){
								NamedNodeMap ParamAttr = paramList.item(cnparam).getAttributes();
								//System.out.println("Attributes in Param tag :"+ ParamAttr.getLength());
								String	 IndexVal = ParamAttr.getNamedItem("index").getTextContent();
								//System.out.println("Param Index Value is :"+ IndexVal);
								String ParamValue = paramList.item(cnparam).getTextContent();
								//System.out.println("The value of the param is  :"+ ParamValue);
								int IndexNum =Integer.parseInt(IndexVal);
								switch(IndexNum)
								{
								//the choice go here
								case 0:
									row.createCell(4).setCellValue(ParamValue.trim());
								case 1:
									row.createCell(5).setCellValue(ParamValue.trim());
								case 3:
									row.createCell(6).setCellValue(ParamValue.trim());
								case 4:
									row.createCell(7).setCellValue(ParamValue.trim());
								}

								//System.out.println("------------------------------");

							}					

						}	
					}
				}

				for (int i1 = 0; i1 < sheet.getPhysicalNumberOfRows(); i1++) {

					String cell = row.getCell(2).getStringCellValue();


					try {
						FileInputStream driverFile = new FileInputStream(inputExcelFile);

						POIFSFileSystem driverFileSystem = new POIFSFileSystem(driverFile);

						HSSFWorkbook driverWorkBook = new HSSFWorkbook(driverFileSystem);

						HSSFSheet driverSheet = driverWorkBook.getSheetAt(0);

						for (int j = 0; j < driverSheet.getPhysicalNumberOfRows(); j++) {
							HSSFRow driverRow = driverSheet.getRow(j);
							String s1 = driverRow.getCell(2).getStringCellValue();

							if (s1.equalsIgnoreCase(cell)) {
								int s2 = (int) (driverRow.getCell(0).getNumericCellValue());
								row.createCell(0).setCellValue(s2);
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			FileOutputStream fileOut = new FileOutputStream(fileToScreenShot);
			hwb.write(fileOut);	
			fileOut.close();

		} catch (Exception ex) {
			System.out.println(ex);

		}
	}

}


