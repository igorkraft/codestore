## Installationsprotokoll

#### Deutsches Tastaturlayout

- `loadkeys de-latin1`

#### Partitionen

- alle Partitionen mit ihrer Größe auflisten `lsblk` oder `lsblk -o NAME,FSTYPE,UUID,SIZE`
- alle eingehängten Dateisysteme mit ihrem Speicherverbrauch anzeigen `df -h`
- Swap-Partition aktivieren `swapon /dev/sdxY`
- Root-Partition und die Boot-Partition einhängen
  - `mount /dev/sda3 /mnt`
  - `mkdir -p /mnt/boot`
  - `mount /dev/sda1 /mnt/boot`
  
#### Installation des Basissystems

- vor dem Hochfahren ein Netzwerkkabel anschließen
- `pacstrap -i /mnt base base-devel`
  - wenn er nachfragt, welche Pakete installiert werden sollen, dann nur `Enter` drücken
- fstab generieren `genfstab -U /mnt > /mnt/etc/fstab`
- Changeroot ins Basissystem `arch-chroot /mnt /bin/bash` um es zu konfigurieren
  - alle folgenden Befehle zur Systemkonfiguration müssen in der Changeroot ausgeführt werden!

#### Sprache und Zeitzone einstellen

- Systemsprache einstellen
  - in der `/etc/locale.gen` die Zeilen `en_US.UTF-8 UTF-8` und `de_DE.UTF-8 UTF-8` einkommentieren
  - Sprachdateien generieren `locale-gen`
  - die Datei `/etc/locale.conf` mit dem Inhalt `LANG=de_DE.UTF-8` anlegen (erste Spalte einer Sprache aus der `/etc/locale.gen`)
- Tastaturlayout einstellen
  - die Datei `/etc/vconsole.conf` mit dem Inhalt `KEYMAP=de-latin1` anlegen
- Zeitzone einstellen
  - interaktives Programm zum Ermitteln der Zeitzone `tzselect`
    - liefert zum Beispiel `Europe/Berlin`
  - Systemzeitzone setzen `ln -s /usr/share/zoneinfo/Europe/Berlin /etc/localtime`
  - Systemzeit auf den UTC-Standard einstellen `hwclock --systohc --utc`

#### Boot loader installieren

- grub-Installationsprogramm und os-prober installieren `pacman -S grub os-prober`
- grub installieren `grub-install --recheck /dev/sda`
  - alternative das versuchen `grub-install --target=i386-pc --recheck /dev/sda`
- grub Konfiguration generieren `grub-mkconfig -o /boot/grub/grub.cfg`

#### Manuelle Netzwerkkonfiguration

- Rechnernamen vergeben `echo hostname > /etc/hostname`
- DHCP für Kabelnetzwerk aktivieren `systemctl enable dhcpcd@enp2s0.service`
- (W)LAN-Schnittstellen auflisten `ip link`
  - liefert `enp2s0`, `wlp1s0` und `wlp0s18f2u4`
  - Kartentyp von `wlp1s0` ist `Realtek Semiconductor RTL8723AE`
    - die Firmware ist `rtlwifi/rtl8723fw_B.bin`
  - Kartentyp von `wlp0s18f2u4` ist `Realtek Semiconductor Corp. RTL8191SU`
    - die Firmware ist `rtlwifi/rtl8712u.bin`
- WLAN aktivieren `ip link set wlp1s0 up`
- prüfen, ob die Aktivierung funktioniert hat `ip link show wlp1s0`
  - in dem Array in spitzen Klammern muss ein `UP` vorkommen
- WLAN-Netze in Reichweite auflisten `iw dev wlp1s0 scan`
  - das geht nicht, deshalb muss jetzt `netctl` her

#### Netzwerkmanager netctl

- optionale Pakete installieren `pacman -S wpa_actiond ifplugd wpa_supplicant dhcpcd dialog`
- Netzwerkkonfiguration generieren `wifi-menu -o wlp0s18f2u4`
  - generiert eine Konfigurationsdatei in `/etc/netctl/`
  - `wifi-menu` ist im Paket `dialog` enthalten
- `netctl restart profile` startet netctl

#### Benutzerkonfiguration

- Benutzer mit eingeschränkten Rechten anlegen `useradd -m -s /bin/bash dummy`
  - legt gleichzeitig eine Gruppe dummy an
- Passwort ändern `passwd dummy`
- alle Gruppen eines Benutzers anzeigen `groups dummy`
- Gruppen mit all ihren Benutzern ausgeben `less /etc/group`
- die Verwendung von `sudo` ermöglichen
  - dummy in die Gruppe wheel aufnehmen `gpasswd -a dummy wheel`
  - `visudo` mit `nano` starten `EDITOR=nano visudo`
  - die Zeile `%wheel ALL=(ALL) ALL` einkommentieren

#### Pacman

- Konfigurationsdatei `/etc/pacman.conf`
  - darin stehen die Repositories 
  - darin die `Color`-Zeile einkommentieren (farbige Ausgaben)
