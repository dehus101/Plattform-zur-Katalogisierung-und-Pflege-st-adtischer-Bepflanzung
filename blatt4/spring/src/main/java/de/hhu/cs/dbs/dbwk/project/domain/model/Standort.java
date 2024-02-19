package de.hhu.cs.dbs.dbwk.project.domain.model;

public class Standort {

    private final int standortid;

    private final float breitengrad;
    private final float laengengrad;

    public Standort(int standortid, float breitengrad, float laengengrad) {
        this.standortid = standortid;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
    }

    public int getStandortid() {
        return standortid;
    }

    public float getBreitengrad() {
        return breitengrad;
    }

    public float getLaengengrad() {
        return laengengrad;
    }
}
