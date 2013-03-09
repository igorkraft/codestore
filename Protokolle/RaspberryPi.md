### Raspberry Pi Multimedia
- WLAN-Treiber sind schon in Raspbian enthalten
- wicd installieren
 - auf der Konfigurationsoberfläche wlan0 als Netzgerätedatei bekannt machen
- Cursor per Tastatur steuern
 - Quelle: http://www.raspberrypi.org/phpBB3/viewtopic.php?f=29&t=27857
 - `sudo apt-get install xwit xdotool`
 - die Sektion `<keyboard>` in der `/home/pi/.config/openbox/lxde-rc.xml` erweitern (Code siehe unten)
 - `Num_4`, `Num_5`, `Num_6` und `Num_8` bilden ein Steuerkreuz
 - `Num_7` ist linke Maustaste; `Num_9` ist rechte Maustaste und `Num_1` ist mittlere Maustaste
- für eine Bildschirmauflösung von 720p, die /boot/config.txt anpassen
 - hdmi_group=1 und hdmi_mode=19
- `sudo raspi-config` -> `change_locale` -> de.UTF-8 sorgt für deutsche Texte
- für deutsches Tastaturlayout die /etc/default/keyboard anpassen `gb` zu `de`
- minetest und torus-trooper laufen nicht
- kobodeluxe läuft (Texte in Menüs werden nur nicht angezeigt)
- vlc läuft und spielt brav Radiostreams ab


Cursor per Tastatur steuern (`/home/pi/.config/openbox/lxde-rc.xml`):

    <keybind key="KP_4"><action name="Execute"><command>xwit -root -rwarp -5 0</command></action></keybind>
    <keybind key="KP_6"><action name="Execute"><command>xwit -root -rwarp 5 0</command></action></keybind>
    <keybind key="KP_8"><action name="Execute"><command>xwit -root -rwarp 0 -5</command></action></keybind>
    <keybind key="KP_5"><action name="Execute"><command>xwit -root -rwarp 0 5</command></action></keybind>
    <keybind key="KP_9"><action name="Execute"><command>xdotool click 3</command></action></keybind>
    <keybind key="KP_1"><action name="Execute"><command>xdotool click 2</command></action></keybind>
    <keybind key="KP_7"><action name="Execute"><command>xdotool click 1</command></action></keybind>
    <keybind key="C-KP_7"><action name="Execute"><command>xdotool click 1</command></action></keybind>
    <keybind key="S-KP_7"><action name="Execute"><command>xdotool click 1</command></action></keybind>
    
### Raspberry Pi Server
- .bashrc danach durchsuchen, wo PS1 gesetzt wird und ersetzen mit
 - `PS1='${debian_chroot:+($debian_chroot)}\[\033[01;31m\]\u@\h \[\033[00;36m\]\w\n\$\[\033[03;00m\]'`
- um Passwortabfrage für root zu aktivieren `sudo visudo` (öffnet nano)
 - Direktive `NOPASSWD:` entfernen (letzte Zeile)
- in `/etc/hostname` und `/etc/hosts` den Rechnernamen ändern
 - in `/etc/hosts` muss stehen `127.0.0.1 localhost <Rechnername aus /etc/hostname>`
- `sudo apt-get install apache2` ausführen
- `sudo apt-get install libapache2-mod-proxy-html` ausführen
- `sudo apt-get install libapache2-mod-gnutls` ausführen
- Module aktivieren
- Apache2 lässt sich starten
- die `apache2.conf`
 - `Include ports.conf` auskommentieren
 - `Include conf.d/` auskommentieren
 - `Include sites-enabled/` auskommentieren
 - `ServerName localhost` unten anfügen
 - darunter die Ports angeben:
 
    <IfModule mod_ssl.c>
        Listen 443
    </IfModule>

    <IfModule mod_gnutls.c>
        Listen 443
    </IfModule>
