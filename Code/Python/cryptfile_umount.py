#!/usr/bin/python
#coding=UTF-8

import shutil
import subprocess
import sys
import uuid
import os

# Aufruf: sudo ./cryptfile_umount.py container_file
# TODO: Abbrechen, wenn das Script nicht als root ausgeführt wird

class Associations:
	mountPoint = ""
	mapper = ""
	loopDevice = ""

def getAssociations(filePath):
	for fso in os.listdir("/media"):
		if (fso.find("crypt_loop") != 0): continue;
		p=subprocess.Popen(["losetup", "--show", "/dev/" + fso.replace("crypt_","")],stdout=subprocess.PIPE,stdin=subprocess.PIPE);
		currentPath = p.stdout.readline().strip().split("(")[1].rstrip(")");
		if (currentPath == filePath):
			result = Associations();
			result.mountPoint = "/media/" + fso
			result.mapper = "/dev/mapper/" + fso
			result.loopDevice = "/dev/" + fso.replace("crypt_","")
			return result;
	return;

if (len(sys.argv) < 2):
	print "Not enough arguments.";
	exit();

# Absoluten Pfad des Dateiparameters ermitteln
filePath = os.path.realpath(sys.argv[1])

# dessen mountPoint, mapper und loopDevice ermitteln
associations = getAssociations(filePath);

if (associations == None):
	print "Übergebene Datei ist nicht eingehängt.";
	exit();

# virtuelles Dateisystem aushängen
subprocess.call(["umount", associations.mountPoint]);

# Mapper auflösen
subprocess.call(["cryptsetup", "luksClose", associations.mapper]);

# Kontainer aus Loop-Gerät entfernen
subprocess.call(["losetup", "-d", associations.loopDevice])

# MountPoint entfernen
shutil.rmtree(associations.mountPoint)
