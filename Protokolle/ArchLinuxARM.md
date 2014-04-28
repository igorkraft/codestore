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
- `pacman -S adduser` (Programm zum Hinzufügen eines Benutzers installieren)
- neuen Benutzer anlegen `adduser ichabod` (außer dem Passwort alles mit `ENTER` bestätigen)
- `pacman -S sudo` das Programm sudo installieren
- ichabod für die Verwendung von sudo freischalten
 - `visudo` ausführen (Öffnet vi mit Syntaxprüfung für /etc/sudoers)
 - mit dem Cursor zu einer freien Zeile navigieren
 - `i` drücken um in den Edit-Mode zu wechseln
 - die Zeile `ichabod ALL=(ALL) ALL` hinzufügen (und eine weitere Leerzeile)
 - `ESC` drücken, um in den Command-Mode zu wechseln
 - `:x` eingeben und bestätigen (entspricht speichern und schließen)
- `pacman -S screen` installieren
- in der `/home/ichabod/.bashrc` folgende Zeile hinzufügen
 - `PS1='\[\033[00;33m\]\u@\h \[\033[00;36m\]\w\n\$ \[\033[03;00m\]'`

#### Webserver aufsetzen

- `pacman -S apache` installieren
- `pacman -S libxml2` installieren
- mit `sudo systemctl enable httpd.service` festlegen, dass der Dienst beim booten gestartet werden soll
- mit `sudo systemctl start httpd.service` den Dienst starten und prüfen, ob er funktioniert

#### Sonstiges

- Verwendung des Paketmanagers (Installation, Aktualisierung, Systemaktualisierung, usw.)
 - https://wiki.archlinux.de/title/pacman
- Erläuterungen zu sudo
 - https://wiki.archlinux.de/title/Sudo
- die Paketdateien aller installierten Programme liegen hier
 - `/var/cache/pacman/pkg/`

#### wichtige Konsolenbefehle

- `sudo pacman -S <program>` installiert ein Programm
- `sudo pacman -Sy` lokale Datenbank anktualisieren
- `sudo pacman -Su` alle installierten Pakete aktualisieren
- `systemctl list-unit-files` zeigt alle Dienste und deren Status an
- `sudo systemctl enable httpd.service` Dienst beim Booten des Rechners starten
- `systemctl status httpd.service` und `systemctl is-enabled httpd.service` zeigen den Status an
- `sudo systemctl start httpd.service` startet den Dienst
