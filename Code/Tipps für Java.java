• File to DOM

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document document = builder.parse(new FileInputStream("adressen2.xml"));

• Save DOM to File
javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(userData);
javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new java.io.File("C:\\testing.xml"));
transformer.transform(source, result);

• Path -> InputStream
new FileInputStream(path)

• File -> InputStream
new FileInputStream(file)

• String -> InputStream
new ByteArrayInputStream(string.getBytes())
IOUtils.toInputStream(string)

• InputStream -> String
IOUtils.toString(inputStream)

• File -> String
FileUtils.readFileToString(file)

• WebPage -> InputStream
new URL( "http://google.de/" ).openStream()

• Enumeration -> ArrayList
Collections.list(enumeration)

• Element (DOM) implementiert Node

• Property-Validator in InitializingBean-Interface

• InputStream -> File
org.apache.commons.io.FileUtils.writeStringToFile(new File("c:\\result.txt"),org.apache.commons.io.IOUtils.toString(in.getContent().get(0).getAttachment().getDataSource().getInputStream()))

• XPath-Helper
private static String xPathToString(Node node, String expression)
{
	try 
	{
		XPathExpression expr = XPathFactory.newInstance().newXPath().compile(expression);
		return (String)expr.evaluate(node, XPathConstants.STRING);
	} 
	catch (Exception e) {return null;}
}

private static NodeList xPathToNodeList(Node node, String expression)
{
	try 
	{
		XPathExpression expr = XPathFactory.newInstance().newXPath().compile(expression);
		return (NodeList)expr.evaluate(node, XPathConstants.NODESET);
	} 
	catch (Exception e) {return null;}
}
