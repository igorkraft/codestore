## RetroArch

#### Installation

- `retroarch retroarch-autoconfig-udev libretro-snes9x libretro-mgba` installieren
- die Cores `libretro-snes9x` und `libretro-mgba` hätten auch mit dem internen `Online Updaten` heruntergeladen werden können
  - im `Main Menu`
  - funktioniert, wie ein anwendungsinterner Paketmanager

#### Konfiguration

- Konfigurationsdatei `~/.config/retroarch/retroarch.cfg`
  - `audio_driver = "alsa"` aktiviert Sound
  - `user_language = "0"` Menüsprache ist Englisch
  - `video_driver = "gl"` OpenGL für das Rendering verwenden
    - weitere Werte `"gdi", "xvideo", "sdl", "d3d"`
- mit `-c` eine Config-Datei angeben, die einzelne Eigenschaften überschreibt
  - retroarch ergänzt fehlende Eigenschaften mit den Defaults

##### Start mit ROM-spezifischer Konfiguration

```
#! /bin/bash

SHADER=~/.config/retroarch/shaders/shaders_glsl/handheld/gb-palette-dmg.glslp

echo -n "
video_shader = \"$SHADER\"
video_shader_enable = \"true\"
video_fullscreen = \"false\"
pause_nonactive = \"false\"
audio_driver = \"alsa\"
input_toggle_fast_forward = \"nul\"
input_player1_a = \"c\"
input_player1_b = \"x\"
user_language = \"0\"
video_driver = \"gl\"
" > config.cfg

(sleep 1 ; i3-msg border pixel 0 ; i3-msg floating toggle) &

retroarch -L /usr/lib/libretro/mgba_libretro.so \
-c config.cfg \
/path/to/rom.gb

rm content_history.lpl
rm config.cfg
```

#### Shortcuts

- `http://emulation.gametechwiki.com/index.php/Using_RetroArch#Hotkeys`
- `F1` Menü öffnen
- `F2` Save state
- `F4` Load state
- `F8` Screenshot
- `F9` Mute
- `Backspace` zum übergeordneten Menü wechseln

#### Sonstiges

- Icons im Menü nicht als schwarze Rechtecke darstellen
  - `http://buildbot.libretro.com/assets/frontend/assets.zip` herunterladen
  - nach `/usr/share/retroarch/assets` auspacken
  - `Menu -> Settings -> User Interface -> Appearance -> Menu Icon Theme`
- Core und Content als Parameter übergeben
  - `retroarch -L /path/to/core /path/to/rom`

#### Shader

- Quellen
  - https://emulation-general.fandom.com/wiki/Shaders_and_Filters
  - https://github.com/libretro/common-shaders
- funktionieren nur mit den Video-Treibern `gl` und `gdi`
- um mit dem Online Updater glsl-Shader zu installieren, muss man retroarch als root starten
  - er versucht die shader nach `/usr/share/libretro/shaders` auszupacken
  - `retroarch -v --log-file retro.log`
- zum laden eines Shader im laufenden Spiel per Quick-Menu Shader Presets laden
- Shader können auch in der Konfiguration voreingestellt werden
```
video_shader_enable = "true"
video_shader = "/path/to/shader.glslp"
```