### Raspberry Pi 2 - Arch ARMv7

#### Herunterladen und Installieren

- http://archlinuxarm.org/platforms/armv7/broadcom/raspberry-pi-2
- SD-Karte in zwei Partitionen teilen
  - Partition 1: 100MB mit fat16 mounten als rp2_boot
  - Partition 2: ext4 mounten als rp2_root
- als root (nicht per sudo!) das Archiv auf rp2_root auspacken:
  - `bsdtar -xpf ArchLinuxARM-rpi-2-latest.tar.gz -C rp2_root`
- die Dateien im Ordner boot nach rp2_boot verschieben

#### Konfiguration

- initiale Benutzer `root:root` und `alarm:alarm`
- man kann sich nicht per SSH als root anmelden (mit `su` kann alarm zu root werden)
- mit `passwd` beide Passwörter ändern
- `pacman -S screen` installieren
- in der .bashrc den Prompt anpassen `PS1='\[\033[00;33m\]\u@\h \[\033[00;36m\]\w\n\$ \[\033[03;00m\]'`
- `pacman -S screen` installieren

#### WLAN Access Point einrichten

###### Abhängigkeiten

- `iw`, `dialog`, `wpa_supplicant`, `strip`, `binutils`, `fakeroot`, `iwlist` (könnte unnötig sein), `wireless_tools` (könnte unnötig sein), `patch`, `pkg-config`, `make`, `cc`, `gcc`, `iptables`
- `git clone https://aur.archlinux.org/hostapd-rtl.git` (könnte unnötig sein)
- `git clone https://aur.archlinux.org/hostapd-rtl871xdrv.git`

###### Hotspot automatisch nach Boot starten

- als `root` Datei `/usr/local/bin/start_wlan` anlegen (erste Zeile ist zwingend nötig)
```
#!/bin/sh

# Access Point starten
create_ap --no-virt --driver rtl871xdrv wlan0 eth0 some_name some_password
```
- die Datei für ihren Besitzer ausführbar machen
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
- `sudo systemctl enable myscript.service` (könnte unnötig sein)
