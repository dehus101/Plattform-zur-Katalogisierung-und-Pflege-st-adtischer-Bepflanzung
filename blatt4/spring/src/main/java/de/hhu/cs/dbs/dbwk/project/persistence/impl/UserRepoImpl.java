package de.hhu.cs.dbs.dbwk.project.persistence.impl;

import de.hhu.cs.dbs.dbwk.project.domain.model.*;
import de.hhu.cs.dbs.dbwk.project.domain.repos.BuergerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.GaertnerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.NutzerRepo;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepoImpl implements UserRepository {

    private final NutzerRepo nutzerRepo;

    private final BuergerRepo buergerRepo;
    private final GaertnerRepo gaertnerRepo;
    private final SQLiteDataSource dataSource;

    public UserRepoImpl(NutzerRepo nutzerRepo, BuergerRepo buergerRepo, GaertnerRepo gaertnerRepo, SQLiteDataSource dataSource) {
        this.nutzerRepo = nutzerRepo;
        this.buergerRepo = buergerRepo;
        this.gaertnerRepo = gaertnerRepo;
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findUser(String uniqueString) {
        try (Connection conn = dataSource.getConnection()) {
            Optional<Nutzer> nutzer = nutzerRepo.getNutzerByEmail(uniqueString, conn);
            if (nutzer.isEmpty()) {
                String message = "User with unique string '" + uniqueString + "' does not exist.";
                throw new RuntimeException(message);
            }
            boolean buerger = buergerRepo.isNutzerBuerger(uniqueString, conn);
            boolean gaertner = gaertnerRepo.isNutzerGaertner(uniqueString, conn);
            Set<Role> roles = Set.of();
            if (buerger) {
                roles = Set.of(new SimpleRole("BUERGER"));
            } else if (gaertner) {
                roles = Set.of(new SimpleRole("GAERTNER"));
            }
            return Optional.of(new SimpleUser(nutzer.get().getEmail(), "{noop}" +nutzer.get().getPasswort(), roles));
        } catch (Exception exception) {
            String message = "User with unique string '" + uniqueString + "' cannot be found.";
            throw new RuntimeException(message, exception);
        }
    }

}
