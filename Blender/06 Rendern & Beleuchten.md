### 06 Rendern & Beleuchten

#### Kamera
- mit `Num_0` wird im 3D-Fenster der sichtbare Ausschnitt der Kamera angezeigt
  - mit `g` kann die Kamera dann bequem positioniert werden
  - mit `Shift` + `f` kann der Flugmodus aktiviert werden (Scrollrad zoomt)
- `Strg` + `Alt` + `Num_0` setzt das Kameraobjekt auf die Position, auf der die Kamera des 3D-Fensters ist
- im Propertiesfenster in Sektion `Object Data` wird unter `Focal Leng` der Winkel des virtuellen Objektivs eingestellt

#### Licht
- die Lampen werden im Propertiesfenster in Sektion `Object Data` eingestellt
 - hier wird die Art des Lichts eingestellt (z. B. Point, Sun, Spot, Hemi, Area)
- wenn man bei Punktlicht die Checkbox `Sphere` aktiviert, dann wird ein Kreis im 3D-Fenster angezeigt, in dem Objekte beleuchtet werden
- bei Sonnenlicht ist die Position der Lampe egel, alles wird im eingestellten Winkel beleuchtet (der Winkel wird durch Rotation der Lampe eingestellt)
- bei einem Lichtkegel (Spot) wird unter `Spot Shape` die Eigenschaft `Size` für den Winkel des Kegels gesetzt
 - `Distance` stellt ein, wie weit die Strahlen reichen
 - aktiviert man `Show Core`, dann wird im 3D-Fenster angezeigt, welche Flächenausschnitte auf den Objekten vom Lichtkegel getroffen werden
 - `Blend` stellt ein, wie weich der Rand des Lichtkegels (auf der projizierten Fläche) sein soll
- alle Lampen haben ein Feld, in dem die Lichtfarbe eingestellt wird
- `Energy` stellt die Lichtstärke ein
- im Abschnitt `Shadow` kann verhindert werden, dass Objekte durch dieses Licht einen Schatten werfen
- `Specular` gibt an, ob stellt ein, ob die Lichtquelle als heller Schein auf den beleuchteten Oberflächen erscheinen soll oder nicht
- wenn man eine Szene einfach nur aufhellen will, dann fügt man Lampen hinzu und stellt `Shadow` und `Specular` aus

----------
weiter mit dem zweiten Video
