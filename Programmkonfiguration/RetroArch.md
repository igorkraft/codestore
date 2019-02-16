## RetroArch

#### Installation

- `retroarch retroarch-autoconfig-udev libretro-snes9x libretro-mgba` installieren
- die Cores `libretro-snes9x` und `libretro-mgba` hätten auch mit dem internen `Online Updaten` heruntergeladen werden können
  - im `Main Menu`
  - funktioniert, wie ein anwendungsinterner Paketmanager

#### Konfiguration

- `Menu -> Settingd -> User -> Language: English`
- `Menu -> Settings -> Driver -> Audio Driver: alsa`
- `Menu -> Settings -> User Interface -> Appearance -> Menu Icon Theme`
- bei bedarft in der `/home/<user>/.config/retroarch/retroarch.cfg` für `video_driver` `gdi` eintragen

#### Shortcuts

- `http://emulation.gametechwiki.com/index.php/Using_RetroArch#Hotkeys`
- `F1` Menü öffnen
- `Backspace` zum übergeordneten Menü wechseln

#### Sonstiges

- Icons im Menü nicht als schwarze Rechtecke darstellen
  - `http://buildbot.libretro.com/assets/frontend/assets.zip` herunterladen
  - nach `/usr/share/retroarch/assets` auspacken
- Core und Content als Parameter übergeben
  - `retroarch -L /path/to/core /path/to/rom`
  - optional mit `-c` eine Config-Datei angeben, die einzelne Eigenschaften überschreibt

#### Shader

- um mit dem Online Updater glsl-Shader zu installieren, muss man retroarch als root starten
  - er versucht die shader nach `/usr/share/libretro/shaders` auszupacken
  - `retroarch -v --log-file retro.log`
- zum laden eines Shader im laufenden Spiel per Quick-Menu Shader Presets laden
- Shader können auch in der Konfiguration voreingestellt werden
```
video_shader_enable = "true"
video_shader = "/path/to/shader.glslp"
```