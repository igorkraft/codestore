## Pacman

#### Allgemeines

- AUR-Packages werden nach der Installation von pacman als Pakete erkannt
- `pkgfile <file>` listet alle Pakete im öffentlichen Repository, in denen die übergebene Datei enthalten ist
- `pkgfile -l <package>` listet alle im Paket enthaltenen Dateien (die installiert werden würden)
- mit den Befehlen `pactree` und `whoneeds` kann man den Abhängigkeitsbaum aufsteigend und absteigend anzeigen
- `/var/cache/pacman/pkg/` enthält die gecachten Pakete
- Pakete haben die Endung `.pkg.tar.xz`

#### Konfiguration

- `/etc/pacman.conf`
- enthält Liste mit Repositories
- `/etc/pacman.d/mirrorlist` enthält offizielle Repositories
  - wird in die Konfigurationsdatei inkludiert
  - zusätzliche Repositories können auch direkt in `/etc/pacman.conf` eingetragen werden
- `Color` farbige Ausgaben
- `SigLevel    = Optional TrustAll` behebt Signaturprobleme

#### Befehle

- `pacman -S <package>` Paket installieren
- `pacman -Sw <package>` Paket nur herunterladen
- `pacman -U <package>` Paket aus dem Dateisystem installieren
- `pacman -Syu` installierte Pakete aktualisieren (y=lokale Datenbank aktualisieren, u=upgrade)
- `pacman -Sc` alle gecachten Pakete löschen, die nicht installiert sind (alte Versionen)
- `pacman -Scc` alle gecachten Pakete löschen
- `pacman -Qqe` Liste explizit installierter Pakete anzeigen (einschließlich AUR-Paketen)
- `pacman -Qqen` Liste explizit installierter Pakete anzeigen (ohne AUR-Pakete)
- `pacman -Ql some-package` alle Dateien eines Pakets anzeigen (Alternative zu `pkgfile -l <package>`)
  - kann man auch auf der Webseite machen
- `pacman -Rsn <package>` deinstalliert das Paket
  - damit können auch AUR-Pakete deinstalliert werden
  - `-s` ebenfalls alle Abhängigkeiten entfernen, die von keinen anderen Paketen gebraucht werden
  - `-n` Konfigurationsdateien ebenfalls entfernen
