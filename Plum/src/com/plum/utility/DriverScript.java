/*
 *************************************************************************************************************
'Script Name		:	DriverScript 
'Script Description	:	To set automation suite(test cases, Browser, Machines, Userid's) input details.
'Input Parameters	:	None
'Return Value		:	
'Prerequisites		:	Driver-fullData.xls should be present at specified location.
'Author				:	Sathya & Jagadeesh
'Reviewed By		:
'Date Created		:	24 August 2012
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class DriverScript {

	static FileInputStream io=null;
	static FileOutputStream out =null;						
	final static String inputFileName = "C:\\Workspace\\catalyst_selenium\\resources\\input\\Driver-fullData.xls";
	static String outputFileName = "C:\\Workspace\\catalyst_selenium\\resources\\input\\Driver.xls";
	static int sSuiteTypeCellNumber=4;
	static int featureCellNumber=5;
	static int sEnvCellNumber = 0;
	static int sBrowserCellNumber = 1;
	static int sBrowserCellNumberIE10 = 2;
	static int sBrowserCellNumberIE11 = 3;
	static String suiteType = null;
	static String feature=null;
	static boolean machineHeadingStart = false;
	static boolean machineHeadingStartIE10 = false;//mine
	static boolean machineHeadingStartIE11 = false;
	static String machineNames = "";
	static String machineNamesIE10 = "";// test
	static String machineNamesIE11 = "";
	static boolean environmentHeadingStart = false;
	static String userIds_passwords = "";
	static String userIds_passwordsIE10 = "";
	static String userIds_passwordsIE11 = "";
	static String URL = null;
	static int i=1;
	static int j=1;
	static int k=1;
	static int rowCount=1;
	static int machineCount=0;
	static int machineCountIE10=0;
	static int machineCountIE11=0;
	static int mc=0;
	static int mcIE10=0;
	static int mcIE11=0;
	static int userIdPassIndex=0;
	static boolean breakDestribution = false;
	static int rowCount3 = 1;
	static int rowNumberSheet1=1;
	static int rowNumberSheet3=1; 
	static FileInputStream fs;
	static Workbook wb;     
	static Row row;
	static Cell cell;
	static Cell cellIE10;//Udita
	static Cell cellIE11;
	static Row row1;
	static Cell cell1;
	static Row row11;
	static Cell cell11;
	static Row row2;
	static Cell cell2;
	static Cell cell3;
	static Cell cell4;
	static Cell cell5;
	static Cell cell6;
	static Cell cell7;
	static Cell cell8;
	//    static Cell cellU;
	//    static Cell cellP;
	//    static Cell cellU2;
	//    static Cell cellP2;
	static int orgMachineCount=0;
	static int orgMachineCountIE10=0;//mine
	static int orgMachineCountIE11=0;
	static int actualMachineCount=0;
	static int numberOfUsersPerMachine=0;
	static int numberOfUsersPerMachineIE10=0;
	static int numberOfUsersPerMachineIE11=0;
	static HSSFSheet sheet1;
	static HSSFSheet sheet2;
	static HSSFSheet sheet3;
	static HSSFSheet sheet4;
	private static final String ALGO = "AES";
	private static final byte[] keyValue = 
			new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
		'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };


	//	public static String encrypt(String Data) throws Exception {
	//		Key key = generateKey();
	//		Cipher c = Cipher.getInstance(ALGO);
	//		c.init(Cipher.ENCRYPT_MODE, key);
	//		byte[] encVal = c.doFinal(Data.getBytes());
	//		String encryptedValue = new BASE64Encoder().encode(encVal);
	//		return encryptedValue;
	//	}

	//	public static String decrypt(String encryptedData) throws Exception {
	//		Key key = generateKey();
	//		Cipher c = Cipher.getInstance(ALGO);
	//		c.init(Cipher.DECRYPT_MODE, key);
	//		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
	//		byte[] decValue = c.doFinal(decordedValue);
	//		String decryptedValue = new String(decValue);
	//		return decryptedValue;
	//	}
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}



	public static boolean funcPingSingleMachine(String host) {
		try{
			
			Process myProcess = Runtime.getRuntime().exec("ping -n 1 " + host);
			
			Thread.sleep(7000);
			

			if (!(myProcess.waitFor()==0)){
				Thread.sleep(7000);

			}
			


			if(myProcess.exitValue() == 0) {				
				myProcess.destroy();
				return true;
			} else {
				myProcess.destroy();
				return false;
			}


		} catch( Exception e ) {
			e.printStackTrace();
			return false;

		}
	}

	public static int funPingMachniesbrowserwise(String strStartBroValue,String strEndBroValue){
		
		
		String ipaddress;

		int Lstrow  = sheet2.getLastRowNum();
		//System.out.println("The total no of rows in Driver script - sheet 2: "+Lstrow);
		//log("The total no of rows in Driver script sheet 2: "+Lstrow);
		for (int i = 0; i < Lstrow; i++) {
			row1   = sheet2.getRow(i);
			cell1  = row1.getCell(0);
			//System.out.println(cell1);
			//cell1.getCellType()==1
			if (cell1 != null) {
				if (cell1.getCellType()==1) {
					if (cell1.getStringCellValue().trim().equalsIgnoreCase(strStartBroValue)) {
						for (int k = 1; k <= Lstrow-i	 ; k++) {
							row   = sheet2.getRow(k+i);
							cell  = row.getCell(0);
							if (cell != null) {
								if (cell.getCellType()==1) {
									if ((cell.getStringCellValue().trim().equalsIgnoreCase(strEndBroValue))){
										break;
									}else{
										orgMachineCount += 1;
										ipaddress=cell.getStringCellValue().trim();
										//System.out.println(ipaddress);
										//InetAddress adr = InetAddress.getByName(ipaddress);
										boolean blMachStatus=funcPingSingleMachine(ipaddress);
										//System.out.println(blMachStatus);
										System.out.println("Machine "+ipaddress+" is reachable? "+blMachStatus);
										//log("Machine "+ipaddress+" is reachable: "+adr.isReachable(3000));
										if (blMachStatus == false) {
											System.out.println("Machine "+ipaddress+" is NOT reachable, hence removing from the list");
											sheet2.shiftRows(k+i+1, Lstrow, -1);
											--k;
											--Lstrow;
											//                                                                      fileOut = new FileOutputStream(filename);
											//                                                            wb.write(fileOut);                                                                        
											//                                                            fileOut.close();
										}
									}
								}
							}
						}
						break;
					}
				}
			}
		}
				
		return orgMachineCount;
	}
	public static int funPingMachines() throws Exception{

		String ipaddress;

		if (inputFileName != null && !inputFileName.equals("")) {
			try{
										
						funPingMachniesbrowserwise("Machine Names","Machine Names IE10");
																	
						funPingMachniesbrowserwise("Machine Names IE10","Machine Names IE11");
						
						funPingMachniesbrowserwise("Machine Names IE11","");
							
						
			}catch(Exception e){
				System.out.println(e);

			}
			/*return orgMachineCount;*/
		}
		else{
			System.out.println("driver.xls file does not exist hence stopping the run");
			System.exit(-1);
		}
		return orgMachineCount+orgMachineCountIE10+orgMachineCountIE11;

	}


	public static int funTasklist(String machineName, String uname, String pwd){
		int iflag=0;
		try {  
			Process pr = Runtime.getRuntime().exec("tasklist /S "+machineName+" /u "+uname+" /p "+pwd);  
			BufferedReader br =  
					new BufferedReader(new InputStreamReader(pr.getInputStream()));  
			String temp = br.readLine();  
			while (temp != null) {  
				//System.out.println(""+temp);  
				if (temp.indexOf("java") > -1) {  
					//System.out.println(" found");  
					//break;  
					iflag+=1;
				}  
				temp = br.readLine();  
			}  
			System.out.println("Java files (Count) running in the machine-"+machineName+" is: " +iflag);

		} catch (Exception e) {  
		}
		return iflag;  
	}
	
	
	public static String funCheckRebootStatusbrowserwise(String strStartBroValue ,String strEndBroValue){
		
		String ipaddress1;
		String text1 = "";
		
		int Lstrow1  = sheet2.getLastRowNum();
		//System.out.println(Lstrow);
		for (int j = 0; j < Lstrow1; j++) {
			row11   = sheet2.getRow(j);
			cell11  = row11.getCell(0);
			//System.out.println(cell1);
			//cell1.getCellType()==1
			if (cell11 != null) {
				if (cell11.getCellType()==1) {
					if (cell11.getStringCellValue().trim().equalsIgnoreCase(strStartBroValue)) {
						for (int l = 1; l <= Lstrow1-j ; l++) {
							row2   = sheet2.getRow(l+j);
							cell2  = row2.getCell(0);
							if (cell2 != null) {
								if (cell2.getCellType()==1) { 
									if ((cell2.getStringCellValue().trim().equalsIgnoreCase(strEndBroValue))){
										break;
									}else{
									ipaddress1=cell2.getStringCellValue().trim();
									System.out.println(ipaddress1);
									//commented by rizwana

									boolean strMachStatus1=funcPingSingleMachine(ipaddress1);
									//System.out.println(strMachStatus1);

									//InetAddress adr = InetAddress.getByName(ipaddress1);
									//System.out.println("Machine "+ipaddress1+" is reachable? "+strMachStatus1);

									//log("Machine "+ipaddress1+" is reachable: "+adr.isReachable(3000));
									if (strMachStatus1 == false) {
										//System.out.println("Machine "+ipaddress+" is reachable: "+adr.isReachable(3000));
										sheet2.shiftRows(l+j+1, Lstrow1, -1);
										--l;
										--Lstrow1;
										//                                                                            fileOut = new FileOutputStream(filename);
										//                                                                  wb.write(fileOut);                                                                              
										//                                                                  fileOut.close();
									}else{
										if (funTasklist(ipaddress1,"svctrtacpqa","Thomson1") == 2) {
											actualMachineCount += 1;
											text1=text1+"C:\\Workspace\\catalyst_selenium\\resources\\input\\xml\\"+ipaddress1+".xml ";
										}else{
											sheet2.shiftRows(l+j+1, Lstrow1, -1);
											--l;
											--Lstrow1;
											//                                                                                  fileOut = new FileOutputStream(filename);
											//                                                                        			wb.write(fileOut);                                                                                    
											//                                                                        			fileOut.close();
											}

											}

									}
											}
											}

										}

						break;
					}

				}     
			}
		}
		return text1;

		
	}
	
	public static void funCheckRebootStatus(int orgMachCount) throws Exception{
//String ipaddress1;
		String text = "";

		/* commented by sathya
		for (int m = 0; m < 10; m++) {
                  Thread.sleep(15000);
                  System.out.println("Waiting for the machines to reboot - 15000msec...");
            }*/
		try {
			
			System.out.println("******Checking the machines reboot status******");
			 if (inputFileName != null && !inputFileName.equals("")) {
				 
				 
				String text1= funCheckRebootStatusbrowserwise("Machine Names" ,"Machine Names IE10");
				
							
				String text2= funCheckRebootStatusbrowserwise("Machine Names IE10" ,"Machine Names IE11");
				
								
				String text3= funCheckRebootStatusbrowserwise("Machine Names IE11" ,"");
				
				text=text1+text2+text3;
				
				System.out.println(orgMachCount +" "+actualMachineCount);
				if (((float)orgMachCount/2) >= actualMachineCount) {
					//send email
					//exit
					System.out.println("More than or equal to 50% of the machines are down, Hence stopped the batch run and sending an email. Please check the machines.");
					// funcSendEmial("Selenium:: Less than 50% of the machines are down", "Less than 50% of the machines are down, Hence stopped the batch run. Please check the machines");
					System.exit(0);
				}else if ((((float)orgMachCount*2)/3) >= actualMachineCount) {
					//send email
					System.out.println("30% - 50% of the machines are down, Continuing the batch run and sending an email. Please check the machines");
					//funcSendEmial("Selenium:: 30% - 50% of the machines are down", "30% - 50% of the machines are down, Continuing the batch run. Please check the machines");
				}

				//System.out.println("Adding this data into testng.bat file: ");
				//System.out.println(text);
				String strTestNg = "c: && cd C:\\Workspace\\catalyst_selenium\\lib && java -cp testng-6.9beta.jar;test.jar;eclipselink.jar;jxl.jar;log4j-1.2.15.jar;selenium-server-standalone-2.41.0.jar; org.testng.TestNG -suitethreadpoolsize "+actualMachineCount+" "+text;
				Writer output = null;

				File file = new File("C:\\Workspace\\catalyst_selenium\\bat\\testng.bat");
				output = new BufferedWriter(new FileWriter(file));
				output.write(strTestNg);
				output.close();
				System.out.println("Driver.xls file has been written successfully");  
				//log("Your file has been written");    
			


			 }
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	//	public static void funcSendEmial(String strSubj, String strBody) throws Exception{
	//		try {
	//			String MailFrom = "jagadeeswar.viswanatam@thomsonreuters.com";
	//			String MailTo = "jagadeeswar.viswanatam@thomsonreuters.com,sreekumar.n@thomsonreuters.com";
	//			String MailSubject = strSubj;
	//			String MailBody = strBody;
	//			Runtime.getRuntime().exec("cmd /c C:\\Selenium\\SendMail\\sendEmail -f "+MailFrom+" -t "+MailTo+" -s TLRINAPHYDMBX01.ERF.THOMSON.COM -xu u0134309 -xp "+decrypt("SNBzqu3iRcPvA4lgmOlnCw==")+" -u "+MailSubject+" -m "+MailBody);
	//		} catch (Exception e) {
	//			// TODO: handle exception
	//			System.out.println("Exception thrown in SendEmail Function:  "+e);
	//		}
	//
	//	}

	public static void updateXLS() 
	{ 
		int noOfCasesforMachineIE10=0;
		int noOfCasesforMachine = 0;
		int noOfCasesforMachineIE11=0;
		
		try 
		{                 
			//                POIFSFileSystem poiFS=new POIFSFileSystem(io); 
			//                HSSFWorkbook wb=new HSSFWorkbook(poiFS);              
			//                HSSFSheet sheet1=wb.getSheetAt(0);              
			//                HSSFSheet sheet2=wb.getSheetAt(1);
			//                HSSFSheet sheet3=wb.getSheetAt(2);
			//                HSSFSheet sheet4=wb.getSheetAt(3);
			HSSFRow rowSheet4=sheet4.getRow(1);            

			int numberOfRowsSheet2 = sheet2.getPhysicalNumberOfRows();
			suiteType = rowSheet4.getCell(sSuiteTypeCellNumber).getStringCellValue();
			if(null!=rowSheet4.getCell(sSuiteTypeCellNumber)){
			}


			if(null!=rowSheet4.getCell(featureCellNumber)){
				feature=rowSheet4.getCell(featureCellNumber).getStringCellValue();
			}
			String envValue = rowSheet4.getCell(sEnvCellNumber).getStringCellValue();
			String BrowserValue = rowSheet4.getCell(sBrowserCellNumber).getStringCellValue();
			String BrowserValueIE10 = rowSheet4.getCell(sBrowserCellNumberIE10).getStringCellValue();
			String BrowserValueIE11 = rowSheet4.getCell(sBrowserCellNumberIE11).getStringCellValue();
			

			// For e getting Machines names from Sheet2
			String machineNamesArray[]=getMachineNames(sheet2,numberOfRowsSheet2);
			//End

			String machineNamesArrayIE10[]=getMachineNamesIE10(sheet2,numberOfRowsSheet2);//mine
			
			String machineNamesArrayIE11[]=getMachineNamesIE11(sheet2,numberOfRowsSheet2);
			//System.out.println(machineNamesArrayIE10.length);

			//String totalmachineNamesArray[] = machineNamesArray[] + machineNamesArrayIE10[];

			//String [] totalmachineNamesArray = ObjectArrays.concat(machineNamesArray, machineNamesArrayIE10, String.class);

			// For f getting UserIds and Pass from Sheet2
			String userIdPassArray[] = getUidPasswords(sheet2,numberOfRowsSheet2,envValue);    
			
			
			
			String userIdPassArrayIE10[] = getUidPasswordsIE10(sheet2,numberOfRowsSheet2,envValue); 
			
			String userIdPassArrayIE11[] = getUidPasswordsIE11(sheet2,numberOfRowsSheet2,envValue); 
			// End
			int remainingRowCount=sheet1.getLastRowNum();
			if(userIdPassArray.length >= 1 && machineNamesArray.length >=1){
			numberOfUsersPerMachine = userIdPassArray.length/machineNamesArray.length;
			noOfCasesforMachine=(remainingRowCount-1)/machineNamesArray.length;
			}
			
			
			// Get Environment URL From Sheet2
			String URL = getEnvURL(sheet2,envValue);
			//End       

			int nubmerofrows=sheet1.getPhysicalNumberOfRows();
			
			// Filter TestCases based on SuiteType and Feature
			
			sortTestCases(sheet1,nubmerofrows,suiteType);
			
			//Updated Rizwana
			
			sortTestCasesonFeature(sheet1,nubmerofrows,feature);
			//End 

			int remainingRowCountDel=sheet1.getLastRowNum();
			
			// update Sheet1 with Environment,Browser and URL values
			updateEnvBrowserURL(sheet1,envValue,BrowserValue,BrowserValueIE10,BrowserValueIE11,URL,remainingRowCountDel);
			//End

			//For calculating no.of test case for each machines
			//if(userIdPassArray.length >= 1 && machineNamesArray.length >=1){
			//int noOfCasesforMachine=(remainingRowCount-1)/machineNamesArray.length;
			// End 
			

			
			//7th May 2014   Udita and Manasa  - condition for Verifying Machine NamesIE10 and Users Name and Password for IE10 columns are not blank
			if(userIdPassArrayIE10.length>1 && machineNamesArrayIE10.length>=1)
			{
				numberOfUsersPerMachineIE10 = userIdPassArrayIE10.length/machineNamesArrayIE10.length;
				noOfCasesforMachineIE10 = (remainingRowCountDel-1)/machineNamesArrayIE10.length;
			}
			
			// End - condition for Verifying Machine NamesIE10 and Users Name and Password for IE10 columns are not blank
			
			
			if(userIdPassArrayIE11.length>1 && machineNamesArrayIE11.length>=1)
			{
				numberOfUsersPerMachineIE11 = userIdPassArrayIE11.length/machineNamesArrayIE11.length;
				noOfCasesforMachineIE11 = (remainingRowCountDel-1)/machineNamesArrayIE11.length;
			}
			

			//Allotment of Machines and Users
			allotMachinesAndUsers(sheet1,machineNamesArrayIE10,machineNamesArrayIE11,machineNamesArray,userIdPassArray,userIdPassArrayIE10,userIdPassArrayIE11,noOfCasesforMachine,noOfCasesforMachineIE10,noOfCasesforMachineIE11,remainingRowCountDel);//mine
			
		
			
			// End
			int numberOfRowsSheet3=sheet3.getLastRowNum();
			//Update TestCases with specificUID ,specificPassword and specificURL from sheet3
		updateSpecificUIDPassURL(sheet1,sheet3,remainingRowCountDel,numberOfRowsSheet3,URL);               
			//End
			//                wb.write(out);
		//}
		}catch(Exception iox) 
		{ 
			iox.printStackTrace() ; 
		}
	} 




	public static String[] getMachineNames(HSSFSheet sheet2,int numberOfRowsSheet2){                



		for(int s2RowCount=0;s2RowCount<numberOfRowsSheet2;s2RowCount++){
			HSSFRow s2Row = sheet2.getRow(s2RowCount);
			try{
				if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Machine Names")){
					machineHeadingStart = true;
					continue;
				}
				if(machineHeadingStart){
					if (s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Machine Names IE10")){
						break;
					}else{
					machineNames = machineNames+ s2Row.getCell(0).getStringCellValue()+",";         
					}
				}
			}catch(Exception e){
				continue;
			}
		}
		String machineNamesArray[]=machineNames.split(",");
		return machineNamesArray;
	}

	//6th May 2014   Udita and Manasa  - Function for fetching Machine names of IE10
	public static String[] getMachineNamesIE10(HSSFSheet sheet2,int numberOfRowsSheet2)
	{
		//String machineNamesArrayIE10[];
		for(int s2RowCount=0;s2RowCount<numberOfRowsSheet2;s2RowCount++){
			HSSFRow s2Row = sheet2.getRow(s2RowCount);
			try{
				
				if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Machine Names IE10")){
					machineHeadingStartIE10 = true;
					continue;
				}
				if(machineHeadingStartIE10){
					if (s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Machine Names IE11")){
						break;
					}else{
						machineNamesIE10  = machineNamesIE10 + s2Row.getCell(0).getStringCellValue()+",";
					}
					//System.out.println(machineNamesIE10.length());
					if(machineNamesIE10.length()>=1)
					{
						continue;
					}
					else
					{
						break;
					}
				}
				
				
			}catch(Exception e){
				continue;
			}
		}
		
		String machineNamesArrayIE10[]=machineNamesIE10.split(",");
		return machineNamesArrayIE10;
		


	}
	
	//END - Function for fetching Machine names of IE10
	

	
	public static String[] getMachineNamesIE11(HSSFSheet sheet2,int numberOfRowsSheet2)
	{
		
		for(int s2RowCount=0;s2RowCount<numberOfRowsSheet2;s2RowCount++){
			HSSFRow s2Row = sheet2.getRow(s2RowCount);
			try{
				//System.out.println(s2Row.getCell(0).getStringCellValue());
				if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Machine Names IE11")){
					machineHeadingStartIE11 = true;
					continue;
				}
				if(machineHeadingStartIE11){
					machineNamesIE11  = machineNamesIE11 + s2Row.getCell(0).getStringCellValue()+",";    
					//System.out.println(machineNamesIE10.length());
					if(machineNamesIE11.length()>=1)
					{
						continue;
					}
					else
					{
						break;
					}
				}
				
				
			}catch(Exception e){
				continue;
			}
		}
		
		String machineNamesArrayIE11[]=machineNamesIE11.split(",");
		return machineNamesArrayIE11;
		


	}
	
	public static String[] getUidPasswords(HSSFSheet sheet2,int numberOfRowsSheet2,String envValue){

		for(int s2RowCount=0;s2RowCount<numberOfRowsSheet2;s2RowCount++){
			HSSFRow s2Row = sheet2.getRow(s2RowCount);
			try{
				if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Environment")){
					environmentHeadingStart = true;
					continue;
				}
				if(environmentHeadingStart){
					if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase(envValue)){
						userIds_passwords = s2Row.getCell(1).getStringCellValue();
						break; 
						
						
					}                             
				}
			}catch(Exception e){
				continue;
			}
		}
		String userIdPassArray[]=userIds_passwords.split("==");
		return userIdPassArray;
	}
	
	// 6th May 2014   Udita and Manasa  - Function for fetching User ID passwords of IE10
	public static String[] getUidPasswordsIE10(HSSFSheet sheet2,int numberOfRowsSheet2,String envValue){

		for(int s2RowCount=0;s2RowCount<numberOfRowsSheet2;s2RowCount++){
			HSSFRow s2Row = sheet2.getRow(s2RowCount);
			try{
				if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Environment")){
					environmentHeadingStart = true;
					continue;
				}
				if(environmentHeadingStart){
					if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase(envValue)){
						userIds_passwordsIE10 = s2Row.getCell(2).getStringCellValue();
						//break; //commented by manasa 
						if(userIds_passwordsIE10.length()>=1)
						{
							continue;
						}
						else
						{
							break;
						}
					}                             
				}
			}catch(Exception e){
				continue;
			}
		}
		String userIdPassArrayIE10[]=userIds_passwordsIE10.split("==");
		return userIdPassArrayIE10;
	}
	
	//END - Function for fetching User ID passwords of IE10
	
	public static String[] getUidPasswordsIE11(HSSFSheet sheet2,int numberOfRowsSheet2,String envValue){

		for(int s2RowCount=0;s2RowCount<numberOfRowsSheet2;s2RowCount++){
			HSSFRow s2Row = sheet2.getRow(s2RowCount);
			try{
				if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase("Environment")){
					environmentHeadingStart = true;
					continue;
				}
				if(environmentHeadingStart){
					if(s2Row.getCell(0).getStringCellValue().equalsIgnoreCase(envValue)){
						userIds_passwordsIE11 = s2Row.getCell(3).getStringCellValue();
						
						if(userIds_passwordsIE11.length()>=1)
						{
							continue;
						}
						else
						{
							break;
						}
					}                             
				}
			}catch(Exception e){
				continue;
			}
		}
		String userIdPassArrayIE11[]=userIds_passwordsIE11.split("==");
		return userIdPassArrayIE11;
	}

	public static String getEnvURL(HSSFSheet sheet2,String envValue){

		for (int k=1; k<=6; k++){
			HSSFRow rowVal = sheet2.getRow(k);
			String EnvValinSheet2 = rowVal.getCell(0).getStringCellValue();
			if (EnvValinSheet2.equals(envValue)){
				URL = rowVal.getCell(1).getStringCellValue();
				break;
			}
		}
		return URL;
	}

	public static void sortTestCases(HSSFSheet sheet1, int nubmerofrows,String suiteType){

		while(i<nubmerofrows){
			HSSFRow row=sheet1.getRow(i);
			String suiteTypeCellValue = row.getCell(3).getStringCellValue();
			
			if(!(suiteType=="")){                        
				if(!(suiteTypeCellValue.equals(suiteType))){
					sheet1.shiftRows(i+1,nubmerofrows,-1);
					i=i-1;
					nubmerofrows = nubmerofrows-1;
				}
					}
			i++;
			
		}     
	}
	
	
