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

#### Apache Webserver

- siehe `Raspberry Pi - Arch ARMv6.md`