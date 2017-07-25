## Gatling

#### Aufzeichnen einer Simulation

- Arbeitsverzeichnis anlegen (z. B. /path/to/workspace)
- im Arbeitsverzeichnis eine `recorder.sh` anlegen
```
export GATLING_HOME=/path/to/gatling_root
/path/to/gatling_root/recorder.sh \
-of /path/to/workspace/ \ # Ablage für Simulation
-bdf /path/to/workspace/ # Ablage für Payload von Post-Anfragen
```

#### Abspielen einer Simulation

- Arbeitsverzeichnis anlegen (z. B. /path/to/workspace)
  - Simulation ins Arbeitsverzeichnis kopieren
- conf-Verzeichnis ins Arbeitsverzeichnis kopieren
  - Pfade in `conf/gatling.conf` anpassen
- im Arbeitsverzeichnis eine `gatling.sh` anlegen
```
export GATLING_HOME=/path/to/gatling_root
export GATLING_CONF=/path/to/workspace/conf
/path/to/gatling_root/bin/gatling.sh
# optionale Parameter: -s "Simulationsname", -rd "Beschreibung"
```