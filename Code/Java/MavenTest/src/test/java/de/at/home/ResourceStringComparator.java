package de.at.home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ResourceStringComparator
{
	@Test
	public void compareLanguageKeys() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException, XPathExpressionException
	{
		Document de = fileToDom("/home/user/de_strings.xml");
		Document en = fileToDom("/home/user/en_strings.xml");
		
		List<String> deKeys = getKeys(xPathToNodeList(de, "//string"));
		List<String> enKeys = getKeys(xPathToNodeList(en, "//string"));
		
		for (String deKey : deKeys) if (!enKeys.contains(deKey)) System.out.println(deKey);
		System.out.println("############################################################");
		for (String enKey : enKeys) if (!deKeys.contains(enKey)) System.out.println(enKey);
	}
	
	private static List<String> getKeys(NodeList nodes)
	{
		List<String> keys = new ArrayList<String>();
		for (int i = 0 ; i < nodes.getLength()  ; i++)
		{
			keys.add(((Element)nodes.item(i)).getAttribute("name"));
		}
		return keys;
	}
	
	private static Document fileToDom(String path) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new FileInputStream(path));
	}

	private static NodeList xPathToNodeList(Node node, String expression) throws XPathExpressionException
	{
		XPathExpression expr = XPathFactory.newInstance().newXPath().compile(expression);
		return (NodeList) expr.evaluate(node, XPathConstants.NODESET);
	}
}
