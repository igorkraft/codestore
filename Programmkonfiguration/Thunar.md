## Thunar

- `thunar` und `zenity` installieren
- eigene `Öffnen mit ...`-Befehle werden in `~/.local/share/applications` als Desktop Dateien gespeichert

#### GTK-Konfiguration

- Lesezeichendatei `~/.config/gtk-3.0/bookmarks`
  - Beispielzeile `file:///media/data data`
- Konfigurationsdatei `~/.config/gtk-3.0/settings.ini`
  - kann auch mit `lxappearance` konfiguriert werden (Paket `lxappearance-gtk3`)

```
[Settings]
gtk-icon-theme-name=Adwaita
gtk-theme-name=Adwaita
gtk-font-name=Sans 12
gtk-cursor-theme-name=Adwaita
gtk-cursor-theme-size=0
gtk-toolbar-style=GTK_TOOLBAR_BOTH
gtk-toolbar-icon-size=GTK_ICON_SIZE_LARGE_TOOLBAR
gtk-button-images=1
gtk-menu-images=1
gtk-enable-event-sounds=1
gtk-enable-input-feedback-sounds=1
gtk-xft-antialias=1
gtk-xft-hinting=1
gtk-xft-hintstyle=hintmedium
gtk-application-prefer-dark-theme=true
```

#### Konfiguration

- Ansicht
  - Verborgene Dateien anzeigen: ☑
  - Detailansicht: ☑
- Anzeige
  - Neue Fenster öffnen mit: `Detailsansicht`
  - Format: `YYYY-mm-dd HH:MM:SS`

#### Benutzerdefinierte Aktionen

- Tastenkürzel `F4` für `Terminal hier öffnen` hinzufügen
- `Neue Datei erstellen ...` anlegen
  - Als Befehl die gesamte Zeile verwenden (einschließlich der einfachen Gänsefüßchen)
    - lxterminal -e `NAME=$(zenity --entry --title "Neue Datei erstellen" --text "Neuen Namen eingeben") ; touch $NAME`
  - Tastenkürzel `Strg+Alt+n`
  - Dateizuordnung: alle Dateien und Ordner
