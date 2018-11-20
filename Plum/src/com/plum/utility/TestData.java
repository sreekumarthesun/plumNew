package com.plum.utility;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.plum.utility.Test;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testcases")
public class TestData {	
		
		
		@XmlElement(name = "testcase", required = true)
		private List<Test> testcases = new ArrayList<Test>();		

	

		public List<Test> getTestcases() {
			return testcases;
		}

		public void setTestcases(List<Test> testcases) {
			this.testcases = testcases;
		}
		
	}



