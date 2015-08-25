### Raspberry Pi 2 - Arch ARMv7

#### Herunterladen und Installieren

- http://archlinuxarm.org/platforms/armv7/broadcom/raspberry-pi-2
- SD-Karte in zwei Partitionen teilen
  - Partition 1: 100MB mit fat16 mounten als rp2_boot
  - Partition 2: ext4 mounten als rp2_root
- als root (nicht per sudo!) das Archiv auf rp2_root auspacken:
  - `bsdtar -xpf ArchLinuxARM-rpi-2-latest.tar.gz -C rp2_root`
- die Dateien im Ordner boot nach rp2_boot verschieben
