package de.hhu.cs.dbs.dbwk.project.security.http;

import de.hhu.cs.dbs.dbwk.project.security.SecurityConfiguration;

public class HttpSecurityConfiguration implements SecurityConfiguration {

    private final HttpAuthenticationService authenticationService;

    public HttpSecurityConfiguration(HttpAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public HttpAuthenticationService authenticationService() {
        return authenticationService;
    }
}
