## JMeter

- Options -> Choose Language -> English (damit die Modulnamen zur Doku passen)

#### Die Komponenten

##### Thread Group
- wird dem Testplan hinzugefügt (unter `Threads (Users)`)
- legt fest, wie oft ein Request gesendet werden soll und mit wie vielen Threads

##### HTTP Cookie Manager
- wird einer `Thread Group` hinzugefügt (unter `Config Element`)
- muss im Request vorhanden sein, wenn bei einer Request-Folge (siehe `Thread Group`) Cookies vom Server wieder mitgeschickt werden sollen
- beim Neustart des Testplans werden die Server-Cookies des vorigen Laufs verworfen
- man kann Client-Cookies hinzufügen

##### HTTP Authorization Manager
- wird einer `Thread Group` hinzugefügt (unter `Config Element`)
- kann für Basic-Authentication benutzt werden (`HTTP Cookie Manager` wäre auch möglich)
- bis auf `Username`, `Password` und `Mechanism` können die anderen Felder leer bleiben

##### Constant Throughput Timer
- wird einer `Thread Group` hinzugefügt (unter `Timer`)
- legt für jeden einzelnen Thread fest, wie viele Requests pro Minute ausgeführt werden sollen

##### HTTP Request
- wird einer `Thread Group` hinzugefügt (unter `Sampler`)
- konfiguriert Server und Port
- `Path:` enthält alles nach dem Port z. B. `/webapp/res/img.png`
- Request-Body und Request-Parameter können konfiguriert werden
- Multipart-Bodies werden per Checkbox aktiviert

##### CVS Data Set Config
- wird einem `HTTP Request` hinzugefügt (unter `Config Element`)
- verweist auf eine csv-Datei relativ zur Testplandatei
- die csv-Datei kann eine umbruchseparierte Liste von Werten enthalten
- ein Umbrauch wird automatisch als Wertetrenner erkannt
- unter `Delimiter (...):` kann ein Wertetrenner innerhalb der Zeile eingetragen werden
- unter `Variable Names (comma-delimited):` wird ein Variablenname vergeben, der im übergeordneten Request verwendet werden kann
- im Request wird der Variablenname nach dem Muster `${var_name}` in den `Path` eingetragen
- bei einer Request-Folge werden die Werte dann nacheinander in den Pfad eingefügt

##### Response Assertion
- wird einer `Thread Group` hinzugefügt (unter `Assertions`)
- bewertet, ob ein Response als erfolgreich gilt oder nicht
- die Auswertung wird in den `View Results`-Komponenten dargestellt
- unter `Pattern to Test` wird Text eingetragen, nach dem im Response-Body gesucht wird
- Reguläre Ausdrücke werden auch unterstützt

##### View Results Tree
- wird einem `HTTP Request` hinzugefügt (unter `Listener`)
- listet alle Requests und Responses aller Testplanläufe auf
- die Raw-Daten von Request und Response sind einsehbar
- Redirects können nachvollzogen werden
- die Liste kann in eine Datei exportiert werden

##### View Results in Table
- wird einer `Thread Group` hinzugefügt (unter `Listener`)
- ähnlich dem `View Results Tree` nur als zusammengefasste Tabelle