package de.hhu.cs.dbs.dbwk.project.security;

import de.hhu.cs.dbs.dbwk.project.model.User;

import java.lang.reflect.Method;

public interface AuthenticationService {

    User authenticate(String authentication, Method method);

    String getAuthenticationScheme();
}
