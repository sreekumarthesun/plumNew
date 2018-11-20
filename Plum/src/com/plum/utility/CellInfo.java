package com.plum.utility;

public class CellInfo {

	private String name;
	private String status;
	private int rowNumber;
	private int columnNumber;
	private String browsername;
	private String className;
	private String TestcaseName;

	public String getTestcaseName() {
		return TestcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		TestcaseName = testcaseName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public String getBrowsername() {
		return browsername;
	}

	public void setBrowsername(String browsername) {
		this.browsername = browsername;
	}

}
