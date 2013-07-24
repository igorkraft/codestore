#!/usr/bin/env python
# -*- coding: utf-8 -*-

from gimpfu import *
from gimpenums import *

# This is the function that will perform actual actions
# image unf drawable koennen weggelassen werden, wenn in der Registrierung keine Uebergabeparameter angegeben sind
# image und drawable werden dann trotzdem in der Funktion zur Verfuegung stehen
# TODO: die zu suchende und die zu ersetzende Farbe sollten uebergeben werden koennen (als Farbtyp)
def my_script_function(image, drawable) :
	region=drawable.get_pixel_rgn(0,0,drawable.width,drawable.height)
	# TODO: fuer diese Operation sollten Tile-Objekte verwendet werden (regions sind zu langsam und zu gross)
	# gimp.image_list()[0].active_layer.get_tile2(False,0,0)[0,0]
	for x in range(0,drawable.width):
		for y in range(0,drawable.height):
			if (region[x,y]=='\x00\x00\x00\xff'):
				region[x,y]='\xff\xff\xff\x00'
	drawable.update(0, 0, drawable.width, drawable.height)
	return

# This is the plugin registration function
register(
	"my_first_script",  # Your plugin's main function name, as it will be found in Gimp's Procedure Browser. This means that your plugin will be callable by other plugins, using this function name (even by a script in a another language)!  
	"My first Python-Fu", # Your plugin's "documentation" name, as it will also appears in the Procedure Browser. This name should describe your plugin briefly.
	"This script does nothing and is extremely good at it", # Plugin's help. Here you should explain in a more detailed manner what kind of function your plugin provides.
	"Igor Kraft", # The name of the author of this plugin
	"kraft private", # Any copyright information needed
	"2013", # The date this version of the plugin was released
	"<Image>/UUU", # The path in the menu where your plugin should be found
	"RGBA", # The image types supported by your plugin
	[], # The list of the parameters needed by your plugin
	[], # The results sent back by your plugin
	my_script_function, # The name of the local function to run to actually start processing, which will be called with a set of parameters.
	)

main()
