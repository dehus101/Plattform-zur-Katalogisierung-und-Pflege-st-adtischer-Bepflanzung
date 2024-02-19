package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Adresse;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegeprotokoll;
import de.hhu.cs.dbs.dbwk.project.domain.repos.PflegeprotokollRepo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SQLHelper.executeQuery;

@Repository
public class PflegeprotokollRepoImpl implements PflegeprotokollRepo {
    @Override
    public List<Pflegeprotokoll> getPflegeprotokolle(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(Pflegemassnahme_kommt_vor_Pflegeprotokoll.Pflegemassnahme) AS pflegemassnahmen_anzahl, Gaertner.ROWID AS gaertnerid, *\n" +
                "FROM Pflegeprotokoll\n" +
                "JOIN Pflegemassnahme_kommt_vor_Pflegeprotokoll ON Pflegeprotokoll.ID = Pflegemassnahme_kommt_vor_Pflegeprotokoll.Pflegeprotokoll\n" +
                "JOIN Gaertner ON Pflegeprotokoll.Email = Gaertner.Email\n" +
                "GROUP BY Gaertner.ROWID;;";
        HashMap<Integer, Object> hm = new HashMap<>();
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Pflegeprotokoll> pflegeprotokolle = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Pflegeprotokoll> pflegeprotokoll = PflegeprotokollMapper(resultSet);
            pflegeprotokoll.ifPresent(pflegeprotokolle::add);
        }
        return pflegeprotokolle;
    }

    private Optional<Pflegeprotokoll> PflegeprotokollMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(new Pflegeprotokoll(resultSet.getInt("pflegemassnahmen_anzahl"),
                resultSet.getInt("gaertnerid")));

    }
}
