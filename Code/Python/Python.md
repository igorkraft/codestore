## Python

#### Allgemeines

- Objekt haben nur Attribute (Funktionen sind ausführbare Attribute)
  - die Attribute werden in einem Dictionary gespeichert (siehe `Map<String, Object>` in Java)
- dir(object) liefert eine Liste aller Attribute von object (Keys des Attribut-Dictionarys)
- Attribute sind nicht typspezifisch
- Typ eines Attribut-Wertes ermitteln: type(object.attr_a)
  - liefert zum Beispiel `<class 'method'> oder <class 'int'>`
- Klassennamen eines Objekts ermitteln: `type(x).__name__` oder `x.__class__.__name__`
- Python-Methoden können mehrere Rückgabewerte liefern
- `help("modules")` listet alle verfügbaren Module auf
- `help("gtk")` listet Informationen zum GTK-Modul auf
- GTK-Layouts
  - http://zetcode.com/gui/pygtk/layout/

#### Code

##### Analyse von Objekten

```
#!/usr/bin/python
#coding=UTF-8

import inspect;

def list_attributes(self, obj):

	print("Analyse type " + str(type(obj)));

	for attr_key in dir(obj):

		attr = getattr(obj, attr_key);
		line = str(type(attr)) + "	" + attr_key;

		if (hasattr(attr, '__code__')):
			line = line + "	" + str(inspect.getargspec(attr));
		
		print(line);
```

##### MessageBoxen mit GTK

```
#!/usr/bin/python
#coding=UTF-8

import gtk;

dialog = gtk.MessageDialog(None, gtk.DIALOG_MODAL|gtk.DIALOG_DESTROY_WITH_PARENT, gtk.MESSAGE_QUESTION,gtk.BUTTONS_YES_NO, "Text")
dialog.run()
```

##### Python-Version anzeigen

```
#!/usr/bin/python
#coding=UTF-8

import platform;

platform.python_version();
```

##### Datei anlegen und schreiben

```
#!/usr/bin/python
#coding=UTF-8

f=open("/tmp/test.txt","w+");
f.write("test");
f.close();
```