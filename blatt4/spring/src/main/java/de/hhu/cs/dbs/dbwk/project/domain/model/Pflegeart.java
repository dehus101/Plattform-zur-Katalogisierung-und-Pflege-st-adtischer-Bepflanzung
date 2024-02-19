package de.hhu.cs.dbs.dbwk.project.domain.model;

public class Pflegeart {

    private final int pflegeartid;

    private final String art;

    public Pflegeart(int pflegeartid, String art) {
        this.pflegeartid = pflegeartid;
        this.art = art;
    }

    public int getPflegeartid() {
        return pflegeartid;
    }

    public String getArt() {
        return art;
    }
}
