# Blatt 4

Umsetzung eines dazugehörigen RESTful Web Services

> ⚠️ Um dieses API-Template zu nutzen, muss `de.hhu.cs.dbs.dbwk.project.api=custom` in [`gradle.properties`](../../gradle.properties) gesetzt werden.

> ⚠️ Unter Umständen müssen die vorhandenen Dateien dieses API-Templates angepasst werden.

## Anleitung

### Allgemein

Die Mainklasse ist [`Application`](src/main/java/de/hhu/cs/dbs/dbwk/project/Application.java).
Nachdem das System mit bspw.

```shell
./gradlew :blatt4:custom:runContainerized
```

gestartet wurde, kann mit [cURL](#nützliche-links) oder [Swagger UI](#nützliche-links) (per http://localhost:8090) die
API getestet werden.

Das System kann mit

```shell
./gradlew :blatt4:custom:composeDown
```

gestoppt werden.

> 🚨 Die Anforderungen der Aufgabenstellung müssen eingehalten werden.

### Wichtig

Sollte sich etwas an der API-Spezifikation ändern, so können Sie mit

```shell
./gradlew :blatt4:custom:updateSpecification
```

Ihre lokale Kopie aktualisieren. Diese wird zudem alle 30 Sekunden invalidiert, sodass sie automatisch beim nächsten
Start der Anwendung per Gradle neu geladen wird.

Wenn es Probleme beim Invalidieren gibt, so können Sie mit

```shell
./gradlew --refresh-dependencies
```

die Abhängigkeiten manuell neu laden.

### Nützliche Links

- [Adoptium](https://adoptium.net/de/)
- [cURL](https://curl.haxx.se)
- [Docker](https://www.docker.com)
- [Gradle](https://gradle.org)
- [Swagger](https://swagger.io)

## Kritische Entscheidungen

...
