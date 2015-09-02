## Inkscape

#### Pfade
- wandelt man ein Objekt in einen Pfad um, dann verläuft der Pfad durch die Mitte der Kontur
- wandelt man die Objekt-Kontur in einen Pfad um, dann wird die Kontur zu einem gefüllten Objekt (die ursprüngliche Objektfüllung geht verloren)
  - auch Pfade mit einer Kontur (z. B. Bezier-Linie) können so in gefüllte Objekte umgewandelt werden
- der Entwurfspfad wird in der Pfad-Werkzeugpalette (Symbol ganz rechts) sichtbar gemacht
- die Pfadrichtung kann am Entwurfspfad eingeblendet werden
  - `Datei`->`Inkscape-Einstellungen`->`Werkzeuge`->`Knoten`->`Zeige Pfadrichtung an Außenlinie`
- wenn zwei Pfade kombiniert (`Strg + K`) und übereinander gelegt werden, dann bestimmt die Pfadrichtung, ob der innere Pfad die Füllung des äußeren ausschneidet
  - unter `Pfad`->`Richtung umkehren` kann die Richtung eines Unterpfades umgekehrt werden (ein Knoten des Unterpfades muss markiert sein)
- `Pfad`->`Zerlegen` zerlegt mehrere unabhängige Unterpfade in separate Objekte (ermöglicht Skalierung von Unterpfaden)
- Bewegt man den Cursor über einen Knoten und scrollt mit `Shift` nach oben, dann werden weitere Knoten, die in der Nähe liegen markiert (wie bei einem sich vergrößerden Markierkreis)
  - führt man den Vorgang mit `Strg` statt mit `Shift` aus, denn werden benachbarte Knoten auf dem Subpfad markiert (erleichtert das Markieren eines gesamten Subpfades)
- hält man `Shift` gedrückt und verschiebt einen Knoten, dann wird einer seiner Bezier-Anfasser bewegt
- mit `Strg` und `Alt` kann man einen Knoten entlang seiner Kanten bewegen

##### Knotentypen
- bei Ecken werden die Bezier-Anfasser des Knotens nicht eingeschränkt
- wenn man einen Knoten glättet, dann bleiben seine Bezier-Anfasser auf einer Linie, können aber verschiedene Abstände zum Knoten haben
- macht man einen Knoten symmetrisch, dann bleiben die Bezier-Anfasser auf einer Linie und die Abstände zum Knoten bleiben gleich
- lässt man einen Knoten automatisch abrunden, dann wird er symmetrisch gemacht und seine Anfasser werden so ausgerichtet, das eine maximale Rundung entsteht
