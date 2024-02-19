package de.hhu.cs.dbs.dbwk.project.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import de.hhu.cs.dbs.dbwk.project.domain.model.views.Views;

@JsonPropertyOrder({"nutzerid" ,"email", "passwort", "vorname", "nachname" })
public class Nutzer {

    private final int nutzerid;
    private final String email;
    private final String passwort;
    private final String vorname;

    private final String nachname;

    public Nutzer(int nutzerid, String email, String vorname,  String nachname, String passwort) {
        this.nutzerid = nutzerid;
        this.email = email;
        this.vorname = vorname;
        this.nachname = nachname;
        this.passwort = passwort;
    }

    @JsonProperty("nutzerid")
    public int getNutzerId() {
        return nutzerid;
    }

    public String getVorname() {
        return vorname;
    }


    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getNachname() {
        return nachname;
    }
}
