package com.plum.utility;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.plum.utility.Parameter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testcase")

public class Testcase {
	
	@XmlAttribute(name = "name")
	private String testcaseName;

	@XmlElement(name = "parameter", required = true)
	private List<Parameter> parameters = new ArrayList<Parameter>();

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	

	


}
