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

• pretty print DOM to OutputStream
ByteArrayOutputStream resultXml = new ByteArrayOutputStream();
Transformer transformer = TransformerFactory.newInstance().newTransformer();
transformer.setOutputProperty(OutputKeys.INDENT, "yes");
transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
transformer.transform(new DOMSource(domElement), new StreamResult(resultXml));


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

• Array -> List
List<TypeOfArray> list = new ArrayList<TypeOfArray>(); 
Collections.addAll(list, array);

• Element (DOM) implementiert Node

• Property-Validator in InitializingBean-Interface

• InputStream -> File
org.apache.commons.io.FileUtils.writeStringToFile(new File("c:\\result.txt"),org.apache.commons.io.IOUtils.toString(in.getContent().get(0).getAttachment().getDataSource().getInputStream()))

• Character Sonderzeichen (http://www.utf8-chartable.de/)
'\u00df' = ß
'\u00e4' = ä
'\u00fc' = ü
HTML-Syntax &#x00df;
HTML dezimal &#223; = ß

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

• Remote debuggen
oben im Catabila.bat das folgende eingeben:
set JAVA_OPTS=%JAVA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address="8000"
im Eclips eine Debugkonfiguration für die Bibliothek anlegen
Remote Java Application -> Projekt auswählen -> Host: localhost; Port: 8000

• Parameter eines ServletRequests iterieren
for (Entry<String,Object> entry : (Set<Entry>)request.getParameterMap().entrySet())

• Ersetze alle Platzhalter, die mit "-error" enden ("Hallo ${test-error} Welt" -> "Hallo Welt")
s.replaceAll("\\$\\{[^\\};]*-error\\}", "");

• Ersetze alle Platzhalter mit den Token einer Map (erfordert commons-lang)
StrSubstitutor.replace(<Objekt auf dem .toString() aufgerufen wid>, map, [Token-Prefix], [Token-Suffix])
StrSubstitutor.replaceSystemProperties(object)

• Ort, an dem die aus JSP generierten Java-Codes liegen
work\Catalina\localhost\<Application>\org\apache\jsp

• der etwas bessere Split aus commons-lang
StringUtils.splitByWholeSeparatorPreserveAllTokens(text,newLine);

• Passwortabfragen
JPasswordField passwordField = new JPasswordField(20);
passwordField.setEchoChar('*');
JOptionPane.showMessageDialog(null,passwordField,"Enter password",JOptionPane.OK_OPTION);
String password = new String(passwordField.getPassword());

• Millisekundendauer formatiert ausgeben (erfordert commons-lang)
DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS")

• nettes LookAndFeel
UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

• eine Funktion als Java-Script auslagern
private String callScriptFunction(String param) throws Exception
{
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");
	InputStreamReader reader = new InputStreamReader(new FileInputStream("script.js"), "UTF-8");
	engine.eval(reader);
	return ((Invocable)engine).invokeFunction("scriptFunction", param).toString();
}
// script.js:
function scriptFunction(param) { return "result"; }

• Trim mit anderen Zeichen als Whitespaces (erfordert guava)
CharMatcher.anyOf("{}").trimFrom("{key1=value1, key2=value2}");

• Schlüssel-Werte-Paare von String nach Map (erfordert guava)
Map<String, String> result = Splitter.on(", ").withKeyValueSeparator("=").split("key1=value1, key2=value2");

• alle Tabellen einer HSQLDB anzeigen
SELECT TABLE_NAME, COLUMN_NAME, TYPE_NAME, COLUMN_SIZE, DECIMAL_DIGITS, IS_NULLABLE 
FROM INFORMATION_SCHEMA.SYSTEM_COLUMNS 
WHERE TABLE_NAME NOT LIKE 'SYSTEM_%'
