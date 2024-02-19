
//GET nutzer
curl -X 'GET' 'http://localhost:8080/nutzer' -H 'accept: application/json'

//POST nutzer
curl -X 'POST' 'http://localhost:8080/nutzer' -H 'accept: */*' -H 'Content-Type: multipart/form-data' -F 'email=nutzer@web.de' -F 'passwort=EfGh5678' -F 'vorname=Tom' -F 'nachname=Müller'

#GET adressen
curl -X 'GET' 'http://localhost:8080/adressen' -H 'accept: application/json'

#POST adressen
curl -X 'POST' 'http://localhost:8080/adressen' -H 'accept: */*' -H 'Content-Type: multipart/form-data' -F 'strasse=Düsseldorfer' -F 'hausnummer=12' -F 'plz=40210' -F 'stadt=Düsseldorf'

#GET buerger
curl -X 'GET' 'http://localhost:8080/buerger' -H 'accept: application/json'

#POST buerger
curl -X 'POST' 'http://localhost:8080/buerger' -H 'accept: */*' -H 'Content-Type: multipart/form-data' -F 'email=buerger2@web.de' -F 'passwort=EfGh5678' -F 'vorname=Thomas' -F 'nachname=Schirm' -F 'adresseid=3'

#GET gaertner
curl -X 'GET' 'http://localhost:8080/gaertner' -H 'accept: application/json'

#POST gaertner
curl -X 'POST' 'http://localhost:8080/gaertner' -H 'accept: */*' -H 'Content-Type: multipart/form-data' -F 'email=gaertner1@web.de' -F 'passwort=EfGh5678' -F 'vorname=Karl' -F 'nachname=Schmidt' -F 'spezialgebiet=Rosengarten'

#GET pflanzen
curl -X 'GET' 'http://localhost:8080/pflanzen' -H 'accept: application/json'

#GET pflegemassnahmen
curl -X 'GET' 'http://localhost:8080/pflegemassnahmen' -H 'accept: application/json'

#GET pflegeprotokolle
curl -X 'GET' 'http://localhost:8080/pflegeprotokolle' -H 'accept: application/json'

#GET pflegemassnahmen/bewertungen
curl -X 'GET' 'http://localhost:8080/pflegemassnahmen/bewertungen' -H 'accept: application/json'

#GET pflanzen/{pflanzeid}/bilder
curl -X 'GET' 'http://localhost:8080/pflanzen/1/bilder' -H 'accept: application/json'


#DELETE adressen/{adresseid}
curl -X 'DELETE' 'http://localhost:8080/adressen/4' -H 'accept: */*'

#PATCH adressen/{adresseid}
curl -X 'PATCH' 'http://localhost:8080/adressen/3' -H 'accept: */*' -H 'Content-Type: multipart/form-data' -F 'strasse=Duisburgerstr.' -F 'hausnummer=12' -F 'plz=43164' -F 'stadt=Duisburg'

#----------------------------------------------------
#POST pflanze
curl -X 'POST' 'http://localhost:8080/pflanzen' -H 'accept: */*' -u 'buerger2@web.de:EfGh5678' -H 'Content-Type: multipart/form-data' -F 'lname=italenoa' -F 'dname=ital' -F 'laengengrad=57.812' -F 'breitengrad=67.95' -F 'pflanzentyp=Obstpflanze' -F 'datum='

#POST pflanzen/{pflanzeid}/bilder
curl -X 'POST' 'http://localhost:8080/pflanzen/3/bilder' -H 'accept: */*' -u 'buerger2@web.de:EfGh5678' -H 'Content-Type: multipart/form-data' -F 'pfad=/bilder/itale'

#DELETE pflanzen/{pflanzeid}/bilder/{bildid}
curl -X 'DELETE' 'http://localhost:8080/pflanzen/3/bilder/3' -H 'accept: */*' -u 'buerger2@web.de:EfGh5678'

#PATCH pflanzen/{pflanzeid}/bilder/{bildid}
curl -X 'PATCH' 'http://localhost:8080/pflanzen/2/bilder/2' -H 'accept: */*' -u 'buerger2@web.de:EfGh5678' -H 'Content-Type: multipart/form-data' -F 'pfad=/bilder/rosagallica1.png'
#-----------------------------------------------------

#POST pflegemassnahmen
curl -X 'POST' 'http://localhost:8080/pflegemassnahmen' -H 'accept: */*' -u 'gaertner1@web.de:EfGh5678' -H 'Content-Type: multipart/form-data' -F 'datum=2023-01-15 10:00:00' -F 'pflegeart=Gießen'

#GET pflegemassnahmen/buerger
curl -X 'GET' 'http://localhost:8080/pflegemassnahmen/buerger' -H 'accept: application/json' -u 'gaertner1@web.de:EfGh5678'