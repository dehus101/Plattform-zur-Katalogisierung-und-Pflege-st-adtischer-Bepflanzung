package de.hhu.cs.dbs.dbwk.project.domain.services;

import de.hhu.cs.dbs.dbwk.project.domain.model.Bild;
import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanze;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface BuergerService {
    Optional<Buerger> getBuergerByEmail(String email);

    Optional<Pflanze> insertPflanze(String lname, String dname, float laengenrad, float breitengrad, String pflanzentyp, Buerger buerger);

    Optional<Bild> insertBildForPflanze(int pflanzeid, String pfad, Buerger buerger);

    void deleteBild(int pflanzeid, int bildid, Buerger buerger);

    void updateBildFromPflanze(int pflanzeid, int bildid, String pfad);
}
