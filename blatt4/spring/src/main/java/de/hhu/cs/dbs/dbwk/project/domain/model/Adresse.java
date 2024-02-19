package de.hhu.cs.dbs.dbwk.project.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"adresseid" ,"strasse", "hausnummer", "plz", "stadt" })
public class Adresse {

    @JsonIgnore
    private boolean alreadyExists;

    private final int adresseid;

    private final String stadt;

    private final String Strasse;

    private final String Hausnummer;

    private final String PLZ;

    public Adresse(int adresseid, String stadt, String strasse, String hausnummer, String PLZ) {
        this.adresseid = adresseid;
        this.stadt = stadt;
        this.Strasse = strasse;
        this.Hausnummer = hausnummer;
        this.PLZ = PLZ;
    }

    @JsonProperty("adresseid")
    public int getAdresseId() {
        return adresseid;
    }

    public String getStadt() {
        return stadt;
    }

    public String getStrasse() {
        return Strasse;
    }

    public String getHausnummer() {
        return Hausnummer;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setAlreadyExists(boolean alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public boolean isAlreadyExists() {
        return alreadyExists;
    }
}
