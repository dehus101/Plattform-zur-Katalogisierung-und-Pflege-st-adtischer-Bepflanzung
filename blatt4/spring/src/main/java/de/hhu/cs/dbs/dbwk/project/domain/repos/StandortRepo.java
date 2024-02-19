package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Standort;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface StandortRepo {
    void insertStandort(float laengengrad, float breitengrad, Connection conn) throws SQLException;

    Optional<Standort> getStandortByKoordinaten(float laengenrad, float breitengrad, Connection conn) throws SQLException;
}
