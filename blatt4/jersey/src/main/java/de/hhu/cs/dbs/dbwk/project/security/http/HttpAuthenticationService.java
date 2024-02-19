package de.hhu.cs.dbs.dbwk.project.security.http;

import de.hhu.cs.dbs.dbwk.project.security.AuthenticationService;

public abstract class HttpAuthenticationService implements AuthenticationService {

    public static final String REALM = "Restricted access";

    public abstract boolean isSecure();

    public abstract boolean validateHeader(String header);

    public String getRealm() {
        return REALM;
    }

    public String getWwwAuthenticateHeader() {
        return getAuthenticationScheme().toLowerCase() + " realm=\"" + getRealm() + "\"";
    }
}
