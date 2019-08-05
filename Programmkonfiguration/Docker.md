## Docker

- nach der Installation von `docker` den Rechner neu starten
- der lokale Image-Speicher befindet sich unterhalb des Docker-Root-Verzeichnisses
  - Default-Root-Location: `/var/lib/docker`
  - kann per Daemon-Parameter angepasst werden
    - in `/usr/lib/systemd/system/docker.service` oder `/etc/systemd/system/docker.service.d/docker-storage.conf`
      - `ExecStart=/usr/bin/dockerd` -> `ExecStart=/usr/bin/dockerd --data-root=/new/location/docker`
- starten mit `sudo systemctl restart docker.service`
- jedes Dockerfile erzeugt für ein Basis-Image eine neue Schicht
  - das Basis-Image und die zusätzliche Schicht werden zu einem neuen Image zusammengefasst

#### Dockerfile

- `FROM python:3-onbuild`-Direktive
  - legt das Basis-Image fest
  - **TODO** prüfen, wie man `FROM scratch`-Images erzeugt
- **TODO** `EXPOSE`-Direktive beschreiben
- `VOLUME`-Direktive
  - markiert eine Verzeichnis innerhalb des Images als Mountpoint
  - zu prüfende Aspekte
    - beim erstmaligem Hochfahren eines mit dem Image assoziierten Containers wird der Inhalt des `VOLUME`-Verzeichnisses auf den Docker-Host kopiert
      - die darin enthaltenen Änderungen bleiben über mehrere Containerstarts persistent
      - wenn beim Erzeugen des Containers kein Link zum `VOLUME`-Verzeichnis (mit `-v`) gemacht wurde, dann wird im Docker-Root ein Verzeichnis angelegt
      - mit `-v` können auch Links zu Image-Verzeichnissen erzeugt werden, die nicht als `VOLUME` deklariert wurden
        - beim erstmaligen Hochfahren werden die Inhalte des Image-Verzeichnisses nicht auf den Host kopiert
        - Dateien können von Container und Host trotzdem geteilt werden
      - die Direktive dient dazu Änderungen an Image-Dateien zu persistieren
- `COPY`- und `ADD`-Direktive
  - für Dateien in die neue Image-Schicht ein
  - z. B. `COPY target/app.jar app.jar`
  - **TODO** Unterschied zwischen `COPY` und `ADD` beschreiben
- `CMD`- und `ENTRYPOINT`-Direktive
  - geben den Startbefehl an
  - z. B. `CMD ["python", "./app.py"]`
  - **TODO** Unterschied zwischen `CMD` und `ENTRYPOINT` beschreiben

#### Befehle

- `sudo docker build -t <Image-Name> <Verzeichnis>` erzeugt das Image
  - das Verzeichnis muss ein `Dockerfile` enthalten
  - Image wird im loaklen Imagespeicher abgelegt
- `sudo docker images -a` zeigt alle Images an
- `sudo docker create --name <Container-Name> -t <Image-Name>` erzeugt den Container
  - `--restart=always` legt fest, dass der Container beim Start des Docker-Daemon hochgefahren wird
  - `-p 8082:8082` Portfreigabe kann mehrfach übergeben werden
  - `-v <Host-Verzeichnis>:<Container-Verzeichnis>` Volume-Mount kann mehrfach angegeben werden
- `sudo docker create` erzeugt einen Container aus einem Image
  - das Image wird falls nötig und wenn möglich aus dem Docker-Hub heruntergeladen
- `sudo docker save` lädt Images aus dem lokalen Image-Speicher und schreibt sie in eine Datei
  - `sudo docker load` lädt Images aus einer Datei und schreibt sie in den lokalen Image-Speicher
- `sudo docker container stop <Container-Name>` fährt den Container herunter
- `sudo docker container rm -v <Container-Name>` löscht den Container
  - `-v` die verbundenen Volumes gleich mit löschen
- `sudo docker image rm <Image-Name>` Image löschen