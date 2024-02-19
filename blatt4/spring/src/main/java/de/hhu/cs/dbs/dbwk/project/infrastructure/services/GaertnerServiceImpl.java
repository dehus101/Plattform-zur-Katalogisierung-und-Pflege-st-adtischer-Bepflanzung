package de.hhu.cs.dbs.dbwk.project.infrastructure.services;

import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.*;
import de.hhu.cs.dbs.dbwk.project.domain.model.dto.PflegemassnahmeDTO;
import de.hhu.cs.dbs.dbwk.project.domain.repos.GaertnerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.NutzerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.PflegemassnahmeRepo;
import de.hhu.cs.dbs.dbwk.project.domain.services.GaertnerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class GaertnerServiceImpl implements GaertnerService {

    private final GaertnerRepo gaertnerRepo;

    private final PflegemassnahmeRepo pflegemassnahmeRepo;

    private final NutzerRepo nutzerRepo;

    private final SQLiteDataSource dataSource;

    public GaertnerServiceImpl(GaertnerRepo gaertnerRepo, PflegemassnahmeRepo pflegemassnahmeRepo, NutzerRepo nutzerRepo, SQLiteDataSource dataSource) {
        this.gaertnerRepo = gaertnerRepo;
        this.pflegemassnahmeRepo = pflegemassnahmeRepo;
        this.nutzerRepo = nutzerRepo;
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Gaertner> getGaertnerByEmail(String email) {
        try (Connection conn = dataSource.getConnection()) {
            try {
                return gaertnerRepo.getGaertnerByEmail(email, conn);
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Optional<PflegemassnahmeDTO> insertPflegemassnahme(String datum, String pflegeart, Gaertner gaertner) {
        if (datum.isEmpty() || pflegeart.isEmpty() || ofNullable(gaertner).isEmpty() ) {
            throw new APIException("Alle Felder müssen angegeben sein!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<PflegemassnahmeDTO> vorhandenePflegemassnahme = pflegemassnahmeRepo.getPflegemassnahmeByEmail(gaertner.getNutzer().getEmail(), conn);
                    Optional<Pflegeart> vorhandenenePflegeart = pflegemassnahmeRepo.getPflegeartByID(pflegeart, conn);
                    if (vorhandenePflegemassnahme.isPresent()) {
                        conn.close();
                        throw new APIException("An einer Pflegemassnahme darf nur ein Gärtner teilnehmen. Du bist schon woanders angemeldet!", HttpStatus.BAD_REQUEST);
                    }
                    if(vorhandenenePflegeart.isPresent()){
                        int vorhandenePflegeartId = vorhandenenePflegeart.get().getPflegeartid();
                        pflegemassnahmeRepo.insertPflegemassnahme(datum, vorhandenePflegeartId, gaertner.getNutzer().getEmail(), conn);
                        Optional<PflegemassnahmeDTO> neuePflegemassnahme = pflegemassnahmeRepo.getPflegemassnahmeByEmail(gaertner.getNutzer().getEmail(), conn);
                        conn.commit();
                        conn.close();
                        return neuePflegemassnahme;
                    }
                    else {
                        pflegemassnahmeRepo.insertPflegeart(pflegeart, conn);
                        Pflegeart neuePflegeart = pflegemassnahmeRepo.getPflegeartByID(pflegeart, conn).orElseThrow(
                                () -> new APIException("Fehler beim Hinzufügen von Pflegeart!", HttpStatus.BAD_REQUEST));
                        int neuePflegeartId = neuePflegeart.getPflegeartid();
                        pflegemassnahmeRepo.insertPflegemassnahme(datum, neuePflegeartId, gaertner.getNutzer().getEmail(), conn);
                        Optional<PflegemassnahmeDTO> neuePflegemassnahme = pflegemassnahmeRepo.getPflegemassnahmeByEmail(gaertner.getNutzer().getEmail(), conn);
                        conn.commit();
                        conn.close();
                        return neuePflegemassnahme;
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
}
