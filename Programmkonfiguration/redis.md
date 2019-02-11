# redis

- redis Container erzeugen
  - `docker pull redis`
  - `docker container run --restart=always -d -p 6379:6379 --name myredis redis`
  - `sudo docker exec -i -t myredis /bin/bash`
- läuft unter Port 3679
- die verschiedenen Datenbanken im Server werden von 0 an durchnummeriert
  - gibt man beim Absetzen eines Befehls mit an
  - ohne Angabe wird Datenbank 0 angesprochen

## redis-cli

- der Client `redis-cli` verbindet sich per deafult mit 127.0.0.1:3679
- `redis-cli ping`
  - liefert `PONG`, wenn die Verbindung zum Server funktioniert
- man kann redis-cli jeden redis-Befehl als Parameter übergeben
  - er führt den Befehl aus und beendet sich dann
- ohne Parameter öffnet er eine Shell, in der man Befehle absetzen kann
- Quelle: https://redis.io/topics/rediscli

## redis Befehle

- Quelle: https://redis.io/commands/
- `set foo 100`
  - erzeugt einen Eintrag mit dem Schlüssel `foo` und dem Wert `100`
- `get foo`
  - gibt den Wert des Eintrags `foo` aus