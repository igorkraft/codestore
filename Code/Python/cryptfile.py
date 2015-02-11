#!/usr/bin/python
#coding=UTF-8

import sys
import os
import subprocess
import shlex

# TODO: Shellbefehle kann man auch so ausführen: retcode = os.system("echo 'foo' &> /dev/null")

class Associations:
	mountPoint = ""
	mapper = ""
	loopDevice = ""

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
	
	if (sys.argv[1] == "create"):
		if (len(sys.argv) < 4):
			printUsage();
			exit();
		if (os.path.isfile(sys.argv[2]) or os.path.isdir(sys.argv[2])):
			print "[" + sys.argv[2] + "] already exists.";
			exit();
		if (not unicode(sys.argv[3]).isnumeric()):
			print "[" + sys.argv[3] + "] is not a number.";
			exit();
		createContainer(sys.argv[2], sys.argv[3]);
	
	if (sys.argv[1] == "mount"):
		if (len(sys.argv) < 3):
			printUsage();
			exit();
		if (not os.path.isfile(sys.argv[2])):
			print "[" + sys.argv[2] + "] is not a file.";
			exit();
		if (getAssociations(sys.argv[2]) != None):
			print "[" + sys.argv[2] + "] is already mounted.";
			exit();
		if (subprocess.call(shlex.split("losetup -f"), stdout=open(os.devnull, 'w'), stderr=subprocess.STDOUT) != 0):
			print "No empty loop device found.";
			exit();
		mountContainer(sys.argv[2]);
	
	if (sys.argv[1] == "umount"):
		if (len(sys.argv) < 3):
			printUsage();
			exit();
		associations = getAssociations(sys.argv[2]);
		if (associations == None):
			print "[" + sys.argv[2] + "] is not mounted.";
			exit();
		unmountContainer(associations);
	
def printUsage():
	print "TODO: print usage";

def getAssociations(filePath):
	filePath = os.path.realpath(filePath);
	for fso in os.listdir("/media"):
		if (fso.find("crypt_loop") != 0): continue;
		# TODO: shlex.split benutzen
		p=subprocess.Popen(["losetup", "--show", "/dev/" + fso.replace("crypt_","")],stdout=subprocess.PIPE,stdin=subprocess.PIPE);
		currentPath = p.stdout.readline().strip().split("(")[1].rstrip(")");
		if (currentPath == filePath):
			result = Associations();
			result.mountPoint = "/media/" + fso
			result.mapper = "/dev/mapper/" + fso
			result.loopDevice = "/dev/" + fso.replace("crypt_","")
			return result;
	return;

def listMountedContainers():
	print "TODO listMountedContainers";

def createContainer(container, size):
	print "TODO createContainer"

def mountContainer(container):
	print "TODO mountContainer";
	
def unmountContainer(associations):
	print "TODO unmountContainer"

main();
