/* auskommentiert, weil in der Konsole eingegeben wie in der Aufgabenstellung
PRAGMA auto_vacuum = 1;
PRAGMA encoding = "UTF-8";
PRAGMA foreign_keys = 1;
PRAGMA journal_mode = WAL;
PRAGMA synchronous = NORMAL;
*/


/*
- Hat VARCHAR:
    - nicht die leere Zeichenkette enthalten ✅
    - nur ASCII Zeichen im Bereich 20-7E enthalten ✅
- Nicht optionale Attribute: NOT NULL ✅
- Email: Format X@Y.Z mit X, Y und Z nicht leer ✅
- Password:
    - zwischen 4 und 8 Zeichen, ✅
    - mindestens 2 Großbuchstabe, ✅
    - mindestens 3 Ziffern, ✅
    - auf Vokale folgen keine Ziffern aus der Menge {Q,W, E, R, T, Y} ✅

*/

CREATE TABLE IF NOT EXISTS Nutzer (
    Email VARCHAR(256)
        NOT NULL
        COLLATE NOCASE
        CHECK(
            (substr(Email,1, INSTR(Email,'@')-1) NOT GLOB '*[^A-Za-z0-9]*')
            AND (substr(Email, INSTR(Email,'@')+1, ((INSTR(Email,'.')-1) - (INSTR(Email,'@')+1))+1) NOT GLOB '*[^A-Za-z0-9]*')
            AND (substr(Email, INSTR(Email,'.')+1) NOT GLOB '*[^A-Za-z]*')
            AND Email LIKE '%_@%_.%_'
        ),
    Vorname VARCHAR(34)
        COLLATE NOCASE NOT NULL
        CHECK (
            Vorname GLOB '[ -~]*'
            AND Vorname  != ''
        ),

    Nachname VARCHAR(34)
        COLLATE NOCASE NOT NULL
        CHECK (
            Nachname GLOB '[ -~]*'
            AND Nachname  != ''
        ),

    Passwort VARCHAR(9) NOT NULL CHECK(
        (LENGTH(Passwort) BETWEEN 4 AND 8)
        AND (Passwort NOT GLOB '*[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ][QWERTY]*')
        AND (Passwort GLOB '*[A-Z]*[A-Z]*')
        AND (Passwort GLOB '*[0-9]*[0-9]*[0-9]*')
        AND (Passwort NOT GLOB '*[^ -~]*')
    ),
    PRIMARY KEY (Email)
);

