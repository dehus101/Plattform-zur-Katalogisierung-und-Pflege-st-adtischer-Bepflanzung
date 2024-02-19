package de.hhu.cs.dbs.dbwk.project.security;

import de.hhu.cs.dbs.dbwk.project.model.User;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    private final AuthenticationService authenticationService;
    private final ResourceInfo resourceInfo;

    @Inject
    public SecurityFilter(
            AuthenticationService authenticationService, @Context ResourceInfo resourceInfo) {
        this.authenticationService = authenticationService;
        this.resourceInfo = resourceInfo;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        requestContext.setSecurityContext(new UserSecurityContext(null));
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        User user =
                authenticationService.authenticate(
                        authorizationHeader, resourceInfo.getResourceMethod());
        requestContext.setSecurityContext(new UserSecurityContext(user));
    }
}
