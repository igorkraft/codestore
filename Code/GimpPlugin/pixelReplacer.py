#!/usr/bin/env python
# -*- coding: utf-8 -*-

from gimpfu import *
from gimpenums import *

def replace_pixel(image, drawable, color, slider) :
	pdb.gimp_message(dir(color))
	#pdb.gimp_message(color.wurst)
	pdb.gimp_message(color.b)
	pdb.gimp_message(slider)
	tileCountX = int(drawable.width / 64)
	if (drawable.width % 64 > 0):
		tileCountX = tileCountX + 1
	tileCountY = int(drawable.height / 64)
	if (drawable.height % 64 > 0):
		tileCountY = tileCountY + 1
	for tileX in range(tileCountX):
		for tileY in range(tileCountY):
			tile=drawable.get_tile2(False,tileX * 64,tileY * 64)
			for x in range(tile.ewidth):
				for y in range(tile.eheight):
					if (tile[x,y]=='\x00\x00\x00\xff'):
						tile[x,y]='\xff\xff\xff\x00'
	drawable.update(0, 0, drawable.width, drawable.height)
	return

# This is the plugin registration function
register(
	"replace_pixel",  # Your plugin's main function name, as it will be found in Gimp's Procedure Browser. This means that your plugin will be callable by other plugins, using this function name (even by a script in a another language)!  
	"My first Python-Fu", # Your plugin's "documentation" name, as it will also appears in the Procedure Browser. This name should describe your plugin briefly.
	"This script does nothing and is extremely good at it", # Plugin's help. Here you should explain in a more detailed manner what kind of function your plugin provides.
	"Igor Kraft", # The name of the author of this plugin
	"kraft private", # Any copyright information needed
	"2013", # The date this version of the plugin was released
	"<Image>/TEST", # The path in the menu where your plugin should be found
	"RGBA", # The image types supported by your plugin
	[
		(PF_COLOR,  "arg3", "Search for", (0, 0, 0) ),
		(PF_SLIDER, "contrast", "Alpha",  255, (0, 255, 1) )# initial steht er auf 255; das Spektrum geht von 0 bis 255; der Zeiger macht Einerschritte
	], # The list of the parameters needed by your plugin
	[], # The results sent back by your plugin
	replace_pixel, # The name of the local function to run to actually start processing, which will be called with a set of parameters.
	)

main()

