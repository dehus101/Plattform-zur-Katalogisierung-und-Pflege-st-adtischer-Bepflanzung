package de.hhu.cs.dbs.dbwk.project.infrastructure.services;

import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.*;
import de.hhu.cs.dbs.dbwk.project.domain.repos.*;
import de.hhu.cs.dbs.dbwk.project.domain.services.AnwenderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

@Service
public class AnwenderServiceImpl implements AnwenderService {

    private final NutzerRepo nutzerRepo;

    private final AdressRepo adressRepo;

    private final BuergerRepo buergerRepo;

    private final GaertnerRepo gaertnerRepo;

    private final SpezialisierungRepo spezialisierungRepo;

    private final PflanzeRepo pflanzeRepo;

    private final PflegemassnahmeRepo pflegemassnahmeRepo;

    private final PflegeprotokollRepo pflegeprotokollRepo;

    private final BewertungRepo bewertungRepo;

    private final BildRepo bildRepo;

    private final SQLiteDataSource dataSource;

    private final UserRepository userRepository;


    public AnwenderServiceImpl(NutzerRepo nutzerRepo, AdressRepo adressRepo, BuergerRepo buergerRepo, GaertnerRepo gaertnerRepo, SpezialisierungRepo spezialisierungRepo, PflanzeRepo pflanzeRepo, PflegemassnahmeRepo pflegemassnahmeRepo, PflegeprotokollRepo pflegeprotokollRepo, BewertungRepo bewertungRepo, BildRepo bildRepo, SQLiteDataSource dataSource, UserRepository userRepository) {
        this.nutzerRepo = nutzerRepo;
        this.adressRepo = adressRepo;
        this.buergerRepo = buergerRepo;
        this.gaertnerRepo = gaertnerRepo;
        this.spezialisierungRepo = spezialisierungRepo;
        this.pflanzeRepo = pflanzeRepo;
        this.pflegemassnahmeRepo = pflegemassnahmeRepo;
        this.pflegeprotokollRepo = pflegeprotokollRepo;
        this.bewertungRepo = bewertungRepo;
        this.bildRepo = bildRepo;
        this.dataSource = dataSource;
        this.userRepository = userRepository;
    }

    private static Function<String, String> getFilterString() {
        return s -> "%" + s + "%";
    }

