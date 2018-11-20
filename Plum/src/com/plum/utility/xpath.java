package com.plum.utility;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xpath {
	public static void main(String[] args) throws Exception {
		try {
			File file = new File("C:\\workspace\\catalyst_selenium\\test-output\\testng-results.xml"); 
			
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			XPath xPath = XPathFactory.newInstance().newXPath();			
			
			String value = xPath.evaluate("//param[@index='0']/value/text()",
					doc).trim();
			System.out.println("Value: " + value);
			
			String value1 = xPath.evaluate("//param[@index='1']/value/text()",
					doc).trim();
			System.out.println("Value: " + value1);
			
			String value2 = xPath.evaluate("//param[@index='2']/value/text()",
					doc).trim();
			System.out.println("Value: " + value2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}