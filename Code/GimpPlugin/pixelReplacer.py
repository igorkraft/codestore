#!/usr/bin/env python
# -*- coding: utf-8 -*-

from gimpfu import *

def replace_alpha_pixels(image, drawable, search_color, search_alpha, new_color, new_alpha):
	search_alpha = int(search_alpha)
	new_alpha = int(new_alpha)
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
					if (search_color[0] == ord(cur_pixel[0]) and 
							search_color[1] == ord(cur_pixel[1]) and 
							search_color[2] == ord(cur_pixel[2]) and 
							search_alpha == ord(cur_pixel[3])):
						tile[x,y] = chr(new_color[0]) + chr(new_color[1]) + chr(new_color[2]) + chr(new_alpha)
	drawable.update(0, 0, drawable.width, drawable.height)
	return

register(
	"replace_alpha_pixels",
	"Replace a color including its alpha channel.", 
	"Replace each pixel with the given color and alpha value by a second given color and alpha value.", 
	"", 
	"private", 
	"2013", 
	"<Image>/Filters/Plug-Ins/Replace Alpha Pixels", 
	"RGBA", 
	[
		(PF_COLOR,   "search_color", "Search for", (255, 255, 255) ),
		(PF_SPINNER, "search_alpha", "Alpha",  255, (0, 255, 1) ),
		(PF_COLOR,   "new_color", "Replace with", (0, 0, 0) ),
		(PF_SPINNER, "new_alpha", "Alpha",  0, (0, 255, 1) )
	], 
	[], 
	replace_alpha_pixels)

main()

