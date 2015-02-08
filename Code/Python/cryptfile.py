#!/usr/bin/python
#coding=UTF-8

import sys
import os

def main():
	
	# Berechtigung prüfen
	if (os.geteuid() != 0):
		exit("no permissions");
		
	# Argumente prüfen
	if (len(sys.argv) == 1):
		printUsage();
		exit();
		
	if (sys.argv[1] != "list" and
		  sys.argv[1] != "create" and
		  sys.argv[1] != "mount" and
		  sys.argv[1] != "umount"
		 ):
		printUsage();
		exit();
	
	if (sys.argv[1] == "list"):
		listMountedContainers();
	
	if (sys.argv[1] == "mount"):
		# TODO: prüfen, ob ein weiterer Parameter angegeben wurde
		# TODO: prüfen, ob der Parameter zu einem existierenden FSO vom Typ Datei ist
		# TODO: prüfen, ob die Datei nicht bereits eingehängt ist
		# TODO: prüfen, ob noch ein freies loop device vorhanden ist
	
def printUsage():
	print "TODO: print usage";

def listMountedContainers():
	print "TODO listMountedContainers";

main();
