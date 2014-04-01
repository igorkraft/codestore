### shellinabox

- shellinabox installieren

#### SSL deaktivieren

- `/etc/init.d/shellinabox` zum bearbeiten öffnen
- in der Funktion `d_start()` werden die Parameter festgelegt, mit denen shellinabox gestartet wird
- dort hinter dem Parameter -q den Parameter -t hinzufügen
 - -t startet shellinabox ohne SSL-Unterstützung
- `sudo service shellinabox restart`
