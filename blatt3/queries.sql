--Geben Sie genau die Pflanzen aus, die wildwachsend sind.
SELECT *
FROM Pflanze
WHERE ID NOT IN (SELECT Pflanze FROM Gaertner_pflanzt_Pflanze);


--Geben Sie die fleißigsten BürgerInnen aus (höchste Anzahl an Teilnahmen an Pflegemaßnahmen).
SELECT B.Email, COUNT(*) AS AnzahlDerTeilnahmen
FROM Buerger B
JOIN Buerger_teilnehmen_Pflegemassnahme P ON B.Email = P.Email
GROUP BY B.Email
ORDER BY AnzahlDerTeilnahmen DESC;

--Geben Sie alle Pflegemaßnahmen aus, die zwischen dem 12.12.2022 und dem 30.02.2023 stattfanden.
SELECT *
FROM Pflegemassnahme
WHERE Datum BETWEEN '2022-12-12' AND '2023-02-28';
