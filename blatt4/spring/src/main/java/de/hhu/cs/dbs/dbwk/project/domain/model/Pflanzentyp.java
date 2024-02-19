package de.hhu.cs.dbs.dbwk.project.domain.model;

public class Pflanzentyp {

    private final int pflanzentypid;

    private final String typ;


    public Pflanzentyp(int pflanzentypid, String typ) {
        this.pflanzentypid = pflanzentypid;
        this.typ = typ;
    }

    public int getPflanzentypid() {
        return pflanzentypid;
    }

    public String getTyp() {
        return typ;
    }
}
