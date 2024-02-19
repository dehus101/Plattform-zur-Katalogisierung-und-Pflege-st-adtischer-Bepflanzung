package de.hhu.cs.dbs.dbwk.project.domain.model.dto;

public class PflegemassnahmeDTO {

    private final int pflegemassnahmeid;

    private final String datum;

    private final int pflegeartid;

    private final String email;

    public PflegemassnahmeDTO(int pflegemassnahmeid, String datum, int pflegeartid, String email) {
        this.pflegemassnahmeid = pflegemassnahmeid;
        this.datum = datum;
        this.pflegeartid = pflegeartid;
        this.email = email;
    }

    public int getPflegemassnahmeid() {
        return pflegemassnahmeid;
    }

    public String getDatum() {
        return datum.split(" ")[0];
    }

    public int getPflegeartid() {
        return pflegeartid;
    }

    public String getEmail() {
        return email;
    }
}
