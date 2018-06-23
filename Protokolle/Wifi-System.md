### Wifi-System Protokoll

#### Herunterladen und Installieren

- Quelle: https://archlinuxarm.org/platforms/armv8/broadcom/raspberry-pi-3
- Raspberry Pi3 mit Image des Raspberry Pi2 (Image-Link als `wget`-Befehl in Quelle enthalten)
- SD-Karte in zwei Partitionen teilen
  - Partition 1: 100MB mit fat32 mounten als rp2_boot (beliebiger Ordner)
  - Partition 2: ext4 mounten als rp2_root
- als root (nicht per sudo!) das Archiv auf rp2_root auspacken:
  - `bsdtar -xpf ArchLinuxARM-rpi-2-latest.tar.gz -C rp2_root`
- die Dateien im Ordner rp2_root/boot nach rp2_boot verschieben

#### Konfiguration

- initiale Benutzer `root:root` und `alarm:alarm`
- man kann sich nicht per SSH als root anmelden (mit `su` kann alarm zu root werden)
- mit `passwd` beide Passwörter ändern
- in der .bashrc den Prompt anpassen `PS1='\[\033[00;33m\]\u@\h \[\033[00;36m\]\w\n\$ \[\033[03;00m\]'`
- System aktualisieren `pacman -Syu`
  - bei Signaturproblemen in der `/etc/pacman.conf` den SigLevel anpassen `SigLevel    = Optional TrustAll`
  - Neustarten, weil sonst keine kernel module gefunden werden
- `sudo` installieren
- `alarm` für die Verwendung von `sudo` legitimieren

#### WLAN Access Point einrichten

- `binutils fakeroot patch pkg-config make gcc create_ap` installieren
- als `root` Datei `/usr/local/bin/start_wlan` anlegen (erste Zeile ist zwingend nötig)
```
#!/bin/sh

# Access Point starten
create_ap --no-virt -c 3 wlan0 eth0 some_name some_password
```
- die Datei für ihren Besitzer ausführbar machen
  - `chmod +x start_wlan`
- Datei `/etc/systemd/system/myscript.service` anlegen
```
[Unit]
Description=MyScript
Requires=network.target
After=network.target

[Service]
Type=simple
ExecStart=/usr/local/bin/start_wlan

[Install]
WantedBy=multi-user.target
```
- `sudo systemctl enable myscript.service`

#### Docker einrichten

- `ext4`-Partition auf USB-Stick anlegen (UUID=5e98791d-3843-4fb0-a0fd-1c9a55687386)
- `fstab` anpassen
```
UUID=5e98791d-3843-4fb0-a0fd-1c9a55687386 /media/16gbStick ext4 defaults 0 2
/media/16gbStick/docker /var/lib/docker none bind 0 0
```
- die beiden verbundenen `docker`-Verzeichnisse als `root` anlegen
- `docker` installieren (anschließend neustarten)
- `sudo systemctl enable docker.service`

#### NextCloud einrichten

- `sudo docker run --name nextcloud --restart=always -d -p 8080:80 -p 8443:443 -v /media/16gbStick/volumes/nextcloud:/var/www/html nextcloud`
- `config/config.php` unter Trusted Domains die IP des Hosts hinzufügen (für SSL-Port!)
- Neustarten, dass neue Apps installiert werden könnnen
- Admin-Konto und Dummy-Konto einrichten
- unter `Apps` im Abschnitt `Büro & Text` die App `Calendar` installieren
- Dummy-Konto einrichten
  - im Kalender unter `Einstellungen & Import` die Zeitzone für Berlin auswählen
  - in den Profileinstellungen im Abschnitt `Zusätzliche Einstellungen` alle Aktivitäten deaktivieren
  - in den Einstellungen der `Dateien`-Übersicht `Versteckte Dateien anzeigen` aktivieren
  - den Kalender `Persönlich` löschen
  - neue Kalender anlegen
    - U2FsdGVkX189O8huMvT8TlLHQTFRFoAiCkZqN3LrO3X26AKTj0kqdjNL/vIqkcu0pVG4L+Zoh3jfkPiUuDHif2PjM8X8264oiAMMUbcNXnEUdPBBHaN4F3qpm7SepHDsEb3dk+rMw2ZALtdM6aHcDw==
- SSL konfigurieren
  - bash öffnen `docker exec -i -t nextcloud /bin/bash`
  - `cd /etc/apache2`
  - `mkdir ssl`
  - `openssl req -x509 -nodes -days 1095 -newkey rsa:2048 -out ssl/server.crt -keyout ssl/server.key`
    - bei `Common Name` die IP eintragen
    - alles andere mit Enter übergehen
  - `cp sites-available/default-ssl.conf sites-available/ssl.conf`
  - `sed -i 's/\/etc\/ssl\/certs\/ssl-cert-snakeoil.pem/\/etc\/apache2\/ssl\/server.crt/g' sites-available/ssl.conf`
  - `sed -i 's/\/etc\/ssl\/private\/ssl-cert-snakeoil.key/\/etc\/apache2\/ssl\/server.key/g' sites-available/ssl.conf`
  - `ln -s /etc/apache2/sites-available/ssl.conf /etc/apache2/sites-enabled/000-ssl.conf`
    - die absoluten Pfade müssen sein
  - `a2enmod ssl`
  - `docker` neustarten

#### Android-Gerät einrichten

- DavDroid installieren
  - https://f-droid.org/packages/at.bitfire.davdroid/
- in DavDroid ein Konto per URL und Benutzernamen anlegen
  - Adresse steht im Browser unter `Kalender` - `Einstellungen & Import` - `iOS-/OS-X-CalDAV-Adresse`
    - nicht `Primäre CalDAV-Adresse` verwenden!
  - Kontakte sind eigene VCards
- CardDAV-Ordner löschen
- Checkboxen aller CalDAV-Kalender aktivieren und Synchronisierung starten
- DavDroid so einstellen, dass es nur im heimischen WLAN synchronisiert
- gegebenenfalls in den Einstellungen der Kalender-App CalDAV-Synchronisierung aktivieren