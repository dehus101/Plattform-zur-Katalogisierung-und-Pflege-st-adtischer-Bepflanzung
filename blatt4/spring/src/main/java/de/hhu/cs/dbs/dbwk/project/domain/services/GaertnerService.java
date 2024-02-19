package de.hhu.cs.dbs.dbwk.project.domain.services;

import de.hhu.cs.dbs.dbwk.project.domain.model.Gaertner;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegemassnahme;
import de.hhu.cs.dbs.dbwk.project.domain.model.dto.PflegemassnahmeDTO;

import java.util.Optional;

public interface GaertnerService {
    Optional<Gaertner> getGaertnerByEmail(String email);

    Optional<PflegemassnahmeDTO> insertPflegemassnahme(String datum, String pflegeart, Gaertner gaertner);
}
