## Eclipse

#### General->Appearance->Colors and Fonts->Basic->Text Font->Edit
- Größe: 12

#### General->Editors->Text Editors
- Displayed tab width: 2
- Show line numbers: &#x2611;
- Show whitespace characters: &#x2611;

#### General->Workspace
- Text file encoding: UTF-8

#### Web->HTML Files
- Encoding: UTF-8

#### XML->XML Files->Editor
- Line width: 800

#### XML->XML Files->Editor->Typing
- Close automatically comments: &#x2610;

#### SpringSource->Dashboard
- Show Dashboard On Startup: &#x2610;

#### Maven
- Update Maven projects on startup: &#x2611;

#### Java->Code Style->Formatter->Edit
- Profile name: custom
- Indentation
 - Tab size: 2
 - Align fields in columns
- Braces
 - Array initializer: Same line
 - alle anderen: Next line
- Line Wrapping
 - Maximum line width: 800

#### Run/Debug->Launching
- Launch the previously launched application: &#x2611;

#### General->Appearance->Color Theme
- http://eclipse-color-theme.github.io/update/ (Color Theme Plugin Update Site)
- Zenburn: &#x2611;
 
#### Variables-View->View Menu->Layout->Select Columns
- Declared Type &#x2611;
- Actual Type &#x2611;

#### Startargumente
- ` -showlocation` zeigt den Workspace in der Titelleiste an
- ` -data /path/to/workspace` übergibt den Workspace

#### Sonstiges
- über das Kontextmenü der Variables-View können Watchpoints für primitive Datentypen erzeugt werden
- Watchpoints können auch bei Attributdeklaration mit Doppelklick auf die Breakpointbar gemacht werden
- mit Doppelklick auf die Breakpointbar bei Klassendeklaration wird ein Breakpoint gesetzt, der immer dann unterbricht, wenn ein Objekt der Klasse erzeugt wird (egal mit welchem Konstruktor)
- unter Watchpoint-Properties kann festgelegt werden, ob ein Watchpoint bei lesendem oder schreibendem Zugriff unterbrechen soll
- Breakpoints bei Methodendeklaration können auch für das Verlassen von Methoden eingestellt werden (praktisch, wenn es mehrere Austrittspunkte gibt)
- wenn man den folgenden Code für eine Condition festlegt, dann werden Konsolenausgaben beim durchlaufen der Breakpoints gemacht, ohne dass die Ausführung unterbrochen wird (praktisch um race conditions zu finden)
 - `System.out.println("Hello there"); return false;`

#### Remote Debugging

- ermöglicht Eclipse über einen offenen Port eine laufende JVM zu debuggen
 - die JVM lauscht auf dem Port und wartet darauf, dass sich ein Debugger anschließt
- funktioniert per Netzwerk auch über verschiedene Rechner
- der Sourcecode der zu debuggenden Bibliothek muss zum Binary in der externen JVM passen
- zu debuggendes Java mit diesen Parametern aufrufen   
  `-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8765`
 - `suspend=[y|n]` legt fest, ob die JVM beim Start so lange blockieren soll, bis sich ein Eclipse auf dem Debugport meldet (sinnvoll für das Debuggen der Initialisierungsphase)
- für Tomcat müssen die Parameter oben in die catalina.bat eingetragen werden
 - `set JAVA_OPTS=%JAVA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8765`
 - hat im Test nicht mit einem Tomcat funktioniert, der als Dienst installiert war
- in Eclipse eine Run Configuration vom Typ `Remote Java Application` anlegen
 - Connection Type: Standard (Socket Attach)
 - Host: <Rechner auf dem die JVM läuft>
 - Port: 8765
