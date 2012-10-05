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
 
--------------

weiter bei 7:30
