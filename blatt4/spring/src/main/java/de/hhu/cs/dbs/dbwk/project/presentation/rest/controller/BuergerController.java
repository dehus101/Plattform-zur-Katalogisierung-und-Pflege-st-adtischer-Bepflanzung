package de.hhu.cs.dbs.dbwk.project.presentation.rest.controller;

import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.Bild;
import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanze;
import de.hhu.cs.dbs.dbwk.project.domain.model.User;
import de.hhu.cs.dbs.dbwk.project.domain.services.BuergerService;
import de.hhu.cs.dbs.dbwk.project.presentation.rest.ControllerHelper;
import de.hhu.cs.dbs.dbwk.project.security.CurrentUser;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hhu.cs.dbs.dbwk.project.presentation.rest.ControllerHelper.errorResponse;

@RequestMapping("/")
@RestController
@RolesAllowed("BUERGER")
public class BuergerController {

    private final BuergerService buergerService;

    public BuergerController(BuergerService buergerService) {
        this.buergerService = buergerService;
    }

    @PostMapping("pflanzen")
    public ResponseEntity<?> insertPflanze(
            @CurrentUser User user,
            @RequestParam(value = "lname") String lname,
            @RequestParam(value = "dname") String dname,
            @RequestParam(value = "laengengrad") float laengengrad,
            @RequestParam(value = "breitengrad") float breitengrad,
            @RequestParam(value = "pflanzentyp") String pflanzentyp) {
        try {
            Buerger buerger = buergerService.getBuergerByEmail(user.getUniqueString()).orElseThrow();
            Optional<Pflanze> pflanze = buergerService.insertPflanze(
                    lname, dname, laengengrad, breitengrad, pflanzentyp, buerger);
            return pflanze.map(
                            value ->
                                    ResponseEntity.status(HttpStatus.CREATED)
                                            .body(
                                                    "Pflanze mit ID "
                                                            + value.getPflanzeid()
                                                            + " wurde erstellt."))
                    .orElseGet(ControllerHelper::somethingWentWrongResponse);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

     @PostMapping("pflanzen/{pflanzeid}/bilder")
    public ResponseEntity<?> insertBildForPflanze(
            @CurrentUser User user,
            @PathVariable("pflanzeid") int pflanzeid,
            @RequestParam(value = "pfad") String pfad) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new APIException("Unauthorized: Nur Bürger haben Zugriff auf diesen Endpunkt.", HttpStatus.UNAUTHORIZED);
            }
            Buerger buerger = buergerService.getBuergerByEmail(user.getUniqueString()) .orElseThrow();
            Optional<Bild> bild = buergerService.insertBildForPflanze(
                    pflanzeid, pfad, buerger);
            return bild.map(
                            value ->
                                    ResponseEntity.status(HttpStatus.CREATED)
                                            .body(
                                                    "Bild wurde erstellt."))
                    .orElseGet(ControllerHelper::somethingWentWrongResponse);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @DeleteMapping("pflanzen/{pflanzeid}/bilder/{bildid}")
    public ResponseEntity<?> deletePlaylist(
            @CurrentUser User user,
            @PathVariable(value = "pflanzeid") int pflanzeid,
            @PathVariable(value = "bildid") int bildid) {
        try {
            Buerger buerger = buergerService.getBuergerByEmail(user.getUniqueString()).orElseThrow();
            buergerService.deleteBild(pflanzeid, bildid, buerger);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bild mit ID " + bildid + " wurde gelöscht.");
        } catch (APIException a) {
            return errorResponse(a);
        }
    }


    @PatchMapping("pflanzen/{pflanzeid}/bilder/{bildid}")
    public ResponseEntity<?> updateBildFromPflanze(
            @PathVariable(value = "pflanzeid") int pflanzeid,
            @PathVariable(value = "bildid") int bildid,
            @RequestParam(value = "pfad") String pfad) {
        try {
            buergerService.updateBildFromPflanze(pflanzeid, bildid, pfad);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Bild mit ID " + bildid + " wurde geupdatet."); //wird nicht ausgegeben
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

}
