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

• Iterator -> List (erfordert guava)
List<ContentType> list = Lists.newArrayList(iteratorOfContentTypes);

• Iterator -> List (erfordert commons-collections)
List<ContentType> list = IteratorUtils.toList(iteratorOfContentTypes);

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

• Map -> List
map.values()

• String -> File (ohne commons-io)
PrintWriter writer = new PrintWriter("/pathtofile");
writer.print(content);
writer.close();

• Primitive Array -> Object Array
Character[] chars = ArrayUtils.toObject("test".toCharArray());

• Array -> List
List<Character> list = Arrays.asList(new Character[]{'a','b'});

• 2d Array -> Map
Map<Object,Object> map = ArrayUtils.toMap(new String[][]{{"abd","123"},{"xyz","789"}});

• Array -> String
StringUtils.join(new String[]{"a","b"},"|")

• Element (DOM) implementiert Node

• Property-Validator in InitializingBean-Interface

• InputStream -> File
org.apache.commons.io.FileUtils.writeStringToFile(new File("c:\\result.txt"),org.apache.commons.io.IOUtils.toString(in.getContent().get(0).getAttachment().getDataSource().getInputStream()))

• Character Sonderzeichen (http://www.utf8-chartable.de/)
'\u00df' = ß
'\u00e4' = ä
'\u00fc' = ü
HTML/XML-Syntax &#x00df;
HTML/XML dezimal &#223; = ß

• Abbilden von Unicodezeichen mit mehr als 4 Hex-Stellen
String face = new String(new int[] { 0x1f634 }, 0, 1);

• die Unicoderepräsentation eines Zeichens ausgeben
"\\u" + Integer.toHexString('₤' | 0x10000).substring(1)

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
// die Zeitzone legt den Offset fest
// die Locale legt das Ausgabeformat fest
DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").print(time) // verwendet die Zone des time-Objekts für die Darstellung
DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(DateTimeZone.UTC).print(time)

• Joda-Zeitpunkt Locale-spezifisch formatiert ausgeben (lässt man withLocale(...) weg, dann wird die Systemsprache benutzt)
//style two characters from the set {"S", "M", "L", "F", "-"}; (first is date second is time)
// S = short; M = medium; L = long; F = full; - = omited
DateTimeFormat.forStyle("FF").withLocale(Locale.US).print(time);

• für eine Locale feststellen, ob 12 oder 24 Stunden angezeigt werden
boolean is24HourView = !DateTimeFormat.patternForStyle("-S", Locale.getDefault()).contains("a");

• prüfen, ob ein Zeitpunkt in einem Zeitraum liegt
(new Interval(from, to)).containsNow()

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

• Charset-Konstanten
StandardCharsets.ISO_8859_1 und StandardCharsets.UTF_8

• alle Charsets des Systems
List<Charset> charsets = Lists.newArrayList((new sun.nio.cs.StandardCharsets()).charsets());

• XSL-Trasformation mit Xalan in der Komandozeile
set CLASSPATH=serializer-2.7.2.jar;xalan-2.7.2.jar;xercesImpl-2.9.1.jar;xml-apis-1.3.04.jar
java org.apache.xalan.xslt.Process -IN Report.xml -XSL Report.xsl -OUT Report.html

• Byte-Verarbeitung
byte b = (byte)0xff; // diese vier Befehle weisen alle den selben Wert zu (der als -1 dargestellt werden würde)
byte b = (byte)255;
byte b = (byte)-1;
byte b = -1;
Wertebereich: 0 bis 127 dann -128 bis -1
Hexadezimale Darstellung: String.format("0x%02X", b)
Vorzeichenfreie Darstellung als int: b & 0xFF
Integer größer 127 müssen explizit gecastet werden, weil sie vorzeichenbehaftet dargestellt werden
Für den logischen Vergleich werden bytes immer erst in Integer konvertiert und dann vergleichen!
Soll heißen: (byte)255  ist kleiner als (byte)10 
bzw.         (byte)0xFF ist kleiner als (byte)0x0A
Für einen Vergleich muss also erst die vorzeichenfreie Darstellung erzeugt werden.

• Java-Download per curl
curl -L -C - -b "oraclelicense=accept-securebackup-cookie" -O http://download.oracle.com/otn-pub/java/jdk/8u77-b03/jdk-8u77-linux-x64.tar.gz

• Heap-Dump eines Java-Prozesses erzeugen (Prozessorarchitektur von jmap muss zu der des Prozesses passen)
jmap -dump:live,format=b,file=dump.hprof <PID>
Anschließend mit jvisualvm öffnen

• Thread-sichere Collections
Map m = Collections.synchronizedMap(new HashMap())
der Wrapper verwendet synchronized(this)-Blöcke (this ist das Wrapper-Objekt)
zum Thread-sicheren Iterieren muss die Schleife in einen synchronized(m)-Block gehüllt werden!

• Timeouts für TCP-Kommunikation
- java.net.Socket
  - setSoTimeout() (wie lange die read()-Methode auf dem InputStream blockiert)
  - connect(endpoint, timeout) (legt fest, wie lange ein Verbindungsaufbauversuch dauert darf)
- org.apache.http.client.config.RequestConfig.Builder
  - setConnectionRequestTimeout() (so lange wird gewartet, bis der Verbindungs-Manager eine Verbindung liefert)
  - setSocketTimeout() (wie lange die read()-Methode auf dem InputStream blockiert)
  - setConnectTimeout() (legt fest, wie lange ein Verbindungsaufbauversuch dauert darf)

