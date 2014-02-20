## Eclipse

#### General->Appearance->Colors and Fonts->Basic->Text Font->Edit
- Größe: 12

#### General->Editors->TextEditors
- Displayed tab width: 2
- Show line numbers: &#x2611;
- Show whitespace characters: &#x2611;

#### General->Workspace
- Text file encoding: UTF-8

#### Web->HTML Files
- Encoding: UTF-8

#### XML->Editor
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

#### Sonstiges
- über das Kontextmenü der Variables-View können Watchpoints für primitive Datentypen erzeugt werden
- Watchpoints können auch bei Attributdeklaration mit Doppelklick auf die Breakpointbar gemacht werden
- mit Doppelklick auf die Breakpointbar bei Klassendeklaration wird ein Breakpoint gesetzt, der immer dann unterbricht, wenn ein Objekt der Klasse erzeugt wird (egal mit welchem Konstruktor)
- unter Watchpoint-Properties kann festgelegt werden, ob ein Watchpoint bei lesendem oder schreibendem Zugriff unterbrechen soll
- Breakpoints bei Methodendeklaration können auch für das Verlassen von Methoden eingestellt werden (praktisch, wenn es mehrere Austrittspunkte gibt)
- wenn man den folgenden Code für eine Condition festlegt, dann werden Konsolenausgaben beim durchlaufen der Breakpoints gemacht, ohne dass die Ausführung unterbrochen wird (praktisch um race conditions zu finden)
 - `System.out.println("Hello there"); return false;`