CREATE TABLE IF NOT EXISTS Wohnort (
    ID INTEGER NOT NULL,
    Stadt VARCHAR(256) COLLATE NOCASE NOT NULL
         CHECK (
            (Stadt NOT LIKE '%[^ -~]%')
            AND (LENGTH(Stadt)>0)
        ),
    Strasse VARCHAR(256) COLLATE NOCASE NOT NULL
         CHECK (
            (Strasse NOT LIKE '%[^ -~]%')
            AND (LENGTH(Strasse)>0)
        ),
    Hausnummer VARCHAR(256) NOT NULL,
    PLZ VARCHAR(256) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Buerger (
     Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    Wohnort INTEGER NOT NULL,
    PRIMARY KEY (Email),
    FOREIGN KEY (Email) REFERENCES Nutzer (Email),
    FOREIGN KEY (Wohnort) REFERENCES Wohnort (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Gaertner (
     Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    PRIMARY KEY (Email),
    FOREIGN KEY (Email) REFERENCES Nutzer (Email)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pflegeart (
    ID INTEGER NOT NULL,
    Art VARCHAR(256)
        COLLATE NOCASE
        UNIQUE
        NOT NULL
        CHECK (
            (Art NOT LIKE '%[^ -~]%')
            AND (LENGTH(Art)>0)
        ),
    PRIMARY KEY (ID)

);

/* Die Bezeichnung der Pflegeart besteht nur aus Zeichen des lateinischen Alphabets. ✅ */
CREATE TABLE IF NOT EXISTS Pflegemassnahme (
    ID INTEGER NOT NULL,
    Datum DATETIME NOT NULL
        CHECK ( Datum IS DATETIME(Datum) ),
    Pflegeart INTEGER NOT NULL,
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Pflegeart) REFERENCES Pflegeart (ID),
    FOREIGN KEY (Email) REFERENCES Gaertner (Email)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pflegeprotokoll (
    ID INTEGER NOT NULL,
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Email) REFERENCES Gaertner (Email)
    ON UPDATE CASCADE
    ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS Standort (
    ID INTEGER NOT NULL,
    Breitengrad DOUBLE
        NOT NULL,
    Laengengrad DOUBLE
        NOT NULL,
    PRIMARY KEY (ID)
);


/* Die Bezeichnung der Pflanzentyp besteht nur aus Zeichen des lateinischen Alphabets. ✅ */
CREATE TABLE IF NOT EXISTS Pflanzentyp (
    ID INTEGER NOT NULL,
    Typ VARCHAR(256)
        COLLATE NOCASE
        UNIQUE
        NOT NULL
        CHECK (
            (Typ NOT LIKE '%[^ -~]%')
            AND (LENGTH(Typ)>0)
        ),
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Pflanze (
    ID INTEGER NOT NULL,
    lateinische_Bezeichnung VARCHAR(256) COLLATE NOCASE NOT NULL
         CHECK (
            (lateinische_Bezeichnung NOT LIKE '%[^ -~]%')
            AND (LENGTH(lateinische_Bezeichnung)>0)
        ),
    deutsche_Bezeichnung VARCHAR(256) COLLATE NOCASE NOT NULL
         CHECK (
            (deutsche_Bezeichnung NOT LIKE '%[^ -~]%')
            AND (LENGTH(deutsche_Bezeichnung)>0)
        ),
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    Standort INTEGER NOT NULL,
    Pflanzentyp INTEGER NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Email) REFERENCES Buerger (Email),
    FOREIGN KEY (Standort) REFERENCES Standort (ID),
    FOREIGN KEY (Pflanzentyp) REFERENCES Pflanzentyp (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Bild (
    ID INTEGER NOT NULL,
    Bildpfad VARCHAR(256)
        COLLATE NOCASE
        NOT NULL
         CHECK (
            (Bildpfad NOT LIKE '%[^ -~]%')
            AND (LENGTH(Bildpfad)>0)
        ),
    Pflanze INTEGER
        NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Pflanze) REFERENCES Pflanze (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

/* Die Bezeichnung der Spezialisierung besteht nur aus Zeichen des lateinischen Alphabets. ✅ */
CREATE TABLE IF NOT EXISTS Spezialisierung (
    ID INTEGER NOT NULL,
    Name VARCHAR(256)
        COLLATE NOCASE
        UNIQUE
        NOT NULL
        CHECK (
            (Name NOT LIKE '%[^ -~]%')
            AND (LENGTH(Name)>0)
        ),
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Buerger_teilnehmen_Pflegemassnahme (
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    Pflegemassnahme INTEGER
        NOT NULL,
    PRIMARY KEY (Email, Pflegemassnahme ),
    FOREIGN KEY (Email) REFERENCES Buerger (Email),
    FOREIGN KEY (Pflegemassnahme) REFERENCES Pflegemassnahme (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Pflegemassnahme_referenzieren (
    Pflegemassnahme1 INTEGER
        NOT NULL,
    Pflegemassnahme2 INTEGER
        NOT NULL,
    PRIMARY KEY (Pflegemassnahme1),
    FOREIGN KEY (Pflegemassnahme1) REFERENCES Pflegemassnahme (ID),
    FOREIGN KEY (Pflegemassnahme2) REFERENCES Pflegemassnahme (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pflegemassnahme_kommt_vor_Pflegeprotokoll (
    Pflegemassnahme INTEGER
        NOT NULL,
    Pflegeprotokoll INTEGER
        NOT NULL,
    Erfolg BOOLEAN NOT NULL,
    PRIMARY KEY (Pflegemassnahme, Pflegeprotokoll ),
    FOREIGN KEY (Pflegemassnahme) REFERENCES Pflegemassnahme (ID),
    FOREIGN KEY (Pflegeprotokoll) REFERENCES Pflegeprotokoll (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Gaertner_bewertet_Pflegemassnahme (
    Pflegemassnahme INTEGER
        NOT NULL,
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    Skala INTEGER
        NOT NULL
        CHECK (Skala BETWEEN 1 AND 5),
    PRIMARY KEY (Pflegemassnahme, Email ),
    FOREIGN KEY (Pflegemassnahme) REFERENCES Pflegemassnahme (ID),
    FOREIGN KEY (Email) REFERENCES Gaertner (Email)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pflegemassnahme_versorgt_Pflanze (
    Pflegemassnahme INTEGER
        NOT NULL,
    Pflanze INTEGER
        NOT NULL,
    PRIMARY KEY (Pflegemassnahme, Pflanze ),
    FOREIGN KEY (Pflegemassnahme) REFERENCES Pflegemassnahme (ID),
    FOREIGN KEY (Pflanze) REFERENCES Pflanze (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

/*In der Aufgabenstellung steht: "Datumsangaben müssen im Format YYYY-MM-DD HH:MM:SS sein."
  Aber paar Zeilen dadrunter steht dann: "Das Pflanzdatum hat das Format "YYYY-MM-DD"."

  Ich nehme mal an, nur für das Attribut Pflanzdatum gilt diese Regel und deshalb benutze ich DATE statt DATETIME
*/
CREATE TABLE IF NOT EXISTS Gaertner_pflanzt_Pflanze (
    Pflanze INTEGER
        NOT NULL,
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    Pflanzdatum DATE
        CHECK ( Pflanzdatum IS DATE(Pflanzdatum) ),
    PRIMARY KEY (Pflanze),
    FOREIGN KEY (Pflanze) REFERENCES Pflanze (ID),
    FOREIGN KEY (Email) REFERENCES Gaertner (Email)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Gaertner_hat_Spezialisierung (
    Email VARCHAR(256)
        COLLATE NOCASE
        NOT NULL,
    Spezialisierung INTEGER
        NOT NULL,
    PRIMARY KEY (Email, Spezialisierung),
    FOREIGN KEY (Email) REFERENCES Gaertner (Email),
    FOREIGN KEY (Spezialisierung) REFERENCES Spezialisierung (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

-- Trigger: Eine Pflanze kann höchstens 5 zugeordnete Bilder haben.
CREATE TRIGGER check_Pflanze_maximal_5_Bilder
BEFORE INSERT ON Bild
FOR EACH ROW
WHEN (
    SELECT COUNT(*) FROM Bild WHERE Pflanze = NEW.Pflanze
) >= 5
BEGIN
    SELECT RAISE(ABORT, 'Eine Pflanze kann höchstens 5 zugeordnete Bilder haben');
END;

-- Trigger: Bürger können nur an Pflegemaßnahmen teilnehmen, welche keine anderen Pflegemaßnahmen voraussetzen.
CREATE TRIGGER check_Pflegemassnahme_andere_Pflegemassnahme_voraussetzt
BEFORE INSERT ON Buerger_teilnehmen_Pflegemassnahme
FOR EACH ROW
WHEN (
    SELECT COUNT(*)
    FROM Pflegemassnahme_referenzieren
    WHERE Pflegemassnahme1 = NEW.Pflegemassnahme
) > 0
BEGIN
    SELECT RAISE(ABORT, 'Bürger können nur an Pflegemaßnahmen teilnehmen, die keine anderen Pflegemaßnahmen voraussetzen');
END;

/*
Trigger: Ein Gärtner kann nur Pflegemaßnahmen bewerten, die auch von diesem Gärtner betreut werden

Beispiel Trigger auslösung
-- Gärtner betreut Pflegemassnahme mit ID 1
INSERT INTO Gaertner (Email) VALUES ('gaertner1@example.com');
INSERT INTO Pflegemassnahme (ID, Datum, Pflegeart, Email) VALUES (1, '2023-01-01', 1, 'gaertner1@example.com');

-- Ein anderer Gärtner versucht, eine Bewertung für die Pflegemassnahme mit ID 1 abzugeben
INSERT INTO Gaertner (Email) VALUES ('gaertner2@example.com');
INSERT INTO Gaertner_bewertet_Pflegemassnahme (Pflegemassnahme, Email, Skala) VALUES (1, 'gaertner2@example.com', 4);
 */
CREATE TRIGGER check_gaertner_bewertet_Pflegemassnahme
BEFORE INSERT ON Gaertner_bewertet_Pflegemassnahme
FOR EACH ROW
WHEN NOT EXISTS (
    SELECT 1
    FROM Pflegemassnahme AS pm
    WHERE pm.ID = NEW.Pflegemassnahme AND pm.Email = NEW.Email
)
BEGIN
    SELECT RAISE(ABORT, 'Ein Gärtner kann nur Pflegemaßnahmen bewerten, die er betreut');
END;
