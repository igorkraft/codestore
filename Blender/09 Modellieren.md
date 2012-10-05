### 09 Modellieren

#### 3D-Fenster

- unten unter `View` gibt es Menüeinträge für alles was mit dem Numpad geht
- Fullscreen des 3D-Fensters wird mit `Strg + Arrow_Up` (de-)aktiviert
- unter `Selection` kann die Auswahlt umgekehrt werden, Borderselection aktiviert werden usw.
- unter `Object` kann Text in ein Mesh umgewandelt werden, die Transformationen können aktiviert werden, Keyframes können bearbeitet werden, Objektgruppen können gebildet werden usw. 
- im `Edit-Mode` wird das `Object`-Menü zu `Mesh` (Flächen füllen, Duplizieren, Extrude usw.)
- hat man mehrere Objekte markiert und wendet Skalieren oder Rotieren an, dann geschehen diese Operationen relativ zu relativ zum durchschnittlichen Koordinatenursprung (Pivot-Punkt) der Gruppe
 - möchte man, dass diese Transformationen bei jedem Objekt auf seinen eigenen Pivot-Punkt angewendet wird (also nicht beispielsweise um den Gruppenmittelpunkt rotiert, sondern um seinen eigenen Mittelpunkt rotiert), dann wählt man unten auf dem Icon mit dem Doppelkreis `Individual Origin` aus
 - wählt man hingegen `3D Cursor` aus, dann werden die Transformationen relativ zu diesem ausgeführt
 - wählt man `Active Element`, dann wird die Transformation relativ zum letzten ausgewählten Element gemacht (zur Hauptselektion)
 - möchte man alle Objekte vergrößern, ohne ihre Position zu ändern, dann wählt man `Individual Origin` und skaliert
 - möchte man nur die Position verändern (bei gleichbleibender Größe), dann wählt man `Bounding Box Center`,  aktiviert den Doppelpfeilbutton neben der Auswahlbox und skaliert!
 - hat man den Doppelpfeilbutton aktiviert und rotiert, dann bleibt die Ausrichtung der Objekte wie sie war (obere Würfelfläche bleibt nach der Rotation nach oben gerichtet) und nur die Position verändert sich
- in der Auswahlbox `Transform Orientaion` (initial steht dort `Global`) wird festgelegt, wie das bunte Koodrinatenkreuz von Objekten ausgerichtet werden soll
 - `Global` heißt, das Kreuz des Objekts wird parallel zum Weltkoordinatensystem ausgerichtet
 - `Local` heißt, das Kreuz zeigt die Ausrichtung des Objekts an (z.B. nach Rotation)
- unabhängig davon, was bei `Transform Orientaion` eingestellt ist, führt zweifaches drücken von `g`, `z`, `z` dazu, dass das Objekt in lokaler Z-Richtung verschoben wird (Achseneinschränkung muss zweimal gedrückt werden)
 - skalieren und rotieren funktionieren analog
- wird Magnet-Button aktiviert, dann richten sich Objekte beim bewegen immer am Raster aus
 
##### Ebenen

- die anzuzeigende Ebene wird unter `Visible Layers` angezeigt
 - wählt man mit `Shift` die Ebenen aus, dann werden mehrere gleichzeitig angezeigt
 - im Szenengrafen befinden sich die Objekte aller Ebenen
 - möchte man im Szenengraf nur die Objekte sehen, die auf den sichtbaren Ebenen liegen, dann wählt man über dem Szenengraf statt `All Scenes` `Visible Layers`
- gerendert werden nur ausgewählte Ebenen
 - gibt es in den ausgewählten Ebenen keinen Lampen, dann gibt es auch kein Licht in der gerenderten Szene (daher sollten die Lampen zu allen relevanten Ebenen gehören)
- die Ebenen dienen dazu ein Objekt zu bearbeiten, ohne dass andere Objekte die Sicht behindern oder fälschlich ausgewählt oder verändert werden
- um Objekte zwischen Ebenen zu verschieben, drückt man `m` und wählt die Zielebenen aus
 - ein Objekt kann Teil mehrere Ebenen sein
- unter `Visible Layers` wird für jede Ebene, auf der sich ein Objekt befindet ein Punkt gemacht
 - der Punkt färbt sich orange, wenn die aktuelle Selektion Teil der Ebene ist (damit man sieht auf welchen Ebenen die Auswahl liegt)

##### Properties

- werden mit `n` geöffnet
- Abschnitt `Transform`
 - hier können Position, Rotation, Skalierung und Größe nummerisch angepasst werden
 - neben den Wertefeldern befinden sich Schlossbuttons
 - sind die Schlossbuttons aktiviert, dann lassen sich die Werte nur noch in den Wertfeldern verändern und nicht mehr durch Transformation im 3D-Fenster!

--------------

weiter bei 14:00

