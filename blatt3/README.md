# Blatt 3

Implementierung der Datenbank im vorgegebenen SQL-Dialekt

## Kritische Entscheidungen

- Bei der Tabelle Wohnort die Attribute PLZ und Hausnummer habe ich die bewusst auf Integer gestellt, weil nichts explizites dazu gesagt wurde und ich davon ausgehe,
 dass es sich in im Land Deutschland handelt. Außerdem war das im letzten Semester auch so.

-  Die Bezeichnung der Pflegeart, der Spezialisierung und des Pflanzentyps wurden UNIQUE
 gesetzt, weil die Namen nur einmal in den jeweiligen Tabellen auftauchen und somit Datenredundanz zu vermeiden.

- In der Aufgabenstellung steht: "Datumsangaben müssen im Format YYYY-MM-DD HH:MM:SS sein."
 Aber paar Zeilen dadrunter steht dann: "Das Pflanzdatum hat das Format "YYYY-MM-DD"."
 Ich nehme mal an, nur für das Attribut Pflanzdatum gilt diese Regel und deshalb benutze ich DATE statt DATETIME

- Die Überprüfung der E-Mail erfolgt, indem diese in Substrings zerlegt wird. Damit können die verschiedenen
Anforderungen an X@Y.Z eingehalten werden.
- Länge der E-Mail wurde auf 256 beschränkt.


- Festlegung der Attribute Pflegeart.Art, Spezialisierung.Name und Pflanzentyp.Typ als UNIQUE.
 Dadurch wird verhindert, dass gleiche Eigenschaften mehrmals gespeichert werden. 
 Grund dafür ist, dass Name nicht als Primary Key gewählt wurden.

