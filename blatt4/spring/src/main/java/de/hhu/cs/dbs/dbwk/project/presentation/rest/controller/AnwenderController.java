package de.hhu.cs.dbs.dbwk.project.presentation.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hhu.cs.dbs.dbwk.project.domain.errors.APIException;
import de.hhu.cs.dbs.dbwk.project.domain.model.*;
import de.hhu.cs.dbs.dbwk.project.domain.model.views.Views;
import de.hhu.cs.dbs.dbwk.project.domain.services.AnwenderService;
import de.hhu.cs.dbs.dbwk.project.presentation.rest.ControllerHelper;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.hhu.cs.dbs.dbwk.project.presentation.rest.ControllerHelper.errorResponse;


@RequestMapping("/")
@PermitAll
@RestController
@EnableWebSecurity()
public class AnwenderController {

    private final AnwenderService anwenderService;

    public AnwenderController(AnwenderService anwenderService) {
        this.anwenderService = anwenderService;
    }

    @GetMapping("nutzer")
    public ResponseEntity<?> getNutzer(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "nachname", required = false) String nachname) {
        try {
            List<Nutzer> nutzer = anwenderService.getAllNutzerWithFilter(email, nachname);

            return ResponseEntity.status(HttpStatus.OK).body(nutzer);


        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @PostMapping("nutzer")
    public ResponseEntity<?> insertNutzer(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "passwort") String passwort,
            @RequestParam(value = "vorname") String vorname,
            @RequestParam(value = "nachname") String nachname) {
        try {
            Optional<Nutzer> nutzer = anwenderService.insertNutzer(email, passwort, vorname, nachname);
            return nutzer.map(
                    value -> ResponseEntity.status(HttpStatus.CREATED)
                            .header("Location", "/nutzer" + value.getNutzerId() + "/")
                            .body("Nutzer mit ID " + value.getNutzerId() + " wurde erstellt."))
                    .orElseGet(ControllerHelper::somethingWentWrongResponse);

        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("adressen")
    public ResponseEntity<?> getAdressen(
            @RequestParam(value = "stadt", required = false) String stadt) {
        try {
            List<Adresse> adressen = anwenderService.getAdressenByStadt(stadt);
            return ResponseEntity.status(HttpStatus.OK).body(adressen);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @PostMapping("adressen")
    public ResponseEntity<?> insertAdresse(
            @RequestParam(value = "strasse") String strasse,
            @RequestParam(value = "hausnummer") String hausnummer,
            @RequestParam(value = "plz") String plz,
            @RequestParam(value = "stadt") String stadt) {
        try {
            Optional<Adresse> adresse = anwenderService.insertAdresse(strasse, hausnummer, plz, stadt);
            return adresse.map(
                    value -> {
                        if (value.isAlreadyExists()) {
                            return ResponseEntity.status(HttpStatus.OK)
                                    .header("Location", "/adressen" + value.getAdresseId() + "/")
                                    .body("Adresse mit ID " + value.getAdresseId() + " existiert bereits.");
                        } else {
                            return ResponseEntity.status(HttpStatus.CREATED)
                                    .header("Location", "/adressen" + value.getAdresseId() + "/")
                                    .body("Adresse mit ID " + value.getAdresseId() + " wurde erstellt.");
                        }
                    }).orElseGet(ControllerHelper::somethingWentWrongResponse);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("buerger")
    public ResponseEntity<?> getBuerger(
            @RequestParam(value = "stadt", required = false) String stadt) {
        try {
            List<Buerger> buerger = anwenderService.getAllBuergerWithFilter(stadt);

            return ResponseEntity.status(HttpStatus.OK).body(buerger);


        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @PostMapping("buerger")
    public ResponseEntity<?> insertBuerger(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "passwort") String passwort,
            @RequestParam(value = "vorname") String vorname,
            @RequestParam(value = "nachname") String nachname,
            @RequestParam(value = "adresseid") int adresseid) {
        try {
            Optional<Buerger> buerger = anwenderService.insertBuerger(email, passwort, vorname, nachname, adresseid);
            return buerger.map(
                    value -> ResponseEntity.status(HttpStatus.CREATED)
                            .header("Location", "/buerger" + value.getBuergerId() + "/")
                            .body("Buerger mit ID " + value.getBuergerId() + " wurde erstellt."))
                    .orElseGet(ControllerHelper::somethingWentWrongResponse);

        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("gaertner")
    public ResponseEntity<?> getGaertner(
            @RequestParam(value = "email", required = false) String email) {
        try {
            List<Gaertner> gaertner = anwenderService.getAllGaertnerByFilter(email);

            return ResponseEntity.status(HttpStatus.OK).body(gaertner);


        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @PostMapping("gaertner")
    public ResponseEntity<?> insertGaertner(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "passwort") String passwort,
            @RequestParam(value = "vorname") String vorname,
            @RequestParam(value = "nachname") String nachname,
            @RequestParam(value = "spezialgebiet") String spezialgebiet) {
        try {
            Optional<Gaertner> gaertner = anwenderService.insertGaertner(email, passwort, vorname, nachname, spezialgebiet);
            return gaertner.map(
                    value -> ResponseEntity.status(HttpStatus.CREATED)
                            .header("Location", "/gaertner" + value.getGaertnerId() + "/")
                            .body("Gaertner mit ID " + value.getGaertnerId() + " wurde erstellt."))
                    .orElseGet(ControllerHelper::somethingWentWrongResponse);

        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("pflanzen")
    public ResponseEntity<?> getPflanzen(
            @RequestParam(value = "dname", required = false) String dname) {
        try {
            List<Pflanze> pflanzen = anwenderService.getAllPflanzenByFilter(dname);

            return ResponseEntity.status(HttpStatus.OK).body(pflanzen);


        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("pflegemassnahmen")
    public ResponseEntity<?> getPflegemassnahmen(
            @RequestParam(value = "pflegeart", required = false) String pflegeart,
            @RequestParam(value = "min_avg_bewertung", required = false) Float min_avg_bewertung ) {
        try {
            List<Pflegemassnahme> pflegemassnahmen = anwenderService.getAllPflegemassnahmenByFilter(pflegeart, min_avg_bewertung);

            return ResponseEntity.status(HttpStatus.OK).body(pflegemassnahmen);


        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("pflegeprotokolle")
    public ResponseEntity<?> getPflegeprotokolle() {
        try {
            List<Pflegeprotokoll> pflegeprotokolle = anwenderService.getAllPflegeprotokolle();

            return ResponseEntity.status(HttpStatus.OK).body(pflegeprotokolle);


        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("pflegemassnahmen/bewertungen")
    public ResponseEntity<?> getBewertungenVonPflegemassnahmen() {
        try {
            // return this list as a json object
            List<Bewertung> bewertungen =
                    anwenderService.getBewertungen();
            return ResponseEntity.status(HttpStatus.OK).body(bewertungen);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @GetMapping("pflanzen/{pflanzeid}/bilder")
    public ResponseEntity<?> getBilderEinerPflanze(
            @PathVariable("pflanzeid") int pflanzeid) {
        try {
            // return this list as a json object
            List<Bild> bilder =
                    anwenderService.getBilder(pflanzeid);
            return ResponseEntity.status(HttpStatus.OK).body(bilder);
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @DeleteMapping("adressen/{adresseid}")
    public ResponseEntity<?> deleteAdresse(
            @PathVariable(value = "adresseid") Integer adresseid) {
        try {

            anwenderService.deleteAdresse(adresseid);
            return ResponseEntity.status(HttpStatus.NO_CONTENT) //wird nicht ausgegeben

                    .body("Adresse mit ID " + adresseid + " wurde gelöscht.");
        } catch (APIException a) {
            return errorResponse(a);
        }
    }

    @PatchMapping("adressen/{adresseid}")
    public ResponseEntity<?> updateAdresse(
            @PathVariable(value = "adresseid") int adresseid,
            @RequestParam(value = "strasse") String strasse,
            @RequestParam(value = "hausnummer") String hausnummer,
            @RequestParam(value = "plz") String plz,
            @RequestParam(value = "stadt") String stadt) {
        try {

            anwenderService.updateAdresse(adresseid, strasse, hausnummer, plz, stadt);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Adresse mit ID " + adresseid + " wurde geupdatet."); //wird nicht ausgegeben
        } catch (APIException a) {
            return errorResponse(a);
        }
    }






}
