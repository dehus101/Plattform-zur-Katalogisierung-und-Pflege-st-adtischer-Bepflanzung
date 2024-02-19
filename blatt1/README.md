# Teil 1: ER-Modellierung der Datenbank

## Fragen während der Bearbeitung
- Braucht die Entität "Pflegeprotokoll" das Attribut "Info über Pflegemaßnahme" wirklich, wenn es schon eine Beziehung gibt "Pflegemaßnahme_kommt_vor_Pflegeprotokoll"?
- wie kann man "wildwachsend" im Er-Modell darstellen?
- Ist Erfolg und Bewertung dasselbe? Aus der Aufgabenstellung ist es unklar. Für mich wäre das Attribut Erfolg ein Boolean und das Attribut Bewertung eine Skala 1-5 also ein Integer. 
  Ich habe nun gehört, dass Erfolg und Bewertung gleich sind und somit die Beziehung "bewertet" mit dem Beziehungsattribut Bewertung zwischen Pflegemassnahme und Gaertner wegfällt.



## Kritische Entscheidungen

Im folgenden werden alle Entscheidungen bezüglich der Modellierung des ER-Modells begründet:


### Entitäten

- Es ist keine Mehrfachbewertung nötig, deshalb wird die Bewertung nicht als seperate Entität ausgelagert und ist dann in der Form:
 `Gärtner - [0,*] - bewertet - [0,*]  - Pflegemaßname`

Entity Nutzer:
- Attribut Email ist Primärschlüssel, weil es eindeutig ist. (Ich hätte eigentlich einen Attribut Benutzername als Primär markieren gewählt, aber die Aufgabenstellung sagt Email ;D)

Entity Pflanze:
- Attribut Pflanzdatum ist optional.

Entity Pflegeprotokoll:
- Ich habe mich dazu entschieden, dass das Atribut Erfolg ein Beziehungsattribut wird und kein Attribut von Pflegeprotokoll. Jede Pflegemassnahme, die in einem Pflegeprotokoll vorkommt, wird mit einem Erfolg bewertet. Die genannte "Information über Pflegemaßnahme" soll das Attribut Beschreibung an der Entity Pflegemassnahme sein.
   Durch Verschmelzungen der Tabellen Gaertner, Pflegemassnahme und Pflegeprotokoll kommt der Gärtner an das Beziehungsattribut Erfolg (falls Erfolg die Bewertung ist),
   weil da würde dann zb die Gaertner ID in der Verschmelzung auftauchen und somit eine Verbindung zwischen Gärtner und Pflegeprotokoll entstehen.


### Relationen
Relation _hat_ mit teilnehmenden Entities: _Buerger_ und _Wohnort_ (1:N):
- [1,1]: Ein Buerger hat genau einen Wohnort.
- [0,*]: Ein Wohnort kann keine oder mehrere Buerger haben.

Relation _traegt_ein mit teilnehmenden Entities: _Buerger_ und _Pflanze_ (1:N):
- [0,*]: Ein Buerger kann mehrere Pflanzen eintragen oder auch keine.
- [1,1]: Eine Pflanze wird genau von einem Bürger eingetragen.
 
Relation _wachst_ mit teilnehmenden Entities: _Pflanze_ und _Standort_ (1:N):
- [1,1]: Eine Pflanze hat genau einen Standort.
- [0,*]: An einem Standort können keine oder mehrere Pflanzen sein.

Relation _gehoert_zu mit teilnehmenden Entities: _Pflanze_ und _Pflanzentyp_ (N:1):
- Der Pflanzentyp der Pflanze wurde als eigenständige Entität aufgefasst, um die Wiederverwendbarkeit dieser zu ermöglichen. Dies erleichtert im späteren Verlauf das Filtern nach einem bestimmten Pflanzentyp. Man könnte Pflanzentyp auch als Attribut von Pflanze auffassen, so wäre
   es schon quasi verschmolzen aber als Entität kann man die Sachen besser wiederverwenden und so wird auch Datenredundanz vermieden.
- [1,1]: Eine Pflanze gehört genau zu einem Pflanzentyp.
- [0,*]: Ein Pflanzentyp kann zu keiner oder mehrere Pflanzen gehören.

Relation _speichert_ mit teilnehmenden Entities: _Pflanze_ und _Bild_ (1:N):
- [0,5]: Eine Pflanze kann bis zu 5 Bilder speichern.
- [0,*]: Ein Bild wird gespeichert von keiner oder mehreren Pflanzen.

Relation _pflanzt_ mit teilnehmenden Entities: _Pflanze_ und _Gaertner_ (N:1):
- Wildwachsend ist ein ableitendes Attribut, denn wenn eine Pflanze gepflanzt worden ist, dann ist sie nicht wildwachsend und das gleiche gilt auch analog.
- [1,1]: Eine Pflanze wird genau von einem Gärtner gepflanzt.
- [0,*]: Ein Gärtner pflanzt keine oder mehrere Pflanzen.

Relation _hat_ mit teilnehmenden Entities: _Gaertner_ und _Spezialisierung_ (N:M):
- [1,*]: Ein Gärtner hat mindestens eine odere mehrere Spezialisierungen.
- [0,*]: Eine Spezialisierung können keine oder mehrere Gärtner haben.

Relation _teilnehmen_ mit teilnehmenden Entities: _Pflegemassnahme_ und _Gaertner_ (1:N):
- [1,1]: An einer Pflegemassnahme kann genau ein Gärtner teilnehmen.
- [0,*]: Gärtner können an keiner oder an mehreren Pflegemassnahmen teilnehmen.

Relation _teilnehmen_ mit teilnehmenden Entities: _Buerger_ und _Pflegemassnahme_ (N:M):
- [0,*]: Ein Buerger kann an mehreren Pflegemassnahmen teilnehmen.
- [0,*]: An einer Pflegemassnahme können beliebig viele Bürger teilnehmen.

Relation _kommt_vor mit teilnehmenden Entities: _Pflegemassnahme_ und _Pflegeprotokoll_ (N:M):
- [0,*]: Jede Pflegemaßnahme kann in beliebig vielen Protokollen vorkommen.
- [1,*]: Jedes Protokoll enthält mindestens eine Pflegemaßnahme.

Relation _erstellt_ mit teilnehmenden Entities: _Gaertner_ und _Pflegeprotokoll_ (N:1):
- [1,1]: Jedes Pflegeprotokoll wird von genau einem Gärtner erstellt.
- [0,*]: Gärtner können keine oder mehrere Pflegeprotokolle erstellen.

Relation _gehoert_zu mit teilnehmenden Entities: _Pflegemassnahme_ und _Pflegeart_ (N:1):
- Pflegeart ist auch als eigenständige Entität gewählt worden wie oben das gleiche mit der Datenredundanz.
- [1,1]: Jede Maßnahme gehört genau einer Pflegeart an.
- [0,*]: Eine Pflegeart kann zu keiner oder mehreren Pflegemassnahmen gehören.


Relation teilnehmen:
Die Beziehung könnte als ternäre Beziehung mit Bürger - Gärtner - Pflegemaßnahme aufgefasst werden, nur würde die Einschränkung dazuführen, dass leere Felder in einem Tupel vorhanden wären und das soll vermieden werden.
Deshalb wird die ternäre Beziehung in zwei binäre Beziehungen umgewandelt.
