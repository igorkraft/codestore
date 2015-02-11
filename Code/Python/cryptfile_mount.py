#!/usr/bin/python
#coding=UTF-8

import subprocess
import sys
import uuid
import os

# Aufruf: sudo ./cryptfile_mount.py container_file
# TODO: Abbrechen, wenn das Script nicht als root ausgeführt wird

if (len(sys.argv) < 2):
	print "Not enough arguments."
	exit()

# freies Loop-Gerät ermitteln
# TODO: den Fall behandeln, dass kein freies Loop-Gerät gefunden wird
p=subprocess.Popen(["losetup", "-f"],stdout=subprocess.PIPE,stdin=subprocess.PIPE)
loopDevice = p.stdout.readline().strip()
container = sys.argv[1]
mapper = "crypt_" + loopDevice.split("/")[2]
mountPoint = "/media/" + mapper

# Container im Loop-Gerät einhängen
subprocess.call(["losetup", loopDevice, container])

# Loop-Gerät im Mapper öffnen (Passwort wird abgefragt)
subprocess.call(["cryptsetup", "luksOpen", loopDevice, mapper])

# Einhängepunkt erzeugen
if not os.path.exists(mountPoint): os.makedirs(mountPoint)

# Mapper ins Dateisystem einhängen
subprocess.call(["mount", "-t", "ext4", "/dev/mapper/" + mapper, mountPoint])

# TODO: den mount point ausgeben
