# Blatt 4

Umsetzung eines dazugehörigen RESTful Web Services

> ⚠️ Um dieses API-Template zu nutzen, muss `de.hhu.cs.dbs.dbwk.project.api=spring` in [`gradle.properties`](../../gradle.properties) gesetzt werden.

> ⚠️ Unter Umständen müssen die vorhandenen Dateien dieses API-Templates angepasst werden.

## Anleitung

### Allgemein

Die Mainklasse ist [`Application`](src/main/java/de/hhu/cs/dbs/dbwk/project/Application.java).
Nachdem das System mit bspw.

```shell
./gradlew :blatt4:spring:runContainerized
```

gestartet wurde, kann mit [cURL](#nützliche-links) oder [Swagger UI](#nützliche-links) (per http://localhost:8090) die
API getestet werden.

Das System kann mit

```shell
./gradlew :blatt4:spring:composeDown
```

gestoppt werden.

Alle Endpunkte der bereitgestellten OpenAPI-Spezifikation müssen im
Package [`de.hhu.cs.dbs.dbwk.project.presentation.rest`](src/main/java/de/hhu/cs/dbs/dbwk/project/presentation/rest) hinzugefügt
werden. Die darin enthaltene Klasse [**`ExampleController`**](src/main/java/de/hhu/cs/dbs/dbwk/project/presentation/rest/ExampleController.java) dient als Beispiel dafür.

Ob in den Controllern selbst mit der Datenbank kommuniziert wird oder dafür im
Package [`de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite`](src/main/java/de/hhu/cs/dbs/dbwk/project/persistence/sql/sqlite)
entsprechende Repositories angelegt werden, kann ausgesucht werden. Vergessen Sie nicht eine korrekte Authentifizierung
und Autorisierung zu implementieren, indem Sie die
Interfaces [`UserRepository`](src/main/java/de/hhu/cs/dbs/dbwk/project/model/UserRepository.java)
und [`RoleRepository`](src/main/java/de/hhu/cs/dbs/dbwk/project/model/RoleRepository.java) des
Package [`de.hhu.cs.dbs.dbwk.project.model`](src/main/java/de/hhu/cs/dbs/dbwk/project/model) im
Package [`de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite`](src/main/java/de/hhu/cs/dbs/dbwk/project/persistence/sql/sqlite) als
Spring Bean umsetzen.

> 🚨 Die Anforderungen der Aufgabenstellung müssen eingehalten werden.

### Wichtig

Sollte sich etwas an der API-Spezifikation ändern, so können Sie mit

```shell
./gradlew :blatt4:spring:updateSpecification
```

Ihre lokale Kopie aktualisieren. Diese wird zudem alle 30 Sekunden invalidiert, sodass sie automatisch beim nächsten
Start der Anwendung per Gradle neu geladen wird.

Wenn es Probleme beim Invalidieren gibt, so können Sie mit

```shell
./gradlew :blatt4:spring:clean --refresh-dependencies
```

die mit Gradle generierten Dateien löschen und die Abhängigkeiten manuell neu laden.

### Nützliche Links

- [Adoptium](https://adoptium.net/de/)
- [cURL](https://curl.haxx.se)
- [Docker](https://www.docker.com)
- [Gradle](https://gradle.org)
- [Spring](https://spring.io)
- [Swagger](https://swagger.io)

## Kritische Entscheidungen

#### Ich habe jede Fehleingabe behandelt und jedes Szenario durchgespielt und dazu eine passende Fehlermeldung ausgegeben, sodass der Nutzer weiß worum es sich handelt.


### POST /buerger:
>verschiedene Bürger können die selben Adressen haben. Das ist gut möglich, wenn die Bürger in einem Hochhaus wohnen, wo
> das Hochhaus natürlich die selbe Adresse hat.

### GET /pflegemassnahmen:
>die min. Bewertung soll die normale Bewertung sein. Weil jede Pflegemassnahme nur eine Bewertung haben kann. So wurde
> es auch von den anderen Mitstudierenden bestätigt, die es von Tutoren gehört haben.
> Trotzdem habe ich den Durchschnitt berechnet, was aber nutzlos los ^^

### POST /pflanzen:
>Es kann nur eine Pflanze an einem bestimmten Standort geben. Genauso wie bei Google wo ein Ort oder Stelle an bestimmten Koordinanten liegt.

### POST /pflegemassnahmen:
>Die Eingabe Datum muss hier mit Datum UND Uhrzeit angegeben werden, weil hier ein Datetime gefordert wird. Im Blatt 3 steht:
  "*Datumsangaben müssen im Format **YYYY-MM-DD HH:MM:SS** sein*". Dies wurde in Korrektur 3 berücksichtigt und nicht als Fehler markiert.
  Wenn man nun ein Datum schreibt kann man einfach dann **00:00:00** hinzufügen.

### GET /pflegemassnahmen/buerger
> Ich habe nochmal extra eine Methode für /pflegemassnahmen/{pflegemassnahmeid}/buerger hinzufügt, um die Bürger EINER BESTIMMTEN pflegemassnahme anzuzeigen.
> Außerdem fehlt das Schloss hier und man kann sich nicht als Gärtner authentifzieren. Übers Terminal oder Postman klappt alles wunderbar, wenn man sich als Gärtner anmelden