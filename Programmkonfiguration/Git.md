## Git

#### Allgemeines

- unbeobachtete FSOs müssen mit `git add` unter Beobachtung gestellt werden
  - `git add` überführt die Dateien in den Stage-Bereich, sodass sie vom nächsten commit erfasst werden können
  - `git add` überführt Änderungen an bereits beobachteten Dateien in den Stage-Bereich
- unbeobachtete Dateien werden auch als **untracked** bezeichnet
- selbst mit `git mv <fso>` kann keine Umbenennung eines FSOs gemacht werden ohne dass die Historie der betroffenen FSOs verloren geht
  - `git log --follow <fso>` gibt die Historie einschließlich Umbenennungen aus
- die Bezeichnung `origin` ist ein Alias für die Remote-URL, mit der geklont wurde
- wenn man pushed, muss man die Remote-URL und den Remote-Zweig angeben (`git push origin master`)
  - lässt man Remote-URL und Remote-Zweig weg, dann wird zu dem Zweig gepushed, der mit dem aktuellen lokalen Zweig verbunden ist (Upstream-Verbindung)
  - `git branch -vv` zeigt welcher lokale Zweig mit welchem Remote-Zweig verbunden ist (Upstream-Verbindung)

#### HEAD

- `HEAD` ist ein lokaler Zeiger auf das, was man aktuell ausgecheckt hat (Zweig, Tag oder Commit/Revision)
- zeigt `HEAD` auf einen Commit/Revision, dann hat man einen `detached HEAD`
  - wenn man auf einem `detached HEAD` Änderungen macht und diese committed, dann gehört der neue Commit zu keinem Zweig oder Tag
  - checkt man nach dem Commitment einen Zweig aus, dann wird der neue Commit nirgends gelistet und irgendwann vom Garbage Collector entfernt
  - mit `git reflog` kann man die Revision des neuen Commits ermitteln, ihn anschließend auschecken und auf ihm einen Zweig definieren

#### Submodule

- ein Projekt, dass Submodule (Links auf auf andere Projekte) enthält heißt **Superprojekt**
- `git submodule add <Remote-URL> <Unterordner>` bettet ein Projekt als Submodul in einen Unterordner des aktuellen Projekts (Superprojekt) ein
  - eine Datei `.gitmodules` wid angelegt, die die Submodul-URL mit dem Unterordner verknüpft
  - die Einbettung muss anschließend committed werden
  - der Unterordner wird in Git als Link repräsentiert, der die Commit-Revision des Superprojekts mit der Commit-Revision des Submodules verbindet (wie eine Datei, die die Commit-Revision des Submodules beinhaltet)
- wenn man im Submodule auscheckt, dann wird das als Änderung des Unterordners im Superprojekt erkannt (commit nötig)
- wenn man im Submodule etwas ändert, dann wird das in beiden Projekten erkannt
  - man muss erst das Submodul commiten, dann das Superprojekt
- `git clone --recursive <Remote-URL>` klont ein Projekt einschließlich seiner Submodule
  - im Submodul wird der Commit ausgecheckt, der im Unterordner-Link steht (dadurch entsteht ein `detached HEAD` im Submodul!)
- checkt man im Superprojekt einen anderen Commit aus, dann wird im Submodul der verlinkted commit ausgecheckt
  - der Checkout im Superprojekt schlägt fehl, wenn im Submodul unveröffentlichte Änderungen sind und es ebenfalls ausgecheckt werden müsste
- `git submodule update --init --recursive` aktualisiert/initialisiert alle Submodule des Superprojekts
  - ist nötig, wenn man einen Commit ausgecheckt hat, in dem es noch kein Submodul gab und wieder zurückwechselt oder wenn man nicht rekursiv geklont hat
- `git submodule status` zeigt die verknüpften Commit-Revisions an (für aktuellen Superprojekt-Commit)

#### Installation

- um den aktuellen Zweig im Promt anzuzeigen, diese Befehle in die .bashrc eintragen
  - `source /cygdrive/c/Program\ Files\ \(x86\)/Git/etc/git-prompt.sh`
  - `PS1='\[\033[00;33m\]\u@\h \[\033[00;36m\]\w\[\033[00;32m\]$(__git_ps1 " (%s)")\[\033[03;00m\]\n\$ '`

#### Befehle

##### Sonstige Befehle

- leeren Ordner als Git-Projekt initialisieren (Git-Projekt anlegen)
  - `git init --bare --shared=group`
- Commit-Id der aktuall ausgecheckten Referenz (Tag oder Branch) anzeigen
  - `git rev-parse HEAD`
- lokale und entfernte Zweige anzeigen (mit aktuellen Commits und Upstream-Verbindungen)
  - `git branch -a -vv`
- Zweig auf aktuellem Commit erzeugen (lokal und remote)
  - `git branch develop`
  - `git checkout develop`
  - `git push origin develop`
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
- unveröffentlichte Änderungen einer Datei mit Basis vergleichen
  - `git difftool <Dateipfad>`
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
- Änderungen im Stage-Bereich commiten
  - `git commit -m "commit message"`
- unbeobachtete FSOs löschen
  - `git clean -f -d`
- nur ignorierte FSOs löschen
  - `git clean -f -d -X`
- unbeobachtete und ignorierte FSOs löschen
  - `git clean -f -d -x`
- Historie einschließlich Umbenennungen ausgeben
  - `git log --follow <fso>`

##### Befehle zur Modifikation des Stage-Bereichs
- alle Änderungen und unbeobachtete Dateien in den Stage-Bereich übernehmen
  - `git add -A`
- eine Änderung oder unbeobachtete Datei in den Stage-Bereich übernehmen
  - `git add <Dateipfad>`
- alle Änderungen und unbeobachtete Dateien aus dem Stage-Bereich entfernen
  - `git reset HEAD *`
- eine Änderung oder unbeobachtete Datei aus dem Stage-Bereich entfernen
  - `git reset HEAD <Dateipfad>`

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
- Benutzer für neue Commits definieren
```
[user]
	email = igorkraft@web.de
	name = Igor Kraft
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
