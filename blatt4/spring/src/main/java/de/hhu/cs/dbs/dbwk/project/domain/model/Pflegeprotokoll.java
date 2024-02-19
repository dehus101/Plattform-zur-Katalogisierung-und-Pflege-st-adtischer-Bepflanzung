package de.hhu.cs.dbs.dbwk.project.domain.model;

public class Pflegeprotokoll {

    private final int pflegemassnahmen_anzahl;

    private final int gaertnerid;

    public Pflegeprotokoll(int pflegemassnahmenAnzahl, int gaertnerid) {
        pflegemassnahmen_anzahl = pflegemassnahmenAnzahl;
        this.gaertnerid = gaertnerid;
    }

    public int getPflegemassnahmen_anzahl() {
        return pflegemassnahmen_anzahl;
    }

    public int getGaertnerid() {
        return gaertnerid;
    }
}
