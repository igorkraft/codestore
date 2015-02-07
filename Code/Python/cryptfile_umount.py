#!/usr/bin/python
#coding=UTF-8

import subprocess
import sys
import uuid
import os

# Aufruf: sudo ./cryptfile_umount.py container_file
# TODO: Abbrechen, wenn das Script nicht als root ausgeführt wird

def getAssociatedParameters(filePath):
	for fso in os.listdir("/media"):
		if (fso.find("crypt_loop") != 0): continue;
		p=subprocess.Popen(["losetup", "--show", "/dev/" + fso.replace("crypt_","")],stdout=subprocess.PIPE,stdin=subprocess.PIPE);
		currentPath = p.stdout.readline().strip().split("(")[1].rstrip(")");
		if (currentPath == filePath):
			mountPoint = "/media/" + fso
			mapper = "/dev/mapper/" + fso
			loopDevice = "/dev/" + fso.replace("crypt_","")
			return [mountPoint, mapper, loopDevice];
	return;

if (len(sys.argv) < 2):
	print "Not enough arguments.";
	exit();

# TODO: Absoluten Pfad des Dateiparameters ermitteln
filePath = sys.argv[1];

# dessen mountPoint, mapper und loopDevice ermitteln
params = getAssociatedParameters(filePath);

if (params == None):
	print "Übergebene Datei ist nicht eingehängt.";
	exit();

# TODO: die Bindung dieser drei Dinge lösen
print params;
