INSERT INTO Nutzer (Email, Vorname, Nachname, Passwort)
VALUES
  ('johndoe@example.com', 'John', 'Doe', 'AbCd1234'),
  ('janedoe@example.com', 'Jane', 'Doe', 'EfGh5678'),
  ('johnsmith@example.com', 'John', 'Smith', 'AbCd1234'),
  ('janesmith@example.com', 'Jane', 'Smith', 'EfGh5678'),
  ('buerger1@gmail.com', 'Jonas', 'Stumpf', 'EfGh5678'),
  ('gaertnerProfi@web.de', 'Jan', 'Vogel', 'EfGh5678');

INSERT INTO Wohnort (ID, Stadt, Strasse, Hausnummer, PLZ)
VALUES
  (1, 'Musterstadt', 'Musterstraße', 123, 12345),
  (2, 'Beispielstadt', 'Beispielstraße', 456, 67890),
  (3, 'Köln', 'Kölnerstr.', 12, 50667);


INSERT INTO Buerger (Email, Wohnort)
VALUES
  ('johndoe@example.com', 1),
  ('johnsmith@example.com', 2),
  ('buerger1@gmail.com', 3);

INSERT INTO Gaertner (Email)
VALUES
  ('janedoe@example.com'),
  ('janesmith@example.com'),
  ('gaertnerProfi@web.de');

INSERT INTO Pflegeart (ID, Art)
VALUES
  (1, 'Gießen'),
  (2, 'Düngen'),
  (3, 'Reißen');

INSERT INTO Pflegemassnahme (ID, Datum, Pflegeart, Email)
VALUES
  (1, '2023-01-15 10:00:00', 1, 'janedoe@example.com'),
  (2, '2023-02-20 14:30:00', 2, 'janesmith@example.com'),
  (3, '2023-01-14 00:00:00', 3, 'gaertnerProfi@web.de');

INSERT INTO Pflegeprotokoll (ID, Email)
VALUES
  (1, 'janedoe@example.com'),
  (2, 'janesmith@example.com');

INSERT INTO Standort (ID, Breitengrad, Laengengrad)
VALUES
  (1, 51.12345, 10.98765),
  (2, 48.54321, 9.87654);

INSERT INTO Pflanzentyp (ID, Typ)
VALUES
  (1, 'Zierpflanze'),
  (2, 'Obstpflanze');

INSERT INTO Pflanze (ID, lateinische_Bezeichnung, deutsche_Bezeichnung, Email, Standort, Pflanzentyp)
VALUES
  (1, 'Lavandula angustifolia', 'Lavendel', 'johndoe@example.com', 1, 1),
  (2, 'Rosa gallica', 'Gallische Rose', 'johnsmith@example.com', 2, 2);

INSERT INTO Bild (ID, Bildpfad, Pflanze)
VALUES
  (1, '/bilder/lavendel1.jpg', 1),
  (2, '/bilder/rosagallica1.jpg', 2);

INSERT INTO Spezialisierung (ID, Name)
VALUES
  (1, 'Kräutergarten'),
  (2, 'Rosengarten');

-- Pflegemassnahme 1 setzt Pflegemassnahme 2 als Voraussetzung
INSERT INTO Pflegemassnahme_referenzieren (Pflegemassnahme1, Pflegemassnahme2)
VALUES
  (1, 2);

INSERT INTO Buerger_teilnehmen_Pflegemassnahme (Email, Pflegemassnahme)
VALUES
  ('johndoe@example.com', 2),
  ('johnsmith@example.com', 2),
  ('johndoe@example.com', 3);

INSERT INTO Pflegemassnahme_kommt_vor_Pflegeprotokoll (Pflegemassnahme, Pflegeprotokoll, Erfolg)
VALUES
  (1, 1, 1),
  (2, 2, 0);

INSERT INTO Gaertner_bewertet_Pflegemassnahme (Pflegemassnahme, Email, Skala)
VALUES
  (1, 'janedoe@example.com', 4),
  (2, 'janesmith@example.com', 5),
  (3, 'gaertnerProfi@web.de', 3);

INSERT INTO Pflegemassnahme_versorgt_Pflanze (Pflegemassnahme, Pflanze)
VALUES
  (1, 1),
  (2, 2);

INSERT INTO Gaertner_pflanzt_Pflanze (Pflanze, Email, Pflanzdatum)
VALUES
  (1, 'janedoe@example.com', '2023-03-01'),
  (2, 'janesmith@example.com', '2023-04-15');

INSERT INTO Gaertner_hat_Spezialisierung (Email, Spezialisierung)
VALUES
  ('janedoe@example.com', 1),
  ('janesmith@example.com', 2),
  ('gaertnerProfi@web.de', 2);
