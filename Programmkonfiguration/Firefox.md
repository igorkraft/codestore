## Firefox

#### Erweiterungen
- `JSONView`
- `Flashblock`
- `Firebug`
- `FireFTP`
- `Custom UserAgent String` von Linder
  - Domain `mm.web.de` 
  - UserAgent `Mozilla/5.0 (Android 4.4.2; Tablet; rv:51.0) Gecko/51.0 Firefox/51.0`

#### Flash-Plugin

- das tar.gz auf der Adobe-Webseite herunterladen und den Anweisungen in der readme.txt folgen (der apt-Link funktioniert nur für die Ubuntu-Distributionen)
- Flashblock kann als Erweiterung über den Browser installiert werden

#### about:config

- browser.fixup.alternate.enabled: false (verhindert dass localhost durch www.localhost.com ersetzt wird)
- geo.enabled: false (verhindert dass Firefox fragt, ob Google der Standort bekannt gemacht werden soll)
- browser.zoom.siteSpecific: false (Zoom wirkt sich nur auf den Tab aus)
- flashblock.whitelist: *youtube.com,vimeo.com,s7.viastreaming.net
