package de.hhu.cs.dbs.dbwk.project.domain.model;

import java.awt.*;
import java.util.List;

public class Bild {

    private final int bildid;

    private final int pflanzeid;

    private final String pfad;


    public Bild(int bildid, int pflanzeid, String pfad) {
        this.bildid = bildid;
        this.pflanzeid = pflanzeid;
        this.pfad = pfad;
    }

    public int getBildid() {
        return bildid;
    }

    public int getPflanzeid() {
        return pflanzeid;
    }

    public String getPfad() {
        return pfad;
    }
}
