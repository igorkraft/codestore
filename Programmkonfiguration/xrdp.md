## xrdp

#### Installation

- `https://aur.archlinux.org/xrdp.git`
- Services `xrdp.service` und `xrdp-sesman.service` aktivieren

##### Konfiguration

- `/etc/xrdp/xrdp.ini`
- im unteren Bereich sind Sektionen die beim Verbinden als `Session`-Profil gelistet werden
- jedes Propery in den Sektionen kann mit dem Wert `ask` belegt werden

##### Verwendung

- Verbindung herstellen
  - `xfreerdp /v:<remote-dest> /sec:tls /f` <- TODO: überprüfen
- zum Anmelden `Session`-Profil `Xvnc` wählen
- zum Trennen der Verbindung `xfreerdp` beenden
  - attach mit offener Session ist dann möglich
- zum Abmelden `i3` beenden
- offene Sessions anzeigen (mit user und display)
  - `ps -ef | grep vnc`

##### Sonstiges

- das Verbinden mit einem lokal laufenden Xserver ist laut Hersteller nicht möglich
- nach dem Verbinden ist die `LANG`-Variable nicht gesetzt
  - export LANG="de_DE.UTF-8"
