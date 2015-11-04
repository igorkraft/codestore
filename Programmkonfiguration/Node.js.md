## npm

- Cache-Verzeichnis sollte wegen zu langer Dateipfade direkt auf C: liegen

#### Konfigurationsdatei:

- $HOME/.npmrc (globale Konfiguration für Benutzer)
- die Datei kann auch im Projekt-Verzeichnis abgelegt werden (Projektkonfiguration)
- die Datei kann auch im npm-Installationsverzeichnis abgelegt werden (globale Konfiguration)
- Dateiinhalt:
```
cache=c:\npm-cache
proxy=http://<Host>:<Port>
https-proxy=http://<Host>:<Port>
```
