## Git

#### Allgemeines

- unbeobachtete FSOs müssen mit `git add` unter Beobachtung gestellt werden
  - `git add` überführt die Dateien in den Stage-Bereich, sodass sie vom nächsten commit erfasst werden können
- unbeobachtete Dateien werden auch als **untracked** bezeichnet
- selbst mit `git mv <fso>` kann keine Umbenennung eines FSOs gemacht werden ohne dass die Historie der betroffenen FSOs verloren geht
  - `git log --follow <fso>` gibt die Historie einschließlich Umbenennungen aus

#### Installation

- um den aktuellen Zweig im Promt anzuzeigen, diese Befehle in die .bashrc eintragen
  - `source /cygdrive/c/Program\ Files\ \(x86\)/Git/etc/git-prompt.sh`
  - `PS1='\[\033[00;33m\]\u@\h \[\033[00;36m\]\w\[\033[00;32m\]$(__git_ps1 " (%s)")\[\033[03;00m\]\n\$ '`

#### Befehle

- Zweige oder Commits auschecken
  - `git checkout origin/develop`
  - `git checkout <commit_id>`
- Auschecken einer aus dem Arbeitsverzeichnis gelöschten Datei
  - `git checkout HEAD -- <Datei>`
- Commits und Zweige als Graf ausgeben
  - `git log --graph --oneline --all`
- lokale Änderungen rückgängig machen
  - `git reset --hard`
- Änderungen einer Datei verwerfen
  - `git checkout <Datei>`
- zwei Versionen der selben Datei über mehrere Commits (und Zweige) vergleichen
  - `git diff <commit_id_1>..<commit_id_2> -- <Dateipfad>`
  - `git difftool <commit_id_1>..<commit_id_2> -- <Dateipfad>`
- Versionsgeschichte einer bestimmten Datei anzeigen
  - `gitk <Dateipfad>`
- Remote-Zweige anzeigen
  - `git ls-remote`
- unbeobachtete FSOs anzeigen, Liste veränderter Dateien anzeigen, Stage-Bereich anzeigen
  - `git status`
- unbeobachtete Dateien anzeigen
  - `git ls-files --others --exclude-standard`
- alle Änderungen beobachteter FSOs commiten
  - `git commit -a -m "commit all"`
- alle unbeobachteten Dateien beobachten
  - `git add -A`
- unbeobachtete FSOs löschen
  - `git clean -f -d`
- nur ignorierte FSOs löschen
  - `git clean -f -d -X`
- unbeobachtete und ignorierte FSOs löschen
  - `git clean -f -d -x`
- alle Änderungen aus dem Stage-Bereich entfernen
  - `git reset HEAD *`
- Historie einschließlich Umbenennungen ausgeben
  - `git log --follow <fso>`

#### Konfigurationsdatei

- liegt unter `$HOME/.gitconfig`
- Kommentare fangen mit `#` an
- globale Exclude-Dateien können hier konfiguriert werden
- HTTP-Einstellungen (Proxy, SSL-Zertifikate ignorieren)
```
[http]
	proxy = http://<proxy>:<port>
	sslVerify = false
```
- Difftool konfigurieren (die Sektion `difftool` kann mehmals vorkommen, in der `diff`-Sektion wird das zu benutzende festgelegt)
  - für die folgenden Vergleichsprogramme kann die Sektion `difftool` weggelassen werden: `kdiff3, kompare, tkdiff, meld, xxdiff, emerge, vimdiff, gvimdiff, ecmerge, diffuse, opendiff, p4merge and araxis`
```
[diff]
	tool = diffmerge
[difftool "diffmerge"]
	cmd = C:/Program\\ Files/SourceGear/Common/DiffMerge/sgdm.exe \"$LOCAL\" \"$REMOTE\"
```

#### Links

- List häufig verwendeter Befehle
  - https://projekte.itmc.tu-dortmund.de/projects/wiki-hsb/wiki/Wichtige_Git_Befehle/1
- Befehle und ihre Wirkung als Grafik
  - http://en.wikipedia.org/wiki/File:Git_operations.svg
- Erweiterung für Baum-Visualisierung
  - https://github.com/jwiegley/git-scripts/blob/master/git-forest
- gutes Git-Tutorial
  - https://www.atlassian.com/git/tutorials/
  - https://www.atlassian.com/git/tutorials/undoing-changes
