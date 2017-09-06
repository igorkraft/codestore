## Sublime Text

#### Allgemeines
- mit `Shift` + `Strg` + `p` wird die Schnellsuche für Befehle gestartet
- Pakete die mit Package Control installiert wurden, erweitern den Befehlssatz (normalerweise fangen die Befehle eines Plugins mit dem Namen des Plugins an)
- `Shift + Strg + p` -> `reveal` öffnet Verzeichnis der aktuellen Datei im FileManager
- Startparameter `-n` öffnet neues Fenster

#### Package Control
- in der Befehlsschnellsuche gibt es ein Eintrag `Install Package Control`
- für manuelle Installation den Anweisungen hier folgen: https://packagecontrol.io/installation
- neue Pakete können mit dem Befehl `Package Control: Install Package` gefunden und installiert werden
  - `Indent XML` installieren (Formatiert JSON und XML mit `Indent JSON` und `Indent XML`)
  - `Gradle_Language` installieren (Highlighting für Gradle)
  - `SideBarEnhancements` installieren (enthält `Locate`, erweitert Kontextmenü der Ordner-Sidebar)
  - `Markdown Preview` rendert Markdown zu Git flavored HTML
  - `Sublimerge 3` installieren (enthält Compare-Operationen)

#### Einstellungen
- Preferences -> Color Scheme -> Monokai

#### Settings
- um die Standardeinstellungen zu überschreiben, müssen sie aus `Settings - Default` nach `Settings - User` kopiert und angepasst werden
- `"draw_white_space": "all",`
- `"word_wrap": "false",`
- `"font_size": 13,`
- `"close_sidebar_if_opened": false,`
- `"scroll_speed": 0.0,` (keine Verzögerung beim Scrollen)

#### Dateien durchsuchen
- Verzeichnis öffnen, das durchsucht werden soll
- `Find` -> `Find in Files...` wählen
- Doppelklick auf Treffer öffnet die Fundstelle in Datei
- es werden alle Verzeichnisse, geöffnete Dateien und ungespeicherte Tabs durchsucht

#### Suche mit Regulären Ausdrücken
- matcht alle Zeilen, die nicht mit einem `c` anfangen: `^((?!c).*)$`
- matcht alle Umbrüche, die nicht von einem `c` gefolgt sind: `\n(?!c)`
- matcht Zeichen mit Unicode (hier ein `a`): `\x{0061}`
  - matcht zwei aufeinanderfolgende Umbrüche: `\x{000a}\x{000a}` oder `\n\n`
  - Wagenrückläufe werden beim Laden einer Datei gefiltert, können nach dem Speichern aber vorhanden bleiben
- in einem Suchergebnis die Inhaltszeilen löschen
  - `^[ ].*\n` durch Leerstring ersetzen

#### Konsolenbefehle
- `view.file_name()` Pfad zur aktuellen Datei ausgeben
