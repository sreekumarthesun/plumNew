package com.plum.utility;


import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class XMLMerger {
	final static String inputFileName = "C:\\workspace\\catalyst_selenium\\resources\\input\\Driver.xls";
	
	public static void main(String[] args) throws Exception {
		FileInputStream io=new FileInputStream(inputFileName);
		POIFSFileSystem poiFS=new POIFSFileSystem(io); 
		HSSFWorkbook wb=new HSSFWorkbook(poiFS);
		HSSFSheet sheet2=wb.getSheetAt(1);
		String machineName=null;
		String machineLocation;
		Row row;
		Cell cell;
		Row row1;
		Cell cell1;
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\workspace\\catalyst_selenium\\resources\\output\\testng-results-final.xml"));
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+"<Allmachines>"+"\n");
		
		if (inputFileName != null && !inputFileName.equals("")) {
			try{
				
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
		        			if (cell1.getStringCellValue().trim().equalsIgnoreCase("Machine Names")) {
			        			for (int k = 1; k <= Lstrow-i ; k++) {
				        			row   = sheet2.getRow(k+i);
						        	cell  = row.getCell(0);
						        	//cellU=row.getCell(1);
						        	//cellP=row.getCell(2);
						        	if (cell != null) {
						        		if (cell.getCellType()==1) {
						        			
						        			machineName=cell.getStringCellValue().trim();
						        			machineLocation = "C:\\workspace\\catalyst_selenium\\resources\\output\\results\\"+machineName+"\\testng-results.xml";
						        			if (machineLocation != null && !machineLocation.equals("")) {
						        				writer.write("<machine name=\""+machineName+"\">"+"\n");
							        			BufferedReader reader = new BufferedReader(new FileReader(machineLocation));
							        	         String line = null;
							        	         int j=1;
							        	         
							        	         while ((line = reader.readLine()) != null)
							        	         {
							        	        	 if (j!=1) {
							        	        		 //System.out.println(line);
							        	                 writer.write(line+"\n");
							        				}
							        	        	 j+=1;
							        	        	 
							        	         }
							        	         writer.write("</machine>"+"\n");
							        	         reader.close();
							        		}else{
							        			System.out.println("testng-results.xml file does not exist or it does not having data @"+machineLocation);
							        			//System.exit(0);
							        		}
						        		}
						        	}
								}
			        			break;
							}	
						}
					}
		        }
		       
			}catch(Exception e){
				System.out.println(e);
				
			}
			/*return orgMachineCount;*/
		}
		else{
			System.out.println("driver.xls file does not exist hence stopping the run");
			System.exit(0);
		}
		
		 
         
         writer.write("</Allmachines>");
         System.out.println("Completed XML results files merging"); 
         // Close to unlock.
         
         // Close to unlock and flush to disk.
         writer.close(); 

	}

}
