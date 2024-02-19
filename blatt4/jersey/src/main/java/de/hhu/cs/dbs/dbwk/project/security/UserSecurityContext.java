package de.hhu.cs.dbs.dbwk.project.security;

import de.hhu.cs.dbs.dbwk.project.model.Role;
import de.hhu.cs.dbs.dbwk.project.model.User;

import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

public class UserSecurityContext implements SecurityContext {

    private final User user;

    public UserSecurityContext(User user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user != null
                && user.getRoles().stream()
                        .flatMap(r -> r.getIncludedRoles().stream())
                        .map(Role::getValue)
                        .anyMatch(role::equals);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }

    public User getUser() {
        return user;
    }
}
