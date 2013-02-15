### Raspberry Pi
- WLAN-Treiber sind schon in Raspbian enthalten
- Cursor per Tastatur steuern
 - Quelle: http://www.raspberrypi.org/phpBB3/viewtopic.php?f=29&t=27857
 - `sudo apt-get install xwit xdotool`
 - die Sektion `<keyboard>` in der `/home/pi/.config/openbox/lxde-rc.xml` erweitern (Code siehe unten)


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
