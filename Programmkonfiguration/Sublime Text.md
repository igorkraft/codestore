## Sublime Text

#### Allgemeines
- mit `Shift` + `Strg` + `p` wird die Schnellsuche für Befehle gestartet
- Pakete die mit Package Control installiert wurden, erweitern den Befehlssatz (normalerweise fangen die Befehle eines Plugins mit dem Namen des Plugins an)

#### Package Control
- für manuelle Installation den Anweisungen hier folgen: https://packagecontrol.io/installation
- neue Pakete können mit dem Befehl `Package Control: Install Package` gefunden und installiert werden
  - `Pretty JSON` installieren
  - `Gradle_Language` installieren (Highlighting für Gradle)
  - `SideBarEnhancements` installieren (erweitert Kontextmenü der Ordner-Sidebar)

#### Einstellungen
- Preferences -> Color Scheme -> Zenburnesque

#### Settings - User
- um die Standardeinstellungen zu überschreiben, müssen sie aus `Settings - Default` nach `Settings - User` kopiert und angepasst werden
- `"draw_white_space": "all",`
- `"word_wrap": "false",`
- `"font_size": 13,`

#### Suche mit Regulären Ausdrücken
- matcht alle Zeilen, die nicht mit einem `c` anfangen: `^((?!c).*)$`
- matcht alle Umbrüche, die nicht von einem `c` gefolgt sind: `\n(?!c)`
- matcht Zeichen mit Unicode `006d` (ist ein `k`) : `\x{0061}`

#### Konsolenbefehle
- `view.file_name()` Pfad zur aktuellen Datei ausgeben
