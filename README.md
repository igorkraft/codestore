### TODO
- WebDAV und Internet weiterleiten testen (python-webdav installieren)
- http://wiki.ubuntuusers.de/RAM-Disk_erstellen
- http://www.raspberrypiblog.de/software/raspberry-pi-vm-v0-8-fur-entwickler-verfugbar/287
- http://wiki.ubuntuusers.de/YAGF
- kobo-deluxe-portable.softonic.de/
- http://imgur.com/yPK23
- http://www.appflower.com/cms/home
- http://www.pcgameshardware.de/aid,1014100/Suidobashi-Kuratas-Mech-Smartphone/Technologie/News/bildergalerie/?iid=1970245
- http://eyeos-apps.org/content/show.php/Sonic+the+Hedgehog?content=69840
- http://www.javac.info/closures-v05.html mal lesen
- http://www.scheissewasschenkichmutti.de/
- http://www.golem.de/news/raspberry-pi-basteln-fuer-geduldige-1207-93058.html
- http://de.wikipedia.org/wiki/Owncloud
- http://www.golem.de/news/raspberry-pi-firefox-os-laeuft-mit-60-fps-1208-93898.html
- http://www.golem.de/news/raspberry-pi-steuerungs-und-bastelplatine-gertboard-ist-fertig-1208-93747.html
- http://mango.blender.org/

-
    NewTab-Bookmark
    javascript:window.open();stop();
    "icon": "data:image/x-icon;base64,AAABAAEAEBAAAAEAIADMAQAAFgAAAIlQTkcNChoKAAAADUlIRFIAAAAQAAAAEAgGAAAAH/P/YQAAAZNJREFUOI19k0+qE0EQh7/uGR8DyRjwqTzIIiAx3iA7DyCCx5iVq5whV/AqkgNEybjKKvusIiLGjJi86emecjE9eW3+vIKChqa++v2quhUPoU5S83+Izzo4o4JiDdwAT7MsewUk1loNoLUWrbVdrVbf5/P5D+Av4IA6DgAxcJvn+bvRaPQhiqKXQQNExOx2u29Zln2azWYWOITyNNCZTqfvrbWfRSQXkS8n+VVE8sVi8RG48w0JFUTALZAA91yOKkmSN96qAlQcDC06HA4K2ANPrgCkLEvrG+tWgbQqyrJUQHWlGECcc45gQ3Fwqaqq0iJiAugZwFpbnwKOezfGKA+oHwE4mnlp/AyOYYypadYTXyhuAa1FObPgFdz7Duq0GqiNMS68PwPQrPAqwFprebB9nIEG4hPARQve5o2v0S0gAtJer/dLKXXH+Uc6Ajqdzh+gS/NWTORVPB+Px68nk0k6HA67NB/lYvb7/bIoiu5yufztnNur9Xr9tiiKF2maPhsMBimPPyQAvdls7Ha7/amU2v8DfxHJizzoUP0AAAAASUVORK5CYII=",

mvn install -Dmaven.test.skip=true

git clone https://github.com/igorkraft/codestore.git
git add --all
git commit -a -m "new code"
git push

Helligkeitssteuerung für Nvidia-Karten:
# Add to your "Device" section in /etc/X11/xorg.conf and restart X
Option "RegistryDwords" "EnableBrightnessControl=1"