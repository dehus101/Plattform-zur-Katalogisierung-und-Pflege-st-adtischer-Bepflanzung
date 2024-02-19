package de.hhu.cs.dbs.dbwk.project.domain.model;

public class Pflegemassnahme {

    private final int pflegemassnahmeid;

    private final float avg_bewertung;

    private final String datum;
    private final String pflegeart;



    public Pflegemassnahme(int pflegemassnahmeid, float avgBewertung, String datum, String pflegeart) {
        this.pflegemassnahmeid = pflegemassnahmeid;
        this.avg_bewertung = avgBewertung;
        this.pflegeart = pflegeart;
        this.datum = datum;
    }

    public int getPflegemassnahmeid() {
        return pflegemassnahmeid;
    }

    public float getAvg_bewertung() {
        return avg_bewertung;
    }

    public String getDatum() {
        return datum.split(" ")[0];
    }



    public String getPflegeart() {
        return pflegeart;
    }
}
