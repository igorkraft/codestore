## GitCGI

Vor den Start müssen zwei Umgebungsvariablen gesetzt werden:

`GIT_PROJECT_ROOT=/path/to/git-repo-root` definiert das Verzeichnis, in dem die Git-Projekte liegen
`GIT_HTTP_EXPORT_ALL=` die Variable braucht keinen Wert, sie wird als Flag interpretiert

Um anonymen Schreibzugriff (`git push`) für ein Projekt zu aktivieren, muss die serverseitige config-Datei des Projekts erweitert werden:

```
[http]
	receivepack = true
```

Alternativ hat man Schreibzugriff, wenn der Authorisation-Header mit gültigen Basic-Credentials an `git-http-backend` übergeben wird.
