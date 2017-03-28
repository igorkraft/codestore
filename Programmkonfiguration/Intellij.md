## Intellij

#### JDK zum Ausführen des Android-Studios
- vor dem Start die Umgebungsvariable setzen: `STUDIO_JDK=$JAVA_HOME`

#### Settings->Compiler->Android Compilers
- Force jumbo mode: &#x2611;
- VM options: --force-jumbo

#### Editor->General
- Change font size (Zoom) with Ctrl+Mouse Wheel: &#x2611;

#### Editor->General->Editor Tabs
- Show tabs in single row: &#x2611;

#### Editor->General->Appearance
- Show whitespaces: &#x2611;
- Show Line Numbers: &#x2611;

#### Editor->Colors and Fonts->Font
- Scheme: Dracula
- Apply
- Save As -> Dracula copy
- Size: 14

#### Editor->File Encodings
- Project Encoding: UTF-8

#### Haltepunkt unterbricht nur den Thread
- irgendwo einen Haltepunkt setzen
- Kontextmenü des Haltepunkts öffnen (Rechtsklick)
- `Thread` wählen
- `Make Default` wählen

#### Editor->General->Auto Import
- Optimize imports on the fly
- Add unambiguous imports on the fly

#### Editor->Code Style->Java->Imports
- Class count to use import with '*': 5000
- Names count to use static import with '*': 5000
- für diese Einstellungen wird ein Schema erzeugt, dass für jedes Projekt angewendet werden muss
