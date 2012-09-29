### Oberfläche & Navigation

- die Menüs können mit mittlerer Maustaste geskrollt werden
- mit `Strg`, `Mouse_mid` und links-rechts-Bewegungen können die Menüs geskrollt werden

#### User Preferences

##### Interface

- unter `Manipulator` kann die Größe der drei bunten Achsenpfeile verändert werden
- unter `View Manipulation` wird folgendes eingestellt:
 - `Auto Depth`: man kann in Blender nicht unbegrenzt rein zoomen, wenn die Option nicht gesetzt ist; ist sie gesetzt, dann funktioniert der Zoom in Abhängigkeit der Entfernung des Objekts zu dem man zoomen möchte
 - `Zoom to Mouse Position`: ist die Checkbox deaktiviert, zoomt Blender zum Koordinatenursprung
 - `Rotate Around Selection`: ist die Checkbox deaktiviert, rotiert Blender um den Koordinatenursprung
 - um mit `Num_7`, `Num_1` und `Num_3` die Perspektive zu wechseln, wird eine Kamerafahrt gemacht; die Geschwindigkeit dieser Kamerafahrt wird mit `Smooth` gesteuert (wobei ein hoher Wert eine langsame Fahrt bedeutet); ist der Wert auf 0, dann gibt es keinen Übergang

##### Editing

- hier können die `Undo Steps` eingestellt werden (32 ist default)
- `Align To` regelt, wie neu eingefügte Objekte initial ausgerichtet werden sollen
 - `World`: die Achsen des Objekte werden so wie die Weltachsen ausgerichtet
 - `View`: die Objektachsen werden relativ zu Sicht ausgerichtet
 
##### Input

- hier kann man ein Numpad auf einer Laptop-Tastatur simulieren lassen
- es gibt eine Liste mit allen Shortcuts und man kann sie verändern

##### Addons

- wo ein aktiviertes Addon auftaucht, steht unter `Location`

##### Themes

- Farbe des gitternetzes wird unter `3D View`->`Grid`

##### File

- `Load UI` legt fest, ob beim Laden eines blend-Files auch die gespeicherten Fenster und deren Positionen geladen werden sollen
- `Save Versions` stellt ein, wieviele Backups von alten Szenenversionen gemacht werden sollen, wenn man speichert (default=2)

#### Fenster

- der Szenengraf wird im `Outliner`-Fenster dargestellt
- es gibt für bestimmte Aufgaben vorgefertigte `Screen-Layouts`, die alle wichtigen Fenster für ein Vorhaben (wie Animation, Compositing usw.) beinhalten
 - die `Screen-Layouts` werden oben auf dem Icon neben `Default` eingestellt
- das 3D-Fenster hat rechts eine Toolbar, die mit `t` angezeigt und verborgen werden kann
- man kann eigene `Screen-Layouts` anlegen, indem man das Plus neben `Default` klickt
 - dann kann man das Layout benennen, alle Fenster und `User Properties` einstellen, die dafür gespeichert werden sollen
 - klickt man in den `User Preferences` unten `Save As Default`, dann werden die Fenster aller `Screen-Layouts`, die vorhandenen Objekte und Benutzereinstellungen gespeichert (in ein Startup-File)
 - erstellt man dann mit `File`->`New` eine neue Szene, so werden diese gespeicherten Dinge geladen
 - das Startup-File ist hier: /home/user/.blender/2.63/config/startup.blend
 - löscht man das Startup-File, dann wird beim nächsten Laden einer neuen Szene das Default-Startup-File generiert und geladen (alternativ kann man auch `File`->`Load Factory Settings` klicken)
 - in `Screen-Layouts` wird auch die Position der Kamera gespeichert (sollte man mal schnell zwischen verschiedenen Blickwinkeln wechseln wollen)
 
 
