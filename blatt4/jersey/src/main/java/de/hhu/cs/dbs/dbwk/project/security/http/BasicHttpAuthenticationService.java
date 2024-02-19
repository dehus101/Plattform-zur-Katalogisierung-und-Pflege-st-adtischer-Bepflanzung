package de.hhu.cs.dbs.dbwk.project.security.http;

import de.hhu.cs.dbs.dbwk.project.model.SimpleUser;
import de.hhu.cs.dbs.dbwk.project.model.User;
import de.hhu.cs.dbs.dbwk.project.model.UserRepository;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.SecurityContext;

import org.glassfish.jersey.server.model.AnnotatedMethod;

import java.lang.reflect.Method;
import java.util.Base64;

public class BasicHttpAuthenticationService extends HttpAuthenticationService {

    private final UserRepository userRepository;

    @Inject
    public BasicHttpAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User authenticate(String authentication, Method method) {
        if (authentication == null) {
            AnnotatedMethod annotatedMethod = new AnnotatedMethod(method);
            if (!annotatedMethod.isAnnotationPresent(DenyAll.class)
                    && annotatedMethod.isAnnotationPresent(RolesAllowed.class)) {
                throw new NotAuthorizedException(getWwwAuthenticateHeader());
            }
            return new SimpleUser(null, null, null);
        }
        if (!validateHeader(authentication)) {
            throw new NotAuthorizedException(getWwwAuthenticateHeader());
        }
        String base64EncodedCredentials = authentication.substring("Basic ".length());
        String base64DecodedCredentials = decodeCredentials(base64EncodedCredentials);
        String uniqueString = getFirstFromDecodedCredentials(base64DecodedCredentials);
        String password = getSecondFromDecodedCredentials(base64DecodedCredentials);
        return authenticate(uniqueString, password);
    }

    private String decodeCredentials(String base64EncodedCredentials) {
        return new String(Base64.getDecoder().decode(base64EncodedCredentials.getBytes()));
    }

    private String getFirstFromDecodedCredentials(String base64DecodedCredentials) {
        return base64DecodedCredentials.replaceFirst(":.*", "");
    }

    private String getSecondFromDecodedCredentials(String base64DecodedCredentials) {
        return base64DecodedCredentials.replaceFirst(".*?:", "");
    }

    private User authenticate(String uniqueString, String password) {
        User user =
                userRepository
                        .findUser(uniqueString)
                        .orElseThrow(() -> new NotAuthorizedException(getWwwAuthenticateHeader()));
        boolean isAuthenticated = user.getPassword().equals(password);
        if (!isAuthenticated) {
            throw new NotAuthorizedException(getWwwAuthenticateHeader());
        }
        return user;
    }

    @Override
    public final boolean validateHeader(String header) {
        return header != null && header.startsWith("Basic ");
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public final String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