- komplette Systemaktualisierung `pacman -Syu` (y=lokale Datenbank aktualisieren, u=upgrade)
- `pacache` dienst zur Verwaltung des pacman caches
- `pkgfile <file>` listet alle Pakete im öffentlichen Repository, in denen die übergebene Datei enthalten ist
  - `pkgfile -l <package>` listet alle im Paket enthaltenen Dateien (die installiert werden würden)
  - zeigt in der Bash für unbekannte Befehle automisch an, in welchen Paketen sie enthalten sind
- mit den Befehlen `pactree` und `whoneeds` kann man den Abhängigkeitsbaum aufsteigend und absteigend anzeigen
- alle gecachten Pakete löschen, die nicht installiert sind `pacman -Sc`
- alle gecachten Pakete löschen `pacman -Scc`
- `/var/cache/pacman/pkg/` enthält die gecachten Pakete 
- ein Paket in den Cache herunterladen ohne es zu installieren `pacman -Sw <package>`
- `/etc/pacman.d/mirrorlist` enthält offizielle Repositories und wird in die Konfigurationsdatei inkludiert
  - zusätzliche Repositories können auch direkt in der Konfigurationsdatei eingetragen werden
- AUR ist kein offizielles Repository
- alle Dateien eines Pakets anzeigen `pacman -Ql some-package`
  - kann man auch auf der Webseite machen

#### X Server und AMD Treiber

- `/etc/pacman.conf` für den Catalyst-Treiber erweitern (oberhalb der Repository-Includes)
```
[catalyst]
Server = http://catalyst.wirephire.com/repo/catalyst/$arch
SigLevel = Never
```
- die Catalyst-Datenbank herunterladen `sudo pacman -Syy`
- das Paket `libgl` installieren
  - mesa-libgl als libgl-Anbieter wählen (Aufgrund eines Abhängigkeitsrings kann man nicht die catalyst-Variante nehmen)
- das Paket `xorg-server` installieren
  - als Anbieter für libgl `catalyst-libgl` wählen -> danach fragt er nicht, wenn man vorher die mesa-Bibliothek installiert hat
  - als Anbieter für xf86-input-driver `xf86-input-evdev` wählen
- das Paket `xorg-server-utils` installieren
- das Paket `xf86-video-amdgpu` oder `xf86-video-ati` installieren 
  - TODO: prüfen welcher wirklich gebraucht wird
- die Konfigurationsdateien aus `/usr/share/X11/xorg.conf.d` nach `/etc/X11` kopieren
- die Logdatei `/var/log/Xorg.0.log`
  - sucht man nach `LoadModule`, dann findet man alle geladenen Treiber
- das Paket `xorg-xinit` installieren (enthält startx)
  - der Server startet nicht, weil er `vesa` und `fbdev` nicht findet
  - `xf86-video-vesa` ist der Standard-Grafiktreiber, der keine 2D- und 3D-Beschleunigung unterstützt
  - den vesa-Treiber nicht einsetzen (oder nur aus Verzweiflung)
