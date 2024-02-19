package de.hhu.cs.dbs.dbwk.project.infrastructure.services;

import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.*;
import de.hhu.cs.dbs.dbwk.project.domain.repos.BildRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.BuergerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.PflanzeRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.StandortRepo;
import de.hhu.cs.dbs.dbwk.project.domain.services.BuergerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class BuergerServiceImpl implements BuergerService {

    private final BuergerRepo buergerRepo;

    private final StandortRepo standortRepo;

    private final PflanzeRepo pflanzeRepo;

    private final BildRepo bildRepo;

    private final SQLiteDataSource dataSource;

    public BuergerServiceImpl(BuergerRepo buergerRepo, StandortRepo standortRepo, PflanzeRepo pflanzeRepo, BildRepo bildRepo, SQLiteDataSource dataSource) {
        this.buergerRepo = buergerRepo;
        this.standortRepo = standortRepo;
        this.pflanzeRepo = pflanzeRepo;
        this.bildRepo = bildRepo;
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Buerger> getBuergerByEmail(String email) {
        try (Connection conn = dataSource.getConnection()) {
            try {
                return buergerRepo.getBuergerByEmail(email, conn);
            } catch (SQLException e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Pflanze> insertPflanze(String lname, String dname, float laengengrad, float breitengrad, String pflanzentyp, Buerger buerger) {
        if (lname.isEmpty() || dname.isEmpty() || laengengrad == 0 || breitengrad == 0 || pflanzentyp.isEmpty() || ofNullable(buerger).isEmpty() ) {
            throw new APIException("Alle Felder müssen angegeben sein!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Standort> vorhandenerStandort = standortRepo.getStandortByKoordinaten(laengengrad, breitengrad, conn);
                    Optional<Pflanzentyp> vorhandenerPflanzentyp = pflanzeRepo.getPflanzentypByName(pflanzentyp, conn);
                    if (vorhandenerStandort.isPresent() ) {
                        conn.close();
                        throw new APIException("Es gibt bereits eine Pflanze an diesem Standort!", HttpStatus.BAD_REQUEST);
                    }
                    if(vorhandenerPflanzentyp.isEmpty()) {
                        pflanzeRepo.insertPflanzentyp(pflanzentyp, conn);

                    }
                    standortRepo.insertStandort(laengengrad, breitengrad, conn);

                    int standortid = standortRepo.getStandortByKoordinaten(laengengrad, breitengrad, conn).orElseThrow(() -> new APIException("Standort konnte nicht hinzugefügt werden!", HttpStatus.BAD_REQUEST)).getStandortid();
                    String buergerEmail = buerger.getNutzer().getEmail();

                    int pflanzentypid = pflanzeRepo.getPflanzentypByName(pflanzentyp, conn).orElseThrow(() -> new APIException("Pflanzentyp konnte nicht hinzugefügt werden!", HttpStatus.BAD_REQUEST)).getPflanzentypid();
                    pflanzeRepo.insertPflanze(lname, dname, buergerEmail, standortid, pflanzentypid, conn);
                    Optional<Pflanze> pflanzeByDname = pflanzeRepo.getPflanzeByEqualDname(dname, conn).stream().findFirst();

                    conn.commit();
                    conn.close();
                    return pflanzeByDname;
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
    public Optional<Bild> insertBildForPflanze(int pflanzeid, String pfad, Buerger buerger) {
        if ( pflanzeid == 0 || pfad.isEmpty() || ofNullable(buerger).isEmpty() ) {
            throw new APIException("Alle Felder müssen angegeben sein!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Pflanze> vorhandenePflanze = pflanzeRepo.getPflanzeByID(pflanzeid, conn);
                    if (vorhandenePflanze.isEmpty() ) {
                        conn.close();
                        throw new APIException("Es gibt keine Pflanze mit der ID " + pflanzeid + "!!!", HttpStatus.BAD_REQUEST);
                    }
                    else {
                        int vorhandenePflanzeId = vorhandenePflanze.get().getPflanzeid();
                        bildRepo.insertBild(vorhandenePflanzeId, pfad, conn);
                        List<Bild> bilder = bildRepo.getBilderByPflanzeID(vorhandenePflanzeId, conn);
                        Optional<Bild> neuesBild = bilder.stream().findFirst();
                        conn.commit();
                        conn.close();
                        return neuesBild;
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
    public void deleteBild(int pflanzeid, int bildid, Buerger buerger) {
        if (pflanzeid == 0 || bildid == 0 || ofNullable(buerger).isEmpty()) {
            throw new APIException("Alle Felder müssen angegeben werden!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Pflanze> vorhandenePflanze = pflanzeRepo.getPflanzeByID(pflanzeid, conn);
                    Optional<Bild> vorhandenesBild = bildRepo.getBildByBildID(bildid, conn);
                    if(vorhandenePflanze.isEmpty()){
                        conn.close();
                        throw new APIException("Es gibt keine Pflanze mit dieser ID " + pflanzeid + " !!!", HttpStatus.NOT_FOUND);
                    } if(vorhandenesBild.isEmpty()){
                        conn.close();
                        throw new APIException("Es gibt kein Bild mit dieser ID " + bildid + " !!!", HttpStatus.NOT_FOUND);
                    }
                    else {
                        bildRepo.deleteBild(bildid, pflanzeid, conn);
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
    public void updateBildFromPflanze(int pflanzeid, int bildid, String pfad) {
        if (pflanzeid == 0 || bildid == 0 || pfad.isEmpty()) {
            throw new APIException("Alle Felder müssen angegeben werden!", HttpStatus.BAD_REQUEST);
        } else {
            try{
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                try {
                    Optional<Pflanze> vorhandenePflanze = pflanzeRepo.getPflanzeByID(pflanzeid, conn);
                    Optional<Bild> vorhandenesBild = bildRepo.getBildByBildID(bildid, conn);
                    if(vorhandenePflanze.isEmpty()){
                        conn.close();
                        throw new APIException("Es gibt keine Pflanze mit dieser ID " + pflanzeid + " !!!", HttpStatus.NOT_FOUND);
                    } if(vorhandenesBild.isEmpty()){
                        conn.close();
                        throw new APIException("Es gibt kein Bild mit dieser ID " + bildid + " !!!", HttpStatus.NOT_FOUND);
                    }
                    else {
                        bildRepo.updateBild(pflanzeid, bildid, pfad, conn);
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
