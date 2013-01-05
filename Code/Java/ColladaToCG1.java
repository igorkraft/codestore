import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ColladaToCG1 
{
	public static String js = "define([\"exports\"], function(exports)\n{\nexports.vertices =\n[\n%s\n];\n\n"+
			"exports.polygonVertices = \n[\n%s\n];\nexports.polygonColors = [];\n"+
			"for (var i = 0; i < exports.polygonVertices.length; i++)\nexports.polygonColors.push(0);\n});";
	
	public static void main(String[] args) 
	{
		try 
		{
			String in = "/home/user/Arbeitsfläche/sphere.dae";
			String out = "/home/user/Arbeitsfläche/sphere.js";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new FileInputStream(in));
			String points = xPathToString(document, "//float_array/.");
			points = transformPoints(points);
			String vcount = xPathToString(document, "//vcount/.");
			String p = xPathToString(document, "//p/.");
			String vertices = generateVertices(vcount,p);
			FileUtils.writeStringToFile(new File(out), String.format(js,points,vertices));
			System.out.println("fertig");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private static String transformPoints(String points) 
	{
		String[] split = points.split(" ");
		StringBuilder result = new StringBuilder("[");
		for (int i = 0; i < split.length; i = i + 3) 
		{
			String v1 = String.format(Locale.US,"%f",Double.parseDouble(split[i]));
			String v2 = String.format(Locale.US,"%f",Double.parseDouble(split[i+1]));
			String v3 = String.format(Locale.US,"%f",Double.parseDouble(split[i+2]));
			result.append(v1 + "," + v2 + "," + v3 + "],\n[");
		}
		return result.substring(0, result.length() - 3);
	}
	
	private static String generateVertices(String vcount,String p) 
	{
		String [] vcountSplit = vcount.split(" ");
		String [] pSplit = p.split(" ");
		String [] pFilterd = new String[pSplit.length / 2];
		for (int i = 0 ; i < pSplit.length ; i = i + 2)
		{
			pFilterd[i/2] = pSplit[i];
		}
		String result = "";
		int lastVcount = 0;
		for (String curVcountStr : vcountSplit)
		{
			int curVcount = Integer.parseInt(curVcountStr);
			String curLine = "[";
			for (int curP = lastVcount ; curP < lastVcount + curVcount ; curP++)
			{
				curLine = curLine + pFilterd[curP]+(curP + 1 < lastVcount + curVcount ? "," : "");
			}
			result = result + curLine + "],\n";
			lastVcount = lastVcount + curVcount;
		}
		return result.substring(0, result.length() - 2);
	}

	private static String xPathToString(Node node, String expression) 
	{
		try 
		{
			XPathExpression expr = XPathFactory.newInstance().newXPath().compile(expression);
			return (String) expr.evaluate(node, XPathConstants.STRING);
		} 
		catch (Exception e) 
		{
			return null;
		}
	}

}
