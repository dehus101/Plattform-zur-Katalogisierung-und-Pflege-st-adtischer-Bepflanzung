package de.hhu.cs.dbs.dbwk.project.domain.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.hhu.cs.dbs.dbwk.project.domain.utils.SpezialisierungListToStringSerializer;

import java.util.List;

@JsonPropertyOrder({ "nutzerid", "gaertnerid", "email", "passwort", "vorname", "nachname", "spezialgebiet" })
public class Gaertner{

    private final int gaertnerid;

    @JsonUnwrapped
    public final Nutzer nutzer;

    @JsonProperty("spezialgebiet")
    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = SpezialisierungListToStringSerializer.class)
    private final List<Spezialisierung> spezialisierungen;

    public Gaertner(int gaertnerid, Nutzer nutzer, List<Spezialisierung> spezialisierungen) {
        this.gaertnerid = gaertnerid;
        this.nutzer = nutzer;
        this.spezialisierungen = spezialisierungen;
    }


    @JsonProperty("gaertnerid")
    public int getGaertnerId() {
        return gaertnerid;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }


    public List<Spezialisierung> getSpezialisierungen() {
        return spezialisierungen;
    }
}
