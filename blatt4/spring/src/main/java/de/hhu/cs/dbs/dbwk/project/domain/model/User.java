package de.hhu.cs.dbs.dbwk.project.domain.model;

import java.util.Locale;
import java.util.Set;

/** Spiegelt einen Nutzer der Anwendung wider. */
public interface User {

    default boolean hasRole(String role) {
        return getRoles().stream().anyMatch(r -> r.getValue().equals(role.toUpperCase(Locale.ENGLISH)));
    }

    String getUniqueString();

    void setUniqueString(String uniqueString);

    String getPassword();

    void setPassword(String password);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);
}
