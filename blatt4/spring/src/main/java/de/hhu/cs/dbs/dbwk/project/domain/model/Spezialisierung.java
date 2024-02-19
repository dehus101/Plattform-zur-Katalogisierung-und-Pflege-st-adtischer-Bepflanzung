package de.hhu.cs.dbs.dbwk.project.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Spezialisierung {

    @JsonIgnore
    private final int spezialisierungid;


    private final String spezialgebiet;

    public Spezialisierung(int spezialisierungid, String spezialgebiet) {
        this.spezialisierungid = spezialisierungid;
        this.spezialgebiet = spezialgebiet;
    }

    public int getSpezialisierungid() {
        return spezialisierungid;
    }

    @JsonProperty("spezialgebiet")
    public String getSpezialgebiet() {
        return spezialgebiet;
    }
}
