package de.hhu.cs.dbs.dbwk.project.domain.model;

public class Bewertung {

    private final int pflegemassnahmeid;

    private final int bewertungid;

    private final int gaertnerid;

    public Bewertung(int pflegemassnahmeid, int bewertungid, int gaertnerid) {
        this.pflegemassnahmeid = pflegemassnahmeid;
        this.bewertungid = bewertungid;
        this.gaertnerid = gaertnerid;
    }

    public int getPflegemassnahmeid() {
        return pflegemassnahmeid;
    }

    public int getBewertungid() {
        return bewertungid;
    }

    public int getGaertnerid() {
        return gaertnerid;
    }
}
