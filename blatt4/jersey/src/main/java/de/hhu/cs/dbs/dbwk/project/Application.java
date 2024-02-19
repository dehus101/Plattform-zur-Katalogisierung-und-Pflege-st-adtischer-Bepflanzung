package de.hhu.cs.dbs.dbwk.project;

import de.hhu.cs.dbs.dbwk.project.dic.CustomBinder;
import de.hhu.cs.dbs.dbwk.project.persistence.PersistenceConfiguration;
import de.hhu.cs.dbs.dbwk.project.persistence.inmemory.InMemoryPersistenceConfiguration;
import de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SqlitePersistenceConfiguration;
import de.hhu.cs.dbs.dbwk.project.security.SecurityConfiguration;
import de.hhu.cs.dbs.dbwk.project.security.http.BasicHttpAuthenticationService;
import de.hhu.cs.dbs.dbwk.project.security.http.HttpAuthenticationService;
import de.hhu.cs.dbs.dbwk.project.security.http.HttpSecurityConfiguration;
import de.hhu.cs.dbs.dbwk.project.presentation.rest.CustomResourceConfig;

import jakarta.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

public class Application {

    public static final int DEFAULT_PORT = 8080;
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        int p;
        try {
            String envvar = System.getenv("PROJECT_IMPLEMENTATION_URL_PORT");
            p = Integer.parseInt(envvar);
        } catch (NumberFormatException exception) {
            logger.warning(
                    "PROJECT_IMPLEMENTATION_URL_PORT is not an integer, set to default ("
                            + DEFAULT_PORT
                            + ").");
            p = DEFAULT_PORT;
        }
        int port = p;
        logger.info("Starting server on port " + port + "...");
        URI uri = UriBuilder.fromUri("//0.0.0.0/").scheme("http").port(port).build();

        ResourceConfig resourceConfig = compositionRoot();
        HttpServer httpServer =
                GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, false);
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    logger.info("Stopping server on port   " + port + "...");
                                    httpServer.shutdownNow();
                                }));
        try {
            httpServer.start();
            logger.info("Waiting for requests...\n");
        } catch (IOException exception) {
            logger.severe("Exception: " + exception.getMessage());
        }
    }

    private static ResourceConfig compositionRoot() {
        PersistenceConfiguration persistenceConfiguration = new InMemoryPersistenceConfiguration();
        HttpAuthenticationService authenticationService =
                new BasicHttpAuthenticationService(persistenceConfiguration.userRepository());
        SecurityConfiguration securityConfiguration =
                new HttpSecurityConfiguration(authenticationService);

        AbstractBinder binder = new CustomBinder(persistenceConfiguration, securityConfiguration);
        return new CustomResourceConfig(binder);
    }
}
