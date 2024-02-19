package de.hhu.cs.dbs.dbwk.project.model;

import java.security.Principal;
import java.util.Set;

/** Spiegelt einen Nutzer der Anwendung wider. */
public interface User extends Principal {

    String getUniqueString();

    void setUniqueString(String uniqueString);

    String getPassword();

    void setPassword(String password);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    @Override
    default String getName() {
        return getUniqueString();
    }
}
