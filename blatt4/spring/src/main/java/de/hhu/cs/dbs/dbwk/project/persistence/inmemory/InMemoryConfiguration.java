package de.hhu.cs.dbs.dbwk.project.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.*;

import de.hhu.cs.dbs.dbwk.project.domain.repos.BuergerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.GaertnerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.NutzerRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.sqlite.SQLiteDataSource;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class InMemoryConfiguration {

    private final BuergerRepo buergerRepo;
    private final GaertnerRepo gaertnerRepo;

    private final SQLiteDataSource dataSource;

    public InMemoryConfiguration(
            BuergerRepo buergerRepo,
            GaertnerRepo gaertnerRepo,
            SQLiteDataSource dataSource) {
        this.buergerRepo = buergerRepo;
        this.gaertnerRepo = gaertnerRepo;
        this.dataSource = dataSource;
    }

    @ConditionalOnBean(InMemoryRoleRepository.class)
    @Bean
    Set<Role> roles() {
        Role nutzer = new SimpleRole("NUTZER");
        Role buerger = new SimpleRole("BUERGER");
        Role geartner = new SimpleRole("GAERTNER");
        return Set.of(nutzer, buerger, geartner);
    }

    private Optional<User> findUserByUsername(List<User> users, String username) {
        return users.stream().filter(u -> u.getUniqueString().equals(username)).findFirst();
    }

    @ConditionalOnBean(InMemoryUserRepository.class)
    @Bean
    Set<User> users() {
        try (Connection conn = dataSource.getConnection()) {
            try {
                Role nutzerRolle = new SimpleRole("NUTZER");
                Role buergerRolle = new SimpleRole("BUERGER");
                Role geartnerRolle = new SimpleRole("GAERTNER");
                ArrayList<Buerger> buerger =
                        buergerRepo.getAllBuerger(conn);
                ArrayList<Gaertner> gaertner = gaertnerRepo.getAllGaertner(conn);
                ArrayList<User> users = new ArrayList<>();

                for (Buerger b : buerger) {
                    users.add(
                            new SimpleUser(b.getNutzer().getEmail(),
                                    "{noop}" + b.getNutzer().getPasswort(), Set.of(nutzerRolle, buergerRolle)));
                }

                for (Gaertner g : gaertner) {
                    users.add(
                            new SimpleUser(g.getNutzer().getEmail(),
                                    "{noop}" + g.getNutzer().getPasswort(), Set.of(nutzerRolle, geartnerRolle)));
                }

                return Set.copyOf(users);

            } catch (Exception e) {
                throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
