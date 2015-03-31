### Javascript

##### zufällige Buchstaben erzeugen
```Javascript
String.fromCharCode((Math.round(Math.random()*1000)%26)+97)
```

##### Relative URL in absolute umwandeln
```Javascript
var a = document.createElement("a");
a.href = relUrl;
return a.href;

// "test"   -> "http://localhost/test"
// "//test" -> "http://test/" (wendet das Schema des aktuellen Hosts an)
```
