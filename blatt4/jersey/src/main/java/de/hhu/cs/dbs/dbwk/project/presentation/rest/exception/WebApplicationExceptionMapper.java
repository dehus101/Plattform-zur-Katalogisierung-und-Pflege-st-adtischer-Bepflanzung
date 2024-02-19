package de.hhu.cs.dbs.dbwk.project.presentation.rest.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception) {
        return Response.fromResponse(exception.getResponse())
                .entity(
                        new ExceptionResponse(
                                exception.getMessage(), exception.getResponse().getStatus()))
                .build();
    }
}
