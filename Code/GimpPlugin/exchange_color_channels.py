#!/usr/bin/env python
# -*- coding: utf-8 -*-

from gimpfu import *

def exchange_color_channels(image, drawable, exchange):
	r = 0
	g = 1
	b = 2
	if (exchange == 0): # GRB
		r = 1
		g = 0
	if (exchange == 1): # BGR
		r = 2
		b = 0
	if (exchange == 2): # RBG
		g = 2
		b = 1
	has_alpha = pdb.gimp_drawable_has_alpha(drawable)
	tile_count_x = int(drawable.width / 64)
	if (drawable.width % 64 > 0):
		tile_count_x = tile_count_x + 1
	tile_count_y = int(drawable.height / 64)
	if (drawable.height % 64 > 0):
		tile_count_y = tile_count_y + 1
	for tile_x in range(tile_count_x):
		for tile_y in range(tile_count_y):
			tile = drawable.get_tile2(False,tile_x * 64,tile_y * 64)
			for x in range(tile.ewidth):
				for y in range(tile.eheight):
					cur_pixel = tile[x,y]
					if (has_alpha):
						tile[x,y] = cur_pixel[r] + cur_pixel[g] + cur_pixel[b] + cur_pixel[3]
					else:
						tile[x,y] = cur_pixel[r] + cur_pixel[g] + cur_pixel[b]
	drawable.update(0, 0, drawable.width, drawable.height)
	return

register(
	"exchange_color_channels",
	"Exchange two color channels.", 
	"Change the order of RGB channels to GRB, BGR or RBG.", 
	"", 
	"private", 
	"2013", 
	"<Image>/Filters/Plug-Ins/Exchange Color Channels", 
	"RGB,RGBA", 
	[
		(PF_RADIO, "exchange", "Channels to exchange", 0, (("GRB", 0),("BGR",1),("RBG",2)))
	], 
	[], 
	exchange_color_channels)

main()

