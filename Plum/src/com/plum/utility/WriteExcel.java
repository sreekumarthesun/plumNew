package com.plum.utility;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;
	private String sheetName;
	private String []headers;
	private WritableSheet excelSheet;
	private WritableWorkbook workbook;

	
	public WriteExcel(String fileName, String sheetName, String []headers) {
		this.inputFile = fileName;
		this.sheetName = sheetName;
		this.headers = headers;
	}
	
	public WritableWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(WritableWorkbook workbook) {
		this.workbook = workbook;
	}

	public WritableSheet getExcelSheet() {
		return excelSheet;
	}

	public void setExcelSheet(WritableSheet excelSheet) {
		this.excelSheet = excelSheet;
	}

	public void prepareExcel() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		setWorkbook(Workbook.createWorkbook(file, wbSettings));
		workbook.createSheet(sheetName, 0);
		setExcelSheet(workbook.getSheet(0));
		createLabel(getExcelSheet(), headers);
	}

	public void prepareAndCloseWorkBook(WritableWorkbook book) throws IOException, WriteException {
		book.write();
		book.close();
	}
	
	private void createLabel(WritableSheet sheet, String []headers)
			throws WriteException {
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write headers
		for (int i = 0; i < headers.length; i++) {
			addCaption(sheet, i, 0, headers[i]);
		}
	}

	public void createContent(WritableSheet sheet, int row, int column,
			String data) throws WriteException, RowsExceededException {
		
		addLabel(sheet, column, row, data);
	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

}
