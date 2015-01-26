## JMeter

- Options -> Choose Language -> English (damit die Modulnamen zur Doku passen)

#### HTTP-Request-Test
- im Testplan eine Threadgruppe anlegen
- der Threadgruppe einen `HTTP-Request` hinzufügen (unter Sampler)
- dem `HTTP-Request` werden anschließend weitere Komponenten hinzugefügt

#### Die Komponenten

##### Thread Group
- legt fest, wie oft ein Request gesendet werden soll und mit wie vielen Threads

##### HTTP Request
- kann nur einer Thread-Gruppe hinzugefügt werden (unter Sampler)
- konfiguriert Server und Port
- `Path:` enthält alles nach dem Port z. B. `/webapp/res/img.png`
- Request-Body und Request-Parameter können konfiguriert werden
- Multipart-Bodies werden per Checkbox aktiviert

##### HTTP Cookie Manager
- muss im Request vorhanden sein, wenn bei einer Request-Folge (siehe `Thread Group`) Cookies vom Server wieder mitgeschickt werden sollen
- beim Neustart des Testplans werden die Server-Cookies des vorigen Laufs verworfen
- man kann Client-Cookies hinzufügen

##### HTTP Authorization Manager
- kann für Basic-Authentication benutzt werden (`HTTP Cookie Manager` wäre auch möglich)
- bis auf `Username`, `Password` und `Mechanism` können die anderen Felder leer bleiben

##### View Results Tree
- listet alle Requests und Responses aller Testplanläufe auf
- die Raw-Daten von Request und Response sind einsehbar
- Redirects können nachvollzogen werden
- die Liste kann in eine Datei exportiert werden
