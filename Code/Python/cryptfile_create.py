#!/usr/bin/python
#coding=UTF-8

import subprocess
import sys
import uuid
import os

# Aufruf: sudo ./cryptfile_create.py container_file 30
# Parameter: Name des zu erzeugenden Parameters, Größe des Parameters in MB

if (len(sys.argv) < 3):
	print "Not enough arguments."
	exit()

container = str(uuid.uuid4())
print container

# Datei mit Zufallszahlen erzeugen
subprocess.call(["dd", "if=/dev/urandom", "of=" + sys.argv[1], "bs=1M", "count=" + sys.argv[2]])

# freies Loop-Gerät ermitteln
p=subprocess.Popen(["losetup", "-f"],stdout=subprocess.PIPE,stdin=subprocess.PIPE)
loopDevice = p.stdout.readline().strip()

# Zufallsdatei im Loop-Gerät einhängen
subprocess.call(["losetup", loopDevice, sys.argv[1]])

# LUKS-Dateisystem anlegen (Passwort wird abgefragt)
subprocess.call(["cryptsetup", "luksFormat", "-c", "aes-xts-plain64", "-s", "512", "-h", "sha512", loopDevice])

# das verschlüsselte Gerät öffnen (Passwort wird erneut abgefragt)
subprocess.call(["cryptsetup", "luksOpen", loopDevice, container])

# virtuelles Dateisystem anlegen
subprocess.call(["mkfs.ext4", "/dev/mapper/" + container])

# verschlüsseltes Gerät schließen
subprocess.call(["cryptsetup", "luksClose", container])

# Kontainer aus Loop-Gerät entfernen
subprocess.call(["losetup", "-d", loopDevice])

# den Aufrufer zum Besitzer des Kontainers machen
subprocess.call(["chown", os.environ['SUDO_USER'] + ":" + os.environ['SUDO_USER'], sys.argv[1]])
