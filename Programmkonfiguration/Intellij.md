## IntelliJ

#### Allgemeines
- Schriftgröße zurück setzen: `<Ctl>+<Shift>+a` -> Reset Font Size

#### View
- Toolbar: &#x2611;

#### Plugins->Browse repositories...
- `Markdown support`
- `Lombok Plugin`
- `Database Navigator`
  - für SQLite-Dateien eine Verbindung vom Typ `Custom...` erzeugen
  - Änderungen werden nur mit aktiviertem `Auto-Commit` gespeichert
- `Maven Helper`
- `Docker integration`

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
- Show parameter name hints: &#x2610;

#### Editor -> Color Scheme -> General
- Dracula-Scheme duplizieren und speichern

#### Editor -> Color Scheme -> Color Scheme Font
- Use color scheme font instead of the default: &#x2611;
- Font: Source Code Pro
- Size: 15

#### Editor->File Encodings
- Project Encoding: UTF-8

#### Haltepunkt unterbricht nur den Thread
- irgendwo einen Haltepunkt setzen
- Kontextmenü des Haltepunkts öffnen (Rechtsklick)
- `Thread` wählen
- `Make Default` wählen

#### Editor->General->Auto Import
- Add unambiguous imports on the fly

#### Editor->Code Style->Java->Tabs and Indents
- Use tab character: &#x2611;
- Tab size: 2
- Indent: 2

#### Editor->Code Style->Java->Imports
- Class count to use import with '*': 5000
- Names count to use static import with '*': 5000
- Packages to Use Import with '*': <leere Liste>

#### Languages & Frameworks->Markdown
- in `URI:` `default.css` durch `darcula.css` ersetzen

#### Appearance & Behavior->System Settings
- Reopen last project on startup: &#x2610;
- Open project in new window: &#x2611;

#### Custom Action mit Ant
- über das `Tool Window` `Ant Build` eine build.xml mit dem Projekt verknüpfen
- eine `Ant Target` Run Configuration anlegen
- `Before Launch` Tasks löschen
- `Target name` festlegen
- `Run Configuration` mit `Shift + F10` ausführen (alternativ `Shift + Alt + F10`)
