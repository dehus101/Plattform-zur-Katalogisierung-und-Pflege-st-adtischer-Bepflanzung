package de.hhu.cs.dbs.dbwk.project.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Pflanze {

    private final int pflanzeid;

    private final String lname;

    private final String dname;

    @JsonUnwrapped
    @JsonIgnoreProperties({ "standortid"})
    public final Standort standort;

    private final String pflanzentyp;

    private final String datum;


    public Pflanze(int pflanzeid, String lname, String dname, Standort standort, String pflanzentyp, String datum) {
        this.pflanzeid = pflanzeid;
        this.lname = lname;
        this.dname = dname;
        this.standort = standort;
        this.pflanzentyp = pflanzentyp;
        this.datum = datum;
    }

    public int getPflanzeid() {
        return pflanzeid;
    }

    public String getLname() {
        return lname;
    }

    public String getDname() {
        return dname;
    }

    public Standort getStandort() {
        return standort;
    }

    public String getPflanzentyp() {
        return pflanzentyp;
    }

    public String getDatum() {
        return datum;
    }
}
