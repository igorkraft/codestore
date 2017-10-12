## Leviyo Protokoll

#### Netzwerkkonfiguration

- Ethernet-Adapter `enp0s20f0u1u1u1`
- WLAN-Adapter `wlp1s0`
- DHCP für Ethernet starten `sudo systemctl start dhcpcd@enp0s20f0u1u1u1.service`
  - sollte nicht enabled werden, weil Hochfahren ohne angeschlossenes Ethernet ewig braucht
- WLAN aktivieren   `ip link set wlp1s0 up`
- WLAN deaktivieren `ip link set wlp1s0 down`
  - wenn das die Meldung `RTNETLINK answers: Operation not possible due to RF-kill` bringt, dann
    - `rfkill unblock all` absetzen (erfordert Paket rfkill)
- `netctl` installieren und konfigurieren (siehe karda-Protokoll)
  - der Start von netctl schlägt fehl, wenn zuvor das WLAN manuell aktiviert wurde
  - meldet netctl beim Start einen Fehler, dann `rfkill unblock all` absetzen

#### Programme installieren

- `rfkill netctl xorg xorg-xinit i3 lxterminal rofi pcmanfm firefox ntfs-3g gpicview freerdp remmina`
- AUR-Paket für Sublime Text 3 installieren
  - `https://aur.archlinux.org/sublime-text-dev.git` klonen
  - `makepkg -s` (Abhängigkeiten validieren und bauen)
  - `makepkg -i` (Paket installieren)
- AUR-Paket `i3-wm-iconpatch` installieren

#### X-Server

- `~/.xinitrc` anlegen
  ```
  # Tastaturlayout auf deutsch stellen
  setxkbmap de

  # Bildschirmauflösung anpassen
  xrandr --newmode 1600x900_60.00 118.25 1600 1696 1856 2112 900 903 908 934 -hsync +vsync
  xrandr --addmode eDP-1 1600x900_60.00
  xrandr --output eDP-1 --mode 1600x900_60.00

  # Window Manager starten
  exec i3
  ```

#### Sonstiges

- NTFS-Dateisysteme mit Schreibberechtigung einhängen 
  `sudo mount -t ntfs-3g -o rw /dev/sdc1 /mnt`
- liefert `makepkg -s` den Fehler `Unbekannter öffentlicher Schlüssel ...` dann
  - `makepkg -s --skippgpcheck` ausführen

#### TODOs

- beim Start einen Key-Mode öffnen, in dem Sublime-Fenster auf das Scratchpad gelegt werden
- Terminal-Schriftart permanent vergrößern
- Sound einrichten
- Shortcuts für Bildschirmhelligkeit
- Shortcut das Umbenennen von workspaces
- diese Datei sichern
- Theme von PCManFM anpassen (+Icons)
  - unter `menu://applications/Entwicklung` gibt es einen Icon Browser
- `SpaceFM` testen
- einen Weg finden, wie das WLAN zuverlässig nach dem Hochfahren funktioniert (rfkill)
- NUM-Pad bei Start aktivieren

#### Bildschirmhelligkeit (als i3-Konfiguration aufnehmen)

- maximale Helligkeit ermitteln
  - `cat /sys/class/backlight/intel_backlight/max_brightness`
- aktuelle Helligkeit ermitteln
  - `cat /sys/class/backlight/intel_backlight/brightness`
- Helligkeit setzen
  - `sudo tee /sys/class/backlight/intel_backlight/brightness <<< 300`
  