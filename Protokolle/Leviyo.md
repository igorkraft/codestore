## Leviyo Protokoll

#### Netzwerkkonfiguration

- Ethernet-Adapter `enp0s20f0u1u1u1`
- WLAN-Adapter `wlp1s0`
- DHCP für Ethernet starten `sudo systemctl start dhcpcd@enp0s20f0u1u1u1.service`
  - sollte nicht enabled werden, weil Hochfahren ohne angeschlossenes Ethernet ewig braucht
- WLAN aktivieren   `ip link set wlp1s0 up`
- WLAN deaktivieren `ip link set wlp1s0 down`
  - wenn das die Meldung `RTNETLINK answers: Operation not possible due to RF-kill` bringt, dann
    - `rfkill unblock all` absetzen
  - wenn der WLAN-Start nicht klappt und
    - `systemctl status netctl@some_wifi.service` den Fehler `The interface of network profile 'some_wifi' is already up` liefert
    - dann `ip link set wlp1s0 down` absetzen und WLAN erneut starten
- `netctl` installieren und konfigurieren (siehe karda-Protokoll)
  - der Start von netctl schlägt fehl, wenn zuvor das WLAN manuell aktiviert wurde
  - meldet netctl beim Start einen Fehler, dann `rfkill unblock all` absetzen
- Wireless Access Point (Hotspot) starten
  - `sudo create_ap --no-virt wlp1s0 lo some_name some_password`

#### Programme installieren

- `mc git dialog wpa_supplicant netctl xorg xorg-xinit i3 lxterminal rofi thunar zenity firefox ntfs-3g minetest`
- `pkgfile alsa-utils create_ap zip unzip numlockx xsel xpra feh p7zip`
  - durch minetest wird Sound installiert
  - `feh` setzt ein Hintergrundbild
- optionale Programme
  - `gpicview openvpn freerdp remmina gparted dosfstools inkscape gimp tk openbsd-netcat diffuse libreoffice-fresh-de`
  - `retroarch retroarch-autoconfig-udev libretro-snes9x libretro-mgba`
  - `gitk` ist von `tk` abhängig

#### AUR Pakete installieren

- `i3-wm-iconpatch sublime-text-dev libretro-tgbdual-git p7zip-gui libretro-mednafen-supergrafx-git`
- `makepkg -sri`

#### X-Server

- `~/.xinitrc` anlegen
  ```
  # Bildschirmauflösung anpassen
  xrandr --newmode 1600x900_60.00 118.25 1600 1696 1856 2112 900 903 908 934 -hsync +vsync
  xrandr --addmode eDP-1 1600x900_60.00
  xrandr --output eDP-1 --mode 1600x900_60.00 --output DP-1 --left-of eDP-1 --primary

  # Hintergrundbild setzen
  feh --bg-max /path/to/image

  # Window Manager starten
  exec i3
  ```
- verhindern, dass der Bildschirm bei Inaktivität geschwärzt wird
  - `/etc/X11/xorg.conf.d/10-monitor.conf` anlegen
  ```
  Section "Monitor"
      Identifier "LVDS0"
      Option "DPMS" "false"
  EndSection
  Section "ServerLayout"
      Identifier "ServerLayout0"
      Option "StandbyTime" "0"
      Option "SuspendTime" "0"
      Option "OffTime"     "0"
      Option "BlankTime"   "0"
  EndSection
  ```

#### Dateisysteme konfigurieren

- fstab
```
# NTFS-Dateisystem einhängen
UUID=341A21A91A2168D6	/media/WinData	ntfs	defaults,nls=utf8,umask=007,uid=1000,gid=10	0	0

# verschlüsseltes Dateisystem-Image einhängen
/dev/mapper/data_mapper	/media/data	ext4	defaults	0	0

# user home einhängen
/media/data/user /home/user none bind 0 0
```

- crypttab
```
data_mapper /media/WinData/data.img none luks,retry=1,cipher=aes-xts-plain64:sha512
```

#### Sonstiges

- NTFS-Dateisysteme mit Schreibberechtigung einhängen 
  `sudo mount -t ntfs-3g -o rw /dev/sdc1 /mnt`
- liefert `makepkg -s` den Fehler `Unbekannter öffentlicher Schlüssel ...` dann
  - `makepkg -s --skippgpcheck` ausführen
- NTP-Daemon aktivieren
  - `/etc/systemd/timesyncd.conf` anpassen
  ```
  [Time]
  NTP=0.arch.pool.ntp.org 1.arch.pool.ntp.org 2.arch.pool.ntp.org 3.arch.pool.ntp.org
  FallbackNTP=0.pool.ntp.org 1.pool.ntp.org
  ```
  - `sudo timedatectl set-ntp true` to start and enable
- default keyboard layout für xorg anpassen
  - `localectl set-x11-keymap de`
- `pkgfile --update` um Paket-Datei-Datenbank zu initialisieren
- in der `/etc/pacman.d/mirrorlist` einen deutschen Server ganz eintragen

#### TODOs

- Shortcuts für Bildschirmhelligkeit
- Shortcuts für Lautstärkeregelung
- Shortcut für Umbenennen von workspaces
- einen Weg finden, wie das WLAN zuverlässig nach dem Hochfahren funktioniert (rfkill)
- Feststelltaste deaktivieren
- Paketmanager für AUR ausprobieren

#### Bildschirmhelligkeit (als i3-Konfiguration aufnehmen)

- maximale Helligkeit ermitteln
  - `cat /sys/class/backlight/intel_backlight/max_brightness`
- aktuelle Helligkeit ermitteln
  - `cat /sys/class/backlight/intel_backlight/brightness`
- Helligkeit setzen
  - `sudo tee /sys/class/backlight/intel_backlight/brightness <<< 300`
