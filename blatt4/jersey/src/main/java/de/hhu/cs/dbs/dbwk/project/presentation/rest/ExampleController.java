package de.hhu.cs.dbs.dbwk.project.presentation.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
@PermitAll
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class ExampleController {

    private final SecurityContext securityContext;
    private final UriInfo uriInfo;

    @Inject
    public ExampleController(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        this.securityContext = securityContext;
        this.uriInfo = uriInfo;
    }

    @GET
    // GET http://localhost:8080
    public String halloWelt() {
        return "\"Hallo Welt!\"";
    }

    @GET
    @Path("/exception")
    // GET http://localhost:8080
    public String halloException() {
        throw new WebApplicationException("Hallo Exception!");
    }

    @GET
    @Path("foo")
    @RolesAllowed("USER")
    // GET http://localhost:8080/foo => OK. Siehe UserRepository.
    public String halloFoo() {
        return "\"Hallo " + securityContext.getUserPrincipal().getName() + "!\"";
    }

    @GET
    @Path("foo2")
    @RolesAllowed("EMPLOYEE")
    // GET http://localhost:8080/foo2 => FORBIDDEN. Siehe SQLiteUserRepository.
    public String halloFoo2() {
        return "\"Hallo " + securityContext.getUserPrincipal().getName() + "!\"";
    }

    @GET
    @Path("foo3/{foo}")
    // GET http://localhost:8080/foo3/bar
    public Object halloFoo3(@PathParam("foo") String foo) {
        if (!foo.equals("bar")) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return "\"Hallo " + foo + "\"!";
    }

    @GET
    @Path("foo4")
    // GET http://localhost:8080/foo4?bar=xyz
    public String halloFoo4(@QueryParam("bar") String bar) {
        return "\"Hallo " + bar + "\"!";
    }

    @GET
    @Path("bar")
    // GET http://localhost:8080/bar => BAD REQUEST; http://localhost/bar?foo=xyz => OK
    public Response halloBar(@QueryParam("foo") String foo) {
        if (foo == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity("Hallo" + foo + "!").build();
    }

    @GET
    @Path("bar2")
    // GET http://localhost:8080/bar2
    public List<Map<String, Object>> halloBar2(
            @QueryParam("name") @DefaultValue("Max Mustermann") List<String> names) {
        List<Map<String, Object>> entities = new ArrayList<>();
        Map<String, Object> entity;
        for (String name : names) {
            entity = new HashMap<>();
            entity.put("name", name);
            entities.add(entity);
        }
        return entities;
    }

    @POST
    @Path("foo")
    // POST http://localhost:8080/foo
    public Response upload(
            @FormDataParam("name") String name, @FormDataParam("file") InputStream file) {
        if (name == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if (file == null) return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            String length = String.valueOf(file.readAllBytes().length);
            return Response.created(uriInfo.getAbsolutePathBuilder().path(length).build()).build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
