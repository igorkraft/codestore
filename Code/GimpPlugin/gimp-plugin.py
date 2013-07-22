#!/usr/bin/env python

from gimpfu import *

# This is the function that will perform actual actions
# image unf drawable koennen weggelassen werden, wenn in der Registrierung keine Uebergabeparameter angegeben sind
# image und drawable werden dann trotzdem in der Funktion zur Verfuegung stehen
def my_script_function(image, drawable, text_value, int_value) :
    pdb.gimp_message("text: "+text_value+"; number: %d"%int_value)
    pdb.gimp_image_flip( image, ORIENTATION_HORIZONTAL )
    return

# This is the plugin registration function
register(
    "my_first_script",  # Your plugin's main function name, as it will be found in Gimp's Procedure Browser. This means that your plugin will be callable by other plugins, using this function name (even by a script in a another language)!  
    "My first Python-Fu", # Your plugin's "documentation" name, as it will also appears in the Procedure Browser. This name should describe your plugin briefly.
    "This script does nothing and is extremely good at it", # Plugin's help. Here you should explain in a more detailed manner what kind of function your plugin provides.
    "Igor Kraft", # The name of the author of this plugin
    "kraft private", # Any copyright information needed
    "2013", # The date this version of the plugin was released
    "<Image>/Filters/MyScripts/My First Python-Fu", # The path in the menu where your plugin should be found
    "*", # The image types supported by your plugin
    [
      (PF_STRING, 'some_text', 'Some text input for our plugin', 'Write something'),
      (PF_INT, 'some_integer', 'Some number input for our plugin', 2010)
    ], # The list of the parameters needed by your plugin
    [], # The results sent back by your plugin
    my_script_function, # The name of the local function to run to actually start processing, which will be called with a set of parameters.
    )

main()
