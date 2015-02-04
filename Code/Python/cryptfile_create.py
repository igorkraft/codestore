#!/usr/bin/python
#coding=UTF-8

import subprocess
import gtk
import platform
import sys

# Aufruf: sudo ./cryptfile_create.py container_file 30
# Parameter: Name des zu erzeugenden Parameters, Größe des Parameters in MB

if (len(sys.argv) < 3):
	print "Not enough arguments."
	exit()

version = platform.python_version()
dialog = gtk.MessageDialog(
	None, 
	gtk.DIALOG_MODAL|gtk.DIALOG_DESTROY_WITH_PARENT, 
	gtk.MESSAGE_QUESTION,
	gtk.BUTTONS_OK, 
	str(sys.argv[1]))
#dialog.run()


#subprocess.call(["dd", "if=/dev/urandom", "of=" + sys.argv[1], "bs=1M", "count=" + sys.argv[2]])

p=subprocess.Popen(["losetup", "-f"],stdout=subprocess.PIPE,stdin=subprocess.PIPE)
print (p.stdout.readline().strip())
