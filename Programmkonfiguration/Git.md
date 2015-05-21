## Git

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
- unbeobachtete (untracked) FSOs anzeigen, Liste veränderter Dateien anzeigen, Stage-Bereich anzeigen
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
- alle Änderungen aus dem Stage-Bereich entfernen
  - `git reset HEAD *`

#### Konfigurationsdatei

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