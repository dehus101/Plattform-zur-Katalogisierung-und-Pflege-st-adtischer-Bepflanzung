[0 FP] Nicht-optionale Spalten sind gekennzeichnet
[0 FP] Vorgabe bzgl. nicht-leerer Zeichenketten umgesetzt
[1 FP] Vorgaben bzgl. ASCII-Zeichen umgesetzt
- Deine CHECKs stimmen hier nicht, Du musst hier mit GLOB und Negierung arbeiten. Teilweise prüfst Du auch nur, ob mind. ein ASCII-Zeichen vorkommt.
  [0 FP] Vorgaben zur E-Mail-Adresse umgesetzt
  [0 FP] Format von Datumsangaben bzw. Uhrzeitangaben wird eingehalten
  [0 FP] Geldbeträge und Prozentwerte haben maximal 2 Nachkommastellen
  [1 FP] Wertebereiche, die nur aus Zahlen bestehen, müssen ggf. hinsichtlich des Vorzeichens und/oder der In- bzw. Exkludierung der 0 eingeschränkt werden
- Hausnummer
  [0 FP] Schlüsselkandidaten (außer solche mit BLOB's) sind gekennzeichnet (UNIQUE)
  [0 FP] Modifikationen (Bearbeiten/Löschen) bei nicht-künstlichen PKs werden kaskadiert
  [0 FP] Jedes Attribut (Datentyp VARCHAR + Element Schlüsselkandidat) sowie jede E-Mail-Adresse und jede URI sind case-insensitive
  [0 FP] Jede Tabelle hat genau einen Primärschlüssel

Weitere vorgegebene Sachverhalte

[0 FP] Das Passwort muss zwischen 4 und 8 Zeichen lang sein und besteht aus mindestens drei Ziffern und mindestens zwei Großbuchstaben (lateinisches Alphabet). Auf einen Konsonanten darf kein Zeichen aus der Menge {Q, W, E, R, T, Y} folgen
[0 FP] Der Wertebereich der Bewertungsskala ist {1, ... ,5}.
[0 FP] Die Bezeichnung der Pflegeart, der Spezialisierung und des Pflanzentyps besteht nur aus Zeichen des lateinischen Alphabets.
[0 FP] Ein Gärtner kann nur Pflegemaßnahmen bewerten, die auch von diesem Gärtner betreut werden.
[0 FP] Eine Pflanze kann höchstens 5 zugeordnete Bilder haben.
[0 FP] Bürger können nur an Pflegemaßnahmen teilnehmen, welche keine anderen Pflegemaßnahmen voraussetzen.
[0 FP] Das Pflanzdatum hat das Format "YYYY-MM-DD".

Falsche Datentypen

[1 FP] PLZ als Integer ist ungeeignet (führende Nullen)

Individuelle Fehler

[1 FP] Anfrage 2 ist falsch (hier muss mit der MAX-Aggregatfunktion gearbeitet werden)

Anmerkungen

-

Bewertung

[4 FP]

Hinweis: Bitte wenden Sie sich bei Fragen zur Korrektur direkt an Ihren zuständigen Korrektor. Nutzen Sie hierfür bitte die Sprechstunden.

- Korrigiert von: Christian Pütz, christian.puetz@hhu.de