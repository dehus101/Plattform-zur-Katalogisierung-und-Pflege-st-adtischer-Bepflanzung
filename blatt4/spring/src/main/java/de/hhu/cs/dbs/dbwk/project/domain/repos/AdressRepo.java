package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Adresse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AdressRepo {
    List<Adresse> getAdressenByStadt(String adressFilter, Connection conn) throws SQLException;

    void insertAdresse(String strasse, String hausnummer, String plz, String stadt, Connection conn) throws SQLException;

    Optional<Adresse> getAdresseByFilter(String strasse, String hausnummer, String plz, String stadt, Connection conn) throws SQLException;

    Optional<Adresse> getAdressByID(int adresseid, Connection conn) throws SQLException;

    void deleteAdresse(Integer adresseid, Connection conn) throws SQLException;

    void updateAdresse(Integer adresseid, String strasse, String hausnummer, String plz, String stadt, Connection conn) throws SQLException;
}
