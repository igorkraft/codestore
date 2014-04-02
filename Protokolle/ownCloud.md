### ownCloud

#### Installation
- `owncloud` und `owncloud-sqlite` installieren
- TODO: prüfen, ob `libsqlite3-dev` auch installiert werden muss
- im Apache-Server muss WebDAV zwingend deaktiviert werden
 - `Alias /webdav ...` und die zugehörige `<Directory`-Sektion auskommentieren (in apache2.conf)
 - `sudo a2dismod dav_fs`
 - `sudo a2dismod dav`
- in der apache2.conf `Include conf-available/` hinzufügen (Alternative siehe unten)
 - bei der Installation wird die Datei `/etc/apache2/conf-available/owncloud.conf` angelegt
- `sudo service apache2 restart`
- Web-Installationsseite aufrufen (z. B.: https://localhost/owncloud/)
 - dort Sqlite auswählen und ein Administrationskonto anlegen
- die Logdatei liegt hier: `/var/lib/owncloud/data/owncloud.log`
 
#### Links

- Erweiterungsprogramme
 - http://apps.owncloud.com/
- wie man die maximale Uploadgröße von 2MB erhöhen kann
 - http://wiki.ubuntuusers.de/ownCloud
- Mounting ownCloud via WebDAV
 - http://doc.owncloud.org/server/5.0/user_manual/connecting_webdav.html
- Der Kalender
 - http://doc.owncloud.org/server/5.0/user_manual/calendars.html
 
#### Alternative apache2.conf Anpassungen

Einzustellen im virtuellen Host:

        # Konfiguration von ownCloud
        Alias /owncloud /usr/share/owncloud
        <Directory /usr/share/owncloud/>
                Options +FollowSymLinks
                AllowOverride All
                <IfVersion < 2.3>
                        order allow,deny
                        allow from all
                </IfVersion>
                <IfVersion >= 2.3>
                        Require all granted
                </IfVersion>
        </Directory>

