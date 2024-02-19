# Blatt 4

Umsetzung eines dazugehörigen RESTful Web Services

> ⚠️ Um dieses API-Template zu nutzen, muss `de.hhu.cs.dbs.dbwk.project.api=jersey` in [`gradle.properties`](../../gradle.properties) gesetzt werden.

> ⚠️ Unter Umständen müssen die vorhandenen Dateien dieses API-Templates angepasst werden.

## Anleitung

### Allgemein

Die Mainklasse ist [`Application`](src/main/java/de/hhu/cs/dbs/dbwk/project/Application.java).
Nachdem das System mit bspw.

```shell
./gradlew :blatt4:jersey:runContainerized
```

gestartet wurde, kann mit [cURL](#nützliche-links) oder [Swagger UI](#nützliche-links) (per http://localhost:8090) die
API getestet werden.

Das System kann mit

```shell
./gradlew :blatt4:jersey:composeDown
```

gestoppt werden.

Alle Endpunkte der bereitgestellten OpenAPI-Spezifikation müssen im
Package [`de.hhu.cs.dbs.dbwk.project.presentation.rest`](src/main/java/de/hhu/cs/dbs/dbwk/project/presentation/rest) hinzugefügt
werden. Die darin enthaltene Klasse [**`ExampleController`**](src/main/java/de/hhu/cs/dbs/dbwk/project/presentation/rest/ExampleController.java) dient als Beispiel dafür.

Ob in den Controllern selbst mit der Datenbank kommuniziert wird oder dafür im
Package [`de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite`](src/main/java/de/hhu/cs/dbs/dbwk/project/persistence/sql/sqlite)
entsprechende Repositories angelegt werden, kann ausgesucht werden. Vergessen Sie nicht eine korrekte Authentifizierung
und Autorisierung zu ermöglichen, indem Sie die
Interfaces [`UserRepository`](src/main/java/de/hhu/cs/dbs/dbwk/project/model/UserRepository.java)
und [`RoleRepository`](src/main/java/de/hhu/cs/dbs/dbwk/project/model/RoleRepository.java) des
Package [`de.hhu.cs.dbs.dbwk.project.model`](src/main/java/de/hhu/cs/dbs/dbwk/project/model) im
Package [`de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite`](src/main/java/de/hhu/cs/dbs/dbwk/project/persistence/sql/sqlite) implementieren und dann per Binding in [`CustomBinder`](src/main/java/de/hhu/cs/dbs/dbwk/project/dic/CustomBinder.java) in [`de.hhu.cs.dbs.dbwk.project.dic`](src/main/java/de/hhu/cs/dbs/dbwk/project/dic) bereitstellen.

> 🚨 Die Anforderungen der Aufgabenstellung müssen eingehalten werden.

### Wichtig

Sollte sich etwas an der API-Spezifikation ändern, so können Sie mit

```shell
./gradlew :blatt4:jersey:updateSpecification
```

Ihre lokale Kopie aktualisieren. Diese wird zudem alle 30 Sekunden invalidiert, sodass sie automatisch beim nächsten
Start der Anwendung per Gradle neu geladen wird.

Wenn es Probleme beim Invalidieren gibt, so können Sie mit

```shell
./gradlew :blatt4:jersey:clean --refresh-dependencies
```

die mit Gradle generierten Dateien löschen und die Abhängigkeiten manuell neu laden.

### Nützliche Links

- [Adoptium](https://adoptium.net/de/)
- [cURL](https://curl.haxx.se)
- [Docker](https://www.docker.com)
- [Gradle](https://gradle.org)
- [Jersey](https://eclipse-ee4j.github.io/jersey/)
- [Swagger](https://swagger.io)

## Kritische Entscheidungen

...
