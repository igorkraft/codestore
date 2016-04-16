## Maven

#### Lebenszyklen

- es gibt 3 Lebenszyklen in Maven (Clean Lifecycle, Default Lifecycle, Site Lifecycle)
- jeder Zyklus besteht aus unterschiedlichen Phasen (welcher Zyklus beim Build benutzt wird ergibt sich implizit aus der Phases, die beim Mavenaufruf übergeben wird)
- zu jedem Lebenszyklus gibt es verschiedene Default-Binding-Listen (jede Zyklusphase wird einem Plugin mit einem Goal zugeordnet)
- welche Default-Binding-Liste benutzt werden soll wird im packaging-Attribut der pom festgelegt (default ist jar)
- beim Maven-Aufruf mit einer Phase wird nicht nur die Phase selbst ausgeführt, sondern auch alle ihre Vorgängerphasen

#### Goals

- jedes Plugin hat individuelle Goals (stehen in der Doku jedes Plugins)
- in einer Phase kann ein Plugin eines oder mehrere Goals ausführen
- der Aufruf 'mvn install:install-file' bewirkt, dass das 'maven-install-plugin' mit dem Goal install-file ausgeführt wird
  - bei disem Aufruf wurde keine Phase angegeben, weswegen keiner der Lebenszyklen abgewickelt wird
  - das der Parameter nicht als Phase interpretiert wird, liegt an dessen Doppelpunkt (alle Goal-Angaben enthalten Doppelpunkte)
- Goals können in abgekürzter oder ausführlicher Schreibweise angegeben werden
  - `mvn dependency:copy`
  - `mvn org.apache.maven.plugins:maven-dependency-plugin:2.10:copy`
- der Aufruf 'mvn dependency:unpack@some-id' führt das 'unpack'-Goal mit der angegebenen ID des 'maven-dependency-plugin' aus
  - ein Goal kann innerhalb eines Plugins mehrfach konfiguriert werden (die Konfigurationen werden durch IDs unterschieden)
  - setzt voraus, dass eine pom.xml mit einer Goal-Konfiguration vorhanden ist
- einige Standard-Goals können auch ohne pom.xml ausgeführt werden (z. B. `deploy:deploy` oder `dependency:get`)

#### Dependency-Plugin

- eine Bibliothek herunterladen und auspacken (keine pom.xml erforderlich)
  - `mvn dependency:unpack -Dartifact=junit:junit:LATEST:jar -Dproject.basedir=. -DoutputDirectory=.` 
- eine Bibliothek herunterladen (keine pom.xml erforderlich)
  - `mvn dependency:copy -Dartifact=junit:junit:LATEST:jar -Dproject.basedir=. -DoutputDirectory=.`
- eine Bibliothek mit Abhängigkeiten herunterladen (keine pom.xml erforderlich)
  - `mvn dependency:get`
- Abhängigkeiten herunterladen und auspacken (erfordert pom.xml)
  - `mvn dependency:unpack-dependencies -Dmdep.useSubDirectoryPerArtifact=true`
- Abhängigkeiten herunterladen (erfordert pom.xml)
  - `mvn dependency:copy-dependencies`
- Abhängigkeitsbaum anzeigen
  - `mvn dependency:tree`
- Abhängigkeiten mit absoluten Pfaden ausgeben
  - `mvn dependency:build-classpath`
  
#### Maven-Wrapper
- auf https://repo1.maven.org/maven2/io/takari/maven-wrapper/ das neuste tar.gz runterladen
- die darin enthaltenen Dateien ins Projektroot kopieren
- statt `mvn` `./mvnw` aufrufen
  
#### Sonstiges

- `mvn source:jar` erzeugt ein -source.jar Paket
- `mvn package -Dmaven.repo.local=target/repository` legt das lokale Repository fest (geht auch in der settings.xml)
- `LATEST` kann statt einer Versionsnummer angegeben werden
- `mvn archetype:generate -DarchetypeArtifactId=maven-archetype-webapp` generiert interaktiv ein Java-Web-Projekt