    @Override
    public List<Nutzer> getAllNutzerWithFilter(String email, String nachname) {
        String emailFilter = Optional.ofNullable(email).map(getFilterString()).orElse("%");
        String nachnameFilter = Optional.ofNullable(nachname).map(getFilterString()).orElse("%");
        try (Connection conn = dataSource.getConnection()) {
            try {
                ArrayList<Nutzer> nutzers = nutzerRepo.getNutzerByEmailAndNachname(emailFilter, nachnameFilter, conn);
                if(nutzers.isEmpty()){
                    throw new APIException("Es gibt keinen Nutzer mit dieser Email: " + email + " oder mit diesem Nachname: " + nachname + " !!!", HttpStatus.NOT_FOUND);
                }
                return nutzers;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Nutzer> insertNutzer(String email, String passwort, String vorname, String nachname) {
        if (email.isEmpty() || passwort.isEmpty() || vorname.isEmpty() || nachname.isEmpty()) {
            throw new APIException("All fields must be provided!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Nutzer> vorhandenerNutzer = nutzerRepo.getNutzerByEmail(email, conn);
                    if (vorhandenerNutzer.isPresent()) {
                        conn.close();
                        throw new APIException("Nutzer existiert bereits mit der Email: " + email, HttpStatus.BAD_REQUEST);
                    } else {
                        nutzerRepo.insertNutzer(email, passwort, vorname, nachname, conn);
                        conn.commit();
                        Optional<Nutzer> neuerNutzer = nutzerRepo.getNutzerByEmail(email, conn);
                        return neuerNutzer;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @Override
    public List<Adresse> getAdressenByStadt(String stadt) {
        String adressFilter = Optional.ofNullable(stadt).map(getFilterString()).orElse("%");
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Adresse> adressen = adressRepo.getAdressenByStadt(adressFilter, conn);
                if(adressen.isEmpty()){
                    throw new APIException("Es gibt keine Adresse mit der Stadt: " + stadt + " !!!", HttpStatus.NOT_FOUND);
                }
                return adressen;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Adresse> insertAdresse(String strasse, String hausnummer, String plz, String stadt) {
        if (strasse.isEmpty() || hausnummer.isEmpty() || plz.isEmpty() || stadt.isEmpty()) {
            throw new APIException("All fields must be provided!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Adresse> vorhandeneAdresse = adressRepo.getAdresseByFilter(strasse, hausnummer, plz, stadt, conn);
                    if (vorhandeneAdresse.isPresent()){
                        vorhandeneAdresse.get().setAlreadyExists(true);
                        return vorhandeneAdresse;
                    }else{
                        adressRepo.insertAdresse(strasse, hausnummer, plz, stadt, conn);
                        conn.commit();
                        Optional<Adresse> neueAdresse = adressRepo.getAdresseByFilter(strasse, hausnummer, plz, stadt, conn);
                        return neueAdresse;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public List<Buerger> getAllBuergerWithFilter(String stadt) {
        String stadtFilter = Optional.ofNullable(stadt).map(getFilterString()).orElse("%");
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Buerger> buergers = buergerRepo.getBuergerbyStadt(stadtFilter, conn);
                if(buergers.isEmpty()){
                    throw new APIException("Es gibt keine Bürger die in der Stadt: " + stadt + " wohnen!!!", HttpStatus.NOT_FOUND);
                }
                return buergers;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Buerger> insertBuerger(String email, String passwort, String vorname, String nachname, int adresseid) {
        if (email.isEmpty() || passwort.isEmpty() || vorname.isEmpty() || nachname.isEmpty() || adresseid == 0 ) {
            throw new APIException("All fields must be provided!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Buerger> vorhandenerBuerger = buergerRepo.getBuergerByEmail(email, conn);
                    Optional<Nutzer> vorhandenerNutzer = nutzerRepo.getNutzerByEmail(email, conn);
                    Optional<Adresse> vorhandeneAdresse = adressRepo.getAdressByID(adresseid, conn);
                    if (vorhandenerBuerger.isPresent() && vorhandenerNutzer.isPresent() && vorhandeneAdresse.isPresent()) {
                        return vorhandenerBuerger;
                    }
                    else if (vorhandenerBuerger.isPresent()) {
                        conn.close();
                        throw new APIException("Buerger existiert bereits mit der Email: " + email, HttpStatus.BAD_REQUEST);
                    }
                    else if(vorhandeneAdresse.isEmpty()){
                        conn.close();
                        throw new APIException("Adresse existiert nicht mit der ID: " + adresseid, HttpStatus.BAD_REQUEST);
                    }
                    else {
                        if (vorhandenerNutzer.isEmpty()) {
                            nutzerRepo.insertNutzer(email, passwort, vorname, nachname, conn);
                        }
                        Optional<Buerger> neuerBuerger = buergerRepo.insertBuerger(email, adresseid, conn);
                        conn.commit();
                        conn.close();
                        return neuerBuerger;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @Override
    public List<Gaertner> getAllGaertnerByFilter(String email) {
        String emailFilter = Optional.ofNullable(email).map(getFilterString()).orElse("%");
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Gaertner> gaertners = gaertnerRepo.getGaertnersByEmail(emailFilter, conn);
                if(gaertners.isEmpty()){
                    throw new APIException("Es gibt kein Gärtner mit der Email: " + email + " !!!", HttpStatus.NOT_FOUND);
                }
                return gaertners;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Gaertner> insertGaertner(String email, String passwort, String vorname, String nachname, String spezialgebiet) {
        if (email.isEmpty() || passwort.isEmpty() || vorname.isEmpty() || nachname.isEmpty() || spezialgebiet.isEmpty() ) {
            throw new APIException("All fields must be provided!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    List<Gaertner> vorhandenerGaertner = gaertnerRepo.getGaertnersByEmail(email, conn);
                    Optional<Nutzer> vorhandenerNutzer = nutzerRepo.getNutzerByEmail(email, conn);
                    Optional<Spezialisierung> vorhandeneSpezialisierung = spezialisierungRepo.getSpezialisierungByName(spezialgebiet, conn);
                    boolean isGaertnerPresent = vorhandenerGaertner.stream().anyMatch(Objects::nonNull);
                    if (vorhandenerNutzer.isPresent() && isGaertnerPresent) {
                        if(vorhandeneSpezialisierung.isPresent()){
                            conn.close();
                            throw new APIException("Spezialgebiet existiert bereits mit dem Namen: " + spezialgebiet, HttpStatus.BAD_REQUEST);
                        }
                        Spezialisierung hinzugefuegteSpezialisierung = spezialisierungRepo.insertSpezialisierung(spezialgebiet, conn)
                                .orElseThrow(() -> new APIException("Fehler beim Hinzufügen der Spezialisierung", HttpStatus.INTERNAL_SERVER_ERROR));
                        gaertnerRepo.InsertGaertnerHatSpezialisierung(email, hinzugefuegteSpezialisierung.getSpezialisierungid(), conn);
                        conn.commit();
                        return vorhandenerGaertner.stream().findFirst();
                    }
                    else if(vorhandenerNutzer.isPresent()){
                        if(vorhandeneSpezialisierung.isPresent()){
                            return getGaertner(email, passwort, conn, vorhandeneSpezialisierung);
                        }
                        List<Gaertner> neuerGaertner = getGaertners(email, spezialgebiet, passwort, conn);
                        conn.commit();
                        return neuerGaertner.stream().findFirst();
                    }
                    else if (isGaertnerPresent) {
                        conn.close();
                        throw new APIException("Gaertner existiert bereits mit der Email: " + email, HttpStatus.BAD_REQUEST);
                    }
                    else {
                        Nutzer hinzugefuegterNutzer = nutzerRepo.insertNutzer(email, passwort, vorname, nachname, conn)
                                .orElseThrow(() -> new APIException("Fehler beim Hinzufügen des Nutzers", HttpStatus.BAD_REQUEST));

                        if(vorhandeneSpezialisierung.isPresent()){
                            return getGaertner(email, passwort, conn, vorhandeneSpezialisierung);
                        }
                        List<Gaertner> neuerGaertner = getGaertners(email, spezialgebiet, passwort, conn);
                        conn.commit();
                        return neuerGaertner.stream().findFirst();
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    private List<Gaertner> getGaertners(String email, String spezialgebiet, String passwort, Connection conn) throws SQLException {
        Spezialisierung hinzugefuegteSpezialisierung = spezialisierungRepo.insertSpezialisierung(spezialgebiet, conn)
                .orElseThrow(() -> new APIException("Fehler beim Hinzufügen der Spezialisierung", HttpStatus.BAD_REQUEST));
        gaertnerRepo.insertGaertner(email, conn);

        gaertnerRepo.InsertGaertnerHatSpezialisierung(email, hinzugefuegteSpezialisierung.getSpezialisierungid(), conn);
        List<Gaertner> neuerGaertner = gaertnerRepo.getGaertnersByEmail(email, conn);
        return neuerGaertner;
    }

    private Optional<Gaertner> getGaertner(String email, String passwort, Connection conn, Optional<Spezialisierung> vorhandeneSpezialisierung) throws SQLException {
        gaertnerRepo.insertGaertner(email, conn);
        Spezialisierung alteSpezialisierung = vorhandeneSpezialisierung.orElseThrow(() -> new APIException("Fehler beim Hinzufügen der Spezialisierung", HttpStatus.BAD_REQUEST));
        gaertnerRepo.InsertGaertnerHatSpezialisierung(email, alteSpezialisierung.getSpezialisierungid(), conn);
        List<Gaertner> neuerGaertner = gaertnerRepo.getGaertnersByEmail(email, conn);
        conn.commit();
        return neuerGaertner.stream().findFirst();
    }

    @Override
    public List<Pflanze> getAllPflanzenByFilter(String dname) {
        String dnameFilter = Optional.ofNullable(dname).map(getFilterString()).orElse("%");
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Pflanze> pflanzeByLikeDname = pflanzeRepo.getPflanzeByLikeDname(dnameFilter, conn);
                if(pflanzeByLikeDname.isEmpty()){
                    throw new APIException("Es gibt keine Pflanzen mit dieser deutschen Bezeichnung: " + dname + " !!!", HttpStatus.NOT_FOUND);
                }
                return pflanzeByLikeDname;

            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Pflegemassnahme> getAllPflegemassnahmenByFilter(String pflegeart, Float minAvgBewertung) {
        String pflegeartFilter = Optional.ofNullable(pflegeart).map(getFilterString()).orElse("%");
        Float minAvgBewertungFilter = Optional.ofNullable(minAvgBewertung).orElse(0.0f);
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Pflegemassnahme> pflegemassnahmen = pflegemassnahmeRepo.getPflegemassnahmenByPflegeartAndDurchschnittbewertung(pflegeartFilter, minAvgBewertungFilter, conn);
                if(pflegemassnahmen.isEmpty()){
                    throw new APIException("Es gibt keine Pflegemassnahmen mit Pflegeart: " + pflegeart + " oder mit der Durchschnittsbewertung: " + minAvgBewertung + " !!!", HttpStatus.NOT_FOUND);
                }
                return pflegemassnahmen;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Pflegeprotokoll> getAllPflegeprotokolle() {
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Pflegeprotokoll> pflegeprotokolle = pflegeprotokollRepo.getPflegeprotokolle(conn);
                return pflegeprotokolle;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Bewertung> getBewertungen() {
        try (Connection conn = dataSource.getConnection()) {
            try {
                return bewertungRepo.getBewertungen(conn);
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Bild> getBilder(int pflanzeid) {
        try (Connection conn = dataSource.getConnection()) {
            try {
                List<Bild> bilder = bildRepo.getBilderByPflanzeID(pflanzeid, conn);
                if(bilder.isEmpty()){
                    throw new APIException("Es gibt keine Bilder einer Pflanze mit der ID: " + pflanzeid + " !!!", HttpStatus.NOT_FOUND);
                }
                return bilder;
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteAdresse(Integer adresseid) {
        if (adresseid == null) {
            throw new APIException("Alle Felder müssen angegeben werden!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Buerger> vorhandenerBuerger = buergerRepo.getBuergerByAdresseID(adresseid, conn);
                    Optional<Adresse> vorhandeneAdresse = adressRepo.getAdressByID(adresseid, conn);

                    if(vorhandeneAdresse.isEmpty()){
                        conn.close();
                        throw new APIException("Adresse mit ID " + adresseid + " existiert nicht!", HttpStatus.NOT_FOUND);
                    }
                    else if(vorhandenerBuerger.isPresent() && vorhandenerBuerger.get().getAdresse() != null){
                        int adresseIDvonBuerger = vorhandenerBuerger.get().getAdresse().getAdresseId();
                        if(adresseIDvonBuerger == adresseid){
                            conn.close();
                            throw new APIException("Diese Adresse mit ID " + adresseid + " ist einem Bürger zugeordnet!", HttpStatus.BAD_REQUEST);
                        }
                    }
                    else {
                        adressRepo.deleteAdresse(adresseid, conn);
                        conn.commit();
                        conn.close();
                    }
                } catch (SQLException e) {
                    throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public void updateAdresse(int adresseid, String strasse, String hausnummer, String plz, String stadt) {
        if (adresseid == 0 || strasse.isEmpty() || hausnummer.isEmpty() || plz.isEmpty() || stadt.isEmpty()) {
            throw new APIException("Alle Felder müssen angegeben werden!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Adresse> vorhandeneAdresse = adressRepo.getAdressByID(adresseid, conn);
                    if(vorhandeneAdresse.isEmpty()){
                        conn.close();
                        throw new APIException("Es gibt keine Adresse mit dieser ID " + adresseid + " !!!", HttpStatus.NOT_FOUND);
                    }
                    else {
                        adressRepo.updateAdresse(adresseid, strasse, hausnummer, plz, stadt, conn);
                        conn.commit();
                        conn.close();
                    }
                } catch (SQLException e) {
                    throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


}