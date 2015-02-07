#!/usr/bin/python
#coding=UTF-8

import subprocess
import os

# Aufruf: sudo ./cryptfile_list.py
# TODO: Abbrechen, wenn das Script nicht als root ausgeführt wird

for fso in os.listdir("/media"):
	if (fso.find("crypt_loop") != 0): continue;
	p=subprocess.Popen(["losetup", "--show", "/dev/" + fso.replace("crypt_","")],stdout=subprocess.PIPE,stdin=subprocess.PIPE);
	# TODO: was wenn der Pfad mit mehreren schließenden Klammern endet?
	# TODO: was wenn im Pfad mehrere öffnende Klammern vorkommen?
	filePath = p.stdout.readline().strip().split("(")[1].rstrip(")");
	print "/media/" + fso + " => " + filePath;
