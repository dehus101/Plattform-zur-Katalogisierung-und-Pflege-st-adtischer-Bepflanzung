package de.hhu.cs.dbs.dbwk.project.presentation.rest.controller;

import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.Gaertner;
import de.hhu.cs.dbs.dbwk.project.domain.model.User;
import de.hhu.cs.dbs.dbwk.project.domain.model.dto.PflegemassnahmeDTO;
import de.hhu.cs.dbs.dbwk.project.domain.services.GaertnerService;
import de.hhu.cs.dbs.dbwk.project.presentation.rest.ControllerHelper;
import de.hhu.cs.dbs.dbwk.project.security.CurrentUser;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static de.hhu.cs.dbs.dbwk.project.presentation.rest.ControllerHelper.errorResponse;

@RequestMapping("/")
@RestController
@RolesAllowed("GAERTNER")
public class GaertnerController {

    private final GaertnerService gaertnerService;

    private final SQLiteDataSource dataSource;

    public GaertnerController(GaertnerService gaertnerService, SQLiteDataSource dataSource) {
        this.gaertnerService = gaertnerService;
        this.dataSource = dataSource;
    }

    @PostMapping("pflegemassnahmen")
    public ResponseEntity<?> insertBildForPflanze(
            @CurrentUser User user,
            @RequestParam(value = "datum") String datum,
            @RequestParam(value = "pflegeart") String pflegeart) {
        try {
            Gaertner gaertner = gaertnerService.getGaertnerByEmail(user.getUniqueString()).orElseThrow();
            Optional<PflegemassnahmeDTO> pflegemassnahme = gaertnerService.insertPflegemassnahme(datum, pflegeart, gaertner);
            return pflegemassnahme.map(
                            value ->
                                    ResponseEntity.status(HttpStatus.CREATED)
                                            .body(
                                                    "Pflegemassnahme wurde erstellt."))
                    .orElseGet(ControllerHelper::somethingWentWrongResponse);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    //Einmal mit Pathvariable pflegemassnahmeid für eine BESTIMMTE Pflegemassnahme
    @GetMapping("/pflegemassnahmen/{pflegemassnahmeid}/buerger")
public ResponseEntity<?> getTeilnehmendeBuergerAnBestimmtePflegemassnahme(@PathVariable("pflegemassnahmeid") Integer pflegemassnahmeid) {
    List<Map<String, Object>> teilnehmendenBuerger = new ArrayList<>();

    try (Connection connection = dataSource.getConnection()) {
        StringBuilder queryBuilder = new StringBuilder(
            """
            SELECT Pflegemassnahme.ROWID as pflegemassnahmeid, Buerger.ROWID as buergerid FROM Gaertner
            JOIN Pflegemassnahme ON Gaertner.Email = Pflegemassnahme.Email
            JOIN Buerger_teilnehmen_Pflegemassnahme ON Pflegemassnahme.ID = Buerger_teilnehmen_Pflegemassnahme.Pflegemassnahme
            JOIN Buerger ON Buerger_teilnehmen_Pflegemassnahme.Email = Buerger.Email
            WHERE 1 = 1
            """);

        if (pflegemassnahmeid != null && pflegemassnahmeid != 0) {
            queryBuilder.append(" AND Pflegemassnahme.ROWID = ?");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            int parameterIndex = 1;

            if (pflegemassnahmeid != null && pflegemassnahmeid != 0) {
                preparedStatement.setString(parameterIndex, String.valueOf(pflegemassnahmeid));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean beforeFirst = resultSet.isBeforeFirst();
                if (!beforeFirst) {
                    // Keine Pflegemassnahme gefunden
                    throw new APIException("Es gibt keine Pflegemassnahme mit der ID: " + pflegemassnahmeid + " !!!", HttpStatus.NOT_FOUND);
                }

                while (resultSet.next()) {
                    Map<String, Object> buerger = new LinkedHashMap<>();
                    buerger.put("pflegemassnahmeid", resultSet.getInt("pflegemassnahmeid"));
                    buerger.put("buergerid", resultSet.getString("buergerid"));
                    teilnehmendenBuerger.add(buerger);
                }
            }
        }
    } catch (SQLException e) {
        throw new APIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (APIException a) {
        return errorResponse(a);
    }
    return ResponseEntity.ok(teilnehmendenBuerger);
}




    //TODO: Im Swagger der Endpunkt hat kein Schloss und man bekommt 403
    @GetMapping("/pflegemassnahmen/buerger")
    public List<Map<String, Object>> getTeilnehmendeBuergerAllerPflegemassnahmen() throws SQLException {
        List<Map<String, Object>> teilnehmendenBuerger = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            StringBuilder queryBuilder = new StringBuilder(
            """
            SELECT Pflegemassnahme.ROWID as pflegemassnahmeid, Buerger.ROWID as buergerid FROM Gaertner
            JOIN Pflegemassnahme ON Gaertner.Email = Pflegemassnahme.Email
            JOIN Buerger_teilnehmen_Pflegemassnahme ON Pflegemassnahme.ID = Buerger_teilnehmen_Pflegemassnahme.Pflegemassnahme
            JOIN Buerger ON Buerger_teilnehmen_Pflegemassnahme.Email = Buerger.Email
            """);

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> buerger = new LinkedHashMap<>();
                        buerger.put("pflegemassnahmeid", resultSet.getInt("pflegemassnahmeid"));
                        buerger.put("buergerid", resultSet.getString("buergerid"));
                        teilnehmendenBuerger.add(buerger);
                    }
                }
            }
        }
        return teilnehmendenBuerger;
    }


}
