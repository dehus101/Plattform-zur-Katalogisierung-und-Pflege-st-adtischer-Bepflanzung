INSERT INTO Nutzer (Email, Vorname, Nachname, Passwort)
VALUES
  ('johndoe@example.com', 'John', 'Doe', 'AbCd1234'),
  ('janesmith@example.com', 'Jane', 'Smith', 'XyZ45678');

INSERT INTO Buerger (Email, Wohnort)
VALUES
  ('johndoe@example.com', 1),
  ('janesmith@example.com', 2);

INSERT INTO Gaertner (Email)
VALUES
  ('johndoe@example.com'),
  ('janesmith@example.com');

INSERT INTO Wohnort (ID, Stadt, Strasse, Hausnummer, PLZ)
VALUES
  (1, 'Musterstadt', 'Musterstraße', 123, 12345),
  (2, 'Beispielstadt', 'Beispielstraße', 456, 67890);

INSERT INTO Pflegemassnahme (ID, Datum, Pflegeart, Email)
VALUES
  (1, '2023-01-15 10:00:00', 1, 'johndoe@example.com'),
  (2, '2023-02-20 14:30:00', 2, 'janesmith@example.com');

INSERT INTO Pflegeart (ID, Art)
VALUES
  (1, 'Gießen'),
  (2, 'Düngen');

INSERT INTO Pflegeprotokoll (ID, Email)
VALUES
  (1, 'johndoe@example.com'),
  (2, 'janesmith@example.com');

INSERT INTO Pflanze (ID, 'lateinische Bezeichnung', 'deutsche Bezeichnung', Email, Standort, Pflanzentyp)
VALUES
  (1, 'Lavandula angustifolia', 'Lavendel', 'johndoe@example.com', 1, 1),
  (2, 'Rosa gallica', 'Gallische Rose', 'janesmith@example.com', 2, 2);

INSERT INTO Standort (ID, Breitengrad, Laengengrad)
VALUES
  (1, '51.12345', '10.98765'),
  (2, '48.54321', '9.87654');

INSERT INTO Pflanzentyp (ID, Typ)
VALUES
  (1, 'Zierpflanze'),
  (2, 'Obstpflanze');

INSERT INTO Bild (ID, Bildpfad, Pflanze)
VALUES
  (1, '/bilder/lavendel1.jpg', 1),
  (2, '/bilder/rosagallica1.jpg', 2);

INSERT INTO Spezialisierung (ID, Name)
VALUES
  (1, 'Kräutergarten'),
  (2, 'Rosengarten');

INSERT INTO Buerger_teilnehmen_Pflegemassnahme (Email, Pflegemassnahme)
VALUES
  ('johndoe@example.com', 1),
  ('janesmith@example.com', 2);

INSERT INTO Pflegemassnahme_referenzieren (Pflegemassnahme1, Pflegemassnahme2)
VALUES
  (1, 2),
  (2, 1);

INSERT INTO Pflegemassnahme_kommt_vor_Pflegeprotokoll (Pflegemassnahme, Pflegeprotokoll, Erfolg)
VALUES
  (1, 1, 1),
  (2, 2, 0);

INSERT INTO Gaertner_bewertet_Pflegemassnahme (Pflegemassnahme, Email, Skala)
VALUES
  (1, 'johndoe@example.com', 4),
  (2, 'janesmith@example.com', 5);

INSERT INTO Pflegemassnahme_versorgt_Pflanze (Pflegemassnahme, Pflanze)
VALUES
  (1, 1),
  (2, 2);

INSERT INTO Gaertner_pflanzt_Pflanze (Pflanze, Email, Pflanzdatum)
VALUES
  (1, 'johndoe@example.com', '2023-03-01'),
  (2, 'janesmith@example.com', '2023-04-15');

INSERT INTO Gaertner_hat_Spezialisierung (Email, Spezialisierung)
VALUES
  ('johndoe@example.com', 1),
  ('janesmith@example.com', 2);
