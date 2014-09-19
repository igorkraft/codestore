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

• String -> File (ohne commons-io)
PrintWriter writer = new PrintWriter("/pathtofile");
writer.print(content);
writer.close();

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

• liefert alles links von einer bestimmten Zeichenkette
StringUtils.substringBefore(str, separator)

• Passwortabfragen
JPasswordField passwordField = new JPasswordField(20);
passwordField.setEchoChar('*');
JOptionPane.showMessageDialog(null,passwordField,"Enter password",JOptionPane.OK_OPTION);
String password = new String(passwordField.getPassword());

• Millisekundendauer formatiert ausgeben (erfordert commons-lang)
DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS")

• Millisekundendatum formatieren (erfordert commons-lang)
// lässt man die Zeitzone weg, dann wird die Systemzeitzone benutzt
DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"))

• Zeitpunkt in Millisekunden umwandeln (erfordert commons-lang)
Date date = DateUtils.parseDate("2011-07-22T12:01:34", new String[]{"yyyy-MM-dd'T'HH:mm:ss"});
date.getTime()

• Joda-Zeitpunkte erzeugen (alle Beispiel-Objekte enthalten den selben Zeitpunkt)
// aus UTC-Zeitstempel (hinterlegt für die Darstellung die Zeitzone des Systems, wird die Zeitzone angegeben, 
// dann ändert sich der Zeitpunkt nicht)
DateTime time = new DateTime(0); 
// hier legt die Zeitzone fest, wie die Zeitangaben in einen Zeitstempel umgewandelt werden sollen, der Zeitpunkt
// ändert sich je nach Zeitzone (lässt man die Zeitzone weg, dann wird die Systemzeitzone benutzt)
DateTime time = new DateTime(1970,1,1,1,0,0,0,DateTimeZone.forID("Europe/Berlin"));
// verhält sich analog zum vorigen Beispiel
DateTime time = DateTimeFormat
		.forPattern("yyyy-MM-dd HH:mm:ss.SSS")
		.withZone(DateTimeZone.forID("Europe/Berlin"))
		.parseDateTime("1970-01-01 01:00:00.000");
System.out.println("UTC time stamp: " + time.getMillis());

• Joda-Zeitpunkt formatiert ausgeben
DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").print(time) // verwendet die Zone des time-Objekts für die Darstellung
DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(DateTimeZone.UTC).print(time)

• Joda-Zeitpunkt Locale-spezifisch formatiert ausgeben (lässt man withLocale(...) weg, dann wird die Systemsprache benutzt)
//style two characters from the set {"S", "M", "L", "F", "-"}; (first is date second is time)
// S = short; M = medium; L = long; - = omited
DateTimeFormat.forStyle("FF").withLocale(Locale.US).print(time);

• für eine Locale feststellen, ob 12 oder 24 Stunden angezeigt werden
boolean is24HourView = !DateTimeFormat.patternForStyle("-S", Locale.getDefault()).contains("a");

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
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA like 'PUBLIC'

• um in LogCat nach Tags zu filtern
tag:^(Tagname1|Tagname2).*$

• Classen eines APK auflistenlisten (nur auf einem Androidgerät und nur mit dem classes.dex der eingenen Anwendung)
DexFile dexFile = new DexFile(this.getBaseContext().getApplicationInfo().sourceDir);
Collections.list(dexFile.entries());

• Stream splitten (erfordert commons-io, der letzte Parameter legt fest, ob beim close beide Streams geschlossen werden sollen)
TeeInputStream(inStream, outStream, true)

• aus Zwischenablage lesen
private String readFromClipboard() throws UnsupportedFlavorException, IOException
{
	Transferable data = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
	for (DataFlavor flavor : data.getTransferDataFlavors())
	{
		if (!(data.getTransferData(flavor) instanceof String)) continue;
		return data.getTransferData(flavor).toString();
	}
	return "";
}

• in Zwischenablage schreiben
public void writeToClipboard(String data)
{
	StringSelection contents = new StringSelection(data);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(contents, null);
}

• Charset-Konstanten (erfordert commons-io)
Charsets.ISO_8859_1 und Charsets.UTF_8
