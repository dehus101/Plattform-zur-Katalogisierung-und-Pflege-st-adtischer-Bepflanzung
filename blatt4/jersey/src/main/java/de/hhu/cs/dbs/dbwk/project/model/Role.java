package de.hhu.cs.dbs.dbwk.project.model;

import java.util.Set;

/** Spiegelt einen Nutzertyp in der Datenbank wider. */
public interface Role {

    String getValue();

    Set<Role> getIncludedRoles();
}
