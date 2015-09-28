## VirtualBox

#### Gasterweiterungen installieren

Quelle:http://randomitblog.blogspot.com/2011/04/installing-virtualbox-guest-additions.html

```
sudo apt-get install dkms
sudo apt-get install build-essential
sudo apt-get install linux-headers-$(uname -r)
```

#### Netzwerke

##### DHCP

- DHCP-Server muss im Netzwerk vorhanden sein
- automatische Vergabe von IP-Adressen
- funktioniert über einen Broadcast-Mechanismus

##### Netzwerkbrücke

- Guest läuft mit einer eigenen IP im Netzwerk
- Netzwerkkarte des Hosts nimmt Pakete der Host-IP und der Guest-IP entgegen
- Guest ist von überall im Netzwerk über seine IP erreichbar
- Guest bekommt seine IP vom DHCP-Server des Netzwerks

##### NAT

- Host und Guest bilden ein eigenes internes Netzwerk
- Host hat im internen Netz eine andere IP als im externen
- Host übernimmt die Rolle eines Routers (und DHCP-Servers) im internen Netz
- Guest hat im externen Netzwerk die selbe IP, wie der Host
- Responses für den Guest werden durch das Routing des Host zurückgereicht
- Dienste des Guests können im externen Netz nicht erreicht werden