- die Pakete `xf86-video-vesa` und `xf86-video-fbdev` installieren
- das Paket `xterm` installieren -> jetzt fährt der Xserver hoch
- das Paket `catalyst-utils` installieren -> geht nicht, weil der installierte X server zu neu ist
- xorg-server downgraden
  - für Downgrades kann man das Programm `agetpkg` (https://github.com/seblu/agetpkg benutzen)
  - nach dem clonen wird es (wie alle AUR-Pakete) mit `makepkg -sri` gebaut
  - der Befehl `makepkg -sri` erwartet, dass im Arbeitsverzeichnis ein Buildscript `PKGBUILD` liegt
  - mit `agetpkg -h` prüfen, ob die Installation erfolgreich war
  - Paketversionen im Arch Linux Archiv recherchieren `https://archive.archlinux.org/packages/` (Sortierung nach Paketnamen)
  - letzter passender xorg-server ist xorg-server-1.17.4-2-x86_64.pkg.tar.xz
  - älteren xorg-server installieren `agetpkg -i xorg-server 1.17.4 2` (statt des Minus in der Versionsnummer ein Leerzeichen einfügen!)
  - mit `*` alle xorg-Pakete für den Downgrade markieren
  - alle in Konflikt stehenden Abhängigkeiten entfernen -> das lässt den Downgrade fehlschlagen, weil der ältere xorg-server die Abhängigkeiten braucht
    -> die Abhängigkeiten müssen zuerst gedowngraded werden
    -> TODO schauen, wie man rausfindet, welche Versionen dieser Abhängigkeiten für den älteren xorg-server nötig sind (in der .PKGINFO des Archivpakets stehen die Versionsnummer nicht)
    -> TODO vielleicht hilft es, wenn man die Abhängigkeiten hard entfernt -> die werden für die Installation des alten xorg-server gebraucht
    -> TODO im catalyst-Repository gibt es einen xorg-server, der vielleicht funktioniert
  - den hier beschribenen Programmen `https://wiki.archlinux.org/index.php/Downgrading_packages` kann man keine Versionsnummer für einen Downgrade übergeben
    -> TODO testen, ob das Alternativen sind

#### X Server und AMD Treiber (zweiter Versuch)

- System neu aufsetzen und ein Backup machen
  - Partitionsbackup erstellen `dd if=/dev/sda3 | bzip2 -9f > file.img.bz2`
- `https://archive.archlinux.org/packages/x/xorg-server/xorg-server-1.17.4-2-x86_64.pkg.tar.xz` herunterladen
  - curl Download `curl -O https://archive.archlinux.org/packages/x/xorg-server/xorg-server-1.17.4-2-x86_64.pkg.tar.xz`
  - dieses Paket wurde am 01-Nov-2015 um 09:42 hochgeladen
  - alle Pakete dieses Zeitpunktes sind hier zu finden `https://archive.archlinux.org/repos/2015/11/01/`
- mit `sudo pacman -U xorg-server-1.17.4-2-x86_64.pkg.tar.xz` installieren
  - liefert Fehler `xf86-input-evdev and xorg-server are in conflict (xorg-server<1.18.0)`
- Paket `xf86-input-evdev` liegt im Repository `extra` (siehe `https://www.archlinux.org/packages/?q=xf86-input-evdev`)
- `https://archive.archlinux.org/repos/2015/11/01/extra/os/x86_64/xf86-input-evdev-2.9.2-1-x86_64.pkg.tar.xz` herunterladen
- für `xf86-input-evdev-2.9.2-1` will er diese Pakete auch heruntergeladen `libevdev-1.5.2-1` `mtdev-1.1.5-1`
  - zum oben angegebenen Zeitpunkt waren dieses Pakete aktuell `libevdev-1.4.4-1` `mtdev-1.1.5-1`
  - `https://archive.archlinux.org/repos/2015/11/01/extra/os/x86_64/libevdev-1.4.4-1-x86_64.pkg.tar.xz` herunterladen und installieren
- `sudo pacman -U xf86-input-evdev-2.9.2-1-x86_64.pkg.tar.xz`
- erneuter Installationsversuch von `xorg-server-1.17.4-2` liefert eine Liste mit allen nötigen Abhängigkeiten
  - für jede Abhängigkeit die Version prüfen und gegebenenfalls downgraden
  - für die Abhängigkeiten der Abhängigkeiten ebenfalls prüfen
- ok, das ist zu anstrengend, mal schauen, ob es nach folgender Anleitung klappt
  - `https://wiki.archlinux.org/index.php/Arch_Linux_Archive#How_to_restore_all_packages_to_a_specific_date`
  - `Server=https://archive.archlinux.org/repos/2015/11/01/$repo/os/$arch` als einzige Zeile in `/etc/pacman.d/mirrorlist` eintragen
  - Downgrade-Befehl absetzen `pacman -Syyuu`
- `sudo pacman xorg-server` absetzen

#### X Server und AMD Treiber (dritter Versuch mit proprietärem Treiber)

- den Treiber herunterladen `http://support.amd.com/de-de/download/desktop?os=Linux%20x86_64`
- den Treiber ausführen `sudo .\amd-driver-installer-15.302-x86.x86_64.run`
  - Fehler: `Detected X Server version 'Xserver _64a' is not supported`
- Google sagt: `https://bbs.archlinux.org/viewtopic.php?id=193773`
  - mit der Rollback-Maschine zum letzten unterstützten XServer downgraden
  - den Treiber installieren
  - das Repository `http://catalyst.wirephire.com/repo/xorg116/` über `core` in der `pacman.conf` eintragen
  - das System normal aktualisieren

#### VirtualBox Guest Additions

- `virtualbox-guest-utils` installieren (enthält Kernelmodule)
- `virtualbox-host-dkms` installieren (braucht man zum kompilieren der Kernelmodule)
  - das geht bei mir nicht, weil es Dateikonflikte gibt

#### TODO

- einen nginx als Reverse-Proxy zu konfigurieren scheint echt einfach zu sein

- den Long-TimeSupport-Kernel installieren (die Gründe stehen hier)
  - https://wiki.archlinux.org/index.php/System_maintenance#Install_the_linux-lts_package
  
- verhindern, dass dummy mit sudo visudo aufrufen kann (siehe sudo-Artikel)

Um den ersten Abschnitt (System administration) aus general recommendations durchzuspielen fehlen noch diese Artikel:
- https://wiki.archlinux.org/index.php/Security

Für den zweiten Abschnitt fehlt noch das hier:
- https://wiki.archlinux.org/index.php/Multilib
- https://wiki.archlinux.org/index.php/PKGBUILD
- https://wiki.archlinux.org/index.php/Arch_User_Repository
- https://wiki.archlinux.org/index.php/AUR_helpers
