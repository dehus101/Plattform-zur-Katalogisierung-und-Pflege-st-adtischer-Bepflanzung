package de.hhu.cs.dbs.dbwk.project.domain.services;

import de.hhu.cs.dbs.dbwk.project.domain.model.*;

import java.util.List;
import java.util.Optional;

public interface AnwenderService {


    List<Nutzer> getAllNutzerWithFilter(String email, String nachname);

    Optional<Nutzer> insertNutzer(String email, String passwort, String vorname, String nachname);

    List<Adresse> getAdressenByStadt(String stadt);

    Optional<Adresse> insertAdresse(String strasse, String hausnummer, String plz, String stadt);

    List<Buerger> getAllBuergerWithFilter(String stadt);

    Optional<Buerger> insertBuerger(String email, String passwort, String vorname, String nachname, int adresseid);

    List<Gaertner> getAllGaertnerByFilter(String email);

    Optional<Gaertner> insertGaertner(String email, String passwort, String vorname, String nachname, String spezialgebiet);

    List<Pflanze> getAllPflanzenByFilter(String dname);

    List<Pflegemassnahme> getAllPflegemassnahmenByFilter(String pflegeart, Float minAvgBewertung);

    List<Pflegeprotokoll> getAllPflegeprotokolle();

    List<Bewertung> getBewertungen();

    List<Bild> getBilder(int pflanzeid);

    void deleteAdresse(Integer adresseid);

    void updateAdresse(int adresseid, String strasse, String hausnummer, String plz, String stadt);
}
