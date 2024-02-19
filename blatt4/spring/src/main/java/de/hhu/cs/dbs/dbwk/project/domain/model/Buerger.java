package de.hhu.cs.dbs.dbwk.project.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonPropertyOrder({ "nutzerid", "buergerid", "adresseid", "email", "passwort", "vorname", "nachname" })
public class Buerger {

    private final int buergerid;

    @JsonUnwrapped public final Nutzer nutzer;

    @JsonUnwrapped
    @JsonIgnoreProperties({ "strasse", "hausnummer", "plz", "stadt" })
    @JsonProperty("adresseid")
    public final Adresse adresse;

    public Buerger(int buergerid, Nutzer nutzer, Adresse adresse) {
        this.buergerid = buergerid;
        this.nutzer = nutzer;
        this.adresse = adresse;
    }

    @JsonProperty("buergerid")
    public int getBuergerId() {
        return buergerid;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public Adresse getAdresse() {
        return adresse;
    }
}
