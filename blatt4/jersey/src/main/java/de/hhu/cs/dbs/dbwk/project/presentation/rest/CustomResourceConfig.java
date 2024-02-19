package de.hhu.cs.dbs.dbwk.project.presentation.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class CustomResourceConfig extends ResourceConfig {

    public CustomResourceConfig(AbstractBinder binder) {
        packages("de.hhu.cs.dbs.dbwk.project;org.glassfish.jersey.examples.multipart");
        register(binder);
        register(RolesAllowedDynamicFeature.class);
        register(MultiPartFeature.class);
        register(LoggingFeature.class);
        register(JacksonFeature.class);
    }
}
