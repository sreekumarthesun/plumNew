package com.plum.utility;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.plum.utility.Classes;
import com.plum.utility.Parameter;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "test")
public class Test {

	@XmlAttribute(name = "name")
	private String testName;

	@XmlElement(name = "parameter", required = true)
	private List<Parameter> parameters = new ArrayList<Parameter>();

	@XmlElement(name = "classes", required = true)
    private Classes classes;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}
}
