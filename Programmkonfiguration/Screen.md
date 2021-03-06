## Screen

#### Konfiguration

- `~/.screenrc` anlegen
```
defscrollback 10000
```

#### Allgemeines

- mehrere Screen-Sitzungen können sowohl nebeneinander, als auch ineinander geöffnet werden (es kann ein Sitzungsbaum entstehen)
 - auf einer Ebene des Baumes kann man nur mit einer Sitzung verbunden sein
 - Wurzel des Baumes ist das Terminal, von dem aus Screen gestartet wurde
 - es ist nicht möglich sich den Sitzungsbaum anzeigen zu lassen
 - um die Hierarchie flach zu halten, muss man sicherstellen, dass man die Verbindung zur aktuellen Sitzung trennt, bevor man eine neue Sitzung öffnet oder sich zu einer anderen Sitzung verbindet
- trennt man in mehreren ineinander verbundenen Sitzungen die Verbindung, so wird nur die Verbindung vom Terminal zur ersten Ebene geschlossen
 - alle darunter befindlichen Sitzungen bleiben verbunden (Status attached)
- haben auf verschiedenen Ebenen des Baumes Sitzungen die selben Namen, dann wird dieser Name beim Auflisten nur einmal angezeigt (mit dem Verbindungsstatus der letzten im Baum geöffneten Sitzung)
- beim Öffnen einer Sitzung, wird man automatisch mit der Sitzung verbunden
- um eine Sitzung zu schließen, verbindet man sich mit ihr und führt `exit` aus
- für jede neue Sitzung wird ein neuer Prozess gestartet (mit dem Namen `screen`)

#### Verwendung

- eine Screen-Sitzung starten   
`screen -S [Name der Sitzung]`

- Namen der aktuellen Screen-Sitzung anzeigen (Name steht rechts vom Punkt in der Ausgabe)   
`echo $STY`

- Verbindung zu einer Sitzung trennen (Sitzung bleibt offen)   
`[Strg]+[a], dann [d]`

- Liste aller offenen Sitzungen anzeigen (in einer flachen Hierachie wird der Status Attached höchstens einmal vorkommen)   
`screen -ls`

- mit einer offenen Sitzung verbinden   
`screen -r [Name der Sitzung]`

- Copy-Mode öffnen (zum scrollen)
  - `[Strg]+[a], dann [ESC]`
  - zum Beenden erneut `[ESC]`

- die .profile anpassen, um nach SSH-Login eine Screen-Session zu starten
```sh
initScreen()
{
	if [[ ! -z "$STY" ]]; then
		return
	fi

	screenLs=`screen -ls`

	if [[ $screenLs == *".s1"* ]]; then
		screen -r s1
	else
		screen -S s1
	fi
}

initScreen
```
