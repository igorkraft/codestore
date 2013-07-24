#!/usr/bin/env python
# -*- coding: utf-8 -*-

from gimpfu import *

def demo_demo(image, drawable, p0, p02, p03,  p04, p1, p2, p3, p4, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28) :

	return

# This is the plugin registration function
register(
	"demo_demo",  # Your plugin's main function name, as it will be found in Gimp's Procedure Browser. This means that your plugin will be callable by other plugins, using this function name (even by a script in a another language)!  
  "Demonstrate builtin plugin GUI widgets",
  "Does nothing but display builtin plugin widgets.",
  "Lloyd Konneker (bootch nc.rr.com)",
  "Copyright 2010 Lloyd Konneker",
  "2010",
	"<Image>/Gui-Demo", # The path in the menu where your plugin should be found
	"*", # image types: blank means don't care but no image param
	[
					# Plugin parameter tuples: (type, name, description, default, [extra])
          # Note these determine both the type of the parameter and the GUI widget displayed.
          # Note the underbar in the description tells what letter is the shortcut key.
          #
          # Editable text boxes
          (PF_INT, "p0", "_INT:", 0), # PF_INT8, PF_INT16, PF_INT32  similar but no difference in Python.
          (PF_FLOAT, "p02", "_FLOAT:", 3.141),
          (PF_STRING, "p03", "_STRING:", "foo"),  # alias PF_VALUE
          (PF_TEXT, "p04", "TEXT:", "bar"),
          # PF_VALUE
          # Pick one from set of choices
          (PF_OPTION,"p1",   "OPTION:", 0, ["0th","1st","2nd"]), # initially 0th is choice
          (PF_RADIO, "p16", "RADIO:", 0, (("0th", 1),("1st",0))), # note bool indicates initial setting of buttons
          # PF_RADIO is usually called a radio button group.
          # SLIDER, ADJUSTMENT types require the extra parameter of the form (min, max, step).
          (PF_TOGGLE, "p2",   "TOGGLE:", 1), # initially True, checked.  Alias PF_BOOL
          # PF_TOGGLE is usually called a checkbox.
          (PF_SLIDER, "contrast", "SLIDER",  100, (0, 255, 1) ), # initial steht er auf 100; das Spektrum geht von 0 bis 255; der Zeiger macht Einerschritte
          (PF_SPINNER, "p4", "SPINNER:", 21, (1, 1000, 50)),  # alias PF_ADJUSTMENT
          # Pickers ie combo boxes ie choosers from lists of existing Gimp objects
          (PF_COLOR, "p14", "_COLOR:", (100, 21, 40) ), # extra param is RGB triple
          # PF_COLOUR is an alias by aussie PyGimp author lol
          (PF_IMAGE, "p15", "IMAGE:", None), # should be type gimp.image, but None works
          (PF_FONT, "p17", "FONT:", 0),
          (PF_FILE, "p18", "FILE:", 0),
          (PF_BRUSH, "p19", "BRUSH:", 0),
          (PF_PATTERN, "p20", "PATTERN:", 0),
          (PF_GRADIENT, "p21", "GRADIENT:", 0),
          (PF_PALETTE, "p22", "PALETTE:", 0),
          (PF_LAYER, "p23", "LAYER:", None),
          (PF_CHANNEL, "p24", "CHANNEL:", None),  # ??? Usually empty, I don't know why.
          (PF_DRAWABLE, "p25", "DRAWABLE:", None),
          # Mostly undocumented, but work
          (PF_VECTORS, "p26", "VECTORS:", None),
          (PF_FILENAME, "p27", "FILENAME:", 0),
          (PF_DIRNAME, "p28", "DIRNAME:", 0)
          # PF_REGION might work but probably of little use.  See gimpfu.py.
	], # The list of the parameters needed by your plugin
	[], # The results sent back by your plugin
	demo_demo, # The name of the local function to run to actually start processing, which will be called with a set of parameters.
	)

main()

