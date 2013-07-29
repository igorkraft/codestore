#!/usr/bin/env python
# -*- coding: utf-8 -*-

from gimpfu import *
import gtk

class GtkDialogTest:

	def click_handler(self, widget, data):
		pdb.gimp_message(data)
	
	def __init__(self):
		# Quelle: http://www.pygtk.org/docs/pygtk/class-gtkdialog.html
		# Icon: http://de.wikipedia.org/wiki/X_PixMap
		
		icon_xpm = ["16 16 3 1", " 	c None", ".	c #EDBE58", "+	c #00FFFF","                ","                ",
					"  ++    .       ","  ++   ...      ","      ....      ","      .....     ","     .......    ",
					"    ........    ","   ..........   ","   ...........  ","  ............  "," .............. ",
					"                ","                ","                ","                "]
		
		icon = gtk.gdk.pixbuf_new_from_xpm_data(icon_xpm)
		
		dialog = gtk.Dialog()
		dialog.set_size_request(400, 300)
		dialog.set_title("Testfenster")
		dialog.set_icon(icon)
		
		fix = gtk.Fixed()
		dialog.vbox.pack_start(fix)
		fix.show()
		
		label = gtk.Label()
		label.set_text("Labeltext")
		fix.put(label,200,180)
		label.show()
	
		checkbox = gtk.CheckButton("Useless checkbox")
		dialog.action_area.pack_start(checkbox)
		checkbox.show()
	
		button = gtk.Button()
		button.set_label("btn")
		button.connect("clicked", self.click_handler, "output")
		dialog.action_area.pack_end(button)
		button.show()
		
		dialog.run()

def gtk_test(image, drawable):
	GtkDialogTest()
	return

register("gtk_test","","","","private","2013","<Image>/GTK-Test","*",[],[],gtk_test)

main()
