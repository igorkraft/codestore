### ArchLinuxARM

#### Herunterladen und Installieren

- http://archlinuxarm.org/platforms/armv6/raspberry-pi
- auf dem Diskimage ist eine Partitionstabelle enthalten
 - am Anfang ist eine Bootpartition, gefolgt von einer Datenpartition
- `dd` kann auf die Karte schreiben auch wenn diese nicht gemountet ist
- mit `gparted` die Datenpartition auf die Größe der SDCard erweitern

#### Konfiguration

- initiale Anmeldedaten `root:root`
- mit `passwd` das Passwort ändern
- `pcman -S adduser` (Programm zum Hinzufügen eines Benutzers installieren)
- neuen Benutzer anlegen `adduser ichabod` (außer dem Passwort alles mit `ENTER` bestätigen)

#### Sonstiges

- Verwendung des Paketmanagers (Installation, Aktualisierung, Systemaktualisierung, usw.)
 - https://wiki.archlinux.de/title/pacman