public static void sortTestCasesonFeature(HSSFSheet sheet1, int nubmerofrows,String feature){
					
		if(!(feature=="")){ 
						
			if (feature.contains(";"))
			{
									
			String strFeatureArray[]= feature.split(";");
			
			String StrTemp="";		
			
						
			for(String inFeature:strFeatureArray){	
				
				StrTemp=StrTemp+inFeature;
			}
				
				while(k<nubmerofrows){
					
					HSSFRow row=sheet1.getRow(k);
					
					String featureTypeCellValue = row.getCell(7).getStringCellValue();
									
											
				if(!(StrTemp.contains(featureTypeCellValue))){
														
					sheet1.shiftRows(k+1,nubmerofrows,-1);
					k=k-1;
					nubmerofrows = nubmerofrows-1;
									
								
			}
				
					k++;
		}     
				
					
			}
			
		else{
			while(k<nubmerofrows){	
				
				HSSFRow row=sheet1.getRow(k);
				
				String featureTypeCellValue = row.getCell(7).getStringCellValue();
				
			if(!(featureTypeCellValue.equals(feature))){
				sheet1.shiftRows(k+1,nubmerofrows,-1);
				k=k-1;
				nubmerofrows = nubmerofrows-1;
			}
			
			k++;
	}     
}
		}

		
	}

	public static void updateEnvBrowserURL(HSSFSheet sheet1,String envValue,String BrowserValue,String BrowserValueIE10,String BrowserValueIE11,String URL,int remainingRowCount){
		
		//updated Rizwana

		while(j<=remainingRowCount){
			
			HSSFRow row1=sheet1.getRow(j);
			
			HSSFCell TestcaseCell = row1.getCell(2);
			
			if ((null != TestcaseCell )){
			
			HSSFCell cell= null;
			if(null != row1.getCell(4)){
				cell= row1.getCell(4);  
			}else{
				cell= row1.createCell(4);
			}                       
			cell.setCellValue(envValue);
			HSSFCell cell1= null;
			if(null != row1.getCell(5)){
				cell1= row1.getCell(5);
			}else{
				cell1= row1.createCell(5);
			}                       
			cell1.setCellValue(BrowserValue);
			HSSFCell cell3= null;//Creating cell for IE10 browser
			if(null != row1.getCell(18)){
				cell3= row1.getCell(18);
			}else{
				cell3= row1.createCell(18);
			}                       
			cell3.setCellValue(BrowserValueIE10);//Ends
			
			HSSFCell cell11= null;//Creating cell for IE10 browser
			if(null != row1.getCell(22)){
				cell11= row1.getCell(22);
			}else{
				cell11= row1.createCell(22);
			}                       
			cell11.setCellValue(BrowserValueIE11);
			
			HSSFCell cell2= null;
			if(null!=row1.getCell(12)){
				cell2= row1.getCell(12);
			}else{
				cell2= row1.createCell(12);
			}
			cell2.setCellValue(URL);
			
			}
			j++;
		
		}
	}

	public static void allotMachinesAndUsers(HSSFSheet sheet1,String[] machineNamesArrayIE10,String[] machineNamesArrayIE11,String[] machineNamesArray,String[] userIdPassArray,String[] userIdPassArrayIE10,String[] userIdPassArrayIE11,int noOfCasesforMachine,int noOfCasesforMachineIE10,int noOfCasesforMachineIE11,int remainingRowCount){
		int userCount=0;
		int userCountIE10=0;
		int userCountIE11=0;
		String userMachineArray[][] = new String[machineNamesArray.length][numberOfUsersPerMachine];
		String userMachineArrayIE10[][] = new String[machineNamesArrayIE10.length][numberOfUsersPerMachineIE10];
		String userMachineArrayIE11[][] = new String[machineNamesArrayIE11.length][numberOfUsersPerMachineIE11];
		
		int userIndex=0;
		int userIndexIE10=0;
		int userIndexIE11=0;
		for(int i=0;i<machineNamesArray.length;i++){
			for(int j=0;j<numberOfUsersPerMachine;j++){
				userMachineArray[i][j]=userIdPassArray[userIndex];
				//System.out.println(userMachineArray[i][j]);
				userIndex++;
			}
		}
		
		// 8th May 2014   Udita and Manasa  - Allotting user ID and passwords to IE10 Machines
		for(int i=0;i<machineNamesArrayIE10.length;i++){
			for(int j=0;j<numberOfUsersPerMachineIE10;j++){
				userMachineArrayIE10[i][j]=userIdPassArrayIE10[userIndexIE10];
				//System.out.println(userMachineArrayIE10[i][j]);
				userIndexIE10++;
			}
		}
		
		//END - Allotting user ID and passwords to IE10 Machines
		
		
		for(int i=0;i<machineNamesArrayIE11.length;i++){
			for(int j=0;j<numberOfUsersPerMachineIE11;j++){
				userMachineArrayIE11[i][j]=userIdPassArrayIE11[userIndexIE11];
				//System.out.println(userMachineArrayIE10[i][j]);
				userIndexIE11++;
			}
		}
		
		while(rowCount<=remainingRowCount){ 
						
			HSSFRow row1=sheet1.getRow(rowCount);
									
			HSSFCell TestcaseCell = row1.getCell(2);
			
			if ((null != TestcaseCell )){
				
			HSSFCell cell= null;
			if(null!=row1.getCell(6)){
				cell = row1.getCell(6);
			}else{
				cell = row1.createCell(6);//Machine name
			}
			HSSFCell cell1= null;
			if(null!=row1.getCell(10)){
				cell1 = row1.getCell(10);
			}else{
				cell1 = row1.createCell(10);//username
			}
			HSSFCell cell2= null;
			if(null!=row1.getCell(11)){
				cell2 = row1.getCell(11);
			}else{
				cell2 = row1.createCell(11);//password
			}
			
		

			if(mc>=machineNamesArray.length ){
				mc=0;
				breakDestribution = true;
			} 
			
			if (machineNamesArray.length >=1){
			cell.setCellValue(machineNamesArray[mc]);
			//System.out.println(cell.getStringCellValue());
			cell1.setCellValue(userMachineArray[mc][userCount].split(";")[0]);
			cell2.setCellValue(userMachineArray[mc][userCount].split(";")[1]);
			
			
			machineCount++;
			userCount++;
			if(userCount==numberOfUsersPerMachine){
				userCount =0;
			}			
			if(machineCount==noOfCasesforMachine || remainingRowCount-1 < machineNamesArray.length || breakDestribution){
				mc++;
				machineCount=0;				
			}
			}
			//rowCount++;
			
			// 8th May 2014   Udita and Manasa  - Creating cells and setting the cell values for IE10 in sheet1
			if(machineNamesArrayIE10.length>=1)
			{

				HSSFCell cell3= null;
				if(null!=row1.getCell(15)){
					cell3 = row1.getCell(15);
				}else{
					cell3 = row1.createCell(15);//IE10 Machine name
				}
				HSSFCell cell4= null;
				if(null!=row1.getCell(16)){
					cell4 = row1.getCell(16);
				}else{
					cell4 = row1.createCell(14);//IE10 User name
				}
				HSSFCell cell5= null;
				if(null!=row1.getCell(17)){
					cell5 = row1.getCell(17);
				}else{
					cell5 = row1.createCell(17);//IE10 Password
				}

				cell3.setCellValue(machineNamesArrayIE10[mcIE10]);
				//System.out.println(cell3.getStringCellValue());
				cell4.setCellValue(userMachineArrayIE10[mcIE10][userCountIE10].split(";")[0]);
				cell5.setCellValue(userMachineArrayIE10[mcIE10][userCountIE10].split(";")[1]);
				machineCountIE10++;
				userCountIE10++;
				if(userCountIE10==numberOfUsersPerMachineIE10){
					userCountIE10 =0;
				}			
				if(machineCountIE10==noOfCasesforMachineIE10 || remainingRowCount-1 < machineNamesArrayIE10.length || breakDestribution){
					mcIE10++;
					machineCountIE10=0;				
				}
				if(mcIE10>=machineNamesArrayIE10.length){
					mcIE10=0;
					//mc++;
					breakDestribution = true;
				}  
			}
			
			
			if(machineNamesArrayIE11.length>=1)
			{

				HSSFCell cell6= null;
				if(null!=row1.getCell(19)){
					cell6 = row1.getCell(19);
				}else{
					cell6 = row1.createCell(19);//IE11 Machine name
				}
				HSSFCell cell7= null;
				if(null!=row1.getCell(20)){
					cell7 = row1.getCell(20);
				}else{
					cell7 = row1.createCell(20);//IE11 User name
				}
				HSSFCell cell8= null;
				if(null!=row1.getCell(21)){
					cell8 = row1.getCell(21);
				}else{
					cell8 = row1.createCell(21);//IE11 Password
				}

				cell6.setCellValue(machineNamesArrayIE11[mcIE11]);
				//System.out.println(cell3.getStringCellValue());
				cell7.setCellValue(userMachineArrayIE11[mcIE11][userCountIE11].split(";")[0]);
				cell8.setCellValue(userMachineArrayIE11[mcIE11][userCountIE11].split(";")[1]);
				machineCountIE11++;
				userCountIE11++;
				if(userCountIE11==numberOfUsersPerMachineIE11){
					userCountIE11 =0;
				}			
				if(machineCountIE11==noOfCasesforMachineIE11 || remainingRowCount-1 < machineNamesArrayIE11.length || breakDestribution){
					mcIE11++;
					machineCountIE11=0;				
				}
				if(mcIE11>=machineNamesArrayIE11.length){
					mcIE11=0;
					//mc++;
					breakDestribution = true;
				}  
			}

			}
			//END - Creating cells and setting the cell values for IE10 in sheet1
			
			
			
			rowCount++;
		}
	}

	public static void updateSpecificUIDPassURL(HSSFSheet sheet1,HSSFSheet sheet3,int numberOfRowsSheet1,int numberOfRowsSheet3,String URL){

		String URLEnvArray[] = URL.split("\\.");
		int noOfRows = sheet3.getPhysicalNumberOfRows();

		while (rowCount3<noOfRows){
			HSSFRow row1=sheet3.getRow(rowCount3);
			HSSFCell cell= null;
			cell = row1.getCell(3);                         
			String existingURLValue = row1.getCell(3).getStringCellValue();
			String existingURLSplitVal[] = existingURLValue.split("\\.",2);
			String updatedURLValue = URLEnvArray[0]+"."+existingURLSplitVal[1];
			cell.setCellValue(updatedURLValue);
			rowCount3++;

		}     

		while(rowNumberSheet1<numberOfRowsSheet1){                            
			HSSFRow row1=sheet1.getRow(rowNumberSheet1);
			String tcNameSheet1=row1.getCell(2).getStringCellValue();

			while(rowNumberSheet3<numberOfRowsSheet3){
				HSSFRow row3=sheet3.getRow(rowNumberSheet3);
				if(null!=row3.getCell(0)){
					String tcNameSheet3=row3.getCell(0).getStringCellValue();
					if(tcNameSheet1.equalsIgnoreCase(tcNameSheet3)){
						HSSFCell userIdCell= row1.getCell(10);
						HSSFCell passwordCell= row1.getCell(11);
						HSSFCell URLCell= row1.getCell(12);
						String userId=null;
						String password=null;
						String specificURL=null;
						if(null!=row3.getCell(1)){
							userId=row3.getCell(1).getStringCellValue();
						}
						if(null!=row3.getCell(2)){
							password=row3.getCell(2).getStringCellValue();
						}
						if(null!=row3.getCell(3)){
							specificURL=row3.getCell(3).getStringCellValue();
						}
						if(null!=userId){
							userIdCell.setCellValue(userId);
						}
						if(null!=password){
							passwordCell.setCellValue(password);
						}
						if(null!=specificURL){
							URLCell.setCellValue(specificURL);
						}
						break;
					}
				}
				rowNumberSheet3++;
			}
			rowNumberSheet1++;
		}
}


	public static String DateFunction(){
		Date d = new Date();          
		String date = d.toString().replaceAll(" ", "_");
		date = date.replaceAll(":", "_");
		date = date.replaceAll("\\+", "_");
		return date;

	}

	
	

	public static void main(String[] args) throws Exception {


		try 
		{      
			//Runtime.getRuntime().exec("cmd /c start C:\\SeleniumHub\\startHubNode.bat");
			io=new FileInputStream(inputFileName);
			out = new FileOutputStream (outputFileName);                

			POIFSFileSystem poiFS=new POIFSFileSystem(io); 
			HSSFWorkbook wb=new HSSFWorkbook(poiFS);
			sheet1=wb.getSheetAt(0);                  
			sheet2=wb.getSheetAt(1);
			sheet3=wb.getSheetAt(2);
			sheet4=wb.getSheetAt(3);
			System.out.println("Driver Script started at: "+DateFunction());

	int orgCount=funPingMachines();
	System.out.println(orgCount);
	funCheckRebootStatus(orgCount);
			updateXLS();
			wb.write(out);                
			System.out.println("Driver script has completed at:  "+ DateFunction());

		}catch(FileNotFoundException fo) 
		{ 
			System.out.println("FileNotFoundException"+ fo.getMessage()); 
		}finally{
			try{
				io.close();
				out.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}
}


